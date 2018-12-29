package com.seasungames.appinhouse.stores.dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.seasungames.appinhouse.stores.IVersion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    }
}
