package com.mazej.plantcare.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.mazej.plantcare.R;
import com.mazej.plantcare.database.PlantCareApi;
import com.mazej.plantcare.database.PostSignIn;

import static com.mazej.plantcare.MainActivity.toolbar;

public class LogInFragment extends Fragment {

    private PlantCareApi plantCareApi;

    private TextView email;
    private TextView password;
    private Button loginButton;

    public LogInFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_log_in, container, false);

        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("Login");

        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        loginButton = view.findViewById(R.id.buttonLogin);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://192.168.0.23:3000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                plantCareApi = retrofit.create(PlantCareApi.class);
                // Call<PostSignIn> call = plantCareApi.createLogInPost(email.getText().toString(), password.getText().toString());
                Call<PostSignIn> call = plantCareApi.createLogInPost("info@plant-care.com", "PlantCare2021!", "");

                call.enqueue(new Callback<PostSignIn>() {
                    @Override
                    public void onResponse(Call<PostSignIn> call, Response<PostSignIn> response) {
                        if (!response.isSuccessful()){ //če request ni uspešen
                            System.out.println("Response: neuspesno!");
                        }
                        else{
                            System.out.println("Response: uspešno!");
                            System.out.println(response.body().getToken());
                        }
                    }

                    @Override
                    public void onFailure(Call<PostSignIn> call, Throwable t) {
                        System.out.println("No response: neuspešno!");
                        System.out.println(t);
                    }
                });
            }
        });

        return view;
    }
}
