package com.mazej.plantcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.database.Cursor;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.mazej.plantcare.fragments.MainFragment;
import com.mazej.plantcare.fragments.MyPlantsFragment;
import com.mazej.plantcare.fragments.SearchPlantsFragment;
import com.mazej.plantcare.fragments.SettingsFragment;
import com.mazej.plantcare.fragments.SingInFragment;
import com.mazej.plantcare.fragments.SingUpFragment;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public static Menu myMenu;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    public static Toolbar toolbar;
    private NavigationView navigationView;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    public static ArrayList<Integer> deleteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

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
        //hideButtons();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { // Handles toolbar buttons

        /*switch(item.getItemId()) {
            case R.id.add_income_btn:
                break;
            case R.id.add_account_btn:
                break;
            default:
                return super.onOptionsItemSelected(item);
        }*/
            return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) { // Handles side nav buttons

        drawerLayout.closeDrawer(GravityCompat.START);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        if(menuItem.getItemId() == R.id.home){
            fragmentTransaction.replace(R.id.container_fragment, new MainFragment());
        }
        if(menuItem.getItemId() == R.id.my_plants){
            fragmentTransaction.replace(R.id.container_fragment, new MyPlantsFragment());
        }
        if(menuItem.getItemId() == R.id.search_plants){
            fragmentTransaction.replace(R.id.container_fragment, new SearchPlantsFragment());
        }
        if(menuItem.getItemId() == R.id.settings){
            fragmentTransaction.replace(R.id.container_fragment, new SettingsFragment());
        }
        if(menuItem.getItemId() == R.id.sing_in){
            fragmentTransaction.replace(R.id.container_fragment, new SingInFragment());
        }
        if(menuItem.getItemId() == R.id.sing_up){
            fragmentTransaction.replace(R.id.container_fragment, new SingUpFragment());
        }
        fragmentTransaction.commit();
        return true;
    }
}
