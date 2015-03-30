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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
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
    private ConnectivityManager connectivityManager;
    double lattitude,longitude;

    //private LocationManager location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et=(EditText)findViewById(R.id.editText1);

        checkLocation();

    }

    public void checkLocation(){


        if(isInternetAvailable()==false)
        {
            Log.i("Internet", "Disabled");
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("No internet connection is available. Please check for internet connection");
            alert.setTitle("No Internet");
            alert.setNegativeButton("Ok",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alert.show();
        }

        else if(isInternetAvailable()) {
            Log.i("Internet","Available");
            myGps=new GPSTracker(MainActivity.this);
            if (myGps.canGetLocation()) {

                this.lattitude = myGps.getLatitude();
                this.longitude = myGps.getLongitude();
                geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> address = null;
                try {
                    address = geocoder.getFromLocation(lattitude, longitude, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (address != null) {
                    String City = address.get(0).getAddressLine(0);
                    String state = address.get(0).getAddressLine(1);
                    String Country = address.get(0).getAddressLine(2);
                    map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
                    LatLng myLL = new LatLng(lattitude, longitude);
                    map.addMarker(new MarkerOptions().position(myLL).title(City + "," + state + "," + Country));
                    Toast.makeText(this, myLL.toString(), Toast.LENGTH_LONG).show();
                    CameraUpdate myCam = CameraUpdateFactory.newLatLngZoom(myLL, 14);
                   // map.addPolygon(new PolygonOptions().add(myLL).fillColor(Color.GRAY));
                    map.animateCamera(myCam);
                    map.addCircle(new CircleOptions().center(myLL).visible(true).radius(200));
                    map.setMyLocationEnabled(true);
                    et.setText(City + "," + state + "," + Country);

                    map.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                        @Override
                        public boolean onMyLocationButtonClick() {
                            map.clear();
                            checkLocation();
                            return true;
                        }
                    });
                }
            }
        }

      

    }

    @Override
    protected void onPostResume() {

        super.onPostResume();
        checkLocation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkLocation();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        checkLocation();
    }

    //To check whether internet is ON/OFF
    public boolean isInternetAvailable(){
        connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState()== NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState()== NetworkInfo.State.CONNECTED)

            return  true;

       else if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState()== NetworkInfo.State.DISCONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState()== NetworkInfo.State.DISCONNECTED)
        {

            return false;
        }

        return  false;

    }


    public void showCurrentLocation(View view) {
        
        if(!isempty()) {

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
            geocoder = new Geocoder(this, Locale.getDefault());

            List<Address> addresss = null;
            try {
                addresss = geocoder.getFromLocationName(et.getText().toString(), 1);
                Address add = addresss.get(0);

                if (addresss.size() > 0) {
                    lattitude = addresss.get(0).getLatitude();
                    longitude = addresss.get(0).getLongitude();
                    Toast.makeText(this, String.valueOf(lattitude) + "=>" + String.valueOf(longitude), Toast.LENGTH_SHORT).show();
                }
                List<Address> ad = geocoder.getFromLocation(lattitude, longitude, 1);
                String City = ad.get(0).getAddressLine(0);
                String state = ad.get(0).getAddressLine(1);
                String Country = ad.get(0).getAddressLine(2);
                Log.i("latlng", String.valueOf(lattitude) + "=>" + String.valueOf(longitude));

                LatLng newLoc = new LatLng(lattitude, longitude);
                CameraUpdate showLoc = CameraUpdateFactory.newLatLngZoom(newLoc, 16);
                if (showLoc != null) {
                    map.clear();
                    map.animateCamera(showLoc);
                    map.addCircle(new CircleOptions().center(newLoc).visible(true).radius(200));

                    map.addMarker(new MarkerOptions().position(newLoc).title(City + "," + state + "," + Country));
                    et.setText(City + "," + state + "," + Country);

                } else {
                    Toast.makeText(this, String.valueOf(lattitude) + "=>" + String.valueOf(longitude), Toast.LENGTH_SHORT).show();
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (IndexOutOfBoundsException e) {

                e.printStackTrace();
            }


        }
        else  Toast.makeText(this, "Enter something to search", Toast.LENGTH_LONG).show();

    }

    private boolean isempty() {
        if(et.getText().length() > 0)
            return false;
        else
        return true;
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
