package com.example.danielstansberry.waitlist;

import com.example.danielstansberry.waitlist.Responses.ApplyResponse;
import com.example.danielstansberry.waitlist.Responses.AvailableResponse;
import com.example.danielstansberry.waitlist.Responses.TableList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by rion on 3/6/16.
 */
public interface WaitListApi {

    @GET("get_waitlist")
    Call<TableList> getWaitList();

    @POST("apply_waitlist")
    Call<ApplyResponse> applyWaitList(@Query("user_name") String userName,
                                      @Query("device_id") String deviceId,
                                      @Query("party_size") int partySize);
    @GET("is_available")
    Call<AvailableResponse> isTableAvailable(@Query("table_size") int tableSize);

}
