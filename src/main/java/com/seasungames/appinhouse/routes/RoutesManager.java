package com.seasungames.appinhouse.routes;

import com.seasungames.appinhouse.HttpServerVerticle;
import com.seasungames.appinhouse.application.APIConstant;
import com.seasungames.appinhouse.application.Async;
import com.seasungames.appinhouse.application.Configuration;
import com.seasungames.appinhouse.dagger.common.scope.AppInHouse;
import com.seasungames.appinhouse.routes.exception.impl.BadRequestException;
import com.seasungames.appinhouse.routes.handlers.RouteFailureHandler;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;

import com.seasungames.appinhouse.routes.handlers.RouteAppHandler;
import com.seasungames.appinhouse.routes.handlers.RouteVersionHandler;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.LoggerHandler;
import io.vertx.ext.web.handler.StaticHandler;

import javax.inject.Inject;
import javax.inject.Named;

/***
 RESTful API

 GET:     Used for retrieving resources.
 POST:    Used for creating resources.
 PATCH:   Used for updating resources with partial JSON data. For instance,
 an Issue resource has title and body attributes. A PATCH request may accept one or more of the
 attributes to update the resource. PATCH is a relatively new and uncommon HTTP verb, so resource endpoints also accept POST requests.

 PUT:     Used for replacing resources or collections. For PUT requests with no body attribute, be sure to set the Content-Length header to zero.
 DELETE:  Used for deleting resources.
 ***/
@AppInHouse
public class RoutesManager implements Async {

    private static final Logger LOG = LoggerFactory.getLogger(RoutesManager.class);

    private final Router router;

    @Inject
    RouteAppHandler appHandler;

    @Inject
    RouteVersionHandler versionHandler;

    @Inject
    @Named("HTTP")
    HttpServer webServer;

    @Inject
    Configuration conf;

    @Inject
    public RoutesManager(Router router) {
        this.router = router;

        router.route().handler(BodyHandler.create());
        router.route().handler(LoggerHandler.create());
        router.route().handler(StaticHandler.create()
            .setWebRoot(APIConstant.WEBROOT)
            .setIndexPage("index.html")
            .setDefaultContentEncoding("UTF-8"));
        router.route().failureHandler(new RouteFailureHandler());
        router.route().last().handler(rc -> rc.fail(new BadRequestException()));
    }

    @Override
    public void start(Future<Void> startFuture) {
        webServer.requestHandler(router)
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

    @Override
    public void stop(Future<Void> stopFuture) {
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
