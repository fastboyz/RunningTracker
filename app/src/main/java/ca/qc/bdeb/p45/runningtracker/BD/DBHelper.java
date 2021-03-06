package ca.qc.bdeb.p45.runningtracker.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import ca.qc.bdeb.p45.runningtracker.Common.Utils;
import ca.qc.bdeb.p45.runningtracker.Modele.Course;
import ca.qc.bdeb.p45.runningtracker.Modele.Objectif;

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
    private static final String COURSE_CALORIES = "BURNED_CALORIES";

    private static final String TABLE_NOM_OBJECTIF = "OBJECTIF";
    // Noms de colonnes
    private static final String OBJECTIF_ID = "_id";
    private static final String OBJECTIF_INITIALE = "DISTANCE_INITIALE";
    private static final String OBJECTIF_FINALE = "DISTANCE";
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
                + COURSE_CALORIES + " DOUBLE, "
                + COURSE_TEMPS + " LONG)";
        db.execSQL(sqlClient);

        sqlClient = "CREATE TABLE " + TABLE_NOM_OBJECTIF + "("
                + OBJECTIF_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + OBJECTIF_INITIALE + " REAL, "
                + OBJECTIF_FINALE + " REAL, "
                + OBJECTIF_DATE_FINAL + " LONG, "
                + OBJECTIF_DATE_COMMENCEMENT + " LONG)";
        db.execSQL(sqlClient);

        creerObjectifInitial(db);

        // Création de donné test durant la semaine passé
        Random random = new Random();
        for (int i = 6; i >= 0; i--) {
            int nombreKilometre = random.nextInt(5) + 1;
            creerCourseTest(db, new Course(nombreKilometre, 3, TimeUnit.MINUTES.toMillis(nombreKilometre * (10 + random.nextInt(4)+1)),
                    new Date(System.currentTimeMillis() - (i * (24 * 60 * 60 * 1000))), 666, 5.3,
                    Utils.COURSE_TYPE.PIEDS));
        }
        creerCourseTest(db, new Course(3, 3, TimeUnit.MINUTES.toMillis(3 * (10 + random.nextInt(2))),
                new Date(System.currentTimeMillis()), 666, 5.3, Utils.COURSE_TYPE.PIEDS));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long getNbrCoursePied(){
        SQLiteDatabase db = this.getReadableDatabase();
        long resultats = DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM "
                + TABLE_NOM_COURSE + " where " + COURSE_TYPE + " = "
                + Utils.COURSE_TYPE.PIEDS.ordinal() + ";", null);
        return resultats;
    }

    public long getNbrPas(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "
                + TABLE_NOM_COURSE + " where " + COURSE_TYPE + " = "
                + Utils.COURSE_TYPE.PIEDS.ordinal() + ";", null);
        long resultat = 0;
        if (cursor != null && cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                resultat += cursor.getLong(4);
            } while (cursor.moveToNext());
        }

        return resultat;
    }

    public long getNbrCal(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "
                + TABLE_NOM_COURSE + ";", null);
        long resultat = 0;
        if (cursor != null && cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                resultat += cursor.getDouble(7);
            } while (cursor.moveToNext());
        }

        return resultat;
    }

    public long getDistanceTotal(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "
                + TABLE_NOM_COURSE + ";", null);
        long resultat = 0;
        if (cursor != null && cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                resultat += cursor.getDouble(1);
            } while (cursor.moveToNext());
        }
        return resultat;
    }

    public long getNbrCourseVelo(){
        SQLiteDatabase db = this.getReadableDatabase();
        long resultats = DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM "
                + TABLE_NOM_COURSE + " where " + COURSE_TYPE + " = "
                + Utils.COURSE_TYPE.VELO.ordinal() + ";", null);
        return resultats;
    }

    public Course getAllStatsInOneDay(Date date) {
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        Date minDate = new Date(date.getTime());

        date.setHours(24);
        Date maxDate = new Date(date.getTime());

        SQLiteDatabase db = this.getReadableDatabase();
        Course course = new Course();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NOM_COURSE + " where " + COURSE_DATE
                + " > " + minDate.getTime() + " AND " + COURSE_DATE
                + " < " + maxDate.getTime() + ";", null);

        if (cursor != null && cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                course.ajouterDistance(cursor.getDouble(1));
                course.setTempsEcouler(course.getTempsEcouler() + cursor.getLong(8));
            } while (cursor.moveToNext());
        }
        return course;
    }

    public void creerObjectifInitial(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(OBJECTIF_INITIALE, 5);
        values.put(OBJECTIF_FINALE, 42);
        values.put(OBJECTIF_DATE_FINAL, new Date().getTime() + (6 * (24 * 60 * 60 * 1000)));
        values.put(OBJECTIF_DATE_COMMENCEMENT, new Date().getTime());
        long id = db.insert(TABLE_NOM_OBJECTIF, null, values);
    }

    public void creerCourseTest(SQLiteDatabase db, Course course) {
        ContentValues values = new ContentValues();
        values.put(COURSE_DISTANCE, course.getDistanteParcourue());
        values.put(COURSE_VITESSE, course.getVitesse());
        values.put(COURSE_DISTANCE_OBJECTIF, course.getObjectif());
        values.put(COURSE_NBR_PAS, course.getNbrPas());
        values.put(COURSE_DATE, course.getDate().getTime());
        values.put(COURSE_TYPE, course.getCourse_type().ordinal());
        values.put(COURSE_TEMPS, course.getTempsEcouler());
        values.put(COURSE_CALORIES, course.getCalories());
        long id = db.insert(TABLE_NOM_COURSE, null, values);
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
        values.put(COURSE_CALORIES, course.getCalories());
        values.put(COURSE_TYPE, course.getCourse_type().ordinal());

        long id = db.insert(TABLE_NOM_COURSE, null, values);
        db.close();
    }

    public void ajouterObjectif(Objectif objectif) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(OBJECTIF_INITIALE, objectif.getObjectifInitiale());
        values.put(OBJECTIF_FINALE, objectif.getObjectifFinale());
        values.put(OBJECTIF_DATE_FINAL, objectif.getObjectifDateFinal().getTime());
        values.put(OBJECTIF_DATE_COMMENCEMENT, objectif.getObjectifDateCommencement().getTime());
        long id = db.insert(TABLE_NOM_OBJECTIF, null, values);
        db.close();
    }

    public float getPourcentageObjectifAccomplie() {
        SQLiteDatabase db = this.getReadableDatabase();
        long nbrObjectifRéussi = DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM "
                + TABLE_NOM_COURSE + " where " + COURSE_DISTANCE + " > "
                + COURSE_DISTANCE_OBJECTIF + ";", null);
        long numRows = DatabaseUtils.queryNumEntries(db, TABLE_NOM_COURSE);
        if (numRows != 0) {
            float pourcentage = (nbrObjectifRéussi * 100) / numRows;
            return pourcentage;
        } else {
            return 100;
        }
    }

    public double getLastRunDistance() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NOM_COURSE + " where " + COURSE_ID
                + " = (SELECT MAX(" + COURSE_ID + ") FROM " + TABLE_NOM_COURSE + ")" + ";", null);
        if (cursor != null && cursor.getCount() != 0) {
            cursor.moveToFirst();
            return cursor.getDouble(1);
        } else {
            return -1;
        }
    }

    public Objectif getCurrentObjectif() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NOM_OBJECTIF + " where " + OBJECTIF_ID
                + " = (SELECT MAX(" + OBJECTIF_ID + ") FROM " + TABLE_NOM_OBJECTIF + ")" + ";", null);
        Objectif objectif = new Objectif();
        if (cursor != null) {
            cursor.moveToFirst();
            objectif.setObjectifDateCommencement(new Date(cursor.getLong(4)));
            objectif.setObjectifDateFinal(new Date(cursor.getLong(3)));
            double initial = cursor.getDouble(1);
            double distanceFinal = cursor.getDouble(2);
            long startTime = cursor.getLong(4);
            long endTime = cursor.getLong(3);
            long todayTime = System.currentTimeMillis();
            long diffTime = endTime - startTime;
            long diffDaysTotal = diffTime / (1000 * 60 * 60 * 24);
            diffTime = todayTime - startTime;
            long diffDaysToday = diffTime / (1000 * 60 * 60 * 24);
            double diffDistance = distanceFinal - initial;
            double distanceParJour = diffDistance / diffDaysTotal;
            if (diffDaysTotal > diffDaysToday) {
                objectif.setObjectifCourrent((int) (initial + (distanceParJour * diffDaysToday)));
            } else {
                objectif.setObjectifCourrent((int) distanceFinal);
            }
            objectif.setObjectifFinale(distanceFinal);
        }
        return objectif;
    }

    public ArrayList<Course> getAllRuns() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Course> listCourse = new ArrayList<Course>();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NOM_COURSE + " ;", null);

        if (cursor != null && cursor.getCount()!=0) {
            cursor.moveToFirst();
            do {
                Course course = new Course();
                course.setCourse_type(Utils.COURSE_TYPE.values()[cursor.getInt(6)]);
                course.ajouterDistance(cursor.getDouble(1));
                course.setObjectif(cursor.getDouble(3));
                course.setNbrPas(cursor.getInt(4));
                course.setDate(new Date(cursor.getLong(5)));
                course.setCalories(cursor.getDouble(7));
                course.setTempsEcouler(cursor.getLong(8));
                listCourse.add(course);
            } while (cursor.moveToNext());
        }
        return listCourse;
    }
}
