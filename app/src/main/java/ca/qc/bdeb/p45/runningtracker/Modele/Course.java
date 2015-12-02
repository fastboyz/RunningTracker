package ca.qc.bdeb.p45.runningtracker.Modele;

import java.util.Date;

import ca.qc.bdeb.p45.runningtracker.Common.StateCourse;
import ca.qc.bdeb.p45.runningtracker.Common.Utils;

/**
 * Created by mbiel on 11/24/2015.
 */
public class Course {
    private double distanteParcourue;
    private double tempsEcouler;
    private double objectif;
    private Date date;
    private int nbrPas;
    private double vitesse;
    private Utils.COURSE_TYPE course_type;

    public double getTempsEcouler() {
        return tempsEcouler;
    }

    public void setTempsEcouler(double tempsEcouler) {

        this.tempsEcouler = tempsEcouler;
    }

    private StateCourse state;

    public void ajouterDistance (double distance){
        distanteParcourue = distanteParcourue + distance;
    }

    public Course() {
        state = StateCourse.EN_COURS;
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
        return distanteParcourue/tempsEcouler;
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
