package com.sujin.hamrobazar.api;


import com.sujin.hamrobazar.model.Product;
import com.sujin.hamrobazar.model.User;
import com.sujin.hamrobazar.serverresponse.ImageResponse;
import com.sujin.hamrobazar.serverresponse.RegisterResponse;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UsersAPI {

    @POST("users/register")
    Call<RegisterResponse> registerUser(@Body User users);

    @FormUrlEncoded
    @POST("users/login")
    Call<RegisterResponse> checkUser(@Field("email") String username, @Field("password") String password);

    @Multipart
    @POST("upload")
    Call<ImageResponse> uploadImage(@Part MultipartBody.Part img);

    @GET("product")
    Call<List<Product>>getAllProducts();

    @GET("users/me")
    Call<User> getUserDetails(@Header("Authorization") String token);

}
