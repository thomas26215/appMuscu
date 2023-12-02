package fr.thomas.applicationtodolistjava.liste_sports;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import fr.thomas.applicationtodolistjava.R;

public class ActivitiesOfListSportAdapter extends ArrayAdapter<ActivitiesOfListSport> {
    public ArrayList<ActivitiesOfListSport> activitiesArrayList;
    public ListeSportsDataBase dataBaseSports;
    private AdapterCallback callback;
    ImageView mooveUp, mooveDown;
    ActivitiesOfListSport currentactivitieOfListSport;

    public interface AdapterCallback {
        void actualiser(ActivitiesOfListSport activity, int id);
        void actualiserWithouthChange(ActivitiesOfListSport activity, int id);
    }




    public ActivitiesOfListSportAdapter(Context context, ArrayList<ActivitiesOfListSport> activitieArrayList, AdapterCallback callback){
        super(context, 0, activitieArrayList);
        this.activitiesArrayList = activitieArrayList;
        dataBaseSports = new ListeSportsDataBase(context);
        this.callback = callback;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent){
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.minimalist_dark_affichage_activities_view_row, parent, false
            );
        }

        currentactivitieOfListSport = getItem(position);


        ImageView delElement = convertView.findViewById(R.id.iv_del_element);
        delElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBaseSports.deleteElementById(currentactivitieOfListSport.getId());
                callback.actualiser(currentactivitieOfListSport, currentactivitieOfListSport.getId_sport());
            }
        });

        mooveUp = convertView.findViewById(R.id.iv_moove_up);
        mooveUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveExerciseUp(currentactivitieOfListSport.getId(), currentactivitieOfListSport.getId_sport());
                callback.actualiser(currentactivitieOfListSport, currentactivitieOfListSport.getId_sport());
            }
        });
        mooveDown = convertView.findViewById(R.id.iv_moove_down);
        mooveDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveExerciseDown(currentactivitieOfListSport.getId(), currentactivitieOfListSport.getId_sport());
                callback.actualiser(currentactivitieOfListSport, currentactivitieOfListSport.getId_sport());
            }
        });

        if(currentactivitieOfListSport.isEditVisible().contains("visible")){
            mooveUp.setVisibility(View.VISIBLE);
            mooveDown.setVisibility(View.VISIBLE);
            delElement.setVisibility(View.VISIBLE);
        }
        if(currentactivitieOfListSport.isEditVisible().contains("hidden")){
            mooveUp.setVisibility(View.GONE);
            mooveDown.setVisibility(View.GONE);
            delElement.setVisibility(View.GONE);
        }



        return convertView;
    }

    void moveExerciseDown(int id, int id_sport){
        int next_id = dataBaseSports.getNextExerciseId(id, id_sport);
        System.out.println("first = " + id + " second = " + next_id);
        //dataBaseSports.inverserElements("CompleteWorkoutSessionDatabase", "id", id, next_id);
    }
    void moveExerciseUp(int id, int id_sport){
        int next_id = dataBaseSports.getLastId(id, id_sport);
        System.out.println("first = " + id + " second = " + next_id);
        dataBaseSports.inverserElements(id, next_id);
    }
    public void actualiser(){
        callback.actualiserWithouthChange(currentactivitieOfListSport, currentactivitieOfListSport.getId_sport());
    }




}
