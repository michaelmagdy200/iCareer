package com.example.icareer.jobSeeker.ui.Jobs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.icareer.Model.JobVacancy;
import com.example.icareer.CustomAdapter.JobsAdapter;
import com.example.icareer.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class JobsFragment extends Fragment {


    RecyclerView job_recycleView ;
    DatabaseReference jobReferences , jobRef_2 , jobRef_3;
    JobsAdapter jobsAdapter;
    ArrayList<JobVacancy> displayJobsList ;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_jobs, container, false);

        job_recycleView =root.findViewById(R.id.jobs_jobSeeker_recycleView);
        job_recycleView.setHasFixedSize(true);
        displayJobsList =new ArrayList<>();
        job_recycleView.setLayoutManager(new LinearLayoutManager(getContext()));


        jobReferences = FirebaseDatabase.getInstance().getReference("Company");
        jobReferences.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String id = dataSnapshot.child("uid").getValue().toString();
                    jobRef_2 = FirebaseDatabase.getInstance().getReference("Company").child(id).child("Job Information");
                    jobRef_2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                String jobId = dataSnapshot.child("id").getValue().toString();
                                jobRef_3 = FirebaseDatabase.getInstance().getReference("Company").child(id).child("Job Information").child(jobId);
                                jobRef_3.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        JobVacancy display = snapshot.getValue(JobVacancy.class);
                                        displayJobsList.add(display);

                                        jobsAdapter = new JobsAdapter(displayJobsList, getContext());
                                        job_recycleView.setAdapter(jobsAdapter);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return root;
    }

}