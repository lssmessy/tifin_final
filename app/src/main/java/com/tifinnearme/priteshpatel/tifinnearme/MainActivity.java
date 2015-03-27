package com.tifinnearme.priteshpatel.tifinnearme;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MainActivity extends Activity implements LocationListener {
    private final LatLng MUMBAI=new LatLng(18.9750,72.8258);
    private GoogleMap map;
    private Location loc;
    private  LocationManager locationManager;
    GPSTracker myGps;
    //double lattitude,longitude;

    //private LocationManager location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //map.setMyLocationEnabled(true);

       myGps=new GPSTracker(MainActivity.this);
        if(myGps.canGetLocation()){

            double lattitude=myGps.getLatitude();
            double longitude=myGps.getLongitude();
            map=((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
            LatLng myLL = new LatLng(lattitude,longitude);
            map.addMarker(new MarkerOptions().position(myLL).title("Current Location"));
            Toast.makeText(this, myLL.toString(), Toast.LENGTH_LONG).show();
            CameraUpdate myCam = CameraUpdateFactory.newLatLngZoom(myLL, 14);
            map.animateCamera(myCam);
            map.setMyLocationEnabled(true);

        }
        else
        {

            Runnable rn=new Runnable() {
                @Override
                public void run() {


                        myGps.showSettingsAlert();

                    }
                };

        }


    }

    public void showCurrentLocation(View view){
        CameraUpdate showLoc= CameraUpdateFactory.newLatLngZoom(MUMBAI,16);
        map.animateCamera(showLoc);
        map.addCircle(new CircleOptions().center(MUMBAI).radius(500).fillColor(Color.TRANSPARENT));
        map.addMarker(new MarkerOptions().position(MUMBAI).title("Mumbai"));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
