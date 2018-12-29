package com.seasungames.appinhouse.routes.handlers;

import com.seasungames.appinhouse.models.AppVo;
import com.seasungames.appinhouse.stores.dynamodb.DynamoDBManager;
import com.seasungames.appinhouse.utils.PathUtils;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.util.List;

/**
 * Created by lile on 12/27/2018
 */
public class RouteAppHandler {

    private final DynamoDBManager dbManager;

    public RouteAppHandler(DynamoDBManager dbManager) {
        this.dbManager = dbManager;
    }

    public void IndexApp(RoutingContext rc) {
        rc.response().sendFile(PathUtils.getAssetsPath("/app.html"));
    }

    /**
     * API
     * */
    public void ApiGetApps(RoutingContext rc) {
        dbManager.appTable.GetAppsList();
        List<AppVo> appLists = dbManager.appTable.GetAppsList();
        JsonArray jsonArray = new JsonArray(appLists);

        rc.response().end(jsonArray.toString());
    }

    public void ApiGetApp(RoutingContext rc) {
        String appId = rc.request().getParam("id");
        String appJson = dbManager.appTable.GetApps(appId);

        rc.response().end(appJson);
    }

    public void ApiCreateApps(RoutingContext rc) {
        String appId = rc.request().getParam("id");
        String desc = rc.request().getParam("desc");
        String alias = rc.request().getParam("alias");

        if(appId.isEmpty()) {

        }

        if(desc.isEmpty()) {

        }

        if(alias.isEmpty()) {

        }

        AppVo appVO = new AppVo();
        appVO.setAppId(appId);
        appVO.setDesc(desc);
        appVO.setAlias(alias);

        int result = dbManager.appTable.CreateApps(appVO);
        JsonObject jsonObject = new JsonObject();
        jsonObject.put("code", result);

        rc.response().end(jsonObject.toString());
    }

    public void ApiUpdateApps(RoutingContext rc) {
        String appId = rc.request().getParam("id");
        String desc = rc.request().getParam("desc");
        String alias = rc.request().getParam("alias");


    }

    public void ApiDeleteApps(RoutingContext rc) {
        rc.response().end("Hello World");
    }
}