package com.mazej.plantcare.fragments;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mazej.plantcare.R;
import com.mazej.plantcare.activities.MainActivity;
import com.mazej.plantcare.adapters.MyPlantsAdapter;
import com.mazej.plantcare.adapters.SearchPlantsAdapter;
import com.mazej.plantcare.database.GetPlant;
import com.mazej.plantcare.database.PlantCareApi;
import com.mazej.plantcare.database.PostLogIn;
import com.mazej.plantcare.objects.MyPlant;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.mazej.plantcare.activities.MainActivity.toolbar;
import static com.mazej.plantcare.database.PlantCareApi.BASE_URL;

public class SearchPlantsFragment extends Fragment implements SearchView.OnQueryTextListener {

    private PlantCareApi plantCareApi;
    private SharedPreferences sp;

    private SearchView searchView;
    private ListView searchPlantsList;
    private ArrayList<MyPlant> theList;
    private SearchPlantsAdapter arrayAdapter;

    public SearchPlantsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search_plants, container, false);

        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("Search Plants");

        MainActivity.myMenu.findItem(R.id.add_plants_btn).setVisible(true);

        searchPlantsList = view.findViewById(R.id.myPlantsList);
        searchView = view.findViewById(R.id.searchView);

        sp = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        theList = new ArrayList<>();
        arrayAdapter = new SearchPlantsAdapter(getActivity().getBaseContext(), R.layout.adapter_search_plants, theList);
        searchPlantsList.setAdapter(arrayAdapter);
        searchPlantsList.setTextFilterEnabled(true);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String token = "Bearer " + sp.getString("access_token", "DEFAULT VALUE ERR");
        plantCareApi = retrofit.create(PlantCareApi.class);
        Call<List<GetPlant>> call = plantCareApi.createPlantGet(token);

        call.enqueue(new Callback<List<GetPlant>>() {
            @Override
            public void onResponse(Call<List<GetPlant>> call, Response<List<GetPlant>> response) {
                if (!response.isSuccessful()) { // ??e request ni uspe??en
                    System.out.println("Response: neuspesno!");
                } else {
                    System.out.println("Response: uspe??no!");
                    // Dodamo rastline na listo
                    for (int i = 0; i < response.body().size(); i++) {
                        int imageResource = getResources().getIdentifier("@mipmap/" + response.body().get(i).getImage_path(), null, getActivity().getPackageName());
                        MyPlant plant = new MyPlant("" + response.body().get(i).getId(), "" + imageResource, response.body().get(i).getName(), response.body().get(i).getDays_water(), response.body().get(i).getInfo(), response.body().get(i).getCare());
                        plant.setRemaining_water_days(null);
                        theList.add(plant);
                    }
                    arrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<GetPlant>> call, Throwable t) {
                System.out.println("No response: neuspe??no!");
                System.out.println(t);
            }
        });

        searchPlantsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_fragment, new PlantFragment(theList.get(position)), "findThisFragment").addToBackStack(null).commit();
            }
        });
        setupSearchView();
        return view;
    }

    private void setupSearchView() {
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("Search Here");
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        if (TextUtils.isEmpty(newText)) {
            searchPlantsList.clearTextFilter();
        } else {
            searchPlantsList.setFilterText(newText);
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
}