package com.example.thesis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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

public class AssessmentActivity extends AppCompatActivity
        implements
        AdapterView.OnItemSelectedListener, //Spinner
        DatePickerDialog.OnDateSetListener, //DatePicker
        TimePickerDialog.OnTimeSetListener //TimePicker
{
    //GSON
    Gson gson = new Gson();

    //Activity
    DatabaseSchema myDB;

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
    Button buttonInputFlightDate, buttonInputSunriseTime, buttonInputSunsetTime, buttonSave;

    //Java Variable
    Calendar calendar = Calendar.getInstance();
    String flightDate, pilotName, coPilotName, projectCode, mission, missionOther, lTelephone,
            lEmail, medicalFacility, securityFacility, address, riskOther, mitigations,
            windSpeed, windDirection, generalForecast, sunriseTime, sunsetTime, note;
    ArrayList<String> riskItems = new ArrayList<>();
    ArrayList<GsonFlight> flights = new ArrayList<>();
    boolean lPermission, vehicleAccess;
    int isFromClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);

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
        checkPermissionAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermissionAccess.isChecked()) {
                    checkPermissionAccess.setChecked(false);
                    lPermission = false;
                } else {
                    checkPermissionAccess.setChecked(true);
                    lPermission = true;
                }
            }
        });

        //checkVehicleAccess onClick
        checkVehicleAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkVehicleAccess.isChecked()) {
                    checkVehicleAccess.setChecked(false);
                    vehicleAccess = false;
                } else {
                    checkVehicleAccess.setChecked(true);
                    vehicleAccess = true;
                }
            }
        });

        //checkRisk onClick
        checkRisk1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkRisk1.isChecked()) {
                    checkRisk1.setChecked(false);
                    riskItems.remove(String.valueOf(checkRisk1.getText()));
                } else {
                    checkRisk1.setChecked(true);
                    riskItems.add(String.valueOf(checkRisk1.getText()));
                }
            }
        });
        checkRisk2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkRisk2.isChecked()) {
                    checkRisk2.setChecked(false);
                    riskItems.remove(String.valueOf(checkRisk2.getText()));
                } else {
                    checkRisk2.setChecked(true);
                    riskItems.add(String.valueOf(checkRisk2.getText()));
                }
            }
        });
        checkRisk3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkRisk3.isChecked()) {
                    checkRisk3.setChecked(false);
                    riskItems.remove(String.valueOf(checkRisk3.getText()));
                } else {
                    checkRisk3.setChecked(true);
                    riskItems.add(String.valueOf(checkRisk3.getText()));
                }
            }
        });
        checkRisk4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkRisk4.isChecked()) {
                    checkRisk4.setChecked(false);
                    riskItems.remove(String.valueOf(checkRisk4.getText()));
                } else {
                    checkRisk4.setChecked(true);
                    riskItems.add(String.valueOf(checkRisk4.getText()));
                }
            }
        });
        checkRisk5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkRisk5.isChecked()) {
                    checkRisk5.setChecked(false);
                    riskItems.remove(String.valueOf(checkRisk5.getText()));
                } else {
                    checkRisk5.setChecked(true);
                    riskItems.add(String.valueOf(checkRisk5.getText()));
                }
            }
        });
        checkRisk6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkRisk6.isChecked()) {
                    checkRisk6.setChecked(false);
                    riskItems.remove(String.valueOf(checkRisk6.getText()));
                } else {
                    checkRisk6.setChecked(true);
                    riskItems.add(String.valueOf(checkRisk6.getText()));
                }
            }
        });
        checkRisk7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkRisk7.isChecked()) {
                    checkRisk7.setChecked(false);
                    riskItems.remove(String.valueOf(checkRisk7.getText()));
                } else {
                    checkRisk7.setChecked(true);
                    riskItems.add(String.valueOf(checkRisk7.getText()));
                }
            }
        });
        checkRisk8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkRisk8.isChecked()) {
                    checkRisk8.setChecked(false);
                    riskItems.remove(String.valueOf(checkRisk8.getText()));
                } else {
                    checkRisk8.setChecked(true);
                    riskItems.add(String.valueOf(checkRisk8.getText()));
                }
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
        buttonSave = findViewById(R.id.buttonSave);

        //buttonInputFlightDate onClick
        buttonInputFlightDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new FragmentDatePicker();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        //buttonInputSunriseTime onClick
        buttonInputSunriseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFromClicked = 0;
                DialogFragment timePicker = new FragmentTimePicker();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        //buttonInputSunsetTime onClick
        buttonInputSunsetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFromClicked = 1;
                DialogFragment timePicker = new FragmentTimePicker();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        //buttonSave OnClick
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    getInputData();
                    //Transform data to json
                    GsonAssessment gsonassessment = new GsonAssessment(flightDate, pilotName,
                            coPilotName, projectCode, mission, missionOther, lTelephone, lEmail,
                            medicalFacility, securityFacility, address, riskOther, mitigations,
                            windSpeed, windDirection, generalForecast, sunriseTime, sunsetTime,
                            note, lPermission, vehicleAccess, riskItems, flights);
                    String json = gson.toJson(gsonassessment);//from object convert to json
                    String syncDate = "";
                    //Save data to DB
                    myDB = new DatabaseSchema(AssessmentActivity.this);
                    myDB.addOperation(json, syncDate);

                    //Exception
                    Toast.makeText(AssessmentActivity.this, "Success", Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    Toast.makeText(AssessmentActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
                    //Finish intent
                    finish();
            }
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
    }

    //Expand Section
    public void expand(TextView textView, LinearLayout linearLayout) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (linearLayout.getVisibility() == View.GONE){
                    linearLayout.setVisibility(View.VISIBLE);
                }else{
                    linearLayout.setVisibility(View.GONE);
                }
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

    //Time format
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