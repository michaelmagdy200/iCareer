package com.example.icareer.jobSeeker.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.icareer.Model.ApplicationClass;
import com.example.icareer.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ApplicationDailog extends AppCompatDialogFragment {

    private EditText app_name , app_email , app_phone , app_summary , app_skills , app_education ;
    DatabaseReference Seeker_ref , reference , reference2 ;
    ApplicationClass applicationClass  ;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_applicant_application, null);
        builder.setView(view)
                .setTitle("Application")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String applicant_name = app_name.getText().toString();
                        String applicant_email = app_email.getText().toString();
                        String applicant_phone = app_phone.getText().toString();
                        String applicant_summary = app_summary.getText().toString();
                        String applicant_education = app_education.getText().toString();
                        String applicant_skills =  app_skills.getText().toString();
                        if (TextUtils.isEmpty(applicant_name) && TextUtils.isEmpty(applicant_email) && TextUtils.isEmpty(applicant_phone) && TextUtils.isEmpty(applicant_skills) && TextUtils.isEmpty(applicant_education) ) {
                            Toast.makeText(getActivity(), "Please add some data.", Toast.LENGTH_SHORT).show();
                        } else {
                            applicationClass.setApplication_name(applicant_name);
                            applicationClass.setApplication_email(applicant_email);
                            applicationClass.setApplication_summary(applicant_summary);
                            applicationClass.setApplication_education(applicant_education);
                            applicationClass.setApplication_skills(applicant_skills);
                            applicationClass.setApplication_phone(applicant_phone);
                            reference2 = FirebaseDatabase.getInstance().getReference("Applicant");
                            String userId = reference2.push().getKey();
                            applicationClass.setApplication_id(userId);
                            reference2.child("Applicant Information").child(userId).setValue(applicationClass);
                            Toast.makeText(getContext(), "your application is sent ... Wait for response SOON", Toast.LENGTH_SHORT).show();

                        }

                    }
                });

        app_name = view.findViewById(R.id.application_name);
        app_email = view.findViewById(R.id.application_email);
        app_phone = view.findViewById(R.id.application_phone);
        app_education = view.findViewById(R.id.application_education);
        app_skills = view.findViewById(R.id.application_skills);
        app_summary = view.findViewById(R.id.application_summary);
        applicationClass = new ApplicationClass();


        Seeker_ref = FirebaseDatabase.getInstance().getReference("Job Seeker");
        Seeker_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                                String name =dataSnapshot.child("nameJobSeeker").getValue().toString();
                                String email =dataSnapshot.child("emailJobSeeker").getValue().toString();
                                String phone =dataSnapshot.child("phoneNumberJobSeeker").getValue().toString();

                                app_name.setText(name);
                                app_email.setText(email);
                                app_phone.setText(phone);
                            }
                        }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return builder.create();
    }


}
