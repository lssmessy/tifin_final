package com.tifinnearme.priteshpatel.tifinnearme;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MainActivity extends Activity implements LocationListener {
    private final LatLng MUMBAI=new LatLng(18.9750,72.8258);
    private GoogleMap map;
    private Location loc;
    private  LocationManager locationManager;
    GPSTracker myGps;
    Geocoder geocoder;
    EditText et;

    double lattitude,longitude;

    //private LocationManager location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et=(EditText)findViewById(R.id.editText1);
        //map.setMyLocationEnabled(true);

       myGps=new GPSTracker(MainActivity.this);
        if(myGps.canGetLocation()){

            lattitude=myGps.getLatitude();
            longitude=myGps.getLongitude();
            geocoder=new Geocoder(this, Locale.getDefault());
            List<Address> address= null;
            try {
                address = geocoder.getFromLocation(lattitude,longitude,1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String City=address.get(0).getAddressLine(0);
            String state=address.get(0).getAddressLine(1);
            String Country=address.get(0).getAddressLine(2);
            map=((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
            LatLng myLL = new LatLng(lattitude,longitude);
            map.addMarker(new MarkerOptions().position(myLL).title(City+","+state+","+Country));
            Toast.makeText(this, myLL.toString(), Toast.LENGTH_LONG).show();
            CameraUpdate myCam = CameraUpdateFactory.newLatLngZoom(myLL, 14);
            map.addPolygon(new PolygonOptions().add(myLL).fillColor(Color.GRAY));
            map.animateCamera(myCam);
            map.setMyLocationEnabled(true);
            et.setText(City+","+state+","+Country);
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

        InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(),0);
        geocoder=new Geocoder(this, Locale.getDefault());

        List<Address> addresss= null;
        try {
            addresss = geocoder.getFromLocationName(et.getText().toString(),1);
            Address add=addresss.get(0);
            if(addresss.size() > 0)
            {
                lattitude=addresss.get(0).getLatitude();
                longitude=addresss.get(0).getLongitude();
            }
            List<Address> ad=geocoder.getFromLocation(lattitude,longitude,1);
            String City=ad.get(0).getAddressLine(0);
            String state=ad.get(0).getAddressLine(1);
            String Country=ad.get(0).getAddressLine(2);
            LatLng newLoc=new LatLng(lattitude,longitude);
            CameraUpdate showLoc= CameraUpdateFactory.newLatLngZoom(newLoc,16);
            map.animateCamera(showLoc);
            map.addCircle(new CircleOptions().center(newLoc).radius(500).fillColor(Color.TRANSPARENT));
            map.addMarker(new MarkerOptions().position(newLoc).title(City+","+state+","+Country));
            et.setText(City+","+state+","+Country);

        } catch (IOException e) {
            e.printStackTrace();
        }





    }

    public void removeText(View view){
        et.setText("");
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
        loc.setLatitude(0.0);
        loc.setLongitude(0.0);
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
