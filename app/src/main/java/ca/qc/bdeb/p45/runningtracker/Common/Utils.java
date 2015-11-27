package ca.qc.bdeb.p45.runningtracker.Common;
import android.location.Location;;

import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;

import ca.qc.bdeb.p45.runningtracker.Modele.Course;

/**
 * Created by mbiel on 11/25/2015.
 */
public class Utils {
    public enum COURSE_TYPE{
        VELO(1),
        PIEDS(2);
        private final int VALEUR;

        COURSE_TYPE(final int valeur){
            this.VALEUR = valeur;
        }

        public int getVALEUR() {
            return this.VALEUR;
        }
    }

    private static Utils ourInstance = new Utils();

    public static Utils getInstance() {
        return ourInstance;
    }

    private Utils() {
    }


    public double calculerDistante(LatLng lastKnownPos, LatLng newPos) {
        Location lastLoc = new Location("");
        Location newLoc = new Location("");
        lastLoc.setLatitude(lastKnownPos.latitude);
        lastLoc.setLongitude(lastKnownPos.longitude);
        newLoc.setLongitude(newPos.longitude);
        newLoc.setLatitude(newPos.latitude);


        return (double) (lastLoc.distanceTo(newLoc) / 1000);
    }

    public String formatDecimal(Course course) {
        DecimalFormat format = new DecimalFormat("0.00");
        return format.format(course.getDistanteParcourue());
    }
}
