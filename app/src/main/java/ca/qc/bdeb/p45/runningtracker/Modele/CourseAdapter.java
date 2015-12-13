package ca.qc.bdeb.p45.runningtracker.Modele;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
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

    private class CourseHolder {
        ImageView courseType;
        ImageView objectifReussi;
        TextView date;
        TextView distance;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CourseHolder holder = null;
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        Course courseCourrant = getItem(position);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_historique, null);
            holder = new CourseHolder();
            holder.courseType = (ImageView) convertView.findViewById(R.id.ListItemHistrique_ImageView_TypeCourse);
            holder.objectifReussi = (ImageView) convertView.findViewById(R.id.ListItemHistrique_ImageView_ObjectifSucceded);
            holder.date = (TextView) convertView.findViewById(R.id.ListItemHistrique_TextView_Date);
            holder.distance = (TextView) convertView.findViewById(R.id.ListItemHistrique_TextView_Distance);
        } else
            holder = (CourseHolder) convertView.getTag();

        try {
            if (courseCourrant.getCourse_type() == Utils.COURSE_TYPE.PIEDS) {
                holder.courseType.setImageResource(R.drawable.ic_walk);
            } else {
                holder.courseType.setImageResource(R.drawable.ic_bicycle);
            }

            if (courseCourrant.getObjectif() < courseCourrant.getDistanteParcourue()) {
                holder.objectifReussi.setImageResource(R.mipmap.ic_objectif_atteint);
            } else {
                holder.objectifReussi.setImageResource(R.drawable.ic_objectif_non_atteint);
            }
            holder.distance.setText("" + courseCourrant.getDistanteParcourue() + " Km");
            SimpleDateFormat dt = new SimpleDateFormat("d-M-yyyy");
            holder.date.setText(dt.format(courseCourrant.getDate()));
        } catch (Exception e) {

        }
        return convertView;
    }

}
