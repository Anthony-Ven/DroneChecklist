package com.example.thesis;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //XML Variable
    RecyclerView recyclerView;
    AdapterOperation adapterOperation;
    Button buttonNewOperation;

    DatabaseSchema myDB;
    ArrayList<String> operation_id, operation_gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set XML
        recyclerView = findViewById(R.id.recyclerView);
        buttonNewOperation = findViewById(R.id.buttonNewOperation);

        //onClick buttonNewOperation
        buttonNewOperation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmOperationDialog();
            }
        });

        //Set Java
        myDB = new DatabaseSchema(MainActivity.this);
        operation_id = new ArrayList<>();
        operation_gson = new ArrayList<>();
    }

    @Override
    protected void onPause() {
        super.onPause();

        //Remove Data in Array so its not multiply by onStart() again
        removeDataInArrays();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Get Data in Database and Store in Array
        storeDataInArrays();

        //Display data in RecycleView
        adapterOperation = new AdapterOperation(MainActivity.this, operation_id, operation_gson);
        recyclerView.setAdapter(adapterOperation);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    void storeDataInArrays(){
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                operation_id.add(cursor.getString(0));
                operation_gson.add(cursor.getString(1));
            }
        }
    }

    void removeDataInArrays(){
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                operation_id.remove(cursor.getString(0));
                operation_gson.remove(cursor.getString(1));
            }
        }
    }

    void confirmOperationDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Create New Flight Operation?");
        builder.setMessage("Are you sure you want to create new Flight Operation?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(MainActivity.this, AssessmentActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.create().show();
    }
}