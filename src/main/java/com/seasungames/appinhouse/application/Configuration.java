package com.seasungames.appinhouse.application;

import org.aeonbits.owner.Config;

/**
 * Created by lile on 12/27/2018
 */
@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({"file:~/.appinhouse.properties",
    "file:appinhouse.properties",
    "classpath:appinhouse.properties"})
public interface Configuration extends Config {

    @Key("http.host")
    @DefaultValue("0.0.0.0")
    String httpHost();

    @Key("http.port")
    @DefaultValue("8443")
    int httpPort();

    @Key("dynamodb.region")
    @DefaultValue("local")
    String dynamodbRegion();

    @Key("dynamodb.local.host")
    @DefaultValue("localhost")
    String dynamodbLocalHost();

    @Key("dynamodb.local.port")
    @DefaultValue("8000")
    int dynamodbLocalPort();

    @Key("dynamodb.tableReadThroughput")
    @DefaultValue("1")
    Long dynamodbTableReadThroughput();

    @Key("dynamodb.tableWriteThroughput")
    @DefaultValue("1")
    Long dynamodbTableWriteThroughput();

    @Key("dynamodb.createTableOnStartup")
    @DefaultValue("true")
    boolean dynamodbcreateTableOnStartup();

    @Key("dynamodb.tableName.apps")
    @DefaultValue("")
    String appsTableName();

    @Key("dynamodb.tableName.versions")
    @DefaultValue("")
    String versionsTableName();

    @Key("webclient.page.size")
    @DefaultValue("10")
    int perPageSize();
}
