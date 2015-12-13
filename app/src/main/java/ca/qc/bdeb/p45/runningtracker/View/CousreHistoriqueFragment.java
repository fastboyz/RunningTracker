package ca.qc.bdeb.p45.runningtracker.View;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import ca.qc.bdeb.p45.runningtracker.BD.DBHelper;
import ca.qc.bdeb.p45.runningtracker.Modele.CourseAdapter;
import ca.qc.bdeb.p45.runningtracker.R;


public class CousreHistoriqueFragment extends ListFragment {
    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;
    CourseAdapter courseAdapter;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CousreHistoriqueFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cousrehistorique_list, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialise();
    }

    private void initialise() {
        if (courseAdapter != null){
            courseAdapter.clear();
        }
        courseAdapter = new CourseAdapter(getActivity(), R.layout.fragment_cousrehistorique_list, DBHelper.getInstance(getActivity()).getAllRuns());
        this.setListAdapter(courseAdapter);
        courseAdapter.notifyDataSetChanged();
    }
}
