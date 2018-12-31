package com.seasungames.appinhouse.stores.dynamodb;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.seasungames.appinhouse.models.AppVo;
import com.seasungames.appinhouse.stores.IAppStore;
import com.seasungames.appinhouse.stores.dynamodb.tables.AppTable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lile on 12/28/2018
 */
public class DynamoDBAppStore implements IAppStore {
    private static final Logger LOG = LogManager.getLogger(DynamoDBAppStore.class);

    private final String tableName = "apps";

    private Table table;
    private AmazonDynamoDB ddb;

    public DynamoDBAppStore(AmazonDynamoDB ddb) {
        this.ddb = ddb;
        table = new DynamoDB(ddb).getTable(tableName);

        CreateTable();
    }

    private void CreateTable() {

        LOG.info("Creating dynamodb table: " + tableName);

        ProvisionedThroughput throughput = new ProvisionedThroughput(1L, 1L);

        CreateTableRequest req = new CreateTableRequest()
                .withTableName(tableName)
                .withKeySchema(new KeySchemaElement(AppTable.HASH_KEY_APPID, KeyType.HASH))
                .withAttributeDefinitions(new AttributeDefinition(AppTable.HASH_KEY_APPID, ScalarAttributeType.S))
                .withProvisionedThroughput(throughput);

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
    public List<AppVo> GetAppsList() {
        List<AppVo> appLists = new ArrayList<>(0);
        AppVo appVO = null;

        ScanRequest scanRequest = new ScanRequest()
                .withTableName(tableName);

        ScanResult result = ddb.scan(scanRequest);
        for (Map<String, AttributeValue> item : result.getItems()) {
            appVO = new AppVo();
            appVO.setAppId(item.get(AppTable.HASH_KEY_APPID).getS());
            appVO.setDesc(item.get(AppTable.ATTRIBUTE_DESC).getS());
            appVO.setAlias(item.get(AppTable.ATTRIBUTE_ALIAS).getS());
            appLists.add(appVO);
        }

        return appLists;
    }

    @Override
    public int CreateApps(AppVo vo) {
        try {
            PutItemOutcome outcome = table.putItem(new Item().withPrimaryKey(AppTable.HASH_KEY_APPID, vo.getAppId())
                    .with(AppTable.ATTRIBUTE_DESC, vo.getDesc())
                    .with(AppTable.ATTRIBUTE_ALIAS, vo.getAlias()));
            return 0;
        } catch (Exception e) {
            return 1;
        }
    }

    @Override
    public int DeleteApps(String appId) {
        DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
                .withPrimaryKey(new PrimaryKey(AppTable.HASH_KEY_APPID, appId));

        try {
            table.deleteItem(deleteItemSpec);
            return 0;
        } catch (Exception e) {
            return 1;
        }
    }

    @Override
    public int UpdateApps(AppVo vo) {
        UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                .withPrimaryKey(new PrimaryKey(AppTable.HASH_KEY_APPID, vo.getAppId()))
                .withUpdateExpression("set #desc = :d, #alias = :a")
                .withNameMap(new NameMap().with("#desc", AppTable.ATTRIBUTE_DESC).with("#alias", AppTable.ATTRIBUTE_ALIAS))
                .withValueMap(new ValueMap().withString(":d", vo.getDesc()).withString(":a", vo.getAlias()))
                .withReturnValues(ReturnValue.UPDATED_NEW);

        try {
            UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
            return 0;
        } catch (Exception e) {
            return 1;
        }
    }

    @Override
    public String GetApps(String appId) {
        GetItemSpec spec = new GetItemSpec().withPrimaryKey(AppTable.HASH_KEY_APPID, appId);
        try {
            Item outcome = table.getItem(spec);
            return outcome.toJSON();
        } catch (Exception e) {
            return "";
        }
    }
}
