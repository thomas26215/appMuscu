package fr.thomas.applicationtodolistjava.liste_sports;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;

import fr.thomas.applicationtodolistjava.RepetitionsOfExercise;

public class ListeSportsDataBase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Liste_Sports.db";
    private static final int DATABASE_VERSION = 1;

    // Table Liste_Sports
    private static final String TABLE_LISTE_SPORTS = "Liste_Sports";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_IMAGE = "image";

    // Table Elements_Sport
    private static final String TABLE_NAME_EXOS = "CompleteWorkoutSessionDatabase";
    private static final String COLUMN_ID_SEANCE = "id_sport";
    private static final String COLUMN_NAME_EXO = "name_exercise";
    private static final String COLUMN_NUMBER_REPETITIONS = "number_repetitions";
    private static final String COLUMN_NUMBER_SERIES = "number_series";
    private static final String COLUMN_CHARGE = "charge";
    private static final String COLUMN_TYPE_EXO = "type_exo";
    private static final String COLUMN_PAUSE = "pause_series";
    private static final String COLUMN_RECUP = "pause_exercise";
    private static final String COLUMN_ETAT = "Etat";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_IMAGE_WORKOUT = "image_workout";
    /*
     * Pour Etat :
     * Non validé : Non validé si l'utilisateur ne l'a pas encore ajouté
     * En cours : L'utilisateur a appuyé sur ajouté mais il n'a pas encore totalement ajouté tous les exos
     * Validé : L'utilisateur a validé tous les exos
     * Exo à faire : L'utilisateur est en exo et n'a pas encore fait cet exo
     * Exo en cours : L'utilisateur est en train de faire cet exo
     * Exo fini : L'utilisateur a déja fait cet exo
     */

    // Table Sport_developer
    private static final String TABLE_SPORT_DEVELOPER = "Sport_developer";
    private static final String COLUMN_THING = "thing";
    private static final String COLUMN_VALUE = "value";

    private static final String TABLE_REPETITIONS = "RepetitionsDatabase";
    private static final String COLUMN_ID_EXO = "id_exo";
    private static final String IS_PARAMETERS_VISIBLE = "is_parameters_visible";
    private static final String COLUMN_EXERCISE_TYPE = "type_of_exercise";


    public ListeSportsDataBase(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String strSql = "CREATE TABLE " + TABLE_LISTE_SPORTS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_TIME + " INTEGER,"
                + COLUMN_IMAGE + " TEXT" + ")";
        db.execSQL(strSql);
        Log.i("Create database sports", "Database crée");

        strSql = "CREATE TABLE " + TABLE_NAME_EXOS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ID_SEANCE + " INTEGER, '" //Récupère l'id dans la base TABLE_LISTE_SPORTS
                + COLUMN_NAME_EXO + "' TEXT,"
                + COLUMN_EXERCISE_TYPE + " TEXT CHECK(" + COLUMN_EXERCISE_TYPE + " IN ('Repetition', 'Time', 'Echauffement', 'Etirement')), "
                + COLUMN_TIME + " INTEGER,"
                + COLUMN_TYPE_EXO + " TEXT,"
                + COLUMN_PAUSE + " INTEGER,"
                + COLUMN_RECUP + " INTEGER,"
                + COLUMN_ETAT + " TEXT CHECK(" + COLUMN_ETAT + " IN ('Non validé', 'En cours', 'Validé', 'Exo à faire', 'Exo en cours', 'Exo fini')), '"
                + COLUMN_DESCRIPTION + "' TEXT,"
                + IS_PARAMETERS_VISIBLE + " TEXT,"
                + COLUMN_IMAGE_WORKOUT + " TEXT" + ")";
        db.execSQL(strSql);
        Log.i("Create database sports", "Database2 crée");

        strSql = "CREATE TABLE " + TABLE_REPETITIONS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_ID_EXO + " INTEGER, "
                + COLUMN_ETAT + " TEXT CHECK(" + COLUMN_ETAT + " IN ('Non validé', 'Validé')), "
                + COLUMN_CHARGE + " INTEGER, "
                + COLUMN_NUMBER_REPETITIONS + " INTEGER, "
                + COLUMN_TIME + " INTEGER" + ")";
        db.execSQL(strSql);

        strSql = "CREATE TABLE " + TABLE_SPORT_DEVELOPER + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_THING + " TEXT,"
                + COLUMN_VALUE + " TEXT" + ")";
        db.execSQL(strSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //String strSql = "alter table Liste_Sports add column ...";
    }


    /*
     * Début Listes de sports
     */

    public ArrayList<ListeSports> getAllSportsLists(){
        ArrayList<ListeSports> ListSports = new ArrayList<>();
        String strSql = "SELECT * FROM Liste_Sports";
        Cursor cursor = this.getWritableDatabase().rawQuery(strSql, null);
        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                int time = cursor.getInt(cursor.getColumnIndex(COLUMN_TIME));
                String image = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE));

                ListeSports listeSportsObj = new ListeSports();
                listeSportsObj.setId(id);
                listeSportsObj.setName(name);
                listeSportsObj.setTime(time);
                listeSportsObj.setImage(image);
                ListSports.add(listeSportsObj);
                cursor.moveToNext();
            }
        }
        return ListSports;
    }

    public String getSessionNameWithId(int id){
        return getElementWithIdString(TABLE_LISTE_SPORTS, COLUMN_NAME, COLUMN_ID, id);
    }
    public int getSessionTimeWithId(int id){
        return getElementWithIdInt(TABLE_LISTE_SPORTS, COLUMN_TIME, COLUMN_ID, id);
    }
    public String getSessionImageWithId(int id){
        return getElementWithIdString(TABLE_LISTE_SPORTS, COLUMN_IMAGE, COLUMN_ID, id);
    }

    public int getLastSessionId(){
        return getLastElementInt(TABLE_LISTE_SPORTS, COLUMN_ID);
    }
    public String getLastSessionName(){
        return getLastElementString(TABLE_LISTE_SPORTS, COLUMN_NAME);
    }
    public int getLastSessionTime(){
        return getLastElementInt(TABLE_LISTE_SPORTS, COLUMN_TIME);
    }
    public String getLastSessionImage(){
        return getLastElementString(TABLE_LISTE_SPORTS, COLUMN_IMAGE);
    }

    public void setLastNameSport(String name) {
        setLastElement(TABLE_LISTE_SPORTS, COLUMN_NAME, name);
    }
    public void setLastSessionTimeSport(int time){
        setLastElement(TABLE_LISTE_SPORTS, COLUMN_TIME, time);
    }
    public void setLastSessionImage(String image){
        setLastElement(TABLE_LISTE_SPORTS, COLUMN_IMAGE, image);
    }

    public void addSession(){
        String strSql = "INSERT INTO Liste_Sports DEFAULT VALUES";
        this.getWritableDatabase().execSQL(strSql);
    }

    public void deleteSessionsWithoutExercises(){
        String strSql = "DELETE FROM " + TABLE_LISTE_SPORTS + " WHERE "
                + COLUMN_ID + " NOT IN (SELECT DISTINCT " + COLUMN_ID_SEANCE + " FROM " + TABLE_NAME_EXOS + ")";
        this.getWritableDatabase().execSQL(strSql);
    }

    public void deleteAllSports(){
        String strSql = "drop table " + TABLE_LISTE_SPORTS;
        this.getWritableDatabase().execSQL(strSql);
    }

    public void displayListesSportDataBase(){

        String strSql = "SELECT * FROM " + TABLE_LISTE_SPORTS;
        Cursor cursor = this.getWritableDatabase().rawQuery(strSql, null);

        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                int time = cursor.getInt(cursor.getColumnIndex(COLUMN_TIME));
                String image = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE));
                System.out.println("ID = " + id + ", Name = " + name + ", Time = " + time + ", Image = " + image);
            }while(cursor.moveToNext());
        }

    }

    /*
     * Fin Listes de sports
     */


    /*
     * Repetitions d'un exo
     */

    public ArrayList<RepetitionsOfExercise> getAllRepetitionsForExercise(int id_exo){
        ArrayList<RepetitionsOfExercise> repetitionsOfExercises = new ArrayList<>();
        String strSql = "SELECT * FROM " + TABLE_REPETITIONS + " WHERE " + COLUMN_ID_EXO + " = " + id_exo;
        Cursor cursor = this.getWritableDatabase().rawQuery(strSql, null);
        if(cursor.moveToFirst()){
            do{
                int id2 = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                int charge = cursor.getInt(cursor.getColumnIndex(COLUMN_CHARGE));
                int repetitions = cursor.getInt(cursor.getColumnIndex(COLUMN_NUMBER_REPETITIONS));
                int time = cursor.getInt(cursor.getColumnIndex(COLUMN_TIME));
                String etat = cursor.getString(cursor.getColumnIndex(COLUMN_ETAT));

                RepetitionsOfExercise RepetitionObject = new RepetitionsOfExercise();
                RepetitionObject.setId(id2);
                RepetitionObject.setId_exercise(id_exo);
                RepetitionObject.setCharge(charge);
                RepetitionObject.setNumber_repetitions(repetitions);
                RepetitionObject.setTime(time);
                RepetitionObject.setEtat(etat);
                repetitionsOfExercises.add(RepetitionObject);

            }while(cursor.moveToNext());

        }
        return repetitionsOfExercises;
    }

    public void setRepetitions(int id, int number_reps){
        setElementColumnById(TABLE_REPETITIONS, COLUMN_NUMBER_REPETITIONS, number_reps, id, COLUMN_ID);
    }
    public void setCharge(int id, int charge){
        setElementColumnById(TABLE_REPETITIONS, COLUMN_CHARGE, charge, id, COLUMN_ID);
    }
    public void setTime(int id, int time){
        setElementColumnById(TABLE_REPETITIONS, COLUMN_CHARGE, time, id, COLUMN_ID);
    }
    public void setSeriesEtat(int id, String etat){
        setElementColumnById(TABLE_REPETITIONS, COLUMN_ETAT, etat, id, COLUMN_ID);
    }

    public int getRepetitions(int id){
        return getElementWithIdInt(TABLE_REPETITIONS, COLUMN_NUMBER_REPETITIONS, COLUMN_ID, id);
    }
    public int getSingleRepetition(int id_exo, int actual_repetition){
        String strSql = "SELECT * FROM " + TABLE_REPETITIONS + " WHERE " + COLUMN_ID_EXO + " = " + id_exo;
        Cursor cursor = this.getWritableDatabase().rawQuery(strSql, null);
        int i = 1;
        if(cursor.moveToFirst()){
            do{
                if(i == actual_repetition) {
                    System.out.println("repetitions = " + cursor.getInt(cursor.getColumnIndex(COLUMN_NUMBER_REPETITIONS)));
                    return cursor.getInt(cursor.getColumnIndex(COLUMN_NUMBER_REPETITIONS));
                }
                ++ i;
            }
            while(cursor.moveToNext());



        }
        return 0;
    }
    public int getCharge(int id){
        return getElementWithIdInt(TABLE_REPETITIONS, COLUMN_CHARGE, COLUMN_ID, id);
    }
    public int getSingleCharge(int id_exo, int actual_repetition){
        String strSql = "SELECT * FROM " + TABLE_REPETITIONS + " WHERE " + COLUMN_ID_EXO + " = " + id_exo;
        Cursor cursor = this.getWritableDatabase().rawQuery(strSql, null);
        int i = 1;
        if(cursor.moveToFirst()){
            do{
                if(i == actual_repetition) {
                    return cursor.getInt(cursor.getColumnIndex(COLUMN_CHARGE));
                }
                ++ i;
            }
            while(cursor.moveToNext());

        }
        return 0;
    }
    public int getTime(int id){
        return getElementWithIdInt(TABLE_REPETITIONS, COLUMN_TIME, COLUMN_ID, id);
    }
    public int getSeriesEtat(int id){
        return getElementWithIdInt(TABLE_REPETITIONS, COLUMN_ETAT, COLUMN_ID, id);
    }
    public int getNumberRepetitionsWithIdExo(int id_exo){
        String strSql = "SELECT * FROM " + TABLE_REPETITIONS + " WHERE " + COLUMN_ID_EXO + " = " + id_exo;
        Cursor cursor = this.getWritableDatabase().rawQuery(strSql, null);
        int number_repetitions = 0;
        if(cursor.moveToFirst()){
            do{
                number_repetitions += 1;
            }while(cursor.moveToNext());
        }
        return number_repetitions;
    }

    public void addRepetitions(int id_exo){
        String strSql = "INSERT INTO " + TABLE_REPETITIONS + " (" + COLUMN_ID_EXO + ", " + COLUMN_ETAT + ", " + COLUMN_CHARGE + ", " + COLUMN_NUMBER_REPETITIONS + ", " + COLUMN_TIME + ") VALUES (" + id_exo + ", 'Non validé', 5, 5, 5)";
        this.getWritableDatabase().execSQL(strSql);
    }

    public void deleteRepetition(int id){
        String strSql = "DELETE FROM " + TABLE_REPETITIONS + " WHERE " + COLUMN_ID + " = " + id;
        this.getWritableDatabase().execSQL(strSql);
    }

    public void displayRepetitionsDataBase() {
        String strSql = "SELECT * FROM " + TABLE_REPETITIONS;
        Cursor cursor = this.getWritableDatabase().rawQuery(strSql, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            int id_exo = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_EXO));
            int charge = cursor.getInt(cursor.getColumnIndex(COLUMN_CHARGE));
            int repetitions = cursor.getInt(cursor.getColumnIndex(COLUMN_NUMBER_REPETITIONS));
            int time = cursor.getInt(cursor.getColumnIndex(COLUMN_TIME));


            System.out.println("id = " + id + ", id_exo = " + id_exo + ", charge = " + charge + ", repetitions = " + repetitions + ", time = " + time);
        }
        cursor.close();
    }

    //Déroulement d'un exo


    /*
     * Fin repetitions d'un exo
     */



    /*
     * Exercices d'un sport
     */

    // Méthodes pour mettre à jour les propriétés d'un exercice

    public void setExerciceName(String name_exo, int id){
        setElementColumnById(TABLE_NAME_EXOS, COLUMN_NAME_EXO, name_exo, id, COLUMN_ID);
    }
    public void setExerciseType(String type_exo, int id){
        setElementColumnById(TABLE_NAME_EXOS, COLUMN_NAME_EXO, type_exo, id, COLUMN_ID);
    }
    public void setExerciseTime(int time_exo, int id){
        setElementColumnById(TABLE_NAME_EXOS, COLUMN_NAME_EXO, time_exo, id, COLUMN_ID);
    }
    public void setExercisePause(int pause, int id){
        setElementColumnById(TABLE_NAME_EXOS, COLUMN_PAUSE, pause, id, COLUMN_ID);
    }
    public void setExerciseRecup(int recuperation, int id){
        setElementColumnById(TABLE_NAME_EXOS, COLUMN_RECUP, recuperation, id, COLUMN_ID);
    }
    public void setExerciseState(String etat, int id){
        setElementColumnById(TABLE_NAME_EXOS, COLUMN_ETAT, etat, id, COLUMN_ID);
    }
    public void setExerciseDescription(String description, int id){
        setElementColumnById(TABLE_NAME_EXOS, COLUMN_DESCRIPTION, description, id, COLUMN_ID);
    }
    public void setExerciseParametersVisible(String visibility, int id){
        setElementColumnById(TABLE_NAME_EXOS, IS_PARAMETERS_VISIBLE, visibility, id, COLUMN_ID);
    }
    public void setExerciseImageWorkout(String image_workout, int id){
        setElementColumnById(TABLE_NAME_EXOS, COLUMN_IMAGE_WORKOUT, image_workout, id, COLUMN_ID);
    }

    // Méthodes pour récupérer les propriétés d'un exercice

    public int getExerciseIdSession(int id){
        return getElementWithIdInt(TABLE_NAME_EXOS, COLUMN_ID_SEANCE, COLUMN_ID, id);
    }
    public String getExerciseName(int id){
        return getElementWithIdString(TABLE_NAME_EXOS, COLUMN_NAME_EXO, COLUMN_ID, id);
    }
    public String getExerciseType(int id) {
        return getElementWithIdString(TABLE_NAME_EXOS, COLUMN_EXERCISE_TYPE, COLUMN_ID, id);
    }
    public int getExerciseTime(int id) {
        return getElementWithIdInt(TABLE_NAME_EXOS, COLUMN_TIME, COLUMN_ID, id);
    }
    public int getExercisePause(int id) {
        return getElementWithIdInt(TABLE_NAME_EXOS, COLUMN_PAUSE, COLUMN_ID, id);
    }
    public int getExerciseRecup(int id) {
        return getElementWithIdInt(TABLE_NAME_EXOS, COLUMN_RECUP, COLUMN_ID, id);
    }
    public String getExerciseState(int id) {
        return getElementWithIdString(TABLE_NAME_EXOS, COLUMN_ETAT, COLUMN_ID, id);
    }
    public String getExerciseDescription(int id) {
        return getElementWithIdString(TABLE_NAME_EXOS, COLUMN_DESCRIPTION, COLUMN_ID, id);
    }
    public String getExerciseParametersVisible(int id) {
        return getElementWithIdString(TABLE_NAME_EXOS, IS_PARAMETERS_VISIBLE, COLUMN_ID, id);
    }
    public String getExerciseImageWorkout(int id) {
        return getElementWithIdString(TABLE_NAME_EXOS, COLUMN_IMAGE_WORKOUT, COLUMN_ID, id);
    }

    // Méthodes pour récupérer des informations spécifiques

    public int getActiveExerciceId(){
        TABLE_NAME_EXOS.replace("'", "''");
        String strSql = "SELECT * FROM " + TABLE_NAME_EXOS + " WHERE " + COLUMN_ETAT + " = 'Exo en cours'";
        Cursor cursor = this.getWritableDatabase().rawQuery(strSql, null);
        if(cursor.moveToFirst()){
            return cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
        }
        return 0;
    }

    public int getLastExerciseId(){
        return getLastElementInt(TABLE_NAME_EXOS, COLUMN_ID);
    }
    public String getLastExerciseState(){
        return getLastElementString(TABLE_NAME_EXOS, COLUMN_ETAT);
    }
    public int getExerciseMaximumId() {
        String query = "SELECT MAX(" + COLUMN_ID + ") FROM " + TABLE_NAME_EXOS;
        Cursor cursor = this.getWritableDatabase().rawQuery(query, null);
        if (cursor.moveToFirst()) {
            int id_maximum = cursor.getInt(0); // Utilisez l'index 0 pour obtenir la valeur de la colonne MAX(COLUMN_ID)
            cursor.close(); // Fermez le curseur après avoir terminé de l'utiliser
            return id_maximum + 1;
        }
        cursor.close(); // Assurez-vous de fermer le curseur même s'il n'y a pas de résultats
        return 1; // Si la table est vide, retournez 1 comme nouvel ID
    }

    //Méthodes pour gérer le déroulement des exercices

    public void startFirstExercise(){
        String strSql = "UPDATE " + TABLE_NAME_EXOS + " SET " + COLUMN_ETAT + " = 'Exo en cours' WHERE " + COLUMN_ID + " = (SELECT " + COLUMN_ID + " FROM " + TABLE_NAME_EXOS + " WHERE " + COLUMN_ETAT + " = 'Exo à faire' ORDER BY " + COLUMN_ID + " ASC LIMIT 1)";
        this.getWritableDatabase().execSQL(strSql);
        System.out.println("___________________________________________________________________________________________________" +
                "");
    }
    public void moveToNextExercise(){
        //Mettre à terminé le premier
        String strSql = "UPDATE " + TABLE_NAME_EXOS + " SET " + COLUMN_ETAT + " = 'Exo fini' WHERE " + COLUMN_ETAT + " = 'Exo en cours'";
        this.getWritableDatabase().execSQL(strSql);

        //Mettre en cours le suivant
        strSql = "UPDATE " + TABLE_NAME_EXOS + " SET " + COLUMN_ETAT + " = 'Exo en cours' WHERE " + COLUMN_ID + " = (SELECT " + COLUMN_ID + " FROM " + TABLE_NAME_EXOS + " WHERE " + COLUMN_ETAT + " = 'Exo à faire' ORDER BY " + COLUMN_ID + " ASC LIMIT 1) AND " + COLUMN_ETAT + " = 'Exo à faire'";
        this.getWritableDatabase().execSQL(strSql);
    }
    public void finalizeSessionCreation(){ //Confirme tous les exercices dont l'état est "en cours". Tous ceux qui sont en "non confirmés" seront supprimés
        TABLE_NAME_EXOS.replace("'", "''");
        String strSql = "UPDATE " + TABLE_NAME_EXOS + " SET " + COLUMN_ETAT + "= 'Validé' WHERE " + COLUMN_ETAT + " = 'En cours'";
        this.getWritableDatabase().execSQL(strSql);
        suppressExerciseNonValidate();
    }
    public void suppressExerciseNonValidate(){
        String strSql = "DELETE FROM " + TABLE_NAME_EXOS + " WHERE " + COLUMN_ETAT + " = 'Non validé'";
        this.getWritableDatabase().execSQL(strSql);
    }
    public void definitExercicesSession(int id){
        setElementColumnById(TABLE_NAME_EXOS, COLUMN_ETAT, "Exo à faire", id);
    }

    public void setAllEtatsToValidate(){
        renameAllColumnValues(TABLE_NAME_EXOS, COLUMN_ETAT);
    }
    public void renameAllColumnValues(String table, String colonne){
        String strSql = "UPDATE " + table + " SET " + colonne + " = 'Validé'";
        this.getWritableDatabase().execSQL(strSql);
    }

    //Méthodes pour la gestion des exercices

    public void addNewExercise(){
        TABLE_NAME_EXOS.replace("'", "''");
        String strSql = "INSERT INTO " + TABLE_NAME_EXOS + " (" + COLUMN_ETAT + ", " + COLUMN_NAME_EXO + ", " + COLUMN_EXERCISE_TYPE + ", " + COLUMN_TIME + ", " + COLUMN_RECUP + ") VALUES ('Non validé', 'EXERCISE', 'Repetition', 30, 90)";
        this.getWritableDatabase().execSQL(strSql);
        setLastSessionIdForLastExercise();
    }
    public void suppressUnconfirmedExercise(){
        String strSql = "DELETE FROM " + TABLE_NAME_EXOS + " WHERE " + COLUMN_ETAT + " = 'En cours'";
        this.getWritableDatabase().execSQL(strSql);
    }
    public ArrayList<ActivitiesOfListSport> getExercicesSeanceWithId(int id_sesson){
        ArrayList<ActivitiesOfListSport> activitiesOfListSports = new ArrayList<>();
        String strSql = "select * from " + TABLE_NAME_EXOS +" where id_sport=" + id_sesson;
        Cursor cursor = this.getWritableDatabase().rawQuery(strSql, null);
        if(cursor.moveToFirst()){
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                int id_sport = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_SEANCE));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_EXO));
                int pause_series = cursor.getInt(cursor.getColumnIndex(COLUMN_RECUP));
                int pause_exos = cursor.getInt(cursor.getColumnIndex(COLUMN_PAUSE));
                String etat = cursor.getString(cursor.getColumnIndex(COLUMN_ETAT));
                String image = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_WORKOUT));
                String type_exercise = cursor.getString(cursor.getColumnIndex(COLUMN_EXERCISE_TYPE));
                ActivitiesOfListSport activitiesOfListSportObject = new ActivitiesOfListSport();
                activitiesOfListSportObject.setId(id);
                activitiesOfListSportObject.setName(name);
                activitiesOfListSportObject.setId_sport(id_sport);
                activitiesOfListSportObject.setExercise_type(type_exercise);
                activitiesOfListSportObject.setPause_series(pause_series);
                activitiesOfListSportObject.setPause_exos(pause_exos);
                activitiesOfListSportObject.setEtat(etat);
                activitiesOfListSportObject.setImage(image);
                activitiesOfListSports.add(activitiesOfListSportObject);
            } while (cursor.moveToNext());
        }
        return activitiesOfListSports;
    }
    public void displayExercisesDataBase(){
        String strSql = "SELECT * FROM " + TABLE_NAME_EXOS;
        Cursor cursor = this.getWritableDatabase().rawQuery(strSql, null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                int idsport = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_SEANCE));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_EXO));
                String type_exo = cursor.getString(cursor.getColumnIndex(COLUMN_EXERCISE_TYPE));
                int time = cursor.getInt(cursor.getColumnIndex(COLUMN_TIME));
                String exo_type = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE_EXO));
                int pause_series = cursor.getInt(cursor.getColumnIndex(COLUMN_PAUSE));
                int pause_exercise = cursor.getInt(cursor.getColumnIndex(COLUMN_RECUP));
                String etat = cursor.getString(cursor.getColumnIndex(COLUMN_ETAT));
                String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
                String image_workout;
                System.out.println("ID = " + id + ", IDsport = " + idsport + ", time = " + time + ", type exo = " + exo_type + ", type d'exercice = " + type_exo + ", pause series = " + pause_series + ", pause exercice = "+ pause_exercise + ", etat = " + etat + ", description = " + description + " " + name);
            }while(cursor.moveToNext());
        }
    }
    public void inverserElements(int id1, int id2) {
        displayExercisesDataBase();
        mettreLigneAuPlusGrandIdPossible(id1);
        displayExercisesDataBase();
        editId(id2, id1);
        displayExercisesDataBase();
        editId(this.getExerciseMaximumId()-1, id2);
        displayExercisesDataBase();

    }
    public void mettreLigneAuPlusGrandIdPossible(int id) {

        SQLiteDatabase database = getWritableDatabase();
        int plusGrandId = getExerciseMaximumId();

        System.out.println(plusGrandId);

        String strSql = "UPDATE " + TABLE_NAME_EXOS + " SET " + COLUMN_ID + " = " + plusGrandId + " WHERE " + COLUMN_ID + " = " + id;
        this.getWritableDatabase().execSQL(strSql);

        database.close();
    }
    public void editId(int id, int new_id){
        String Strsql = "UPDATE " + TABLE_NAME_EXOS + " SET " + COLUMN_ID + " = " + new_id + " WHERE " + COLUMN_ID + " = " + id;
        this.getWritableDatabase().execSQL(Strsql);
    }

    //Méthodes pour la gestion des statistiques

    public int getNumberRepetitionsOfExercise(int id_exo) {
        String strSql = "SELECT COUNT(*) FROM " + TABLE_REPETITIONS + " WHERE " + COLUMN_ID_EXO + " = " + id_exo;
        Cursor cursor = null;
        int count = 0;

        try {
            cursor = this.getWritableDatabase().rawQuery(strSql, null);
            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
        } catch (Exception e) {
            // Handle any exceptions that may occur during query execution
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return count;
    }

    //Méthodes pour récupérer le prochain élément

    public int getNextExerciseId(int id, int id_sport) {
        String strSql = "SELECT " + COLUMN_ID + " FROM " + TABLE_NAME_EXOS + " WHERE " + COLUMN_ID_SEANCE + " = " + id_sport + " ORDER BY " + COLUMN_ID + " ASC";
        Cursor cursor = this.getWritableDatabase().rawQuery(strSql, null);

        int nextId = 0;

        if (cursor.moveToNext()) {
            int columnIndex = cursor.getColumnIndex(COLUMN_ID);
            while (!cursor.isLast()) {
                int currentId = cursor.getInt(columnIndex);
                cursor.moveToNext();

                if (currentId == id) {
                    nextId = cursor.getInt(columnIndex);
                    break;
                }
            }
        }

        cursor.close();
        return nextId;
    }
    public String getNextNameExo(){
        return getNextExercise(TABLE_NAME_EXOS, COLUMN_NAME_EXO);
    }

    //Méthodes pour mettre le dernier élément

    public void setLastName(String name){
        setLastElement(TABLE_NAME_EXOS, COLUMN_NAME_EXO, name);
    }
    public void setLastTypeExo(String type_exo){
        setLastElement(TABLE_NAME_EXOS, COLUMN_TYPE_EXO, type_exo);
    }
    public void setLastDescription(String description){
        setLastElement(TABLE_NAME_EXOS, COLUMN_DESCRIPTION, description);
    }
    public void setLastPause(String pause){
        setLastElement(TABLE_NAME_EXOS, COLUMN_PAUSE, pause);
    }
    public void setLastRecup(String recup){
        setLastElement(TABLE_NAME_EXOS, COLUMN_RECUP, recup);
    }

    public void setLastSessionIdForLastExercise(){ //Pour le dernier exercice crée
        //Récupérer le dernier id de la liste sport
        String strSql = "SELECT * FROM " + TABLE_LISTE_SPORTS;
        Cursor cursor = this.getWritableDatabase().rawQuery(strSql, null);
        if(cursor.moveToLast()){
            int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            //Le mettre au dernier exercice
            setLastElement(TABLE_NAME_EXOS, COLUMN_ID_SEANCE, id);
        }
    }

    //Méthodes pour récupérer le dernier élément

    public int getLastId(int id, int id_sport) {
        String strSql = "SELECT " + COLUMN_ID + " FROM " + TABLE_NAME_EXOS + " WHERE " + COLUMN_ID_SEANCE + " = " + id_sport + " ORDER BY " + COLUMN_ID + " ASC";
        Cursor cursor = this.getWritableDatabase().rawQuery(strSql, null);

        int nextId = 0;

        if (cursor.moveToLast()) {
            int columnIndex = cursor.getColumnIndex(COLUMN_ID);
            while (!cursor.isFirst()) {
                int currentId = cursor.getInt(columnIndex);
                cursor.moveToPrevious();

                if (currentId == id) {
                    nextId = cursor.getInt(columnIndex);
                    break;
                }
            }
        }

        cursor.close();
        return nextId;
    }

    //Check

    public Boolean checkIfExerciseInProgress(){
        String strSql = "SELECT COUNT(*) FROM " + TABLE_NAME_EXOS + " WHERE Etat = 'Exo en cours'";
        Cursor cursor = this.getWritableDatabase().rawQuery(strSql, null);
        int result = 1;
        if (cursor != null && cursor.moveToFirst()) {
            result = cursor.getInt(0);

        }
        System.out.println(result);
        return result == 1;
    }
    public Boolean isExerciseToDo(){
        String strSql = "SELECT * FROM " + TABLE_NAME_EXOS + " WHERE " + COLUMN_ETAT + " = 'Exo à faire'";
        Cursor cursor = this.getWritableDatabase().rawQuery(strSql, null);
        return cursor.moveToFirst() == true;
    }

    /*
     * Fin exercices d'un sport
     */




    public int getIntNextElement(String column){
        String strSql = "SELECT * FROM " + TABLE_NAME_EXOS + " WHERE " + COLUMN_ETAT + " = 'Exo à faire'";
        Cursor cursor = this.getWritableDatabase().rawQuery(strSql, null);
        if(cursor.moveToFirst()){
            return cursor.getInt(cursor.getColumnIndex(column));
        }
        return 1;
    }
    public String getStringNextElement(String column){
        String strSql = "SELECT * FROM " + TABLE_NAME_EXOS + " WHERE " + COLUMN_ETAT + " = 'Exo à faire'";
        Cursor cursor = this.getWritableDatabase().rawQuery(strSql, null);
        if(cursor.moveToFirst()){
            return cursor.getString(cursor.getColumnIndex(column));
        }
        return "erreur";
    }
    public Object getValueForCurrentExercise(String column_name){
        Object element = null;
        String strSql = "SELECT * FROM " + TABLE_NAME_EXOS + " WHERE " + COLUMN_ETAT + " = 'Exo en cours'";
        Cursor cursor = this.getWritableDatabase().rawQuery(strSql, null);
        if(cursor.moveToFirst()){
            int columnIndex = cursor.getColumnIndex(column_name);
            if (cursor.getType(columnIndex) == Cursor.FIELD_TYPE_STRING) {
                element = cursor.getString(columnIndex);
            } else if (cursor.getType(columnIndex) == Cursor.FIELD_TYPE_INTEGER) {
                element = cursor.getInt(columnIndex);
            }
        }
        return element;
    }
    public void setElementColumnById(String table, String colonne, String value, int id){
        String strSql = "UPDATE " + table + " SET " + colonne + "='" +  value + "' WHERE " + COLUMN_ID_SEANCE + " = " + id;
        this.getWritableDatabase().execSQL(strSql);
    }
    public void updateLastEtat(String etat){
        setLastElement(TABLE_NAME_EXOS, COLUMN_ETAT, etat);
    }
    public void editColumnWithId(String column, String updated_value, int id){
        String strSql = "UPDATE " + TABLE_NAME_EXOS + " SET " + column + " = '" + updated_value + "' WHERE " + COLUMN_ID + " = " + id;
        this.getWritableDatabase().execSQL(strSql);
    }
    public void deleteElementById(int id){
        String strSql = "DELETE FROM " + TABLE_NAME_EXOS + " WHERE " + COLUMN_ID + " = " + id;
        this.getWritableDatabase().execSQL(strSql);
    }


    public String getElementWithIdString(String table, String column, String column_id, int id){
        String strSql = "SELECT * FROM " + table + " WHERE " + column_id + " = " + id;
        Cursor cursor = this.getWritableDatabase().rawQuery(strSql, null);
        String element = null;
        if(cursor.moveToFirst()){
            element = cursor.getString(cursor.getColumnIndex(column));
        }
        cursor.close();
        return element;
    }
    public int getElementWithIdInt(String table, String column, String column_id, int id){
        String strSql = "SELECT * FROM " + table + " WHERE " + column_id + " = " + id;
        Cursor cursor = this.getWritableDatabase().rawQuery(strSql, null);
        int element = 0;
        if(cursor.moveToFirst()){
            element = cursor.getInt(cursor.getColumnIndex(column));
        }
        cursor.close();
        return element;
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
    public void setElementColumnById(String table, String colonne, int value, int id, String column_id){
        String strSql = "UPDATE " + table + " SET " + colonne + "=" + value + " WHERE " + column_id + " = " + id;
        this.getWritableDatabase().execSQL(strSql);
    }
    public void setElementColumnById(String table, String colonne, String value, int id, String colonne_id){
        String strSql = "UPDATE " + table + " SET " + colonne + "='" +  value + "' WHERE " + colonne_id + " = " + id;
        this.getWritableDatabase().execSQL(strSql);
    }
    public String getNextExercise(String table, String column_table){
        String strSql = "SELECT * FROM " + table + " WHERE " + COLUMN_ETAT + " = 'Exo à faire' OR " + COLUMN_ETAT + " = 'Exo en cours'";
        Cursor cursor = this.getWritableDatabase().rawQuery(strSql, null);
        if(cursor.moveToFirst()){
            while(cursor.moveToNext()){
                if(cursor.getString(cursor.getColumnIndex(COLUMN_ETAT)).contains("Exo en cours")){
                    cursor.moveToNext();
                    return cursor.getString(cursor.getColumnIndex(column_table));
                }
            }
        }
        return null;
    }





}
