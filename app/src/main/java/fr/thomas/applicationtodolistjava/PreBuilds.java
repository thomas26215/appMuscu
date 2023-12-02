package fr.thomas.applicationtodolistjava;

import android.os.Parcel;
import android.os.Parcelable;

public class PreBuilds implements Parcelable {


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUtilisation() {
        return utilisation;
    }

    public void setUtilisation(String utilisation) {
        this.utilisation = utilisation;
    }

    int id;
    String value;
    String utilisation;






    public PreBuilds(Parcel in){
        id = in.readInt();
        value = in.readString();
        utilisation = in.readString();
    }

    public PreBuilds(){

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(value);
        dest.writeString(utilisation);
    }

    @Override
    public int describeContents() {
        return 0;
    }


    public static final Creator<PreBuilds> CREATOR = new Creator<PreBuilds>() {
        @Override
        public PreBuilds createFromParcel(Parcel in) {
            return new PreBuilds(in);
        }

        @Override
        public PreBuilds[] newArray(int size) {
            return new PreBuilds[size];
        }
    };




}

