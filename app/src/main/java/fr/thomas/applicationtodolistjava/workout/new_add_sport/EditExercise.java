package fr.thomas.applicationtodolistjava.workout.new_add_sport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import fr.thomas.applicationtodolistjava.EditExerciseInRepetitionAdapter;
import fr.thomas.applicationtodolistjava.R;
import fr.thomas.applicationtodolistjava.RepetitionsOfExercise;
import fr.thomas.applicationtodolistjava.liste_sports.ListeSportsDataBase;

public class EditExercise extends AppCompatActivity implements EditExerciseInRepetitionAdapter.AdapterCallback{

    ListeSportsDataBase dataBaseSport;
    private ArrayList<RepetitionsOfExercise> repetitionsOfExercises = new ArrayList<>();

    private RecyclerView recyclerView;
    private EditExerciseInRepetitionAdapter adapter;

    ImageView add_repetition, go_back;
    TextView name_exo;

    private ListView listOfRepetitions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_exercise);

        dataBaseSport = new ListeSportsDataBase(this);

        Intent intent = getIntent();
        int idExo = intent.getIntExtra("id_exo", 0);

        name_exo = findViewById(R.id.tv_name_exo);
        add_repetition = findViewById(R.id.iv_backround_add_repetition);
        go_back = findViewById(R.id.iv_go_back_from_exercises_list);

        name_exo.setText(dataBaseSport.getExerciseName(idExo));



        System.out.println("id exo" + idExo);

        name_exo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dataBaseSport.setExerciceName(name_exo.getText().toString(), idExo);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        add_repetition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dataBaseSport.addRepetitions(idExo);
                AcutaliseAdapter(idExo);
            }
        });
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), newAddSportActivity.class);
                startActivity(intent);
            }
        });

        AcutaliseAdapter(idExo);






    }


    @Override
    public void AcutaliseAdapter(int idExo) {
        // Initialisez votre RecyclerView
        recyclerView = findViewById(R.id.rv_repetitions);

        // Créez une instance de ListeSportsDatabase et appelez la fonction pour obtenir la liste des sports

        repetitionsOfExercises = dataBaseSport.getAllRepetitionsForExercise(idExo);

        // Créez une instance de votre adaptateur en lui passant la liste des sports
        adapter = new EditExerciseInRepetitionAdapter(getApplicationContext(), repetitionsOfExercises, this);

        // Configurez le LinearLayoutManager pour votre RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        // Associez l'adaptateur à votre RecyclerView
        recyclerView.setAdapter(adapter);
    }
}