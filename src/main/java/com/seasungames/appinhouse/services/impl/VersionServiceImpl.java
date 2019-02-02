package com.seasungames.appinhouse.services.impl;

import com.seasungames.appinhouse.application.Errors;
import com.seasungames.appinhouse.dagger.common.scope.AppInHouse;
import com.seasungames.appinhouse.stores.services.app.AppDBService;
import com.seasungames.appinhouse.stores.services.app.models.AppResponseVo;
import com.seasungames.appinhouse.stores.services.version.VersionDBService;
import com.seasungames.appinhouse.stores.services.version.models.VersionListResponseVo;
import com.seasungames.appinhouse.stores.services.version.models.VersionVo;
import com.seasungames.appinhouse.stores.services.version.models.VersionResponseVo;
import com.seasungames.appinhouse.routes.exception.impl.NotFoundException;
import com.seasungames.appinhouse.services.VersionService;
import com.seasungames.appinhouse.utils.PlistUtils;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;

import javax.inject.Inject;
import java.util.List;

/**
 * @author lile on 2019-01-04
 */
@AppInHouse
public class VersionServiceImpl implements VersionService {

    @Inject
    AppDBService appDBServiceProxy;

    @Inject
    VersionDBService versionDBServiceProxy;

    @Inject
    public VersionServiceImpl() {

    }

    @Override
    public void getPlatformList(String appId, String platform, String lastKey,
                                Handler<AsyncResult<VersionListResponseVo>> resultHandler) {
        versionDBServiceProxy.getPlatformList(appId, platform, lastKey, resultHandler);
    }

    @Override
    public void getLatestList(String appId, Handler<AsyncResult<List<VersionResponseVo>>> resultHandler) {
        versionDBServiceProxy.getLatestList(appId, resultHandler);
    }

    @Override
    public void createVersion(VersionVo vo, Handler<AsyncResult<Void>> resultHandler) {
        Future<AppResponseVo> future = Future.future();
        future.compose(appResponseVo ->
            Future.<Void>future(f -> versionDBServiceProxy.createVersion(vo, f))
        ).setHandler(resultHandler);

        appDBServiceProxy.getApps(vo.getAppId(), future);
    }

    @Override
    public void getPlist(String appId, String platform, String version, Handler<AsyncResult<String>> resultHandler) {
        Future<VersionVo> future = Future.future();

        future.setHandler(ar -> {
            if (ar.succeeded()) {
                VersionVo vo = ar.result();
                if (vo != null) {
                    String result = PlistUtils.genPlist(vo.getDownloadUrl(), vo.getIOSBundleId(), vo.getIOSTitle());
                    resultHandler.handle(Future.succeededFuture(result));
                } else {
                    String error = String.format(Errors.NOT_FOUND_VERSION_MESSAGE, appId, platform, version);
                    resultHandler.handle(Future.failedFuture(new NotFoundException(error)));
                }
            } else {
                resultHandler.handle(Future.failedFuture(ar.cause()));
            }
        });

        versionDBServiceProxy.getVersion(appId, platform, version, future);
    }
}
