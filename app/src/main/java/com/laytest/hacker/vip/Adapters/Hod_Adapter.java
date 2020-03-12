package com.laytest.hacker.vip.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.laytest.hacker.vip.HoD;
import com.laytest.hacker.vip.Models.Hod_Model;
import com.laytest.hacker.vip.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Hacker on 9/30/2017.
 */

public class Hod_Adapter extends RecyclerView.Adapter<Hod_Adapter.MyViewHolder>{

    private List<Hod_Model> list;
    private Context c;
    int lastPosition = -1;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public Button open,approve;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            open = (Button) view.findViewById(R.id.open);
        }
    }

    public Hod_Adapter(List<Hod_Model> moviesList,Context c) {
        this.list = moviesList;
        this.c = c;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hod_adap_layout, parent, false);

        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Hod_Model p = list.get(position);
        holder.name.setText(p.getPaper());
        holder.open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(p.getUrl()), "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Intent newIntent = Intent.createChooser(intent, "Open File");
                c.startActivity(newIntent);

            }
        });

        if(position >lastPosition) {

            Animation animation = AnimationUtils.loadAnimation(c,
                    R.anim.up_from_bottom);
            holder.itemView.startAnimation(animation);
            lastPosition = position;
        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

}
