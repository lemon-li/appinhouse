package com.seasungames.appinhouse.routes.handlers;

import com.seasungames.appinhouse.models.VersionVo;
import com.seasungames.appinhouse.stores.dynamodb.DynamoDBManager;
import com.seasungames.appinhouse.utils.PathUtils;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.util.Date;

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

        String result = dbManager.versionTable.GetLatestList(appId);

        rc.response().setStatusCode(200).end(result);
    }

    public void ApiHistoryVersion(RoutingContext rc) {

        String appId = rc.request().getParam("id");
        String platform = rc.request().getParam("platform");

        String result = dbManager.versionTable.GetPlatformList(appId, platform);

        rc.response().setStatusCode(200).end(result);
    }

    /***
     Attention: IOS Rules , need a .plist file
     */
    public void ApiCreateVersion(RoutingContext rc) {

        String appId = rc.request().getParam("id");
        String platform = rc.request().getParam("platform");
        String version = rc.request().getParam("version");
        String download_url = rc.request().getParam("download_url");
        String jenkins_url = rc.request().getParam("jenkins_url");

        String plist = "plist";
        int create_time = (int) (new Date().getTime() / 1000);

        VersionVo vo = new VersionVo(appId, platform, version,
                download_url, jenkins_url, plist, create_time);


        int result = dbManager.versionTable.CreateVersion(vo);

        if (result == 0) {
            rc.response().setStatusCode(200).end();
        } else {
            rc.response().setStatusCode(404).end();
        }

        /*
        var json = new JsonObject();
        json.put("test", "file");

        rc.response()
                .putHeader("content-type",
                        "application/x-plist; charset=utf-8")
                .end(json.encodePrettily());

        */
    }
}
