package com.seasungames.appinhouse.services;

import com.seasungames.appinhouse.models.AppVo;

public interface AppService {

    String getAppsList(String lastKey);

    int updateApps(AppVo appVo);

    int createApps(AppVo appVo);

    int deleteApps(String id);

    String getApps(String id);

}