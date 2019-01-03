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

import javax.inject.Inject;

/**
 * Created by lile on 12/27/2018
 */
public class RoutesManager {

    private static final Logger log = LoggerFactory.getLogger(RoutesManager.class);

    private final Router router;

    @Inject
    public DynamoDBManager dbManager;

    public RoutesManager(Vertx vertx) {
        this.router = Router.router(vertx);
    }

    public Router GetRouter() {
        return this.router;
    }

    public RoutesManager SetRoutes() {
        RouteAppHandler appHandler = new RouteAppHandler(this.dbManager);
        RouteVersionHandler versionHandler = new RouteVersionHandler(this.dbManager);

        //webroot
        router.route("/").handler(this::Index);
        router.route().last().handler(this::Error);
        router.route("/app/:app").handler(appHandler::IndexApp);
        router.route("/app/:id/platform/:pf/:version").handler(versionHandler::IndexVersion);

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
        router.get("/api/apps").handler(appHandler::ApiGetApps);
        router.get("/api/apps/:id").handler(appHandler::ApiGetApp);
        router.post("/api/apps").handler(appHandler::ApiCreateApps);
        router.put("/api/apps").handler(appHandler::ApiUpdateApps);
        router.delete("/api/apps").handler(appHandler::ApiDeleteApps);

        router.get("/api/versions/:id/latest").handler(versionHandler::ApiLatestVersion);
        router.get("/api/versions/:id/:platform/history").handler(versionHandler::ApiHistoryVersion);
        router.post("/api/versions/:id/:platform").handler(versionHandler::ApiCreateVersion);
        router.get("/api/versions/plist/:id/:platform/:version").handler(versionHandler::GetPlist);

        return this;
    }

    private void Index(RoutingContext rc) {
        rc.response().sendFile(PathUtils.getAssetsPath("/index.html"));
    }

    private void Error(RoutingContext rc) {
        rc.response().setStatusCode(404).end();
    }
}
