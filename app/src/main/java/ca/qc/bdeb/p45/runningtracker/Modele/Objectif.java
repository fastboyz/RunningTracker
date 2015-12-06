package ca.qc.bdeb.p45.runningtracker.Modele;

import java.util.Date;

/**
 * Created by 1345280 on 2015-12-04.
 */
public class Objectif {
    private int OBJECTIF_ID ;
    private int OBJECTIF_INITIALE;
    private int OBJECTIF_DISTANCE;
    private double OBJECTIF_DISTANCE_Final;
    private Date OBJECTIF_DATE_FINAL;
    private Date OBJECTIF_DATE_COMMENCEMENT;

    public double getOBJECTIF_DISTANCE_Final() {
        return OBJECTIF_DISTANCE_Final;
    }

    public void setOBJECTIF_DISTANCE_Final(double OBJECTIF_DISTANCE_Final) {
        this.OBJECTIF_DISTANCE_Final = OBJECTIF_DISTANCE_Final;
    }

    public int getOBJECTIF_ID() {
        return OBJECTIF_ID;
    }

    public void setOBJECTIF_ID(int OBJECTIF_ID) {
        this.OBJECTIF_ID = OBJECTIF_ID;
    }

    public int getOBJECTIF_INITIALE() {
        return OBJECTIF_INITIALE;
    }

    public void setOBJECTIF_INITIALE(int OBJECTIF_INITIALE) {
        this.OBJECTIF_INITIALE = OBJECTIF_INITIALE;
    }

    public int getOBJECTIF_DISTANCE() {
        return OBJECTIF_DISTANCE;
    }

    public void setOBJECTIF_DISTANCE(int OBJECTIF_DISTANCE) {
        this.OBJECTIF_DISTANCE = OBJECTIF_DISTANCE;
    }

    public Date getOBJECTIF_DATE_FINAL() {
        return OBJECTIF_DATE_FINAL;
    }

    public void setOBJECTIF_DATE_FINAL(Date OBJECTIF_DATE_FINAL) {
        this.OBJECTIF_DATE_FINAL = OBJECTIF_DATE_FINAL;
    }

    public Date getOBJECTIF_DATE_COMMENCEMENT() {
        return OBJECTIF_DATE_COMMENCEMENT;
    }

    public void setOBJECTIF_DATE_COMMENCEMENT(Date OBJECTIF_DATE_COMMENCEMENT) {
        this.OBJECTIF_DATE_COMMENCEMENT = OBJECTIF_DATE_COMMENCEMENT;
    }
}
