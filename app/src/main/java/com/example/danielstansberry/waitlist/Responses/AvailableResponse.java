
package com.example.danielstansberry.waitlist.Responses;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class AvailableResponse {

    @SerializedName("Response")
    @Expose
    public String Response;
    @SerializedName("Table_available")
    @Expose
    public Boolean TableAvailable;
    @SerializedName("Error_reason")
    @Expose
    public String ErrorReason;

}
