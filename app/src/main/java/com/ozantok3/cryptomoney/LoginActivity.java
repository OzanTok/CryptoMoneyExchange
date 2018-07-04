package com.ozantok3.cryptomoney;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    //For CryptoFragmentst

    @BindView(R.id.textViewMessage)
    TextView textViewMessage;
    @BindView(R.id.etName)
    EditText editTextName;
    @BindView(R.id.etEmail)
    EditText editTextEmail;
    @BindView(R.id.etPassword)
    EditText editTextPassword;

    private boolean isRegister = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        onClickLogin();
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }


    }

    @OnClick(R.id.btnContinue)
    protected void onClickContinue() {
        if (isRegister) {
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            if (TextUtils.isEmpty(editTextEmail.getText())
                    || TextUtils.isEmpty(editTextPassword.getText())
                    || TextUtils.isEmpty(editTextName.getText())) {
                Toast.makeText(this, "E-Mail, Password and Username cannot be empty", Toast.LENGTH_SHORT).show();
            } else {
                callRegister(email, password);
            }
        } else {
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            if (TextUtils.isEmpty(editTextEmail.getText()) || TextUtils.isEmpty(editTextPassword.getText())) {
                Toast.makeText(this, "E-Mail and Password cannot be empty", Toast.LENGTH_SHORT).show();
            } else {
                callLogin(email, password);
            }
        }

    }

    @OnClick(R.id.btnLogin)
    protected void onClickLogin() {
        editTextName.setVisibility(View.GONE);
        textViewMessage.setText("Login Screen");
        isRegister = false;
    }

    @OnClick(R.id.btnRegister)
    protected void onClickRegister() {
        editTextName.setVisibility(View.VISIBLE);
        textViewMessage.setText("Register Screen");
        isRegister = true;
    }

    //Create Account

    private void callRegister(String email, String password) {
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
                    .setDisplayName(editTextName.getText().toString().trim()).build();

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

    private void callLogin(String email, String password) {

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
