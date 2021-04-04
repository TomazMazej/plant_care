package com.mazej.plantcare.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mazej.plantcare.R;

import static com.mazej.plantcare.MainActivity.toolbar;

public class SingUpFragment extends Fragment {

    public SingUpFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sing_up, container, false);

        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("Sing Up");

        return view;
    }
}
