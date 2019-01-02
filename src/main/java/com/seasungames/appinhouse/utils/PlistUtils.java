package com.seasungames.appinhouse.utils;

import com.dd.plist.*;

public class PlistUtils {

    public static String GenPlist(String url, String bundle_id, String title) {
        try {
            NSDictionary root = new NSDictionary();
            NSArray items = new NSArray(1);

            NSDictionary assetsRoot = new NSDictionary();

            //assets key
            NSArray assetArray = new NSArray(1);
            NSDictionary assetsDict = new NSDictionary();
            assetsDict.put("kind", "software-package");
            assetsDict.put("url", url);
            assetArray.setValue(0, assetsDict);
            assetsRoot.put("assets", assetArray);

            //metadata key
            NSDictionary bundleDict = new NSDictionary();
            bundleDict.put("bundle-identifier", bundle_id);
            bundleDict.put("bundle-version", "1.0");
            bundleDict.put("kind", "software");
            bundleDict.put("title", title);
            assetsRoot.put("metadata", bundleDict);

            items.setValue(0, assetsRoot);
            root.put("items", items);

            return root.toXMLPropertyList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

}
