package com.example.danielstansberry.waitlist;

import android.content.Context;

/**
 * Created by rion on 3/6/16.
 */
public class AppInfo {

    public WaitListApi api;
    public Context context;
    public String nickname;
    public String deviceId;
    public int partySize;

    private static AppInfo sAppInfo = null;

    protected AppInfo() {} // To prevent instantiation

    public static AppInfo getAppInfo() {
        if (sAppInfo == null) {
            sAppInfo = new AppInfo();
        }
        return sAppInfo;
    }
}
