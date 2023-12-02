package fr.thomas.applicationtodolistjava.workout;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import fr.thomas.applicationtodolistjava.R;
import fr.thomas.applicationtodolistjava.liste_sports.ListeSportsDataBase;


public class RepetitionsWorkoutFragment extends Fragment {

    View view;
    TextView name_workout, series, repetitions, charge, description;
    ImageView complete, fail;
    int id = 0, actual_series;

    String etat_exercice = "Exercise", etat;
    ListeSportsDataBase dataBaseSport;
    DataBaseStatistiques dataBaseStatistiques;

    private WorkoutActivity wActivity;


    //Définir l'activité pour pouvoir y efféctuer des actions
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        wActivity = (WorkoutActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.minimalist_dark_workout_exercise_reps_fragment, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getInt("id");
            actual_series = bundle.getInt("actual_series");
            etat = bundle.getString("etat");
        }

        dataBaseSport = new ListeSportsDataBase(getContext());
        dataBaseStatistiques = new DataBaseStatistiques(getContext());

        //For the visual

        getElementsById();

        System.out.println(dataBaseSport.getActiveExerciceId());
        System.out.println(actual_series);

        repetitions.setText(String.valueOf(dataBaseSport.getSingleRepetition(dataBaseSport.getActiveExerciceId(), actual_series)));
        charge.setText(String.valueOf(dataBaseSport.getSingleCharge(dataBaseSport.getActiveExerciceId(), actual_series)));
        series.setText(actual_series + "/" + dataBaseSport.getNumberRepetitionsWithIdExo(dataBaseSport.getActiveExerciceId()));

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wActivity.transitionToNextStep();
                dataBaseStatistiques.setLastRepetitions(dataBaseSport.getSingleRepetition(dataBaseSport.getActiveExerciceId(), actual_series));
                dataBaseStatistiques.setLastCharge(dataBaseSport.getSingleCharge(dataBaseSport.getActiveExerciceId(), actual_series));
            }
        });



        return view;
    }


    void getElementsById(){
        name_workout = view.findViewById(R.id.tv_workout_name_from_rep_session);
        series = view.findViewById(R.id.tv_series_from_rep_session);
        repetitions = view.findViewById(R.id.tv_repetitions_from_rep_session);
        charge = view.findViewById(R.id.tv_charge_from_rep_session);
        description = view.findViewById(R.id.tv_description_from_reps_session);
        complete = view.findViewById(R.id.iv_fail_complete_from_reps_session);
        fail = view.findViewById(R.id.iv_fail_button_from_reps_session);
    }



}