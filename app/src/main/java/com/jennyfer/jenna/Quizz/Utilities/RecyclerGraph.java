package com.jennyfer.jenna.Quizz.Utilities;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jennyfer.jenna.Quizz.Models.GraphData;
import com.jennyfer.jenna.R;

import java.util.ArrayList;

public class RecyclerGraph extends RecyclerView.Adapter<RecyclerGraph.ViewHolder> {

    private ArrayList<GraphData> dimension;


    public RecyclerGraph(ArrayList<GraphData> dimension) {
        this.dimension = dimension;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(layoutInflater.inflate(R.layout.new_item_result, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GraphData item = dimension.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return dimension.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView semaforo_1;
        private ImageView semaforo_2;
        private ImageView semaforo_3;
        private RelativeLayout color_general;
        private TextView factorName_1;
        private TextView factorName_2;
        private TextView factorName_3;
        private TextView dim_1;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            semaforo_1 = itemView.findViewById(R.id.fact_1_color);
            semaforo_2 = itemView.findViewById(R.id.fac_2_color);
            semaforo_3 = itemView.findViewById(R.id.fac_3_color);
            color_general = itemView.findViewById(R.id.general_color);
            factorName_1 = itemView.findViewById(R.id.fac_1_txt);
            factorName_2 = itemView.findViewById(R.id.fac_2_text);
            factorName_3 = itemView.findViewById(R.id.fac_3_txt);
            dim_1 = itemView.findViewById(R.id.dim_txt);
        }

        public void bind(GraphData factordatas) {
            semaforo_1.setImageResource(setImage(factordatas.getFactores().get(0).getColor()));
            semaforo_2.setImageResource(setImage(factordatas.getFactores().get(1).getColor()));
            semaforo_3.setImageResource(setImage(factordatas.getFactores().get(2).getColor()));
            factorName_1.setText(factordatas.getFactores().get(0).getTittulo());
            factorName_2.setText(factordatas.getFactores().get(1).getTittulo());
            factorName_3.setText(factordatas.getFactores().get(2).getTittulo());


            if(factordatas.getFactores().get(0).getColor() != "gray" && factordatas.getFactores().get(1).getColor() != "gray" && factordatas.getFactores().get(2).getColor() != "gray"){
                if ( (factordatas.getFactores().get(0).getColor() == "Verde" && factordatas.getFactores().get(1).getColor() == "Verde" && factordatas.getFactores().get(2).getColor() == "Verde") || (factordatas.getFactores().get(0).getColor() == "Verde" && factordatas.getFactores().get(1).getColor() == "Verde" && factordatas.getFactores().get(2).getColor() == "Amarillo") || (factordatas.getFactores().get(0).getColor() == "Verde" && factordatas.getFactores().get(1).getColor() == "Amarillo" && factordatas.getFactores().get(2).getColor() == "Verde" || factordatas.getFactores().get(0).getColor() == "Amarillo" && factordatas.getFactores().get(1).getColor() == "Verde" && factordatas.getFactores().get(2).getColor() == "Verde")) {
                    color_general.setBackgroundColor(Color.GREEN);
                }else if( (factordatas.getFactores().get(0).getColor() == "Verde" && factordatas.getFactores().get(1).getColor() == "Amarillo" && factordatas.getFactores().get(2).getColor() == "Amarillo") || (factordatas.getFactores().get(0).getColor() == "Amarillo" && factordatas.getFactores().get(1).getColor() == "Verde" && factordatas.getFactores().get(2).getColor() == "Amarillo") || (factordatas.getFactores().get(0).getColor() == "Amarillo" && factordatas.getFactores().get(1).getColor() == "Amarillo" && factordatas.getFactores().get(2).getColor() == "Verde") || (factordatas.getFactores().get(0).getColor() == "Amarillo" && factordatas.getFactores().get(1).getColor() == "Amarillo" && factordatas.getFactores().get(2).getColor() == "Amarillo") ){
                    color_general.setBackgroundColor(Color.YELLOW);
                }else{
                    color_general.setBackgroundColor(Color.RED);
                }
            }
            dim_1.setText(factordatas.getTitulo());
        }


        private int setImage(String desc) {
            switch (desc) {
                case "Rojo":
                    return R.drawable.red_light;
                case "Verde":
                    return R.drawable.green_light;
                case "Amarillo":
                    return R.drawable.yellow_light;
                default:
                    return R.drawable.offline_light;
            }
        }
    }

}