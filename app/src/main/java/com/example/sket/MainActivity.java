package com.example.sket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    Button login_btn;

    EditText email_login,password_login;

    FirebaseAuth auth;
    TextView login_to_signup;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        login_btn = findViewById(R.id.login_button);
        email_login = findViewById(R.id.EmailId_login);
        password_login = findViewById(R.id.login_password);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = email_login.getText().toString();
                String password = password_login.getText().toString();

                if((TextUtils.isEmpty(Email))){
                    Toast.makeText(MainActivity.this,"Enter The Email",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(password)){
                        Toast.makeText(MainActivity.this,"Enter The Password",Toast.LENGTH_SHORT).show();
                } else if (!Email.matches(emailPattern)) {
                    email_login.setError("Give Proper EmailAddress");
                } else if (password_login.length()<6) {
                    password_login.setError("More Then Six Characters");
                    Toast.makeText(MainActivity.this,"Password Needs To Be Longer Then Six Charater",Toast.LENGTH_SHORT).show();
                }else {
                    auth.signInWithEmailAndPassword(Email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                try {

                                    Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                                    SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("Email", Email);
                                    editor.putString("Pass", password);
                                    editor.apply();
                                    startActivity(intent);

                                    finish();
                                }catch (Exception e){
                                    Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(MainActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }


            }
        });




        login_to_signup = findViewById(R.id.login_to_signup);
        Intent Login_to_signup_intent = new Intent(this, SignUp.class);
        login_to_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(Login_to_signup_intent);
                finish();
            }
        });

    }
}