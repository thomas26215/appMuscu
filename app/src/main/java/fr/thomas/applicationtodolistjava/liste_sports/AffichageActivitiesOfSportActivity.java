package fr.thomas.applicationtodolistjava.liste_sports;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import fr.thomas.applicationtodolistjava.EditExerciseInRepetitionAdapter;
import fr.thomas.applicationtodolistjava.MainActivity;
import fr.thomas.applicationtodolistjava.R;
import fr.thomas.applicationtodolistjava.workout.WorkoutActivity;

public class AffichageActivitiesOfSportActivity extends AppCompatActivity implements ActivitiesOfListSportAdapter.AdapterCallback {

    private ListView listViewListeActivities;
    private RecyclerView recyclerViewListeActivities;
    TextView edit_button;
    private ArrayList<ActivitiesOfListSport> listOfActivities = new ArrayList<>();
    private ArrayList list = new ArrayList<>();

    private ListeSportsDataBase dataBaseSports;
    private ActivitiesOfListSportAdapter activitiesOfListSportAdapter;
    private ActivitiesOfListSportAdapterRV activitiesOfListSportAdapterRV;


    RecyclerView recyclerView;
    private ActivitiesOfListSportAdapterForRecycler adapter;

    ImageView go_back, launch_session;

    String theme = "minimalist", visibility = "hidden";

    @Override
    public void actualiser(ActivitiesOfListSport activity, int id_session) {
        dataBaseSports = new ListeSportsDataBase(getApplicationContext());
        listOfActivities = dataBaseSports.getExercicesSeanceWithId(id_session);
        activitiesOfListSportAdapter = new ActivitiesOfListSportAdapter(this, listOfActivities, this);
        listViewListeActivities.setAdapter(activitiesOfListSportAdapter);
    }
    public void actualiserWithouthChange(ActivitiesOfListSport activity, int id_session) {
        dataBaseSports = new ListeSportsDataBase(getApplicationContext());
        activitiesOfListSportAdapter = new ActivitiesOfListSportAdapter(this, listOfActivities, this);
        listViewListeActivities.setAdapter(activitiesOfListSportAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);


        super.onCreate(savedInstanceState);
        if (theme.contains("normal")) {
            setContentView(R.layout.activity_affichage_activities_of_sport);
        } else if (theme.contains("minimalist")) {
            setContentView(R.layout.minimalist_dark_activity_affichage_activities_of_sport);
        }

        Intent intent = getIntent();
        int id_session = intent.getIntExtra("id", 0);
        String name = intent.getStringExtra("name");
        TextView tv_name = findViewById(R.id.tv_session_name_from_display_of_exercises_of_workout);
        tv_name.setText(name);

        dataBaseSports = new ListeSportsDataBase(getApplicationContext());



        setRecyclerView(id_session);

        go_back = findViewById(R.id.iv_go_back_from_exrcises_list);
        launch_session = findViewById(R.id.launch_session_from_display_of_exercises_of_workout);
        edit_button = findViewById(R.id.tv_edit);

        dataBaseSports.setAllEtatsToValidate();

        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AffichageActivitiesOfSportActivity.this, MainActivity.class);
                v.getContext().startActivity(intent);
                overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
            }
        });

        launch_session.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AffichageActivitiesOfSportActivity.this, WorkoutActivity.class);
                int a = id_session;
                intent.putExtra("id_session", a);
                startActivity(intent);
            }
        });

        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeEditVisibility();
                setRecyclerViewWithouthChange()
 ;           }
        });
    }

    private void changeEditVisibility() {
        if (visibility.contains("hidden")) {
            for (ActivitiesOfListSport activity : listOfActivities) {
                activity.setIsEditVisible("visible");
                visibility = "visible";
                edit_button.setText("done");
            }
        } else {
            for (ActivitiesOfListSport activity : listOfActivities) {
                activity.setIsEditVisible("hidden");
                visibility = "hidden";
                edit_button.setText("edit");
            }
        }
    }
    private void setRecyclerView(int id_session){
        // Initialisez votre RecyclerView
        recyclerView = findViewById(R.id.affichage_activities_list_activities);

        // Créez une instance de ListeSportsDatabase et appelez la fonction pour obtenir la liste des sports

        listOfActivities = dataBaseSports.getExercicesSeanceWithId(id_session);

        // Créez une instance de votre adaptateur en lui passant la liste des sports
        adapter = new ActivitiesOfListSportAdapterForRecycler(this, listOfActivities);

        // Configurez le LinearLayoutManager pour votre RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        // Associez l'adaptateur à votre RecyclerView
        recyclerView.setAdapter(adapter);
    }

    private void setRecyclerViewWithouthChange(){
        // Initialisez votre RecyclerView
        recyclerView = findViewById(R.id.affichage_activities_list_activities);

        // Créez une instance de votre adaptateur en lui passant la liste des sports
        adapter = new ActivitiesOfListSportAdapterForRecycler(this, listOfActivities);

        // Configurez le LinearLayoutManager pour votre RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        // Associez l'adaptateur à votre RecyclerView
        recyclerView.setAdapter(adapter);
    }
}