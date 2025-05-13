package com.kitakits.app;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class JobPosterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_poster);

        FloatingActionButton fabAddJob = findViewById(R.id.fabAddJob);
        RecyclerView jobsRecyclerView = findViewById(R.id.postedJobsRecyclerView);

        jobsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<JobModel> postedJobs = new ArrayList<>();
        postedJobs.add(new JobModel("Dishwasher Position", "CMU Market", "₱300/day", "Open"));
        postedJobs.add(new JobModel("Tutor Needed", "College Park", "₱250/session", "Filled"));

        jobsRecyclerView.setAdapter(new JobAdapter(postedJobs));

        fabAddJob.setOnClickListener(v -> startActivity(new Intent(this, CreateJobActivity.class)));
    }
}
