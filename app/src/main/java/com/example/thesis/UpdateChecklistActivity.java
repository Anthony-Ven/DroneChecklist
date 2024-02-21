package com.example.thesis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;

public class UpdateChecklistActivity extends AppCompatActivity
        implements
        TimePickerDialog.OnTimeSetListener //TimePicker
{
    ChecklistInput[] checklistInputs;
    //Database Related
    DatabaseSchema myDB;
    //GSON
    Gson gson = new Gson();
    ArrayList <GsonFlight> flights = new ArrayList<>();
    String index, _id, assessment_gson; // For Update

    //XML Variable
    LinearLayout layoutDetailPre1, layoutDetailPre2, layoutDetailPre3, layoutDetailPre4,
            layoutDetailPre5, layoutDetailPost1, layoutDetailPost2;
    TextView textPreHeader1, textPreHeader2, textPreHeader3, textPreHeader4, textPreHeader5,
            textPostHeader1, textPostHeader2, textTakeOffTime, textLandingTime;
    CheckedTextView checkInput_BodyCond, checkInput_PropellerCondition, checkInput_PropellerInstalled,
            checkInput_InstallDroneBatt, checkInput_Remove2Gimbal, checkInput_InstallDevice, checkInput_ConnectDevice,
            checkInput_StraightenAntenna, checkInput_Software, checkInput_PowerOnRemote, checkInput_PowerOnDrone, checkInput_SDcardInsert,
            checkInput_CameraClean, checkInput_CameraAdjusted, checkInput_GimbalSystem, checkInput_CameraWork, checkInput_AOI,
            checkInput_HomeIndicator, checkInput_FlightMission, checkInput_FlightParameter, checkInput_AssessRisks,
            checkInput_EndMissionAct, checkInput_UploadFlightPlan, checkInput_IMU, checkInput_SetRTH, checkInput_Countdown,
            checkInput_TurnOffRemote, checkInput_TurnOffDrone, checkInput_DroneCondition,
            checkInput_Disassembly, checkInput_CopyData, checkInput_PhotoQLTY;
    EditText inputRemoteBattery, inputDroneBattery, inputDeviceBatt, inputMissionDuration, inputLiPoBatt, inputTelSignal, inputGPSsatellite,
            inputTotalFlight, inputDroneBattPost, inputPhotos;
    Button buttonInputTakeOffTime, buttonInputLandingTime, buttonUpdate;

    //Java Variable
    Calendar calendar = Calendar.getInstance();
    int isFromClicked; //flag
    int remoteBattery, droneBattery, deviceBatt, missionDuration, liPoBatt, telSignal, gpsSatellite, totalFlight, droneBattPost, photos;
    long takeOffTimeHour, takeOffTimeMin, landingTimeHour, landingTimeMin, totalFlightTime,
            deltaHour, deltaMin, deltaMinute;
    boolean checkBodyCond, checkPropellerCond, checkPropellerInstalled, checkInstallDroneBatt, checkRemove2Gimbal,
            checkInstallDevice, checkConnectDevice, checkStraightenAntenna, checkSoftware, checkPowerOnRemote,
            checkPowerOnDrone, checkSDcardInsert, checkCameraClean, checkCameraAdjusted, checkGimbalSystem,
            checkCameraWork, checkAOI, checkHomeIndicator, checkFlightMission, checkFlightParameter,
            checkAssessRisks, checkEndMissionAct, checkUploadFlightPlan, checkIMU, checkSetRTH,
            checkCountdown,
            checkTurnOffRemote, checkTurnOffDrone, checkDroneCondition, checkDisassembly, checkCopyData,
            checkPhotoQLTY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_checklist);

        myDB = new DatabaseSchema(UpdateChecklistActivity.this);

        //Set Java
        remoteBattery = 0;
        droneBattery = 0;
        deviceBatt = 0;
        missionDuration = 0;
        liPoBatt = 0;
        telSignal = 0;
        gpsSatellite = 0;
        totalFlight = 0;
        droneBattPost = 0;
        photos = 0;
        takeOffTimeHour = 0;
        takeOffTimeMin = 0;
        landingTimeHour = 0;
        landingTimeMin = 0;
        totalFlightTime = 0;
        deltaHour = 0;
        deltaMin = 0;
        deltaMinute = 0;

        //Set XML
        //LinearLayout
        layoutDetailPre1 = findViewById(R.id.layoutDetailPre1);
        layoutDetailPre2 = findViewById(R.id.layoutDetailPre2);
        layoutDetailPre3 = findViewById(R.id.layoutDetailPre3);
        layoutDetailPre4 = findViewById(R.id.layoutDetailPre4);
        layoutDetailPre5 = findViewById(R.id.layoutDetailPre5);
        layoutDetailPost1 = findViewById(R.id.layoutDetailPost1);
        layoutDetailPost2 = findViewById(R.id.layoutDetailPost2);

        //TextView
        textPreHeader1 = findViewById(R.id.textPreHeader1);
        textPreHeader2 = findViewById(R.id.textPreHeader2);
        textPreHeader3 = findViewById(R.id.textPreHeader3);
        textPreHeader4 = findViewById(R.id.textPreHeader4);
        textPreHeader5 = findViewById(R.id.textPreHeader5);
        textPostHeader1 = findViewById(R.id.textPostHeader1);
        textPostHeader2 = findViewById(R.id.textPostHeader2);
        textTakeOffTime = findViewById(R.id.textTakeOffTime);
        textLandingTime = findViewById(R.id.textLandingTime);

        //CheckedTextView
        checkedTextViewOnClickListener();

        //EditText
        //PreFlight
        inputRemoteBattery = findViewById(R.id.inputRemoteBattery);
        inputDroneBattery = findViewById(R.id.inputDroneBattery);
        inputDeviceBatt = findViewById(R.id.inputDeviceBatt);
        inputMissionDuration = findViewById(R.id.inputMissionDuration);
        inputLiPoBatt = findViewById(R.id.inputLiPoBatt);
        inputTelSignal = findViewById(R.id.inputTelSignal);
        inputGPSsatellite = findViewById(R.id.inputGPSsatellite);
        //PostFlight
        inputTotalFlight = findViewById(R.id.inputTotalFlight);
        inputTotalFlight.setOnClickListener(view -> flightTime());
        inputDroneBattPost = findViewById(R.id.inputDroneBattPost);
        inputPhotos = findViewById(R.id.inputPhotos);

        //Button
        buttonInputTakeOffTime = findViewById(R.id.buttonInputTakeOffTime);
        buttonInputLandingTime = findViewById(R.id.buttonInputLandingTime);
        buttonUpdate = findViewById(R.id.buttonUpdate);

        //Button setOnClick
        //buttonInputTakeOffTime onClick
        buttonInputTakeOffTime.setOnClickListener(view -> {
            isFromClicked = 0;
            DialogFragment timePicker = new FragmentTimePicker();
            timePicker.show(getSupportFragmentManager(), "time picker");
        });

        //buttonInputLandingTime onClick
        buttonInputLandingTime.setOnClickListener(view -> {
            isFromClicked = 1;
            DialogFragment timePicker = new FragmentTimePicker();
            timePicker.show(getSupportFragmentManager(), "time picker");

        });

        //Function to expand CardView
        expand(textPreHeader1, layoutDetailPre1);
        expand(textPreHeader2, layoutDetailPre2);
        expand(textPreHeader3, layoutDetailPre3);
        expand(textPreHeader4, layoutDetailPre4);
        expand(textPreHeader5, layoutDetailPre5);
        expand(textPostHeader1, layoutDetailPost1);
        expand(textPostHeader2, layoutDetailPost2);

        //get Intent
        getAndSetIntentData();

        //buttonSave
        buttonUpdate.setOnClickListener(view -> {
            try {
                getInputData();
                //Transform data to json
                GsonFlight gsonFlight = new GsonFlight(checkBodyCond, checkPropellerCond,
                        checkPropellerInstalled, checkInstallDroneBatt, checkRemove2Gimbal, checkInstallDevice,
                        checkConnectDevice, checkStraightenAntenna, checkSoftware, checkPowerOnRemote,
                        checkPowerOnDrone, checkSDcardInsert, checkCameraClean, checkCameraAdjusted,
                        checkGimbalSystem, checkCameraWork, checkAOI, checkHomeIndicator,
                        checkFlightMission, checkFlightParameter, checkAssessRisks, checkEndMissionAct,
                        checkUploadFlightPlan, checkIMU, checkSetRTH, checkCountdown,
                        checkTurnOffRemote, checkTurnOffDrone, checkDroneCondition, checkDisassembly,
                        checkCopyData, checkPhotoQLTY, remoteBattery, droneBattery, deviceBatt, missionDuration, liPoBatt,
                        telSignal, gpsSatellite, totalFlight, droneBattPost, photos, totalFlightTime);
                flights.set(Integer.parseInt(index), gsonFlight);
                //Update database
                GsonAssessment gsonAssessment = gson.fromJson(assessment_gson, GsonAssessment.class);
                gsonAssessment.setFlights(flights);
                String jsonAssessment = gson.toJson(gsonAssessment);
                myDB.updateOperation(_id, jsonAssessment);

                //Exception
                Toast.makeText(UpdateChecklistActivity.this, "Success", Toast.LENGTH_LONG).show();
            }catch (Exception e){
                Toast.makeText(UpdateChecklistActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
            //Finish intent and send result
            finish();
        });
    }

    //Function
    public void getInputData(){
        //Get Input Data
        //EditText
        remoteBattery = Integer.parseInt(inputRemoteBattery.getText().toString().trim());
        droneBattery = Integer.parseInt(inputDroneBattery.getText().toString().trim());
        deviceBatt = Integer.parseInt(inputDeviceBatt.getText().toString().trim());
        missionDuration = Integer.parseInt(inputMissionDuration.getText().toString().trim());
        liPoBatt = Integer.parseInt(inputLiPoBatt.getText().toString().trim());
        telSignal = Integer.parseInt(inputTelSignal.getText().toString().trim());
        gpsSatellite = Integer.parseInt(inputGPSsatellite.getText().toString().trim());
        totalFlight = Integer.parseInt(inputTotalFlight.getText().toString().trim());
        droneBattPost = Integer.parseInt(inputDroneBattPost.getText().toString().trim());
        photos = Integer.parseInt(inputPhotos.getText().toString().trim());

        //CheckedTextView
        checkBodyCond = checkedInput(0);
        checkPropellerCond = checkedInput(1);
        checkPropellerInstalled = checkedInput(2);
        checkInstallDroneBatt = checkedInput(3);
        checkRemove2Gimbal = checkedInput(4);
        checkInstallDevice = checkedInput(5);
        checkConnectDevice = checkedInput(6);
        checkStraightenAntenna = checkedInput(7);
        checkSoftware = checkedInput(8);
        checkPowerOnRemote = checkedInput(9);
        checkPowerOnDrone = checkedInput(10);
        checkSDcardInsert = checkedInput(11);
        checkCameraClean = checkedInput(12);
        checkCameraAdjusted = checkedInput(13);
        checkGimbalSystem = checkedInput(14);
        checkCameraWork = checkedInput(15);
        checkAOI = checkedInput(16);
        checkHomeIndicator = checkedInput(17);
        checkFlightMission = checkedInput(18);
        checkFlightParameter = checkedInput(19);
        checkAssessRisks = checkedInput(20);
        checkEndMissionAct = checkedInput(21);
        checkUploadFlightPlan = checkedInput(22);
        checkIMU = checkedInput(23);
        checkSetRTH = checkedInput(24);
        checkCountdown = checkedInput(25);
        checkTurnOffRemote = checkedInput(26);
        checkTurnOffDrone = checkedInput(27);
        checkDroneCondition = checkedInput(28);
        checkDisassembly = checkedInput(29);
        checkCopyData = checkedInput(30);
        checkPhotoQLTY = checkedInput(31);

        //Boolean
        checkBodyCond = getCheckedInput(checkInput_BodyCond);
        checkPropellerCond = getCheckedInput(checkInput_PropellerCondition);
        checkPropellerInstalled = getCheckedInput(checkInput_PropellerInstalled);
        checkInstallDroneBatt = getCheckedInput(checkInput_InstallDroneBatt);
        checkRemove2Gimbal = getCheckedInput(checkInput_Remove2Gimbal);
        checkInstallDevice = getCheckedInput(checkInput_InstallDevice);
        checkConnectDevice = getCheckedInput(checkInput_ConnectDevice);
        checkStraightenAntenna = getCheckedInput(checkInput_StraightenAntenna);
        checkSoftware = getCheckedInput(checkInput_Software);
        checkPowerOnRemote = getCheckedInput(checkInput_PowerOnRemote);
        checkPowerOnDrone = getCheckedInput(checkInput_PowerOnDrone);
        checkSDcardInsert = getCheckedInput(checkInput_SDcardInsert);
        checkCameraClean = getCheckedInput(checkInput_CameraClean);
        checkCameraAdjusted = getCheckedInput(checkInput_CameraAdjusted);
        checkGimbalSystem = getCheckedInput(checkInput_GimbalSystem);
        checkCameraWork = getCheckedInput(checkInput_CameraWork);
        checkAOI = getCheckedInput(checkInput_AOI);
        checkHomeIndicator = getCheckedInput(checkInput_HomeIndicator);
        checkFlightMission = getCheckedInput(checkInput_FlightMission);
        checkFlightParameter = getCheckedInput(checkInput_FlightParameter);
        checkAssessRisks = getCheckedInput(checkInput_AssessRisks);
        checkEndMissionAct = getCheckedInput(checkInput_EndMissionAct);
        checkUploadFlightPlan = getCheckedInput(checkInput_UploadFlightPlan);
        checkIMU = getCheckedInput(checkInput_IMU);
        checkSetRTH = getCheckedInput(checkInput_SetRTH);
        checkCountdown = getCheckedInput(checkInput_Countdown);
        checkTurnOffRemote = getCheckedInput(checkInput_TurnOffRemote);
        checkTurnOffDrone = getCheckedInput(checkInput_TurnOffDrone);
        checkDroneCondition = getCheckedInput(checkInput_DroneCondition);
        checkDisassembly = getCheckedInput(checkInput_Disassembly);
        checkCopyData = getCheckedInput(checkInput_CopyData);
        checkPhotoQLTY = getCheckedInput(checkInput_PhotoQLTY);
    }
    public void getAndSetIntentData() {
        //to Update Data
        //Get data from intent
        _id = getIntent().getStringExtra("id");
        index = getIntent().getStringExtra("index");

        getOperationGson(_id);
        GsonAssessment gsonAssessment = gson.fromJson(assessment_gson, GsonAssessment.class); //Object

        flights = gsonAssessment.getFlights(); //Object
        String flight_gson = gson.toJson(flights.get(Integer.parseInt(index))); //String
        GsonFlight gsonFlight = gson.fromJson(flight_gson, GsonFlight.class); //Object

        //Set XML
        //EditText
        inputRemoteBattery.setText(String.valueOf(gsonFlight.getRemoteBattery()));
        inputDroneBattery.setText(String.valueOf(gsonFlight.getDroneBattery()));
        inputDeviceBatt.setText(String.valueOf(gsonFlight.getDeviceBatt()));
        inputMissionDuration.setText(String.valueOf(gsonFlight.getMissionDuration()));
        inputLiPoBatt.setText(String.valueOf(gsonFlight.getLiPoBatt()));
        inputTelSignal.setText(String.valueOf(gsonFlight.getTelSignal()));
        inputGPSsatellite.setText(String.valueOf(gsonFlight.getGpsSatellite()));
        inputTotalFlight.setText(String.valueOf(gsonFlight.getTotalFlight()));
        inputDroneBattPost.setText(String.valueOf(gsonFlight.getDroneBattPost()));
        inputPhotos.setText(String.valueOf(gsonFlight.getPhotos()));

        //CheckedTextView
        checkInput_BodyCond.setChecked(gsonFlight.isCheckBodyCond());
        checkInput_PropellerCondition.setChecked(gsonFlight.isCheckPropellerCond());
        checkInput_PropellerInstalled.setChecked(gsonFlight.isCheckPropellerInstalled());
        checkInput_InstallDroneBatt.setChecked(gsonFlight.isCheckInstallDroneBatt());
        checkInput_Remove2Gimbal.setChecked(gsonFlight.isCheckRemove2Gimbal());
        checkInput_InstallDevice.setChecked(gsonFlight.isCheckInstallDevice());
        checkInput_ConnectDevice.setChecked(gsonFlight.isCheckConnectDevice());
        checkInput_StraightenAntenna.setChecked(gsonFlight.isCheckStraightenAntenna());
        checkInput_Software.setChecked(gsonFlight.isCheckSoftware());
        checkInput_PowerOnRemote.setChecked(gsonFlight.isCheckPowerOnRemote());
        checkInput_PowerOnDrone.setChecked(gsonFlight.isCheckPowerOnDrone());
        checkInput_SDcardInsert.setChecked(gsonFlight.isCheckSDcardInsert());
        checkInput_CameraClean.setChecked(gsonFlight.isCheckCameraClean());
        checkInput_CameraAdjusted.setChecked(gsonFlight.isCheckCameraAdjusted());
        checkInput_GimbalSystem.setChecked(gsonFlight.isCheckGimbalSystem());
        checkInput_CameraWork.setChecked(gsonFlight.isCheckCameraWork());
        checkInput_AOI.setChecked(gsonFlight.isCheckAOI());
        checkInput_HomeIndicator.setChecked(gsonFlight.isCheckHomeIndicator());
        checkInput_FlightMission.setChecked(gsonFlight.isCheckFlightMission());
        checkInput_FlightParameter.setChecked(gsonFlight.isCheckFlightParameter());
        checkInput_AssessRisks.setChecked(gsonFlight.isCheckAssessRisks());
        checkInput_EndMissionAct.setChecked(gsonFlight.isCheckEndMissionAct());
        checkInput_UploadFlightPlan.setChecked(gsonFlight.isCheckUploadFlightPlan());
        checkInput_IMU.setChecked(gsonFlight.isCheckIMU());
        checkInput_SetRTH.setChecked(gsonFlight.isCheckSetRTH());
        checkInput_Countdown.setChecked(gsonFlight.isCheckCountdown());
        checkInput_TurnOffRemote.setChecked(gsonFlight.isCheckTurnOffRemote());
        checkInput_TurnOffDrone.setChecked(gsonFlight.isCheckTurnOffDrone());
        checkInput_DroneCondition.setChecked(gsonFlight.isCheckDroneCondition());
        checkInput_Disassembly.setChecked(gsonFlight.isCheckDisassembly());
        checkInput_CopyData.setChecked(gsonFlight.isCheckCopyData());
        checkInput_PhotoQLTY.setChecked(gsonFlight.isCheckPhotoQLTY());
    }

    //Read Data from Database
    public void getOperationGson(String id){
        DatabaseSchema myDB = new DatabaseSchema(UpdateChecklistActivity.this);
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

    //Set on click listener checklist
    public void checkedTextViewOnClickListener(){
        checklistInputs = new ChecklistInput[32];//total array
        //PreFlight
        checkInput_BodyCond = findViewById(R.id.checkInput_BodyCond);
        checklistInputs[0] = new ChecklistInput(checkBodyCond);
        isCheckedInput(checkInput_BodyCond,0);

        checkInput_PropellerCondition = findViewById(R.id.checkInput_PropellerCondition);
        checklistInputs[1] = new ChecklistInput(checkPropellerCond);
        isCheckedInput(checkInput_PropellerCondition,1);

        checkInput_PropellerInstalled = findViewById(R.id.checkInput_PropellerInstalled);
        checklistInputs[2] = new ChecklistInput(checkPropellerInstalled);
        isCheckedInput(checkInput_PropellerInstalled,2);

        checkInput_InstallDroneBatt = findViewById(R.id.checkInput_InstallDroneBatt);
        checklistInputs[3] = new ChecklistInput(checkInstallDroneBatt);
        isCheckedInput(checkInput_InstallDroneBatt,3);

        checkInput_Remove2Gimbal = findViewById(R.id.checkInput_Remove2Gimbal);
        checklistInputs[4] = new ChecklistInput(checkRemove2Gimbal);
        isCheckedInput(checkInput_Remove2Gimbal,4);

        checkInput_InstallDevice = findViewById(R.id.checkInput_InstallDevice);
        checklistInputs[5] = new ChecklistInput(checkInstallDevice);
        isCheckedInput(checkInput_InstallDevice,5);

        checkInput_ConnectDevice = findViewById(R.id.checkInput_ConnectDevice);
        checklistInputs[6] = new ChecklistInput(checkConnectDevice);
        isCheckedInput(checkInput_ConnectDevice,6);

        checkInput_StraightenAntenna = findViewById(R.id.checkInput_StraightenAntenna);
        checklistInputs[7] = new ChecklistInput(checkStraightenAntenna);
        isCheckedInput(checkInput_StraightenAntenna,7);

        checkInput_Software = findViewById(R.id.checkInput_Software);
        checklistInputs[8] = new ChecklistInput(checkSoftware);
        isCheckedInput(checkInput_Software,8);

        checkInput_PowerOnRemote = findViewById(R.id.checkInput_PowerOnRemote);
        checklistInputs[9] = new ChecklistInput(checkPowerOnRemote);
        isCheckedInput(checkInput_PowerOnRemote,9);

        checkInput_PowerOnDrone = findViewById(R.id.checkInput_PowerOnDrone);
        checklistInputs[10] = new ChecklistInput(checkPowerOnDrone);
        isCheckedInput(checkInput_PowerOnDrone,10);

        checkInput_SDcardInsert = findViewById(R.id.checkInput_SDcardInsert);
        checklistInputs[11] = new ChecklistInput(checkSDcardInsert);
        isCheckedInput(checkInput_SDcardInsert,11);

        checkInput_CameraClean = findViewById(R.id.checkInput_CameraClean);
        checklistInputs[12] = new ChecklistInput(checkCameraClean);
        isCheckedInput(checkInput_CameraClean,12);

        checkInput_CameraAdjusted = findViewById(R.id.checkInput_CameraAdjusted);
        checklistInputs[13] = new ChecklistInput(checkCameraAdjusted);
        isCheckedInput(checkInput_CameraAdjusted,13);

        checkInput_GimbalSystem = findViewById(R.id.checkInput_GimbalSystem);
        checklistInputs[14] = new ChecklistInput(checkGimbalSystem);
        isCheckedInput(checkInput_GimbalSystem,14);

        checkInput_CameraWork = findViewById(R.id.checkInput_CameraWork);
        checklistInputs[15] = new ChecklistInput(checkCameraWork);
        isCheckedInput(checkInput_CameraWork,15);

        checkInput_AOI = findViewById(R.id.checkInput_AOI);
        checklistInputs[16] = new ChecklistInput(checkAOI);
        isCheckedInput(checkInput_AOI,16);

        checkInput_HomeIndicator = findViewById(R.id.checkInput_HomeIndicator);
        checklistInputs[17] = new ChecklistInput(checkHomeIndicator);
        isCheckedInput(checkInput_HomeIndicator,17);

        checkInput_FlightMission = findViewById(R.id.checkInput_FlightMission);
        checklistInputs[18] = new ChecklistInput(checkFlightMission);
        isCheckedInput(checkInput_FlightMission,18);

        checkInput_FlightParameter = findViewById(R.id.checkInput_FlightParameter);
        checklistInputs[19] = new ChecklistInput(checkFlightParameter);
        isCheckedInput(checkInput_FlightParameter,19);

        checkInput_AssessRisks = findViewById(R.id.checkInput_AssessRisks);
        checklistInputs[20] = new ChecklistInput(checkAssessRisks);
        isCheckedInput(checkInput_AssessRisks,20);

        checkInput_EndMissionAct = findViewById(R.id.checkInput_EndMissionAct);
        checklistInputs[21] = new ChecklistInput(checkEndMissionAct);
        isCheckedInput(checkInput_EndMissionAct,21);

        checkInput_UploadFlightPlan = findViewById(R.id.checkInput_UploadFlightPlan);
        checklistInputs[22] = new ChecklistInput(checkUploadFlightPlan);
        isCheckedInput(checkInput_UploadFlightPlan,22);

        checkInput_IMU = findViewById(R.id.checkInput_IMU);
        checklistInputs[23] = new ChecklistInput(checkIMU);
        isCheckedInput(checkInput_IMU,23);

        checkInput_SetRTH = findViewById(R.id.checkInput_SetRTH);
        checklistInputs[24] = new ChecklistInput(checkSetRTH);
        isCheckedInput(checkInput_SetRTH,24);

        checkInput_Countdown = findViewById(R.id.checkInput_Countdown);
        checklistInputs[25] = new ChecklistInput(checkCountdown);
        isCheckedInput(checkInput_Countdown,25);

        //PostFlight
        checkInput_TurnOffRemote = findViewById(R.id.checkInput_TurnOffRemote);
        checklistInputs[26] = new ChecklistInput(checkTurnOffRemote);
        isCheckedInput(checkInput_TurnOffRemote,26);

        checkInput_TurnOffDrone = findViewById(R.id.checkInput_TurnOffDrone);
        checklistInputs[27] = new ChecklistInput(checkTurnOffDrone);
        isCheckedInput(checkInput_TurnOffDrone,27);

        checkInput_DroneCondition = findViewById(R.id.checkInput_DroneCondition);
        checklistInputs[28] = new ChecklistInput(checkDroneCondition);
        isCheckedInput(checkInput_DroneCondition,28);

        checkInput_Disassembly = findViewById(R.id.checkInput_Disassembly);
        checklistInputs[29] = new ChecklistInput(checkDisassembly);
        isCheckedInput(checkInput_Disassembly,29);

        checkInput_CopyData = findViewById(R.id.checkInput_CopyData);
        checklistInputs[30] = new ChecklistInput(checkCopyData);
        isCheckedInput(checkInput_CopyData,30);

        checkInput_PhotoQLTY = findViewById(R.id.checkInput_PhotoQLTY);
        checklistInputs[31] = new ChecklistInput(checkPhotoQLTY);
        isCheckedInput(checkInput_PhotoQLTY,31);
    }

    public void isCheckedInput(CheckedTextView checkedTextView,int i){
        checkedTextView.setOnClickListener(view -> {
            if (checkedTextView.isChecked()){
                checkedTextView.setChecked(false);
                checklistInputs[i].setCheckedInput(false);
            }else{
                checkedTextView.setChecked(true);
                checklistInputs[i].setCheckedInput(true);
            }
        });
    }

    public boolean getCheckedInput(CheckedTextView checkedTextView){
        boolean i;
        if (checkedTextView.isChecked()){
            i = true;
        }else {
            i = false;
        }
        return i;
    }

    public boolean checkedInput(int i){
        return checklistInputs[i].isCheckedInput();
    }

    //Expand Detail
    public void expand(TextView textView, LinearLayout linearLayout) {
        textView.setOnClickListener(view -> {
            if (linearLayout.getVisibility() == View.GONE){
                linearLayout.setVisibility(View.VISIBLE);
            }else{
                linearLayout.setVisibility(View.GONE);
            }
        });
    }

    //TimePicker
    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        calendar.set(Calendar.HOUR, hour);
        calendar.set(Calendar.MINUTE, minute);
        if (isFromClicked == 0) {
            textTakeOffTime.setText(new StringBuilder(pad(hour)).append(":").append(pad(minute)));
            takeOffTimeHour = hour;
            takeOffTimeMin = minute;
        }
        if (isFromClicked == 1) {
            textLandingTime.setText(new StringBuilder(pad(hour)).append(":").append(pad(minute)));
            landingTimeHour = hour;
            landingTimeMin = minute;
        }
    }
    //Time format
    private static String pad(int c) {
        if (c >= 10) {
            return String.valueOf(c);
        } else {
            return "0" + c;
        }
    }
    //Count total flight
    public void flightTime(){
        //set totalFlightTime
        deltaHour = (landingTimeHour - takeOffTimeHour) * 60;
        deltaMin = landingTimeMin - takeOffTimeMin;
        if (deltaMin < 0){
            deltaMinute = deltaMin * (-1);
        }else{
            deltaMinute = deltaMin;
        }
        totalFlightTime = deltaHour + deltaMinute;
        inputTotalFlight.setText(String.valueOf(totalFlightTime));
    }
}