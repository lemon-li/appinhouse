package com.seasungames.appinhouse.routes;

import com.seasungames.appinhouse.stores.dynamodb.DynamoDBManager;
import com.seasungames.appinhouse.utils.PathUtils;
import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;

import com.seasungames.appinhouse.routes.handlers.RouteAppHandler;
import com.seasungames.appinhouse.routes.handlers.RouteVersionHandler;

import io.vertx.ext.web.RoutingContext;

/**
 * Created by lile on 12/27/2018
 */
public class RoutesManager {

    private static final Logger log = LoggerFactory.getLogger(RoutesManager.class);

    private final Router router;

    DynamoDBManager dbManager;

    public RoutesManager(Vertx vertx, DynamoDBManager dbManager) {
        this.router = Router.router(vertx);
        this.dbManager = dbManager;
    }

    public Router getRouter() {
        return this.router;
    }

    public RoutesManager setRoutes() {
        RouteAppHandler appHandler = new RouteAppHandler(this.dbManager);
        RouteVersionHandler versionHandler = new RouteVersionHandler(this.dbManager);

        //webroot
        router.route("/").handler(this::index);
        router.route().last().handler(this::error);
        router.route("/app/:app").handler(appHandler::indexApp);
        router.route("/app/:id/platform/:pf/:version").handler(versionHandler::indexVersion);

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
        router.get("/api/apps").handler(appHandler::apiGetApps);
        router.get("/api/apps/:id").handler(appHandler::apiGetApp);
        router.post("/api/apps").handler(appHandler::apiCreateApps);
        router.put("/api/apps").handler(appHandler::apiUpdateApps);
        router.delete("/api/apps").handler(appHandler::apiDeleteApps);

        router.get("/api/versions/:id/latest").handler(versionHandler::apiLatestVersion);
        router.get("/api/versions/:id/:platform/history").handler(versionHandler::apiHistoryVersion);
        router.post("/api/versions/:id/:platform").handler(versionHandler::apiCreateVersion);
        router.get("/api/versions/plist/:id/:platform/:version").handler(versionHandler::getPlist);

        return this;
    }

    private void index(RoutingContext rc) {
        rc.response().sendFile(PathUtils.getAssetsPath("/index.html"));
    }

    private void error(RoutingContext rc) {
        rc.response().setStatusCode(404).end();
    }
}
