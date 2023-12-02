package fr.thomas.applicationtodolistjava;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import fr.thomas.applicationtodolistjava.workout.DataBaseStatistiques;


public class MainFragment extends Fragment {

    ////Citation

    private TextView citation;

    private ImageView developAllCitations;

    private DataBaseStatistiques dataBaseStatistiques;


    View view;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_main_fragment, container, false);


        ////Citation

        dataBaseStatistiques = new DataBaseStatistiques(getContext());


        dataBaseStatistiques.displayStatistics();




        ///AllCitations









        return view;
    }
}