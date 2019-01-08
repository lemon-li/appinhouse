package com.seasungames.appinhouse.routes.handlers;

import com.seasungames.appinhouse.application.APIConstant;
import com.seasungames.appinhouse.routes.validations.impl.AppValidationHandler;
import com.seasungames.appinhouse.services.AppService;
import com.seasungames.appinhouse.utils.PathUtils;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

import static com.seasungames.appinhouse.utils.RestApiUtils.toResponseJson;

/**
 * Created by lile on 12/27/2018
 */
public class RouteAppHandler {

    private final AppService appService;

    public RouteAppHandler(Router router, AppService appService) {
        router.route(APIConstant.INDEX_APP).handler(this::index);

        router.get(APIConstant.API_APPS)
                .handler(this::apiGetApps);
        router.get(APIConstant.API_APPS + "/:id")
                .handler(AppValidationHandler.validateId())
                .handler(this::apiGetApp);
        router.post(APIConstant.API_APPS)
                .handler(AppValidationHandler.validateAppForm())
                .handler(this::apiCreateApps);
        router.put(APIConstant.API_APPS + "/:id")
                .handler(AppValidationHandler.validateId())
                .handler(this::apiUpdateApps);
        router.delete(APIConstant.API_APPS + "/:id")
                .handler(AppValidationHandler.validateId())
                .handler(this::apiDeleteApps);

        this.appService = appService;
    }

    private void index(RoutingContext rc) {
        rc.response().sendFile(PathUtils.getAssetsPath("/assets/html/app.html"));
    }

    /**
     * API
     */
    private void apiGetApps(RoutingContext rc) {
        String lastKey = rc.request().getParam("lastKey");
        toResponseJson(rc, 200, Json.encodePrettily(appService.getAppsList(lastKey)));
    }

    private void apiGetApp(RoutingContext rc) {
        String appId = rc.request().getParam("id");
        toResponseJson(rc, 200, appService.getApps(appId));
    }

    private void apiCreateApps(RoutingContext rc) {
        String appId = rc.request().getParam("id");
        String desc = rc.request().getParam("desc");
        String alias = rc.request().getParam("alias");

        appService.createApps(appId, desc, alias);
        toResponseJson(rc, 201);
    }

    private void apiUpdateApps(RoutingContext rc) {
        String appId = rc.request().getParam("id");
        String desc = rc.request().getParam("desc");
        String alias = rc.request().getParam("alias");

        appService.updateApps(appId, desc, alias);
        toResponseJson(rc, 200);
    }

    private void apiDeleteApps(RoutingContext rc) {
        String appId = rc.request().getParam("id");
        appService.deleteApps(appId);
        toResponseJson(rc, 204);
    }
}
