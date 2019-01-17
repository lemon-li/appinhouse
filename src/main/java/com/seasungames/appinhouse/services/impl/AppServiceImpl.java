package com.seasungames.appinhouse.services.impl;

import com.seasungames.appinhouse.dagger.common.scope.AppInHouse;
import com.seasungames.appinhouse.stores.services.app.AppDBService;
import com.seasungames.appinhouse.stores.services.app.models.AppVo;
import com.seasungames.appinhouse.stores.services.app.models.AppListResponseVo;
import com.seasungames.appinhouse.stores.services.app.models.AppResponseVo;
import com.seasungames.appinhouse.routes.exception.impl.NotFoundException;
import com.seasungames.appinhouse.services.AppService;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;

import javax.inject.Inject;

/**
 * Created by lile on 1/4/2019
 */
@AppInHouse
public class AppServiceImpl implements AppService {

    @Inject
    AppDBService appDBServiceProxy;

    @Inject
    public AppServiceImpl() {

    }

    @Override
    public void getAppsList(String lastKey, Handler<AsyncResult<AppListResponseVo>> resultHandler) {
        appDBServiceProxy.getAppsList(lastKey, resultHandler);
    }

    @Override
    public void updateApps(AppVo appVo, Handler<AsyncResult<AppResponseVo>> resultHandler) {
        appDBServiceProxy.updateApps(appVo, resultHandler);
    }

    @Override
    public void createApps(AppVo appVo, Handler<AsyncResult<Void>> resultHandler) {
        appDBServiceProxy.createApps(appVo, resultHandler);
    }

    @Override
    public void deleteApps(String id, Handler<AsyncResult<Void>> resultHandler) {
        appDBServiceProxy.deleteApps(id, resultHandler);
    }

    @Override
    public void getApps(String id, Handler<AsyncResult<AppResponseVo>> resultHandler) {
        Future<AppResponseVo> future = Future.future();

        future.setHandler(ar -> {
            if (ar.succeeded() && ar.result() == null) {
                String error = String.format("The app with id: %s can not be found", id);
                resultHandler.handle(Future.failedFuture(new NotFoundException(error)));
            } else {
                resultHandler.handle(ar);
            }
        });
        
        appDBServiceProxy.getApps(id, future);
    }
}
