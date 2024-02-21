package com.example.thesis;

public class GsonFlight {
    private boolean checkBodyCond, checkPropellerCond,
            checkPropellerInstalled, checkInstallDroneBatt, checkRemove2Gimbal, checkInstallDevice,
            checkConnectDevice, checkStraightenAntenna, checkSoftware, checkPowerOnRemote,
            checkPowerOnDrone, checkSDcardInsert, checkCameraClean, checkCameraAdjusted,
            checkGimbalSystem, checkCameraWork, checkAOI, checkHomeIndicator,
            checkFlightMission, checkFlightParameter, checkAssessRisks, checkEndMissionAct,
            checkUploadFlightPlan, checkIMU, checkSetRTH, checkCountdown,
            checkTurnOffRemote, checkTurnOffDrone, checkDroneCondition, checkDisassembly,
            checkCopyData, checkPhotoQLTY;
    private int remoteBattery, droneBattery, deviceBatt, missionDuration, liPoBatt,
            telSignal, gpsSatellite, totalFlight, droneBattPost, photos;
    private long totalFlightTime;

    public GsonFlight(boolean checkBodyCond, boolean checkPropellerCond, boolean checkPropellerInstalled,
                      boolean checkInstallDroneBatt, boolean checkRemove2Gimbal, boolean checkInstallDevice,
                      boolean checkConnectDevice, boolean checkStraightenAntenna, boolean checkSoftware,
                      boolean checkPowerOnRemote, boolean checkPowerOnDrone, boolean checkSDcardInsert,
                      boolean checkCameraClean, boolean checkCameraAdjusted, boolean checkGimbalSystem,
                      boolean checkCameraWork, boolean checkAOI, boolean checkHomeIndicator,
                      boolean checkFlightMission, boolean checkFlightParameter, boolean checkAssessRisks,
                      boolean checkEndMissionAct, boolean checkUploadFlightPlan, boolean checkIMU,
                      boolean checkSetRTH, boolean checkCountdown, boolean checkTurnOffRemote,
                      boolean checkTurnOffDrone, boolean checkDroneCondition, boolean checkDisassembly,
                      boolean checkCopyData, boolean checkPhotoQLTY, int remoteBattery, int droneBattery,
                      int deviceBatt, int missionDuration, int liPoBatt, int telSignal, int gpsSatellite,
                      int totalFlight, int droneBattPost, int photos, long totalFlightTime) {
        this.checkBodyCond = checkBodyCond;
        this.checkPropellerCond = checkPropellerCond;
        this.checkPropellerInstalled = checkPropellerInstalled;
        this.checkInstallDroneBatt = checkInstallDroneBatt;
        this.checkRemove2Gimbal = checkRemove2Gimbal;
        this.checkInstallDevice = checkInstallDevice;
        this.checkConnectDevice = checkConnectDevice;
        this.checkStraightenAntenna = checkStraightenAntenna;
        this.checkSoftware = checkSoftware;
        this.checkPowerOnRemote = checkPowerOnRemote;
        this.checkPowerOnDrone = checkPowerOnDrone;
        this.checkSDcardInsert = checkSDcardInsert;
        this.checkCameraClean = checkCameraClean;
        this.checkCameraAdjusted = checkCameraAdjusted;
        this.checkGimbalSystem = checkGimbalSystem;
        this.checkCameraWork = checkCameraWork;
        this.checkAOI = checkAOI;
        this.checkHomeIndicator = checkHomeIndicator;
        this.checkFlightMission = checkFlightMission;
        this.checkFlightParameter = checkFlightParameter;
        this.checkAssessRisks = checkAssessRisks;
        this.checkEndMissionAct = checkEndMissionAct;
        this.checkUploadFlightPlan = checkUploadFlightPlan;
        this.checkIMU = checkIMU;
        this.checkSetRTH = checkSetRTH;
        this.checkCountdown = checkCountdown;
        this.checkTurnOffRemote = checkTurnOffRemote;
        this.checkTurnOffDrone = checkTurnOffDrone;
        this.checkDroneCondition = checkDroneCondition;
        this.checkDisassembly = checkDisassembly;
        this.checkCopyData = checkCopyData;
        this.checkPhotoQLTY = checkPhotoQLTY;
        this.remoteBattery = remoteBattery;
        this.droneBattery = droneBattery;
        this.deviceBatt = deviceBatt;
        this.missionDuration = missionDuration;
        this.liPoBatt = liPoBatt;
        this.telSignal = telSignal;
        this.gpsSatellite = gpsSatellite;
        this.totalFlight = totalFlight;
        this.droneBattPost = droneBattPost;
        this.photos = photos;
        this.totalFlightTime = totalFlightTime;
    }

    public boolean isCheckBodyCond() {
        return checkBodyCond;
    }

    public boolean isCheckPropellerCond() {
        return checkPropellerCond;
    }

    public boolean isCheckPropellerInstalled() {
        return checkPropellerInstalled;
    }

    public boolean isCheckInstallDroneBatt() {
        return checkInstallDroneBatt;
    }

    public boolean isCheckRemove2Gimbal() {
        return checkRemove2Gimbal;
    }

    public boolean isCheckInstallDevice() {
        return checkInstallDevice;
    }

    public boolean isCheckConnectDevice() {
        return checkConnectDevice;
    }

    public boolean isCheckStraightenAntenna() {
        return checkStraightenAntenna;
    }

    public boolean isCheckSoftware() {
        return checkSoftware;
    }

    public boolean isCheckPowerOnRemote() {
        return checkPowerOnRemote;
    }

    public boolean isCheckPowerOnDrone() {
        return checkPowerOnDrone;
    }

    public boolean isCheckSDcardInsert() {
        return checkSDcardInsert;
    }

    public boolean isCheckCameraClean() {
        return checkCameraClean;
    }

    public boolean isCheckCameraAdjusted() {
        return checkCameraAdjusted;
    }

    public boolean isCheckGimbalSystem() {
        return checkGimbalSystem;
    }

    public boolean isCheckCameraWork() {
        return checkCameraWork;
    }

    public boolean isCheckAOI() {
        return checkAOI;
    }

    public boolean isCheckHomeIndicator() {
        return checkHomeIndicator;
    }

    public boolean isCheckFlightMission() {
        return checkFlightMission;
    }

    public boolean isCheckFlightParameter() {
        return checkFlightParameter;
    }

    public boolean isCheckAssessRisks() {
        return checkAssessRisks;
    }

    public boolean isCheckEndMissionAct() {
        return checkEndMissionAct;
    }

    public boolean isCheckUploadFlightPlan() {
        return checkUploadFlightPlan;
    }

    public boolean isCheckIMU() {
        return checkIMU;
    }

    public boolean isCheckSetRTH() {
        return checkSetRTH;
    }

    public boolean isCheckCountdown() {
        return checkCountdown;
    }

    public boolean isCheckTurnOffRemote() {
        return checkTurnOffRemote;
    }

    public boolean isCheckTurnOffDrone() {
        return checkTurnOffDrone;
    }

    public boolean isCheckDroneCondition() {
        return checkDroneCondition;
    }

    public boolean isCheckDisassembly() {
        return checkDisassembly;
    }

    public boolean isCheckCopyData() {
        return checkCopyData;
    }

    public boolean isCheckPhotoQLTY() {
        return checkPhotoQLTY;
    }

    public int getRemoteBattery() {
        return remoteBattery;
    }

    public int getDroneBattery() {
        return droneBattery;
    }

    public int getDeviceBatt() {
        return deviceBatt;
    }

    public int getMissionDuration() {
        return missionDuration;
    }

    public int getLiPoBatt() {
        return liPoBatt;
    }

    public int getTelSignal() {
        return telSignal;
    }

    public int getGpsSatellite() {
        return gpsSatellite;
    }

    public int getTotalFlight() {
        return totalFlight;
    }

    public int getDroneBattPost() {
        return droneBattPost;
    }

    public int getPhotos() {
        return photos;
    }

    public long getTotalFlightTime() {
        return totalFlightTime;
    }
}
