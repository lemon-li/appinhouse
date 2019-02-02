package com.seasungames.appinhouse.routes;

import com.seasungames.appinhouse.application.APIConstant;
import com.seasungames.appinhouse.application.Async;
import com.seasungames.appinhouse.configs.impl.RouteConfig;
import com.seasungames.appinhouse.dagger.common.scope.AppInHouse;
import com.seasungames.appinhouse.routes.exception.impl.BadRequestException;
import com.seasungames.appinhouse.routes.handlers.impl.RouteFailureHandler;
import com.seasungames.appinhouse.routes.handlers.impl.RouteFilterHandler;
import io.vertx.core.Future;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;

import com.seasungames.appinhouse.routes.handlers.impl.RouteAppHandler;
import com.seasungames.appinhouse.routes.handlers.impl.RouteVersionHandler;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.LoggerHandler;
import io.vertx.ext.web.handler.StaticHandler;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

/**
 * @author lile on 2018-12-27
 */
@AppInHouse
public class RoutesManager implements Async {

    private static final Logger LOG = LoggerFactory.getLogger(RoutesManager.class);

    private final Router router;

    private final RouteConfig conf;

    @Inject
    RouteAppHandler appHandler;

    @Inject
    RouteVersionHandler versionHandler;

    @Inject
    HttpServer webServer;

    @Inject
    public RoutesManager(Router router, RouteConfig conf) {
        this.router = router;
        this.conf = conf;

        router.route()
            .method(HttpMethod.POST)
            .method(HttpMethod.PUT)
            .handler(RouteFilterHandler.create(conf.httpSecureKey()));
        router.route().handler(BodyHandler.create());
        router.route().handler(LoggerHandler.create());
        router.route().handler(StaticHandler.create()
            .setWebRoot(APIConstant.WEBROOT)
            .setIndexPage("index.html")
            .setDefaultContentEncoding("UTF-8"));
        router.route().handler(CorsHandler.create("*")
            .allowedHeaders(getCORSHeaders())
            .allowedMethods(getCORSMethods()));
        router.route().failureHandler(new RouteFailureHandler());
        router.route().last().handler(rc -> rc.fail(new BadRequestException()));
    }

    private Set<String> getCORSHeaders() {
        Set<String> allowedHeaders = new HashSet<>();
        allowedHeaders.add("x-requested-with");
        allowedHeaders.add("Access-Control-Allow-Origin");
        allowedHeaders.add("origin");
        allowedHeaders.add("Content-Type");
        allowedHeaders.add("accept");
        allowedHeaders.add("X-PINGARUNER");
        return allowedHeaders;
    }

    private Set<HttpMethod> getCORSMethods() {
        Set<HttpMethod> allowedMethods = new HashSet<>();
        allowedMethods.add(HttpMethod.GET);
        allowedMethods.add(HttpMethod.POST);
        allowedMethods.add(HttpMethod.OPTIONS);
        allowedMethods.add(HttpMethod.DELETE);
        allowedMethods.add(HttpMethod.PATCH);
        allowedMethods.add(HttpMethod.PUT);
        return allowedMethods;
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
