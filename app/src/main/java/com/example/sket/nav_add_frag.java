package com.example.sket;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sket.databinding.FragmentNavAddFragBinding;
import com.example.sket.model.post_model;
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

import java.util.Date;


public class nav_add_frag extends Fragment {

    FragmentNavAddFragBinding binding;
    Uri uri;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    ProgressDialog progressDialog;


    public nav_add_frag() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        progressDialog =  new ProgressDialog(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding =  FragmentNavAddFragBinding.inflate(inflater, container, false);

        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Uploading");
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        database.getReference().child("user").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Users users = snapshot.getValue(Users.class);
                    Picasso.get().load(users.getProfilepicture()).into(binding.profile);
                    binding.name.setText(users.getName());
                    binding.username.setText(users.getUsername());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //to set post but enable acoording to edit text
        binding.postdiscription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String decs = binding.postdiscription.getText().toString();
                if(!decs.isEmpty()){
                    binding.postbtn.setBackgroundColor(getContext().getResources().getColor(R.color.purple));
                    binding.postbtn.setTextColor(getContext().getResources().getColor(R.color.white));
                    binding.postbtn.setEnabled(true);
                }else {
                    binding.postbtn.setBackgroundColor(getContext().getResources().getColor(R.color.down));
                    binding.postbtn.setTextColor(getContext().getResources().getColor(R.color.grey));
                    binding.postbtn.setEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //enabling imagebtn to go to gallery to select an image
        binding.addimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,11);
            }
        });

        binding.postbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.show();

                // to store data in storage at a particular path
                final StorageReference reference = storage.getReference().child("posts").child(FirebaseAuth.getInstance().getUid()).child(new Date().getTime()+"");

                // reference.putFile(uri) this will put that store imformative uri at that particular location provided
                reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        //this will download that stored post imformation in database
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                post_model post = new post_model();
                                post.setPostimage(uri.toString());
                                post.setPostedby(FirebaseAuth.getInstance().getUid());
                                post.setPostdescription(binding.postdiscription.getText().toString());
                                post.setPostedat(new Date().getTime());

                                database.getReference().child("post").push().setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        progressDialog.dismiss();

                                        FirebaseDatabase.getInstance().getReference("user").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                Users user = snapshot.getValue(Users.class);
                                                FirebaseDatabase.getInstance().getReference("user").child(FirebaseAuth.getInstance().getUid()).child("postcount").setValue(user.getPostcount()+1);

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                        Toast.makeText(getActivity(),"Posted Successfully",Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }
                        });
                    }
                });
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data.getData() != null){
            uri = data.getData();
            binding.postimage.setImageURI(uri);

            binding.postimage.setVisibility(View.VISIBLE);
            binding.postbtn.setBackgroundColor(getContext().getResources().getColor(R.color.purple));
            binding.postbtn.setTextColor(getContext().getResources().getColor(R.color.white));
            binding.postbtn.setEnabled(true);

        }
    }
}