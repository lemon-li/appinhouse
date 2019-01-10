package com.seasungames.appinhouse.services.impl;

import com.seasungames.appinhouse.models.VersionVo;
import com.seasungames.appinhouse.models.response.VersionResponseVo;
import com.seasungames.appinhouse.services.AppService;
import com.seasungames.appinhouse.services.VersionService;
import com.seasungames.appinhouse.stores.VersionStore;
import com.seasungames.appinhouse.utils.PlistUtils;

import java.util.List;

/**
 * Created by lile on 1/4/2019
 */
public class VersionServiceImpl implements VersionService {

    private final VersionStore versionTable;
    private final AppService appService;

    public VersionServiceImpl(VersionStore versionTable, AppService appService) {
        this.versionTable = versionTable;
        this.appService = appService;
    }

    @Override
    public List<VersionResponseVo> getPlatformList(String appId, String platform) {
        return versionTable.getPlatformList(appId, platform);
    }

    @Override
    public List<VersionResponseVo> getLatestList(String appId) {
         return versionTable.getLatestList(appId);
    }

    @Override
    public int createVersion(VersionVo vo) {
        appService.getApps(vo.getAppId());
        return versionTable.createVersion(vo);
    }

    @Override
    public String getPlist(String appId, String platform, String version) {
        VersionVo vo = versionTable.getOneApp(appId, platform, version);
        if (vo != null) {
            return PlistUtils.genPlist(vo.getDownload_url(), vo.getIos_bundle_id(), vo.getIos_title());
        } else {
            throw new IllegalStateException("getOneApp");
        }
    }
}
