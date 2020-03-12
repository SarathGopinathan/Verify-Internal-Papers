package com.laytest.hacker.vip;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class Assistant_prof extends AppCompatActivity {
    TextView username;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    private DatabaseReference root,root2;
    EditText name;
    Button upload,edit,logout;
    String viewurl;
    int pick=100;
    ProgressDialog progress;
    StorageReference sr;
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assistant_prof);

        username=(TextView)findViewById(R.id.tv_username);
        name=(EditText) findViewById(R.id.name);

//        add_one=(Button)findViewById(R.id.b1);
//        add_two=(Button)findViewById(R.id.b2);
//        add_three=(Button)findViewById(R.id.b3);
//        add_four=(Button)findViewById(R.id.b4);
//        add_five=(Button)findViewById(R.id.b5);
//        add_six=(Button)findViewById(R.id.b6);
        sr= FirebaseStorage.getInstance().getReference();
        upload=(Button)findViewById(R.id.b_upload);
        edit=(Button)findViewById(R.id.b_edit);
        logout=(Button)findViewById(R.id.b_logout);

        username.setText("fatima");
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getContent();

            }
        });
    }

    private void getContent() {

        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("application/pdf");
        startActivityForResult(i,pick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if(requestCode == pick) {

                final Uri path = data.getData();
                final StorageReference stref = sr.child("CSE/" + name.getText().toString());
                progress = new ProgressDialog(this);
                progress.setMessage("UPLOADING...");
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setIndeterminate(true);
                progress.setCancelable(false);
                progress.show();
                Log.d("path=", "" + path);
                stref.putFile(path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progress.dismiss();
                        Toast.makeText(Assistant_prof.this, "UPLOADED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                        viewurl = taskSnapshot.getDownloadUrl().toString();
                        gg();
                    }
                });

            }
        }
    }

    private void gg() {

        root= FirebaseDatabase.getInstance().getReference().child("HOD").child("CSE").child("ALLURL");

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Map<String,Object> map=new HashMap<String,Object>();

                map.put(name.getText().toString(),viewurl);

                root.updateChildren(map);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
