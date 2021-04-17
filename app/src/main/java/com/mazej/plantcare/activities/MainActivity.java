package com.mazej.plantcare.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.mazej.plantcare.R;
import com.mazej.plantcare.database.PlantCareApi;
import com.mazej.plantcare.database.PostUserPlant;
import com.mazej.plantcare.fragments.MainFragment;
import com.mazej.plantcare.fragments.MyPlantsFragment;
import com.mazej.plantcare.fragments.SearchPlantsFragment;
import com.mazej.plantcare.fragments.SettingsFragment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.mazej.plantcare.database.PlantCareApi.BASE_URL;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public static Menu myMenu;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    public static Toolbar toolbar;
    private NavigationView navigationView;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private PlantCareApi plantCareApi;
    private SharedPreferences sp;

    public static ArrayList<Integer> deleteList;
    public static ArrayList<Integer> addPlantsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);

        // Drawer
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        deleteList = new ArrayList<>();
        addPlantsList = new ArrayList<>();

        // Load default fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_fragment, new MainFragment());
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.drawer_menu, menu);
        myMenu = menu;
        hideButtons();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { // Handles toolbar buttons
        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        plantCareApi = retrofit.create(PlantCareApi.class);
        String token = "Bearer " + sp.getString("access_token","DEFAULT VALUE ERR");

        switch(item.getItemId()) {
            case R.id.delete_plants_btn:
                // Tukaj izbrisemo rastline iz deleteLista, na katerega se dodajo rastline iz MyPlantsFragment

                for(int i = 0; i < deleteList.size(); i++) {
                    System.out.println(deleteList.get(i));

                    Call<Void> call = plantCareApi.createUserPlantDelete(token, deleteList.get(i));

                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (!response.isSuccessful()) { // Če request ni uspešen
                                System.out.println("Response: DeleteUserPlant  neuspesno!");
                                System.out.println(response.raw().toString());

                            } else {
                                System.out.println("Response:DeleteUserPlant uspešno!");
                                fragmentManager = getSupportFragmentManager();
                                fragmentTransaction = fragmentManager.beginTransaction();
                                myMenu.findItem(R.id.delete_plants_btn).setVisible(true);
                                fragmentTransaction.replace(R.id.container_fragment, new MyPlantsFragment());
                                myMenu.findItem(R.id.delete_plants_btn).setVisible(true);
                                fragmentTransaction.commit();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            System.out.println("No response: DeleteUserPlant  neuspešno!");
                            System.out.println(t);
                        }
                    });

                }
                deleteList.clear();
                break;
            case R.id.add_plants_btn:
                // Tukaj posljemo na API rastline, ki smo jih izbrali v SearchPlantsFragment in se nahajajo v myPlantsList

                for(int i = 0; i < addPlantsList.size(); i++) {
                    System.out.println("parameter:"+ addPlantsList.get(i).toString());
                    //API klic

                    Call<PostUserPlant> call = plantCareApi.createUserPlantPost(token,addPlantsList.get(i));

                    call.enqueue(new Callback<PostUserPlant>() {
                        @Override
                        public void onResponse(Call<PostUserPlant> call, Response<PostUserPlant> response) {
                            if (!response.isSuccessful()) { // Če request ni uspešen
                                System.out.println("Response: PostUserPlant  neuspesno!");
                                System.out.println(response.raw().toString());
                            } else {
                                System.out.println("Response: PostUserPlant uspešno!");
                                fragmentManager = getSupportFragmentManager();
                                fragmentTransaction = fragmentManager.beginTransaction();
                                myMenu.findItem(R.id.add_plants_btn).setVisible(true);
                                fragmentTransaction.replace(R.id.container_fragment, new SearchPlantsFragment());
                                fragmentTransaction.commit();
                            }
                        }

                        @Override
                        public void onFailure(Call<PostUserPlant> call, Throwable t) {
                            System.out.println("No response: PostUserPlant  neuspešno!");
                            System.out.println(t);
                        }
                    });
                }

                addPlantsList.clear();

                break;
            default:
                return super.onOptionsItemSelected(item);
        }
            return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) { // Handles side nav buttons

        hideButtons();
        drawerLayout.closeDrawer(GravityCompat.START);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        if(menuItem.getItemId() == R.id.home){
            fragmentTransaction.replace(R.id.container_fragment, new MainFragment());
        }
        if(menuItem.getItemId() == R.id.my_plants){
            myMenu.findItem(R.id.delete_plants_btn).setVisible(true);
            fragmentTransaction.replace(R.id.container_fragment, new MyPlantsFragment());
        }
        if(menuItem.getItemId() == R.id.search_plants){
            myMenu.findItem(R.id.add_plants_btn).setVisible(true);
            fragmentTransaction.replace(R.id.container_fragment, new SearchPlantsFragment());
        }
        if(menuItem.getItemId() == R.id.settings){
            fragmentTransaction.replace(R.id.container_fragment, new SettingsFragment());
        }
        fragmentTransaction.commit();
        return true;
    }

    public static void hideButtons(){ //hides all the toolbar buttons
        myMenu.findItem(R.id.general).setVisible(false);
        myMenu.findItem(R.id.other).setVisible(false);
        myMenu.findItem(R.id.delete_plants_btn).setVisible(false);
        myMenu.findItem(R.id.add_plants_btn).setVisible(false);
    }
}
