package com.seasungames.appinhouse.stores;

import com.seasungames.appinhouse.models.AppVo;

import java.util.List;

/**
 * Created by lile on 12/28/2018
 */
public interface IAppStore {

    List<AppVo> GetAppsList();

    String UpdateApps(AppVo vo);

    int CreateApps(AppVo vo);

    int DeleteApps(String appId);

    String GetApps(String appId);

}
