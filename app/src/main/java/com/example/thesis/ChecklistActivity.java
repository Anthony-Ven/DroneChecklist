package com.example.thesis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
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

public class ChecklistActivity extends AppCompatActivity
        implements
        TimePickerDialog.OnTimeSetListener //TimePicker
{
    ChecklistInput[] checklistInputs;
    //Database Related
    DatabaseSchema myDB;
    //GSON
    Gson gson = new Gson();
    String _id, flights_gson; // For Update

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
    Button buttonInputTakeOffTime, buttonInputLandingTime,buttonSave;

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
        setContentView(R.layout.activity_checklist);

        myDB = new DatabaseSchema(ChecklistActivity.this);

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
        buttonSave = findViewById(R.id.buttonSave);

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

        //buttonSave
        buttonSave.setOnClickListener(view -> {
            String json;
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

                //Put Intent Data
                Intent intent = new Intent();
                json = gson.toJson(gsonFlight);//from object to json string
                intent.putExtra("flights", json);
                setResult(RESULT_OK, intent);

                //Exception
                Toast.makeText(ChecklistActivity.this, "Success", Toast.LENGTH_LONG).show();
            }catch (Exception e){
                Toast.makeText(ChecklistActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
            //Finish intent and send result
            finish();
        });
    }

    //Function
    public void getInputData(){
        //Get Input Data
        //EditText
        String RemoteBattery = String.valueOf(inputRemoteBattery.getText());
        String DroneBattery = String.valueOf(inputDroneBattery.getText());
        String DeviceBatt = String.valueOf(inputDeviceBatt.getText());
        String MissionDuration = String.valueOf(inputMissionDuration.getText());
        String LiPoBatt = String.valueOf(inputLiPoBatt.getText());
        String TelSignal = String.valueOf(inputTelSignal.getText());
        String GPSsatellite = String.valueOf(inputGPSsatellite.getText());
        String TotalFlight = String.valueOf(inputTotalFlight.getText());
        String DroneBattPost = String.valueOf(inputDroneBattPost.getText());
        String Photos = String.valueOf(inputPhotos.getText());
        if (RemoteBattery.equals("")) {
            remoteBattery = 0;
        }else {
            remoteBattery = Integer.parseInt(inputRemoteBattery.getText().toString().trim());
        }
        if (DroneBattery.equals("")){
            droneBattery = 0;
        }else {
            droneBattery = Integer.parseInt(inputDroneBattery.getText().toString().trim());
        }
        if (DeviceBatt.equals("")) {
            deviceBatt = 0;
        }else {
            deviceBatt = Integer.parseInt(inputDeviceBatt.getText().toString().trim());
        }
        if (MissionDuration.equals("")) {
            missionDuration = 0;
        }else {
            missionDuration = Integer.parseInt(inputMissionDuration.getText().toString().trim());
        }
        if (LiPoBatt.equals("")) {
            liPoBatt = 0;
        }else {
            liPoBatt = Integer.parseInt(inputLiPoBatt.getText().toString().trim());
        }
        if (TelSignal.equals("")) {
            telSignal = 0;
        }else {
            telSignal = Integer.parseInt(inputTelSignal.getText().toString().trim());
        }
        if (GPSsatellite.equals("")) {
            gpsSatellite = 0;
        }else {
            gpsSatellite = Integer.parseInt(inputGPSsatellite.getText().toString().trim());
        }
        if (TotalFlight.equals("")) {
            totalFlight = 0;
        }else {
            totalFlight = Integer.parseInt(inputTotalFlight.getText().toString().trim());
        }
        if (DroneBattPost.equals("")) {
            droneBattPost = 0;
        }else {
            droneBattPost = Integer.parseInt(inputDroneBattPost.getText().toString().trim());
        }
        if (Photos.equals("")) {
            photos = 0;
        }else {
            photos = Integer.parseInt(inputPhotos.getText().toString().trim());
        }

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