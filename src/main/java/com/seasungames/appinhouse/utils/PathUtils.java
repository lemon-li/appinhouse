package com.seasungames.appinhouse.utils;

import com.seasungames.appinhouse.application.APIConstant;

/**
 * @author lile on 2018-12-27
 */
public class PathUtils {

    public static String getAssetsPath(String relativePath) {
        return APIConstant.WEBROOT + relativePath;
    }
}
