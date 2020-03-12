package com.laytest.hacker.vip;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageReference;
import com.laytest.hacker.vip.Adapters.Hod_Adapter;
import com.laytest.hacker.vip.Models.Hod_Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HoD extends AppCompatActivity {
    RecyclerView papers;
    private DatabaseReference root,root2;
    private List<Hod_Model> paperlist = new ArrayList<>();
    private Hod_Adapter mAdapter;
    private ArrayList names = new ArrayList();
    private ArrayList urls = new ArrayList();
    String department,username,delete;
    TextView name;
    int chk=0;

    ProgressDialog progress;
    StorageReference sr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ho_d);

        SharedPreferences sp = HoD.this.getPreferences(Context.MODE_PRIVATE);
        department=sp.getString("department","");
        username=sp.getString("username","");

        papers = (RecyclerView) findViewById(R.id.rv_allpapers);
        name = (TextView) findViewById(R.id.un);

        name.setText(username);

        progress = new ProgressDialog(this);
        progress.setMessage("LOADING...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();

        root= FirebaseDatabase.getInstance().getReference().child("HOD").child("CSE").child("ALLURL");

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    names.add(ds.getKey().toString());
                    urls.add(ds.getValue().toString());
                    Log.d("bk:",""+ds.getValue().toString());
                }
                gg();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        papers.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), papers, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }
            @Override
            public void onLongClick(View view,final int position) {

                Hod_Model model = paperlist.get(position);
                final DatabaseReference root;
                Log.d("one:","one");
                Map<String,Object> map=new HashMap<String,Object>();
                map.clear();
                map.put(paperlist.get(position).getPaper(),paperlist.get(position).getUrl());
                FirebaseDatabase.getInstance().getReference().child("HOD").child("CSE").child("APPROVED").setValue(map);

                }
        }));

    }

    private void gg() {

        for(int i = 0;i<names.size();i++){

            Hod_Model samplep = new Hod_Model(names.get(i).toString(),urls.get(i).toString());
            paperlist.add(samplep);

        }
        mAdapter = new Hod_Adapter(paperlist,HoD.this);

        papers.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        papers.setLayoutManager(mLayoutManager);
        papers.setItemAnimator(new DefaultItemAnimator());
        papers.setAdapter(mAdapter);
        progress.dismiss();

    }
}
