package com.seasungames.appinhouse;


import com.seasungames.appinhouse.routes.RoutesManager;
import com.seasungames.appinhouse.stores.dynamodb.DynamoDBManager;
import io.vertx.config.ConfigRetriever;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import javax.swing.*;
import java.awt.*;

/**
 * Created by lile on 12/26/2018
 */
public class AppInHouseVerticle extends AbstractVerticle {

    private static final Logger log = LoggerFactory.getLogger(AppInHouseVerticle.class);

    private DynamoDBManager dbManager;
    private RoutesManager routesManager;
    private HttpServer webServer;

    private final int PORT = 8080;

    @Override
    public void start() throws Exception {

        ConfigRetriever retriever = ConfigRetriever.create(vertx);

        JsonObject json = new JsonObject(
                vertx.fileSystem()
                    .readFileBlocking("conf.json"));

        webServer = vertx.createHttpServer();

        dbManager = CreateDynamoDBManager();

        routesManager = CreateRoutesManager();

        webServer.requestHandler(routesManager.GetRouter()).listen(PORT);

        log.info("WebServer started listening at {}", PORT);
    }

    private DynamoDBManager CreateDynamoDBManager() {
        return new DynamoDBManager();
    }

    private RoutesManager CreateRoutesManager() {
        return new RoutesManager(vertx)
            .SetDB(dbManager)
            .SetRoutes();
    }

    @Override
    public void stop() throws Exception {
        webServer.close(ar -> {
            if (ar.succeeded()) {
                log.info("WebServer stopped listening at {}", PORT);
            } else {
                log.info("WebServer stopped failed listening at {} , Reason: {}", PORT, ar.cause());
            }
        });
    }
}
