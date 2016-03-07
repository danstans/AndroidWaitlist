
package com.example.danielstansberry.waitlist.Responses;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class TableList {

    @SerializedName("Data")
    @Expose
    public List<Datum> Data = new ArrayList<Datum>();
    @SerializedName("Response")
    @Expose
    public String Response;
    @SerializedName("NumberInList")
    @Expose
    public Integer NumberInList;

}
