package com.mazej.plantcare.fragments;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mazej.plantcare.R;
import com.mazej.plantcare.adapters.MyPlantsAdapter;
import com.mazej.plantcare.database.GetPlant;
import com.mazej.plantcare.database.GetUserPlant;
import com.mazej.plantcare.database.PlantCareApi;
import com.mazej.plantcare.objects.MyPlant;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.mazej.plantcare.activities.MainActivity.toolbar;
import static com.mazej.plantcare.database.PlantCareApi.BASE_URL;

public class MyPlantsFragment extends Fragment {

    private PlantCareApi plantCareApi;
    private SharedPreferences sp;

    private ListView myPlantsList;
    private ArrayList<MyPlant> theList;
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

        sp = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        // Add plants to list
        // Test insert... later we get data from database and add to list with loop
        //MyPlant plant = new MyPlant("0", "cactus", "Kaktus", 5, "Potegn mi ga", "2x");
        //theList.add(plant);

        myPlantsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_fragment, new PlantFragment(theList.get(position)), "findThisFragment").addToBackStack(null).commit();

            }
        });

        //Get all plants from current user
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String token = "Bearer " + sp.getString("access_token","DEFAULT VALUE ERR");
        plantCareApi = retrofit.create(PlantCareApi.class);
        Call<List<GetUserPlant>> call = plantCareApi.createUserPlantGet(token);

        call.enqueue(new Callback<List<GetUserPlant>>() {
            @Override
            public void onResponse(Call<List<GetUserPlant>> call, Response<List<GetUserPlant>> response) {
                if (!response.isSuccessful()){ // Če request ni uspešen
                    System.out.println("Response: GetUserPlant neuspesno!");
                }
                else{
                    System.out.println("Response: GetUserPlant uspešno!");
                    // Dodamo rastline na listo

                    for(int i = 0; i< response.body().size(); i++ ){

                        int imageResource = getResources().getIdentifier("@mipmap/cactus", null, getActivity().getPackageName());
                        //int imageResource = getResources().getIdentifier("@mipmap/" + response.body().get(i).getImage_path(), null, getActivity().getPackageName());
                        //MyPlant plant = new MyPlant("" + i, "" + imageResource, response.body().get(i).getName(), response.body().get(i).getDays_water(), response.body().get(i).getInfo(), response.body().get(i).getCare());

                        MyPlant plant = new MyPlant("" + response.body().get(i).getPlant().getId(), "" + imageResource, response.body().get(i).getPlant().getName(), response.body().get(i).getPlant().getDays_water(), response.body().get(i).getPlant().getInfo(), response.body().get(i).getPlant().getCare(), response.body().get(i).getId(),response.body().get(i).getLast_water_day(),response.body().get(i).getRemaining_water_days());
                        theList.add(plant);
                    }
                    arrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<GetUserPlant>> call, Throwable t) {
                System.out.println("No response: GetUserPlant neuspešno!");
                System.out.println(t);
            }
        });

        return view;
    }
}
