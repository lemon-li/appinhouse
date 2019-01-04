package com.seasungames.appinhouse;

import com.seasungames.appinhouse.application.Configuration;
import com.seasungames.appinhouse.dagger.DaggerMainComponent;
import com.seasungames.appinhouse.dagger.VertxModule;
import com.seasungames.appinhouse.routes.RoutesManager;
import com.seasungames.appinhouse.stores.dynamodb.DynamoDBManager;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import javax.inject.Inject;

/**
 * Created by lile on 12/26/2018
 */
public class AppInHouseVerticle extends AbstractVerticle {

    private static final Logger log = LoggerFactory.getLogger(AppInHouseVerticle.class);

    @Inject
    public Configuration conf;

    @Inject
    public DynamoDBManager dbManager;

    @Inject
    public RoutesManager routesManager;

    @Inject
    public HttpServer webServer;

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        injectDependencies();
        startHttpsServer();
    }

    private void injectDependencies() {
        DaggerMainComponent.builder()
                .vertxModule(new VertxModule(vertx))
                .build()
                .inject(this);
    }

    private void startHttpsServer() {
        webServer.requestHandler(routesManager.getRouter())
                .listen(conf.httpPort(), ar -> {
                    if (ar.succeeded()) {
                        log.info("WebServer started listening at {}", conf.httpPort());
                    } else {
                        log.info("WebServer started failed listening at {} , Reason: {}", conf.httpPort(), ar.cause());
                    }
                });
    }

    @Override
    public void stop(Future<Void> stopFuture) throws Exception {
        webServer.close(ar -> {
            if (ar.succeeded()) {
                stopFuture.complete();
                log.info("WebServer stopped listening at {}", conf.httpPort());
            } else {
                log.info("WebServer stopped failed listening at {} , Reason: {}", conf.httpPort(), ar.cause());
            }
        });
    }
}
