package fr.thomas.applicationtodolistjava;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fr.thomas.applicationtodolistjava.liste_sports.ListeSportsDataBase;

public class EditExerciseInRepetitionAdapter extends RecyclerView.Adapter<EditExerciseInRepetitionAdapter.RepetitionsViewHolder>{


    private ArrayList<RepetitionsOfExercise> repetitionsList;
    private ListeSportsDataBase sportsDataBase;

    private Context context;

    private EditExerciseInRepetitionAdapter.AdapterCallback callback;

    public interface AdapterCallback {
        void AcutaliseAdapter(int idExo);
    }

    public EditExerciseInRepetitionAdapter(Context context, ArrayList<RepetitionsOfExercise> repetitionsList, AdapterCallback callback) {
        this.context = context;
        this.repetitionsList = repetitionsList;
        this.callback = callback;
    }

    @NonNull
    @Override
    public RepetitionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.minimalist_repetition_row, parent, false);
        return new RepetitionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RepetitionsViewHolder holder, int position) {
        RepetitionsOfExercise repetitions = repetitionsList.get(position);
        holder.bind(repetitions);
    }

    @Override
    public int getItemCount() {
        return repetitionsList.size();
    }


    public class RepetitionsViewHolder extends RecyclerView.ViewHolder {



        private EditText reps, charge;
        private ImageView delete, confirm;

        /*private TextView textViewId;
        private TextView textViewExercise;
        private TextView textViewCharge;
        private TextView textViewRepetitions;
        private TextView textViewTime;*/

        public RepetitionsViewHolder(@NonNull View itemView) {
            super(itemView);

            reps = itemView.findViewById(R.id.et_reps);
            charge = itemView.findViewById(R.id.et_charge);

            confirm = itemView.findViewById(R.id.iv_background_confirm);
            delete = itemView.findViewById(R.id.iv_background_delete);

            /*textViewId = itemView.findViewById(R.id.textViewId);
            textViewExercise = itemView.findViewById(R.id.textViewExercise);
            textViewCharge = itemView.findViewById(R.id.textViewCharge);
            textViewRepetitions = itemView.findViewById(R.id.textViewRepetitions);
            textViewTime = itemView.findViewById(R.id.textViewTime);*/
        }

        public void bind(RepetitionsOfExercise repetitions) {

            sportsDataBase = new ListeSportsDataBase(itemView.getContext());
            reps.setText(String.valueOf(repetitions.getNumber_repetitions()));
            charge.setText(String.valueOf(repetitions.getCharge()));

            reps.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(reps.getText().toString().isEmpty()){
                        System.out.println("reps empty");
                    }else{
                        sportsDataBase.setRepetitions(repetitions.getId(), Integer.parseInt(reps.getText().toString()));
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            charge.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(charge.getText().toString().isEmpty()){
                        System.out.println("charge empty");
                    }else{
                        sportsDataBase.setCharge(repetitions.getId(), Integer.parseInt(charge.getText().toString()));
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            System.out.println(repetitions.getEtat());

            if(repetitions.getEtat().contains("Non validé")){
                int color = ContextCompat.getColor(context, R.color.background_elements_minimalist_dark);
                confirm.setColorFilter(color);
            }else{
                int color = ContextCompat.getColor(context, R.color.green);
                confirm.setColorFilter(color);
            }

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sportsDataBase.deleteRepetition(repetitions.getId());
                    callback.AcutaliseAdapter(repetitions.getId_exercise());
                }
            });
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(repetitions.getEtat().contains("Non validé")){
                        sportsDataBase.setElementColumnById("RepetitionsDatabase", "Etat", "Validé", repetitions.getId(), "id");
                        int color = ContextCompat.getColor(context, R.color.background_elements_minimalist_dark);
                        confirm.setColorFilter(color);
                    }else{
                        sportsDataBase.setElementColumnById("RepetitionsDatabase", "Etat", "Non validé", repetitions.getId(), "id");
                        int color = ContextCompat.getColor(context, R.color.green);
                        confirm.setColorFilter(color);
                    }
                    callback.AcutaliseAdapter(repetitions.getId_exercise());
                }
            });

            /*textViewId.setText(String.valueOf(repetitions.getId()));
            textViewExercise.setText(String.valueOf(repetitions.getId_exercise()));
            textViewCharge.setText(String.valueOf(repetitions.getCharge()));
            textViewRepetitions.setText(String.valueOf(repetitions.getNumber_repetitions()));
            textViewTime.setText(String.valueOf(repetitions.getTime()));*/
        }

    }

}
