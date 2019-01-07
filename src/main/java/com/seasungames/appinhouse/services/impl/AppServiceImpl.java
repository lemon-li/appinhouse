package com.seasungames.appinhouse.services.impl;

import com.seasungames.appinhouse.models.AppVo;
import com.seasungames.appinhouse.routes.exception.impl.NotFoundException;
import com.seasungames.appinhouse.services.AppService;
import com.seasungames.appinhouse.stores.AppStore;
import io.vertx.core.json.JsonArray;

import java.util.List;

/**
 * Created by lile on 1/4/2019
 */
public class AppServiceImpl implements AppService {

    private final AppStore appTable;

    public AppServiceImpl(AppStore app) {
        this.appTable = app;
    }

    @Override
    public String getAppsList() {
        List<AppVo> appLists = appTable.getAppsList();
        return new JsonArray(appLists).toString();
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
        String result = appTable.getApps(id);
        if (result.isEmpty()) {
            throw new NotFoundException("The app with id " + id + " can not be found");
        }
        return result;
    }
}
