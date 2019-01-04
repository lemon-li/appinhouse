package com.seasungames.appinhouse.routes;

import com.seasungames.appinhouse.services.impl.AppServiceImpl;
import com.seasungames.appinhouse.services.impl.VersionServiceImpl;
import com.seasungames.appinhouse.stores.dynamodb.DynamoDBManager;
import com.seasungames.appinhouse.utils.PathUtils;
import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;

import com.seasungames.appinhouse.routes.handlers.RouteAppHandler;
import com.seasungames.appinhouse.routes.handlers.RouteVersionHandler;

import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

/**
 * Created by lile on 12/27/2018
 */
public class RoutesManager {

    private static final Logger log = LoggerFactory.getLogger(RoutesManager.class);

    private final Router router;

    private static final String API_APPS = "/api/apps";
    private static final String API_VERSIONS = "/api/versions";

    DynamoDBManager dbManager;
    private AppServiceImpl appService;
    private VersionServiceImpl versionService;

    public RoutesManager(Vertx vertx, DynamoDBManager dbManager) {
        this.router = Router.router(vertx);
        this.dbManager = dbManager;

        appService = new AppServiceImpl(this.dbManager.appTable);
        versionService = new VersionServiceImpl(this.dbManager.versionTable);
    }

    public Router getRouter() {
        return this.router;
    }

    public RoutesManager setRoutes() {
        RouteAppHandler appHandler = new RouteAppHandler(appService);
        RouteVersionHandler versionHandler = new RouteVersionHandler(versionService);

        //webroot
        router.route("/").handler(this::index);
        router.route("/app/:app").handler(appHandler::index);
        router.route("/app/:id/platform/:pf/:version").handler(versionHandler::index);
        router.route().last().handler(this::error);
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
        router.get(API_APPS).handler(appHandler::apiGetApps);
        router.get(API_APPS + "/:id").handler(appHandler::apiGetApp);
        router.post(API_APPS).handler(appHandler::apiCreateApps);
        router.put(API_APPS).handler(appHandler::apiUpdateApps);
        router.delete(API_APPS).handler(appHandler::apiDeleteApps);

        router.get(API_VERSIONS + "/:id/latest").handler(versionHandler::apiLatestVersion);
        router.get(API_VERSIONS + "/:id/:platform/history").handler(versionHandler::apiHistoryVersion);
        router.post(API_VERSIONS + "/:id/:platform").handler(versionHandler::apiCreateVersion);
        router.get(API_VERSIONS + "/plist/:id/:platform/:version").handler(versionHandler::getPlist);

        return this;
    }

    private void index(RoutingContext rc) {
        rc.response().sendFile(PathUtils.getAssetsPath("/index.html"));
    }

    private void error(RoutingContext rc) {
        rc.response().setStatusCode(404).end();
    }
}
