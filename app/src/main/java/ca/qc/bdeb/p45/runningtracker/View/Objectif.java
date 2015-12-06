package ca.qc.bdeb.p45.runningtracker.View;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import android.graphics.Color;

import java.text.SimpleDateFormat;
import java.util.Date;

import ca.qc.bdeb.p45.runningtracker.BD.DBHelper;
import ca.qc.bdeb.p45.runningtracker.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Objectif.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Objectif#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Objectif extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView distance;
    private TextView date;
    private PieChart mPieChart;
    private Button newButton;
    private DBHelper db = DBHelper.getInstance(getActivity());

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
     * @return A new instance of fragment Objectif.
     */
    // TODO: Rename and change types and number of parameters
    public static Objectif newInstance(String param1, String param2) {
        Objectif fragment = new Objectif();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Objectif() {
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
        return inflater.inflate(R.layout.fragment_objectif, container, false);
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
        distance = (TextView) getActivity().findViewById(R.id.Objectif_TexteView_Distance);
        date = (TextView) getActivity().findViewById(R.id.Objectif_TexteView_Date);
        mPieChart = (PieChart) getActivity().findViewById(R.id.Objectif_piechart);
        newButton = (Button) getActivity().findViewById(R.id.Objectif_Button_new);

        PieChart.LayoutParams layoutParams = mPieChart.getLayoutParams();
        Display mDisplay = getActivity().getWindowManager().getDefaultDisplay();
        layoutParams.width = mDisplay.getWidth();

        float objectifReusi = db.getPourcentageObjectifAccomplie();
        distance.setText("" + db.getCurrentObjectif().getOBJECTIF_DISTANCE_Final() + " Km");
        SimpleDateFormat dt = new SimpleDateFormat("d MMM yyyy");
        Date dateFinale = db.getCurrentObjectif().getOBJECTIF_DATE_FINAL();
        date.setText("" + dt.format(dateFinale));
        mPieChart.addPieSlice(new PieModel("Objectif réussi",
                (float) objectifReusi,
                Color.parseColor("#EE7600")));
        mPieChart.addPieSlice(new PieModel("Objectif échouer",
                (float) (100 - objectifReusi),
                Color.parseColor("#56B7F1")));
        mPieChart.startAnimation();
    }

}
