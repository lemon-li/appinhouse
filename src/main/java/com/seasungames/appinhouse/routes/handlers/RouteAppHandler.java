package com.seasungames.appinhouse.routes.handlers;

import com.seasungames.appinhouse.application.APIConstant;
import com.seasungames.appinhouse.services.AppService;
import com.seasungames.appinhouse.utils.PathUtils;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

/**
 * Created by lile on 12/27/2018
 */
public class RouteAppHandler {

    private final AppService appService;

    public RouteAppHandler(Router router, AppService appService) {
        router.route(APIConstant.INDEX_APP).handler(this::index);

        router.get(APIConstant.API_APPS).handler(this::apiGetApps);
        router.get(APIConstant.API_APPS + "/:id").handler(this::apiGetApp);
        router.post(APIConstant.API_APPS).handler(this::apiCreateApps);
        router.put(APIConstant.API_APPS + "/:id").handler(this::apiUpdateApps);
        router.delete(APIConstant.API_APPS + "/:id").handler(this::apiDeleteApps);

        this.appService = appService;
    }

    private void index(RoutingContext rc) {
        rc.response().sendFile(PathUtils.getAssetsPath("/assets/html/app.html"));
    }

    /**
     * API
     */
    private void apiGetApps(RoutingContext rc) {
        rc.response().setStatusCode(200).end(appService.getAppsList());
    }

    private void apiGetApp(RoutingContext rc) {
        String appId = rc.request().getParam("id");
        appService.getApps(appId);
        rc.response().setStatusCode(200).end();
    }

    private void apiCreateApps(RoutingContext rc) {
        String appId = rc.request().getParam("id");
        String desc = rc.request().getParam("desc");
        String alias = rc.request().getParam("alias");

        appService.createApps(appId, desc, alias);
        rc.response().setStatusCode(201).end();
    }

    private void apiUpdateApps(RoutingContext rc) {
        String appId = rc.request().getParam("id");
        String desc = rc.request().getParam("desc");
        String alias = rc.request().getParam("alias");

        appService.updateApps(appId, desc, alias);
        rc.response().setStatusCode(200).end();
    }

    private void apiDeleteApps(RoutingContext rc) {
        String appId = rc.request().getParam("id");
        appService.deleteApps(appId);
        rc.response().setStatusCode(204).end();
    }
}
