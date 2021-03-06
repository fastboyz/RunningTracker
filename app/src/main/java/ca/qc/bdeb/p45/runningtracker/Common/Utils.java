package ca.qc.bdeb.p45.runningtracker.Common;

import android.location.Location;;

import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;

import ca.qc.bdeb.p45.runningtracker.Modele.Course;

/**
 * Created by mbiel on 11/25/2015.
 */
public class Utils {
    public enum COURSE_TYPE {
        VELO(1),
        PIEDS(2);
        private final int VALEUR;

        COURSE_TYPE(final int valeur) {
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

        // Distance en Kilometre
        return (double) (lastLoc.distanceTo(newLoc) / 1000);
    }

    public String formatDecimal(double aConvertir) {
        DecimalFormat format = new DecimalFormat("0.00");
        return format.format(aConvertir);
    }

    public double convertirEnHeure(double temp) {
        return (temp / (1000 * 60 * 60));
    }

    public int calculerPourcentageFini(double distanceParcourue, int distanceObjectif) {

        int pourcentage = (int) ((distanceParcourue * 100) / distanceObjectif);
        if (pourcentage < 100) {
            return pourcentage;
        }
        return 100;
    }

    public int calculerNombreDePas(double distanceParcourue){

        int nbsPas = (int) (Commons.NBS_DE_PAS_MOYEN_PAR_KM * distanceParcourue);

        return nbsPas;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
