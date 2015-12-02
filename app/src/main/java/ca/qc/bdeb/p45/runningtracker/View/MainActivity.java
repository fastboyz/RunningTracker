package ca.qc.bdeb.p45.runningtracker.View;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import ca.qc.bdeb.p45.runningtracker.Common.StateCourse;
import ca.qc.bdeb.p45.runningtracker.Common.Utils;
import ca.qc.bdeb.p45.runningtracker.Modele.Course;
import ca.qc.bdeb.p45.runningtracker.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Run.OnFragmentInteractionListener, OnMapReadyCallback {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialise();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        RelativeLayout container = (RelativeLayout) findViewById(R.id.MainActivity_container);
        FrameLayout fragContainer = new FrameLayout(this);
        fragContainer.setId(R.id.MainActivity_Frag_container);


        getSupportFragmentManager().beginTransaction().add(fragContainer.getId(), new Run()).commit();
        container.addView(fragContainer);


//        SupportMapFragment mapFragmen(SupportMapFragment) this.getSupportFragmentManager()e)
//                .findFragmentById(R.id.ma;
//        mapFragment.getMapAsync(this);

//        nbp = (NumberProgressBar) findViewById(R.id.MainActivity_progess);
//
//        nbp.setMax(100);
//        nbp.setProgress(25);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Menu_course) {
            // Handle the camera action
        } else if (id == R.id.Menu_historique) {

        } else if (id == R.id.Menu_objectif) {

        } else if (id == R.id.Menu_Statistique) {

        } else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//        mMap.setMyLocationEnabled(true);
//        mMap.getUiSettings().setScrollGesturesEnabled(false);
//        mMap.getUiSettings().setMyLocationButtonEnabled(false);
//
//        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
//            @Override
//            public void onMyLocationChange(Location location) {
//                moveCamToLocation(mMap.getMyLocation());
//                if (course != null) {
//                    drawLine();
//                }
//            }
//        });
//
//    }
//
//    private void moveCamToLocation(Location location) {
//        LatLng position;
//        if (location != null) {
//            position = new LatLng(location.getLatitude(), location.getLongitude());
//            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 14.85f));
//        } else {
//            Toast.makeText(MainActivity.this, "GPS Fermer", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void initialise() {
//        chronometre = (Chronometer) findViewById(R.id.MainActivity_time);
//        //chronometre.setFormat("MM:SS");
//        startStop = (ToggleButton) findViewById(R.id.MainActivity_btnStartStop);
//        distanceVoyager = (TextView) findViewById(R.id.MainActivity_traveled);
//        distanceVoyager.setText(R.string.distanceVoyagerInitiale);
//        startStop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            LatLng pos;
//
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    if (course != null) {
//                        //TODO save the current course
//                        mMap.clear();
//                        distanceVoyager.setText(R.string.distanceVoyagerInitiale);
//                    }
//                    course = new Course();
//                    chronometre.setBase(SystemClock.elapsedRealtime());
//                    chronometre.start();
//                    initialiserCourse();
//                } else {
//                    pos = new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation()
//                            .getLongitude());
//                    mMap.addMarker(new MarkerOptions().position(pos).
//                            icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory
//                                    .HUE_RED))
//                            .title(getResources().getString(R.string.stop)));
//                    course.changeState();
//                    chronometre.stop();
//                    course.setTempsEcouler(SystemClock.elapsedRealtime()-chronometre.getBase());
//                }
//            }
//
//            private void initialiserCourse() {
//                pos = new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation()
//                        .getLongitude());
//                lastKnownPos = pos;
//                mMap.addMarker(new MarkerOptions().position(pos).
//                        icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory
//                                .HUE_GREEN)).
//                        title(getResources().getString(R.string.start)));
//            }
//        });
//    }
//
//    private void drawLine() {
//        if (course.getState() != StateCourse.ARRETER) {
//            if (lastKnownPos != null) {
//                newPos = new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation()
//                        .getLongitude());
//                PolylineOptions opts = new PolylineOptions().width(5).color(Color.BLUE)
//                        .add(lastKnownPos).add(newPos);
//                mMap.addPolyline(opts);
//                course.ajouterDistance(Utils.getInstance().calculerDistante(lastKnownPos, newPos));
//                distanceVoyager.setText(String.format("%s%s", Utils.getInstance()
//                        .formatDecimal(course), getString(R.string.unite_distance)));
//                lastKnownPos = newPos;
//            }
//        }
//    }
}
