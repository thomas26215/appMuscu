package fr.thomas.applicationtodolistjava.liste_sports;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;


import fr.thomas.applicationtodolistjava.workout.new_add_sport.newAddSportActivity;
import fr.thomas.applicationtodolistjava.workout.DataBaseStatistiques;
import fr.thomas.applicationtodolistjava.R;


public class ListeSportsFragment extends Fragment {

    private ListView listViewListeSports;
    private ArrayList<ListeSports> listOfSports = new ArrayList<>();
    private ArrayList<ListeSports> listOfSportsOriginal = new ArrayList<>();
    private ArrayList list = new ArrayList<>();

    private ListeSportsDataBase dataBaseSports;
    private DataBaseStatistiques statistiques;
    private ListeSportsAdapter listeSportsAdapter;


    EditText search_sport;

    String theme = "minimalist";





    ImageView add_sport;


    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.sport_list_fragment, container, false);


        ////ListView


        dataBaseSports = new ListeSportsDataBase(getContext());

        statistiques = new DataBaseStatistiques(getContext());
        statistiques.displayStatistics();


        dataBaseSports.suppressExerciseNonValidate();
        dataBaseSports.suppressUnconfirmedExercise();
        dataBaseSports.deleteSessionsWithoutExercises();

        //Se fait dans ce fragment car c'est essentielement ce fragment qui est utilisé pour afficher les liste de sports
        dataBaseSports.deleteSessionsWithoutExercises();

        listViewListeSports = (ListView)  view.findViewById(R.id.liste);
        listViewListeSports.setVerticalScrollBarEnabled(false);
        listViewListeSports.setDivider(null);
        listOfSports = dataBaseSports.getAllSportsLists();
        listOfSportsOriginal = dataBaseSports.getAllSportsLists();
        listeSportsAdapter = new ListeSportsAdapter(getContext(), listOfSports);
        listViewListeSports.setAdapter(listeSportsAdapter);









        add_sport = view.findViewById(R.id.add_liste_sport);
        search_sport = view.findViewById(R.id.sport_list_fragment_search_sport);



        add_sport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBaseSports.suppressExerciseNonValidate();
                dataBaseSports.suppressUnconfirmedExercise();
                dataBaseSports.addSession();

                Intent intent = new Intent(getActivity(), newAddSportActivity.class);
                startActivity(intent);
            }
        });







        search_sport.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                listOfSports= filterArrayListListeSports(s.toString());
                listeSportsAdapter = new ListeSportsAdapter(getContext(),  listOfSports);
                listViewListeSports.setAdapter(listeSportsAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return view;
    }

    private ArrayList<ListeSports> filterArrayListListeSports(String textFilter){
        ArrayList<ListeSports> arrayListListeSportsTemp = new ArrayList<>();

        if (textFilter != null) {
            for(int i = 0; i < listOfSportsOriginal.size(); i++){
                if(listOfSportsOriginal.get(i).getName().toUpperCase().contains(textFilter.toUpperCase())){
                    arrayListListeSportsTemp.add(listOfSportsOriginal.get(i));
                }
            }
        }
        else{
            arrayListListeSportsTemp = listOfSportsOriginal;
        }

        return arrayListListeSportsTemp;
    }


}