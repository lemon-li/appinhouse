package com.seasungames.appinhouse.services.impl;

import com.seasungames.appinhouse.models.VersionVo;
import com.seasungames.appinhouse.services.VersionService;
import com.seasungames.appinhouse.stores.VersionStore;
import com.seasungames.appinhouse.utils.PlistUtils;

/**
 * Created by lile on 1/4/2019
 */
public class VersionServiceImpl implements VersionService {

    private final VersionStore versionTable;

    public VersionServiceImpl(VersionStore versionTable) {
        this.versionTable = versionTable;
    }

    @Override
    public String getPlatformList(String appId, String platform) {
        return versionTable.getPlatformList(appId, platform);
    }

    @Override
    public String getLatestList(String appId) {
        return versionTable.getLatestList(appId);
    }

    @Override
    public int createVersion(VersionVo vo) {
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
