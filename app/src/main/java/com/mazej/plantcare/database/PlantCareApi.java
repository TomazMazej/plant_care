package com.mazej.plantcare.database;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PlantCareApi {

    @FormUrlEncoded
    @POST("auth/signin")
    Call<PostSignIn> createLogInPost(
            @Field("email") String email,
            @Field("password") String password,
            @Field("access_token") String access_token);
}
