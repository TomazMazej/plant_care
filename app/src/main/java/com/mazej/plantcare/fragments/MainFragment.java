package com.mazej.plantcare.fragments;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mazej.plantcare.R;
import com.mazej.plantcare.database.GetUserPlant;
import com.mazej.plantcare.database.PlantCareApi;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.mazej.plantcare.activities.MainActivity.toolbar;
import static com.mazej.plantcare.database.PlantCareApi.BASE_URL;

public class MainFragment extends Fragment {

    private PlantCareApi plantCareApi;
    private SharedPreferences sp;

    private TextView plantInfo;

    private int waterCounter = 0;
    public static int plantCounter = 0;

    public MainFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("Home");

        plantInfo = view.findViewById(R.id.plant_info_text);
        sp = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        // Get all plants from current user
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String token = "Bearer " + sp.getString("access_token", "DEFAULT VALUE ERR");
        plantCareApi = retrofit.create(PlantCareApi.class);
        Call<List<GetUserPlant>> call = plantCareApi.createUserPlantGet(token);

        call.enqueue(new Callback<List<GetUserPlant>>() {
            @Override
            public void onResponse(Call<List<GetUserPlant>> call, Response<List<GetUserPlant>> response) {
                if (!response.isSuccessful()) { // If request is not successful
                    Toast.makeText(getActivity().getApplicationContext(), "Could not connect to server.", Toast.LENGTH_SHORT).show();
                } else {
                    plantCounter = response.body().size();
                    for (int i = 0; i < response.body().size(); i++) { // Add plants to list
                        System.out.println("" + i + " " + response.body().get(i).getRemaining_water_days());
                        if (response.body().get(i).getRemaining_water_days() == 0) {
                            waterCounter++;
                        }
                    }
                }
                if (waterCounter == 0) {
                    plantInfo.setText("All your plants are taken care of.");
                } else if (waterCounter == 1) {
                    plantInfo.setText("You have to water 1 plant today.");
                } else {
                    plantInfo.setText("You have to water " + waterCounter + " plants today.");
                }
            }

            @Override
            public void onFailure(Call<List<GetUserPlant>> call, Throwable t) {
                System.out.println("No response: GetUserPlant neuspešno!");
                System.out.println(t);
                // Toast.makeText(getActivity().getApplicationContext(),"Could not connect to server.", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
