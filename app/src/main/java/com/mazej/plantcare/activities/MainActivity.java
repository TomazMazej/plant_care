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

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.mazej.plantcare.R;
import com.mazej.plantcare.database.PlantCareApi;
import com.mazej.plantcare.database.PostUserPlant;
import com.mazej.plantcare.fragments.MainFragment;
import com.mazej.plantcare.fragments.MyPlantsFragment;
import com.mazej.plantcare.fragments.SearchPlantsFragment;
import com.mazej.plantcare.fragments.SettingsFragment;
import com.mazej.plantcare.notifications.NotificationService;

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
        createNotificationsChanel();

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
                for(int i = 0; i < deleteList.size(); i++) {
                    Call<Void> call = plantCareApi.createUserPlantDelete(token, deleteList.get(i));

                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (!response.isSuccessful()) { // If request is not successful
                                System.out.println("Response: DeleteUserPlant  neuspesno!");
                                Toast.makeText(getApplicationContext(),"Could not connect to server.", Toast.LENGTH_SHORT).show();
                            } else {
                                System.out.println("Response:DeleteUserPlant uspešno!");
                                fragmentManager = getSupportFragmentManager();
                                fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.container_fragment, new MainFragment());
                                fragmentTransaction.commit();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            System.out.println("No response: DeleteUserPlant  neuspešno!");
                            System.out.println(t);
                            Toast.makeText(getApplicationContext(),"Could not connect to server.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                deleteList.clear();
                break;
            case R.id.add_plants_btn:
                for(int i = 0; i < addPlantsList.size(); i++) {
                    Call<PostUserPlant> call = plantCareApi.createUserPlantPost(token,addPlantsList.get(i));

                    call.enqueue(new Callback<PostUserPlant>() {
                        @Override
                        public void onResponse(Call<PostUserPlant> call, Response<PostUserPlant> response) {
                            if (!response.isSuccessful()) { // If request is not successful
                                System.out.println("Response: PostUserPlant  neuspesno!");
                                Toast.makeText(getApplicationContext(),"Could not connect to server.", Toast.LENGTH_SHORT).show();
                            } else {
                                System.out.println("Response: PostUserPlant uspešno!");
                                fragmentManager = getSupportFragmentManager();
                                fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.container_fragment, new MainFragment());
                                fragmentTransaction.commit();
                            }
                        }

                        @Override
                        public void onFailure(Call<PostUserPlant> call, Throwable t) {
                            System.out.println("No response: PostUserPlant  neuspešno!");
                            System.out.println(t);
                            Toast.makeText(getApplicationContext(),"Could not connect to server.", Toast.LENGTH_SHORT).show();
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

    public static void hideButtons(){ // Hides all the toolbar buttons
        myMenu.findItem(R.id.general).setVisible(false);
        myMenu.findItem(R.id.other).setVisible(false);
        myMenu.findItem(R.id.delete_plants_btn).setVisible(false);
        myMenu.findItem(R.id.add_plants_btn).setVisible(false);
    }

    private void createNotificationsChanel(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = "notificationsChanel";
            String description = "Chanel to notify when we need to water our plants.";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel chanel = new NotificationChannel("id", name, importance);
            chanel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(chanel);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        startService(new Intent(this, NotificationService.class));
    }
}
