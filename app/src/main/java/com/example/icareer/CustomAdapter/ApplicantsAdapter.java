package com.example.icareer.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.icareer.Model.ApplicationClass;
import com.example.icareer.Model.JobVacancy;
import com.example.icareer.R;
import com.google.firebase.database.ValueEventListener;

import java.security.AccessControlContext;
import java.util.ArrayList;
import java.util.List;

public class ApplicantsAdapter extends RecyclerView.Adapter<ApplicantsAdapter.ViewHolder> {
    private List<ApplicationClass> applicantsList ;

    public ApplicantsAdapter( ArrayList<ApplicationClass> applicantsList) {
        this.applicantsList = applicantsList;
    }
    @Override
    public ApplicantsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_reycleview_applicants,parent,false);
        return new ApplicantsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ApplicationClass application = applicantsList.get(position);
        holder.applicantName.setText("Applicant Name : " +application.getApplication_name());
        holder.applicantEmail.setText("Email : "+application.getApplication_email());
        holder.applicantPhone.setText("Phone Number : "+application.getApplication_phone());
        holder.applicantSummary.setText("Summary : "+application.getApplication_summary());
        holder.applicantEducation.setText("Education : "+application.getApplication_education());
        holder.applicantSkills.setText("Skills : "+application.getApplication_skills());

        holder.approveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount()
    {
        return applicantsList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView applicantName , applicantEmail ,applicantPhone ,applicantSummary , applicantEducation ,applicantSkills;
        Button approveBtn;

        ViewHolder(View itemView) {
            super(itemView);
            applicantName = itemView.findViewById(R.id.display_applicant_name);
            applicantEmail = itemView.findViewById(R.id.display_applicant_email);
            applicantPhone = itemView.findViewById(R.id.display_applicant_phone);
            applicantSummary = itemView.findViewById(R.id.display_summary_applicant);
            applicantEducation = itemView.findViewById(R.id.display_education);
            applicantSkills = itemView.findViewById(R.id.display_skill);
            approveBtn = itemView.findViewById(R.id.approve);
        }
    }
}
