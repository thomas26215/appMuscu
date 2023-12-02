package fr.thomas.applicationtodolistjava.workout;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import fr.thomas.applicationtodolistjava.R;
import fr.thomas.applicationtodolistjava.liste_sports.ListeSportsDataBase;

public class RecuperationWorkoutFragment extends Fragment {

    View view;
    ListeSportsDataBase dataBaseSport;

    int id, time, totalTime, remainingTime;

    private TextView time_remaining, next_exercise, next_charge;
    private ImageView complete_button;
    private LinearLayout pointsLayout;

    private Handler handler;
    private Runnable runnable;

    String theme = "minimalist";

    DataBaseStatistiques dataBaseStatistiques;

    View spinner_easy, spinner_medium, spinner_hard, spinner_head;
    TextView spinner_easy_tv, spinner_medium_tv, spinner_hard_tv, spinner_difficulty_head;
    ImageView background_spinner;
    Boolean isSpinnerDeployed = Boolean.FALSE;



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

        // Initialisation des bases de données
        dataBaseSport = new ListeSportsDataBase(getContext());
        dataBaseStatistiques = new DataBaseStatistiques(getContext());

        // Récupération de l'ActionBar de l'activité parente et suppression de l'icône de retour
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(null);
        }

        // Récupération de l'id de l'exercice
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getInt("id");
        }

        // Récupération du layout correspondant au thème
        if(theme == "normal"){
            view = inflater.inflate(R.layout.fragment_recuperation_workout, container, false);
        } else if(theme == "minimalist"){
            view = inflater.inflate(R.layout.minimalist_dark_fragment_recuperation_workout, container, false);
        }

        // Affichage du timer de récupération
        setRecoveryTimer();

        // Gestion des éléments spinner
        setSpinnerElements();

        //Next exercise
        setNextExerciseElements();
        //displayNextExercise();

        // Bouton pour marquer la fin de la pause
        complete_button = view.findViewById(R.id.iv_complete_button_from_pause_workout);
        complete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wActivity.transitionToNextStep();
            }
        });

        return view;
    }


    /**
     * Affichage du timer de récupération en fonction du thème
     */
    private void setRecoveryTimer() {
        time = dataBaseSport.getExerciseTime(dataBaseSport.getActiveExerciceId());
        time_remaining = view.findViewById(R.id.tv_time_remaining);
        CountDownTimer timer = new CountDownTimer(time * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                time--;
                if(theme == "normal"){
                    time_remaining.setText(Integer.toString(time));
                } else if(theme == "minimalist"){
                    String adapted_time = adaptSecondTimeInMinute(time);
                    time_remaining.setText(adapted_time);
                }
            }

            public void onFinish() {
                if (isVisible()) {
                    wActivity.transitionToNextStep();
                }
            }
        }.start();

        // Animation des points chaque seconde pour le thème normal
        if(theme == "normal"){
            totalTime = time;
            remainingTime = totalTime;
            playAnimationTimeRemaining();
        }
    }

    /**
     * Affichage des éléments spinner pour le thème minimalist
     */
    private void setSpinnerElements(){
        if(theme == "minimalist"){

            //Corps
            spinner_easy = view.findViewById(R.id.view_spinner_corps_easy);
            spinner_medium = view.findViewById(R.id.view_spinner_corps_moyen);
            spinner_hard = view.findViewById(R.id.view_spinner_corps_hard);
            spinner_easy_tv = view.findViewById(R.id.textview_spinner_corps_easy);
            spinner_medium_tv = view.findViewById(R.id.textview_spinner_corps_moyen);
            spinner_hard_tv = view.findViewById(R.id.textview_spinner_corps_hard);
            background_spinner = view.findViewById(R.id.iv_spinner_corps);
            spinner_head = view.findViewById(R.id.view_spinner_head_fold_unfold);
            spinner_difficulty_head = view.findViewById(R.id.view_spinner_difficulty_head);

            spinner_easy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    setDifficultyOnSpinnerHead("facile");
                    foldSpinner();
                    isSpinnerDeployed = Boolean.FALSE;
                }
            });
            spinner_medium.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    setDifficultyOnSpinnerHead("moyen");
                    foldSpinner();
                    isSpinnerDeployed = Boolean.FALSE;
                }
            });
            spinner_hard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    setDifficultyOnSpinnerHead("difficile");
                    foldSpinner();
                    isSpinnerDeployed = Boolean.FALSE;
                }
            });
            spinner_head.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isSpinnerDeployed == Boolean.TRUE){
                        foldSpinner();
                        isSpinnerDeployed = Boolean.FALSE;
                    }else{
                        unfoldSpinner();
                        isSpinnerDeployed = Boolean.TRUE;
                    }
                }
            });
        }
    }
    void unfoldSpinner(){
        background_spinner.setVisibility(View.VISIBLE);
        spinner_easy.setVisibility(View.VISIBLE);
        spinner_medium.setVisibility(View.VISIBLE);
        spinner_hard.setVisibility(View.VISIBLE);
        spinner_easy_tv.setVisibility(View.VISIBLE);
        spinner_medium_tv.setVisibility(View.VISIBLE);
        spinner_hard_tv.setVisibility(View.VISIBLE);
    }
    void foldSpinner(){
        background_spinner.setVisibility(View.INVISIBLE);
        spinner_easy.setVisibility(View.INVISIBLE);
        spinner_medium.setVisibility(View.INVISIBLE);
        spinner_hard.setVisibility(View.INVISIBLE);
        spinner_easy_tv.setVisibility(View.INVISIBLE);
        spinner_medium_tv.setVisibility(View.INVISIBLE);
        spinner_hard_tv.setVisibility(View.INVISIBLE);
    }
    void setDifficultyOnSpinnerHead(String difficulty){
        spinner_difficulty_head.setText(difficulty);
    }






    String adaptSecondTimeInMinute(int time){
        int minutes = 0, secondes = 0;
        while(time > 60){
            minutes += 1;
            time -= 60;
        }
        secondes = time;
        return minutes + ":" + secondes;
    }


    ////Pour l'animation des points
    void playAnimationTimeRemaining(){
        handler = new Handler();
        pointsLayout = view.findViewById(R.id.points_layout);
        createPoints(totalTime);
        runnable = new Runnable() {
            @Override
            public void run() {
                if (remainingTime > 0) {
                    activatePoint(totalTime - remainingTime);
                    remainingTime--;
                    handler.postDelayed(this, 1000);
                } else {
                    // Temps écoulé
                }
            }
        };
        handler.postDelayed(runnable, 1000);


    }
    // Méthode pour créer les points initiaux
    private void createPoints(int totalTime) {
        int pointSize;
        if (totalTime <= 10) {
            pointSize = 36;
        } else if (totalTime <= 20) {
            pointSize = 24;
        } else {
            pointSize = 18;
        }

        for (int i = 0; i < totalTime; i++) {
            TextView point = new TextView(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.weight = 1;
            point.setLayoutParams(params);
            point.setText("•");
            point.setTextSize(pointSize);
            point.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            point.setGravity(Gravity.CENTER);
            pointsLayout.addView(point);
        }
    }
    // Méthode pour activer un point
    private void activatePoint(int index) {
        TextView point = (TextView) pointsLayout.getChildAt(index);
        point.setTextColor(ContextCompat.getColor(getContext(), R.color.orange_corail));
        point.animate()
                .scaleX(1.5f)
                .scaleY(1.5f)
                .setDuration(500)
                .withEndAction(() -> {
                    point.animate()
                            .scaleX(1f)
                            .scaleY(1f)
                            .setDuration(500)
                            .start();
                })
                .start();
    }
    // Méthode pour activer l'effet de shadow
    private void activateShadow(View view) {
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(),
                ContextCompat.getColor(getContext(), R.color.black),
                ContextCompat.getColor(getContext(), R.color.orange_corail));
        colorAnimation.setDuration(500);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                view.setBackgroundColor((int) animator.getAnimatedValue());
            }
        });
        colorAnimation.start();
    }
    ////Fin pour l'animation des points

    private void setNextExerciseElements(){
        next_exercise = view.findViewById(R.id.tv_next_exercise);
        next_charge = view.findViewById(R.id.tv_next_charge);
    }
    /*private void displayNextExercise(){
        next_exercise.setText(dataBaseSport.getStringNextElement("name_exercise"));
        next_charge.setText(Integer.toString(dataBaseSport.getIntNextElement("charge")));
    }*/
}