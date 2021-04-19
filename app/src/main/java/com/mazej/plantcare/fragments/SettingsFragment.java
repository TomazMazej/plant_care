package com.mazej.plantcare.fragments;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mazej.plantcare.R;
import com.mazej.plantcare.database.GetPlant;
import com.mazej.plantcare.database.GetUser;
import com.mazej.plantcare.database.PlantCareApi;
import com.mazej.plantcare.objects.MyPlant;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.mazej.plantcare.activities.MainActivity.toolbar;
import static com.mazej.plantcare.database.PlantCareApi.BASE_URL;

public class SettingsFragment extends Fragment {

    private PlantCareApi plantCareApi;
    private SharedPreferences sp;

    private Switch sw;
    private TextView userTextView;
    private TextView plantCounter;

    public SettingsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("Settings");

        sp = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        userTextView = view.findViewById(R.id.usernameTV);
        sw = view.findViewById(R.id.Notifications_switch);
        plantCounter = view.findViewById(R.id.plant_count);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        plantCareApi = retrofit.create(PlantCareApi.class);

        String token = "Bearer " + sp.getString("access_token", "DEFAULT VALUE ERR");
        Call<GetUser> call = plantCareApi.createUserGet(token);

        call.enqueue(new Callback<GetUser>() {
            @Override
            public void onResponse(Call<GetUser> call, Response<GetUser> response) {
                if (!response.isSuccessful()) { // If request is not successful
                    System.out.println("Response: neuspesno!");
                    Toast.makeText(getActivity().getApplicationContext(), "Could not connect to server.", Toast.LENGTH_SHORT).show();
                } else {
                    System.out.println("Response: uspešno!");
                    System.out.println(response.body().getUsername());

                    // Set username and switch as it apears in API
                    userTextView.setText(response.body().getUsername());
                    sw.setChecked(response.body().getNotifications());
                    plantCounter.setText("My plants: " + MainFragment.plantCounter);
                }
            }

            @Override
            public void onFailure(Call<GetUser> call, Throwable t) {
                System.out.println("No response: neuspešno!");
                System.out.println(t);
            }
        });
        return view;
    }
}