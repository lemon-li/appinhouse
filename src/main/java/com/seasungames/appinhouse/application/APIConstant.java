package com.seasungames.appinhouse.application;

public class APIConstant {
    public static final String INDEX = "/";
    public static final String API_INDEX_APP = "/app/:app";
    public static final String API_INDEX_VERSION = "/app/:id/platform/:pf/:version";


    public static final String API_GET_APPS = "/api/apps";
    public static final String API_GET_APP = "/api/apps/:id";
    public static final String API_CREATE_APPS = "/api/apps";
    public static final String API_UPDATE_APPS = "/api/apps";
    public static final String API_DELETE_APPS = "/api/apps";

    public static final String API_GET_VERSIONS_LATEST = "/api/versions/:id/latest";
    public static final String API_GET_VERSIONS_HISTORY = "/api/versions/:id/:platform/history";
    public static final String API_CREATE_VERSIONS = "/api/versions/:id/:platform";
    public static final String API_GET_VERSIONS_PLIST = "/api/versions/plist/:id/:platform/:version";
}
