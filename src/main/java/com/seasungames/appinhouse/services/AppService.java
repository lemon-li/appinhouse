package com.seasungames.appinhouse.services;

import com.seasungames.appinhouse.stores.services.app.models.AppVo;
import com.seasungames.appinhouse.stores.services.app.models.AppListResponseVo;
import com.seasungames.appinhouse.stores.services.app.models.AppResponseVo;

public interface AppService {

    AppListResponseVo getAppsList(String lastKey);

    AppResponseVo updateApps(AppVo appVo);

    void createApps(AppVo appVo);

    void deleteApps(String id);

    AppResponseVo getApps(String id);

}
