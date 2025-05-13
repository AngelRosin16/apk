package com.kitakits.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;  // Import MenuItem
import androidx.annotation.NonNull;  // Import NonNull
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    JobAdapter adapter;
    List<JobModel> jobList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Existing job list setup
        recyclerView = findViewById(R.id.jobRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        jobList = new ArrayList<>();
        jobList.add(new JobModel("Dishwasher", "Marta A. Maritsa", "₱300 (Daily)", "CMU Market"));
        jobList.add(new JobModel("Math Tutor", "Lorna Erica", "₱250 (each Session)", "College Park"));
        jobList.add(new JobModel("Waiter", "Sungkah X Sisiq", "₱350 (Daily)", "CMU Market"));
        jobList.add(new JobModel("Delivery Man", "D & J Pater", "₱350 (Daily)", "CMU Market"));

        adapter = new JobAdapter(jobList);
        recyclerView.setAdapter(adapter);

        // New navigation setup
        setupBottomNavigation();
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        return true;
                    case R.id.nav_saved:
                        startActivity(new Intent(MainActivity.this, SavedJobsActivity.class));
                        return true;
                    case R.id.nav_profile:
                        startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                        return true;
                    case R.id.nav_notifications:
                        startActivity(new Intent(MainActivity.this, NotificationsActivity.class));
                        return true;
                }
                return false;
            }
        });
    }
}
