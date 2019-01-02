package com.seasungames.appinhouse.application;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class ConfigManager {
    private static final Logger log = LoggerFactory.getLogger(ConfigManager.class);

    private static JsonObject jsonConfig;

    public static void AsyncLoadConfig(Vertx vertx, Handler<AsyncResult<Void>> resultHandler) {
        jsonConfig = vertx.getOrCreateContext().config();

        if (jsonConfig.isEmpty()) {
            log.warn("use this configuration just launch your application with : java -jar target/Your.jar -conf src/main/resources/conf.json");
            vertx.fileSystem().readFile("conf.json", ar -> {
                if (ar.succeeded()) {
                    jsonConfig = new JsonObject(ar.result());
                    resultHandler.handle(Future.succeededFuture());

                } else {
                    resultHandler.handle(Future.failedFuture(ar.cause()));
                }
            });
        } else {
            resultHandler.handle(Future.succeededFuture());
        }
    }

    /***
     * Configuration API
     * */

    public static String httpHost() {
        return jsonConfig.getString("http.host", "0.0.0.0");
    }

    public static int httpPort() {
        return jsonConfig.getInteger("http.port", 8082);
    }

    public static String dynamoDBRegion() {
        return jsonConfig.getString("dynamodb.region", "local");
    }

    public static String dynamoDBLocalhost() {
        return jsonConfig.getString("dynamodb.local.host", "localhost");
    }

    public static int dynamoDBLocalPort() {
        return jsonConfig.getInteger("dynamodb.local.port",8000);
    }

    public static long dynamoDBTableReadThroughput() {
        return jsonConfig.getLong("dynamodb.tableReadThroughput",1L);
    }

    public static long dynamoDBTableWriteThroughput() {
        return jsonConfig.getLong("dynamodb.tableWriteThroughput",1L);
    }

    public static boolean createDynamoDBTableOnStartup() {
        return jsonConfig.getBoolean("dynamodb.createTableOnStartup",false);
    }
}
