package com.seasungames.appinhouse.utils;

import com.seasungames.appinhouse.application.APIConstant;

/**
 * Created by lile on 12/27/2018
 */
public class PathUtils {

    public static String getAssetsPath(String relativePath) {
        return APIConstant.WEBROOT + relativePath;
    }
}
