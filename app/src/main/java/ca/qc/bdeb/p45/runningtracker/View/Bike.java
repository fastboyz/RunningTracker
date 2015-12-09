package ca.qc.bdeb.p45.runningtracker.View;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.CompoundButton;
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

import ca.qc.bdeb.p45.runningtracker.BD.DBHelper;
import ca.qc.bdeb.p45.runningtracker.Common.StateCourse;
import ca.qc.bdeb.p45.runningtracker.Common.Utils;
import ca.qc.bdeb.p45.runningtracker.Modele.*;
import ca.qc.bdeb.p45.runningtracker.Modele.Objectif;
import ca.qc.bdeb.p45.runningtracker.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Bike.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Bike#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Bike extends Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private GoogleMap mMap;
    private static LatLng lastKnownPos;
    private Course course;
    private TextView distanceVoyager;
    private Chronometer chronometre;
    private TextView speed;
    private TextView lblObjectif;
    private DBHelper helper;
    private  Objectif objectif;
    NumberProgressBar nbp;

    private OnFragmentInteractionListener mListener;

    public Bike() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Bike.
     */
    // TODO: Rename and change types and number of parameters
    public static Bike newInstance(String param1, String param2) {
        Bike fragment = new Bike();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bike, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            // to do: must implement OnFragmentInteractionListener
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setScrollGesturesEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);

        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                moveCamToLocation(mMap.getMyLocation());
                if (course != null) {
                    drawLine();
                }
            }
        });
    }

    private void moveCamToLocation(Location location) {
        LatLng position;
        if (location != null) {
            position = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 14.85f));
        } else {
            Toast.makeText(getContext(), "GPS Fermer", Toast.LENGTH_SHORT).show();
        }
    }

    private void initialise() {
        helper = DBHelper.getInstance(getContext());
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById((R.id.map));
        objectif = helper.getCurrentObjectif();
        mapFragment.getMapAsync(this);
        nbp = (NumberProgressBar) getActivity().findViewById(R.id.BikeFragment_progess);
        nbp.setMax(100);
        nbp.setProgress(0);
        chronometre = (Chronometer) getActivity().findViewById(R.id.MainActivity_time_bike);
        //chronometre.setFormat("MM:SS");
        ToggleButton startStop = (ToggleButton) getActivity().findViewById(R.id.BikeFragment_btnStartStop);
        distanceVoyager = (TextView) getActivity().findViewById(R.id.MainActivity_traveled_bike);
        distanceVoyager.setText("0.00 Km");
        speed = (TextView) getActivity().findViewById(R.id.MainActivity_Speed_bike);
        lblObjectif = (TextView) getActivity().findViewById(R.id.MainActivity_objective_bike);
        lblObjectif.setText(String.format(" %d Km", objectif.getOBJECTIF_DISTANCE()));
        startStop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            LatLng pos;

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (course != null) {
                        mMap.clear();
                        distanceVoyager.setText(R.string.distanceVoyagerInitiale);
                    }
                    course = new Course();
                    course.setCourse_type(Utils.COURSE_TYPE.VELO);
                    chronometre.setBase(SystemClock.elapsedRealtime());
                    chronometre.start();
                    initialiserCourse();
                } else {
                    pos = new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation()
                            .getLongitude());
                    mMap.addMarker(new MarkerOptions().position(pos).
                            icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory
                                    .HUE_RED))
                            .title(getResources().getString(R.string.stop)));
                    course.changeState();
                    chronometre.stop();
                    course.setTempsEcouler(SystemClock.elapsedRealtime() - chronometre.getBase());
                    helper.ajouterCourse(course);
                }
            }

            private void initialiserCourse() {
                pos = new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation()
                        .getLongitude());
                lastKnownPos = pos;
                mMap.addMarker(new MarkerOptions().position(pos).
                        icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory
                                .HUE_GREEN)).
                        title(getResources().getString(R.string.start)));
            }
        });
    }

    private void drawLine() {
        if (course.getState() != StateCourse.ARRETER) {
            if (lastKnownPos != null) {
                LatLng newPos = new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation()
                        .getLongitude());
                PolylineOptions opts = new PolylineOptions().width(5).color(Color.BLUE)
                        .add(lastKnownPos).add(newPos);
                mMap.addPolyline(opts);
                course.ajouterDistance(Utils.getInstance().calculerDistante(lastKnownPos, newPos));
                course.setTempsEcouler(SystemClock.elapsedRealtime() - chronometre.getBase());
                distanceVoyager.setText(String.format("%s%s", Utils.getInstance()
                        .formatDecimal(course.getDistanteParcourue()), getString(R.string.unite_distance)));
                speed.setText(String.format("%s%s", Utils.getInstance()
                        .formatDecimal(course.getVitesse()), getString(R.string.unite_vitesse)));
                lastKnownPos = newPos;
                nbp.setProgress(Utils.getInstance().calculerPourcentageFini(course.getDistanteParcourue()
                        , objectif.getOBJECTIF_DISTANCE()));
            }
        }
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialise();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
