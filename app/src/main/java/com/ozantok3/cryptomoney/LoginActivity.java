package com.ozantok3.cryptomoney;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText name, email, password;
    private Button signIn, signUp;

    //For CryptoFragments


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        name = (EditText) findViewById(R.id.etName);
        email = (EditText) findViewById(R.id.etEmail);
        password = (EditText) findViewById(R.id.etPassword);
        signIn = (Button) findViewById(R.id.btnSignIn);
        signUp = (Button) findViewById(R.id.btnSignUp);


        //Check if users is already logged in

        if (mAuth.getCurrentUser() != null) {
            //User not logged in

            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));


        }

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getemail = email.getText().toString().trim();
                String getpassword = password.getText().toString().trim();
                callsignin(getemail, getpassword);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String getemail = email.getText().toString().trim();
                String getpassword = password.getText().toString().trim();
                callsignup(getemail, getpassword);

            }
        });


    }

    //Create Account

    private void callsignup(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("TESTING", "Sign UP Successful" + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Sign Up Failed ",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            userProfile();
                            Toast.makeText(LoginActivity.this, "Creat Account", Toast.LENGTH_SHORT).show();
                            Log.d("TESTING", "Creating Account");
                        }
                    }


                });
    }


    //Set User Display Name

    private void userProfile() {
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {

            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name.getText().toString().trim()).build();

            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                Log.d("TESTİNG", "User Profile Updated");
                            }
                        }
                    });
        }
    }


    //Sign In Process

    private void callsignin(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        Log.d("TESTING", "Sign In Successful" + task.isSuccessful());

                        if (!task.isSuccessful()) {

                            Log.v("TESTING", "Sign In with E-Mail Failed" + task.getException());
                            Toast.makeText(LoginActivity.this, "FAILED", Toast.LENGTH_SHORT).show();
                        } else {

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            finish();
                            startActivity(intent);
                        }
                    }
                });


    }



}
