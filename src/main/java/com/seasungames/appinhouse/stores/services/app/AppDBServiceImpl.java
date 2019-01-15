package com.seasungames.appinhouse.stores.services.app;

import com.seasungames.appinhouse.dagger.scope.AppInHouse;
import com.seasungames.appinhouse.stores.services.app.models.AppListResponseVo;
import com.seasungames.appinhouse.stores.services.app.models.AppResponseVo;
import com.seasungames.appinhouse.stores.services.app.models.AppVo;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

import javax.inject.Inject;

/**
 * Created by lile on 1/15/2019
 */
@AppInHouse
public class AppDBServiceImpl implements AppDBService {

    @Inject
    public AppDBServiceImpl() {

    }

    @Override
    public void getAppsList(String lastKey, Handler<AsyncResult<AppListResponseVo>> resultHandler) {

    }

    @Override
    public void updateApps(AppVo vo, Handler<AsyncResult<AppResponseVo>> resultHandler) {

    }

    @Override
    public void createApps(AppVo vo, Handler<AsyncResult<Void>> resultHandler) {

    }

    @Override
    public void deleteApps(String appId, Handler<AsyncResult<Void>> resultHandler) {

    }

    @Override
    public void getApps(String appId, Handler<AsyncResult<AppResponseVo>> resultHandler) {

    }

    @Override
    public void close(Handler<AsyncResult<Void>> resultHandler) {

    }
}
