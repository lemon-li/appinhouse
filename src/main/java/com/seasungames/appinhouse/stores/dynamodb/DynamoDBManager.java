package com.seasungames.appinhouse.stores.dynamodb;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.RegionUtils;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.seasungames.appinhouse.application.ConfigManager;
import com.seasungames.appinhouse.stores.IAppStore;
import com.seasungames.appinhouse.stores.IVersion;

/**
 * Created by lile on 12/28/2018
 */
public class DynamoDBManager {

    private AmazonDynamoDBClient client;

    public IAppStore appTable;
    public IVersion versionTable;

    public DynamoDBManager() {
        String region = ConfigManager.dynamoDBRegion();
        AmazonDynamoDBClientBuilder builder = AmazonDynamoDBClientBuilder.standard().
                withClientConfiguration(getClientConfiguration());

        if ("local".equals(region)) {
            String endpoint = "http://" + ConfigManager.dynamoDBLocalhost() + ":" + ConfigManager.dynamoDBLocalPort();
            client = (AmazonDynamoDBClient) builder.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, "local"))
                    .withClientConfiguration(getClientConfiguration())
                    .build();
        } else {
            client = (AmazonDynamoDBClient) builder.withRegion(region).build();
        }

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
