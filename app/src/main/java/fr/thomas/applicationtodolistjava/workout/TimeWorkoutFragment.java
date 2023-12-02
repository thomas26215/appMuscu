package fr.thomas.applicationtodolistjava.workout;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import fr.thomas.applicationtodolistjava.R;
import fr.thomas.applicationtodolistjava.liste_sports.ListeSportsDataBase;
import fr.thomas.applicationtodolistjava.workout.DataBaseStatistiques;
import fr.thomas.applicationtodolistjava.workout.WorkoutActivity;


public class TimeWorkoutFragment extends Fragment {

    View view;

    int id, actual_series;
    String etat;
    TextView name_workout, series, repetitions, time, description;
    ImageView complete, fail;
    int temp_time;

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

        view = inflater.inflate(R.layout.fragment_time_workout, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getInt("id");
            actual_series = bundle.getInt("actual_series");
            etat = bundle.getString("etat");
        }

        dataBaseSport = new ListeSportsDataBase(getContext());
        dataBaseStatistiques = new DataBaseStatistiques(getContext());

        getElementsById();
        afficherInformationsOnScreen();

        displayStatistics();


        temp_time = Integer.parseInt(dataBaseSport.getValueForCurrentExercise("time").toString());

        CountDownTimer timer = new CountDownTimer(temp_time * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                temp_time--;
                time.setText(convert_time(temp_time));
            }

            public void onFinish() {
                if (isVisible()) {
                    wActivity.transitionToNextStep();
                }
            }
        }.start();

        complete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                wActivity.transitionToNextStep();
            }
        });
        fail.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                wActivity.transitionToNextStep();
            }
        });

        return view;
    }
    void getElementsById(){
        name_workout = view.findViewById(R.id.tv_workout_name_from_rep_session);
        series = view.findViewById(R.id.tv_series_from_rep_session);
        repetitions = view.findViewById(R.id.tv_repetitions_from_rep_session);
        time = view.findViewById(R.id.tv_time_from_rep_session);
        description = view.findViewById(R.id.tv_description_from_reps_session);
        complete = view.findViewById(R.id.iv_fail_complete_from_reps_session);
        fail = view.findViewById(R.id.iv_fail_button_from_reps_session);
    }
    void afficherInformationsOnScreen(){
        name_workout.setText(dataBaseSport.getValueForCurrentExercise("name_exercise").toString());
        series.setText(convert_series_good(actual_series));
        repetitions.setText(dataBaseSport.getValueForCurrentExercise("number_repetitions").toString());
        time.setText(dataBaseSport.getValueForCurrentExercise("time").toString());
        description.setText(dataBaseSport.getValueForCurrentExercise("description").toString());
    }
    String convert_series_good(int number_series){
        return String.valueOf(number_series) + "/" + dataBaseSport.getValueForCurrentExercise("number_series").toString();
    }
    String convert_time(int time){
        int returned_time = 0;
        while(time>60){
            returned_time += 1;
            time -= 60;
        }
        return returned_time + ":" + time;
    }
    void displayStatistics(){
        dataBaseStatistiques.displayStatistics();
    }
}