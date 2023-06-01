package com.example.mobileproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
public class uploadActivity extends AppCompatActivity {
Button upload_btn;
EditText pdf_name;

StorageReference storageReference;
DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        upload_btn=findViewById(R.id.btn_upload);

pdf_name=findViewById(R.id.text_pdf);

storageReference= FirebaseStorage.getInstance().getReference();
databaseReference= FirebaseDatabase.getInstance().getReference("uploads");
   upload_btn.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
         selectFiles();
       }
   });


    }

    private void selectFiles() {
        Intent intent=new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"select PDF files"),1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK&& data!=null && data.getData()!=null){
            uploadFiles(data.getData());
        }


    }

    private void uploadFiles(Uri data) {
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("uploading..");
        progressDialog.show();

        StorageReference reference=storageReference.child("Uploads/"+System.currentTimeMillis()+".pdf");
        reference.putFile(data) .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
while (!uriTask.isComplete());
Uri url=uriTask.getResult();
pdfClass pdfClass=new pdfClass(pdf_name.getText().toString(),url.toString());
databaseReference.child(databaseReference.push().getKey()).setValue(pdfClass);
Toast.makeText(uploadActivity.this,"File uploaded",Toast.LENGTH_SHORT);
progressDialog.dismiss();

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                double
                        progress=(100.0* snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                progressDialog.setMessage("uploading  "+(int)progress+"%");

            }
        });


    }

}