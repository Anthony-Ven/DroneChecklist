package com.example.thesis;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class UpdateActivity extends AppCompatActivity {

    String id, assessment_gson, syncDate;
    DatabaseSchema myDB = new DatabaseSchema(UpdateActivity.this);
    //Declare XML
    TextView textSyncDate;
    Button buttonAssessment, buttonChecklist, buttonSync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        //Function to get data from intent
        getAndSetIntentData();//Get intent From MainActivity

        //Assign XML
        //TextView
        textSyncDate = findViewById(R.id.textSyncDate);
        //Button
        buttonAssessment = findViewById(R.id.buttonAssessment);
        buttonAssessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateActivity.this, UpdateAssessmentActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
                // finish(); // di finish karena getIntentData berasal dari Main activity bukan dari UpdateAssessment
                // Sehingga menyebabkan data tidak lngsung terupdate
            }
        });
        buttonChecklist = findViewById(R.id.buttonChecklist);
        buttonChecklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateActivity.this, FlightActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
        buttonSync = findViewById(R.id.buttonSync);
        buttonSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Function to Send Data to Server
                confirmOperationDialog();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAndSetIntentData();//Get intent From UpdateAssessment

        //Enable when flight attempt not null
        Gson gson = new Gson();
        getOperationGson(id);
        GsonAssessment gsonAssessment = gson.fromJson(assessment_gson, GsonAssessment.class); //Object
        ArrayList <GsonFlight> flights = gsonAssessment.getFlights(); //Object
        String json = gson.toJson(flights);
        if (!Objects.equals(json, "[]")){
            buttonSync.setEnabled(true);
        }
        if (!syncDate.equals("")){
            textSyncDate.setText("Last uploaded: " + syncDate);
        }
    }

    //Get data from intent
    public void getAndSetIntentData() {
        //Get data from intent
        id = getIntent().getStringExtra("id");
        getOperationGson(id);
        getSyncDate(id);
    }

    //Read Data from Database
    public void getOperationGson(String id){
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
    public void getSyncDate(String id){
        ArrayList <String> syncDates = new ArrayList<>();
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                syncDates.add(cursor.getString(2));
            }
        }
        syncDate = String.valueOf(syncDates.get(Integer.parseInt(id)-1));
    }

    void setSyncDate(){
        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        syncDate = df.format(c);
        myDB.updateSyncDate(id, syncDate);

        textSyncDate.setText("Last uploaded: " + syncDate);
    }

    void confirmOperationDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Gson gson = new Gson();
        GsonAssessment gsonAssessment = gson.fromJson(assessment_gson, GsonAssessment.class);
        String projectCode = gsonAssessment.getProjectCode();
        builder.setTitle("WARNING!!!");
        builder.setMessage(
                        "Before synchronizing Operation - " + projectCode +
                        ", please ensure the following statements:\n\n" +
                        "1. All of the data is correct.\n" +
                        "2. Have a stable internet connection.\n\n" +
                        "Are you sure you want to start the synchronization process?"
        );
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Upload Data to Server
                registerFlight();
                setSyncDate();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

private void registerFlight(){
    StringRequest stringRequest = new StringRequest(Request.Method.POST,
            Constants.URL_REGISTER,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        //myDB.deleteOneRow(id);
                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        finish();
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(UpdateActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            })
    {
        @Nullable
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> params = new HashMap<>();
            params.put("sqlite_id", id);
            params.put("operation", assessment_gson);
            params.put("sync_date", syncDate);
            return params;
        }
    };

    RequestQueue requestQueue = Volley.newRequestQueue(this);
    requestQueue.add(stringRequest);
}

}