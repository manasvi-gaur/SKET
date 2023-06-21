package com.example.sket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sket.R;
import com.example.sket.Users;
import com.example.sket.databinding.CommentrvBinding;
import com.example.sket.model.comment_model;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class comment_adapter extends  RecyclerView.Adapter<comment_adapter.viewholder>{
    Context context;
    ArrayList<comment_model> list;

    public comment_adapter(Context context, ArrayList<comment_model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.commentrv,parent,false);

        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        comment_model comment = list.get(position);
        holder.binding.comment.setText(comment.getCommentbody());

        String time = TimeAgo.using(comment.getCommentedat());
        holder.binding.time.setText(time);

        FirebaseDatabase.getInstance().getReference().child("user").child(comment.getCommentedby()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users = snapshot.getValue(Users.class);
                Picasso.get().load(users.getProfilepicture()).into(holder.binding.profile);
                holder.binding.comment.setText(users.getName()+"-:"+comment.getCommentbody());
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
        CommentrvBinding binding;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            binding = CommentrvBinding.bind(itemView);
        }
    }
}
