package com.seasungames.appinhouse.routes.handlers.impl;

import com.seasungames.appinhouse.application.APIConstant;
import com.seasungames.appinhouse.dagger.common.scope.AppInHouse;
import com.seasungames.appinhouse.routes.handlers.BaseHandler;
import com.seasungames.appinhouse.stores.services.app.models.AppVo;
import com.seasungames.appinhouse.routes.validations.impl.AppValidationHandler;
import com.seasungames.appinhouse.services.AppService;
import com.seasungames.appinhouse.utils.PathUtils;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

import javax.inject.Inject;

/**
 * @author lile on 2018-12-27
 */
@AppInHouse
public class RouteAppHandler extends BaseHandler {

    @Inject
    AppService appService;

    @Inject
    public RouteAppHandler(Router router) {
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
    }

    private void index(RoutingContext rc) {
        rc.response().sendFile(PathUtils.getAssetsPath("/html/app.html"));
    }

    /**
     * API
     */
    private void apiGetApps(RoutingContext rc) {
        String lastKey = rc.request().getParam("last_key");

        appService.getAppsList(lastKey, resultVoidHandler(rc));
    }

    private void apiGetApp(RoutingContext rc) {
        String appId = rc.request().getParam("id");

        appService.getApps(appId, resultVoidHandler(rc));
    }

    private void apiCreateApps(RoutingContext rc) {
        AppVo appVo = Json.decodeValue(rc.getBodyAsJson().toString(), AppVo.class);

        appService.createApps(appVo, resultVoidHandler(rc, HttpResponseStatus.CREATED.code()));
    }

    private void apiUpdateApps(RoutingContext rc) {
        AppVo appVo = Json.decodeValue(rc.getBodyAsJson().toString(), AppVo.class);

        appService.updateApps(appVo, resultVoidHandler(rc));
    }

    private void apiDeleteApps(RoutingContext rc) {
        String appId = rc.request().getParam("id");

        appService.deleteApps(appId, resultVoidHandler(rc, HttpResponseStatus.NO_CONTENT.code()));
    }
}
