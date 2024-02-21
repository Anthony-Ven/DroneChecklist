package com.example.thesis;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import java.util.ArrayList;

public class FlightActivity extends AppCompatActivity {

    //XML Variable
    RecyclerView recyclerView;
    AdapterFlight adapterFlight;
    RecyclerView.LayoutManager layoutManager;
    Button buttonNewFlight;

    //JavaVariable
    DatabaseSchema myDB;//Database
    Gson gson = new Gson();//Call gson library
    ArrayList<GsonFlight> flights;//to save in DBS
    String id, assessment_gson, index;//for intent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight);

        //Set Java
        myDB = new DatabaseSchema(FlightActivity.this);
        flights = new ArrayList<>();
        //Set XML
        recyclerView = findViewById(R.id.recyclerView);
        buttonNewFlight = findViewById(R.id.buttonNewFlight);

        //Get Intent Data
        getAndSetIntentData();

        //onClick buttonNewFlight
        buttonNewFlight.setOnClickListener(view -> {
            Intent intent = new Intent(FlightActivity.this, ChecklistActivity.class);
            startActivityForResult(intent, 1);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                //Add data to flights Arraylist
                String jsonFlight = data.getStringExtra("flights");
                GsonFlight gsonFlight = gson.fromJson(jsonFlight, GsonFlight.class);//from json string convert to object
                flights.add(gsonFlight);

                //Update database
                GsonAssessment gsonAssessment = gson.fromJson(assessment_gson, GsonAssessment.class);
                gsonAssessment.setFlights(flights);
                String jsonAssessment = gson.toJson(gsonAssessment);
                myDB.updateOperation(id, jsonAssessment);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Update Recycle View Layout List
        //Assign Layout
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapterFlight = new AdapterFlight(this, flights, id, assessment_gson);

        //Set Layout
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterFlight);
    }

    public void getAndSetIntentData() {//Get Intent from Previous Activity
        //Get data from intent
        id = getIntent().getStringExtra("id");
        getOperationGson(id);
        GsonAssessment gsonAssessment = gson.fromJson(assessment_gson, GsonAssessment.class);
        flights = gsonAssessment.getFlights();
    }

    //Read Data from Database
    public void getOperationGson(String id){
        DatabaseSchema myDB = new DatabaseSchema(FlightActivity.this);
        ArrayList <String> operation_gson = new ArrayList<>();
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                operation_gson.add(cursor.getString(1));
            }
        }
        assessment_gson = String.valueOf(operation_gson.get(Integer.parseInt(id)-1));
    }
}