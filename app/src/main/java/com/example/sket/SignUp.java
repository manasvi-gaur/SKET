package com.example.sket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class SignUp extends AppCompatActivity {

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageView googleBtn;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;

    Button signUp_button;
    TextView signUp_password,signUp_EmailId,signUp_username,signUp_Name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();

        googleBtn = findViewById(R.id.google_integration);
        signUp_button = findViewById(R.id.signup_button);
        signUp_EmailId = findViewById(R.id.signup_emailid);
        signUp_Name = findViewById(R.id.signup_firstname);
        signUp_password = findViewById(R.id.signup_password);
        signUp_username = findViewById(R.id.signup_username);


        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);

        signUp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = signUp_Name.getText().toString();
                String emailid = signUp_EmailId.getText().toString();
                String password = signUp_password.getText().toString();
                String username = signUp_username.getText().toString();
                auth.createUserWithEmailAndPassword(emailid,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            String user_id = task.getResult().getUser().getUid();
                            String default_coverpage ="https://firebasestorage.googleapis.com/v0/b/sket-ffe68.appspot.com/o/default_coverPage.png?alt=media&token=0795468f-6cfc-4608-9808-807c4d76c7d7";
                            String default_profile ="https://firebasestorage.googleapis.com/v0/b/sket-ffe68.appspot.com/o/default_profile.png?alt=media&token=e00a00df-eca8-4f43-ab4e-4c9c8274a056";
                            DatabaseReference databaseReference = database.getReference().child("user").child(user_id);
                            Users u = new Users(name,username,emailid,password,default_coverpage,default_profile);
                            databaseReference.setValue(u).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Intent signUp_to_login = new Intent(SignUp.this,MainActivity.class);
                                        Toast.makeText(getApplicationContext(),"Successfully Registered",Toast.LENGTH_SHORT).show();
                                        startActivity(signUp_to_login);
                                        finishAffinity();
                                    }else {
                                        Toast.makeText(SignUp.this,"Error in creating user", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else {
                            Toast.makeText(SignUp.this, Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });

    }

    void signUp(){
        Intent signUp_to_googleintehration = gsc.getSignInIntent();
        startActivityForResult(signUp_to_googleintehration,200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==200){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                navigateToSecondActivity();
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext()," "+e,Toast.LENGTH_SHORT).show();
            }

        }
    }

    void navigateToSecondActivity(){

        Intent SignUp_Through_Google_toHome = new Intent(SignUp.this,MainActivity2.class);
        startActivity(SignUp_Through_Google_toHome);
        finish();
    }
}