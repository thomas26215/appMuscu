package fr.thomas.applicationtodolistjava.workout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;



import org.w3c.dom.Text;

import java.util.ArrayList;

import fr.thomas.applicationtodolistjava.R;

public class CompletionActivity extends AppCompatActivity {


    DataBaseStatistiques dataBaseStatistiques;

    private ListView listView;

    TextView dateHour, session_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completion2);

        dataBaseStatistiques = new DataBaseStatistiques(this);




        dateHour = findViewById(R.id.tv_date_and_date);
        //session_name.findViewById(R.id.tv_session_name);




        /*
         * Afficher la liste de statistiques de l'entraînement
         * Utilise database DataBaseStatistiques
         * + adapteur personnalisé
         */

        listView = findViewById(R.id.listview2);
        listView.setVerticalScrollBarEnabled(false);
        listView.setDivider(null);
        //statistiquesAdapter = new StatistiquesForEndExercisesAdapter(this, stats);
        //listView.setAdapter(statistiquesAdapter);


    }



}