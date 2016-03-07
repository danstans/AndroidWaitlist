package com.example.danielstansberry.waitlist;

import com.example.danielstansberry.waitlist.Responses.ApplyResponse;
import com.example.danielstansberry.waitlist.Responses.AvailableResponse;
import com.example.danielstansberry.waitlist.Responses.TableList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rion on 3/6/16.
 */
public class TableListCallback implements Callback<TableList> {

    @Override
    public void onResponse(Response<TableList> response) {
        AppInfo appInfo = AppInfo.getAppInfo();

        if (response.body().NumberInList > 0) {
            Call<ApplyResponse> applyResponseCall =  appInfo.api.applyWaitList(appInfo.nickname, appInfo.deviceId, appInfo.partySize);
            applyResponseCall.enqueue(new ApplyCallback());
        }
        else {
            Call<AvailableResponse> availableResponseCall = appInfo.api.isTableAvailable(appInfo.partySize);
            availableResponseCall.enqueue(new AvailableCallback());
        }
    }

    @Override
    public void onFailure(Throwable t) {

    }

}
