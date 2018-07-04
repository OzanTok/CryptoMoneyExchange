package com.ozantok3.cryptomoney;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ozantok on 2.07.2018.
 */

public class ProfileTabActivity extends AppCompatActivity {

    @BindView(R.id.tvName)
    TextView username;
    @BindView(R.id.tvEmail)
    TextView email;

    private FirebaseAuth mAuth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();

        //Again check if the user is Already Logged in or Not
        if (mAuth.getCurrentUser() == null) {
            //User NOT logged In
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }

        //Fetch the Display name of current User
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            username.setText("Username: " + user.getDisplayName());
            email.setText("E-mail: " + user.getEmail());
        }
    }

    @OnClick(R.id.btnSignOut)
    protected void onClickSignOut() {
        mAuth.signOut();
        finish();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }


}
