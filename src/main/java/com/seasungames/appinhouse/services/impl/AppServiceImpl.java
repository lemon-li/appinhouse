package com.seasungames.appinhouse.services.impl;

import com.seasungames.appinhouse.models.AppVo;
import com.seasungames.appinhouse.models.response.AppListResponseVo;
import com.seasungames.appinhouse.models.response.AppResponseVo;
import com.seasungames.appinhouse.routes.exception.impl.NotFoundException;
import com.seasungames.appinhouse.services.AppService;
import com.seasungames.appinhouse.stores.AppStore;

/**
 * Created by lile on 1/4/2019
 */
public class AppServiceImpl implements AppService {

    private final AppStore appTable;

    public AppServiceImpl(AppStore app) {
        this.appTable = app;
    }

    @Override
    public AppListResponseVo getAppsList(String lastKey) {
        return appTable.getAppsList(lastKey);
    }

    @Override
    public int updateApps(AppVo appVo) {
        return appTable.updateApps(appVo);
    }

    @Override
    public int createApps(AppVo appVo) {
        return appTable.createApps(appVo);
    }

    @Override
    public int deleteApps(String id) {
        return appTable.deleteApps(id);
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
