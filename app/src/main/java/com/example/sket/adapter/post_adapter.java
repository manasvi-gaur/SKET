package com.example.sket.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sket.R;
import com.example.sket.Users;
import com.example.sket.commentActivity;
import com.example.sket.databinding.DashboardRvSampleHomefragBinding;
import com.example.sket.model.post_model;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class post_adapter extends RecyclerView.Adapter<post_adapter.viewholder> {

    ArrayList<post_model> list;
    Context context;

    public post_adapter(ArrayList<post_model> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dashboard_rv_sample_homefrag,parent,false);

        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        post_model model = list.get(position);
        Picasso.get().load(model.getPostimage()).into(holder.binding.homfragpostimage);
        String desc = model.getPostdescription();
        holder.binding.like.setText(model.getPostlike()+"");
        holder.binding.comment.setText(model.getCommentcount()+"");
        if(desc.equals("")){
            holder.binding.postdiscription.setVisibility(View.GONE);
        }else {
        holder.binding.postdiscription.setText(model.getPostdescription());
        holder.binding.postdiscription.setVisibility(View.VISIBLE);}

        FirebaseDatabase.getInstance().getReference().child("user").child(model.getPostedby()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users = snapshot.getValue(Users.class);
                Picasso.get().load(users.getProfilepicture()).into(holder.binding.dashboardProfileHomefrag);
                holder.binding.dashboardNameHomefrag.setText(users.getName());
                holder.binding.dashboardUsernameHomefrag.setText(users.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance().getReference().child("post").child(model.getPostid()).child("likes").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    holder.binding.like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.heart_filled, 0, 0, 0);
                }else{
                    holder.binding.like.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            FirebaseDatabase.getInstance().getReference().child("post").child(model.getPostid()).child("likes").child(FirebaseAuth.getInstance().getUid()).setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    FirebaseDatabase.getInstance().getReference().child("post").child(model.getPostid()).child("postlike").setValue(model.getPostlike() + 1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            holder.binding.like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.heart_filled, 0, 0, 0);
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

        holder.binding.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, commentActivity.class);
                intent.putExtra("postid",model.getPostid());
                intent.putExtra("postedby",model.getPostedby());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

        holder.binding.shareit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_COMPONENT_NAME,model.getPostimage());
                context.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class viewholder extends RecyclerView.ViewHolder{

        DashboardRvSampleHomefragBinding binding;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            binding = DashboardRvSampleHomefragBinding.bind(itemView);


        }
    }
}
