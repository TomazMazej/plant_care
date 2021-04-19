package com.mazej.plantcare.fragments;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mazej.plantcare.R;
import com.mazej.plantcare.activities.MainActivity;
import com.mazej.plantcare.database.GetUserPlant;
import com.mazej.plantcare.database.PlantCareApi;
import com.mazej.plantcare.database.PutUserPlant;
import com.mazej.plantcare.objects.MyPlant;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.mazej.plantcare.activities.MainActivity.toolbar;
import static com.mazej.plantcare.database.PlantCareApi.BASE_URL;

public class PlantFragment extends Fragment {

    private MyPlant plant;

    private ImageView plantImage;
    private TextView info;
    private TextView care;
    private TextView need_water;
    private Button water_plant;

    private PlantCareApi plantCareApi;
    private SharedPreferences sp;

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
        need_water = view.findViewById(R.id.need_water_text);
        water_plant = view.findViewById(R.id.water_plant_button);

        info.setText(plant.getInfo());
        care.setText(plant.getCare());

        if(plant.getRemaining_water_days() != null){
            need_water.setVisibility(View.VISIBLE);
            if(plant.getRemaining_water_days() == 0)
                need_water.setText("Needs water today!");
            else if(plant.getRemaining_water_days() == 1)
                need_water.setText("Needs water tommorow!");
            else
                need_water.setText("Needs water in " + plant.getRemaining_water_days() + " days!");

            water_plant.setVisibility(View.VISIBLE);
            water_plant.setEnabled(true);
            sp = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

            water_plant.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // API call, to water current plant
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    String token = "Bearer " + sp.getString("access_token","DEFAULT VALUE ERR");
                    plantCareApi = retrofit.create(PlantCareApi.class);
                    Call<PutUserPlant> call = plantCareApi.createUserPlantPut(token, plant.getApiPlantId());

                    call.enqueue(new Callback<PutUserPlant>() {
                        @Override
                        public void onResponse(Call<PutUserPlant> call, Response<PutUserPlant> response) {
                            if (!response.isSuccessful()){ // If request is not successful
                                System.out.println("Response: PutUserPlant neuspesno!");
                                Toast.makeText(getActivity().getApplicationContext(),"Could not connect to server.", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                System.out.println("Response: PutUserPlant uspešno!");
                                // TODO get actual plant name
                                int imageResource = getResources().getIdentifier("@mipmap/cactus", null, getActivity().getPackageName());
                                MyPlant temp = new MyPlant("" + response.body().getPlant().getId(), "" + imageResource, response.body().getPlant().getName(), response.body().getPlant().getDays_water(), response.body().getPlant().getInfo(), response.body().getPlant().getCare(), response.body().getId(),response.body().getLast_water_day(),response.body().getRemaining_water_days());
                                plant.setLast_water_date(temp.getLast_water_date());
                                plant.setRemaining_water_days(temp.getRemaining_water_days());

                                if(temp.getRemaining_water_days() == 0)
                                    need_water.setText("Needs water today!");
                                else if(temp.getRemaining_water_days() == 1)
                                    need_water.setText("Needs water tommorow!");
                                else
                                    need_water.setText("Needs water in " + temp.getRemaining_water_days() + " days!");
                            }
                        }

                        @Override
                        public void onFailure(Call<PutUserPlant> call, Throwable t) {
                            System.out.println("No response: PutUserPlant neuspešno!");
                            System.out.println(t);
                        }
                    });
                }
            });
        }
        return view;
    }
}