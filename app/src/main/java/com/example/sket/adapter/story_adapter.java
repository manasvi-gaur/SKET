package com.example.sket.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sket.R;
import com.example.sket.model.story_model;

import java.util.ArrayList;

public class story_adapter extends RecyclerView.Adapter<story_adapter.viewholder>{


    ArrayList<story_model> list ;
    Context context;

    public story_adapter(ArrayList<story_model> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.story_rv_design,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        story_model model = list.get(position);
        holder.story_image_view.setImageResource(model.getStory_imageview());
        holder.story_profile_imageview.setImageResource(model.getStory_profile_view());
        holder.story_name.setText(model.getStory_name());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class viewholder extends RecyclerView.ViewHolder{
        ImageView story_image_view, story_profile_imageview;
        TextView story_name;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            story_image_view = itemView.findViewById(R.id.homfragpostimage);
            story_profile_imageview = itemView.findViewById(R.id.dashboard_profile_homefrag);
            story_name = itemView.findViewById(R.id.story_name);

        }
    }
}
