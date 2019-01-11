package com.seasungames.appinhouse.models.response;

import java.util.Map;

/**
 * Created by lile on 1/10/2019
 */
public class VersionResponseVo {

    private String Id;

    private String platform;

    private String version;

    private Map<String, Object> info;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Map<String, Object> getInfo() {
        return info;
    }

    public void setInfo(Map<String, Object> info) {
        this.info = info;
    }
}
