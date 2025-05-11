package com.kitakits.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.ViewHolder> {
    List<JobModel> jobList;

    public JobAdapter(List<JobModel> jobList) {
        this.jobList = jobList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_job, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        JobModel job = jobList.get(position);
        holder.title.setText(job.getTitle());
        holder.poster.setText(job.getPoster());
        holder.pay.setText(job.getPay());
        holder.location.setText(job.getLocation());
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, poster, pay, location;

        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.jobTitle);
            poster = view.findViewById(R.id.jobPoster);
            pay = view.findViewById(R.id.jobPay);
            location = view.findViewById(R.id.jobLocation);
        }
    }
}
