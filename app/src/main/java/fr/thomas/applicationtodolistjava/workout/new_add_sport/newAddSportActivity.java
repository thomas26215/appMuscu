package fr.thomas.applicationtodolistjava.workout.new_add_sport;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fr.thomas.applicationtodolistjava.MainActivity;
import fr.thomas.applicationtodolistjava.R;
import fr.thomas.applicationtodolistjava.Utilitaire.AdapterUtil;
import fr.thomas.applicationtodolistjava.liste_sports.ActivitiesOfListSport;
import fr.thomas.applicationtodolistjava.liste_sports.ListeSportsDataBase;
import fr.thomas.applicationtodolistjava.workout.DataBaseStatistiques;

/*
 * Cette page sert à afficher la page pour créer les sports
 * Sur l'activité, on trouve les deux bouttons du bas, pour ajouter un exo et pour valider la séance
 * On y trouve aussi le bouton pour revenir en arrière - pour revenir à la page principale
 * Au centre on y trouve un fragment qui représente la listview des sports en train d'être créer ainsi que la possibilité de modifier chaque éléments
 * Il y a aussi la possibilité de modifier le nom de l'exercice et le nom de la note d'entraînement
 */
public class newAddSportActivity extends AppCompatActivity{
    DataBaseStatistiques dataBaseStatistiques;
    private ArrayList<ActivitiesOfListSport> listOfActivities = new ArrayList<>();private RecyclerView recyclerView;
    private newAddSportAdapter adapter;
    ImageView buttonAddExo, add_session, go_back;
    EditText name_session;

    ListeSportsDataBase dataBaseSports = new ListeSportsDataBase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.minimalist_fragment_add_sport);
        setContentView(R.layout.minimalist_fragment_add_sport);

        dataBaseStatistiques = new DataBaseStatistiques(this);
        dataBaseSports = new ListeSportsDataBase(this);

        buttonAddExo = findViewById(R.id.iv_back_add_exo);
        add_session = findViewById(R.id.iv_confirm_session);
        go_back = findViewById(R.id.iv_go_back_from_exrcises_list);

        name_session = findViewById(R.id.et_session_name);
        dataBaseSports.setLastNameSport("Session");

        recyclerView = findViewById(R.id.lv_list_exos);

        buttonAddExo.setOnClickListener(new View.OnClickListener() {
            /*
             * Ajouter une nouvel exercice
             * Actualiser l'adapteur pour que le nouvel exercice apparaîsse
             */
            @Override
            public void onClick(View v) {
                dataBaseSports.addNewExercise();
                AdapterUtil.setOrActualiseAdapter(recyclerView, dataBaseSports);
            }
        });
        add_session.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dataBaseSports.setAllEtatsToValidate();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        name_session.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dataBaseSports.setLastNameSport(name_session.getText().toString());
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        AdapterUtil.setOrActualiseAdapter(recyclerView, dataBaseSports);



    }










}
