package com.example.sket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sket.R;
import com.example.sket.Users;
import com.example.sket.databinding.FriendRvProfilfragBinding;
import com.example.sket.model.followers_model;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class followers_adapter extends RecyclerView.Adapter<followers_adapter.viewholder>{

    ArrayList<followers_model> list;
    Context context;

    public followers_adapter(ArrayList<followers_model> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.friend_rv_profilfrag,parent,false);

        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
    followers_model follow = list.get(position);

        FirebaseDatabase.getInstance().getReference().child("user").child(follow.getFollowedby()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users = snapshot.getValue(Users.class);
                Picasso.get().load(users.getProfilepicture()).into(holder.binding.profileProfilefrag);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{

        FriendRvProfilfragBinding binding;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            binding = FriendRvProfilfragBinding.bind(itemView);
        }
    }
}
