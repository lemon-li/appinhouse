package com.seasungames.appinhouse.stores;

import com.seasungames.appinhouse.models.AppVo;
import com.seasungames.appinhouse.models.DBResultVo;

/**
 * Created by lile on 12/28/2018
 */
public interface AppStore {

    DBResultVo getAppsList(String lastKey);

    int updateApps(AppVo vo);

    int createApps(AppVo vo);

    int deleteApps(String appId);

    String getApps(String appId);

}
