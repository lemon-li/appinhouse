package com.seasungames.appinhouse;

import com.seasungames.appinhouse.application.Configuration;
import com.seasungames.appinhouse.dagger.DaggerMainComponent;
import com.seasungames.appinhouse.dagger.MainComponent;
import com.seasungames.appinhouse.dagger.VertxModule;
import com.seasungames.appinhouse.routes.RoutesManager;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by lile on 12/26/2018
 */
public class AppInHouseVerticle extends AbstractVerticle {

    private static final Logger log = LoggerFactory.getLogger(AppInHouseVerticle.class);

    @Inject
    Configuration conf;

    @Inject
    RoutesManager routesManager;

    @Inject
    @Named("HTTP")
    HttpServer webServer;

    @Override
    public void start() throws Exception {
        injectDependencies();
        startHttpsServer();
    }

    private void injectDependencies() {
        MainComponent component = DaggerMainComponent.builder()
                .vertxModule(new VertxModule(vertx))
                .build();
        component.inject(this);
        component.inject(routesManager);
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
    public void stop() throws Exception {
        webServer.close(ar -> {
            if (ar.succeeded()) {
                log.info("WebServer stopped listening at {}", conf.httpPort());
            } else {
                log.info("WebServer stopped failed listening at {} , Reason: {}", conf.httpPort(), ar.cause());
            }
        });
    }
}
