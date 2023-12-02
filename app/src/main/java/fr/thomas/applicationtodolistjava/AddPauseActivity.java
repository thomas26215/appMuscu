package fr.thomas.applicationtodolistjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import fr.thomas.applicationtodolistjava.liste_sports.ListeSportsDataBase;

public class AddPauseActivity extends AppCompatActivity implements AddPauseAdapter.AdapterCallback{

    PreBuildsDataBase preBuildsDataBase;
    ListeSportsDataBase listeSportsDataBase;
    int id_exo;

    TextView minutes_display, secondes_display;
    ImageView addPausePreset;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pause_and_recovery);

        preBuildsDataBase = new PreBuildsDataBase(this);
        listeSportsDataBase = new ListeSportsDataBase(this);

        Intent intent = getIntent();
        id_exo = intent.getIntExtra("id_exo", 0);

        GridView pre_timers = findViewById(R.id.pre_timers);
        int verticalSpacingInDp = 16; // Remplacez 16 par la valeur d'espacement vertical souhaitée en dp
        float scale = getResources().getDisplayMetrics().density;
        int verticalSpacingInPixels = (int) (verticalSpacingInDp * scale + 0.5f);

        pre_timers.setVerticalSpacing(verticalSpacingInPixels);

        minutes_display = findViewById(R.id.tv_minutes_display);
        secondes_display = findViewById(R.id.tv_secondes_display);

        addPausePreset = findViewById(R.id.iv_background_ajouter);

        //preBuildsDataBase.addPause("20");

        ArrayList<PreBuilds> arrayList = preBuildsDataBase.getPausePreBuilds();
        System.out.println("taille = " + arrayList.size());
        AddPauseAdapter adaptateur = new AddPauseAdapter(this, arrayList, this);
        pre_timers.setAdapter(adaptateur);

        addPausePreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preBuildsDataBase.addPause(String.valueOf(Integer.parseInt(minutes_display.getText().toString()) * 60 + Integer.parseInt(secondes_display.getText().toString())));
            }
        });


    }
    public void setPause(int pause){
        listeSportsDataBase.setExercisePause(pause , id_exo);
        int minute = 0;
        int time = pause;

        while(time > 60){
            minute += 1;
            time -= 60;
        }

        minutes_display.setText(String.valueOf(minute));
        secondes_display.setText(String.valueOf(time));
    }

}