package com.bytotech.Restorder.WS;


import com.bytotech.Restorder.WS.Response.BaseVideoResponse;
import com.bytotech.Restorder.WS.Response.ResponseBaseGetOTP;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;


public interface Api {


    String BASE_URL=" https://phool.spindiabazaar.com/";




    @Multipart
    @POST("user_login_byotpapi.php")
    Call<ResponseBaseGetOTP> getLoginOTP(@Part("mobile") RequestBody mobile);

    @GET("api_vedio.php")
    Call<BaseVideoResponse> getVideo(@Query("id") String id);




}
