package com.example.lab12wtabs;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

private sectionPageAdaptor msectionPageAdaptor;
private ViewPager mViewPager;


 TextView view1, view2, view3, view4, view5, view6, view7, view8, view9;



    private LocationManager locationManager;

    private LocationListener myloc1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




msectionPageAdaptor = new sectionPageAdaptor(getSupportFragmentManager());

mViewPager= (ViewPager) findViewById(R.id.container);
setupViewPager(mViewPager);
        TabLayout tabLayout= (TabLayout) findViewById(R.id.tabs);

tabLayout.setupWithViewPager(mViewPager);







        view1 = (TextView) findViewById(R.id.view1);

     view2 = (TextView) findViewById(R.id.view2);
        view3 = (TextView) findViewById(R.id.view3);
        view4 = (TextView) findViewById(R.id.view4);
        view5 = (TextView) findViewById(R.id.view5);
        view6 = (TextView) findViewById(R.id.view6);
        view7 = (TextView) findViewById(R.id.view7);
        view8 =(TextView) findViewById(R.id.view8);
        view9= (TextView) findViewById(R.id.view9);



        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);


        myloc1 = new LocationListener() {


            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                if (location.getProvider().equals(LocationManager.NETWORK_PROVIDER))
                    view1.setText("NET latitude: " + location.getLatitude() + ", " + "logititute: " + location.getLongitude());
                else if (location.getProvider().equals(LocationManager.GPS_PROVIDER))
                    view2.setText("GPS latitude: " + location.getLatitude() + ", " + "logititute: " + location.getLongitude()); }

            public void onStatusChanged(String provider, int status, Bundle extras) { }

            public void onProviderEnabled(String provider) { }

            public void onProviderDisabled(String provider) { }};




//Location
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                //Toast.makeText(this, "We need your location, NOW!",2);
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                // Register the listener with the Location Manager to receive location updates
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, myloc1);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, myloc1);
            }
        }else{
            // Register the listener with the Location Manager to receive location updates
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, myloc1);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, myloc1);
        }





        //Gravity
        SensorManager mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        MySensorListener mysensor = new MySensorListener();
        mSensorManager.registerListener(mysensor, mSensor, SensorManager.SENSOR_DELAY_NORMAL);



        //gyro
        SensorManager gSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor gSensor = gSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        gSensorListener gsensor = new gSensorListener();
        gSensorManager.registerListener(gsensor, gSensor, SensorManager.SENSOR_DELAY_NORMAL);


        //proximity
        SensorManager pSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor pSensor = pSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        pSensorListener psensor = new pSensorListener();
        pSensorManager.registerListener(psensor, pSensor, SensorManager.SENSOR_DELAY_GAME);



        //light
        SensorManager lSensorManager= (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor lSensor = lSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        lSensorListener lsensor= new lSensorListener();
        lSensorManager.registerListener(lsensor, lSensor,0);



        //compass
        SensorManager cSensorManager= (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor cSensor = cSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        cSensorListener csensor= new cSensorListener();
        cSensorManager.registerListener(csensor, cSensor,0);











    }//oncreate



    private void setupViewPager(ViewPager viewPager){

        sectionPageAdaptor adaptor= new sectionPageAdaptor(getSupportFragmentManager());
adaptor.addFragment(new compassFragment(), "Compass");
        adaptor.addFragment(new gravityFragment(), "Gravity");
        adaptor.addFragment(new gyroFragment(), "Gyro");
        adaptor.addFragment(new lightFragment(), "Light");
        adaptor.addFragment(new locationFragment(), "Location");
        adaptor.addFragment(new proximityFragment(), "Proximity");

viewPager.setAdapter(adaptor);


    }











    //Gravity
    public class MySensorListener implements SensorEventListener {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float yGravity = event.values[1]; //value[0] corresponds to x-axis, value[1] to y-axis, and 2 to z-axis

            view3.setText("val: " + yGravity);

        }
        @Override
        public void onAccuracyChanged(Sensor arg0, int arg1) {
        }}


    //Gyroscope
    public class gSensorListener implements SensorEventListener {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float xGy = event.values[0];
            float yGy = event.values[1];
            float zGy = event.values[2];
            view4.setText("x-Axes: " + xGy);
            view5.setText("y-Axes: " + yGy);
            view6.setText("z-Axes: " + zGy);

        }
        @Override
        public void onAccuracyChanged(Sensor arg0, int arg1) { }}




    //proximity
    public class pSensorListener implements SensorEventListener {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float prox = event.values[0];
            view7.setText("Dist: " + prox + " cm");

        }
        @Override
        public void onAccuracyChanged(Sensor arg0, int arg1) { }}




    //light
    public class lSensorListener implements SensorEventListener {

        @Override
        public void onSensorChanged(SensorEvent event){

            float light =event.values[0];
            view8.setText("Light: " + light);
        }

        @Override
        public void onAccuracyChanged(Sensor arg0, int arg1) { }

    }





    //compass
    public class cSensorListener implements SensorEventListener{

        @Override
        public void onSensorChanged(SensorEvent event){

            float compass =Math.round(event.values[0]);
            view9.setText( compass + "'");
        }

        @Override
        public void onAccuracyChanged(Sensor arg0, int arg1) { }}

























}//main
