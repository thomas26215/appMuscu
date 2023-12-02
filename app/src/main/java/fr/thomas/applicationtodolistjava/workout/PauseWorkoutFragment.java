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


public class PauseWorkoutFragment extends Fragment {


    int id, time;
    String etat, adapted_time;
    TextView time_remaining;
    View view;

    private LinearLayout pointsLayout;
    private int totalTime = 60; // Temps total en secondes
    private int remainingTime = totalTime; // Temps restant en secondes
    private Handler handler;
    private Runnable runnable;
    ImageView complete_button, pass;

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

        // Récupérer l'ActionBar de l'activité parente
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        // Vérifier si l'ActionBar est null avant de définir l'icône de retour
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(null);
        }
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getInt("id");
            etat = bundle.getString("etat");
        }

        dataBaseSport = new ListeSportsDataBase(getContext());
        dataBaseStatistiques = new DataBaseStatistiques(getContext());


        dataBaseStatistiques.setLastRest(dataBaseSport.getActiveExerciceId());




        view = inflater.inflate(R.layout.minimalist_dark_workout_exercise_pause_fragment, container, false);
        time = 10;
        time_remaining = view.findViewById(R.id.tv_time_remaining);
        complete_button = view.findViewById(R.id.iv_complete_button_from_pause_workout);
        pass = view.findViewById(R.id.iv_background_skip);
        CountDownTimer timer = new CountDownTimer(time * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                time--;
                adapted_time = adaptSecondTimeInMinute(time);
                time_remaining.setText(adapted_time
                );
            }

            public void onFinish() {
                if (isVisible()) {
                    wActivity.transitionToNextStep();
                }
            }
        }.start();

        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wActivity.transitionToNextStep();
            }
        });


        return view;
    }

    void setRessenti(){
        if(etat == "succed"){

        }else if(etat == "fail"){

        }
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

        complete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wActivity.transitionToNextStep();
            }
        });
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
}