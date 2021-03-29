package com.mazej.plantcare.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mazej.plantcare.R;
import com.mazej.plantcare.adapters.MyPlantsAdapter;
import com.mazej.plantcare.objects.MyPlant;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static com.mazej.plantcare.MainActivity.toolbar;

public class MyPlantsFragment extends Fragment {

    public static ListView myPlantsList;
    public static ArrayList<MyPlant> theList;
    public static MyPlantsAdapter arrayAdapter;

    public MyPlantsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_plants, container, false);

        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("My Plants");

        theList = new ArrayList<>();
        myPlantsList = view.findViewById(R.id.myPlantsList);

        arrayAdapter = new MyPlantsAdapter(getActivity().getBaseContext(), R.layout.adapter_my_plants, theList);
        myPlantsList.setAdapter(arrayAdapter);

        // Add plants to list
        // Test insert... later we get data from database and add to list with loop
        MyPlant plant = new MyPlant("0", "cactus", "Kaktus", "Potegn mi ga");
        theList.add(plant);
        return view;
    }
}
