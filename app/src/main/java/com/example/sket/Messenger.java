package com.example.sket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sket.adapter.chats_adapter;
import com.example.sket.model.chats_model;
import com.example.sket.model.followers_model;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Messenger extends AppCompatActivity {
    ArrayList<chats_model> list;
    chats_adapter adapter;
    RecyclerView chatlayout;
    Set<String> chatid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        chatlayout = findViewById(R.id.chat_rv);

        list = new ArrayList<>();
        chatid = new HashSet<>();
        FirebaseDatabase.getInstance().getReference().child("user").child(FirebaseAuth.getInstance().getUid()).child("followers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatid.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    chatid.add(dataSnapshot.getKey());

                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });

        FirebaseDatabase.getInstance().getReference().child("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Users model = dataSnapshot.getValue(Users.class);

                    if(chatid.contains(dataSnapshot.getKey())) {
                        chats_model chats_model = new chats_model(model.getProfilepicture(), model.getName(), dataSnapshot.getKey());
                        list.add(chats_model);

                    }
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        adapter = new chats_adapter(Messenger.this,list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        chatlayout.setLayoutManager(linearLayoutManager);
        chatlayout.setNestedScrollingEnabled(true);
        chatlayout.setAdapter(adapter);




    }
}