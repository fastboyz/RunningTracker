package ca.qc.bdeb.p45.runningtracker.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.w3c.dom.ProcessingInstruction;

import java.util.ArrayList;
import java.util.Date;

import ca.qc.bdeb.p45.runningtracker.Common.Utils;
import ca.qc.bdeb.p45.runningtracker.Modele.Course;

/**
 * Created by 1345280 on 2015-11-25.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "RUN_TRACKER";
    public static final int DBVERSION = 1;
    private Context context;
    private static DBHelper instance = null;

    private static final String TABLE_NOM_COURSE = "COURSE";
    // Noms de colonnes
    private static final String COURSE_ID = "_id";
    private static final String COURSE_DISTANCE = "DISTANCE";
    private static final String COURSE_VITESSE = "VITESSE";
    private static final String COURSE_NBR_PAS = "NBR_PAS";
    private static final String COURSE_DISTANCE_OBJECTIF = "DISTANCE_OBJECTIF";
    private static final String COURSE_DATE = "DATE";
    private static final String COURSE_TEMPS = "TEMPS";
    private static final String COURSE_TYPE = "TYPE";

    private static final String TABLE_NOM_OBJECTIF = "OBJECTIF";
    // Noms de colonnes
    private static final String OBJECTIF_ID = "_id";
    private static final String OBJECTIF_INITIALE = "DISTANCE_INITIALE";
    private static final String OBJECTIF_DISTANCE = "DISTANCE";
    private static final String OBJECTIF_DATE_FINAL = "VITESSE";
    private static final String OBJECTIF_DATE_COMMENCEMENT = "NBR_PAS";

    public static DBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DBHelper(context.getApplicationContext());
        }
        return instance;
    }

    /**
     * Constructeur de DBHelper
     */
    private DBHelper(Context context) {
        super(context, DB_NAME, null, DBVERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlClient = "CREATE TABLE " + TABLE_NOM_COURSE + "("
                + COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COURSE_DISTANCE + " REAL, "
                + COURSE_VITESSE + " REAL, "
                + COURSE_DISTANCE_OBJECTIF + " REAL, "
                + COURSE_NBR_PAS + " INTEGER, "
                + COURSE_DATE + " LONG, "
                + COURSE_TYPE + " INTEGER, "
                + COURSE_TEMPS + " REAL)";
        db.execSQL(sqlClient);

        sqlClient = "CREATE TABLE " + TABLE_NOM_OBJECTIF + "("
                + OBJECTIF_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + OBJECTIF_INITIALE + " REAL, "
                + OBJECTIF_DISTANCE + " REAL, "
                + OBJECTIF_DATE_FINAL + " LONG, "
                + OBJECTIF_DATE_COMMENCEMENT + " LONG)";
        db.execSQL(sqlClient);

        creerObjectifInitial(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void creerObjectifInitial(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(OBJECTIF_INITIALE, 0);
        values.put(OBJECTIF_DISTANCE, 0);
        values.put(OBJECTIF_DATE_FINAL, 0);
        values.put(OBJECTIF_DATE_COMMENCEMENT, 0);
        long id = db.insert(TABLE_NOM_OBJECTIF, null, values);
    }

    public void ajouterCourse(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COURSE_DATE, course.getDate().getTime());
        values.put(COURSE_DISTANCE, course.getDistanteParcourue());
        values.put(COURSE_NBR_PAS, course.getNbrPas());
        values.put(COURSE_DISTANCE_OBJECTIF, course.getObjectif());
        values.put(COURSE_VITESSE, course.getVitesse());
        values.put(COURSE_TEMPS, course.getTempsEcouler());
        values.put(COURSE_TYPE, course.getCourse_type().getVALEUR());

        long id = db.insert(TABLE_NOM_COURSE, null, values);
        db.close();
    }

    public double getPourcentageObjectifAccomplie() {
        SQLiteDatabase db = this.getReadableDatabase();
        long nbrObjectifRéussi = DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM "
                + TABLE_NOM_COURSE + " where " + COURSE_DISTANCE + " > "
                + COURSE_DISTANCE_OBJECTIF + ";", null);
        long numRows = DatabaseUtils.queryNumEntries(db, "table_name");
        return (nbrObjectifRéussi / numRows) * 100;
    }

    public double getLastRunDistance(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NOM_COURSE + " where " + COURSE_ID
                + " = (SELECT MAX("+COURSE_ID+") FROM "+TABLE_NOM_COURSE+")" + ";", null);
        if (cursor != null){
            cursor.moveToFirst();
            return cursor.getDouble(1);
        } else {
            return -1;
        }
    }

    public double getCurrentObjectif(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NOM_OBJECTIF + " where " + OBJECTIF_ID
                + " = (SELECT MAX("+OBJECTIF_ID+") FROM "+TABLE_NOM_OBJECTIF+")" + ";", null);
        if (cursor != null) {
            cursor.moveToFirst();
            double initial = cursor.getDouble(1);
            double distanceFinal = cursor.getDouble(1);
            return distanceFinal;
        } else {
            return -1;
        }
    }

    public ArrayList<Course> getLasMonthRuns() {
        Date lastMonth = new Date();
        lastMonth.setMonth(lastMonth.getMonth() - 1);
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Course> listCourse = new ArrayList<Course>();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NOM_COURSE + " where " + COURSE_DATE
                + " > " + lastMonth.getTime() + " ORDER BY " + COURSE_DATE + " ;", null);

        if (cursor != null) {
            cursor.moveToFirst();
            do {
                Course course = new Course();
                // TO DO: maitre le bon type de course
                course.setCourse_type(Utils.COURSE_TYPE.PIEDS);
                course.ajouterDistance(cursor.getDouble(1));
                course.setObjectif(cursor.getDouble(3));
                course.setNbrPas(cursor.getInt(4));
                course.setDate(new Date(cursor.getLong(5)));
                course.setTempsEcouler(cursor.getDouble(7));
                listCourse.add(course);
            } while (cursor.moveToNext());
        }
        return listCourse;
    }
}
