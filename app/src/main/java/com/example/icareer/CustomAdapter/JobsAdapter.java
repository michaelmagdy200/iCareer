package com.example.icareer.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.icareer.Model.JobVacancy;
import com.example.icareer.R;
import com.example.icareer.jobSeeker.Dialogs.ApplicationDailog;

import java.util.List;

public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.ViewHolder>{
    private List<JobVacancy> jobList;
    Context context ;

    public JobsAdapter(List<JobVacancy> jobList , Context context) {
        this.jobList = jobList;
        this.context = context ;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_display_jobs,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JobVacancy job = jobList.get(position);
        holder.companyName.setText("Company Name : " +job.getCompanyName());
        holder.display_jobTitle.setText("Title : "+job.getJobTitle());
        holder.display_jobDescription.setText("Description about Jobs : "+job.getJobDescription());
        holder.display_jobDate.setText("Date : "+job.getJobDate());
        holder.display_jobSalary.setText("Salary : "+job.getJobSalary());
        holder.display_noOfVancany.setText("Number of Vacancy : "+job.getNumberOfVacancy());

        holder.applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationDailog dailog = new ApplicationDailog();
                dailog.show(((AppCompatActivity)context).getSupportFragmentManager(), "Fragment");
            }
        });
    }


    @Override
    public int getItemCount() {
        return jobList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView companyName , display_jobTitle ,display_jobDescription ,display_jobDate , display_jobSalary ,display_noOfVancany;
        Button applyBtn;

        ViewHolder(View itemView) {
            super(itemView);
            companyName = itemView.findViewById(R.id.display_company_name);
            display_jobTitle = itemView.findViewById(R.id.display_title_jobs);
            display_jobDescription = itemView.findViewById(R.id.display_description);
            display_jobDate = itemView.findViewById(R.id.display_date);
            display_jobSalary = itemView.findViewById(R.id.display_salary);
            display_noOfVancany = itemView.findViewById(R.id.display_number_of_vacancy);
            applyBtn = itemView.findViewById(R.id.apply_jobs);

        }

    }

}
