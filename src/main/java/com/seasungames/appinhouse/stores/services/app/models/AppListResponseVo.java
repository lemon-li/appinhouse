package com.seasungames.appinhouse.stores.services.app.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.util.List;

/**
 * Created by lile on 1/10/2019
 */
@DataObject(generateConverter = true)
public class AppListResponseVo {

    private List<AppResponseVo> list;
    private String lastKey;

    public AppListResponseVo(JsonObject jsonObject) {
        AppListResponseVoConverter.fromJson(jsonObject, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        AppListResponseVoConverter.toJson(this, json);
        return json;
    }

    public AppListResponseVo() {

    }

    public List<AppResponseVo> getList() {
        return list;
    }

    public AppListResponseVo setList(List<AppResponseVo> list) {
        this.list = list;
        return this;
    }

    @JsonProperty("last_key")
    public String getLastKey() {
        return lastKey;
    }

    public AppListResponseVo setLastKey(String lastKey) {
        this.lastKey = lastKey;
        return this;
    }
}
