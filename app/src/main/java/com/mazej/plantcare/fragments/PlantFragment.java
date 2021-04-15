package com.mazej.plantcare.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mazej.plantcare.R;
import com.mazej.plantcare.activities.MainActivity;
import com.mazej.plantcare.objects.MyPlant;

import static com.mazej.plantcare.activities.MainActivity.toolbar;

public class PlantFragment extends Fragment {

    private MyPlant plant;

    private ImageView plantImage;
    private TextView info;
    private TextView care;

    public PlantFragment(MyPlant plant) {
        this.plant = plant;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_plant, container, false);

        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(plant.getName());
        MainActivity.hideButtons();

        plantImage = view.findViewById(R.id.plant_image);
        info = view.findViewById(R.id.info_text);
        care = view.findViewById(R.id.care_text);

        info.setText(plant.getInfo());
        care.setText(plant.getCare());

        return view;
    }
}