package com.seasungames.appinhouse.stores.dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.seasungames.appinhouse.application.Configuration;
import com.seasungames.appinhouse.dagger.scope.AppiInHouse;
import com.seasungames.appinhouse.models.AppVo;
import com.seasungames.appinhouse.models.response.AppListResponseVo;
import com.seasungames.appinhouse.models.response.AppResponseVo;
import com.seasungames.appinhouse.stores.AppStore;
import com.seasungames.appinhouse.stores.dynamodb.tables.AppTable;
import io.vertx.core.json.Json;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lile on 12/28/2018
 */
@AppiInHouse
public class DynamoDBAppStore implements AppStore {

    private static final Logger LOG = LoggerFactory.getLogger(DynamoDBAppStore.class);

    private static final String CONDITION_APP_NOT_EXIST = String.format("attribute_not_exists(%s)", AppTable.HASH_KEY_APPID);
    private static final String CONDITION_APP_EXIST = String.format("attribute_exists(%s)", AppTable.HASH_KEY_APPID);

    private String tableName;

    private Configuration conf;

    private Table table;
    private AmazonDynamoDB ddb;

    @Inject
    public DynamoDBAppStore(AmazonDynamoDB ddb, Configuration conf) {
        this.ddb = ddb;
        this.conf = conf;

        tableName = conf.appsTableName();
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
    public AppListResponseVo getAppsList(String lastKey) {
        List<AppResponseVo> appLists = new ArrayList<>();
        AppResponseVo appVO;

        ScanRequest scanRequest = new ScanRequest()
                .withTableName(tableName)
                .withLimit(conf.perPageSize());

        if (lastKey != null && !lastKey.isEmpty()) {
            Map<String, AttributeValue> startKey = new HashMap<>();
            startKey.put(AppTable.HASH_KEY_APPID, new AttributeValue().withS(lastKey));
            scanRequest.setExclusiveStartKey(startKey);
        }

        ScanResult result = ddb.scan(scanRequest);
        for (Map<String, AttributeValue> item : result.getItems()) {
            appVO = new AppResponseVo();
            appVO.setId(item.get(AppTable.HASH_KEY_APPID).getS());
            appVO.setDesc(item.get(AppTable.ATTRIBUTE_DESC).getS());
            appVO.setAlias(item.get(AppTable.ATTRIBUTE_ALIAS).getS());
            appLists.add(appVO);
        }

        Map<String, AttributeValue> lastEvaluatedKeys = result.getLastEvaluatedKey();
        String lastEvaluatedKey = "";
        if (null != lastEvaluatedKeys) {
            lastEvaluatedKey = result.getLastEvaluatedKey().get(AppTable.HASH_KEY_APPID).toString();
        }
        return new AppListResponseVo().setList(appLists).setLastKey(lastEvaluatedKey);
    }

    @Override
    public void createApps(AppVo vo) {
        Item item = new Item()
                .withPrimaryKey(AppTable.HASH_KEY_APPID, vo.getAppId())
                .withString(AppTable.ATTRIBUTE_DESC, vo.getDesc())
                .withString(AppTable.ATTRIBUTE_ALIAS, vo.getAlias());
        PutItemSpec putItemSpec = new PutItemSpec()
                .withConditionExpression(CONDITION_APP_NOT_EXIST)
                .withItem(item);
        table.putItem(putItemSpec);
    }

    @Override
    public void deleteApps(String appId) {
        DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
                .withConditionExpression(CONDITION_APP_EXIST)
                .withPrimaryKey(new PrimaryKey(AppTable.HASH_KEY_APPID, appId));

        table.deleteItem(deleteItemSpec);
    }

    @Override
    public AppResponseVo updateApps(AppVo vo) {
        UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                .withPrimaryKey(new PrimaryKey(AppTable.HASH_KEY_APPID, vo.getAppId()))
                .withConditionExpression(CONDITION_APP_EXIST)
                .withUpdateExpression("set #desc = :v_desc, #alias = :v_alias")
                .withNameMap(new NameMap().with("#desc", AppTable.ATTRIBUTE_DESC).with("#alias", AppTable.ATTRIBUTE_ALIAS))
                .withValueMap(new ValueMap().withString(":v_desc", vo.getDesc()).withString(":v_alias", vo.getAlias()))
                .withReturnValues(ReturnValue.ALL_NEW);

        UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
        return Json.decodeValue(outcome.getItem().toJSON(), AppResponseVo.class);
    }

    @Override
    public AppResponseVo getApps(String appId) {
        GetItemSpec spec = new GetItemSpec().withPrimaryKey(AppTable.HASH_KEY_APPID, appId);
        Item outcome = table.getItem(spec);
        if (outcome != null) {
            return Json.decodeValue(outcome.toJSON(), AppResponseVo.class);
        } else {
            return null;
        }
    }
}
