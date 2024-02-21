package com.example.thesis;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class AdapterFlight extends RecyclerView.Adapter<AdapterFlight.MyViewHolder> {

    private Context context;
    private ArrayList <GsonFlight> flights;
    private String id, assessment_gson;

    AdapterFlight(Context context, ArrayList<GsonFlight> flights, String id, String assessment_gson){
        this.context = context;
        this.flights = flights;
        this.id = id;
        this.assessment_gson = assessment_gson;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_flight, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GsonFlight gsonFlight = flights.get(position);
        holder.textAttempt.setText(String.valueOf(flights.indexOf(gsonFlight)+1));
        holder.rowFlight.setOnClickListener(view -> {
            Intent intent = new Intent(context, UpdateChecklistActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("index", String.valueOf(position));
            intent.putExtra("assessment_gson", assessment_gson);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return flights.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView textAttempt;
        CardView rowFlight;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textAttempt = itemView.findViewById(R.id.textAttempt);
            rowFlight = itemView.findViewById(R.id.rowFlight);
        }
    }
}
