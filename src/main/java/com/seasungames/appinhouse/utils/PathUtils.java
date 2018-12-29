package com.seasungames.appinhouse.utils;

/**
 * Created by lile on 12/27/2018
 */
public class PathUtils {

    public static String getAssetsPath(String relativePath) {
        return "webroot" + relativePath;
    }
}
