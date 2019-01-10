package com.seasungames.appinhouse.services;

import com.seasungames.appinhouse.models.AppVo;
import com.seasungames.appinhouse.models.response.AppListResponseVo;
import com.seasungames.appinhouse.models.response.AppResponseVo;

public interface AppService {

    AppListResponseVo getAppsList(String lastKey);

    int updateApps(AppVo appVo);

    int createApps(AppVo appVo);

    int deleteApps(String id);

    AppResponseVo getApps(String id);

}