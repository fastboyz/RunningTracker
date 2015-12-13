package ca.qc.bdeb.p45.runningtracker.View;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import ca.qc.bdeb.p45.runningtracker.BD.DBHelper;
import ca.qc.bdeb.p45.runningtracker.R;

public class ProgressionGenerale extends Fragment {


    public ProgressionGenerale() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_progression_generale, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialise();
    }

    private void initialise() {
        DBHelper db = DBHelper.getInstance(getActivity());
        ((TextView) getActivity().findViewById(R.id.Progession_TextView_nbrPied)).setText(" " + db.getNbrCoursePied());
        ((TextView) getActivity().findViewById(R.id.Progession_TextView_nbrVelo)).setText(" " + db.getNbrCourseVelo());
        ((TextView) getActivity().findViewById(R.id.Progession_TextView_Distance)).setText(" " + db.getDistanceTotal() + " Km");
        ((TextView) getActivity().findViewById(R.id.Progession_TextView_nbrPas)).setText(" " + db.getNbrPas());
        ((TextView) getActivity().findViewById(R.id.Progession_TextView_nbrCal)).setText(" " + db.getNbrCal());

        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) ((LinearLayout) getActivity().findViewById(R.id.Progression_Layout_Main)).getLayoutParams();
        Display mDisplay = getActivity().getWindowManager().getDefaultDisplay();
        layoutParams.width = mDisplay.getWidth();
        layoutParams.height = mDisplay.getHeight()-200;
    }


}
