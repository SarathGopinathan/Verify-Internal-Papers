package com.laytest.hacker.vip;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

//import com.firebase.client.Firebase;
//import com.firebase.client.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button login,register;
//    Firebase mref;
    ArrayList p=new ArrayList();
    ArrayList de=new ArrayList();
    ArrayList out=new ArrayList();
    String pst,depat,chk_uname,chk_password,uname,passwd;
    String TAG="MainActivity";
    private DatabaseReference root,root2;
    int base_post=0,base_dept=0;
    int z,verify_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login=(Button)findViewById(R.id.login);
        register=(Button)findViewById(R.id.register);
        SharedPreferences sp = MainActivity.this.getPreferences(Context.MODE_PRIVATE);

        if(sp.getString("post","").equals("DEAN")){
            startActivity(new Intent(MainActivity.this,Dean.class));
            finish();
        }
        else if(sp.getString("post","").equals("HOD")){
            startActivity(new Intent(MainActivity.this,HoD.class));
            finish();
        }
        else if(sp.getString("post","").equals("ASSISTANT PROFESSOR")){
            startActivity(new Intent(MainActivity.this,Assistant_prof.class));
            finish();
        }


//        mref=new Firebase("https://vip-8c41d.firebaseio.com/");

        p.add("DEAN");
        p.add("HOD");
        p.add("ASSISTANT PROFESSOR");

        de.add("CSE");
        de.add("ECE");
        de.add("MECH");

        final ArrayAdapter ad=new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,p);
        final ArrayAdapter ada=new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,de);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog di=new Dialog(MainActivity.this);
                di.requestWindowFeature(Window.FEATURE_NO_TITLE);
                di.setContentView(R.layout.login);

                final Spinner post =(Spinner)di.findViewById(R.id.post);
                final Spinner dept =(Spinner)di.findViewById(R.id.dept);
                final EditText un =(EditText)di.findViewById(R.id.un);
                final EditText pswd =(EditText)di.findViewById(R.id.pswd);
                final Button logi =(Button)di.findViewById(R.id.log);

                post.setAdapter(ad);
                dept.setAdapter(ada);


                post.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                {
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                    {
                        pst = parent.getItemAtPosition(position).toString();
                        //Toast.makeText(MainActivity.this,pst, Toast.LENGTH_SHORT).show();
                        if(pst.equals("DEAN"))
                        {
                            base_post=1;
                            dept.setVisibility(view.INVISIBLE);
                        }
                        else if (pst.equals("HOD")){
                            base_post=2;
                            dept.setVisibility(view.VISIBLE);
                        }
                        else{
                            base_post=3;
                            dept.setVisibility(view.VISIBLE);
                        }
                    }
                    public void onNothingSelected(AdapterView<?> parent)
                    {

                    }
                });

                dept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                {
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                    {
                        depat = parent.getItemAtPosition(position).toString();
                        if(depat.equals("CSE"))
                        {
                            base_dept=1;
                        }
                        else if (depat.equals("ECE")){
                            base_dept=2;
                        }
                        else if (depat.equals("MECH")){
                            base_dept=3;
                        }
                       // Toast.makeText(MainActivity.this,depat, Toast.LENGTH_SHORT).show();
                    }
                    public void onNothingSelected(AdapterView<?> parent)
                    {

                    }
                });

                logi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Toast.makeText(MainActivity.this,"Working on it",Toast.LENGTH_SHORT).show();
                        chk_uname=un.getText().toString();
                        chk_password=pswd.getText().toString();
                        checkuser(base_post,base_dept,chk_uname,chk_password);

                    }
                });

                di.show();

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog d=new Dialog(MainActivity.this);
                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.setContentView(R.layout.register);

                final Spinner post =(Spinner)d.findViewById(R.id.post);
                final Spinner dept =(Spinner)d.findViewById(R.id.dept);
                final EditText un =(EditText)d.findViewById(R.id.un);
                final EditText pswd =(EditText)d.findViewById(R.id.pswd);
                final Button reg =(Button)d.findViewById(R.id.reg);

                post.setAdapter(ad);
                dept.setAdapter(ada);

                post.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                {
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                    {
                        pst = parent.getItemAtPosition(position).toString();
                        Toast.makeText(MainActivity.this,pst, Toast.LENGTH_SHORT).show();
                        if(pst.equals("DEAN"))
                        {
                            dept.setVisibility(view.INVISIBLE);
                        }
                        else
                            dept.setVisibility(view.VISIBLE);
                    }
                    public void onNothingSelected(AdapterView<?> parent)
                    {

                    }
                });

                dept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                {
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                    {
                        depat = parent.getItemAtPosition(position).toString();
                        Toast.makeText(MainActivity.this,depat, Toast.LENGTH_SHORT).show();
                    }
                    public void onNothingSelected(AdapterView<?> parent)
                    {

                    }
                });

                reg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(pst.equals("DEAN")){

                            root= FirebaseDatabase.getInstance().getReference().child(pst);
                            root2= FirebaseDatabase.getInstance().getReference().child(pst).child("username");

                            root2.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.getValue(String.class).isEmpty()){
                                        Map<String,Object> map=new HashMap<String,Object>();

                                        passwd=pswd.getText().toString();
                                        uname=un.getText().toString();

                                        map.put("password",pswd.getText().toString());
                                        map.put("username",un.getText().toString());

                                        root.updateChildren(map);
                                        Toast.makeText(MainActivity.this, "Registered successfully!Please Login!", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                        Toast.makeText(MainActivity.this, "Already Registered!Please Login!", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        }

                        if(pst.equals("HOD")){
                            root= FirebaseDatabase.getInstance().getReference().child(pst).child(depat);
                            root2= FirebaseDatabase.getInstance().getReference().child(pst).child(depat).child("username");

                            root2.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.getValue(String.class).isEmpty()){
                                        Map<String,Object> map=new HashMap<String,Object>();

                                        passwd=pswd.getText().toString();
                                        uname=un.getText().toString();

                                        map.put("password",pswd.getText().toString());
                                        map.put("username",un.getText().toString());

                                        root.updateChildren(map);
                                        Toast.makeText(MainActivity.this, "Registered successfully!Please Login!", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                        Toast.makeText(MainActivity.this, "Already Registered!Please Login!", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        }

                        if(pst.equals("ASSISTANT PROFESSOR")){

                            root= FirebaseDatabase.getInstance().getReference().child(pst);
                            root2= FirebaseDatabase.getInstance().getReference().child(pst).child(depat);

                            root2.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for(DataSnapshot ds : dataSnapshot.getChildren()){
                                        if(ds.getKey().equals(un.getText().toString())){
                                            z=1;
                                            Toast.makeText(MainActivity.this, "Already Registered!Please Login!", Toast.LENGTH_SHORT).show();
                                        }
                                        Log.d(TAG,"child:"+ds.getKey());
                                    }
                                    if(z==1){
                                    }
                                    else{

                                        //Map<String,Object> map=new HashMap<String,Object>();

                                        passwd=pswd.getText().toString();
                                        uname=un.getText().toString();

                                        //map.put("password",pswd.getText().toString());
                                        //map.put(un.getText().toString(),null);

                                        try{
                                            root2.child(uname).setValue(root2.child(uname).child("password").setValue(passwd));
                                        }
                                        catch (Exception e){
                                            Toast.makeText(MainActivity.this, "Registered Successfully!Please Login!", Toast.LENGTH_SHORT).show();
                                        }

                                        //root2.setValue(un.getText().toString());

                                        //root2.updateChildren(map);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }


//                        DatabaseReference msg_root=root.child(temp_key);
//                        Map<String,Object>map2=new HashMap<String,Object>();
//
//
//                        msg_root.updateChildren(map2);

//                        progress.setMessage("Saving...");
//                        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//                        progress.setIndeterminate(true);
//                        progress.setCancelable(false);
//                        progress.show();
//
//                        progress.dismiss();
//
//                        d.dismiss();
                    }
                });


                d.show();

            }
        });

    }

    private void checkuser(int post, final int department, final String funame, String fpassword) {
        //String chk = dataSnapshot.getValue().toString();
        Log.d(TAG,"post:"+post);
        Log.d(TAG,"department:"+department);
        if(post== 0 && department== 0){
            Toast.makeText(MainActivity.this,"empty",Toast.LENGTH_SHORT).show();
        }
        else if(post==1){

//            root= FirebaseDatabase.getInstance().getReference().child(pst);
//            root2= FirebaseDatabase.getInstance().getReference().child("ASSISTANT PROFESSOR").child(depat);
//            Log.d(TAG,"child:"+root2.getParent().getKey());
//
//            root2.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//
//
//
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });

            root= FirebaseDatabase.getInstance().getReference().child(pst).child("username");
            root2= FirebaseDatabase.getInstance().getReference().child(pst).child("password");

            root.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(funame.equals(dataSnapshot.getValue(String.class))){
                        root2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(chk_password.equals(dataSnapshot.getValue(String.class)))
                                {
                                    SharedPreferences sp = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putString("username",chk_uname);
                                    editor.commit();
                                    startActivity(new Intent(MainActivity.this,Dean.class));
                                    finish();
                                }
                                else{
                                    Toast.makeText(MainActivity.this,"wrong password",Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                    else{
                        Log.i(TAG,"dudee:"+dataSnapshot.getValue(String.class));
                        Log.i(TAG,"dudee:"+funame);
                        Toast.makeText(MainActivity.this,"wrong username",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else if(post==2 && department==1){
            root= FirebaseDatabase.getInstance().getReference().child(pst).child(depat).child("username");
            root2= FirebaseDatabase.getInstance().getReference().child(pst).child(depat).child("password");

            root.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(chk_uname.equals(dataSnapshot.getValue(String.class))){
                        root2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(chk_password.equals(dataSnapshot.getValue(String.class))){

                                    SharedPreferences sp = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putString("username",chk_uname);
                                    editor.putString("department",depat);
                                    editor.putString("post",pst);
                                    editor.commit();
                                    startActivity(new Intent(MainActivity.this,HoD.class));
                                    finish();
                                }
                                else
                                    Toast.makeText(MainActivity.this,"wrong password",Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                    else{
                        Toast.makeText(MainActivity.this,"wrong username",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else if(post==2 && department==2){
            root= FirebaseDatabase.getInstance().getReference().child(pst).child(depat).child("username");
            root2= FirebaseDatabase.getInstance().getReference().child(pst).child(depat).child("password");

            root.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(chk_uname.equals(dataSnapshot.getValue(String.class))){
                        root2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(chk_password.equals(dataSnapshot.getValue(String.class))){
                                    SharedPreferences sp = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putString("username",chk_uname);
                                    editor.putString("department",depat);
                                    editor.putString("post",pst);
                                    editor.commit();
                                    startActivity(new Intent(MainActivity.this,HoD.class));
                                    finish();
                                }
                                else
                                    Toast.makeText(MainActivity.this,"wrong password",Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                    else{
                        Toast.makeText(MainActivity.this,"wrong username",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else if(post==2 && department==3){
            root= FirebaseDatabase.getInstance().getReference().child(pst).child(depat).child("username");
            root2= FirebaseDatabase.getInstance().getReference().child(pst).child(depat).child("password");

            root.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(chk_uname.equals(dataSnapshot.getValue(String.class))){
                        root2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(chk_password.equals(dataSnapshot.getValue(String.class))){
                                    SharedPreferences sp = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putString("username",chk_uname);
                                    editor.putString("department",depat);
                                    editor.putString("post",pst);
                                    editor.commit();
                                    startActivity(new Intent(MainActivity.this,HoD.class));
                                    finish();
                                }
                                else
                                    Toast.makeText(MainActivity.this,"wrong password",Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                    else{
                        Toast.makeText(MainActivity.this,"wrong username",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else if(post==3 && department==1){
            root= FirebaseDatabase.getInstance().getReference().child(pst).child(depat);

            root.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds : dataSnapshot.getChildren()){
                        if(ds.getKey().equals(chk_uname)){
                            verify_login=1;
                        }
                    }
                    if(verify_login==1){
                        root2= FirebaseDatabase.getInstance().getReference().child(pst).child(depat).child(chk_uname).child("password");

                        root2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                if(chk_password.equals(dataSnapshot.getValue(String.class))){
                                    SharedPreferences sp = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putString("username",chk_uname);
                                    editor.putString("department",depat);
                                    editor.putString("post",pst);
                                    editor.commit();
                                    startActivity(new Intent(MainActivity.this,Assistant_prof.class));
                                    finish();
                                }
                                else
                                    Toast.makeText(MainActivity.this,"wrong password",Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else if(post==3 && department==2){
            root= FirebaseDatabase.getInstance().getReference().child(pst).child(depat);

            root.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds : dataSnapshot.getChildren()){
                        if(ds.getKey().equals(chk_uname)){
                            verify_login=1;
                        }
                    }
                    if(verify_login==1){
                        root2= FirebaseDatabase.getInstance().getReference().child(pst).child(depat).child(chk_uname).child("password");

                        root2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                if(chk_password.equals(dataSnapshot.getValue(String.class))){
                                    SharedPreferences sp = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putString("username",chk_uname);
                                    editor.putString("department",depat);
                                    editor.putString("post",pst);
                                    editor.commit();
                                    startActivity(new Intent(MainActivity.this,Assistant_prof.class));
                                    finish();
                                }
                                else
                                    Toast.makeText(MainActivity.this,"wrong password",Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else if(post==3 && department==3){
            root= FirebaseDatabase.getInstance().getReference().child(pst).child(depat);

            root.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds : dataSnapshot.getChildren()){
                        if(ds.getKey().equals(chk_uname)){
                            verify_login=1;
                        }
                    }
                    if(verify_login==1){
                        root2= FirebaseDatabase.getInstance().getReference().child(pst).child(depat).child(chk_uname).child("password");

                        root2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                if(chk_password.equals(dataSnapshot.getValue(String.class))){
                                    SharedPreferences sp = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putString("username",chk_uname);
                                    editor.putString("department",depat);
                                    editor.putString("post",pst);
                                    editor.commit();
                                    startActivity(new Intent(MainActivity.this,Assistant_prof.class));
                                    finish();
                                }
                                else
                                    Toast.makeText(MainActivity.this,"wrong password",Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else{
            root= FirebaseDatabase.getInstance().getReference().child(pst).child("password");

            root.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.i(TAG,"dudee:"+dataSnapshot.getValue(String.class));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }

}
