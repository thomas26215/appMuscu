package fr.thomas.applicationtodolistjava.liste_sports;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class ListeSportsAdapter extends ArrayAdapter<ListeSports> {

    public ArrayList<ListeSports> listSportsArrayList;
    public ListeSportsDataBase databaseSports;

    public ListeSportsAdapter(Context context, ArrayList<ListeSports> listeSportsArrayList){
        super(context, 0, listeSportsArrayList);
        this.listSportsArrayList = listeSportsArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent){
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.listesports_list_view_row2, parent, false
            );
        }

        databaseSports = new ListeSportsDataBase(getContext());



        ListeSports currentListeSports = getItem(position);

        TextView trainingText = convertView.findViewById(R.id.training_case_training_text);
        if (currentListeSports != null){
            trainingText.setText(currentListeSports.getName());
        }

        TextView timeText = convertView.findViewById(R.id.training_case_time_text);
        if (currentListeSports != null){
            timeText.setText(convert_time_good(currentListeSports.getTime()));

        }

        TextView numberExercises = convertView.findViewById(R.id.training_case_number_exercises_text);




        ImageView background = convertView.findViewById(R.id.training_case_background_image);
        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AffichageActivitiesOfSportActivity.class);

                intent.putExtra("id", currentListeSports.getId());
                intent.putExtra("name", databaseSports.getSessionNameWithId(currentListeSports.getId()));

                //Démarrer l'activité avec l'intent modifié

                v.getContext().startActivity(intent);
                ((Activity)v.getContext()).overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                Log.i("coucou", "coucou");
            }
        });

        //ImageView trainingImage = convertView.findViewById(R.id.training_case_activity_image);
        //if (currentListeSports != null){
        //    String image = currentListeSports.getImage();;
        //    int ressource = getContext().getResources().getIdentifier(image, "drawable", getContext().getPackageName());

        //    trainingImage.setImageResource(ressource);
        //}












        return convertView;
    }

    private String convert_time_good(int time){
        int hours = 0;
        int minutes = 0;

        while(time > 60){
            time -= 60;
            hours += 1;
        }
        minutes = time;

        return hours + " h" + minutes + " min";
    }


}
