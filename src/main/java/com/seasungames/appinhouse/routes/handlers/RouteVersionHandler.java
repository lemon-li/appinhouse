package com.seasungames.appinhouse.router.handlers;

import com.seasungames.appinhouse.stores.dynamodb.DynamoDBManager;
import com.seasungames.appinhouse.utils.PathUtils;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

/**
 * Created by lile on 12/27/2018
 */
public class RouteVersionHandler {

    private final DynamoDBManager dbManager;

    public RouteVersionHandler(DynamoDBManager dbManager) {
        this.dbManager = dbManager;
    }

    public void IndexVersion(RoutingContext rc) {
        rc.response().sendFile(PathUtils.getAssetsPath("/version.html"));
    }

    public void ApiVersion(RoutingContext rc) {

    }

    /***
        IOS Rules , Gen .plist file
     */
    public void ApiIOSVersion(RoutingContext rc) {

        var json = new JsonObject();
        json.put("test","file");

        rc.response()
            .putHeader("content-type",
                "application/x-plist; charset=utf-8")
            .end(json.encodePrettily());

    }
}
