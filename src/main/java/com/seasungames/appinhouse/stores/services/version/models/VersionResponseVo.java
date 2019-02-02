package com.seasungames.appinhouse.stores.services.version.models;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.util.Map;

/**
 * @author lile on 2019-01-10
 */
@DataObject(generateConverter = true)
public class VersionResponseVo {

    private String Id;

    private String platform;

    private String version;

    private Map<String, Object> info;

    public VersionResponseVo(JsonObject jsonObject) {
        VersionResponseVoConverter.fromJson(jsonObject, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        VersionResponseVoConverter.toJson(this, json);
        return json;
    }

    public VersionResponseVo() {

    }

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
