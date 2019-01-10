package com.seasungames.appinhouse.stores;

import com.seasungames.appinhouse.models.AppVo;
import com.seasungames.appinhouse.models.response.AppListResponseVo;
import com.seasungames.appinhouse.models.response.AppResponseVo;

/**
 * Created by lile on 12/28/2018
 */
public interface AppStore {

    AppListResponseVo getAppsList(String lastKey);

    int updateApps(AppVo vo);

    int createApps(AppVo vo);

    int deleteApps(String appId);

    AppResponseVo getApps(String appId);

}
