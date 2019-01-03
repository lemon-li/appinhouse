package com.seasungames.appinhouse.stores.dynamodb;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.seasungames.appinhouse.application.ConfigManager;
import com.seasungames.appinhouse.stores.IAppStore;
import com.seasungames.appinhouse.stores.IVersion;

import javax.inject.Inject;

/**
 * Created by lile on 12/28/2018
 */
public class DynamoDBManager {

    private AmazonDynamoDBClient client;

    private final ConfigManager conf;

    IAppStore appTable;
    IVersion versionTable;

    public DynamoDBManager(ConfigManager conf) {
        this.conf = conf;
    }

    public void startDB() {
        String region = conf.dynamoDBRegion();
        AmazonDynamoDBClientBuilder builder = AmazonDynamoDBClientBuilder.standard().
                withClientConfiguration(new ClientConfiguration()
                        .withGzip(true)
                        .withTcpKeepAlive(true));

        if ("local".equals(region)) {
            String endpoint = "http://" + conf.dynamoDBLocalhost() + ":" + conf.dynamoDBLocalPort();
            client = (AmazonDynamoDBClient) builder.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, "local"))
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
}
