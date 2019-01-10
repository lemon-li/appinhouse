package com.seasungames.appinhouse.routes.handlers;

import com.seasungames.appinhouse.application.APIConstant;
import com.seasungames.appinhouse.models.VersionVo;
import com.seasungames.appinhouse.models.response.ResponseVo;
import com.seasungames.appinhouse.models.response.VersionResponseVo;
import com.seasungames.appinhouse.routes.validations.impl.VersionValidationHandler;
import com.seasungames.appinhouse.services.VersionService;
import com.seasungames.appinhouse.utils.PathUtils;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

import java.util.List;

import static com.seasungames.appinhouse.utils.RestApiUtils.toResponseJson;
import static com.seasungames.appinhouse.utils.RestApiUtils.toResponseXML;

/**
 * Created by lile on 12/27/2018
 */
public class RouteVersionHandler {

    private final VersionService versionService;

    public RouteVersionHandler(Router router, VersionService versionService) {
        router.route(APIConstant.INDEX_VERSION).handler(this::index);

        router.get(APIConstant.API_GET_VERSIONS_LATEST)
                .handler(VersionValidationHandler.validateId())
                .handler(this::apiLatestVersion);
        router.get(APIConstant.API_GET_VERSIONS_HISTORY)
                .handler(VersionValidationHandler.validatePlatform())
                .handler(this::apiHistoryVersion);
        router.post(APIConstant.API_CREATE_VERSIONS)
                .handler(VersionValidationHandler.validateForm())
                .handler(this::apiCreateVersion);
        router.get(APIConstant.API_GET_VERSIONS_PLIST)
                .handler(VersionValidationHandler.validateVersion())
                .handler(this::getPlist);

        this.versionService = versionService;
    }

    private void index(RoutingContext rc) {
        rc.response().sendFile(PathUtils.getAssetsPath("/assets/html/version.html"));
    }

    private void apiLatestVersion(RoutingContext rc) {
        String appId = rc.request().getParam("id");

        ResponseVo<List<VersionResponseVo>> responseVo = new ResponseVo<>();
        toResponseJson(rc, 200, responseVo.setData(versionService.getLatestList(appId)).toJson());
    }

    private void apiHistoryVersion(RoutingContext rc) {
        String appId = rc.request().getParam("id");
        String platform = rc.request().getParam("platform");

        ResponseVo<List<VersionResponseVo>> responseVo = new ResponseVo<>();
        toResponseJson(rc, 200, responseVo.setData(versionService.getPlatformList(appId, platform)).toJson());
    }

    private void apiCreateVersion(RoutingContext rc) {
        String appId = rc.request().getParam("id");
        String platform = rc.request().getParam("platform");
        String version = rc.request().getParam("version");
        String desc = rc.request().getParam("desc");
        String downloadUrl = rc.request().getParam("software_url");
        String jenkinsUrl = rc.request().getParam("url");
        String createTime = rc.request().getParam("time");

        VersionVo vo = new VersionVo(appId, platform, version,
                desc, downloadUrl, jenkinsUrl, createTime);

        if (vo.isIOS()) {
            String iosBundleId = rc.request().getParam("ios_bundle_id");
            String iosTitle = rc.request().getParam("ios_title");
            vo.setIosBundleId(iosBundleId).setIosTitle(iosTitle);
        }

        versionService.createVersion(vo);
        toResponseJson(rc, 201);
    }

    private void getPlist(RoutingContext rc) {
        String appId = rc.request().getParam("id");
        String platform = rc.request().getParam("platform");
        String version = rc.request().getParam("version");
        toResponseXML(rc, 200, versionService.getPlist(appId, platform, version));
    }
}
