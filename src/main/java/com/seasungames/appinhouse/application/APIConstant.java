package com.seasungames.appinhouse.application;

/**
 * @author lile on 2019-01-05
 */
public class APIConstant {

    public static final String WEBROOT = "webroot";

    public static final String INDEX_APP = "/app/:app";
    public static final String INDEX_VERSION = "/version/:app";


    public static final String API_APPS = "/api/apps";

    public static final String API_GET_VERSIONS_LATEST = "/api/versions/:id/latest";
    public static final String API_GET_VERSIONS_HISTORY = "/api/versions/:id/:platform/history";
    public static final String API_CREATE_VERSIONS = "/api/versions/:id/:platform";
    public static final String API_GET_VERSIONS_PLIST = "/api/versions/:id/:platform/:version/plist";
}
