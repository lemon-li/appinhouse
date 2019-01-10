package com.seasungames.appinhouse.models.response;

/**
 * Created by lile on 1/10/2019
 */
public class AppResponseVo {

    private String id;

    private String alias;

    private String desc;

    public AppResponseVo() {

    }

    public AppResponseVo(String id, String alias, String desc) {
        this.id = id;
        this.alias = alias;
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String AppId) {
        this.id = AppId;
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
