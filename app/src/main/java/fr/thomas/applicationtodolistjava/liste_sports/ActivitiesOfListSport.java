package fr.thomas.applicationtodolistjava.liste_sports;

import android.os.Parcel;
import android.os.Parcelable;

public class ActivitiesOfListSport implements Parcelable {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_sport() {
        return id_sport;
    }

    public void setId_sport(int id_sport) {
        this.id_sport = id_sport;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPause_series() {
        return pause_series;
    }

    public void setPause_series(int pause_series) {
        this.pause_series = pause_series;
    }

    public int getPause_exos() {
        return pause_exos;
    }

    public void setPause_exos(int pause_exos) {
        this.pause_exos = pause_exos;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String isEditVisible(){
        return is_edit_visible;
    }

    public void setIsEditVisible(String visibility){
        is_edit_visible = visibility;
    }

    public String getIs_parametres_visible() {
        return is_parametres_visible;
    }

    public void setIs_parametres_visible(String is_parametres_visible) {
        this.is_parametres_visible = is_parametres_visible;
    }

    public String getIs_edit_visible() {
        return is_edit_visible;
    }

    public void setIs_edit_visible(String is_edit_visible) {
        this.is_edit_visible = is_edit_visible;
    }

    public String getExercise_type() {
        return exercise_type;
    }

    public void setExercise_type(String exercise_type) {
        this.exercise_type = exercise_type;
    }

    private int id;
    private int id_sport;
    private String name;
    private String exercise_type;
    private int pause_series; //Pause entre deux séries
    private int pause_exos; //Pause entre deux exos
    private String etat;
    private String image;
    private String is_parametres_visible = "false";
    private String is_edit_visible = "hidden";

    public ActivitiesOfListSport(Parcel in) {
        id = in.readInt();
        id_sport = in.readInt();
        name = in.readString();
        exercise_type = in.readString();
        pause_series = in.readInt();
        pause_exos = in.readInt();
        etat = in.readString();
        image = in.readString();
        is_edit_visible = in.readString();
    }

    public ActivitiesOfListSport(){

    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(id_sport);
        dest.writeString(name);
        dest.writeString(exercise_type);
        dest.writeInt(pause_series);
        dest.writeInt(pause_exos);
        dest.writeString(etat);
        dest.writeString(image);
        dest.writeString(is_edit_visible);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ActivitiesOfListSport> CREATOR = new Creator<ActivitiesOfListSport>() {
        @Override
        public ActivitiesOfListSport createFromParcel(Parcel in) {
            return new ActivitiesOfListSport(in);
        }

        @Override
        public ActivitiesOfListSport[] newArray(int size) {
            return new ActivitiesOfListSport[size];
        }
    };

}
