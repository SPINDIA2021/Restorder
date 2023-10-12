package com.bytotech.Restorder.WS.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseBaseGetOTP {
    @SerializedName("response")
    @Expose
    private ResponseGetOTP response;
    @SerializedName("login_detail")
    @Expose
    private ResponseLoginDetail loginDetail;

    public ResponseGetOTP getResponse() {
        return response;
    }

    public void setResponse(ResponseGetOTP response) {
        this.response = response;
    }

    public ResponseLoginDetail getLoginDetail() {
        return loginDetail;
    }

    public void setLoginDetail(ResponseLoginDetail loginDetail) {
        this.loginDetail = loginDetail;
    }

}
