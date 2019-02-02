package com.seasungames.appinhouse.stores.services.version.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.util.List;

/**
 * @author lile on 2019-01-27
 */
@DataObject(generateConverter = true)
public class VersionListResponseVo {

    private List<VersionResponseVo> list;
    private String lastKey;

    public VersionListResponseVo(JsonObject jsonObject) {
        VersionListResponseVoConverter.fromJson(jsonObject, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        VersionListResponseVoConverter.toJson(this, json);
        return json;
    }

    public VersionListResponseVo() {

    }

    public List<VersionResponseVo> getList() {
        return list;
    }

    public VersionListResponseVo setList(List<VersionResponseVo> list) {
        this.list = list;
        return this;
    }

    @JsonProperty("last_key")
    public String getLastKey() {
        return lastKey;
    }

    public VersionListResponseVo setLastKey(String lastKey) {
        this.lastKey = lastKey;
        return this;
    }
}
