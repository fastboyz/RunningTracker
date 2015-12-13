package ca.qc.bdeb.p45.runningtracker.View;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import ca.qc.bdeb.p45.runningtracker.BD.DBHelper;
import ca.qc.bdeb.p45.runningtracker.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Run.OnFragmentInteractionListener, OnMapReadyCallback {
    ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        // initialise();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        RelativeLayout container = (RelativeLayout) findViewById(R.id.MainActivity_container);
        FrameLayout fragContainer = new FrameLayout(this);
        fragContainer.setId(R.id.MainActivity_Frag_container);


        getSupportFragmentManager().beginTransaction().add(fragContainer.getId(), new Run()).commit();
        container.addView(fragContainer);



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
        Fragment fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        int id = item.getItemId();
        FrameLayout fragContainer = new FrameLayout(this);
        fragContainer.setId(R.id.MainActivity_Frag_container);

        Class fragmentClass = null;

        if (id == R.id.Menu_course) {
            fragmentClass = Run.class;
        } else if (id == R.id.Menu_bike) {
            fragmentClass = Bike.class;
        } else if (id == R.id.Menu_historique) {

        } else if ( id == R.id.Menu_objectif) {
            fragmentClass = ObjectifFragment.class;
        } else if (id == R.id.Menu_Statistique) {
            fragmentClass = Statistiques.class;
        } else if (id == R.id.nav_share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            shareIntent.setType("*/*");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "J'ai parcourue "
                    + DBHelper.getInstance(this).getLastRunDistance()
                    + " Km a l'aide de la super application \"Run Tracker\"");
            startActivity(Intent.createChooser(shareIntent, "Partager avec"));
        }

        if (fragmentClass != null) {
            try {
                fragment = (Fragment) fragmentClass.newInstance();
                fragmentManager.beginTransaction().replace(R.id.MainActivity_Frag_container, fragment).commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
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

}
