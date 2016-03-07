package com.example.danielstansberry.waitlist;

import android.app.AlertDialog;

import com.example.danielstansberry.waitlist.Responses.ApplyResponse;

import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rion on 3/6/16.
 */
public class ApplyCallback implements Callback<ApplyResponse> {

    @Override
    public void onResponse(Response<ApplyResponse> response) {
        AppInfo appInfo = AppInfo.getAppInfo();

        AlertDialog.Builder applied = new AlertDialog.Builder(appInfo.context);
        applied.setTitle("You have been added to the wait list");
        applied.setMessage("You will be notified when your table is ready");
        applied.setPositiveButton("OK", null);
        applied.create().show();
    }

    @Override
    public void onFailure(Throwable t) {

    }
}
