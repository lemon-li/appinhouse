package com.seasungames.appinhouse.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by lile on 1/10/2019
 */
public class AppListResponseVo {

    private List<AppResponseVo> list;
    private String lastKey;

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
