package fr.thomas.applicationtodolistjava.workout.new_add_sport;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fr.thomas.applicationtodolistjava.AddPauseActivity;
import fr.thomas.applicationtodolistjava.AddRecuperationActivity;
import fr.thomas.applicationtodolistjava.R;
import fr.thomas.applicationtodolistjava.liste_sports.ActivitiesOfListSport;
import fr.thomas.applicationtodolistjava.liste_sports.ListeSportsDataBase;

public class newAddSportAdapter extends RecyclerView.Adapter<newAddSportAdapter.NewAddSportViewHolder>{


    private ArrayList<ActivitiesOfListSport> repetitionsList;
    private ListeSportsDataBase sportsDataBase;

    private Context context;

    public newAddSportAdapter(Context context, ArrayList<ActivitiesOfListSport> repetitionsList) {
        this.context = context;
        this.repetitionsList = repetitionsList;
    }

    @NonNull
    @Override
    public NewAddSportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.minimalist_add_sport_row, parent, false);
        return new NewAddSportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewAddSportViewHolder holder, int position) {
        ActivitiesOfListSport currentactivitieOfListSport = repetitionsList.get(position);
        holder.bind(currentactivitieOfListSport, position);
    }

    @Override
    public int getItemCount() {
        return repetitionsList.size();
    }

    public ActivitiesOfListSport getItem(int position) {
        return repetitionsList.get(position);
    }


    public class NewAddSportViewHolder extends RecyclerView.ViewHolder {


        TextView name_exo, muscles_travailles, edit_exercise, add_note, add_pause, add_recuperation, number_series_validate;
        ImageView background_parameters, parameters;

        public ListeSportsDataBase dataBaseSports;


        public NewAddSportViewHolder(@NonNull View itemView) {
            super(itemView);

            name_exo = itemView.findViewById(R.id.tv_name_exo);
            muscles_travailles = itemView.findViewById(R.id.tv_muscles_travailles);
            edit_exercise = itemView.findViewById(R.id.tv_parameters_edit_exercise);
            add_note = itemView.findViewById(R.id.tv_parameters_add_note);
            add_pause = itemView.findViewById(R.id.tv_parameters_add_pause);
            add_recuperation = itemView.findViewById(R.id.tv_parameters_add_recuperation);
            number_series_validate = itemView.findViewById(R.id.tv_total_number_series);

            background_parameters = itemView.findViewById(R.id.iv_background_parameters);
            parameters = itemView.findViewById(R.id.iv_parameters);

            dataBaseSports = new ListeSportsDataBase(context);

        }

        public void bind(ActivitiesOfListSport currentactivitieOfListSport, int position) {

            name_exo.setText(String.valueOf(currentactivitieOfListSport.getName()));
            muscles_travailles.setText(currentactivitieOfListSport.getIs_parametres_visible());

            number_series_validate.setText(dataBaseSports.getNumberRepetitionsOfExercise(currentactivitieOfListSport.getId()) + " séries entrées");

            if (currentactivitieOfListSport.getIs_parametres_visible().contains("true")){
                background_parameters.setVisibility(View.VISIBLE);
                edit_exercise.setVisibility(View.VISIBLE);
                add_note.setVisibility(View.VISIBLE);
                add_pause.setVisibility(View.VISIBLE);
                add_recuperation.setVisibility(View.VISIBLE);
            }else{
                background_parameters.setVisibility(View.GONE);
                background_parameters.setVisibility(View.GONE);
                edit_exercise.setVisibility(View.GONE);
                add_note.setVisibility(View.GONE);
                add_pause.setVisibility(View.GONE);
                add_recuperation.setVisibility(View.GONE);
            }

            parameters.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateParametersVisibility(position);
                }
            });

            edit_exercise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EditExercise.class);
                    intent.putExtra("id_exo", currentactivitieOfListSport.getId());
                    context.startActivity(intent);



                    if (context instanceof Activity) {
                        Activity activity = (Activity) context;
                        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                }
            });
            add_pause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AddPauseActivity.class);
                    intent.putExtra("id_exo", currentactivitieOfListSport.getId());
                    context.startActivity(intent);
                    if (context instanceof Activity) {
                        Activity activity = (Activity) context;
                        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                }
            });
            add_recuperation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AddRecuperationActivity.class);
                    intent.putExtra("id_exo", currentactivitieOfListSport.getId());
                    context.startActivity(intent);



                    if (context instanceof Activity) {
                        Activity activity = (Activity) context;
                        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                }
            });



        }

        void updateParametersVisibility(int position) {
            ActivitiesOfListSport activity = getItem(position);
            if (activity.getIs_parametres_visible().equals("true")) {
                activity.setIs_parametres_visible("false");
            } else {
                activity.setIs_parametres_visible("true");
            }
            notifyDataSetChanged();
        }





    }


}

