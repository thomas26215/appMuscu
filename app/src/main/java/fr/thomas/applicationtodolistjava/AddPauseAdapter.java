package fr.thomas.applicationtodolistjava;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import fr.thomas.applicationtodolistjava.liste_sports.ActivitiesOfListSport;

public class AddPauseAdapter extends ArrayAdapter<PreBuilds> {
    private Context context;
    private ArrayList<PreBuilds> preBuilds;
    private PreBuildsDataBase preBuildsDataBase;

    private AdapterCallback callBack;

    TextView timer;
    ImageView timer_back;

    public interface AdapterCallback {
        void setPause(int pause);
    }

    public AddPauseAdapter(Context context, ArrayList<PreBuilds> preBuilds, AdapterCallback callBack) {
        super(context, 0, preBuilds);
        this.context = context;
        this.preBuilds = preBuilds;
        this.callBack = callBack;
    }

    @Override
    public int getCount() {
        return preBuilds.size();
    }

    @Override
    public PreBuilds getItem(int position) {
        return preBuilds.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PreBuilds currentItem = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.pause_and_recovery_row_minimalist, parent, false);
        }

        preBuildsDataBase = new PreBuildsDataBase(context);


        timer = convertView.findViewById(R.id.tv_timer);
        timer.setText(currentItem.getValue() + " sec");

        timer_back = convertView.findViewById(R.id.iv_background_element);
        timer_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.setPause(Integer.parseInt(currentItem.getValue()));
            }
        });


        return convertView;
    }
}