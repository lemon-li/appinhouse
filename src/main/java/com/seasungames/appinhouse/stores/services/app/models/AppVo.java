package com.seasungames.appinhouse.stores.services.app.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * Created by lile on 12/28/2018
 */
@DataObject(generateConverter = true)
public class AppVo {

    private String appId;

    private String alias;

    private String desc;

    public AppVo(JsonObject jsonObject) {
        AppVoConverter.fromJson(jsonObject, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        AppVoConverter.toJson(this, json);
        return json;
    }

    public AppVo() {

    }

    public AppVo(String id, String alias, String desc) {
        this.appId = id;
        this.alias = alias;
        this.desc = desc;
    }

    @JsonProperty("id")
    public String getAppId() {
        return appId;
    }

    public void setAppId(String AppId) {
        this.appId = AppId;
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
