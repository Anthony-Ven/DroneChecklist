package com.example.thesis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.google.gson.Gson;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class UpdateAssessmentActivity extends AppCompatActivity
        implements
        AdapterView.OnItemSelectedListener, //Spinner
        DatePickerDialog.OnDateSetListener, //DatePicker
        TimePickerDialog.OnTimeSetListener //TimePicker
{
    //Activity
    DatabaseSchema myDB;
    Gson gson = new Gson();

    //XML
    LinearLayout layoutDetailSect1, layoutDetailSect2,  layoutDetailSect3, layoutDetailSect4, layoutDetailSect5;
    Spinner spinnerMission;
    EditText inputPilotName, inputCoPilotName, inputProjectCode, inputMissionOther,
            inputLandownerPhone, inputLandownerEmail, inputMedicalFacility, inputSecurityFacility,
            inputAddress, inputRiskOther, inputMitigations, inputWindSpeed, inputWindDirect,
            inputGeneralForecast, inputNotes;
    TextView textSectionHeader1, textSectionHeader2, textSectionHeader3, textSectionHeader4, textSectionHeader5,
            textFlightDate, textSunriseTime, textSunsetTime;
    CheckedTextView checkPermissionAccess, checkVehicleAccess, checkRisk1, checkRisk2,
            checkRisk3, checkRisk4, checkRisk5, checkRisk6, checkRisk7, checkRisk8;
    Button buttonInputFlightDate, buttonInputSunriseTime, buttonInputSunsetTime, buttonUpdate;

    //Java Variable
    Calendar calendar = Calendar.getInstance();
    String id, flightDate, pilotName, coPilotName, projectCode, mission, missionOther, lTelephone,
            lEmail, medicalFacility, securityFacility, address, riskOther, mitigations,
            windSpeed, windDirection, generalForecast, sunriseTime, sunsetTime, note, assessment_gson;
    ArrayList<String> riskItems = new ArrayList<>();
    ArrayList<GsonFlight> flights = new ArrayList<>();
    boolean lPermission, vehicleAccess;
    int isFromClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_assessment);

        //Set XML
        //LinearLayout
        layoutDetailSect1 = findViewById(R.id.layoutDetailSect1);
        layoutDetailSect2 = findViewById(R.id.layoutDetailSect2);
        layoutDetailSect3 = findViewById(R.id.layoutDetailSect3);
        layoutDetailSect4 = findViewById(R.id.layoutDetailSect4);
        layoutDetailSect5 = findViewById(R.id.layoutDetailSect5);

        //TextView
        textFlightDate = findViewById(R.id.textFlightDate);
        textSunriseTime = findViewById(R.id.textSunriseTime);
        textSunsetTime = findViewById(R.id.textSunsetTime);
        textSectionHeader1 = findViewById(R.id.textSectionHeader1);
        textSectionHeader2 = findViewById(R.id.textSectionHeader2);
        textSectionHeader3 = findViewById(R.id.textSectionHeader3);
        textSectionHeader4 = findViewById(R.id.textSectionHeader4);
        textSectionHeader5 = findViewById(R.id.textSectionHeader5);

        //EditText
        inputPilotName = findViewById(R.id.inputPilotName);
        inputCoPilotName = findViewById(R.id.inputCoPilotName);
        inputProjectCode = findViewById(R.id.inputProjectCode);
        inputMissionOther = findViewById(R.id.inputMissionOther);
        inputLandownerPhone = findViewById(R.id.inputLandownerPhone);
        inputLandownerEmail = findViewById(R.id.inputLandownerEmail);
        inputMedicalFacility = findViewById(R.id.inputMedicalFacility);
        inputSecurityFacility = findViewById(R.id.inputSecurityFacility);
        inputAddress = findViewById(R.id.inputAddress);
        inputRiskOther = findViewById(R.id.inputRiskOther);
        inputMitigations = findViewById(R.id.inputMitigations);
        inputWindSpeed = findViewById(R.id.inputWindSpeed);
        inputWindDirect = findViewById(R.id.inputWindDirect);
        inputGeneralForecast = findViewById(R.id.inputGeneralForecast);
        inputNotes = findViewById(R.id.inputNotes);

        //Spinner
        spinnerMission = findViewById(R.id.spinnerMission);
        //Set Spinner Adapter
        ArrayAdapter<CharSequence> adt = ArrayAdapter.createFromResource(this,
                R.array.mission_items, R.layout.color_spinner_layout);
        adt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMission.setAdapter(adt);
        spinnerMission.setOnItemSelectedListener(this);

        //CheckedTextView
        checkPermissionAccess = findViewById(R.id.checkPermissionAccess);
        checkVehicleAccess = findViewById(R.id.checkVehicleAccess);
        checkRisk1 = findViewById(R.id.checkRisk1);
        checkRisk2 = findViewById(R.id.checkRisk2);
        checkRisk3 = findViewById(R.id.checkRisk3);
        checkRisk4 = findViewById(R.id.checkRisk4);
        checkRisk5 = findViewById(R.id.checkRisk5);
        checkRisk6 = findViewById(R.id.checkRisk6);
        checkRisk7 = findViewById(R.id.checkRisk7);
        checkRisk8 = findViewById(R.id.checkRisk8);

        //checkPermissionAccess onClick
        checkPermissionAccess.setOnClickListener(view -> {
            if (checkPermissionAccess.isChecked()) {
                checkPermissionAccess.setChecked(false);
                lPermission = false;
            } else {
                checkPermissionAccess.setChecked(true);
                lPermission = true;
            }
        });

        //checkVehicleAccess onClick
        //terdapat error dmna jika checklist tidak di klik maka akan berubah menjadi false
        //walaupun sebelumnya merupakan true
        checkVehicleAccess.setOnClickListener(view -> {
            if (checkVehicleAccess.isChecked()) {
                checkVehicleAccess.setChecked(false);
                vehicleAccess = false;
            } else {
                checkVehicleAccess.setChecked(true);
                vehicleAccess = true;
            }
        });

        //checkRisk onClick
        checkRisk1.setOnClickListener(view -> {
            if (checkRisk1.isChecked()) {
                checkRisk1.setChecked(false);
                riskItems.remove(String.valueOf(checkRisk1.getText()));
            } else {
                checkRisk1.setChecked(true);
                riskItems.add(String.valueOf(checkRisk1.getText()));
            }
        });
        checkRisk2.setOnClickListener(view -> {
            if (checkRisk2.isChecked()) {
                checkRisk2.setChecked(false);
                riskItems.remove(String.valueOf(checkRisk2.getText()));
            } else {
                checkRisk2.setChecked(true);
                riskItems.add(String.valueOf(checkRisk2.getText()));
            }
        });
        checkRisk3.setOnClickListener(view -> {
            if (checkRisk3.isChecked()) {
                checkRisk3.setChecked(false);
                riskItems.remove(String.valueOf(checkRisk3.getText()));
            } else {
                checkRisk3.setChecked(true);
                riskItems.add(String.valueOf(checkRisk3.getText()));
            }
        });
        checkRisk4.setOnClickListener(view -> {
            if (checkRisk4.isChecked()) {
                checkRisk4.setChecked(false);
                riskItems.remove(String.valueOf(checkRisk4.getText()));
            } else {
                checkRisk4.setChecked(true);
                riskItems.add(String.valueOf(checkRisk4.getText()));
            }
        });
        checkRisk5.setOnClickListener(view -> {
            if (checkRisk5.isChecked()) {
                checkRisk5.setChecked(false);
                riskItems.remove(String.valueOf(checkRisk5.getText()));
            } else {
                checkRisk5.setChecked(true);
                riskItems.add(String.valueOf(checkRisk5.getText()));
            }
        });
        checkRisk6.setOnClickListener(view -> {
            if (checkRisk6.isChecked()) {
                checkRisk6.setChecked(false);
                riskItems.remove(String.valueOf(checkRisk6.getText()));
            } else {
                checkRisk6.setChecked(true);
                riskItems.add(String.valueOf(checkRisk6.getText()));
            }
        });
        checkRisk7.setOnClickListener(view -> {
            if (checkRisk7.isChecked()) {
                checkRisk7.setChecked(false);
                riskItems.remove(String.valueOf(checkRisk7.getText()));
            } else {
                checkRisk7.setChecked(true);
                riskItems.add(String.valueOf(checkRisk7.getText()));
            }
        });
        checkRisk8.setOnClickListener(view -> {
            if (checkRisk8.isChecked()) {
                checkRisk8.setChecked(false);
                riskItems.remove(String.valueOf(checkRisk8.getText()));
            } else {
                checkRisk8.setChecked(true);
                riskItems.add(String.valueOf(checkRisk8.getText()));
            }
        });

        //Function to expand Cardview
        expand(textSectionHeader1, layoutDetailSect1);
        expand(textSectionHeader2, layoutDetailSect2);
        expand(textSectionHeader3, layoutDetailSect3);
        expand(textSectionHeader4, layoutDetailSect4);
        expand(textSectionHeader5, layoutDetailSect5);

        //Button
        buttonInputFlightDate = findViewById(R.id.buttonInputFlightDate);
        buttonInputSunriseTime = findViewById(R.id.buttonInputSunriseTime);
        buttonInputSunsetTime = findViewById(R.id.buttonInputSunsetTime);
        buttonUpdate = findViewById(R.id.buttonUpdate);

        //buttonInputFlightDate onClick
        buttonInputFlightDate.setOnClickListener(view -> {
            DialogFragment datePicker = new FragmentDatePicker();
            datePicker.show(getSupportFragmentManager(), "date picker");
        });

        //buttonInputSunriseTime onClick
        buttonInputSunriseTime.setOnClickListener(view -> {
            isFromClicked = 0;
            DialogFragment timePicker = new FragmentTimePicker();
            timePicker.show(getSupportFragmentManager(), "time picker");
        });

        //buttonInputSunsetTime onClick
        buttonInputSunsetTime.setOnClickListener(view -> {
            isFromClicked = 1;
            DialogFragment timePicker = new FragmentTimePicker();
            timePicker.show(getSupportFragmentManager(), "time picker");
        });

        //Function to load data from intent
        getAndSetIntentData();
        //buttonUpdate OnClick
        buttonUpdate.setOnClickListener(view -> {
            try {
                getInputData();
                //Transfer data to GsonAssessment.class
                GsonAssessment gsonassessment = new GsonAssessment(flightDate, pilotName,
                        coPilotName, projectCode, mission, missionOther, lTelephone, lEmail,
                        medicalFacility, securityFacility, address, riskOther, mitigations,
                        windSpeed, windDirection, generalForecast, sunriseTime, sunsetTime,
                        note, lPermission, vehicleAccess, riskItems, flights);
                //Transform data to json
                String json = gson.toJson(gsonassessment);
                //Save data to DB
                myDB = new DatabaseSchema(UpdateAssessmentActivity.this);
                myDB.updateOperation(id, json);

                Toast.makeText(UpdateAssessmentActivity.this, "Success", Toast.LENGTH_LONG).show();
            }catch (Exception e){
                Toast.makeText(UpdateAssessmentActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
            //Finish intent
            finish();
        });
    }

    //Get data from user input
    public void getInputData() {
        //get Data
        //String
        flightDate = textFlightDate.getText().toString().trim();
        pilotName = inputPilotName.getText().toString().trim();
        coPilotName = inputCoPilotName.getText().toString().trim();
        projectCode = inputProjectCode.getText().toString().trim();
        mission = spinnerMission.getSelectedItem().toString().trim();
        missionOther = inputMissionOther.getText().toString().trim();
        sunriseTime = textSunriseTime.getText().toString().trim();
        sunsetTime = textSunsetTime.getText().toString().trim();
        lTelephone = inputLandownerPhone.getText().toString().trim();
        lEmail = inputLandownerEmail.getText().toString().trim();
        medicalFacility = inputMedicalFacility.getText().toString().trim();
        securityFacility = inputSecurityFacility.getText().toString().trim();
        address = inputAddress.getText().toString().trim();
        riskOther = inputRiskOther.getText().toString().trim();
        mitigations = inputMitigations.getText().toString().trim();
        windSpeed = inputWindSpeed.getText().toString().trim();
        windDirection = inputWindDirect.getText().toString().trim();
        generalForecast = inputGeneralForecast.getText().toString().trim();
        note = inputNotes.getText().toString().trim();
        //checkedTextView
        //checkPermissionAccess
        if (checkPermissionAccess.isChecked()) {
            lPermission = true;
        } else {
            lPermission = false;
        }
        //checkVehicleAccess
        if (checkVehicleAccess.isChecked()){
            vehicleAccess = true;
        } else {
            vehicleAccess =false;
        }
    }

    //get data from intent
    public void getAndSetIntentData() {
        //Get data from intent
        id = getIntent().getStringExtra("id");
        getOperationGson(id);
        GsonAssessment gsonAssessment = gson.fromJson(assessment_gson, GsonAssessment.class);//from json string convert to gson

        //Set data from intent
        textFlightDate.setText(gsonAssessment.getFlightDate());
        inputPilotName.setText(gsonAssessment.getPilotName());
        inputCoPilotName.setText(gsonAssessment.getCoPilotName());
        inputProjectCode.setText(gsonAssessment.getProjectCode());
        inputMissionOther.setText(gsonAssessment.getMissionOther());
        textSunriseTime.setText(gsonAssessment.getSunriseTime());
        textSunsetTime.setText(gsonAssessment.getSunsetTime());
        inputLandownerPhone.setText(gsonAssessment.getlTelephone());
        inputLandownerEmail.setText(gsonAssessment.getlEmail());
        inputMedicalFacility.setText(gsonAssessment.getMedicalFacility());
        inputSecurityFacility.setText(gsonAssessment.getSecurityFacility());
        inputAddress.setText(gsonAssessment.getAddress());
        inputRiskOther.setText(gsonAssessment.getRiskOther());
        inputMitigations.setText(gsonAssessment.getMitigations());
        inputWindSpeed.setText(gsonAssessment.getWindSpeed());
        inputWindDirect.setText(gsonAssessment.getWindDirection());
        inputGeneralForecast.setText(gsonAssessment.getGeneralForecast());
        inputNotes.setText(gsonAssessment.getNote());
        checkPermissionAccess.setChecked(gsonAssessment.islPermission());
        checkVehicleAccess.setChecked(gsonAssessment.isVehicleAccess());
        riskItems.addAll(gsonAssessment.getRiskItems());
        if (riskItems.contains("Cables")){
            checkRisk1.setChecked(true);
        }
        if (riskItems.contains("Chimneys and poles")){
            checkRisk2.setChecked(true);
        }
        if (riskItems.contains("Crowd")){
            checkRisk3.setChecked(true);
        }
        if (riskItems.contains("Antenna and radio interference")){
            checkRisk4.setChecked(true);
        }
        if (riskItems.contains("Roads / Rights of Way")){
            checkRisk5.setChecked(true);
        }
        if (riskItems.contains("Airspace traffics")){
            checkRisk6.setChecked(true);
        }
        if (riskItems.contains("Livestock / Wildlife")){
            checkRisk7.setChecked(true);
        }
        if (riskItems.contains("Buildings / structures / vegetation")){
            checkRisk8.setChecked(true);
        }
        //Spinner
        mission = gsonAssessment.getMission();
        spinnerMission.setSelection(getIndex(spinnerMission,mission));

        //Flight Object
        flights = gsonAssessment.getFlights(); //Object
    }

    //Read Data from Database
    public void getOperationGson(String id){
        DatabaseSchema myDB = new DatabaseSchema(UpdateAssessmentActivity.this);
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

    //Get spinner index by mission string value
    private int getIndex(Spinner spinnerMission, String mission){
        for (int i = 0; i < spinnerMission.getCount(); i ++){
            if (spinnerMission.getItemAtPosition(i).toString().equalsIgnoreCase(mission)){
                return i;
            }
        }
        return 0;
    }

    //Expand Section
    public void expand(TextView textView, LinearLayout linearLayout) {
        textView.setOnClickListener(view -> {
            if (linearLayout.getVisibility() == View.GONE){
                linearLayout.setVisibility(View.VISIBLE);
            }else{
                linearLayout.setVisibility(View.GONE);
            }
        });
    }

    //DatePicker
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        flightDate = DateFormat.getDateInstance().format(calendar.getTime());
        textFlightDate.setText(flightDate);
    }

    //TimePicker
    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        calendar.set(Calendar.HOUR, hour);
        calendar.set(Calendar.MINUTE, minute);
        if (isFromClicked == 0) {
            textSunriseTime.setText(new StringBuilder(pad(hour)).append(":").append(pad(minute)));
        }
        if (isFromClicked == 1) {
            textSunsetTime.setText(new StringBuilder(pad(hour)).append(":").append(pad(minute)));
        }
    }

    //add 0 to time Format
    private static String pad(int c) {
        if (c >= 10) {
            return String.valueOf(c);
        } else {
            return "0" + String.valueOf(c);
        }
    }

    //Spinner onItemSelect
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i == 4) {
            inputMissionOther.setVisibility(View.VISIBLE);
        } else {
            inputMissionOther.setVisibility(View.GONE);
            inputMissionOther.setText(null);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}