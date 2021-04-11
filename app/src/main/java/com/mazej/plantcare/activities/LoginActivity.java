package com.mazej.plantcare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mazej.plantcare.R;
import com.mazej.plantcare.database.PlantCareApi;
import com.mazej.plantcare.database.PostSignIn;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private PlantCareApi plantCareApi;

    private TextView email;
    private TextView password;
    private TextView errorText;
    private Button loginButton;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        email = findViewById(R.id.login_mail);
        password = findViewById(R.id.login_password);
        errorText = findViewById(R.id.error_text);
        loginButton = findViewById(R.id.buttonLogin);
        signUpButton = findViewById(R.id.sign_up_button);

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
                            errorText.setText("Wrong email or password!");
                        }
                        else{
                            System.out.println("Response: uspešno!");
                            System.out.println(response.body().getToken());
                            /*Intent a = new Intent(getApplicationContext(), MainActivity.class);
                            a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(a);*/
                        }
                    }

                    @Override
                    public void onFailure(Call<PostSignIn> call, Throwable t) {
                        System.out.println("No response: neuspešno!");
                        System.out.println(t);
                        errorText.setText("Failed to connect to server!");
                        Intent a = new Intent(getApplicationContext(), MainActivity.class);
                        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(a);
                    }
                });
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(getApplicationContext(), SignUpActivity.class);
                a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(a);
            }
        });
    }
}
