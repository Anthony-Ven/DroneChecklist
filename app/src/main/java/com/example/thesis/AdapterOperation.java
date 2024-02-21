package com.example.thesis;

import android.annotation.SuppressLint;
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

public class AdapterOperation extends RecyclerView.Adapter<AdapterOperation.MyViewHolder> {

    private Context context;
    private ArrayList operation_id, operation_gson;
    Gson gson = new Gson();

    AdapterOperation(Context context, ArrayList operation_id, ArrayList operation_gson){
        this.context = context;
        this.operation_id = operation_id;
        this.operation_gson = operation_gson;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_operation, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,
                                 @SuppressLint("RecyclerView") int position) {
        //Get data to Display in RecycleView
        GsonAssessment gsonAssessment = gson.fromJson(String.valueOf(operation_gson.get(position)), GsonAssessment.class);//from json string convert to object
        holder.textCode.setText(gsonAssessment.getProjectCode());//Memilih key/object pada gson
        holder.rowOperation.setOnClickListener(new View.OnClickListener() {

            //Intent data to Update Assessment
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(operation_id.get(position))); //send id agar tau posisi baris yang di update
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return operation_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textCode;
        CardView rowOperation;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textCode = itemView.findViewById(R.id.textCode);
            rowOperation = itemView.findViewById(R.id.rowOperation);
        }
    }
}
