package com.maxpallu.go4lunch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.maxpallu.go4lunch.api.UserHelper;
import com.maxpallu.go4lunch.models.PlaceAutocomplete;
import com.maxpallu.go4lunch.models.PlaceDetailsResponse;
import com.maxpallu.go4lunch.models.PredictionsItem;
import com.maxpallu.go4lunch.models.Restaurants;
import com.maxpallu.go4lunch.util.ApiCalls;
import com.maxpallu.go4lunch.views.MyAdapter;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ApiCalls.Callbacks {
    private DrawerLayout drawer;
    private SearchView searchView;
    private static final int AUTOCOMPLETE_REQUEST_CODE = 1;
    private MyAdapter mAdapter;
    private RecyclerView recyclerView;
    private TextView profileName;
    private TextView profileEmail;
    private ImageView profileAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //toolbar.inflateMenu(R.menu.menu);

        recyclerView = findViewById(R.id.list_recycler_view);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerLayout = navigationView.getHeaderView(0);
        profileName = headerLayout.findViewById(R.id.profile_name);
        profileEmail = headerLayout.findViewById(R.id.profile_email);
        profileAvatar = headerLayout.findViewById(R.id.profile_avatar);

        profileName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        profileEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        Glide.with(this.getApplicationContext()).load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()).apply(RequestOptions.centerCropTransform()).into(profileAvatar);

        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MapFragment()).commit();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Intent intent = new Intent(this, DetailActivity.class);
                intent.putExtra("resto_place_id", place.getId());
                startActivity(intent);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;

            switch (menuItem.getItemId()) {
                case R.id.action_map:
                    selectedFragment = new MapFragment();
                    break;
                case R.id.action_list:
                    selectedFragment = new ListFragment();
                    break;
                case R.id.action_workmates:
                    selectedFragment = new WorkmatesFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

            return true;
        }
    };

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_lunch:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LunchFragment()).commit();
                break;
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingsFragment()).commit();
                break;
            case R.id.nav_disconnect:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onDetailsResponse(PlaceDetailsResponse placeDetailsResponse) {

    }

    @Override
    public void onResponse(@Nullable Restaurants restaurants) {

    }

    @Override
    public void onAutocompleteResponse(PlaceAutocomplete placeAutocompleteResponse) {

    }


    @Override
    public void onFailure() {

    }
}