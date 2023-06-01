package com.example.mobileproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mobileproject.model.Cv;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class cvViewActivity extends AppCompatActivity {
ListView listView;
DatabaseReference databaseReference;
List<Cv> uploadedPDF;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cv_view);
        listView=findViewById(R.id.listView);
        listView.setBackgroundColor(Color.WHITE);
uploadedPDF=new ArrayList<Cv>();
retrievePDF();
listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Cv cv=uploadedPDF.get(i);
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setType("application/pdf");
        intent.setData(Uri.parse(cv.getUrl()));
        startActivity(intent);
    }
});

    }

    private void retrievePDF() {

     databaseReference= FirebaseDatabase.getInstance().getReference("uploads");
     databaseReference.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
           for(DataSnapshot ds:snapshot.getChildren()){
             Cv cv=ds.getValue(Cv.class);
             uploadedPDF.add(cv);

           }
           String[] uploadsName = new String[uploadedPDF.size()];


for(int i=0;i<uploadsName.length;i++){
    uploadsName[i]=uploadedPDF.get(i).getName();
}
             ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getApplicationContext(),
                     android.R.layout.simple_list_item_1,uploadsName){
                 @NonNull
                 @Override
                 public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    View view=super.getView(position,convertView,parent);
                     TextView textView=(TextView) view.findViewById(android.R.id.text1);

                     textView.setTextColor(Color.BLACK);
                     textView.setTextSize(20);


return view;                 }
             };
listView.setAdapter(arrayAdapter);
         }


         @Override
         public void onCancelled(@NonNull DatabaseError error) {

         }
     });
    }
}