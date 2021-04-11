package com.mazej.plantcare.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mazej.plantcare.R;
import com.mazej.plantcare.database.PlantCareApi;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    private PlantCareApi plantCareApi;

    private TextView username;
    private TextView email;
    private TextView password;
    private Button signUpButton;

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

            }
        });
    }
}
