package com.example.mobileproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnemployer;
    private Button btnworker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnemployer=findViewById(R.id.btn_employer);
        btnworker=findViewById(R.id.btn_worker);

        btnemployer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(new Intent(getApplicationContext(),LoginEmployerActivity.class));}}

           );
        btnworker.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

             startActivity(new Intent(getApplicationContext(),LoginActivity.class));                                  }
                                           }
        );
    }
}
