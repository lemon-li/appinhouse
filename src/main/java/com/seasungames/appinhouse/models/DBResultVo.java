package com.seasungames.appinhouse.models;

import java.util.List;

/**
 * Created by lile on 1/8/2019
 */
public class DBResultVo {
    private List<AppVo> list;
    private String lastKey;

    public DBResultVo() {

    }

    public List<AppVo> getList() {
        return list;
    }

    public DBResultVo setList(List<AppVo> list) {
        this.list = list;
        return this;
    }

    public String getLastKey() {
        return lastKey;
    }

    public DBResultVo setLastKey(String lastKey) {
        this.lastKey = lastKey;
        return this;
    }
}
