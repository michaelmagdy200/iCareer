package com.example.icareer.Company;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.icareer.Model.JobVacancy;
import com.example.icareer.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class VacancyDialog extends AppCompatDialogFragment {

    EditText jobTitle , descriptionJob , jobSalary , jobDate , noOfVacancy , companyName ;
    final Calendar myCalendar = Calendar.getInstance();
    DatabaseReference job_reference ,job_reference2 ;
    JobVacancy jobVacancy ;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_vacancy_dialog, null);
        builder.setView(view)
                .setTitle("ADD Vacancy Jobs")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String job_title = jobTitle.getText().toString();
                        String job_description = descriptionJob.getText().toString();
                        String job_data = jobDate.getText().toString();
                        String job_salary = jobSalary.getText().toString();
                        String no_Of_vacancy = noOfVacancy.getText().toString();
                        String Comapny_Name =  companyName.getText().toString();
                        if (TextUtils.isEmpty(job_title) && TextUtils.isEmpty(job_description) && TextUtils.isEmpty(job_data) && TextUtils.isEmpty(job_salary) && TextUtils.isEmpty(no_Of_vacancy) ) {
                            Toast.makeText(getActivity(), "Please add some data.", Toast.LENGTH_SHORT).show();
                        } else {
                            addDatatoFirebase(job_title, job_description, job_data , job_salary ,no_Of_vacancy , Comapny_Name);
                        }

                    }
                });


        jobDate = (EditText)view.findViewById(R.id.post_data);
        jobTitle = (EditText)view.findViewById(R.id.job_title);
        descriptionJob = (EditText)view.findViewById(R.id.job_description);
        noOfVacancy = (EditText)view.findViewById(R.id.no_of_vacancy);
        jobSalary = (EditText)view.findViewById(R.id.job_salary);
        companyName = (EditText) view.findViewById(R.id.company_name_add);
        jobVacancy = new JobVacancy();
        job_reference = FirebaseDatabase.getInstance().getReference().child("Company");


        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        jobDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        return builder.create();
    }


    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        jobDate.setText(sdf.format(myCalendar.getTime()));
    }

    private void addDatatoFirebase(String job_title, String job_description, String job_data, String job_salary, String no_of_vacancy , String companyName) {


        job_reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String id = dataSnapshot.child("id").getValue().toString();
                    jobVacancy.setJobTitle(job_title);
                    jobVacancy.setJobDate(job_data);
                    jobVacancy.setJobDescription(job_description);
                    jobVacancy.setJobSalary(job_salary);
                    jobVacancy.setNumberOfVacancy(no_of_vacancy);
                    jobVacancy.setCompanyName(companyName);
                    job_reference2 = FirebaseDatabase.getInstance().getReference("Company").child(id);
                    String userId = job_reference2.push().getKey();
                    jobVacancy.setId(userId);
                    job_reference2.child("Job Information").child(userId).setValue(jobVacancy);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
}
