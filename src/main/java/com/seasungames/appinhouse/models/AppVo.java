package com.seasungames.appinhouse.models;

/**
 * Created by lile on 12/28/2018
 */
public class AppVo {

    private String appId;

    private String alias;

    private String desc;

    public AppVo() {

    }

    public AppVo(String id, String alias, String desc) {
        this.appId = id;
        this.alias = alias;
        this.desc = desc;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String hydraId) {
        this.appId = hydraId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

}