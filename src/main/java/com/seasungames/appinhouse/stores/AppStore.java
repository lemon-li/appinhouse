package com.seasungames.appinhouse.stores;

import com.seasungames.appinhouse.stores.services.app.models.AppVo;
import com.seasungames.appinhouse.stores.services.app.models.AppListResponseVo;
import com.seasungames.appinhouse.stores.services.app.models.AppResponseVo;

/**
 * @author lile on 2018-12-28
 */
public interface AppStore {

    AppListResponseVo getAppsList(String lastKey);

    AppResponseVo updateApps(AppVo vo);

    void createApps(AppVo vo);

    void deleteApps(String appId);

    AppResponseVo getApps(String appId);
}
