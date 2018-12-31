package com.seasungames.appinhouse.stores.dynamodb.tables;

/**
 * Created by lile on 12/28/2018
 */
public class VersionTable {

    public static final String HASH_KEY_APPID = "Id";

    public static final String SECONDARY_INDEX_VERSION = "platform";

    public static final String RANGE_KEY_VERSION = "version";

    public static final String ATTRIBUTE_DOWNLOAD_URL = "download_url";

    public static final String ATTRIBUTE_JENKINS_URL = "jenkins_url";

    public static final String ATTRIBUTE_PLIST = "plist";
}
