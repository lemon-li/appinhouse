package com.seasungames.appinhouse.stores.services.app.models;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * @author lile on 2019-01-10
 */
@DataObject(generateConverter = true)
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

    public AppResponseVo(JsonObject jsonObject) {
        AppResponseVoConverter.fromJson(jsonObject, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        AppResponseVoConverter.toJson(this, json);
        return json;
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
