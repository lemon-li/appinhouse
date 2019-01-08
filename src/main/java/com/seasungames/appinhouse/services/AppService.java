package com.seasungames.appinhouse.services;

public interface AppService {

    String getAppsList(String lastKey);

    int updateApps(String id, String desc, String alias);

    int createApps(String id, String desc, String alias);

    int deleteApps(String id);

    String getApps(String id);

}