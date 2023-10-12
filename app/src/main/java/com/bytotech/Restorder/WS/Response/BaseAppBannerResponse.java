package com.bytotech.Restorder.WS.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BaseAppBannerResponse {
    @SerializedName("FOOD_APP")
    @Expose
    private ArrayList<AppBannerResponse> appBannerResponses;

    public ArrayList<AppBannerResponse> getAppBannerResponse() {
        return appBannerResponses;
    }

    public void setAppBannerResponse(ArrayList<AppBannerResponse> appBannerResponses) {
        this.appBannerResponses = appBannerResponses;
    }
}
