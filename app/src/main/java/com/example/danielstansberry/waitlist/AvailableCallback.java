package com.example.danielstansberry.waitlist;

import android.app.AlertDialog;

import com.example.danielstansberry.waitlist.Responses.ApplyResponse;
import com.example.danielstansberry.waitlist.Responses.AvailableResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rion on 3/6/16.
 */
public class AvailableCallback implements Callback<AvailableResponse> {

    @Override
    public void onResponse(Response<AvailableResponse> response) {
        AppInfo appInfo = AppInfo.getAppInfo();

        if (response.body().TableAvailable) {
            AlertDialog.Builder tableIsOpen = new AlertDialog.Builder(appInfo.context);
            tableIsOpen.setTitle("A table is open for you");
            tableIsOpen.setMessage("We found an open table for your party, so head on in to the restaurant!");
            tableIsOpen.setPositiveButton("OK", null);
            tableIsOpen.create().show();
        }
        else {
            Call<ApplyResponse> applyResponseCall =  appInfo.api.applyWaitList(appInfo.nickname, appInfo.deviceId, appInfo.partySize);
            applyResponseCall.enqueue(new ApplyCallback());
        }
    }

    @Override
    public void onFailure(Throwable t) {

    }
}
