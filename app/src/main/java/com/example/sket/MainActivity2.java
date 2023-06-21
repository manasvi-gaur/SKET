package com.example.sket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity2 extends AppCompatActivity {

 BottomNavigationView mainActivity2_bottom_nav;

 FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        FragmentManager FM = getSupportFragmentManager();
        FragmentTransaction ft = FM.beginTransaction();
        ft.replace(R.id.container,new nav_home_fragment());
        ft.commit();
        auth = FirebaseAuth.getInstance();

//

        mainActivity2_bottom_nav = findViewById(R.id.bottom_navigation_mainactivitytwo);

        mainActivity2_bottom_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.nav_home){
                    load_frag(new nav_home_fragment());
                    
                } else if (id == R.id.nav_notification) {
                    load_frag(new nav_notification_fragment());
                    
                } else if (id == R.id.nav_add) {
                    load_frag(new nav_add_frag());
                    
                } else if (id == R.id.nav_search) {
                    load_frag(new nav_search_frag());
                } else  {
                    load_frag(new nav_profile_frag());
                }

                return true;
            }

        });
        mainActivity2_bottom_nav.setSelectedItemId(R.id.nav_home);
    }
    public void load_frag(Fragment frag){
        FragmentManager FM = getSupportFragmentManager();
        FragmentTransaction ft = FM.beginTransaction();
        ft.replace(R.id.container, frag);
        ft.commit();



    }
}