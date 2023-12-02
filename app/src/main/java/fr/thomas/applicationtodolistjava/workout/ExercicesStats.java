package fr.thomas.applicationtodolistjava.workout;

import android.os.Parcel;
import android.os.Parcelable;

public class ExercicesStats implements Parcelable {


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSession_id() {
        return session_id;
    }

    public void setSession_id(int session_id) {
        this.session_id = session_id;
    }

    public int getSeance_id() {
        return seance_id;
    }

    public void setSeance_id(int seance_id) {
        this.seance_id = seance_id;
    }

    public int getExercise_number() {
        return exercise_number;
    }

    public void setExercise_number(int exercise_number) {
        this.exercise_number = exercise_number;
    }

    public int getRepetition_number() {
        return repetition_number;
    }

    public void setRepetition_number(int repetition_number) {
        this.repetition_number = repetition_number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getName_exo() {
        return name_exo;
    }

    public void setName_exo(String name_exo) {
        this.name_exo = name_exo;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getRest_time() {
        return rest_time;
    }

    public void setRest_time(int rest_time) {
        this.rest_time = rest_time;
    }

    public int getRecovery_time() {
        return recovery_time;
    }

    public void setRecovery_time(int recovery_time) {
        this.recovery_time = recovery_time;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private int id;
    private int session_id; //Permet de différencier quelle session, notamment si elles ont été faites depuis une même séance
    private int seance_id; //Correspond à la seance faite par l'utilisateur
    private int exercise_number;
    private int repetition_number;
    private String date;
    private String hour;
    private String name_exo;
    private int repetitions;
    private int series;
    private int time;
    private int rest_time;
    private int recovery_time;
    private String difficulty;
    private String status;

    public ExercicesStats(Parcel in) {
        id = in.readInt();
        session_id = in.readInt();
        seance_id = in.readInt();
        exercise_number = in.readInt();
        repetition_number = in.readInt();
        date = in.readString();
        hour = in.readString();
        name_exo = in.readString();
        repetitions = in.readInt();
        series = in.readInt();
        time = in.readInt();
        rest_time = in.readInt();
        recovery_time = in.readInt();
        difficulty = in.readString();
        status = in.readString();
    }

    public ExercicesStats(){

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(session_id);
        dest.writeInt(seance_id);
        dest.writeInt(exercise_number);
        dest.writeInt(repetition_number);
        dest.writeString(date);
        dest.writeString(hour);
        dest.writeString(name_exo);
        dest.writeInt(repetitions);
        dest.writeInt(series);
        dest.writeInt(time);
        dest.writeInt(rest_time);
        dest.writeInt(recovery_time);
        dest.writeString(difficulty);
        dest.writeString(status);
    }

    public static final Creator<ExercicesStats> CREATOR = new Creator<ExercicesStats>() {
        @Override
        public ExercicesStats createFromParcel(Parcel in) {
            return new ExercicesStats(in);
        }

        @Override
        public ExercicesStats[] newArray(int size) {
            return new ExercicesStats[size];
        }
    };


}
