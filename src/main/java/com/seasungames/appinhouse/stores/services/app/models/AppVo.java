package com.seasungames.appinhouse.stores.services.app.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * @author lile on 2018-12-28
 */
@DataObject(generateConverter = true)
public class AppVo {

    @JsonProperty(value = "id",required = true)
    private String appId;

    @JsonProperty(required = true)
    private String alias;

    @JsonProperty(required = true)
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
