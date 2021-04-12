package com.mazej.plantcare.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mazej.plantcare.R;
import com.mazej.plantcare.activities.MainActivity;
import com.mazej.plantcare.database.GetPlant;
import com.mazej.plantcare.database.PlantCareApi;
import com.mazej.plantcare.database.PostLogIn;
import com.mazej.plantcare.objects.MyPlant;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.mazej.plantcare.activities.MainActivity.toolbar;

public class SearchPlantsFragment extends Fragment {

    private PlantCareApi plantCareApi;
    private SharedPreferences sp;
    //private ArrayList<MyPlant> plantList;
    //private RecyclerViewAdapter recyclerViewAdapter;


    public SearchPlantsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search_plants, container, false);

        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("Search Plants");
        sp = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.17.224.1:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        plantCareApi = retrofit.create(PlantCareApi.class);

        String a = "Bearer " + sp.getString("access_token","DEFAULT VALUE ERR");
        // String a = sp.getString("access_token","DEFAULT VALUE ERR");
        System.out.println(a);

        Call<List<GetPlant>> call = plantCareApi.createPlantGet(a);

        call.enqueue(new Callback<List<GetPlant>>() {
            @Override
            public void onResponse(Call<List<GetPlant>> call, Response<List<GetPlant>> response) {
                if (!response.isSuccessful()){ //če request ni uspešen
                    System.out.println("Response: neuspesno!");
                }
                else{
                    System.out.println("Response: uspešno!");
                    System.out.println(response.body().size());

                    // Print plant data
                    for(int i = 0; i< response.body().size(); i++ ){
                        System.out.println(response.body().get(i).getName());
                        System.out.println(response.body().get(i).getImage_path());
                        System.out.println(response.body().get(i).getDays_water());
                        System.out.println(response.body().get(i).getInfo());

                    }
                }
            }

            @Override
            public void onFailure(Call<List<GetPlant>> call, Throwable t) {
                System.out.println("No response: neuspešno!");
                System.out.println(t);
            }
        });

        return view;
    }
}