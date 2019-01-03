package com.seasungames.appinhouse.stores.dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.seasungames.appinhouse.application.ConfigManager;
import com.seasungames.appinhouse.application.PlatformConstant;
import com.seasungames.appinhouse.models.VersionVo;
import com.seasungames.appinhouse.stores.IVersion;
import com.seasungames.appinhouse.stores.dynamodb.tables.VersionTable;
import io.vertx.core.json.JsonArray;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.*;

/**
 * Created by lile on 12/28/2018
 */
public class DynamoDBVersionStore implements IVersion {

    private static final Logger LOG = LogManager.getLogger(DynamoDBAppStore.class);

    private final String tableName = "versions";

    @Inject
    public ConfigManager conf;

    private Table table;
    private AmazonDynamoDB ddb;

    public DynamoDBVersionStore(AmazonDynamoDB ddb) {
        this.ddb = ddb;
        table = new DynamoDB(ddb).getTable(tableName);

        if(conf.createDynamoDBTableOnStartup()) {
            CreateTable();
        }
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

        // Table key schema
        ArrayList<KeySchemaElement> tableKeySchema = new ArrayList<>();
        tableKeySchema.add(new KeySchemaElement()
                .withAttributeName(VersionTable.HASH_KEY_APPID)
                .withKeyType(KeyType.HASH));
        tableKeySchema.add(new KeySchemaElement()
                .withAttributeName(VersionTable.RANGE_KEY_VERSION)
                .withKeyType(KeyType.RANGE));

        CreateTableRequest req = new CreateTableRequest()
                .withTableName(tableName)
                .withProvisionedThroughput(new ProvisionedThroughput()
                        .withReadCapacityUnits(conf.dynamoDBTableReadThroughput())
                        .withWriteCapacityUnits(conf.dynamoDBTableWriteThroughput()))
                .withAttributeDefinitions(attributeDefinitions)
                .withKeySchema(tableKeySchema);

        try {
            if (TableUtils.createTableIfNotExists(this.ddb, req)) {
                TableUtils.waitUntilActive(this.ddb, tableName);
            }
        } catch (InterruptedException e) {
            LOG.info("Creating dynamodb table: {} , reason: {}", tableName, e.getMessage());
        }
    }

    private String GetPrimaryKey(String id, String platform) {
        return id + "_" + platform;
    }

    /**
     * Impl
     **/

    @Override
    public VersionVo GetOneApp(String id, String platform, String version) {
        GetItemSpec spec = new GetItemSpec().withPrimaryKey(VersionTable.HASH_KEY_APPID, GetPrimaryKey(id, platform),
                VersionTable.RANGE_KEY_VERSION, version);

        try {
            Item outcome = table.getItem(spec);
            Map<String, String> info = outcome.getMap(VersionTable.ATTRIBUTE_JSON_INFO);

            VersionVo vo = new VersionVo()
                    .setDownload_url(info.get(VersionTable.ATTRIBUTE_DOWNLOAD_URL))
                    .setIos_bundle_id(info.get(VersionTable.ATTRIBUTE_IOS_BUNDLE_ID))
                    .setIos_title(info.get(VersionTable.ATTRIBUTE_IOS_TITLE));

            return vo;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public int CreateVersion(VersionVo vo) {
        final Map<String, Object> infoMap = new HashMap<>();
        infoMap.put(VersionTable.ATTRIBUTE_DOWNLOAD_URL, vo.getDownload_url());
        infoMap.put(VersionTable.ATTRIBUTE_JENKINS_URL, vo.getJenkins_url());
        infoMap.put(VersionTable.ATTRIBUTE_TIME, vo.getCreate_time());
        infoMap.put(VersionTable.ATTRIBUTE_DESC, vo.getDesc());

        if (vo.isIOS()) {
            infoMap.put(VersionTable.ATTRIBUTE_IOS_BUNDLE_ID, vo.getIos_bundle_id());
            infoMap.put(VersionTable.ATTRIBUTE_IOS_TITLE, vo.getIos_title());
        }

        try {
            Item item = new Item().withPrimaryKey(VersionTable.HASH_KEY_APPID,
                    GetPrimaryKey(vo.getAppId(), vo.getPlatform()), VersionTable.RANGE_KEY_VERSION, vo.getVersion())
                    .withString(VersionTable.ATTRIBUTE_PLATFORM, vo.getPlatform())
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
        QuerySpec querySpec = new QuerySpec();
        ItemCollection<QueryOutcome> items = null;
        Iterator<Item> iterator = null;

        List<String> jsonList = new ArrayList<>(PlatformConstant.values().length);

        for (PlatformConstant platform : PlatformConstant.values()) {
            querySpec.withKeyConditionExpression("#id = :v_id")
                    .withNameMap(new NameMap().with("#id", VersionTable.HASH_KEY_APPID))
                    .withValueMap(new ValueMap().withString(":v_id", GetPrimaryKey(appId, platform.getPlatform())))
                    .withScanIndexForward(false)
                    .setMaxResultSize(1);

            items = table.query(querySpec);
            iterator = items.iterator();

            while (iterator.hasNext()) {
                jsonList.add(iterator.next().toJSON());
            }
        }
        return new JsonArray(jsonList).toString();
    }

    @Override
    public String GetPlatformList(String appId, String platform) {
        QuerySpec querySpec = new QuerySpec();
        querySpec.withKeyConditionExpression("#id = :v_id")
                .withNameMap(new NameMap().with("#id", VersionTable.HASH_KEY_APPID))
                .withValueMap(new ValueMap().withString(":v_id", GetPrimaryKey(appId, platform)))
                .withScanIndexForward(false);

        ItemCollection<QueryOutcome> items = table.query(querySpec);
        Iterator<Item> iterator = items.iterator();
        List<String> jsonList = new ArrayList<>();

        while (iterator.hasNext()) {
            jsonList.add(iterator.next().toJSON());
        }
        return new JsonArray(jsonList).toString();
    }
}
