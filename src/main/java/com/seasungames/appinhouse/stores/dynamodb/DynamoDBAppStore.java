package com.seasungames.appinhouse.stores.dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.seasungames.appinhouse.application.Configuration;
import com.seasungames.appinhouse.models.AppVo;
import com.seasungames.appinhouse.stores.AppStore;
import com.seasungames.appinhouse.stores.dynamodb.tables.AppTable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lile on 12/28/2018
 */
public class DynamoDBAppStore implements AppStore {
    private static final Logger LOG = LogManager.getLogger(DynamoDBAppStore.class);

    private final String tableName = "apps";

    private Configuration conf;

    private Table table;
    private AmazonDynamoDB ddb;

    public DynamoDBAppStore(AmazonDynamoDB ddb, Configuration conf) {
        this.ddb = ddb;
        this.conf = conf;

        table = new DynamoDB(ddb).getTable(tableName);

        if (conf.dynamodbcreateTableOnStartup()) {
            createTable();
        }
    }

    private void createTable() {
        LOG.info("Creating dynamodb table: " + tableName);

        CreateTableRequest req = new CreateTableRequest()
                .withTableName(tableName)
                .withKeySchema(new KeySchemaElement(AppTable.HASH_KEY_APPID, KeyType.HASH))
                .withAttributeDefinitions(new AttributeDefinition(AppTable.HASH_KEY_APPID, ScalarAttributeType.S))
                .withProvisionedThroughput(new ProvisionedThroughput()
                        .withReadCapacityUnits(conf.dynamodbTableReadThroughput())
                        .withWriteCapacityUnits(conf.dynamodbTableWriteThroughput()));

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
    public List<AppVo> getAppsList() {
        List<AppVo> appLists = new ArrayList<>();
        AppVo appVO = null;

        try {
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
        } catch (Exception e) {
            return null;
        }
        return appLists;
    }

    @Override
    public int createApps(AppVo vo) {
        try {
            table.putItem(new Item().withPrimaryKey(AppTable.HASH_KEY_APPID, vo.getAppId())
                    .withString(AppTable.ATTRIBUTE_DESC, vo.getDesc())
                    .withString(AppTable.ATTRIBUTE_ALIAS, vo.getAlias()));
            return 0;
        } catch (Exception e) {
            return 1;
        }
    }

    @Override
    public int deleteApps(String appId) {
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
    public int updateApps(AppVo vo) {
        UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                .withPrimaryKey(new PrimaryKey(AppTable.HASH_KEY_APPID, vo.getAppId()))
                .withUpdateExpression("set #desc = :d, #alias = :a")
                .withNameMap(new NameMap().with("#desc", AppTable.ATTRIBUTE_DESC).with("#alias", AppTable.ATTRIBUTE_ALIAS))
                .withValueMap(new ValueMap().withString(":d", vo.getDesc()).withString(":a", vo.getAlias()))
                .withReturnValues(ReturnValue.UPDATED_NEW);

        try {
            table.updateItem(updateItemSpec);
            return 0;
        } catch (Exception e) {
            return 1;
        }
    }

    @Override
    public String getApps(String appId) {
        GetItemSpec spec = new GetItemSpec().withPrimaryKey(AppTable.HASH_KEY_APPID, appId);

        try {
            Item outcome = table.getItem(spec);
            return outcome.toJSON();
        } catch (Exception e) {
            return "";
        }
    }
}
