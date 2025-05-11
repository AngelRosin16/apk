package com.kitakits.app;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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

        recyclerView = findViewById(R.id.jobRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        jobList = new ArrayList<>();
        jobList.add(new JobModel("Dishwasher", "Marta A. Maritsa", "₱300 (Daily)", "CMU Market"));
        jobList.add(new JobModel("Math Tutor", "Lorna Erica", "₱250 (each Session)", "College Park"));
        jobList.add(new JobModel("Waiter", "Sungkah X Sisiq", "₱350 (Daily)", "CMU Market"));
        jobList.add(new JobModel("Delivery Man", "D & J Pater", "₱350 (Daily)", "CMU Market"));

        adapter = new JobAdapter(jobList);
        recyclerView.setAdapter(adapter);
    }
}
