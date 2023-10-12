package com.bytotech.Restorder.WS.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BaseVideoResponse {
    @SerializedName("response")
    @Expose
    private VideoResponse response;
    @SerializedName("vedio")
    @Expose
    private ArrayList<String> vedio;

    public VideoResponse getResponse() {
        return response;
    }

    public void setResponse(VideoResponse response) {
        this.response = response;
    }

    public ArrayList<String> getVedio() {
        return vedio;
    }

    public void setVedio(ArrayList<String> vedio) {
        this.vedio = vedio;
    }

}
