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

import static com.mazej.plantcare.database.PlantCareApi.BASE_URL;

public class SignUpActivity extends AppCompatActivity {

    private TextView username;
    private TextView email;
    private TextView password;
    private TextView password2;
    private TextView errorText;
    private Button signUpButton;

    private PlantCareApi plantCareApi;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = findViewById(R.id.sign_up_username);
        email = findViewById(R.id.sign_up_mail);
        password = findViewById(R.id.sign_up_password);
        password2 = findViewById(R.id.sign_up_password2);
        errorText = findViewById(R.id.error_text);
        signUpButton = findViewById(R.id.sign_up_btn);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                plantCareApi = retrofit.create(PlantCareApi.class);

                // If passwords match
                if(password.getText().toString().equals(password2.getText().toString())){
                    Call<PostSignUp> call = plantCareApi.createSignUpPost(username.getText().toString(), email.getText().toString(), password.getText().toString(), "");

                    call.enqueue(new Callback<PostSignUp>() {
                        @Override
                        public void onResponse(Call<PostSignUp> call, Response<PostSignUp> response) {
                            if (!response.isSuccessful()){ // If request is not successful
                                System.out.println("Response: neuspesno!");
                            }
                            else{
                                System.out.println("Response: uspešno!");
                                // Save access token to Shared Preferences
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
                            errorText.setText("Failed to connect to server!");
                        }
                    });
                }
                else{
                    errorText.setText("Passwords do not match!");
                }
            }
        });
    }
}
