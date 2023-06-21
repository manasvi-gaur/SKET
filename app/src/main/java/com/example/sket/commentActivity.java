package com.example.sket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.sket.adapter.comment_adapter;
import com.example.sket.databinding.ActivityCommentBinding;
import com.example.sket.model.comment_model;
import com.example.sket.model.post_model;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class commentActivity extends AppCompatActivity {
    ActivityCommentBinding binding;
    Intent intent;
    String postid;
    String postedby;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ArrayList<comment_model> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        binding = ActivityCommentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        intent = getIntent();

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

       postid = intent.getStringExtra("postid");
       postedby = intent.getStringExtra("postedby");


       database.getReference().child("post").child(postid).addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
                post_model post = snapshot.getValue(post_model.class);
               Picasso.get().load(post.getPostimage()).into(binding.postimage);
               binding.description.setText(post.getPostdescription());
               binding.like.setText(post.getPostlike()+"");
               binding.comment.setText(post.getCommentcount()+"");

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

       list = new ArrayList<>();

       database.getReference().child("user").child(postedby).addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               Users user = snapshot.getValue(Users.class);
               Picasso.get().load(user.getProfilepicture()).into(binding.profile);
               binding.name.setText(user.getName());

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

       binding.commentpostbtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               comment_model comment = new comment_model();
               comment.setCommentbody(binding.commentET.getText().toString());
               comment.setCommentedat(new Date().getTime());
               comment.setCommentedby(FirebaseAuth.getInstance().getUid());

               // to store data in firebase for comments
                database.getReference().child("post").child(postid).child("comments").push().setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        database.getReference().child("post").child(postid).child("commentcount").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int commentcount = 0;
                                if(snapshot.exists()){
                                    commentcount = snapshot.getValue(Integer.class);

                                }

                                database.getReference().child("post").child(postid).child("commentcount").setValue(commentcount+1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        binding.commentET.setText("");
                                        Toast.makeText(commentActivity.this, "Commented", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });
           }
       });

       comment_adapter adapter = new comment_adapter(this,list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.commentrv.setLayoutManager(linearLayoutManager);
        binding.commentrv.setAdapter(adapter);


        database.getReference().child("post").child(postid).child("comments").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                    list.clear();
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    comment_model comment = dataSnapshot.getValue(comment_model.class);
                    list.add(comment);
                    }
                    adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}