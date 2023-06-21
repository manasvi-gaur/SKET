package com.example.sket;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sket.adapter.followers_adapter;
import com.example.sket.model.followers_model;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class nav_profile_frag extends Fragment {

    RecyclerView recyclerView;
    ArrayList<followers_model> list;
    ImageView change_cover_photo,cover_picture,change_profile_picture,profile_picture;

    FirebaseAuth auth;
    FirebaseStorage storage;
    FirebaseDatabase database;

    TextView username,name,followerno,postcount,followingcount;
    ImageView logoutbtn;
    Activity context;


    public nav_profile_frag() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth  = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nav_profile_frag, container, false);
        context = getActivity();

        database.getReference().child("user").child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Users user = snapshot.getValue(Users.class);
                    Picasso.get().load(user.getCoverpage()).into(cover_picture);
                    Picasso.get().load(user.getProfilepicture()).into(profile_picture);
                    name.setText(user.getName());
                    username.setText(user.getUsername());
                    postcount.setText(user.getPostcount()+"");
                    followingcount.setText(user.getFollowingCount()+"");
                    followerno.setText(user.getFollowercount()+"");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        recyclerView = view.findViewById(R.id.friends_rv_profile);
        change_cover_photo = view.findViewById(R.id.change_cover_photo);
        cover_picture = view.findViewById(R.id.cover_picture);
        username = view.findViewById(R.id.username);
        name = view.findViewById(R.id.name);
        logoutbtn = view.findViewById(R.id.logoutbtn);
        change_profile_picture = view.findViewById(R.id.change_profile_picture);
        profile_picture = view.findViewById(R.id.profile);
        followerno = view.findViewById(R.id.followers);
        followingcount = view.findViewById(R.id.following);
        postcount = view.findViewById(R.id.post);



        list = new ArrayList<>();


        followers_adapter adapter = new followers_adapter(list,getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager((linearLayoutManager));
        recyclerView.setAdapter(adapter);

        // to send data in friends rv only of those who are following

        database.getReference().child("user").child(auth.getUid()).child("followers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        followers_model follow =  dataSnapshot.getValue(followers_model.class);
                        list.add(follow);
                    }
                    adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Intent intent = new Intent(context, MainActivity.class);
                Toast.makeText(context,"Successfully Loged out",Toast.LENGTH_SHORT).show();
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Email", "NA");
                editor.putString("Pass", "NA");
                editor.apply();
                startActivity(intent);
            }
        });

        change_cover_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,11);

            }
        });

        change_profile_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,22);
            }
        });


        return view;
    }

    public void onstart(){
        super.onStart();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==11){
            if(data.getData() != null){
                Uri uri = data.getData();
                cover_picture.setImageURI(uri);

                final  StorageReference reference = storage.getReference().child("cover_picture").child(FirebaseAuth.getInstance().getUid());
                reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getActivity(),"cover photo saved",Toast.LENGTH_SHORT).show();
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                database.getReference().child("user").child(auth.getUid()).child("coverpage").setValue(uri.toString());
                            }
                        });
                    }
                });
            }
        }else {
            if(data.getData() != null){
                Uri uri = data.getData();
                profile_picture.setImageURI(uri);

                final  StorageReference reference = storage.getReference().child("profile_picture").child(FirebaseAuth.getInstance().getUid());
                reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getActivity(),"Profile photo saved",Toast.LENGTH_SHORT).show();
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                database.getReference().child("user").child(auth.getUid()).child("profilepicture").setValue(uri.toString());
                            }
                        });
                    }
                });
            }
        }

    }





}