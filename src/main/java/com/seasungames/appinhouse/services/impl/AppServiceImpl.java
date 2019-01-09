package com.seasungames.appinhouse.services.impl;

import com.seasungames.appinhouse.models.AppVo;
import com.seasungames.appinhouse.models.ResponseVo;
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
    public String getAppsList(String lastKey) {
        return new ResponseVo()
                .setData(appTable.getAppsList(lastKey))
                .toJson();
    }

    @Override
    public int updateApps(String id, String desc, String alias) {
        AppVo appVO = new AppVo(id, desc, alias);
        return appTable.updateApps(appVO);
    }

    @Override
    public int createApps(String id, String desc, String alias) {
        AppVo appVO = new AppVo(id, desc, alias);
        return appTable.createApps(appVO);
    }

    @Override
    public int deleteApps(String id) {
        return appTable.deleteApps(id);
    }

    @Override
    public String getApps(String id) {
        AppVo appVo = appTable.getApps(id);
        if (appVo == null) {
            throw new NotFoundException("The app with id " + id + " can not be found");
        }
        return new ResponseVo()
                .setData(appVo)
                .toJson();
    }
}
