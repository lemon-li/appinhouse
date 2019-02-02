package com.seasungames.appinhouse.stores.services.app;

import com.seasungames.appinhouse.dagger.common.scope.AppInHouse;
import com.seasungames.appinhouse.stores.exception.DBException;
import com.seasungames.appinhouse.stores.AppStore;
import com.seasungames.appinhouse.stores.services.app.models.AppListResponseVo;
import com.seasungames.appinhouse.stores.services.app.models.AppResponseVo;
import com.seasungames.appinhouse.stores.services.app.models.AppVo;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;

import javax.inject.Inject;

/**
 * @author lile on 2019-01-15
 */
@AppInHouse
public class AppDBServiceImpl implements AppDBService {

    @Inject
    AppStore appStore;

    @Inject
    public AppDBServiceImpl() {

    }

    @Override
    public void getAppsList(String lastKey, Handler<AsyncResult<AppListResponseVo>> resultHandler) {
        try {
            AppListResponseVo result = appStore.getAppsList(lastKey);
            resultHandler.handle(Future.succeededFuture(result));
        } catch (Exception e) {
            resultHandler.handle(DBException.fail(e));
        }
    }

    @Override
    public void updateApps(AppVo vo, Handler<AsyncResult<AppResponseVo>> resultHandler) {
        try {
            AppResponseVo result = appStore.updateApps(vo);
            resultHandler.handle(Future.succeededFuture(result));
        } catch (Exception e) {
            resultHandler.handle(DBException.fail(e));
        }
    }

    @Override
    public void createApps(AppVo vo, Handler<AsyncResult<Void>> resultHandler) {
        try {
            appStore.createApps(vo);
            resultHandler.handle(Future.succeededFuture());
        } catch (Exception e) {
            resultHandler.handle(DBException.fail(e));
        }
    }

    @Override
    public void deleteApps(String appId, Handler<AsyncResult<Void>> resultHandler) {
        try {
            appStore.deleteApps(appId);
            resultHandler.handle(Future.succeededFuture());
        } catch (Exception e) {
            resultHandler.handle(DBException.fail(e));
        }
    }

    @Override
    public void getApps(String appId, Handler<AsyncResult<AppResponseVo>> resultHandler) {
        try {
            AppResponseVo result = appStore.getApps(appId);
            resultHandler.handle(Future.succeededFuture(result));
        } catch (Exception e) {
            resultHandler.handle(DBException.fail(e));
        }
    }

    @Override
    public void close(Handler<AsyncResult<Void>> resultHandler) {

    }
}
