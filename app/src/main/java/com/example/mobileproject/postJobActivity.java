package com.example.mobileproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobileproject.model.Data;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.EventListener;

public class postJobActivity extends AppCompatActivity {
    private FloatingActionButton fabBtn;
     private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private DatabaseReference mJobPostDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postjob);

        fabBtn = findViewById(R.id.fab_add);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        String uId = mUser.getUid();

        mJobPostDatabase = FirebaseDatabase.getInstance().getReference().child("Job Post").child(uId);

        recyclerView = findViewById(R.id.recycler_job_post_id);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), insertJobActivity.class));
            }
        });
    }


 @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Data> firebaseRecyclerOptions =
                new FirebaseRecyclerOptions.Builder<Data>()
                        .setQuery(mJobPostDatabase, Data.class)
                        .build();


        FirebaseRecyclerAdapter<Data, MyViewHolder> adapter = new FirebaseRecyclerAdapter<Data, MyViewHolder>(firebaseRecyclerOptions) {

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_post_item, parent, false);
                return new MyViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i, @NonNull Data data) {

                viewHolder.setJobTitle(data.getTitle());
                viewHolder.setJobDate(data.getDate());
                viewHolder.setJobDescription(data.getDescription());
                viewHolder.setJobSkills(data.getSkills());
                viewHolder.setJobSalary(data.getSalary());
viewHolder.Delete.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        mJobPostDatabase= FirebaseDatabase.getInstance().getReference()
                .child("Job Post");
        mJobPostDatabase.removeValue();
    }
});


            }

        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        View myView;
        public ImageView Delete;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myView = itemView;
            Delete=itemView.findViewById(R.id.delete);
        }

        public void setJobTitle(String title) {
            TextView mTitle = myView.findViewById(R.id.job_title);
            mTitle.setText(title);
        }

        public void setJobDate(String date) {
            TextView mDate = myView.findViewById(R.id.job_date);
            mDate.setText(date);
        }

        public void setJobDescription(String description) {
            TextView mDescription = myView.findViewById(R.id.job_description);
            mDescription.setText(description);
        }

        public void setJobSkills(String skills) {
            TextView mSkills = myView.findViewById(R.id.job_skills);
            mSkills.setText(skills);
        }

        public void setJobSalary(String salary) {
            TextView mSalary = myView.findViewById(R.id.job_salary);
            mSalary.setText(salary);
        }

    }


}