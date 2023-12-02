package fr.thomas.applicationtodolistjava;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class PreBuildsDataBase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Pre_Builds.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_PRE_BUILDS = "Pre_builds";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_VALUE = "valeurs";
    private static final String COLUMN_UTILISATION = "utilisation";
    /*
     * Utilisations :
     * 1 - pause
     * 2 - recuperation
     * 3 - echauffement
     * 4 - conseils
     */

    public PreBuildsDataBase(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String strSql = "CREATE TABLE " + TABLE_PRE_BUILDS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, '"
                + COLUMN_VALUE + "' TEXT, " // Ajout de la virgule après COLUMN_VALUE
                + COLUMN_UTILISATION + " TEXT)";
        db.execSQL(strSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public ArrayList<PreBuilds> getPausePreBuilds(){
        ArrayList<PreBuilds> preBuilds = new ArrayList<>();
        String strSql = "SELECT * FROM " + TABLE_PRE_BUILDS + " WHERE " + COLUMN_UTILISATION + " = 'pause' ORDER BY " + COLUMN_VALUE;
        Cursor cursor = this.getWritableDatabase().rawQuery(strSql, null);
        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String value = cursor.getString(cursor.getColumnIndex(COLUMN_VALUE));
                String utilisation = cursor.getString(cursor.getColumnIndex(COLUMN_UTILISATION));

                PreBuilds preBuildObj = new PreBuilds();
                preBuildObj.setId(id);
                preBuildObj.setValue(value);
                preBuildObj.setUtilisation(utilisation);
                preBuilds.add(preBuildObj);
                cursor.moveToNext();
            }
        }
        return preBuilds;
    }

    public void addPause(String value){
        ContentValues values = new ContentValues();
        values.put(COLUMN_VALUE, value.replace("'", "''"));
        values.put(COLUMN_UTILISATION, "pause");
        this.getWritableDatabase().insert("Pre_builds", null, values);
    }



    public ArrayList<PreBuilds> getRecuperationPreBuilds(){
        ArrayList<PreBuilds> preBuilds = new ArrayList<>();
        String strSql = "SELECT * FROM " + TABLE_PRE_BUILDS + " WHERE " + COLUMN_UTILISATION + " = 'recuperation' ORDER BY " + COLUMN_VALUE;
        Cursor cursor = this.getWritableDatabase().rawQuery(strSql, null);
        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String value = cursor.getString(cursor.getColumnIndex(COLUMN_VALUE));
                String utilisation = cursor.getString(cursor.getColumnIndex(COLUMN_UTILISATION));

                PreBuilds preBuildObj = new PreBuilds();
                preBuildObj.setId(id);
                preBuildObj.setValue(value);
                preBuildObj.setUtilisation(utilisation);
                preBuilds.add(preBuildObj);
                cursor.moveToNext();
            }
        }
        return preBuilds;
    }

    public void addRecuperation(String value){
        ContentValues values = new ContentValues();
        values.put(COLUMN_VALUE, value.replace("'", "''"));
        values.put(COLUMN_UTILISATION, "recuperation");
        this.getWritableDatabase().insert("Pre_builds", null, values);
    }



    public void setElementColumnById(String table, String colonne, String value, int id, String colonne_id){
        String strSql = "UPDATE " + table + " SET " + colonne + "=" + value + " WHERE " + colonne_id + "=" + id;
        this.getWritableDatabase().execSQL(strSql);
    }

}
