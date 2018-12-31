package com.seasungames.appinhouse.stores.dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.seasungames.appinhouse.models.VersionVo;
import com.seasungames.appinhouse.stores.IVersion;
import com.seasungames.appinhouse.stores.dynamodb.tables.AppTable;
import com.seasungames.appinhouse.stores.dynamodb.tables.VersionTable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lile on 12/28/2018
 */
public class DynamoDBVersionStore implements IVersion {

    private static final Logger LOG = LogManager.getLogger(DynamoDBAppStore.class);

    private final String tableName = "versions";

    private Table table;
    private AmazonDynamoDB ddb;

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
                .withAttributeType("N"));
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
                .withIndexName("PlatformIndex")
                .withProvisionedThroughput(new ProvisionedThroughput()
                        .withReadCapacityUnits((long) 10)
                        .withWriteCapacityUnits((long) 1))
                .withProjection(new Projection().withProjectionType(ProjectionType.KEYS_ONLY));

        ArrayList<KeySchemaElement> indexKeySchema = new ArrayList<>();

        indexKeySchema.add(new KeySchemaElement()
                .withAttributeName(VersionTable.SECONDARY_INDEX_VERSION)
                .withKeyType(KeyType.HASH));
        indexKeySchema.add(new KeySchemaElement()
                .withAttributeName(VersionTable.HASH_KEY_APPID)
                .withKeyType(KeyType.RANGE));

        platformIndex.setKeySchema(indexKeySchema);

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
    public void CreateVersion(VersionVo vo) {

    }

    @Override
    public List<VersionVo> GetLatestList(String appId) {
        return null;
    }

    @Override
    public List<VersionVo> GetPlatformList(String appId, String platform) {
        return null;
    }
}
