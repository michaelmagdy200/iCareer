package com.example.icareer.jobSeeker.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.icareer.Model.JobSeekerClass;
import com.example.icareer.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DialogShow extends AppCompatDialogFragment {

    private EditText editTextUsername;
    private EditText editTextTitel;
    private EditText editTextAddress;
    private EditText editTextPhone;
    DatabaseReference jobSeeker_ref ;
    JobSeekerClass jobSeekerClass ;
    FirebaseUser user;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_info, null);
        builder.setView(view)
                .setTitle("Update Information")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        })
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String username = editTextUsername.getText().toString();
                        String title = editTextTitel.getText().toString();
                        String address = editTextAddress.getText().toString();
                        String phone = editTextPhone.getText().toString();
                        jobSeekerClass = new JobSeekerClass() ;
                        jobSeeker_ref =FirebaseDatabase.getInstance().getReference("Job Seeker");

                    }
                });

        editTextUsername = view.findViewById(R.id.updateName);
        editTextTitel = view.findViewById(R.id.updateTitle);
        editTextAddress = view.findViewById(R.id.updateAddress);
        editTextPhone = view.findViewById(R.id.updatePhone);

        jobSeeker_ref = FirebaseDatabase.getInstance().getReference("Job Seeker");
        jobSeeker_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    String user_name =dataSnapshot.child("nameJobSeeker").getValue().toString();
                    String user_address =dataSnapshot.child("addressJobSeeker").getValue().toString();
                    String user_phone =dataSnapshot.child("phoneNumberJobSeeker").getValue().toString();
                    String user_title =dataSnapshot.child("titleJobSeeker").getValue().toString();

                    editTextUsername.setText(user_name);
                    editTextAddress.setText(user_address);
                    editTextPhone.setText(user_phone);
                    editTextTitel.setText(user_title);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "SomeThing Wrong", Toast.LENGTH_SHORT).show();

            }});




        return builder.create();
    }

}
