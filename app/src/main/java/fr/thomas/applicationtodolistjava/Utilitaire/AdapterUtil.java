package fr.thomas.applicationtodolistjava.Utilitaire;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fr.thomas.applicationtodolistjava.liste_sports.ActivitiesOfListSport;
import fr.thomas.applicationtodolistjava.liste_sports.ListeSportsDataBase;
import fr.thomas.applicationtodolistjava.workout.new_add_sport.newAddSportAdapter;

public class AdapterUtil {
    public static void setOrActualiseAdapter(RecyclerView recyclerView, ListeSportsDataBase dataBaseSports){
        /*
         * Permettre à un recyclerView de s'actualiser
         * Attention ! La liste va se remettre à 0 et va se servir des éléments de la base de données
         */

        // Créez une instance de ListeSportsDatabase et appelez la fonction pour obtenir la liste des sports
        ArrayList<ActivitiesOfListSport> listOfActivities = dataBaseSports.getExercicesSeanceWithId(dataBaseSports.getLastSessionId());

        // Créez une instance de votre adaptateur en lui passant la liste des sports
        newAddSportAdapter adapter = new newAddSportAdapter(recyclerView.getContext(), listOfActivities);

        // Configurez le LinearLayoutManager pour votre RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);

        // Associez l'adaptateur à votre RecyclerView
        recyclerView.setAdapter(adapter);

    }
}
