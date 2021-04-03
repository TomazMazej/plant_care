package com.mazej.plantcare.fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mazej.plantcare.R;
import com.mazej.plantcare.adapters.MyPlantsAdapter;
import com.mazej.plantcare.objects.MyPlant;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static com.mazej.plantcare.MainActivity.toolbar;

public class MyPlantsFragment extends Fragment {

    private static ListView myPlantsList;
    private static ArrayList<MyPlant> theList;
    private static MyPlantsAdapter arrayAdapter;

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
        MyPlant plant = new MyPlant("0", "cactus", "Kaktus", 5, "Potegn mi ga", "2x");
        theList.add(plant);

        myPlantsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),"WTF",1).show();
            }
        });

        return view;
    }
}
