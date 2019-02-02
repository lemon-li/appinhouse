package com.seasungames.appinhouse.stores.dynamodb.tables;

/**
 * @author lile on 2018-12-28
 */
public class VersionTable {

    public static final String HASH_KEY_APPID = "id";

    public static final String RANGE_KEY_VERSION = "version";

    public static final String ATTRIBUTE_PLATFORM = "platform";

    public static final String ATTRIBUTE_JSON_INFO = "info";

    public static final String ATTRIBUTE_DOWNLOAD_URL = "download_url";

    public static final String ATTRIBUTE_JENKINS_URL = "jenkins_url";

    public static final String ATTRIBUTE_DESC = "desc";

    public static final String ATTRIBUTE_IOS_BUNDLE_ID = "ios_bundle_id";

    public static final String ATTRIBUTE_IOS_TITLE = "ios_title";

    public static final String ATTRIBUTE_TIME = "create_time";
}
