package com.seasungames.appinhouse.stores.dynamodb;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.seasungames.appinhouse.stores.IAppStore;
import com.seasungames.appinhouse.stores.IVersion;

import java.util.concurrent.Future;

/**
 * Created by lile on 12/28/2018
 */
public class DynamoDBManager {

    private AmazonDynamoDBClient client;

    public IAppStore appTable;
    public IVersion versionTable;


    public DynamoDBManager() {
        client = (AmazonDynamoDBClient) AmazonDynamoDBClientBuilder.standard()
            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "local"))
            .withClientConfiguration(getClientConfiguration())
            .build();

        InitTables();
    }

    private void InitTables() {
        appTable = new DynamoDBAppStore(this.client);
        versionTable = new DynamoDBVersionStore(this.client);
    }

    private static ClientConfiguration getClientConfiguration() {
        ClientConfiguration config = new ClientConfiguration()
            .withGzip(true)
            .withTcpKeepAlive(true);

        return config;
    }
}