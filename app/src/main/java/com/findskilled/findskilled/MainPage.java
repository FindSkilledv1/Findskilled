package com.findskilled.findskilled;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainPage extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, ResultCallback<LocationSettingsResult> {
    //   SweetAlertDialog pDialog;
    //   private Button golocation;
   private String cityName;
    ArrayList<String> localitylist,listID;
    AutoCompleteTextView locality;
    //bydefault keep the value as false
    Boolean isInternetPresent = false;
    //to check whether internet is available or not
    InternetConnectionDetector icd;

    private Button gobtn;
    //  private TextView txt_location;
//    protected TextView txt_latitude, txt_longitude, mLastUpdateTimeTextView, tv_pincode,
    protected TextView tv_city;
    protected static final String TAG = "MainActivity";

    /**
     * Constant used in the location settings dialog.
     */
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
  //  public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    /**
     * The fastest rate for active location updates. Exact. Updates will never be more frequent
     * than this value.
     */
//    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
//            UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    // Keys for storing activity state in the Bundle.
    protected final static String KEY_REQUESTING_LOCATION_UPDATES = "requesting-location-updates";
    protected final static String KEY_LOCATION = "location";
    protected final static String KEY_LAST_UPDATED_TIME_STRING = "last-updated-time-string";
    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;

    /**
     * Stores parameters for requests to the FusedLocationProviderApi.
     */
    protected LocationRequest mLocationRequest;
    /**
     * Stores the types of location services the client is interested in using. Used for checking
     * settings to determine if the device has optimal location settings.
     */
    protected LocationSettingsRequest mLocationSettingsRequest;
    /**
     * Represents a geographical location.
     */
    protected Location mCurrentLocation;

    // Labels.
    protected String mLatitudeLabel;
    protected String mLongitudeLabel;
    protected String mLastUpdateTimeLabel;
    /**
     * Tracks the status of the location updates request. Value changes when the user presses the
     * Start Updates and Stop Updates buttons.
     */
    protected Boolean mRequestingLocationUpdates;
    /**
     * Time when the location was updated represented as a String.
     */
    protected String mLastUpdateTime;
    int RQS_GooglePlayServices = 0;

    @Override
    protected void onStart() {
        super.onStart();

        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();

        int resultCode = googleAPI.isGooglePlayServicesAvailable(getApplicationContext());

        if (resultCode == ConnectionResult.SUCCESS) {
//            Toast.makeText(getApplicationContext(),
//                    "isGooglePlayServicesAvailable SUCCESS", Toast.LENGTH_LONG).show();
            mGoogleApiClient.connect();
        } else {

//            Toast.makeText(getApplicationContext(),
//                    "isGooglePlayServicesAvailable FAIL", Toast.LENGTH_LONG).show();
            googleAPI.getErrorDialog(this, resultCode, RQS_GooglePlayServices).show();

        }

    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        // Within {@code onPause()}, we pause location updates, but leave the
//// connection to GoogleApiClient intact. Here, we resume receiving
//// location updates if the user has requested them.
////        if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
////// Toast.makeText(MainPage.this, "location was already on so detecting location now", Toast.LENGTH_SHORT).show();
////            startLocationUpdates();
////        }
//    }

    @Override
    protected void onPause() {
        super.onPause();
        // Stop location updates to save battery, but don't disconnect the GoogleApiClient object.
//        if (mGoogleApiClient.isConnected()) {
//            stopLocationUpdates();
//        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        mGoogleApiClient.disconnect();
    }

    AutoCompleteTextView localityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        localitylist = new ArrayList<>();
        listID = new ArrayList<>();
        localityName = (AutoCompleteTextView) findViewById(R.id.searchlocality);
        localityName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Toast.makeText(MainPage.this,""+listID.get(position),Toast.LENGTH_LONG).show();
                final String localityID=listID.get(position);
            }
        });




        //      golocation = (Button) findViewById(R.id.location);
        //      txt_location = (TextView) findViewById(R.id.txtlocation);
//        txt_latitude = (TextView) findViewById(R.id.latitude);
//        txt_longitude = (TextView) findViewById(R.id.longitude);
//        mLastUpdateTimeTextView = (TextView) findViewById(R.id.mlastupdatetime);
        tv_city = (TextView) findViewById(R.id.city);
        gobtn = (Button) findViewById(R.id.go);
        gobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainPage.this, HomePage.class));
            }
        });

//        tv_pincode = (TextView) findViewById(R.id.pincode);
        //setlabels
        mLatitudeLabel = getResources().getString(R.string.latitude_lable);
        mLongitudeLabel = getResources().getString(R.string.longitude_lable);
        mLastUpdateTimeLabel = getResources().getString(R.string.lastUpdateTime);

        mRequestingLocationUpdates = false;
        mLastUpdateTime = "";
// Kick off the process of building the GoogleApiClient, LocationRequest, and
// LocationSettingsRequest objects.

//step 1

        //initializing the boolean value after checking the presence of internet
        buildGoogleApiClient();
        //step 2
        createLocationRequest();
//step 3
        buildLocationSettingsRequest();

        checkLocationSettings();



    }
    //step 1
    protected synchronized void buildGoogleApiClient() {
        //Log.i(TAG, "Building GoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    //step 2
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
// Sets the desired interval for active location updates. This interval is
// inexact. You may not receive updates at all if no location sources are available, or
// you may receive them slower than requested. You may also receive updates faster than
// requested if other applications are requesting location at a faster interval.
//        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
//// Sets the fastest rate for active location updates. This interval is exact, and your
//// application will never receive updates faster than this value.
//        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    //step 3
    protected void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    //step 4
    protected void checkLocationSettings() {
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(
                        mGoogleApiClient,
                        mLocationSettingsRequest
                );
        result.setResultCallback(this);
    }

    /**
     * Requests location updates from the FusedLocationApi.
     */

    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient,
                mLocationRequest,
                this
        ).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(Status status) {
                mRequestingLocationUpdates = true;
                //     setButtonsEnabledState();
            }
        });


    }

    protected void stopLocationUpdates() {
        // It is a good practice to remove location requests when the activity is in a paused or
        // stopped state. Doing so helps battery performance and is especially
        // recommended in applications that request frequent location updates.
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient,
                this
        ).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(Status status) {
                mRequestingLocationUpdates = false;
                //   setButtonsEnabledState();
            }
        });
    }


    @Override
    public void onConnected(Bundle bundle) {
        Toast.makeText(MainPage.this, "connected to GoogleApiClient", Toast.LENGTH_LONG).show();
//        Log.i(TAG, "Connected to GoogleApiClient");
// If the initial location was never previously requested, we use
// FusedLocationApi.getLastLocation() to get it. If it was previously requested, we store
// its value in the Bundle and check for it in onCreate(). We
// do not request it again unless the user specifically requests location updates by pressing
// the Start Updates button.
//
// Because we cache the value of the initial location in the Bundle, it means that if the
// user launches the activity,
// moves to a new location, and then changes the device orientation, the original location
// is displayed as the activity is re-created.
        if (mCurrentLocation == null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.

            }
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
            updateLocationUI();

        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection suspended");

    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        updateLocationUI();
        Toast.makeText(this, getResources().getString(R.string.location_updated_message),
                Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
// Refer to the javadoc for ConnectionResult to see what error codes might be returned in
// onConnectionFailed.
//        SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
//                .setTitleText("Oops...")
//                .setContentText("Something went wrong!")
//                .setConfirmText("Ok.")
//                .showCancelButton(true)
//                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                    @Override
//                    public void onClick(SweetAlertDialog sDialog) {
//                        sDialog.cancel();
//                        finish();
//                    }
//                });
//
//        pDialog.show();


        //Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
    }

    @Override
    public void onResult(LocationSettingsResult locationSettingsResult) {
        final Status status = locationSettingsResult.getStatus();
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:
                //    Log.i(TAG, "All location settings are satisfied.");
      //          Toast.makeText(MainPage.this, "Location is already on.", Toast.LENGTH_SHORT).show();
                startLocationUpdates();
                break;
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                //     Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to" +
                //             "upgrade location settings ");
                try {
// Show the dialog by calling startResolutionForResult(), and check the result
// in onActivityResult().
//            Toast.makeText(MainPage.this, "Location dialog will be open", Toast.LENGTH_SHORT).show();
//
//move to step 6 in onActivityResult to check what action user has taken on settings dialog
                    status.startResolutionForResult(MainPage.this, REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException e) {
                    //       Log.i(TAG, "PendingIntent unable to execute request.");
                }
                break;
            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                // Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog " +
                //       "not created.");
                break;
        }


    }

    /**
     * This OnActivityResult will listen when
     * case LocationSettingsStatusCodes.RESOLUTION_REQUIRED: is called on the above OnResult
     */
//step 6:
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
// Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
//                        Log.i(TAG, "User agreed to make required location settings changes.");
                        startLocationUpdates();
                        break;
                    case Activity.RESULT_CANCELED:
//                        Log.i(TAG, "User chose not to make required location settings changes.");
                        break;
                }
                break;
        }
    }

    /**
     * Sets the value of the UI fields for the location latitude, longitude and last update time.
     */
    private void updateLocationUI() {
        if (mCurrentLocation != null) {
//            Toast.makeText(MainPage.this,""+mLatitudeLabel,Toast.LENGTH_SHORT).show();
//            txt_latitude.setText(String.format("%s: %f", mLatitudeLabel,
//                    mCurrentLocation.getLatitude()));
//            txt_longitude.setText(String.format("%s: %f", mLongitudeLabel,
//                    mCurrentLocation.getLongitude()));

//            mLastUpdateTimeTextView.setText(String.format("%s: %s", mLastUpdateTimeLabel,
//                    mLastUpdateTime));
            icd = new InternetConnectionDetector(getApplicationContext());
            isInternetPresent = icd.isConnectingToInternet();
            if (isInternetPresent) {
                //        pDialog.dismiss();
                updateCityAndPincode(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
                Connect cnct = new Connect();
                cnct.execute();

            } else {

                SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("check your internet connectivity!")
                        .setConfirmText("Ok.")
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.cancel();
                                finish();
                            }
                        });

                pDialog.show();
            }

        }
    }

    /**
     * This updateCityAndPincode method uses Geocoder api to map the latitude and longitude into city location or pincode.
     * We can retrieve many details using this Geocoder class.
     * <p/>
     * And yes the Geocoder will not work unless you have data connection or wifi connected to internet.
     */
    public void updateCityAndPincode(double latitude, double longitude) {
        try {
            Geocoder gcd = new Geocoder(MainPage.this, Locale.getDefault());
            List<Address> addresses = gcd.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                cityName = ("" + addresses.get(0).getLocality()).toString();
                tv_city.setText(cityName);

//                tv_pincode.setText("Pincode="+addresses.get(0).getPostalCode());
// System.out.println(addresses.get(0).getLocality());
            }
        } catch (Exception e) {
            Log.e(TAG, "exception:" + e.toString());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //    pDialog.dismiss();

    }

    class Connect extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(MainPage.this,""+cityName, Toast.LENGTH_LONG).show();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainPage.this, android.R.layout.simple_dropdown_item_1line, localitylist);
            localityName.setAdapter(adapter);

        }

        @Override
        protected Void doInBackground(Void... params) {

            ServerReq m = new ServerReq();
            String a;
            try {
                a = m.getLocalityName(cityName);
                JsonResponce(a);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;
        }
        public void JsonResponce(String s) throws JSONException {


                     JSONObject json = new JSONObject(s);
                JSONArray jarray = json.getJSONArray("user");


                for (int i = 0; i <= jarray.length(); i++) {

                    JSONObject jobj = jarray.getJSONObject(i);


                    listID.add(jobj.getString("id"));
                    localitylist.add(jobj.getString("name"));


                }

        }
     }
}