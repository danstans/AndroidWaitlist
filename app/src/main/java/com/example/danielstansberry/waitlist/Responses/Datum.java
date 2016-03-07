
package com.example.danielstansberry.waitlist.Responses;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Datum {

    @SerializedName("has_been_contacted")
    @Expose
    public Boolean hasBeenContacted;
    @SerializedName("timestamp")
    @Expose
    public String timestamp;
    @SerializedName("party_size")
    @Expose
    public String partySize;
    @SerializedName("user_name")
    @Expose
    public String userName;
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("device_id")
    @Expose
    public String deviceId;

}
