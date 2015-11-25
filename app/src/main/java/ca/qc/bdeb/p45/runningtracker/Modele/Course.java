package ca.qc.bdeb.p45.runningtracker.Modele;

import ca.qc.bdeb.p45.runningtracker.Common.StateCourse;

/**
 * Created by mbiel on 11/24/2015.
 */
public class Course {
    private double distanteParcourue;

    private double tempsEcouler;

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
}
