package com.seasungames.appinhouse.routes.handlers;

import com.seasungames.appinhouse.models.AppVo;
import com.seasungames.appinhouse.stores.dynamodb.DynamoDBManager;
import com.seasungames.appinhouse.utils.PathUtils;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.util.List;

/**
 * Created by lile on 12/27/2018
 */
public class RouteAppHandler extends RouteHandler {

    private final DynamoDBManager dbManager;

    public RouteAppHandler(DynamoDBManager dbManager) {
        this.dbManager = dbManager;
    }

    public void indexApp(RoutingContext rc) {
        rc.response().sendFile(PathUtils.getAssetsPath("/assets/html/app.html"));
    }

    /**
     * API
     */
    public void apiGetApps(RoutingContext rc) {
        List<AppVo> appLists = dbManager.appTable.getAppsList();
        JsonArray jsonArray = new JsonArray(appLists);

        rc.response().end(jsonArray.toString());
    }

    public void apiGetApp(RoutingContext rc) {
        String appId = rc.request().getParam("id");

        String appJson = dbManager.appTable.getApps(appId);

        if (appJson.isEmpty()) {
            rc.response().setStatusCode(400).end();
        } else {
            rc.response().setStatusCode(200).end();
        }
    }

    public void apiCreateApps(RoutingContext rc) {
        String appId = rc.request().getParam("id");
        String desc = rc.request().getParam("desc");
        String alias = rc.request().getParam("alias");

        if (appId.isEmpty() || desc.isEmpty() || alias.isEmpty()) {
            rc.response().setStatusCode(400).end();
        }

        AppVo appVO = new AppVo(appId, desc, alias);

        int result = dbManager.appTable.createApps(appVO);

        if (IsSuccess(result)) {
            rc.response().setStatusCode(201).end();
        } else {
            JsonObject jsonObject = new JsonObject();
            jsonObject.put("code", result);
            rc.response().end(jsonObject.toString());
        }
    }

    public void apiUpdateApps(RoutingContext rc) {
        String appId = rc.request().getParam("id");
        String desc = rc.request().getParam("desc");
        String alias = rc.request().getParam("alias");

        AppVo appVO = new AppVo(appId, desc, alias);

        int result = dbManager.appTable.updateApps(appVO);

        if (IsSuccess(result)) {
            rc.response().setStatusCode(202).end();
        } else {
            JsonObject jsonObject = new JsonObject();
            jsonObject.put("code", result);
            rc.response().end(jsonObject.toString());
        }
    }

    public void apiDeleteApps(RoutingContext rc) {
        String appId = rc.request().getParam("id");
        int result = dbManager.appTable.deleteApps(appId);

        if (IsSuccess(result)) {
            rc.response().setStatusCode(204).end();
        } else {
            JsonObject jsonObject = new JsonObject();
            jsonObject.put("code", result);
            rc.response().end(jsonObject.toString());
        }
    }
}
