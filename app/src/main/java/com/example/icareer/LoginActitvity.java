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
import android.widget.TextView;
import android.widget.Toast;

import com.example.icareer.R;
import com.example.icareer.jobSeeker.JobSeekerHome;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActitvity extends AppCompatActivity implements View.OnClickListener {

    TextView createAccount;
    EditText loginEmail, loginPassword;
    Button loginBtn;
    CheckBox checkBoxJobSeekerLogin, checkBoxCompanyLogin;

    private FirebaseAuth firebaseAuth;
    ProgressDialog loadingBar ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_actitvity);
        createAccount = (TextView) findViewById(R.id.creatAccount);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActitvity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        loginEmail = (EditText) findViewById(R.id.email_edtxt);
        loginPassword = (EditText) findViewById(R.id.password_edtxt);
        loginBtn = (Button) findViewById(R.id.login_btn);
        checkBoxJobSeekerLogin = (CheckBox) findViewById(R.id.checkbox_jobSeeker_login);
        checkBoxJobSeekerLogin.setOnClickListener(this);
        checkBoxCompanyLogin = (CheckBox) findViewById(R.id.checkbox_company_login);
        checkBoxCompanyLogin.setOnClickListener(this);

        loadingBar = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {


        if (checkBoxJobSeekerLogin.isChecked() && checkBoxCompanyLogin.isChecked()) {
            checkBoxJobSeekerLogin.setError("you error msg");
            checkBoxCompanyLogin.setError("you error msg");
            Toast.makeText(this, "please Select One", Toast.LENGTH_SHORT).show();
        } else {
            switch (v.getId()) {
                case R.id.checkbox_jobSeeker_login:
                    if (checkBoxJobSeekerLogin.isChecked()) {
                        checkBoxJobSeekerLogin.setError(null);
                        checkBoxCompanyLogin.setError(null);
                        loginBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                loadingBar = ProgressDialog.show(LoginActitvity.this,"Please wait","Processing",true);
                                firebaseAuth.signInWithEmailAndPassword(loginEmail.getText().toString() , loginPassword.getText().toString())
                                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                loadingBar.dismiss();
                                                if (task.isSuccessful()){
                                                    Toast.makeText(LoginActitvity.this,"Login successful",Toast.LENGTH_LONG).show();
                                                    Intent intent = new Intent(LoginActitvity.this, JobSeekerHome.class);
                                                    startActivity(intent);
                                                }
                                                else {
                                                    Toast.makeText(LoginActitvity.this, task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                            }
                        });

                    }

                case R.id.checkbox_company_login:
                    if (checkBoxCompanyLogin.isChecked()) {
                        checkBoxJobSeekerLogin.setError(null);
                        checkBoxCompanyLogin.setError(null);
                        loginBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                loadingBar = ProgressDialog.show(LoginActitvity.this,"Please wait","Processing",true);

                                firebaseAuth.signInWithEmailAndPassword(loginEmail.getText().toString() , loginPassword.getText().toString())
                                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                loadingBar.dismiss();
                                                if (task.isSuccessful()){
                                                    Toast.makeText(LoginActitvity.this,"Login successful",Toast.LENGTH_LONG).show();

                                                }
                                                else {
                                                    Toast.makeText(LoginActitvity.this, task.getException().getMessage(),Toast.LENGTH_LONG).show();

                                                }
                                            }
                                        });
                            }
                        });

                    }

            }
        }
    }

}
