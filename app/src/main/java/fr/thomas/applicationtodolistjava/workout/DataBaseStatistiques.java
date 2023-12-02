package fr.thomas.applicationtodolistjava.workout;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.se.omapi.Session;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import fr.thomas.applicationtodolistjava.workout.ExercicesStats;

public class DataBaseStatistiques extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Statistiques.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_STATISTICS = "statistics";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_SESSION_ID = "session_id";
    private static final String COLUMN_SEANCE_ID = "id_statistic";
    private static final String COLUMN_EXERCISE_NUMBER = "exercise_number";
    private static final String COLUMN_REPETITION_NUMBER = "repetition_number";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_HOUR = "heure";
    private static final String COLUMN_NAME_EXO = "name_exo";
    private static final String COLUMN_REPETITIONS = "repetitions";
    private static final String COLUMN_SERIES = "number_series";
    private static final String COLUMN_CHARGE = "charge";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_REST_TIME = "rest_time";
    private static final String COLUMN_RECOVERY_TIME = "recovery_time";
    private static final String COLUMN_DIFFICULTY = "difficulté";
    private static final String COLUMN_STATUS = "status";

    /*
     *Status :
     * "Record" : en train d'être enregistré
     * "Finish" : la séance est finie (met logiquement l'id statistique
     * "Edit" : l'utilisateur modifie la séance
     */

    /*
     * Facile
     * Moyen
     * Difficile
     * Fail
     * Trop facile
     * Sauté
     */


    public DataBaseStatistiques(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String strSql = "CREATE TABLE " + TABLE_STATISTICS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_SESSION_ID + " INTEGER,"
                + COLUMN_SEANCE_ID + " INTEGER,"
                + COLUMN_EXERCISE_NUMBER + " INTEGER,"
                + COLUMN_REPETITION_NUMBER + " INTEGER,"
                + COLUMN_DATE + " TEXT,"
                + COLUMN_HOUR + " TEXT,"
                + COLUMN_NAME_EXO + " TEXT,"
                + COLUMN_REPETITIONS + " INTEGER,"
                + COLUMN_SERIES + " INTEGER,"
                + COLUMN_CHARGE + " INTEGER,"
                + COLUMN_TIME + " INTEGER,"
                + COLUMN_REST_TIME + " INTEGER,"
                + COLUMN_RECOVERY_TIME + " INTEGER,"
                + COLUMN_DIFFICULTY + " TEXT,"
                + COLUMN_STATUS + " TEXT" + ")";
        db.execSQL(strSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //Fonctions générales

    private int getLastSessionId() {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT MAX(" + COLUMN_SESSION_ID + ") FROM " + TABLE_STATISTICS, null);
        int lastSessionId = 0;
        if (cursor.moveToFirst()) {
            lastSessionId = cursor.getInt(0);
        }
        cursor.close();
        db.close();

        return lastSessionId;
    }
    private int getLastExerciseNumber() {
        int lastExerciseNumber = -1;
        String query = "SELECT " + COLUMN_EXERCISE_NUMBER + " FROM " + TABLE_STATISTICS + " ORDER BY " + COLUMN_ID + " DESC LIMIT 1";

        Cursor cursor = this.getWritableDatabase().rawQuery(query, null);
        if (cursor.moveToFirst()) {
            lastExerciseNumber = cursor.getInt(cursor.getColumnIndex(COLUMN_EXERCISE_NUMBER));
        }
        cursor.close();

        return lastExerciseNumber;
    }
    private int getLastRepetitionNumber(){
        int lastRepetitionNumber = -1;
        String query = "SELECT " + COLUMN_REPETITION_NUMBER + " FROM " + TABLE_STATISTICS + " ORDER BY " + COLUMN_ID + " DESC LIMIT 1";

        Cursor cursor = this.getWritableDatabase().rawQuery(query, null);
        if (cursor.moveToFirst()) {
            lastRepetitionNumber = cursor.getInt(cursor.getColumnIndex(COLUMN_REPETITION_NUMBER));
        }
        cursor.close();

        return lastRepetitionNumber;
    }

    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    private String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void setLastSessionId(int sessionId){
        setLastElement(TABLE_STATISTICS, COLUMN_SESSION_ID, sessionId);
    }
    public void setIdSeance(int seanceId){
        setLastElement(TABLE_STATISTICS, COLUMN_SEANCE_ID, seanceId);
    }
    public void setLastExerciseNumber(int exerciseNumber){

    }
    public void setLastDate(){

    }
    public void setLastHour(){

    }
    public void setLastNameExo(String nameExo){

    }
    public void setLastRepetitions(int repetitions){
        String strSql = "UPDATE statistics SET repetitions = 10 WHERE id = (SELECT MAX(id) FROM statistics)";
        this.getWritableDatabase().execSQL(strSql);
    }
    public void setLastSeries(int series){
        setLastElement(TABLE_STATISTICS, COLUMN_SERIES, series);
    }
    public void setLastCharge(int charge){
        setLastElement(TABLE_STATISTICS, COLUMN_CHARGE, charge);
    }
    public void setLastTime(int time){
        setLastElement(TABLE_STATISTICS, COLUMN_TIME, time);
    }
    public void setLastRest(int rest){
        setLastElement(TABLE_STATISTICS, COLUMN_REST_TIME, rest);
    }
    public void setLastRecovery(int recovery){
        setLastElement(TABLE_STATISTICS, COLUMN_RECOVERY_TIME, recovery);
    }
    public void setLastDifficulty(String difficulty){
        setLastElement(TABLE_STATISTICS, COLUMN_DIFFICULTY, difficulty);
    }
    public void setLastStatus(String status){
        setLastElement(TABLE_STATISTICS, COLUMN_STATUS, status);
    }

    // Méthodes pour la gestion des exercices

    public void addNewSession(int id_seance){
        String query = "INSERT INTO " + TABLE_STATISTICS + " (" +
                COLUMN_SESSION_ID + ", " + COLUMN_SEANCE_ID + ", " + COLUMN_EXERCISE_NUMBER + ", " + COLUMN_REPETITION_NUMBER + ", " + COLUMN_DATE + ", " +
                COLUMN_HOUR + ", " + COLUMN_NAME_EXO + ", " + COLUMN_REPETITIONS + ", " +
                COLUMN_SERIES + ", " + COLUMN_CHARGE + ", " + COLUMN_TIME + ", " +
                COLUMN_REST_TIME + ", " + COLUMN_RECOVERY_TIME + ", " + COLUMN_DIFFICULTY + ", " +
                COLUMN_STATUS + ") VALUES (" +
                (getLastSessionId() + 1) + ", " + id_seance + ", 1, 1, \"" + getCurrentDate() + "\", \"" +
                getCurrentTime() + "\", \"\", 0, 0, 0, \"\", \"\", \"\", \"\", \"Record\")";
        this.getWritableDatabase().execSQL(query);
    }
    public void addNewExercise(int id_seance){
        String query = "INSERT INTO " + TABLE_STATISTICS + " (" +
                COLUMN_SESSION_ID + ", " + COLUMN_SEANCE_ID + ", " + COLUMN_EXERCISE_NUMBER + ", " + COLUMN_REPETITION_NUMBER + ", " + COLUMN_DATE + ", " +
                COLUMN_HOUR + ", " + COLUMN_NAME_EXO + ", " + COLUMN_REPETITIONS + ", " +
                COLUMN_SERIES + ", " + COLUMN_CHARGE + ", " + COLUMN_TIME + ", " +
                COLUMN_REST_TIME + ", " + COLUMN_RECOVERY_TIME + ", " + COLUMN_DIFFICULTY + ", " +
                COLUMN_STATUS + ") VALUES (" +
                (getLastSessionId()) + ", " + id_seance + ", " + (getLastExerciseNumber() + 1) + ", 1, \"" + getCurrentDate() + "\", \"" +
                getCurrentTime() + "\", \"\", 0, 0, 0, \"\", \"\", \"\", \"\", \"Record\")";
        this.getWritableDatabase().execSQL(query);
    }
    public void addNewRepetition(int id_seance){
        String query = "INSERT INTO " + TABLE_STATISTICS + " (" +
                COLUMN_SESSION_ID + ", " + COLUMN_SEANCE_ID + ", " + COLUMN_EXERCISE_NUMBER + ", " + COLUMN_REPETITION_NUMBER + ", " + COLUMN_DATE + ", " +
                COLUMN_HOUR + ", " + COLUMN_NAME_EXO + ", " + COLUMN_REPETITIONS + ", " +
                COLUMN_SERIES + ", " + COLUMN_CHARGE + ", " + COLUMN_TIME + ", " +
                COLUMN_REST_TIME + ", " + COLUMN_RECOVERY_TIME + ", " + COLUMN_DIFFICULTY + ", " +
                COLUMN_STATUS + ") VALUES (" +
                (getLastSessionId()) + ", " + id_seance + ", " + (getLastExerciseNumber()) + ", " + (getLastRepetitionNumber() + 1) + ", \"" + getCurrentDate() + "\", \"" +
                getCurrentTime() + "\", \"\", 0, 0, 0, \"\", \"\", \"\", \"\", \"Record\")";
        this.getWritableDatabase().execSQL(query);
    }






    public void setElement(String table, String colonne, String value, int id, String id_colonne){
        String strSql = "UPDATE " + table + " SET " + colonne + " = " + value + " WHERE " + id_colonne + "=" + id;
        this.getWritableDatabase().execSQL(strSql);
    }
    public void setElement(String table, String colonne, int value, int id, String id_colonne){
        String strSql = "UPDATE " + table + " SET " + colonne + " = " + value + " WHERE " + id_colonne + "=" + id;
        this.getWritableDatabase().execSQL(strSql);
    }

    public int getIntElement(String table, String column, String condition, String condition_element){
        String strSql = "SELECT * FROM " + table + " WHERE " + condition + "=" + condition_element;
        Cursor cursor = this.getWritableDatabase().rawQuery(strSql, null);
        if(cursor.moveToFirst()){
            return cursor.getInt(cursor.getColumnIndex(column));
        }
        return 0;
    }
    public String getStringElement(String table, String column, String condition, String condition_element){
        String strSql = "SELECT * FROM " + table + " WHERE " + condition + "=" + condition_element;
        Cursor cursor = this.getWritableDatabase().rawQuery(strSql, null);
        if(cursor.moveToFirst()){
            return cursor.getString(cursor.getColumnIndex(column));
        }
        return null;
    }

    public String getLastElementString(String table, String column){
        String strSql = "SELECT * FROM " + table;
        Cursor cursor = this.getWritableDatabase().rawQuery(strSql, null);
        String last_element = null;
        if(cursor.moveToLast()){
            last_element = cursor.getString(cursor.getColumnIndex(column));
        }
        cursor.close();
        return last_element;
    }
    public int getLastElementInt(String table, String column){
        String strSql = "SELECT * FROM " + table;
        Cursor cursor = this.getWritableDatabase().rawQuery(strSql, null);
        int last_element = 0;
        if(cursor.moveToLast()){
            last_element = cursor.getInt(cursor.getColumnIndex(column));
        }
        cursor.close();
        return last_element;
    }
    public void setLastElement(String table, String column, String element) {
        String strSql = "UPDATE " + table + " SET " + column + " = ? WHERE id = (SELECT MAX(id) FROM " + table + ")";
        this.getWritableDatabase().execSQL(strSql, new String[] { element });
    }
    public void setLastElement(String table, String column, int element){
        String strSql = "UPDATE " + table + " SET " + column + " = " + element + " WHERE id = (SELECT MAX(id) FROM " + table + ")";
        this.getWritableDatabase().execSQL(strSql);
    }









    public ArrayList<ExercicesStats> getLastExercisesStatisticWithExoName(String name_exo){
        ArrayList<ExercicesStats> exercicesStats = new ArrayList<>();
        String strSql = "SELECT * FROM " + TABLE_STATISTICS + " WHERE id_statistic = (SELECT MAX(id_statistic) FROM " + TABLE_STATISTICS + ") AND " + COLUMN_NAME_EXO + " = '" + name_exo + "'";

        Cursor cursor = getWritableDatabase().rawQuery(strSql, null);

        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                int session_id = cursor.getInt(cursor.getColumnIndex(COLUMN_SESSION_ID));
                int seance_id = cursor.getInt(cursor.getColumnIndex(COLUMN_SEANCE_ID));
                int exercise_number = cursor.getInt(cursor.getColumnIndex(COLUMN_EXERCISE_NUMBER));
                int repetition_number = cursor.getInt(cursor.getColumnIndex(COLUMN_REPETITIONS));
                String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
                String hour = cursor.getString(cursor.getColumnIndex(COLUMN_HOUR));
                String exo_name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_EXO));
                int repetitions = cursor.getInt(cursor.getColumnIndex(COLUMN_REPETITIONS));
                int series = cursor.getInt(cursor.getColumnIndex(COLUMN_SERIES));
                int time = cursor.getInt(cursor.getColumnIndex(COLUMN_TIME));
                int rest_time = cursor.getInt(cursor.getColumnIndex(COLUMN_REST_TIME));
                int recovery_time = cursor.getInt(cursor.getColumnIndex(COLUMN_RECOVERY_TIME));
                String difficulty = cursor.getString(cursor.getColumnIndex(COLUMN_DIFFICULTY));
                String status = cursor.getString(cursor.getColumnIndex(COLUMN_STATUS));

                ExercicesStats ExerciseObject = new ExercicesStats();
                ExerciseObject.setId(id);
                ExerciseObject.setSession_id(session_id);
                ExerciseObject.setSeance_id(seance_id);
                ExerciseObject.setExercise_number(exercise_number);
                ExerciseObject.setRepetition_number(repetition_number);
                ExerciseObject.setDate(date);
                ExerciseObject.setHour(hour);
                ExerciseObject.setName_exo(exo_name);
                ExerciseObject.setRepetitions(repetitions);
                ExerciseObject.setSeries(series);
                ExerciseObject.setTime(time);
                ExerciseObject.setRest_time(rest_time);
                ExerciseObject.setRecovery_time(recovery_time);
                ExerciseObject.setDifficulty(difficulty);
                ExerciseObject.setStatus(status);

                exercicesStats.add(ExerciseObject);

            }while(cursor.moveToNext());
        }
        return exercicesStats;
    }


    public void displayStatistics(){
        String strSql = "SELECT * FROM " + TABLE_STATISTICS;
        Cursor cursor = this.getWritableDatabase().rawQuery(strSql, null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                int id_session = cursor.getInt(cursor.getColumnIndex(COLUMN_SESSION_ID));
                int id_seance = cursor.getInt(cursor.getColumnIndex(COLUMN_SEANCE_ID));
                int exercise_number = cursor.getInt(cursor.getColumnIndex(COLUMN_EXERCISE_NUMBER));
                int repetition_number = cursor.getInt(cursor.getColumnIndex(COLUMN_REPETITION_NUMBER));
                String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
                String hour = cursor.getString(cursor.getColumnIndex(COLUMN_HOUR));
                String exo_name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_EXO));
                int repetitions = cursor.getInt(cursor.getColumnIndex(COLUMN_REPETITIONS));
                int series = cursor.getInt(cursor.getColumnIndex(COLUMN_SERIES));
                int charge = cursor.getInt(cursor.getColumnIndex(COLUMN_CHARGE));
                int time_pause = cursor.getInt(cursor.getColumnIndex(COLUMN_REST_TIME));
                int time_recup = cursor.getInt(cursor.getColumnIndex(COLUMN_RECOVERY_TIME));
                String difficulty= cursor.getString(cursor.getColumnIndex(COLUMN_DIFFICULTY));
                String status = cursor.getString(cursor.getColumnIndex(COLUMN_STATUS));

                System.out.println("id = " + id + ", id_session = " + id_session + ", id_seance = " + id_seance + ", exercise number = " + exercise_number + ", repetition number = " + repetition_number + ", date = " + date + ", hour = " + hour + ", exo name = " + exo_name + ", repetitions " + repetitions + ", series = " + series + ", charge = " + charge
                        + ", time pause = " + time_pause + ", time recup = " + time_recup + ", difficulty = " + difficulty + ", status = " + status);

            }while(cursor.moveToNext());
        }
    }



}
