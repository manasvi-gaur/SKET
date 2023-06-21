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

import com.example.sket.ChatWin;
import com.example.sket.R;
import com.example.sket.databinding.ChatsLayoutBinding;
import com.example.sket.model.chats_model;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class chats_adapter extends RecyclerView.Adapter<chats_adapter.viewholder>{
    Context context;
    ArrayList<chats_model> list;

    public chats_adapter(Context context, ArrayList<chats_model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chats_layout,parent,false);

        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        chats_model chats = list.get(position);
        Picasso.get().load(chats.getChatprofile()).into(holder.binding.chatProfile);
        holder.binding.chatName.setText(chats.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatWin.class);
                intent.putExtra("profileChat",chats.getChatprofile());
                intent.putExtra("nameChat", chats.getName());
                intent.putExtra("uidChat", chats.getUid());
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        ChatsLayoutBinding binding;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            binding = ChatsLayoutBinding.bind(itemView);
        }
    }
}
