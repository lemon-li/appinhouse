package com.seasungames.appinhouse.configs.impl;

import com.seasungames.appinhouse.configs.BaseConfig;

/**
 * Created by lile on 2019-01-26
 */
public interface DBConfig extends BaseConfig {

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
