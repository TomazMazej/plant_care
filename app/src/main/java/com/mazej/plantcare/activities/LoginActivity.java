package com.mazej.plantcare.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mazej.plantcare.R;
import com.mazej.plantcare.database.PlantCareApi;
import com.mazej.plantcare.database.PostLogIn;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.mazej.plantcare.database.PlantCareApi.BASE_URL;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private TextView errorText;
    private Button loginButton;
    private Button signUpButton;
    public CheckBox hidePaswordCheckbox;

    private SharedPreferences sp;
    private PlantCareApi plantCareApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        email = findViewById(R.id.login_mail);
        password = findViewById(R.id.login_password);
        errorText = findViewById(R.id.error_text);
        loginButton = findViewById(R.id.buttonLogin);
        signUpButton = findViewById(R.id.sign_up_button);
        hidePaswordCheckbox = findViewById(R.id.hide_password_checkbox);

        // Shows or hides password
        hidePaswordCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                if (arg1) {
                    password.setTransformationMethod(null);
                } else {
                    password.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                plantCareApi = retrofit.create(PlantCareApi.class);
                // TODO uncomment this
                // Call<PostSignIn> call = plantCareApi.createLogInPost(email.getText().toString(), password.getText().toString());
                Call<PostLogIn> call = plantCareApi.createLogInPost("info@plant-care.com", "PlantCare2021!", "");

                call.enqueue(new Callback<PostLogIn>() {
                    @Override
                    public void onResponse(Call<PostLogIn> call, Response<PostLogIn> response) {
                        if (!response.isSuccessful()) { // If request is not successful
                            System.out.println("Response: neuspesno!");
                            errorText.setText("Wrong email or password!");
                        } else {
                            System.out.println("Response: Login uspešno!");
                            // Save access_token to shared preferences
                            sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("access_token", response.body().getToken());
                            editor.apply();

                            // Go to MainActivity
                            Intent a = new Intent(getApplicationContext(), MainActivity.class);
                            a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(a);
                        }
                    }

                    @Override
                    public void onFailure(Call<PostLogIn> call, Throwable t) {
                        System.out.println("No response: neuspešno!");
                        errorText.setText("Failed to connect to the server!");
                        // TODO delete this
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
