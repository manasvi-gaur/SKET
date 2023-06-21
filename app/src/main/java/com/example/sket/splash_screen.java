package com.example.sket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class splash_screen extends AppCompatActivity {

    Animation topAnim , bottomAnim;
    ImageView image;
    TextView Name;

    private static int screen_time = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        image = findViewById(R.id.imageView);
        Name = findViewById(R.id.Name);
        image.setAnimation(topAnim);
        Name.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences getShared = getSharedPreferences("user", MODE_PRIVATE);
                String eml = getShared.getString("Email", "NA");
                String pas = getShared.getString("Pass", "NA");

                FirebaseAuth.getInstance().signInWithEmailAndPassword(eml, pas).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            try {
                                Intent loginToMainIntent = new Intent(splash_screen.this, MainActivity2.class);
                                startActivity(loginToMainIntent);
                                finishAffinity();
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Intent intent = new Intent(splash_screen.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });

            }
        },screen_time);
    }
}