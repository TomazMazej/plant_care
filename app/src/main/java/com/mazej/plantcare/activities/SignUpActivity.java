package com.mazej.plantcare.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mazej.plantcare.R;
import com.mazej.plantcare.database.PlantCareApi;
import com.mazej.plantcare.database.PostLogIn;
import com.mazej.plantcare.database.PostSignUp;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity {

    private PlantCareApi plantCareApi;

    private TextView username;
    private TextView email;
    private TextView password;
    private Button signUpButton;

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = findViewById(R.id.sign_up_username);
        email = findViewById(R.id.sign_up_mail);
        password = findViewById(R.id.sign_up_password);
        signUpButton = findViewById(R.id.sign_up_btn);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://172.17.224.1:3000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                plantCareApi = retrofit.create(PlantCareApi.class);
                // Call<PostSignIn> call = plantCareApi.createLogInPost(username.getText().toString(), email.getText().toString(), password.getText().toString());
                Call<PostSignUp> call = plantCareApi.createSignUpPost("test1", "info1@pla-care.com", "PlantCare2021!", "");

                call.enqueue(new Callback<PostSignUp>() {
                    @Override
                    public void onResponse(Call<PostSignUp> call, Response<PostSignUp> response) {
                        if (!response.isSuccessful()){ //če request ni uspešen
                            System.out.println("Response: neuspesno!");
                            //errorText.setText("Wrong email or password!");
                        }
                        else{
                            System.out.println("Response: uspešno!");
                            System.out.println(response.body().getToken());
                            // Save access_token to shared preferences
                            sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("access_token", response.body().getToken());
                            editor.apply();

                            Intent a = new Intent(getApplicationContext(), MainActivity.class);
                            a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(a);
                        }
                    }

                    @Override
                    public void onFailure(Call<PostSignUp> call, Throwable t) {
                        System.out.println("No response: neuspešno!");
                        System.out.println(t);
                        //errorText.setText("Failed to connect to server!");
                        /*Intent a = new Intent(getApplicationContext(), MainActivity.class);
                        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(a);*/
                    }
                });
            }
        });
    }
}
