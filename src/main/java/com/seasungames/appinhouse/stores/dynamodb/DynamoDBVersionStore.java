package com.seasungames.appinhouse.stores.dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.seasungames.appinhouse.models.VersionVo;
import com.seasungames.appinhouse.stores.IVersion;
import com.seasungames.appinhouse.stores.dynamodb.tables.VersionTable;
import io.vertx.core.json.JsonArray;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * Created by lile on 12/28/2018
 */
public class DynamoDBVersionStore implements IVersion {

    private static final Logger LOG = LogManager.getLogger(DynamoDBAppStore.class);

    private final String tableName = "versions";

    private Table table;
    private AmazonDynamoDB ddb;
    private String indexName = "platform_index";

    public DynamoDBVersionStore(AmazonDynamoDB ddb) {
        this.ddb = ddb;
        table = new DynamoDB(ddb).getTable(tableName);

        CreateTable();
    }

    private void CreateTable() {

        LOG.info("Creating dynamodb table: " + tableName);


        // Attribute definitions
        ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<>();

        attributeDefinitions.add(new AttributeDefinition()
                .withAttributeName(VersionTable.HASH_KEY_APPID)
                .withAttributeType("S"));
        attributeDefinitions.add(new AttributeDefinition()
                .withAttributeName(VersionTable.RANGE_KEY_VERSION)
                .withAttributeType("S"));
        attributeDefinitions.add(new AttributeDefinition()
                .withAttributeName(VersionTable.SECONDARY_INDEX_VERSION)
                .withAttributeType("S"));

        // Table key schema
        ArrayList<KeySchemaElement> tableKeySchema = new ArrayList<>();
        tableKeySchema.add(new KeySchemaElement()
                .withAttributeName(VersionTable.HASH_KEY_APPID)
                .withKeyType(KeyType.HASH));
        tableKeySchema.add(new KeySchemaElement()
                .withAttributeName(VersionTable.RANGE_KEY_VERSION)
                .withKeyType(KeyType.RANGE));

        // PlatformIndex
        GlobalSecondaryIndex platformIndex = new GlobalSecondaryIndex()
                .withIndexName(indexName)
                .withProvisionedThroughput(new ProvisionedThroughput()
                        .withReadCapacityUnits((long) 10)
                        .withWriteCapacityUnits((long) 1))
                .withKeySchema(new KeySchemaElement().withAttributeName(VersionTable.SECONDARY_INDEX_VERSION).withKeyType(KeyType.HASH),
                        new KeySchemaElement().withAttributeName(VersionTable.HASH_KEY_APPID).withKeyType(KeyType.RANGE))
                .withProjection(new Projection().withProjectionType(ProjectionType.ALL));

        //
        CreateTableRequest req = new CreateTableRequest()
                .withTableName(tableName)
                .withProvisionedThroughput(new ProvisionedThroughput()
                        .withReadCapacityUnits((long) 5)
                        .withWriteCapacityUnits((long) 1))
                .withAttributeDefinitions(attributeDefinitions)
                .withKeySchema(tableKeySchema)
                .withGlobalSecondaryIndexes(platformIndex);

        try {
            if (TableUtils.createTableIfNotExists(this.ddb, req)) {
                TableUtils.waitUntilActive(this.ddb, tableName);
            }
        } catch (InterruptedException e) {
            LOG.info("Creating dynamodb table: {} , reason: {}", tableName, e.getMessage());
        }
    }

    /**
     * Impl
     **/

    @Override
    public int CreateVersion(VersionVo vo) {
        final Map<String, Object> infoMap = new HashMap<>();
        infoMap.put(VersionTable.ATTRIBUTE_DOWNLOAD_URL, vo.getDownload_url());
        infoMap.put(VersionTable.ATTRIBUTE_JENKINS_URL, vo.getJenkins_url());
        infoMap.put(VersionTable.ATTRIBUTE_PLIST, vo.getPlist());
        infoMap.put(VersionTable.ATTRIBUTE_TIME, vo.getCreate_time());

        try {
            Item item = new Item().withPrimaryKey(VersionTable.HASH_KEY_APPID,
                    vo.getAppId(), VersionTable.RANGE_KEY_VERSION, vo.getVersion())
                    .withString(VersionTable.SECONDARY_INDEX_VERSION, vo.getPlatform())
                    .withMap(VersionTable.ATTRIBUTE_JSON_INFO, infoMap);

            PutItemOutcome outcome = table.putItem(item); //check new or replace.
            return 0;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 1;
        }
    }

    @Override
    public String GetLatestList(String appId) {
        return null;
    }

    @Override
    public String GetPlatformList(String appId, String platform) {
        Index index = table.getIndex(indexName);

        QuerySpec querySpec = new QuerySpec();
        querySpec.withKeyConditionExpression("#platform = :v_platform and #id = :v_id")
                .withNameMap(new NameMap().with("#platform", VersionTable.SECONDARY_INDEX_VERSION).with("#id", VersionTable.HASH_KEY_APPID))
                .withValueMap(new ValueMap().withString(":v_platform", platform).withString(":v_id", appId));

        ItemCollection<QueryOutcome> items = index.query(querySpec);

        Iterator<Item> iterator = items.iterator();

        List<String> jsonList = new ArrayList<>();

        while (iterator.hasNext()) {
            jsonList.add(iterator.next().toJSON());
        }
        return new JsonArray(jsonList).toString();
    }
}
