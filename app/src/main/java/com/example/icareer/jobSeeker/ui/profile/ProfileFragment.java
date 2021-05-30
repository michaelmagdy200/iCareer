package com.example.icareer.jobSeeker.ui.profile;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.icareer.CustomAdapter.EducationAdapter;
import com.example.icareer.CustomAdapter.ExperienceAdapter;
import com.example.icareer.CustomAdapter.FirebaseExperienceHelper;
import com.example.icareer.CustomAdapter.FirebaseHelperEducation;
import com.example.icareer.CustomAdapter.FirebaseHelperSkills;
import com.example.icareer.CustomAdapter.SkillsAdapter;
import com.example.icareer.Model.JobSeekerClass;
import com.example.icareer.jobSeeker.Dialogs.DialogShow;
import com.example.icareer.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends Fragment {

    TextView username , address , phone ,title;
    DatabaseReference referenceJobSeeker , referenceJobSeeker2  , referenceJobSeeker3;
    CircleImageView profileImage ;
    private final int PICK_IMAGE_REQUEST = 22;
    StorageReference storageReference;
    private Uri filePath;
    Button saveBtn ;
    ImageButton editInfo , editListEducation , editListSkills , editListExperience ;
    RecyclerView education_recycleView , skills_recycleView , experience_recycleView ;

    FirebaseHelperEducation educationhelper;
    FirebaseHelperSkills helperSkills ;
    FirebaseExperienceHelper experienceHelper ;

    EducationAdapter educationAdapter ;
    SkillsAdapter skillsAdapter;
    ExperienceAdapter experienceAdapter ;

    EditText editTextitemAddingEducation , editTextitemAddingExperience ,editTextitemAddingSkills  ;
    Button addEducationBtn , addSkillsBtn , addExperienceBtn ;
    DatabaseReference skill1 , skill2 , education1 , education2 , experience1 , experience2;
    List<JobSeekerClass> skillsList , educationlist , experienceList ;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel dashboardViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        storageReference = FirebaseStorage.getInstance().getReference("Job Seeker");
        saveBtn = (Button) root.findViewById(R.id.save_btn);
        username = (TextView) root.findViewById(R.id.jobSeeker_profile_name);
        address = (TextView) root.findViewById(R.id.jobSeeker_address);
        title =(TextView) root.findViewById(R.id.jobSeeker_title) ;
        phone = (TextView) root.findViewById(R.id.jobSeeker_phone);
        profileImage = (CircleImageView) root.findViewById(R.id.jobSeeker_profile_image);
        ProgressDialog loadingbar = new ProgressDialog(getActivity());
        editInfo = (ImageButton) root.findViewById(R.id.infoEdit);
        editListEducation = (ImageButton) root.findViewById(R.id.edit_education);
        editListSkills = (ImageButton) root.findViewById(R.id.edit_skills);
        editListExperience = (ImageButton) root.findViewById(R.id.edit_experience);

        editInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogInfo();
            }
        });
        editListEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogAddEducation();
            }
        });

        editListSkills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogAddSkills();
            }
        });
        editListExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogAddExperience();
            }
        });


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImage();
            }
        });
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(getActivity() , v);
                popupMenu.getMenuInflater().inflate(R.menu.set_profile_image , popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.upload_image:
                                uploadProfileImage();
                                return true;
                            case R.id.delete_image:
                                profileImage.setImageResource(R.drawable.profile);
                                Toast.makeText(getActivity(), "deleted", Toast.LENGTH_SHORT).show();
                                return true;
                            default:
                                return false;
                        }
                    }
                });

                popupMenu.show();
            }
        });


        skillsList =new ArrayList<>();
        educationlist =new ArrayList<>();
        experienceList =new ArrayList<>();

        //SKILLS
        skills_recycleView = (RecyclerView) root.findViewById(R.id.skills_recycleView);
        skills_recycleView.setLayoutManager(new GridLayoutManager(getContext() , 2));
        //INITIALIZE FIREBASE DB
        helperSkills=new FirebaseHelperSkills(referenceJobSeeker);
        skill1 = FirebaseDatabase.getInstance().getReference("Job Seeker");
        skill1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    String uid = ds.child("uid").getValue().toString();
                    skill2 = FirebaseDatabase.getInstance().getReference("Job Seeker").child(uid).child("Skills");
                    skill2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                JobSeekerClass jobSeekerClass = ds.getValue(JobSeekerClass.class);
                                skillsList.add(jobSeekerClass);

                            }
                            skillsAdapter =new SkillsAdapter(getContext() , skillsList);
                            skills_recycleView.setAdapter(skillsAdapter);
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



        experience_recycleView = (RecyclerView) root.findViewById(R.id.experience_recycleView);
        experience_recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        //INITIALIZE FIREBASE DB
        experienceHelper=new FirebaseExperienceHelper(referenceJobSeeker);
        experience1 = FirebaseDatabase.getInstance().getReference("Job Seeker");
        experience1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    String uid = ds.child("uid").getValue().toString();
                    experience2 = FirebaseDatabase.getInstance().getReference("Job Seeker").child(uid).child("Experience");
                    experience2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                JobSeekerClass jobSeekerClass = ds.getValue(JobSeekerClass.class);
                                experienceList.add(jobSeekerClass);

                            }
                            experienceAdapter =new ExperienceAdapter(getContext() , experienceList);
                            experience_recycleView.setAdapter(experienceAdapter);
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




        //SETUP RECYCLER
        education_recycleView = (RecyclerView) root.findViewById(R.id.education_recycleView);
        education_recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        //INITIALIZE FIREBASE DB
        educationhelper=new FirebaseHelperEducation(referenceJobSeeker);
        education1 = FirebaseDatabase.getInstance().getReference("Job Seeker");
        education1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    String uid = ds.child("uid").getValue().toString();
                    education2 = FirebaseDatabase.getInstance().getReference("Job Seeker").child(uid).child("Education");
                    education2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                JobSeekerClass jobSeekerClass = ds.getValue(JobSeekerClass.class);
                                educationlist.add(jobSeekerClass);

                            }
                            educationAdapter =new EducationAdapter(getContext() , educationlist);
                            education_recycleView.setAdapter(educationAdapter);
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


        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        return root;
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST  && resultCode == RESULT_OK  && data != null && data.getData() != null){
            // Get the Uri of data
            filePath = data.getData();
            profileImage.setImageURI(filePath);
            try {
                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                profileImage.setImageBitmap(bitmap);
            }
            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onStart() {
        super.onStart();
               retrieveData();
    }





    private void uploadProfileImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image from here..."), PICK_IMAGE_REQUEST);

    }
    private void saveImage(){

        if (filePath != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();


            // Defining the child of storageReference
            StorageReference ref = storageReference.child("Profile Job Seeker images/" + UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

//                  JobSeekerClass jobSeekerClass = new JobSeekerClass();
//                    String upLoadId = referenceJobSeeker.push().getKey();
//                    referenceJobSeeker.child(upLoadId).setValue(jobSeekerClass);

                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Image Uploaded!!", Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded " + (int)progress + "%");
                }
            });
        }

    }
    private void retrieveData() {
        referenceJobSeeker2 = FirebaseDatabase.getInstance().getReference("Job Seeker");
        referenceJobSeeker2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    String JobSeekerID = dataSnapshot.child("uid").getValue().toString();
                    referenceJobSeeker3 = FirebaseDatabase.getInstance().getReference("Job Seeker").child(JobSeekerID);
                    referenceJobSeeker3.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String user_name =dataSnapshot.child("nameJobSeeker").getValue().toString();
                            String user_address =dataSnapshot.child("addressJobSeeker").getValue().toString();
                            String user_phone =dataSnapshot.child("phoneNumberJobSeeker").getValue().toString();
                            String user_title =dataSnapshot.child("titleJobSeeker").getValue().toString();

                            username.setText(user_name);
                            address.setText(user_address);
                            phone.setText(user_phone);
                            title.setText(user_title);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "SomeThing Wrong", Toast.LENGTH_SHORT).show();

            }});


    }


    private void openDialogInfo() {
        DialogShow DialogUpdateInfo = new DialogShow();
        DialogUpdateInfo.show(getFragmentManager(), "dialog");
    }
    private void openDialogAddEducation() {
        Dialog d=new Dialog(getContext());
        d.setTitle("Save To Firebase");
        d.setContentView(R.layout.layout_dialog);
        editTextitemAddingEducation= (EditText) d.findViewById(R.id.item_edtext_education);
        addEducationBtn = (Button) d.findViewById(R.id.add_btn_education);
        addEducationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //GET DATA
                String item =editTextitemAddingEducation.getText().toString();
                //SET DATA
                JobSeekerClass jobSeekerClass= new JobSeekerClass() ;
                jobSeekerClass.setEducation(item);
                //SIMPLE VALIDATION
                if(item != null && item.length()>0)
                {
                    //THEN SAVE
                    if(educationhelper.save(jobSeekerClass))
                    {
                        editTextitemAddingEducation.setText("");
                        education1 = FirebaseDatabase.getInstance().getReference("Job Seeker");
                        education1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot ds : snapshot.getChildren()){
                                    String uid = ds.child("uid").getValue().toString();
                                    education2 = FirebaseDatabase.getInstance().getReference("Job Seeker").child(uid).child("Education");
                                    education2.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for (DataSnapshot ds : snapshot.getChildren()) {
                                                JobSeekerClass jobSeekerClass = ds.getValue(JobSeekerClass.class);
                                                educationlist.add(jobSeekerClass);

                                            }
                                            educationAdapter =new EducationAdapter(getContext() , educationlist);
                                            education_recycleView.setAdapter(educationAdapter);
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
                }else
                {
                    Toast.makeText(getContext(), "Name Must Not Be Empty", Toast.LENGTH_SHORT).show();
                }

            }

        });
        d.show();

    }
    private void openDialogAddSkills() {
        Dialog d=new Dialog(getContext());
        d.setTitle("Save To Firebase");
        d.setContentView(R.layout.layout_dialog_skills);
        editTextitemAddingSkills= (EditText) d.findViewById(R.id.item_edtext_skills);
        addSkillsBtn = (Button) d.findViewById(R.id.add_btn_skills);
        addSkillsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //GET DATA
                String item =editTextitemAddingSkills.getText().toString();
                //SET DATA
                JobSeekerClass jobSeekerClass= new JobSeekerClass() ;
                jobSeekerClass.setSkill(item);
                //SIMPLE VALIDATION
                if(item != null && item.length()>0)
                {
                    //THEN SAVE
                    if(helperSkills.save(jobSeekerClass))
                    {
                        editTextitemAddingSkills.setText("");
                        skill1 = FirebaseDatabase.getInstance().getReference("Job Seeker");
                        skill1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot ds : snapshot.getChildren()){
                                    String uid = ds.child("uid").getValue().toString();
                                    skill2 = FirebaseDatabase.getInstance().getReference("Job Seeker").child(uid).child("Skills");
                                    skill2.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for (DataSnapshot ds : snapshot.getChildren()) {
                                                JobSeekerClass jobSeekerClass = ds.getValue(JobSeekerClass.class);
                                                skillsList.add(jobSeekerClass);

                                            }
                                            skillsAdapter =new SkillsAdapter(getContext() , skillsList);
                                            skills_recycleView.setAdapter(skillsAdapter);
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
                }else
                {
                    Toast.makeText(getContext(), "Name Must Not Be Empty", Toast.LENGTH_SHORT).show();
                }

            }

        });
        d.show();


    }
    private void openDialogAddExperience() {
        Dialog d=new Dialog(getContext());
        d.setTitle("Save To Firebase");
        d.setContentView(R.layout.layout_dialog_experience);
        editTextitemAddingExperience= (EditText) d.findViewById(R.id.item_edtext_experience);
        addExperienceBtn = (Button) d.findViewById(R.id.add_btn_experience);
        addExperienceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //GET DATA
                String item =editTextitemAddingExperience.getText().toString();
                //SET DATA
                JobSeekerClass jobSeekerClass= new JobSeekerClass() ;
                jobSeekerClass.setExperience(item);
                //SIMPLE VALIDATION
                if(item != null && item.length()>0)
                {
                    //THEN SAVE
                    if(experienceHelper.save(jobSeekerClass))
                    {
                        editTextitemAddingExperience.setText("");
                        experience1 = FirebaseDatabase.getInstance().getReference("Job Seeker");
                        experience1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot ds : snapshot.getChildren()){
                                    String uid = ds.child("uid").getValue().toString();
                                    experience2 = FirebaseDatabase.getInstance().getReference("Job Seeker").child(uid).child("Experience");
                                    experience2.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for (DataSnapshot ds : snapshot.getChildren()) {
                                                JobSeekerClass jobSeekerClass = ds.getValue(JobSeekerClass.class);
                                                experienceList.add(jobSeekerClass);

                                            }
                                            experienceAdapter =new ExperienceAdapter(getContext() , experienceList);
                                            experience_recycleView.setAdapter(experienceAdapter);
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
                }else
                {
                    Toast.makeText(getContext(), "Name Must Not Be Empty", Toast.LENGTH_SHORT).show();
                }

            }

        });
        d.show();

    }

}

