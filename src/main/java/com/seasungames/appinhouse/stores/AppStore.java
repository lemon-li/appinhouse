package com.seasungames.appinhouse.stores;

import com.seasungames.appinhouse.stores.services.app.models.AppVo;
import com.seasungames.appinhouse.stores.services.app.models.AppListResponseVo;
import com.seasungames.appinhouse.stores.services.app.models.AppResponseVo;

/**
 * Created by lile on 12/28/2018
 */
public interface AppStore {

    AppListResponseVo getAppsList(String lastKey);

    AppResponseVo updateApps(AppVo vo);

    void createApps(AppVo vo);

    void deleteApps(String appId);

    AppResponseVo getApps(String appId);

}
