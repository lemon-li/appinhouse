package com.seasungames.appinhouse.routes.handlers;

import com.seasungames.appinhouse.application.APIConstant;
import com.seasungames.appinhouse.dagger.scope.AppInHouse;
import com.seasungames.appinhouse.models.AppVo;
import com.seasungames.appinhouse.models.response.AppListResponseVo;
import com.seasungames.appinhouse.models.response.AppResponseVo;
import com.seasungames.appinhouse.models.response.ResponseVo;
import com.seasungames.appinhouse.routes.validations.impl.AppValidationHandler;
import com.seasungames.appinhouse.services.AppService;
import com.seasungames.appinhouse.utils.PathUtils;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.apache.http.HttpStatus;

import javax.inject.Inject;

import static com.seasungames.appinhouse.utils.RestApiUtils.toResponseJson;

/**
 * Created by lile on 12/27/2018
 */
@AppInHouse
public class RouteAppHandler {

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
        rc.response().sendFile(PathUtils.getAssetsPath("/assets/html/app.html"));
    }

    /**
     * API
     */
    private void apiGetApps(RoutingContext rc) {
        String lastKey = rc.request().getParam("last_key");

        AppListResponseVo appListResponseVo = appService.getAppsList(lastKey);
        ResponseVo<AppListResponseVo> responseVo = new ResponseVo<>();
        toResponseJson(rc, HttpStatus.SC_OK, responseVo.setData(appListResponseVo).toJson());
    }

    private void apiGetApp(RoutingContext rc) {
        String appId = rc.request().getParam("id");

        AppResponseVo appResponseVo = appService.getApps(appId);
        ResponseVo<AppResponseVo> responseVo = new ResponseVo<>();
        toResponseJson(rc, HttpStatus.SC_OK, responseVo.setData(appResponseVo).toJson());
    }

    private void apiCreateApps(RoutingContext rc) {
        AppVo appVo = Json.decodeValue(rc.getBodyAsJson().toString(), AppVo.class);

        appService.createApps(appVo);
        toResponseJson(rc, HttpStatus.SC_CREATED);
    }

    private void apiUpdateApps(RoutingContext rc) {
        AppVo appVo = Json.decodeValue(rc.getBodyAsJson().toString(), AppVo.class);

        AppResponseVo appResponseVo = appService.updateApps(appVo);
        ResponseVo<AppResponseVo> responseVo = new ResponseVo<>();
        toResponseJson(rc, HttpStatus.SC_OK, responseVo.setData(appResponseVo).toJson());
    }

    private void apiDeleteApps(RoutingContext rc) {
        String appId = rc.request().getParam("id");

        appService.deleteApps(appId);
        toResponseJson(rc, HttpStatus.SC_NO_CONTENT);
    }
}
