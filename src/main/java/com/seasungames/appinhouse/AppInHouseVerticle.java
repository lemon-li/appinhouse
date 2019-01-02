package com.seasungames.appinhouse;


import com.seasungames.appinhouse.application.ConfigManager;
import com.seasungames.appinhouse.routes.RoutesManager;
import com.seasungames.appinhouse.stores.dynamodb.DynamoDBManager;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * Created by lile on 12/26/2018
 */
public class AppInHouseVerticle extends AbstractVerticle {

    private static final Logger log = LoggerFactory.getLogger(AppInHouseVerticle.class);

    private DynamoDBManager dbManager;
    private RoutesManager routesManager;
    private HttpServer webServer;

    @Override
    public void start(Future<Void> startFuture) throws Exception {

        Future<Void> future = Future.future();

        future.compose(ar -> {
            webServer = vertx.createHttpServer();

            dbManager = CreateDynamoDBManager();

            routesManager = CreateRoutesManager();

            webServer.requestHandler(routesManager.GetRouter()).listen(ConfigManager.httpPort(), res -> {
                if (res.succeeded()) {
                    log.info("WebServer started listening at {}", ConfigManager.httpPort());
                    startFuture.complete();
                } else {
                    log.error("Could not start a HTTP server", res.cause());
                }
            });
        }, startFuture);

        ConfigManager.AsyncLoadConfig(vertx, future);
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
    public void stop(Future<Void> stopFuture) throws Exception {
        webServer.close(ar -> {
            if (ar.succeeded()) {
                stopFuture.complete();
                log.info("WebServer stopped listening at {}", ConfigManager.httpPort());
            } else {
                log.info("WebServer stopped failed listening at {} , Reason: {}", ConfigManager.httpPort(), ar.cause());
            }
        });
    }
}
