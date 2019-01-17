package com.seasungames.appinhouse;

import com.seasungames.appinhouse.application.Configuration;
import com.seasungames.appinhouse.dagger.common.module.VertxModule;
import com.seasungames.appinhouse.dagger.route.DaggerRouteComponent;
import com.seasungames.appinhouse.dagger.route.RouteComponent;
import com.seasungames.appinhouse.routes.RoutesManager;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by lile on 1/14/2019
 */
public class HttpServerVerticle extends AbstractVerticle {

    private static final Logger LOG = LoggerFactory.getLogger(HttpServerVerticle.class);

    @Inject
    Configuration conf;

    @Inject
    RoutesManager routesManager;

    @Inject
    @Named("HTTP")
    HttpServer webServer;

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        injectDependencies();

        webServer.requestHandler(routesManager.getRouter())
            .listen(conf.httpPort(), ar -> {
                if (ar.succeeded()) {
                    LOG.info("WebServer started listening at {}", conf.httpPort());
                    startFuture.complete();
                } else {
                    LOG.info("WebServer started failed listening at {} , Reason: {}", conf.httpPort(), ar.cause());
                    startFuture.fail(ar.cause());
                }
            });
    }

    private void injectDependencies() {
        RouteComponent component = DaggerRouteComponent.builder()
            .vertxModule(new VertxModule(vertx))
            .build();
        component.inject(this);
    }

    @Override
    public void stop(Future<Void> stopFuture) throws Exception {
        webServer.close(ar -> {
            if (ar.succeeded()) {
                LOG.info("WebServer stopped listening at {}", conf.httpPort());
                stopFuture.complete();
            } else {
                LOG.info("WebServer stopped failed listening at {} , Reason: {}", conf.httpPort(), ar.cause());
                stopFuture.fail(ar.cause());
            }
        });
    }
}
