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

import static com.mazej.plantcare.activities.MainActivity.toolbar;

public class SearchPlantsFragment extends Fragment {

    public SearchPlantsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search_plants, container, false);

        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("Search Plants");

        return view;
    }
}