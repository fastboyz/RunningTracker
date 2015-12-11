package ca.qc.bdeb.p45.runningtracker.Modele;

import java.util.Date;

/**
 * Created by 1345280 on 2015-12-04.
 */
public class Objectif {
    private int objectifId;
    private int objectifInitiale;
    private int objectifCourrent;
    private double objectifFinale;
    private Date objectifDateFinal;
    private Date objectifDateCommencement;

    public double getObjectifFinale() {
        return objectifFinale;
    }

    public void setObjectifFinale(double objectifFinale) {
        this.objectifFinale = objectifFinale;
    }

    public int getObjectifId() {
        return objectifId;
    }

    public void setObjectifId(int objectifId) {
        this.objectifId = objectifId;
    }

    public int getObjectifInitiale() {
        return objectifInitiale;
    }

    public void setObjectifInitiale(int objectifInitiale) {
        this.objectifInitiale = objectifInitiale;
    }

    public int getObjectifCourrent() {
        return objectifCourrent;
    }

    public void setObjectifCourrent(int objectifCourrent) {
        this.objectifCourrent = objectifCourrent;
    }

    public Date getObjectifDateFinal() {
        return objectifDateFinal;
    }

    public void setObjectifDateFinal(Date objectifDateFinal) {
        this.objectifDateFinal = objectifDateFinal;
    }

    public Date getObjectifDateCommencement() {
        return objectifDateCommencement;
    }

    public void setObjectifDateCommencement(Date objectifDateCommencement) {
        this.objectifDateCommencement = objectifDateCommencement;
    }
}
