package com.seasungames.appinhouse.stores.dynamodb;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.seasungames.appinhouse.application.Configuration;
import com.seasungames.appinhouse.stores.App;
import com.seasungames.appinhouse.stores.Version;

/**
 * Created by lile on 12/28/2018
 */
public class DynamoDBManager {

    private AmazonDynamoDBClient client;

    private final Configuration conf;

    public App appTable;
    public Version versionTable;

    public DynamoDBManager(Configuration conf) {
        this.conf = conf;
    }

    public void startDB() {
        String region = conf.dynamodbRegion();
        AmazonDynamoDBClientBuilder builder = AmazonDynamoDBClientBuilder.standard().
                withClientConfiguration(new ClientConfiguration()
                        .withGzip(true)
                        .withTcpKeepAlive(true));

        if ("local".equals(region)) {
            String endpoint = "http://" + conf.dynamodbLocalHost() + ":" + conf.dynamodbLocalPort();
            client = (AmazonDynamoDBClient) builder.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, "local"))
                    .build();
        } else {
            client = (AmazonDynamoDBClient) builder.withRegion(region).build();
        }

        InitTables();
    }

    private void InitTables() {
        appTable = new DynamoDBAppStore(this.client, conf);
        versionTable = new DynamoDBVersionStore(this.client, conf);
    }
}
