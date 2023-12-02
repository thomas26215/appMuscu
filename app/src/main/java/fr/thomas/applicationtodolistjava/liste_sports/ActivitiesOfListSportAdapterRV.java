package fr.thomas.applicationtodolistjava.liste_sports;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import fr.thomas.applicationtodolistjava.R;

public class ActivitiesOfListSportAdapterRV extends RecyclerView.Adapter<ActivitiesOfListSportAdapterRV.ActivitiesViewHolder> {
    private ArrayList<ActivitiesOfListSport> activitiesArrayList;
    private LayoutInflater inflater;
    private String theme = "minimalist";



    public ActivitiesOfListSportAdapterRV(Context context, ArrayList<ActivitiesOfListSport> activitiesArrayList) {
        inflater = LayoutInflater.from(context);
        this.activitiesArrayList = activitiesArrayList;
    }

    @NonNull
    @Override
    public ActivitiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(theme.contains("normal")){
            View itemView = inflater.inflate(R.layout.affichage_activities_view_row, parent, false);
            return new ActivitiesViewHolder(itemView);
        }else if(theme.contains("minimalist")){
            View itemView = inflater.inflate(R.layout.minimalist_dark_affichage_activities_view_row, parent, false);
            return new ActivitiesViewHolder(itemView);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ActivitiesViewHolder holder, int position) {
        ActivitiesOfListSport currentActivitieOfListSport = activitiesArrayList.get(position);
        holder.nomTextView.setText(currentActivitieOfListSport.getName());
        holder.delElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return activitiesArrayList.size();
    }

    public static class ActivitiesViewHolder extends RecyclerView.ViewHolder {
        public final TextView nomTextView;
        public final ImageView delElement;


        public ActivitiesViewHolder(View itemView) {
            super(itemView);
            nomTextView = itemView.findViewById(R.id.tv_nom_exercise_from_list_exercises_in_session);
            delElement = itemView.findViewById(R.id.iv_del_element);
        }
    }
}
