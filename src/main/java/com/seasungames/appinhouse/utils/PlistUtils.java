package com.seasungames.appinhouse.utils;

import com.dd.plist.*;

/***
 <?xml version="1.0" encoding="UTF-8"?>
 <!DOCTYPE plist PUBLIC "-//Apple//DTD PLIST 1.0//EN" "http://www.apple.com/DTDs/PropertyList-1.0.dtd">
 <plist version="1.0">
    <dict>
        <key>items</key>
        <array>
            <dict>
                <key>assets</key>
                <array>
                    <dict>
                        <key>kind</key>
                        <string>software-package</string>
                        <key>url</key>
                        <string>https://jenkins.intranet.rog2.org/job/Personal/job/liujun8/job/pirates-ios/lastSuccessfulBuild/artifact/pirates-personal-trunk-b9-20181225-development.ipa</string>
                    </dict>
                </array>
                <key>metadata</key>
                <dict>
                    <key>bundle-identifier</key>
                    <string>cn.com.xishanju.pirates</string>
                    <key>bundle-version</key>
                    <string>1.0</string>
                    <key>kind</key>
                    <string>software</string>
                    <key>title</key>
                    <string>加勒比海盗</string>
                </dict>
            </dict>
        </array>
    </dict>
 </plist>

 ***/
public class PlistUtils {

    public static String genPlist(String url, String bundle_id, String title) {
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
