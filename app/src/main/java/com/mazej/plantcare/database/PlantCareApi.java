package com.mazej.plantcare.database;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PlantCareApi {

    String BASE_URL = "http://192.168.0.110:3000/";

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

    @FormUrlEncoded
    @POST("user-plant")
    Call<PostUserPlant> createUserPlantPost(
            @Header("Authorization") String access_token,
            @Field("plant_id") int plant_id);

    @GET("user-plant")
    Call<List<GetUserPlant>> createUserPlantGet(
            @Header("Authorization") String access_token);

    @GET("plant")
    Call<List<GetPlant>> createPlantGet(
            @Header("Authorization") String access_token);

    @GET("user")
    Call<GetUser> createUserGet(
            @Header("Authorization") String access_token);

    @DELETE("user-plant/{Id}")
    Call<Void> createUserPlantDelete(
            @Header("Authorization") String access_token,
            @Path("Id") int apiPlantId);
}
