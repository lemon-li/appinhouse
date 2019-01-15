package com.seasungames.appinhouse.services.impl;

import com.seasungames.appinhouse.dagger.scope.AppInHouse;
import com.seasungames.appinhouse.stores.services.app.models.AppVo;
import com.seasungames.appinhouse.stores.services.app.models.AppListResponseVo;
import com.seasungames.appinhouse.stores.services.app.models.AppResponseVo;
import com.seasungames.appinhouse.routes.exception.impl.NotFoundException;
import com.seasungames.appinhouse.services.AppService;
import com.seasungames.appinhouse.stores.AppStore;

import javax.inject.Inject;

/**
 * Created by lile on 1/4/2019
 */
@AppInHouse
public class AppServiceImpl implements AppService {

    @Inject
    AppStore appTable;

    @Inject
    public AppServiceImpl() {

    }

    @Override
    public AppListResponseVo getAppsList(String lastKey) {
        return appTable.getAppsList(lastKey);
    }

    @Override
    public AppResponseVo updateApps(AppVo appVo) {
        return appTable.updateApps(appVo);
    }

    @Override
    public void createApps(AppVo appVo) {
        appTable.createApps(appVo);
    }

    @Override
    public void deleteApps(String id) {
        appTable.deleteApps(id);
    }

    @Override
    public AppResponseVo getApps(String id) {
        AppResponseVo appVo = appTable.getApps(id);
        if (appVo == null) {
            throw new NotFoundException("The app with id " + id + " can not be found");
        }
        return appVo;
    }
}
