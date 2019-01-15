package com.seasungames.appinhouse.dagger;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.seasungames.appinhouse.application.Configuration;
import com.seasungames.appinhouse.dagger.scope.AppInHouse;
import com.seasungames.appinhouse.stores.AppStore;
import com.seasungames.appinhouse.stores.VersionStore;
import com.seasungames.appinhouse.stores.dynamodb.DynamoDBAppStore;
import com.seasungames.appinhouse.stores.dynamodb.DynamoDBVersionStore;
import com.seasungames.appinhouse.stores.services.app.AppDBService;
import com.seasungames.appinhouse.stores.services.app.AppDBServiceImpl;
import com.seasungames.appinhouse.stores.services.version.VersionDBService;
import com.seasungames.appinhouse.stores.services.version.VersionDBServiceImpl;
import dagger.Module;
import dagger.Provides;

import javax.inject.Named;

@Module
public class DBModule {

    @Provides
    @AppInHouse
    AmazonDynamoDB provideAmazonDynamoDB(Configuration conf) {
        AmazonDynamoDBClient client;
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
        return client;
    }

    @Provides
    @Named("APP_DB_SERVICE")
    @AppInHouse
    AppDBService provideAppDBService(AppDBServiceImpl appDBService) {
        return appDBService;
    }

    @Provides
    @Named("VERSION_DB_SERVICE")
    @AppInHouse
    VersionDBService provideVersionDBService(VersionDBServiceImpl versionDBService) {
        return versionDBService;
    }

    @Provides
    @AppInHouse
    AppStore provideAppStore(DynamoDBAppStore aDynamoDBAppStore) {
        return aDynamoDBAppStore;
    }

    @Provides
    @AppInHouse
    VersionStore provideVersionStore(DynamoDBVersionStore dynamoDBVersionStore) {
        return dynamoDBVersionStore;
    }
}
