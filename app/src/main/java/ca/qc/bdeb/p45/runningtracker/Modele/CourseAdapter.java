package ca.qc.bdeb.p45.runningtracker.Modele;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import ca.qc.bdeb.p45.runningtracker.Common.Utils;
import ca.qc.bdeb.p45.runningtracker.R;

/**
 * Created by 1345280 on 2015-12-13.
 */
public class CourseAdapter extends ArrayAdapter<Course> {


    Context context;

    public CourseAdapter(Context context, int resource, List<Course> items) {
        super(context, resource, items);
        this.context = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        Course courseCourrant = getItem(position);

        ImageView courseType;
        ImageView objectifReussi;
        TextView date;
        TextView distance;

        convertView = mInflater.inflate(R.layout.list_item_historique, null);
        courseType = (ImageView) convertView.findViewById(R.id.ListItemHistrique_ImageView_TypeCourse);
        objectifReussi = (ImageView) convertView.findViewById(R.id.ListItemHistrique_ImageView_ObjectifSucceded);
        date = (TextView) convertView.findViewById(R.id.ListItemHistrique_TextView_Date);
        distance = (TextView) convertView.findViewById(R.id.ListItemHistrique_TextView_Distance);

        try {
            if (courseCourrant.getCourse_type() == Utils.COURSE_TYPE.PIEDS) {
                courseType.setImageResource(R.drawable.ic_walk);
            } else {
                courseType.setImageResource(R.drawable.ic_bicycle_deux);
            }
            if (courseCourrant.getObjectif() < courseCourrant.getDistanteParcourue()) {
                objectifReussi.setImageResource(R.mipmap.ic_objectif_atteint);
            } else {
                objectifReussi.setImageResource(R.drawable.ic_objectif_non_atteint);
            }
            distance.setText("" + Utils.round(courseCourrant.getDistanteParcourue(), 2) + " Km");
            SimpleDateFormat dt = new SimpleDateFormat("d-M-yyyy");
            date.setText(dt.format(courseCourrant.getDate()));
        } catch (Exception e) {

        }
        return convertView;
    }

}
