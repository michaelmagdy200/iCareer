package com.example.icareer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.icareer.Model.ComapnyClass;
import com.example.icareer.Model.JobSeekerClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    EditText name, email, password , phoneNumber , address , title ;
    Button signUp;
    DatabaseReference referenceJobSeeker;
    DatabaseReference referenceCompany;
    JobSeekerClass jobSeekerClass;
    ComapnyClass comapnyClass;
    CheckBox checkBoxJobSeekerSignUp, checkBoxCompanySingUp;
    private FirebaseAuth mAuth ;
    ProgressDialog loadingBar ;
    FirebaseUser user;
    String userID ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        loadingBar = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();


        referenceJobSeeker = FirebaseDatabase.getInstance().getReference("Job Seeker");
        referenceCompany = FirebaseDatabase.getInstance().getReference("Company");


        name = (EditText) findViewById(R.id.name_edtxt);
        email = (EditText) findViewById(R.id.emailSignUp_edtxt);
        password = (EditText) findViewById(R.id.passwordSignUp_edtxt);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber_edtTxt);
        address = (EditText) findViewById(R.id.address_edtText);
        title = (EditText) findViewById(R.id.title_edtTxt);

        signUp = (Button) findViewById(R.id.signUp_btn);
        checkBoxJobSeekerSignUp = (CheckBox) findViewById(R.id.checkbox_jobSeeker_signup);
        checkBoxJobSeekerSignUp.setOnClickListener(this);
        checkBoxCompanySingUp = (CheckBox) findViewById(R.id.checkbox_company_signUp);
        checkBoxCompanySingUp.setOnClickListener(this);

        comapnyClass = new ComapnyClass();
        jobSeekerClass = new JobSeekerClass();

    }

    @Override
    public void onClick(View v) {
        if(checkBoxJobSeekerSignUp.isChecked() && checkBoxCompanySingUp.isChecked()) {
            checkBoxJobSeekerSignUp.setError("you error msg");
            checkBoxCompanySingUp.setError("you error msg");
            Toast.makeText(this, "please Select One", Toast.LENGTH_SHORT).show();
        }

        else {
            switch (v.getId()) {
                case R.id.checkbox_jobSeeker_signup:
                    if (checkBoxJobSeekerSignUp.isChecked())
                        checkBoxJobSeekerSignUp.setError(null);
                        checkBoxCompanySingUp.setError(null);
                    signUp.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!name.equals("") &&  !email.equals("") && !password.equals("")&& !phoneNumber.equals("") && !address.equals("")) {

                                    loadingBar.setTitle("Create Job Seeker Account");
                                    loadingBar.setMessage("Please wait, while we are checking the credentials.");
                                    loadingBar.setCanceledOnTouchOutside(false);
                                    loadingBar.show();
                                    mAuth.createUserWithEmailAndPassword(email.getText().toString() , password.getText().toString())
                                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(SignUpActivity.this, "Sign Up Succefully", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(SignUpActivity.this, LoginActitvity.class);
                                                        startActivity(intent);

                                                        jobSeekerClass.setNameJobSeeker(name.getText().toString().trim());
                                                        jobSeekerClass.setEmailJobSeeker(email.getText().toString().trim());
                                                        jobSeekerClass.setPasswordJobSeeker(password.getText().toString().trim());
                                                        jobSeekerClass.setPhoneNumberJobSeeker(phoneNumber.getText().toString().trim());
                                                        jobSeekerClass.setAddressJobSeeker(address.getText().toString().trim());
                                                        jobSeekerClass.setTitleJobSeeker(title.getText().toString().trim());
                                                        String userIdForJobSeeker = referenceJobSeeker.push().getKey();
                                                        jobSeekerClass.setUid(userIdForJobSeeker);
                                                        referenceJobSeeker.child(userIdForJobSeeker).setValue(jobSeekerClass);
                                                    }
                                                    else {
                                                        Toast.makeText(SignUpActivity.this, task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                                    }
                                                    loadingBar.dismiss();
                                                }
                                            });


                                }
                                else
                                {
                                    name.setError("full field");
                                    address.setError("full field");
                                    phoneNumber.setError("full field");
                                    email.setError("full field");
                                    password.setError("full field");

                                }
                            }
                        });
                    break;
                case R.id.checkbox_company_signUp:
                    if (checkBoxCompanySingUp.isChecked())
                        checkBoxJobSeekerSignUp.setError(null);
                        checkBoxCompanySingUp.setError(null);
                        signUp.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!name.equals("") && !email.equals("") && !password.equals("") && !phoneNumber.equals("") && !address.equals("")   ) {

                                    loadingBar.setTitle("Create Company Account");
                                    loadingBar.setMessage("Please wait, while we are checking the credentials.");
                                    loadingBar.setCanceledOnTouchOutside(false);
                                    loadingBar.show();
                                    mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(SignUpActivity.this, "Sign Up Succefully", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(SignUpActivity.this, LoginActitvity.class);
                                                        startActivity(intent);
                                                    } else {
                                                        Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                    }
                                                    loadingBar.dismiss();
                                                }
                                            });

                                    comapnyClass.setNameComany(name.getText().toString().trim());
                                    comapnyClass.setEmailComapny(email.getText().toString().trim());
                                    comapnyClass.setPasswordCompany(password.getText().toString().trim());
                                    comapnyClass.setPhoneNumberComapny(phoneNumber.getText().toString().trim());
                                    comapnyClass.setAddressComapny(address.getText().toString().trim());
                                    comapnyClass.setTitleCompany(title.getText().toString().trim());
                                    String userIdForCompany = referenceCompany.push().getKey();
                                    comapnyClass.setUid(userIdForCompany);
                                    referenceCompany.child(userIdForCompany).setValue(comapnyClass);

                                }
                                else
                                {
                                    name.setError("full field");
                                    address.setError("full field");
                                    phoneNumber.setError("full field");
                                    email.setError("full field");
                                    password.setError("full field");

                                }
                            }
                        });
                    break;
            }
        }
    }
}