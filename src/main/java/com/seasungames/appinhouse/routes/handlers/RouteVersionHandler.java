package com.seasungames.appinhouse.routes.handlers;

import com.seasungames.appinhouse.constants.PlatformConstant;
import com.seasungames.appinhouse.models.VersionVo;
import com.seasungames.appinhouse.stores.dynamodb.DynamoDBManager;
import com.seasungames.appinhouse.utils.PathUtils;
import com.seasungames.appinhouse.utils.PlistUtils;
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
        String desc = rc.request().getParam("desc");
        String download_url = rc.request().getParam("software_url");
        String jenkins_url = rc.request().getParam("url");
        String create_time = rc.request().getParam("time");

        VersionVo vo = new VersionVo(appId, platform, version,
                desc, download_url, jenkins_url, create_time);

        if (vo.isIOS()) {
            String ios_bundle_id = rc.request().getParam("ios_bundle_id");
            String ios_title = rc.request().getParam("ios_title");
            vo.setIos_bundle_id(ios_bundle_id).setIos_title(ios_title);
        }

        int result = dbManager.versionTable.CreateVersion(vo);

        if (result == 0) {
            rc.response().setStatusCode(200).end();
        } else {
            rc.response().setStatusCode(404).end();
        }
    }

    public void GetPlist(RoutingContext rc) {
        String appId = rc.request().getParam("id");
        String platform = rc.request().getParam("platform");
        String version = rc.request().getParam("version");

        VersionVo vo = dbManager.versionTable.GetOneApp(appId, platform, version);

        if (vo != null) {
            String plist = PlistUtils.GenPlist(vo.getDownload_url(), vo.getIos_bundle_id(), vo.getIos_title());
            rc.response().putHeader("content-type", "application/x-plist; charset=utf-8").end(plist);
        } else {
            rc.response().setStatusCode(404).end();
        }
    }
}
