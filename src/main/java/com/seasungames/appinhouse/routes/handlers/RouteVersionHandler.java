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

    public void ApiLatestVersion(RoutingContext rc) {
        String appId = rc.request().getParam("id");

        dbManager.versionTable.GetLatestList(appId);

    }

    public void ApiHistoryVersion(RoutingContext rc) {
        String appId = rc.request().getParam("id");
        String platform = rc.request().getParam("platform");

        dbManager.versionTable.GetLatestList(appId);
    }

    /***
     Attention: IOS Rules , need a .plist file
     */
    public void ApiCreateVersion(RoutingContext rc) {

        String appId = rc.request().getParam("id");
        String platform = rc.request().getParam("platform");
        String download_url = rc.request().getParam("");
        String jenkins_url = rc.request().getParam("platform");


        var json = new JsonObject();
        json.put("test", "file");

        rc.response()
                .putHeader("content-type",
                        "application/x-plist; charset=utf-8")
                .end(json.encodePrettily());

    }
}
