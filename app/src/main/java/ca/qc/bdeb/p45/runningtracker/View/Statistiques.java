package ca.qc.bdeb.p45.runningtracker.View;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

import java.text.SimpleDateFormat;
import java.util.Date;

import ca.qc.bdeb.p45.runningtracker.BD.DBHelper;
import ca.qc.bdeb.p45.runningtracker.Modele.Course;
import ca.qc.bdeb.p45.runningtracker.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Statistiques.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Statistiques#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Statistiques extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private DBHelper db = DBHelper.getInstance(getActivity());
    private ToggleButton distance;
    private ToggleButton temps;
    private ToggleButton vitesse;
    private BarChart mBarChart;
    private ValueLineChart mCubicValueLineChart;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Statistiques.
     */
    // TODO: Rename and change types and number of parameters
    public static Statistiques newInstance(String param1, String param2) {
        Statistiques fragment = new Statistiques();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Statistiques() {
        // Required empty public constructor
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
        return inflater.inflate(R.layout.fragment_statistiques, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
        public void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialise();
    }

    private void initialise() {
        mBarChart = (BarChart) getActivity().findViewById(R.id.barchart);
        distance = (ToggleButton) getActivity().findViewById(R.id.statistiques_toggleButton_Distance);
        temps = (ToggleButton) getActivity().findViewById(R.id.statistiques_toggleButton_Temps);
        vitesse = (ToggleButton) getActivity().findViewById(R.id.statistiques_toggleButton_Vitesse);
        // Une bar par jour
        showDistance();
        distance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearGraphs();
                showDistance();
            }
        });
        temps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearGraphs();
                showTemps();
            }
        });
        vitesse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearGraphs();
                showVitesse();
            }
        });
    }

    private void showDistance() {
        distance.setChecked(true);
        temps.setChecked(false);
        vitesse.setChecked(false);
        for (int i = 6; i >= 0; i--) {
            SimpleDateFormat dt = new SimpleDateFormat("MMM d");
            Date date = new Date(System.currentTimeMillis() - (i * (24 * 60 * 60 * 1000)));
            mBarChart.addBar(new BarModel(dt.format(date), (float) db.getAllStatsInOneDay(date).getDistanteParcourue(), 0xFF123456));
        }
        mBarChart.startAnimation();
        mCubicValueLineChart = (ValueLineChart) getActivity().findViewById(R.id.cubiclinechart);
        ValueLineSeries series = new ValueLineSeries();
        series.setColor(0xFF56B7F1);
        for (int i = 30; i >= 0; i--) {
            SimpleDateFormat dt = new SimpleDateFormat("MMM d");
            Date date = new Date(System.currentTimeMillis() - (i * (24 * 60 * 60 * 1000)));
            series.addPoint(new ValueLinePoint(dt.format(date), (float) db.getAllStatsInOneDay(date).getDistanteParcourue()));
        }
        mCubicValueLineChart.addSeries(series);
        mCubicValueLineChart.startAnimation();
    }

    private void showVitesse() {
        distance.setChecked(false);
        temps.setChecked(false);
        vitesse.setChecked(true);
        for (int i = 6; i >= 0; i--) {
            SimpleDateFormat dt = new SimpleDateFormat("MMM d");
            Date date = new Date(System.currentTimeMillis() - (i * (24 * 60 * 60 * 1000)));
            mBarChart.addBar(new BarModel(dt.format(date), (float) db.getAllStatsInOneDay(date).getVitesse(), 0xFF123456));
        }
        mBarChart.startAnimation();
        mCubicValueLineChart = (ValueLineChart) getActivity().findViewById(R.id.cubiclinechart);
        ValueLineSeries series = new ValueLineSeries();
        series.setColor(0xFF56B7F1);
        for (int i = 30; i >= 0; i--) {
            SimpleDateFormat dt = new SimpleDateFormat("MMM d");
            Date date = new Date(System.currentTimeMillis() - (i * (24 * 60 * 60 * 1000)));
            series.addPoint(new ValueLinePoint(dt.format(date), (float) db.getAllStatsInOneDay(date).getVitesse()));
        }
        mCubicValueLineChart.addSeries(series);
        mCubicValueLineChart.startAnimation();
    }

    private void showTemps() {
        distance.setChecked(false);
        temps.setChecked(true);
        vitesse.setChecked(false);
        for (int i = 6; i >= 0; i--) {
            SimpleDateFormat dt = new SimpleDateFormat("MMM d");
            Date date = new Date(System.currentTimeMillis() - (i * (24 * 60 * 60 * 1000)));
            mBarChart.addBar(new BarModel(dt.format(date), (float) db.getAllStatsInOneDay(date).getTempsEcouler(), 0xFF123456));
        }
        mBarChart.startAnimation();
        mCubicValueLineChart = (ValueLineChart) getActivity().findViewById(R.id.cubiclinechart);
        ValueLineSeries series = new ValueLineSeries();
        series.setColor(0xFF56B7F1);
        for (int i = 30; i >= 0; i--) {
            SimpleDateFormat dt = new SimpleDateFormat("MMM d");
            Date date = new Date(System.currentTimeMillis() - (i * (24 * 60 * 60 * 1000)));
            series.addPoint(new ValueLinePoint(dt.format(date), (float) db.getAllStatsInOneDay(date).getTempsEcouler()));
        }
        mCubicValueLineChart.addSeries(series);
        mCubicValueLineChart.startAnimation();
    }

    private void clearGraphs() {
        mBarChart.clearChart();
        mCubicValueLineChart.clearChart();
    }

}
