package com.mazej.plantcare.database;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface PlantCareApi {

    String BASE_URL = "http://172.17.224.1:3000/";

    @FormUrlEncoded
    @POST("auth/signin")
    Call<PostLogIn> createLogInPost(
            @Field("email") String email,
            @Field("password") String password,
            @Field("access_token") String access_token);

    @FormUrlEncoded
    @POST("auth/signup")
    Call<PostSignUp> createSignUpPost(
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password,
            @Field("access_token") String access_token);

    @GET("plant")
    Call<List<GetPlant>> createPlantGet(
            @Header("Authorization") String access_token);
}
