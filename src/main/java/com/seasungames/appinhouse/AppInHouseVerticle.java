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

    private final int PORT = 8082;

    @Override
    public void start(Future<Void> startFuture) throws Exception {

        Future<Void> future = Future.future();

        future.setHandler(ar ->{
            webServer = vertx.createHttpServer();

            dbManager = CreateDynamoDBManager();

            routesManager = CreateRoutesManager();

            webServer.requestHandler(routesManager.GetRouter()).listen(PORT);

            log.info("WebServer started listening at {}", PORT);

            startFuture.complete();
        });

        ConfigManager.AsyncLoadConfig(vertx,future);
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
                log.info("WebServer stopped listening at {}", PORT);
            } else {
                log.info("WebServer stopped failed listening at {} , Reason: {}", PORT, ar.cause());
            }
        });
    }
}
