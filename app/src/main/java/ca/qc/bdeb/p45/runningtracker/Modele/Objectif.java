package ca.qc.bdeb.p45.runningtracker.Modele;

/**
 * Created by 1345280 on 2015-12-04.
 */
public class Objectif {
    private int OBJECTIF_ID ;
    private int OBJECTIF_INITIALE;
    private int OBJECTIF_DISTANCE;
    private long OBJECTIF_DATE_FINAL;
    private long OBJECTIF_DATE_COMMENCEMENT;


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

    public long getOBJECTIF_DATE_FINAL() {
        return OBJECTIF_DATE_FINAL;
    }

    public void setOBJECTIF_DATE_FINAL(long OBJECTIF_DATE_FINAL) {
        this.OBJECTIF_DATE_FINAL = OBJECTIF_DATE_FINAL;
    }

    public long getOBJECTIF_DATE_COMMENCEMENT() {
        return OBJECTIF_DATE_COMMENCEMENT;
    }

    public void setOBJECTIF_DATE_COMMENCEMENT(long OBJECTIF_DATE_COMMENCEMENT) {
        this.OBJECTIF_DATE_COMMENCEMENT = OBJECTIF_DATE_COMMENCEMENT;
    }
}
