package ca.qc.bdeb.p45.runningtracker.Modele;


import java.util.Date;

import ca.qc.bdeb.p45.runningtracker.Common.Commons;
import ca.qc.bdeb.p45.runningtracker.Common.StateCourse;
import ca.qc.bdeb.p45.runningtracker.Common.Utils;

/**
 * Created by mbiel on 11/24/2015.
 */
public class Course {
    private double distanteParcourue;
    private long tempsEcouler;
    private double objectif;
    private Date date;
    private int nbrPas;
    private double vitesse;
    private double calories;

    public double getCalories() {
        double cal = 0.0;
        if (this.course_type == Utils.COURSE_TYPE.PIEDS) {
            cal = (1.16 * Commons.POIDS_MOYEN) * this.distanteParcourue;
        } else {
            cal = ((1.16 * Commons.POIDS_MOYEN) * this.distanteParcourue * 2.9);
        }
        return cal;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    private Utils.COURSE_TYPE course_type;

    public long getTempsEcouler() {
        return tempsEcouler;
    }

    public void setTempsEcouler(long tempsEcouler) {
        this.tempsEcouler = tempsEcouler;
    }

    private StateCourse state;

    public void ajouterDistance (double distance) {
        distanteParcourue = distanteParcourue + distance;
    }

    public Course(double distanteParcourue, double objectif, long tempsEcouler, Date date, int nbrPas, double vitesse, Utils.COURSE_TYPE course_type) {
        this.distanteParcourue = distanteParcourue;
        this.objectif = objectif;
        this.tempsEcouler = tempsEcouler;
        this.date = date;
        this.nbrPas = nbrPas;
        this.vitesse = vitesse;
        this.course_type = course_type;
        state = StateCourse.EN_COURS;
    }

    public Course() {
        state = StateCourse.EN_COURS;
        date = new Date();
    }

    public void changeState(){
        if (state == StateCourse.EN_COURS){
            state = StateCourse.ARRETER;
        }else{
            state = StateCourse.EN_COURS;
        }
    }

    public StateCourse getState() {
        return state;
    }

    public double getDistanteParcourue() {
        return distanteParcourue;
    }

    public double getVitesse() {
        if (tempsEcouler != 0) {
            return distanteParcourue / Utils.getInstance().convertirEnHeure(tempsEcouler);
        } else {
            return 0;
        }
    }

    public double getPourcentageObjectifCompleter() {
        return distanteParcourue/objectif;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public double getObjectif() {
        return objectif;
    }

    public int getNbrPas() {
        return nbrPas;
    }


    public void setObjectif(double objectif) {
        this.objectif = objectif;
    }

    public void setNbrPas(int nbrPas) {
        this.nbrPas = nbrPas;
    }

    public Utils.COURSE_TYPE getCourse_type() {
        return course_type;
    }

    public void setCourse_type(Utils.COURSE_TYPE course_type) {
        this.course_type = course_type;
    }
}
