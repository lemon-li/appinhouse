package com.seasungames.appinhouse.stores;

import com.seasungames.appinhouse.models.AppVo;

import java.util.List;

/**
 * Created by lile on 12/28/2018
 */
public interface AppStore {

    List<AppVo> getAppsList();

    int updateApps(AppVo vo);

    int createApps(AppVo vo);

    int deleteApps(String appId);

    String getApps(String appId);

}
