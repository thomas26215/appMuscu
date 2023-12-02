package fr.thomas.applicationtodolistjava.workout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import fr.thomas.applicationtodolistjava.R;
import fr.thomas.applicationtodolistjava.liste_sports.ListeSportsDataBase;

public class WorkoutActivity extends AppCompatActivity {

    private int actualSeries = 0;
    private int sessionId;
    private String exerciseState = "To begin";
    private ListeSportsDataBase dataBaseSport;
    private DataBaseStatistiques dataBaseStatistiques;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.minimalist_dark_activity_workout);

        dataBaseSport = new ListeSportsDataBase(this);
        dataBaseStatistiques = new DataBaseStatistiques(this);

        Intent intent = getIntent();
        sessionId = intent.getIntExtra("id_session", 0);
        actualSeries = intent.getIntExtra("actual_series", 0);

        transitionToNextStep();
    }



    private void replaceFragment(Fragment fragment, String lastFragmentState, int actualSeries) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", sessionId);
        bundle.putInt("actual_series", actualSeries);
        bundle.putString("etat", lastFragmentState);
        fragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.workoutFragment, fragment);
        fragmentTransaction.commit();
    }


    public void transitionToNextStep() {
        System.out.println("Affichage statistiques");
        dataBaseStatistiques.displayStatistics();
        if (exerciseState.contains("To begin")) {
            if(dataBaseSport.checkIfExerciseInProgress() == Boolean.FALSE){
                dataBaseSport.definitExercicesSession(sessionId);
                dataBaseSport.startFirstExercise();
                exerciseState = "Exercise";
            }
            dataBaseStatistiques.addNewSession(sessionId);
            actualSeries += 1;
            replaceFragment(new RepetitionsWorkoutFragment(), "", actualSeries);
        }
        else if(exerciseState.contains("Exercise")){
            if(actualSeries != dataBaseSport.getNumberRepetitionsOfExercise(dataBaseSport.getActiveExerciceId())){
                exerciseState = "Pause";
                replaceFragment(new PauseWorkoutFragment(), "", actualSeries);
            }else if(dataBaseSport.isExerciseToDo() == Boolean.FALSE){
                Intent intent = new Intent(WorkoutActivity.this, CompletionActivity.class);
                startActivity(intent);
            }else{
                exerciseState = "Recuperation";
                replaceFragment(new RecuperationWorkoutFragment(), "", actualSeries);
            }
            /*if(actualSeries == dataBaseSport.getNumberRepetitionsOfExercise(dataBaseSport.getActiveExerciceId())){
                exerciseState = "Recuperation";
                replaceFragment(new RecuperationWorkoutFragment(), "", actualSeries);
            }else if(dataBaseSport.isExerciseToDo() == Boolean.FALSE){
                Intent intent = new Intent(WorkoutActivity.this, CompletionActivity.class);
            }
            else{
                exerciseState = "Pause";
                replaceFragment(new PauseWorkoutFragment(), "", actualSeries);
            }*/
        }
        else if(exerciseState.contains("Pause")){
            actualSeries += 1;
            replaceFragment(new RepetitionsWorkoutFragment(), "", actualSeries);
            dataBaseStatistiques.addNewRepetition(sessionId);
            exerciseState = "Exercise";
        }
        else if(exerciseState.contains("Recuperation")){
            actualSeries = 1;
            dataBaseSport.moveToNextExercise();
            dataBaseStatistiques.addNewExercise(sessionId);
            replaceFragment(new RepetitionsWorkoutFragment(), "", actualSeries);
            exerciseState = "Exercise";
        }
    }







}