package com.decoders.wanderlustapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.maps.android.SphericalUtil;

public class fragment2 extends Fragment {
    private RecyclerView mRecyclerview;
    private DatabaseReference mRef;
    ProgressBar progress;
    Button directionbutton;
    TextView address,phone,name,distance;
    FirebaseDatabase mfireDatabase;
    DatabaseReference databaseReference;
    Double distance1;
    int dis;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_2layout, container, false);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Hotels");
        mRef= FirebaseDatabase.getInstance().getReference().child("Hotels");
        mRef.keepSynced(true);
        progress=view.findViewById(R.id.progess);
        progress.setVisibility(View.VISIBLE);
        mRecyclerview = (RecyclerView)view.findViewById(R.id.recyclerview);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        mfireDatabase = FirebaseDatabase.getInstance();


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Query query = databaseReference.orderByChild("title");
        FirebaseRecyclerAdapter<Blog,ViewHolder> firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<Blog, ViewHolder>(
                Blog.class,
                R.layout.blow_row,
                ViewHolder.class,
                query
        ) {
            @Override
            protected void populateViewHolder(ViewHolder viewHolder, final Blog blog, final int i) {
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(),blog.getTitle(), Toast.LENGTH_LONG).show();
                    }
                });
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewGroup v1=(ViewGroup)getView().findViewById(R.id.content);
                        final View Dialog = LayoutInflater.from(getContext()).inflate(R.layout.description_dialog, (ViewGroup) v,false);
                        AlertDialog.Builder b = new AlertDialog.Builder(getContext());
                        b.setView(Dialog);
                        final AlertDialog alertDialog=b.create();
                        directionbutton=Dialog.findViewById(R.id.directionbutton);
                        distance=Dialog.findViewById(R.id.distance);
                        name=Dialog.findViewById(R.id.name);
                        address=Dialog.findViewById(R.id.add);
                        phone=Dialog.findViewById(R.id.phone);
                        address.setText("ADDRESS  : " + blog.getAddress());
                        phone.setText("MOBILE    : "+blog.getPhone());
                        name.setText(blog.getTitle());
                        distance.setText("DISTANCE : "+String.valueOf(distance1)+" M");
                        directionbutton.setOnClickListener(new View.OnClickListener() {
                            String latitude="12.9165";
                            String longitude="79.1325";
                            @Override
                            public void onClick(View v) {
                                Uri gmmIntentUri=Uri.parse("google.navigation:q=" + blog.getLongitude() + "," + blog.getLongitude());
                                Intent mapIntent = new Intent(Intent.ACTION_VIEW,gmmIntentUri);
                                mapIntent.setPackage("com.google.android.apps.maps");
                                startActivity(mapIntent);
                            }
                        });
                        alertDialog.show();
                    }
                });




                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

                final String lati1=sharedPreferences.getString("lati","0");

                final String longi1=sharedPreferences.getString("longi","0");

                LatLng location1 = new LatLng(Double.valueOf(lati1),Double.valueOf(longi1));

                String lati2=blog.getLatitude();

                String longi2=blog.getLongitude();

                LatLng location2 = new LatLng(Double.valueOf(lati2),Double.valueOf(longi2));

                distance1 = SphericalUtil.computeDistanceBetween(location1, location2);













                viewHolder.setDetails(getContext(),blog.getTitle(),blog.getDesc(),blog.getImage());
                progress.setVisibility(View.INVISIBLE);
                mRecyclerview.setVisibility(View.VISIBLE);


            }
        };
        mRecyclerview.setAdapter(firebaseRecyclerAdapter);


    }

}
