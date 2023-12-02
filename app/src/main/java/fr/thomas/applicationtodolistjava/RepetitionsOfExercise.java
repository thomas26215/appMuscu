package fr.thomas.applicationtodolistjava;

import android.os.Parcel;
import android.os.Parcelable;

import fr.thomas.applicationtodolistjava.databinding.MinimalistDarkActivityAffichageActivitiesOfSportBinding;
import fr.thomas.applicationtodolistjava.liste_sports.ActivitiesOfListSport;

public class RepetitionsOfExercise implements Parcelable {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_exercise() {
        return id_exercise;
    }

    public void setId_exercise(int id_exercise) {
        this.id_exercise = id_exercise;
    }

    public int getCharge() {
        return charge;
    }

    public void setCharge(int charge) {
        this.charge = charge;
    }

    public int getNumber_repetitions() {
        return number_repetitions;
    }

    public void setNumber_repetitions(int number_repetitions) {
        this.number_repetitions = number_repetitions;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }



    int id;
    int id_exercise;
    int charge;
    int number_repetitions;
    int time;



    String etat;


    public RepetitionsOfExercise(Parcel in) {
        id = in.readInt();
        id_exercise = in.readInt();
        charge = in.readInt();
        number_repetitions = in.readInt();
        time = in.readInt();
        etat = in.readString();
    }

    public RepetitionsOfExercise(){

    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(id_exercise);
        dest.writeInt(charge);
        dest.writeInt(number_repetitions);
        dest.writeInt(time);
        dest.writeString(etat);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RepetitionsOfExercise> CREATOR = new Creator<RepetitionsOfExercise>() {
        @Override
        public RepetitionsOfExercise createFromParcel(Parcel in) {
            return new RepetitionsOfExercise(in);
        }

        @Override
        public RepetitionsOfExercise[] newArray(int size) {
            return new RepetitionsOfExercise[size];
        }
    };
}
