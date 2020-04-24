package com.example.maptry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Hotspot extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Button submit;
    int f=0,i,j;
    String add="";
    EditText edit;
    DataSnapshot m2,m3;
    private DatabaseReference mref;
    double[][] value;
    double lat;
    double longi;
    long x;
    double[] price;
    double range;
    int idd;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        submit=findViewById(R.id.submit);
        edit=findViewById(R.id.edit);
 Intent i=getIntent();
   range=Double.parseDouble(i.getStringExtra("range"));
   idd=i.getIntExtra("id",1);
 Toast.makeText(this," "+i.getIntExtra("name",0),Toast.LENGTH_SHORT).show();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(Hotspot.this);
                progressDialog.setMessage("Please Wait..."); // Setting Message
                progressDialog.setTitle("Fetching Details...."); // Setting Title
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                progressDialog.show(); // Display Progress Dialog
                progressDialog.setCancelable(false);
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            Thread.sleep(8000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                    }
                }).start();
                mref = FirebaseDatabase.getInstance().getReference().child("College");

                mref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        long l = dataSnapshot.getChildrenCount();
                        m2 = dataSnapshot.child("sample").child("cor");

                        x = m2.getChildrenCount();

                        value = new double[(int) x][2];
                        price=new double[(int) x];
                        for (int i = 0; i < x; i++) {
                            String s = String.valueOf(i);
                            value[i][0] = (double) m2.child(s).child("lan").getValue();
                            value[i][1] = (double) m2.child(s).child("lon").getValue();
                            price[i]=(double) m2.child(s).child("price").getValue();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                add=edit.getText().toString();
                Geocoder gc=new Geocoder(Hotspot.this);
                List<Address> list= null;
                try {
                    mref.child("id").setValue(1);
                    list = gc.getFromLocationName(add,1);
                    Address address=list.get(0);
                    lat=address.getLatitude();
                    longi=address.getLongitude();
                    mref.child("mycor").child("lat").setValue(lat);
                    mref.child("mycor").child("long").setValue(longi);
                    mref.child("mycor").child("range").setValue(range);
                    Toast.makeText(getApplicationContext(),"latitide: "+lat+"Longitude: "+longi,Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                new CountDownTimer(7000,7000){
                    public void onTick(long millisUntilFinished) {

                    }

                    public void onFinish() {
                        if(idd==1) {
                            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                                    .findFragmentById(R.id.map);
                            mapFragment.getMapAsync(Hotspot.this);
                        }
                    }
                }.start();



            }
        });

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        LatLng[] xy = new LatLng[(int) x];
        for (int i = 0; i < x; i++) {
            xy[i] = new LatLng(value[i][0], value[i][1]);
        }
        mMap.clear();
        //Toast.makeText(getApplicationContext(), "S " + xy.length, Toast.LENGTH_SHORT).show();
        LatLng nag = new LatLng(lat, longi);
        mMap.addMarker(new MarkerOptions().position(nag).title("Marker in "+add));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(nag, 13F));


        for (i = 0; i < xy.length; i++) {

            if(price[i]<10) {
                mMap.addCircle(new CircleOptions()
                        .center(xy[i])
                        .radius(300)
                        .strokeColor(Color.RED)
                        .fillColor(Color.argb(80, 150, 50, 50))
                        .clickable(true));

            }
            else if(price[i]>10 && price[i]<30) {
                mMap.addCircle(new CircleOptions()
                        .center(xy[i])
                        .radius(300)
                        .strokeColor(Color.BLUE)
                        .fillColor(Color.argb(70,135, 206, 235))
                        .clickable(true));

            }
            else {
                mMap.addCircle(new CircleOptions()
                        .center(xy[i])
                        .radius(300)
                        .strokeColor(Color.GREEN)
                        .fillColor(Color.argb(170,34,139,34))
                        .clickable(true));


            }

            mMap.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {

                @Override
                public void onCircleClick(Circle circle) {

                    final LatLng center=circle.getCenter();
                    for ( j=0;j<price.length;j++){
                        if(value[j][0]== center.latitude && value[j][1]==center.longitude){
                            final AlertDialog.Builder box=new AlertDialog.Builder(Hotspot.this);
                            box.setMessage("Estimated Fare:"+price[j])
                                    .setCancelable(false)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                                            List<Address> addresses  = null;
                                            try {
                                                addresses = geocoder.getFromLocation(center.latitude,center.longitude, 1);
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                            String address = addresses.get(0).getAddressLine(0);

                                            Toast.makeText(getApplicationContext(),address+"  \n"+add,Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                                    Uri.parse("https://www.google.com/maps/dir/?api=1&origin="+add+"&destination="+address+"&travelmode=driving"));
                                            startActivity(intent);

                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //  Action for 'NO' Button
                                            dialog.cancel();

                                        }
                                    });
                            AlertDialog alert = box.create();

                            alert.setTitle("HotSpot:");
                            alert.show();
                            break;
                        }
                    }

                }
            });

            mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {


                @Override
                public void onMapLongClick(LatLng latLng) {

                }

            });

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.map_options, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Change the map type based on the user's selection.
        switch (item.getItemId()) {
            case R.id.normal_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                return true;
            case R.id.hybrid_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                return true;
            case R.id.satellite_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                return true;
            case R.id.terrain_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}

