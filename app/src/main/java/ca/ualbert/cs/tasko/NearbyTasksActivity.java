/*
 * Copyright © 2018 Chase Buhler, Imtihan Ahmed, Thomas Lafrance, Ryan Romano, Stephen Packer,
 * Alden Emerson Ern Tan
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ca.ualbert.cs.tasko;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ca.ualbert.cs.tasko.data.DataManager;

public class NearbyTasksActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1111;
    private static final float DEFAULT_ZOOM = 15f;
    private static final String TAG = "NearbyTasksActivity";
    private Boolean mLocationPermission = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location currentLocation;
    private Activity thisActivity = this;
    //widgets
    private EditText mSearchText;
    private ImageView mGps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        //View contentView = inflater.inflate(R.layout.activity_nearby_tasks, null, false);
        //drawerLayout.addView(contentView, 0);
        setContentView(R.layout.activity_nearby_tasks);
        //mSearchText = (EditText)  findViewById(R.id.nearby_tasks_location_search_text);
        mGps = (ImageView) findViewById(R.id.ic_gps);
        getLocationPermission();

    }

    private void init(){

/*
        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH ||
                        actionId == EditorInfo.IME_ACTION_DONE ||
                        actionId == KeyEvent.ACTION_DOWN ||
                        actionId == KeyEvent.KEYCODE_ENTER){
                    //execute search
                    geoLocate();
                }
                return false;
            }
        });
*/

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.

                LatLng address = place.getLatLng();
                moveCamera(new LatLng(address.latitude, address.longitude), DEFAULT_ZOOM, place.getAddress().toString());
                Log.i(TAG, "Place: " + place.getName());
            }

            @Override
            public void onError(com.google.android.gms.common.api.Status status) {
                Log.i(TAG, "An error occurred: " + status);
            }

        });
        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDeviceLocation();
            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return false;
            }
        });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent = new Intent(thisActivity, ViewSearchedTaskDetailsActivity.class);
                intent.putExtra("TaskID", marker.getTag().toString());
                startActivity(intent);

            }
        });

    }

    public void displayNearbyTasks() {

        try {
            TaskList tasks = new TaskList();
            ArrayList<Task> listOfTasks = new ArrayList();
            tasks = DataManager.getInstance().getTasksByLatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            
            listOfTasks = tasks.getTasks();

            for( Task task : listOfTasks){
                try {
                    mMap.addMarker(new MarkerOptions()
                            .position(task.getGeolocation())
                            .title(task.getTaskName())
                            .snippet(task.getDescription()))
                            .setTag(task.getId());
                    Log.i("Here's a task", " Title:" + task.getTaskName());
                } catch (Exception e){
                    Log.i("TaskNoLocation", " task has no location");

                }

            }

            Toast.makeText(this, "Showing nearby tasks", Toast.LENGTH_SHORT).show();

        } catch (Exception e){
            Toast.makeText(this, "Could not get tasks", Toast.LENGTH_SHORT).show();
            Log.i("NearbyTasksError", "Error is " + e.toString());
        }

        //TODO
    }

    private void geoLocate(){

        String searchString = mSearchText.getText().toString();

        Geocoder geocoder = new Geocoder(NearbyTasksActivity.this);
        List<Address> list = new ArrayList<>();
        try{
            list = geocoder.getFromLocationName(searchString, 1);
        }catch (IOException e){
            Log.e("NearbyTasksActivity", "geolocate IOException" + e.getMessage());
        }

        if (list.size() > 0){
            Address address = list.get(0);
            Toast.makeText(this, address.toString(), Toast.LENGTH_SHORT).show();
            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM, address.getAddressLine(0));
        }
    }
    public void initMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "Map online", Toast.LENGTH_SHORT).show();
        mMap = googleMap;

        if (mLocationPermission) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                    //Location permission not granted
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(NearbyTasksActivity.this));
            init();
        }
        // Add a marker in Sydney and move the camera
        /*
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }

    private void getLocationPermission(){
        String[]  permissions = {FINE_LOCATION, COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(), COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED){
                mLocationPermission = true;
                initMap();
            }else {
                ActivityCompat.requestPermissions(this,permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else {
            ActivityCompat.requestPermissions(this,permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    public void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults ){
        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for(int i = 0; i < grantResults.length;i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermission = false;
                            return;
                        }
                    }
                    mLocationPermission = true;
                    initMap();
                }


            }
        }
    }

    private void getDeviceLocation(){
        Log.d("DeviceLocation", "Getting user location");
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try{
            if(mLocationPermission){
                com.google.android.gms.tasks.Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener(){
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task task){
                        if(task.isSuccessful()){
                            Log.d("onComplete", "location found");
                            currentLocation = (Location) task.getResult();
                            try{
                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM, "My location");
                            //puts the tasks on the map
                            displayNearbyTasks();
                            } catch (Exception e){
                                Log.d("onComplete1","current location is null");
                                Toast.makeText(getApplicationContext(), "Could not get location", Toast.LENGTH_LONG).show();

                            }
                        }else{
                            Log.d("onComplete","current location is null");
                            Toast.makeText(NearbyTasksActivity.this,
                                    "Current location unavailable", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        }catch (SecurityException e){
            Log.e("getDeviceLocation", "Security Exception: " + e.getMessage());
        }
    }

    private void moveCamera(LatLng latlng, float zoom, String title){
        Log.e("moveCamera", "move camera to current location");
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, zoom));


    }
}
