package ict2105.team10project.dailychallenges;

/**
 * Created by ckahsheng on 8/3/2017.
 */

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Config;
import com.firebase.client.Firebase;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import ict2105.team10project.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,LocationListener{

    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    TextView mTextField;
    TextView displayTimer;
    TextView displayDistance;

    Button startTracking_Btn;
    Button storeIntoDb;
    private CountDownTimer countDownTimer;
    /**
     * Create 4 variables
     * Current Lat Long
     * New Lat Long
     *
     */
    static Double currentLat;
    static Double currentLong;
    static Double newLat;
    static Double newLong;
    static float distanceTravelled;
    static boolean StartPressed;
    static int timingForApp=60;
    /**
     * Purpose of this arraylist is to store the location
     */
    private ArrayList<LatLng>cordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        //latLngList = new ArrayList<LatLng>();
        /**
         * Initialize all the values from the button and TextViews
         */

        displayTimer=(TextView) findViewById(R.id.TimerView);
        displayDistance=(TextView) findViewById(R.id.distanceText);
        startTracking_Btn= (Button) findViewById(R.id.startTimer_btn);
        storeIntoDb=(Button) findViewById(R.id.storeDb_Btn);
        Firebase.setAndroidContext(this);
        cordList=new ArrayList<LatLng>();


        startTracking_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Button has been pressed","boolean value: " + StartPressed);
                StartPressed=true;
                Log.d("Button is true","boolean value: " + StartPressed);
                reverseTimer(30,displayTimer);
            }
        });


//        storeIntoDb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                /**
//                 * This codes allow the storing of the distance into the database
//                 * For now this are just under Debug testing mode
//                 */
//                Firebase database = new Firebase(Config.FIREBASE_URL);
//                String distanceMoved = displayDistance.getText().toString();
//                User user = new User();
//
//                user.setName("Kahsheng");
//                user.setSprintChalleng(distanceMoved);
//                String name="Keegan";
//                database.child(name).setValue(user);
//            }
//        });

        /** Obtain the SupportMapFragment and get notified when the map is ready to be used.**/

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //SET SIZE OF THE GPS MAP
        ViewGroup.LayoutParams params = mapFragment.getView().getLayoutParams();
        params.height = 1200;
        mapFragment.getView().setLayoutParams(params);



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
    //When map has finish loading,this would be called

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }
    protected synchronized void buildGoogleApiClient() {
        //Configure client
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                //.addConnectionCallbacks provides callbacks that are called when client connected or disconnected.
                .addConnectionCallbacks(this)
                //addOnConnectionFailedListener covers scenarios of failed attempt of connect client to servic
                .addOnConnectionFailedListener(this)
                //adds the LocationServices API endpoint from Google Play Services.
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //quality of service for location updates from the FusedLocationProviderApi using requestLocationUpdates.
        mLocationRequest = new LocationRequest();

        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    /**
     * This function calculate the distance everytime onlocationchange is called
     * GETS THE DISTANCE
     * @param startLat
     * @param startLong
     * @param endLat
     * @param endLong
     * @return
     */
    public float calculateDistance(Double startLat,Double startLong,Double endLat,Double endLong){
        Location startPoint=new Location("locationA");
        startPoint.setLatitude(startLat);
        startPoint.setLongitude(startLong);

        Location endPoint=new Location("locationB");
        endPoint.setLatitude(endLat);
        endPoint.setLongitude(endLong);

        float distanceCalculated=startPoint.distanceTo(endPoint);
        return distanceCalculated;

    }

    /**
     * This function allows the user to view the distance that they have travelled
     * @param list
     */
    public void drawLines(ArrayList<LatLng>list){
        int size=list.size();
        PolylineOptions polyOptions = new PolylineOptions();
        polyOptions.color(Color.RED);
        polyOptions.width(10);
        polyOptions.addAll(list);
        //mMap.clear();
        mMap.addPolyline(polyOptions);


    }

    public int speedOfUser(float distance,int timing){
        int dist=Math.round(distance);
        int speed=dist/timing;
        return speed;
    }



    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
//        if (mCurrLocationMarker != null) {
//            mCurrLocationMarker.remove();
//        }
        Log.d("Location has changed ","boolean value: " + StartPressed);
        if(StartPressed==true){
            //Place current location marker
            //Get current location using getLatitude and Longitude
            if ((currentLat == null) && (currentLong ==null)){
                currentLat=location.getLatitude();
                currentLong=location.getLongitude();

                cordList.add(new LatLng(currentLat,currentLong));

            }
            else {

                newLat = location.getLatitude();
                newLong = location.getLongitude();
                //Get the travelled distance
                //Replace the current Vales with the new values and abandon the old values as it is already calculated
                //Calculate the distance between two location and add on to the total distance
                //Display the total distance out to the user
                float distanceReturn = calculateDistance(currentLat, currentLong, newLat, newLong);
                distanceTravelled = distanceTravelled + distanceReturn;
                /**
                 * Set the float value to no decimal places
                 * This is to allow easy display
                 */
                String distance = String.format("%.0f", distanceTravelled);

                displayDistance.setText(distance);
                currentLat = newLat;
                currentLong = newLong;
                cordList.add(new LatLng(currentLat,currentLong));
                drawLines(cordList);




            }

        }

            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title("Current Position");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            //mCurrLocationMarker = mMap.addMarker(markerOptions);

            //move map camera
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            //Adjust this from 2.0 to 21.0 to set the zoom height
            mMap.animateCamera(CameraUpdateFactory.zoomTo(19));

            //stop location updates
//            if (mGoogleApiClient != null) {
//                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
//            }






    }
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // Permission was granted.
                    if (ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            //You can add here other case statements according to your requirement.
        }
    }
    /**
     * CountdownTimer function to set up timer
     * When only when timer is valid then we can track the distance travelled by user
     */
    public void reverseTimer(int Seconds,final TextView textView){

        new CountDownTimer(Seconds* 1000+1000, 1000) {

            public void onTick(long millisUntilFinished) {
                /**
                 * Display the timer
                 * For now it is set to 60 seconds
                 */
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                textView.setText(String.format("%02d", minutes)
                        + ":" + String.format("%02d", seconds));
                startTracking_Btn.setEnabled(false);

            }

            /**
             * Upon timer successfully counts to 0
             */
            public void onFinish() {
                textView.setText("COMPLETE");
                /**
                 * Calculate speed of user
                 * If speed of user exceeds 44km per hour ,means it is cheating
                 *
                 */
                int speedTravel=speedOfUser(distanceTravelled,timingForApp);
                Log.i("Speed is : ",Integer.toString(speedTravel));
                if (speedTravel > 50 ){
                    /**
                     * Means user is cheating, th fastest speed is 50
                     */
                }
                else{
                    /**
                     * Accept user's timing, and store the score.
                     */
                }

                /**
                 * When the count down timer is completed,
                 * it will display the end result to the user
                 * This display includes the score and store the result into the database
                 */
                /**
                 * Once the tmier hits 0 , means user has already completed the challenge
                 * 1) Disable the timer-Done
                 * 2) Store the result into the database-Done just have to shift the code down
                 * 3) Pop up a alert dialog box show the result of the run-Not yet done
                 * 4) Upon user pressing out of the dialog box
                 * 5) Lead him back to the homescreen
                 * 6) Disable the
                 */
                //Once
                StartPressed=false;
                //Secs.setVisibility(View.GONE);
                startTracking_Btn.setEnabled(false);
            }
        }.start();
    }
}
