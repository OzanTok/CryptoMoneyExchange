package com.ozantok3.cryptomoney;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ozantok on 2.07.2018.
 */

public class ProfileTab extends AppCompatActivity {

    Button signout;
    private FirebaseAuth mAuth;
    TextView username, email;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);

        mAuth = FirebaseAuth.getInstance();
        signout = (Button) findViewById(R.id.btnSignOut);
        username = (TextView) findViewById(R.id.tvName);
        email = (TextView) findViewById(R.id.tvEmail);


        //Again check if the user is Already Logged in or Not

        if (mAuth.getCurrentUser() == null) {

            //User NOT logged In

            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        //Fetch the Display name of current User

        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            username.setText("Username: " + user.getDisplayName());
            email.setText("E-mail: " + user.getEmail());

        }


        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                finish();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }


}
