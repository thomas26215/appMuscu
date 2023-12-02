package fr.thomas.applicationtodolistjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import fr.thomas.applicationtodolistjava.liste_sports.ListeSports;
import fr.thomas.applicationtodolistjava.liste_sports.ListeSportsAdapter;
import fr.thomas.applicationtodolistjava.liste_sports.ListeSportsDataBase;
import fr.thomas.applicationtodolistjava.liste_sports.ListeSportsFragment;

public class MainActivity extends AppCompatActivity {

    private ListView listViewListeSports;
    private ArrayList<ListeSports> listOfSports = new ArrayList<>();
    private ArrayList list = new ArrayList<>();


    private ListeSportsAdapter listeSportsAdapter;

    ///private CitationsDataBase citationDataBase;
    ///TextView citation;

    Button firstFragmentBtn, secondFragmentBtn;

    private int selected_tab_in_navigation_bar = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Set the activity to full screen
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);


        super.onCreate(savedInstanceState);

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder(StrictMode.getVmPolicy())
                .detectLeakedClosableObjects()
                .build());

        setContentView(R.layout.activity_main);

        //// Implémentation des fragments



        replaceFragment(new MainFragment());






        //// Barre de navigation

        //Récupération des éléments

        final LinearLayout backlayout = findViewById(R.id.navigation_background);
        final LinearLayout homeLayout = findViewById(R.id.navigation_bar_home_layout);
        final LinearLayout trainingLayout = findViewById(R.id.navigation_bar_training_layout);
        final LinearLayout statisticsLayout = findViewById(R.id.navigation_bar_statistics_layout);
        final LinearLayout parametersLayout = findViewById(R.id.navigation_bar_parameters_layout);


        final ImageView homeImage = findViewById(R.id.navigation_bar_home_image);
        final ImageView trainingImage = findViewById(R.id.navigation_bar_training_image);
        final ImageView statisticsImage = findViewById(R.id.navigation_bar_statistics_image);
        final ImageView parametersImage = findViewById(R.id.navigation_bar_parameters_image);

        final TextView homeText = findViewById(R.id.navigation_bar_home_text);
        final TextView trainingText = findViewById(R.id.navigation_bar_training_text);
        final TextView statisticsText = findViewById(R.id.navigation_bar_statistics_text);
        final TextView parametersText = findViewById(R.id.navigation_bar_parameters_text);


        trainingLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        statisticsLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        parametersLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selected_tab_in_navigation_bar != 1){
                    trainingText.setVisibility(View.GONE);
                    statisticsText.setVisibility(View.GONE);
                    parametersText.setVisibility(View.GONE);

                    trainingImage.setImageResource(R.drawable.icon_training);
                    statisticsImage.setImageResource(R.drawable.icon_statistics);
                    parametersImage.setImageResource(R.drawable.icon_parameters);

                    trainingLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    statisticsLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    parametersLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    homeText.setVisibility(View.VISIBLE);
                    homeImage.setImageResource(R.drawable.navigation_bar_home);
                    homeLayout.setBackgroundResource(R.drawable.corner_rounds);

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    homeLayout.startAnimation(scaleAnimation);

                    selected_tab_in_navigation_bar = 1;

                    replaceFragment(new MainFragment());



                }
            }
        });
        trainingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selected_tab_in_navigation_bar != 2){
                    homeText.setVisibility(View.GONE);
                    statisticsText.setVisibility(View.GONE);
                    parametersText.setVisibility(View.GONE);

                    homeImage.setImageResource(R.drawable.navigation_bar_home);
                    statisticsImage.setImageResource(R.drawable.icon_statistics);
                    //parametersImage.setImageResource(R.drawable.icon_parameters);

                    homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    statisticsLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    parametersLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    trainingText.setVisibility(View.VISIBLE);
                    trainingImage.setImageResource(R.drawable.icon_training);
                    trainingLayout.setBackgroundResource(R.drawable.corner_rounds);

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    trainingLayout.startAnimation(scaleAnimation);

                    selected_tab_in_navigation_bar = 2;

                    replaceFragment(new ListeSportsFragment());



                }
            }
        });
        statisticsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selected_tab_in_navigation_bar != 3){
                    homeText.setVisibility(View.GONE);
                    trainingText.setVisibility(View.GONE);
                    parametersText.setVisibility(View.GONE);

                    homeImage.setImageResource(R.drawable.navigation_bar_home);
                    trainingImage.setImageResource(R.drawable.icon_training);
                    //parametersImage.setImageResource(R.drawable.icon_parameters);

                    homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    trainingLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    parametersLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    statisticsText.setVisibility(View.VISIBLE);
                    statisticsImage.setImageResource(R.drawable.icon_statistics);
                    statisticsLayout.setBackgroundResource(R.drawable.corner_rounds);

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    statisticsLayout.startAnimation(scaleAnimation);

                    selected_tab_in_navigation_bar = 3;




                }

            }
        });
        parametersLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selected_tab_in_navigation_bar != 4) {
                    homeText.setVisibility(View.GONE);
                    trainingText.setVisibility(View.GONE);
                    statisticsText.setVisibility(View.GONE);

                    homeImage.setImageResource(R.drawable.navigation_bar_home);
                    trainingImage.setImageResource(R.drawable.icon_training);
                    statisticsImage.setImageResource(R.drawable.icon_statistics);

                    homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    trainingLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    statisticsLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    parametersText.setVisibility(View.VISIBLE);
                    //parametersImage.setImageResource(R.drawable.icon_parameters);
                    parametersLayout.setBackgroundResource(R.drawable.corner_rounds);

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    parametersLayout.startAnimation(scaleAnimation);

                    selected_tab_in_navigation_bar = 4;

                }



            }
        });






        ///citationDataBase = new CitationsDataBase(getApplicationContext());

        ///citation = (TextView) findViewById(R.id.Home_citations_content_text);
        ///citationDataBase.addCitation("1", "Test");
        ///citation.setText(citationDataBase.get_one_random_citation());







        //listViewListeSports.setAdapter(listeSportsAdapter);



    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainFragment, fragment);
        fragmentTransaction.commit();
    }


}