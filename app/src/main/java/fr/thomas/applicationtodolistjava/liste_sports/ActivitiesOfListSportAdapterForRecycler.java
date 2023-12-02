package fr.thomas.applicationtodolistjava.liste_sports;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fr.thomas.applicationtodolistjava.R;

public class ActivitiesOfListSportAdapterForRecycler extends RecyclerView.Adapter<ActivitiesOfListSportAdapterForRecycler.NewActivitiesOfListSportsViewHolder>{

    private ArrayList<ActivitiesOfListSport> activitiesOfListSports;
    private ListeSportsDataBase sportsDataBase;

    private Context context;

    public ActivitiesOfListSportAdapterForRecycler(Context context, ArrayList<ActivitiesOfListSport> activitiesOfListSports) {
        this.context = context;
        this.activitiesOfListSports = activitiesOfListSports;
    }

    @NonNull
    @Override
    public NewActivitiesOfListSportsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.minimalist_dark_affichage_activities_view_row, parent, false);
        return new NewActivitiesOfListSportsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewActivitiesOfListSportsViewHolder holder, int position) {
        ActivitiesOfListSport currentactivitieOfListSport = activitiesOfListSports.get(position);
        holder.bind(currentactivitieOfListSport, position);
    }

    @Override
    public int getItemCount() {
        return activitiesOfListSports.size();
    }

    public ActivitiesOfListSport getItem(int position) {
        return activitiesOfListSports.get(position);
    }

    public class NewActivitiesOfListSportsViewHolder extends RecyclerView.ViewHolder {

        public ListeSportsDataBase dataBaseSports;

        TextView Nom;
        ImageView mooveUp, mooveDown, delElement;

        public NewActivitiesOfListSportsViewHolder(@NonNull View itemView) {
            super(itemView);
            dataBaseSports = new ListeSportsDataBase(context);

            Nom = itemView.findViewById(R.id.tv_nom_exercise_from_list_exercises_in_session);

            mooveUp = itemView.findViewById(R.id.iv_moove_up);
            mooveDown = itemView.findViewById(R.id.iv_moove_down);
            delElement = itemView.findViewById(R.id.iv_del_element);
            mooveDown = itemView.findViewById(R.id.iv_moove_down);


        }

        public void bind(ActivitiesOfListSport currentactivitieOfListSport, int position) {
            Nom.setText(currentactivitieOfListSport.getName());

            delElement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dataBaseSports.deleteElementById(currentactivitieOfListSport.getId());
                    activitiesOfListSports.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                }
            });

            mooveUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moveExerciseUp(currentactivitieOfListSport.getId(), currentactivitieOfListSport.getId_sport());
                    notifyItemChanged(getAdapterPosition());
                }
            });
            mooveDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moveExerciseDown(currentactivitieOfListSport.getId(), currentactivitieOfListSport.getId_sport());
                    notifyDataSetChanged();
                    notifyItemChanged(getAdapterPosition());
                }
            });

            if(currentactivitieOfListSport.isEditVisible().contains("visible")){
                mooveUp.setVisibility(View.VISIBLE);
                mooveDown.setVisibility(View.VISIBLE);
                delElement.setVisibility(View.VISIBLE);
            }
            if(currentactivitieOfListSport.isEditVisible().contains("hidden")){
                mooveUp.setVisibility(View.GONE);
                mooveDown.setVisibility(View.GONE);
                delElement.setVisibility(View.GONE);
            }
        }

        void moveExerciseDown(int id, int id_sport){
            int next_id = dataBaseSports.getNextExerciseId(id, id_sport);
            System.out.println("first = " + id + " second = " + next_id);
            dataBaseSports.inverserElements(id, next_id);
        }
        void moveExerciseUp(int id, int id_sport){
            int next_id = dataBaseSports.getLastId(id, id_sport);
            System.out.println("first = " + id + " second = " + next_id);
            dataBaseSports.inverserElements(id, next_id);
        }

    }
}