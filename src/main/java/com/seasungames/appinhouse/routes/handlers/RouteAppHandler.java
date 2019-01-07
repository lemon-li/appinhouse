package com.seasungames.appinhouse.routes.handlers;

import com.seasungames.appinhouse.services.AppService;
import com.seasungames.appinhouse.utils.PathUtils;
import io.vertx.ext.web.RoutingContext;

/**
 * Created by lile on 12/27/2018
 */
public class RouteAppHandler extends RouteHandler {

    private final AppService appService;

    public RouteAppHandler(AppService appService) {
        this.appService = appService;
    }

    public void index(RoutingContext rc) {
        rc.response()
                .sendFile(PathUtils.getAssetsPath("/assets/html/app.html"));
    }

    /**
     * API
     */
    public void apiGetApps(RoutingContext rc) {
        rc.response().setStatusCode(200).end(appService.getAppsList());
    }

    public void apiGetApp(RoutingContext rc) {
        String appId = rc.request().getParam("id");
        appService.getApps(appId);
        rc.response().setStatusCode(200).end();
    }

    public void apiCreateApps(RoutingContext rc) {
        String appId = rc.request().getParam("id");
        String desc = rc.request().getParam("desc");
        String alias = rc.request().getParam("alias");

        appService.createApps(appId, desc, alias);
        rc.response().setStatusCode(201).end();
    }

    public void apiUpdateApps(RoutingContext rc) {
        String appId = rc.request().getParam("id");
        String desc = rc.request().getParam("desc");
        String alias = rc.request().getParam("alias");

        appService.updateApps(appId, desc, alias);
        rc.response().setStatusCode(200).end();
    }

    public void apiDeleteApps(RoutingContext rc) {
        String appId = rc.request().getParam("id");
        appService.deleteApps(appId);
        rc.response().setStatusCode(204).end();
    }
}
