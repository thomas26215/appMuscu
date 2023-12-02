package fr.thomas.applicationtodolistjava.liste_sports;

import android.os.Parcel;
import android.os.Parcelable;

public class ListeSports implements Parcelable {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) { this.time = time; }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }







    private int id;
    private String name;
    private int time;
    private String image;







    public ListeSports(Parcel in){
        id = in.readInt();
        name = in.readString();
        time = in.readInt();
        image = in.readString();
    }

    public ListeSports(){

    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(time);
        dest.writeString(image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ListeSports> CREATOR = new Creator<ListeSports>() {
        @Override
        public ListeSports createFromParcel(Parcel in) {
            return new ListeSports(in);
        }

        @Override
        public ListeSports[] newArray(int size) {
            return new ListeSports[size];
        }
    };
}
