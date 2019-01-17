package com.seasungames.appinhouse.stores.services.version;

import com.seasungames.appinhouse.dagger.common.scope.AppInHouse;
import com.seasungames.appinhouse.routes.exception.DBException;
import com.seasungames.appinhouse.stores.VersionStore;
import com.seasungames.appinhouse.stores.services.version.models.VersionVo;
import com.seasungames.appinhouse.stores.services.version.models.VersionResponseVo;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by lile on 1/15/2019
 */
@AppInHouse
public class VersionDBServiceImpl implements VersionDBService {

    @Inject
    VersionStore versionStore;

    @Inject
    public VersionDBServiceImpl() {

    }

    @Override
    public void getPlatformList(String appId, String platform, Handler<AsyncResult<List<VersionResponseVo>>> resultHandler) {
        try {
            List<VersionResponseVo> versionResponseVos = versionStore.getPlatformList(appId, platform);
            resultHandler.handle(Future.succeededFuture(versionResponseVos));
        } catch (Exception e) {
            resultHandler.handle(DBException.fail(e));
        }
    }

    @Override
    public void getLatestList(String appId, Handler<AsyncResult<List<VersionResponseVo>>> resultHandler) {
        try {
            List<VersionResponseVo> versionResponseVos = versionStore.getLatestList(appId);
            resultHandler.handle(Future.succeededFuture(versionResponseVos));
        } catch (Exception e) {
            resultHandler.handle(DBException.fail(e));
        }
    }

    @Override
    public void createVersion(VersionVo vo, Handler<AsyncResult<Void>> resultHandler) {
        try {
            versionStore.createVersion(vo);
            resultHandler.handle(Future.succeededFuture());
        } catch (Exception e) {
            resultHandler.handle(DBException.fail(e));
        }
    }

    @Override
    public void getVersion(String appId, String platform, String version, Handler<AsyncResult<VersionVo>> resultHandler) {
        try {
            VersionVo vo = versionStore.getVersion(appId, platform, version);
            resultHandler.handle(Future.succeededFuture(vo));
        } catch (Exception e) {
            resultHandler.handle(DBException.fail(e));
        }
    }

    @Override
    public void close(Handler<AsyncResult<Void>> resultHandler) {

    }
}
