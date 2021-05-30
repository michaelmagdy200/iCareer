package com.example.icareer.Company;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.icareer.CustomAdapter.ApplicantsAdapter;
import com.example.icareer.CustomAdapter.JobsAdapter;
import com.example.icareer.MainActivity;
import com.example.icareer.Model.ApplicationClass;
import com.example.icareer.Model.JobVacancy;
import com.example.icareer.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class CompanyHome extends AppCompatActivity {

    TextView companyName;
    ImageButton logout;
    FloatingActionButton addVacancyJobs;
    RecyclerView recyclerView_jobSeeker;
    DatabaseReference referenceCompany , referenceApplicant ,referenceApplicant2 ;
    ApplicantsAdapter applicantsAdapter ;
    ArrayList<ApplicationClass> displayApplicantList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_home);

        companyName = (TextView) findViewById(R.id.company_name);
        logout = (ImageButton) findViewById(R.id.logout_company);
        addVacancyJobs = (FloatingActionButton) findViewById(R.id.add_vacancy_jobs);
        recyclerView_jobSeeker = (RecyclerView) findViewById(R.id.job_seekers_info_rycView);
        referenceCompany = FirebaseDatabase.getInstance().getReference("Company");
        referenceApplicant = FirebaseDatabase.getInstance().getReference("Applicant").child("Applicant Information");

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CompanyHome.this, MainActivity.class);
                startActivity(intent);
            }
        });
        addVacancyJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogToAddVacancyJobs();
            }
        });


        displayApplicantList = new ArrayList<>();
        recyclerView_jobSeeker.setHasFixedSize(true);
        recyclerView_jobSeeker.setLayoutManager(new LinearLayoutManager(this));

        referenceApplicant.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String applicantId = dataSnapshot.child("application_id").getValue().toString();
                    referenceApplicant2 = FirebaseDatabase.getInstance().getReference("Applicant").child("Applicant Information").child(applicantId);
                    referenceApplicant2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            ApplicationClass display = snapshot.getValue(ApplicationClass.class);
                            displayApplicantList.add(display);

                            applicantsAdapter = new ApplicantsAdapter(displayApplicantList);
                            recyclerView_jobSeeker.setAdapter(applicantsAdapter);
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

    @Override
    protected void onStart() {
        super.onStart();

        referenceCompany.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String user_name_Company = dataSnapshot.child("nameComany").getValue().toString();
                    companyName.setText(user_name_Company);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CompanyHome.this, "SomeThing Wrong", Toast.LENGTH_SHORT).show();

            }
        });

    }
    private void openDialogToAddVacancyJobs() {
        VacancyDialog vacancyDialog = new VacancyDialog();
        vacancyDialog.show(getSupportFragmentManager(), "dialog Vacancy");
    }
}