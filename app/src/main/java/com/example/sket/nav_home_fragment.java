package com.example.sket;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.sket.adapter.post_adapter;
import com.example.sket.adapter.story_adapter;
import com.example.sket.model.post_model;
import com.example.sket.model.story_model;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class nav_home_fragment extends Fragment {

    RecyclerView story_RV,dashboard_rv;
    ArrayList<story_model> list;
    ArrayList<post_model> post_list;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ImageView shareit;
    ImageView messenger;




    public nav_home_fragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_nav_home_fragment, container, false);

        story_RV = view.findViewById(R.id.story_rv);
        messenger = view.findViewById(R.id.dashboard_profile_homefrag);



        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        list = new ArrayList<>();
        list.add(new story_model(R.drawable.img_1,R.drawable.img_5,"Nikki"));  // to add newly added stories
        list.add(new story_model(R.drawable.img_1,R.drawable.img_5,"Nikki"));
        list.add(new story_model(R.drawable.img_1,R.drawable.img_5,"Nikki"));
        list.add(new story_model(R.drawable.img_1,R.drawable.img_5,"Nikki"));


        story_adapter adapter = new story_adapter(list,getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        story_RV.setLayoutManager(linearLayoutManager);
        story_RV.setNestedScrollingEnabled(false);
        story_RV.setAdapter(adapter);



        dashboard_rv  = view.findViewById(R.id.dashboard_rv);
        post_list = new ArrayList<>();

        post_adapter postAdapter = new post_adapter(post_list,getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        dashboard_rv.setLayoutManager(layoutManager);
        dashboard_rv.setNestedScrollingEnabled(true);
        dashboard_rv.setAdapter(postAdapter);

        database.getReference().child("post").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    post_list.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    post_model post = dataSnapshot.getValue(post_model.class);
                    post.setPostid(dataSnapshot.getKey());
                    post_list.add(post);
                }
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        messenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Messenger.class));
            }
        });


        return  view;


    }
}