package com.example.sket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sket.R;
import com.example.sket.Users;
import com.example.sket.model.followers_model;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class UserAdapter extends  RecyclerView.Adapter<UserAdapter.viewHolder>{

    Context context;
    ArrayList<Users> list;




    public UserAdapter(Context context, ArrayList<Users> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_sample_rv,parent,false);
        return  new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        // to add fetched phtoto,name,username in recycleview(user)
        Users users = list.get(position);
        Picasso.get().load(users.getProfilepicture()).into(holder.profile);
        holder.name.setText(users.getName());
        holder.username.setText(users.getUsername());

        FirebaseDatabase.getInstance().getReference().child("user").child(users.getUserID()).child("followers").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    holder.followbtn.setBackgroundColor(context.getResources().getColor(R.color.activefollow));
                    holder.followbtn.setText("following");
                    holder.followbtn.setEnabled(false);
                    holder.followbtn.setTextColor(context.getResources().getColor(R.color.textfollow));

                }else {
                    // follow button
                    holder.followbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            followers_model follow = new followers_model();
                            follow.setFollowedby(FirebaseAuth.getInstance().getUid());
                            follow.setFollowedat(new Date().getTime());

                            FirebaseDatabase.getInstance().getReference().child("user").child(FirebaseAuth.getInstance().getUid()).child("following").child(users.getUserID()).setValue(new Date().getTime()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    FirebaseDatabase.getInstance().getReference().child("user").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            Users user = snapshot.getValue(Users.class);
                                            FirebaseDatabase.getInstance().getReference().child("user").child(FirebaseAuth.getInstance().getUid()).child("followingCount").setValue(user.getFollowingCount()+1);

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }
                            });

                            FirebaseDatabase.getInstance().getReference().child("user").child(users.getUserID()).child("followers").child(FirebaseAuth.getInstance().getUid()).setValue(follow).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    FirebaseDatabase.getInstance().getReference().child("user").child(users.getUserID()).child("followercount").setValue(users.getFollowercount()+1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            holder.followbtn.setBackgroundColor(context.getResources().getColor(R.color.activefollow));
                                            holder.followbtn.setText("following");
                                            holder.followbtn.setEnabled(false);
                                            holder.followbtn.setTextColor(context.getResources().getColor(R.color.textfollow));
                                            Toast.makeText(context, "Following"+users.getName(), Toast.LENGTH_SHORT).show();

                                        }
                                    });
                                }
                            });

                        }
                    });
                }
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

    public class viewHolder extends RecyclerView.ViewHolder{


        TextView name,username;
        ImageView profile;
        Button followbtn;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            profile = itemView.findViewById(R.id.profile);
            username= itemView.findViewById(R.id.username);
            followbtn = itemView.findViewById(R.id.followbtn);
        }
    }
}
