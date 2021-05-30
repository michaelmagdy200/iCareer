package com.example.icareer.CustomAdapter;

import androidx.annotation.NonNull;

import com.example.icareer.Model.JobSeekerClass;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseExperienceHelper {
    DatabaseReference db , reference , ref ;
    Boolean saved=null;
    ArrayList<JobSeekerClass> experience =new ArrayList<>();

    public FirebaseExperienceHelper(DatabaseReference db) {
        this.db = db;
    }

    //SAVE
    public Boolean save(JobSeekerClass jobSeekerClass)
    {
        if(jobSeekerClass==null)
        {
            saved=false;
        }else {

            try
            {

                db = FirebaseDatabase.getInstance().getReference("Job Seeker");
                db.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()){
                            String uid = ds.child("uid").getValue().toString();
                            reference = FirebaseDatabase.getInstance().getReference("Job Seeker").child(uid);
                            reference.child("Experience").push().setValue(jobSeekerClass);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                saved=true;
            }catch (DatabaseException e)
            {
                e.printStackTrace();
                saved=false;
            }

        }

        return saved;
    }
    //IMPLEMENT FETCH DATA AND FILL ARRAYLIST
    private void fetchData(DataSnapshot dataSnapshot)
    {
        experience.clear();
        
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            JobSeekerClass jobSeekerClass = ds.getValue(JobSeekerClass.class);
            experience.add(jobSeekerClass);
        }
    }
    //READ
    public ArrayList<JobSeekerClass> retrieve()
    {
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        return experience;
    }


}
