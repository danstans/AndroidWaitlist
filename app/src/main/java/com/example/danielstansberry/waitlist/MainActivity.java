package com.example.danielstansberry.waitlist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.danielstansberry.waitlist.Responses.ApplyResponse;
import com.example.danielstansberry.waitlist.Responses.AvailableResponse;
import com.example.danielstansberry.waitlist.Responses.TableList;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.lang.reflect.Array;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements OnConnectionFailedListener
{
    SharedPreferences ch;
    private GoogleApiClient mGoogleApiClient;
    String TAG = "TAG";
    ArrayAdapter<String> adapter;
    LocationManager locationManager;
    AppInfo mAppInfo = AppInfo.getAppInfo();

    @Bind(R.id.userName) EditText username;
    @Bind(R.id.numberPicker) NumberPicker numList;
    @Bind(R.id.button) Button SendInfo;
    @Bind(R.id.ret) TextView Retrieve;
    @Bind(R.id.spinner) Spinner RestaurantList;
    @Bind(R.id.Confirm) Button confirmButton;

    String PROJECT_NUMBER="494797651756";
    String DEVICE_ID = "";

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        ButterKnife.bind(this);

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        username.setEnabled(false);

        // Create logger and set desired log level
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        // Build retrofit api
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://brave-computer-123802.appspot.com/welcome/api/")    // We are using Luca API to get data
                .addConverterFactory(GsonConverterFactory.create())	// Add Gson parser
                .client(httpClient) //Add in logging
                .build();

        mAppInfo.api = retrofit.create(WaitListApi.class);
        mAppInfo.context = this;

        GCMClientManager pushClientManager = new GCMClientManager(this, PROJECT_NUMBER);
        pushClientManager.registerIfNeeded(new GCMClientManager.RegistrationCompletedHandler() {
            @Override
            public void onSuccess(String registrationId, boolean isNewRegistration) {

                Log.d("Registration id",registrationId);
                DEVICE_ID = registrationId;
                //send this registrationId to your server
            }
            @Override
            public void onFailure(String ex) {
                super.onFailure(ex);
            }
        });
    }

    // TODO: Please implement GoogleApiClient.OnConnectionFailedListener to
    // handle connection failures.
    public void onConnectionFailed(ConnectionResult result)
    {
        AlertDialog.Builder errorLog = new AlertDialog.Builder(this);
        errorLog.setTitle("An error has occured");
        errorLog.setMessage("An unreasonable error has occured and a cnnection to Google APIs could not be established. Please try again later.");
        errorLog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        Dialog alertDialog = errorLog.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }


    public void onStart()
    {
        numList.setMinValue(0);
        numList.setMaxValue(9);
        super.onStart();
        ch = PreferenceManager.getDefaultSharedPreferences(this);
        Retrieve.setVisibility(View.VISIBLE);
        // Get Location Manager and check for GPS & Network location services
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if(!lm.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                !lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
        {
            // Build the alert dialog
            AlertDialog.Builder locationPop = new AlertDialog.Builder(this);
            locationPop.setTitle("Location Services Not Active");
            locationPop.setMessage("The app will not work properly without Location Services, please make sure that they are enabled and restart app");
            locationPop.setPositiveButton("OK", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialogInterface, int i) {
                    // Show location settings when the user acknowledges the alert dialog
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });
            Dialog alertDialog = locationPop.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        }

        //Listens to the location, and gets the most precise recent location.
        final LocationListener locationListener = new LocationListener()
        {

            @Override
            public void onLocationChanged(Location location)
            {
                SharedPreferences.Editor e = ch.edit();
                if(location.getAccuracy() < 51) {
                    username.setEnabled(true);

                    locationManager.removeUpdates(this);
                    PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi.getCurrentPlace(mGoogleApiClient,null);
                    result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
                        @Override
                        public void onResult(PlaceLikelihoodBuffer placeLikelihoods) {
                            ArrayList<String> arraySpinner =  new ArrayList<>();
                            arraySpinner.add("Select a Restaurant");
                            for (PlaceLikelihood placeLikelihood : placeLikelihoods) {
                               // if (placeLikelihood.getPlace().getPlaceTypes().contains(38) || placeLikelihood.getPlace().getPlaceTypes().contains(79) || placeLikelihood.getPlace().getPlaceTypes().contains(82) )
                                // { TODO: DELETE THESE COMMENTS SO THAT ONLY FOOD/RESTAURANT/SCHOOL PLACES SHOW UP IN LIST.
                                    arraySpinner.add(placeLikelihood.getPlace().getName().toString());
                                // }
                            }
                            adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, arraySpinner);
                            RestaurantList.setAdapter(adapter);
                            placeLikelihoods.release();
                        }
                    });

                    enableIfReady();
                }
                e.putString("Latitude", String.valueOf(location.getLatitude()));
                e.putString("Longitude", String.valueOf(location.getLongitude()));
                e.putString("Accuracy",String.valueOf(location.getAccuracy()));
                e.commit();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}

            @Override
            public void onProviderEnabled(String provider) {}

            @Override
            public void onProviderDisabled(String provider) {}
        };

        try
        {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        } catch (SecurityException e)
        {
            e.printStackTrace();
        }

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                enableIfReady();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });

        RestaurantList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                enableIfReady();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        numList.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                enableIfReady();
            }
        });


    }

    public void chat(View v) //ON CLICK FOR BUTTON
    {
        mAppInfo.nickname = username.getText().toString();
        // TODO: Use actual device ID
        mAppInfo.deviceId = DEVICE_ID ;
        mAppInfo.partySize = numList.getValue();


        SharedPreferences.Editor editor = ch.edit();
        editor.putString("nickName", mAppInfo.nickname);
        editor.commit();

        Call<TableList> tableListCall = mAppInfo.api.getWaitList();
        tableListCall.enqueue(new TableListCallback());

    }

    public void enableIfReady() {
        boolean isReady = username.getText().toString().length()>0
                && !RestaurantList.getSelectedItem().toString().equals("Select a Restaurant")
                && numList.getValue()!=0;
        Retrieve.setVisibility(View.INVISIBLE);
        SendInfo.setClickable(isReady);
        SendInfo.setEnabled(isReady);
    }
}

