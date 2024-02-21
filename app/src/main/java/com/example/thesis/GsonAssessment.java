package com.example.thesis;

import java.util.ArrayList;

public class GsonAssessment {
    private String flightDate, pilotName, coPilotName, projectCode, mission, missionOther, lTelephone,
            lEmail, medicalFacility, securityFacility, address, riskOther, mitigations,
            windSpeed, windDirection, generalForecast, sunriseTime, sunsetTime, note;

    private boolean lPermission, vehicleAccess;
    private ArrayList<String> riskItems = new ArrayList<>();
    private ArrayList<GsonFlight> flights = new ArrayList<>();

    public GsonAssessment(String flightDate, String pilotName, String coPilotName,
                          String projectCode, String mission, String missionOther, String lTelephone,
                          String lEmail, String medicalFacility, String securityFacility, String address,
                          String riskOther, String mitigations, String windSpeed, String windDirection,
                          String generalForecast, String sunriseTime, String sunsetTime, String note,
                          boolean lPermission, boolean vehicleAccess,
                          ArrayList<String> riskItems,
                          ArrayList<GsonFlight> flights){
        this.flightDate = flightDate;
        this.pilotName = pilotName;
        this.coPilotName = coPilotName;
        this.projectCode = projectCode;
        this.mission = mission;
        this.missionOther = missionOther;
        this.lTelephone = lTelephone;
        this.lEmail = lEmail;
        this.medicalFacility = medicalFacility;
        this.securityFacility = securityFacility;
        this.address = address;
        this.riskOther = riskOther;
        this.mitigations = mitigations;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.generalForecast = generalForecast;
        this.sunriseTime = sunriseTime;
        this.sunsetTime = sunsetTime;
        this.note = note;
        this.lPermission = lPermission;
        this.vehicleAccess = vehicleAccess;
        this.riskItems = riskItems;
        this.flights = flights;
    }

    //Get Data
    public String getFlightDate() {
        return flightDate;
    }

    public String getPilotName() {
        return pilotName;
    }

    public String getCoPilotName() {
        return coPilotName;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public String getMission() {
        return mission;
    }

    public String getMissionOther() {
        return missionOther;
    }

    public String getlTelephone() {
        return lTelephone;
    }

    public String getlEmail() {
        return lEmail;
    }

    public String getMedicalFacility() {
        return medicalFacility;
    }

    public String getSecurityFacility() {
        return securityFacility;
    }

    public String getAddress() {
        return address;
    }

    public String getRiskOther() {
        return riskOther;
    }

    public String getMitigations() {
        return mitigations;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public String getGeneralForecast() {
        return generalForecast;
    }

    public String getSunriseTime() {
        return sunriseTime;
    }

    public String getSunsetTime() {
        return sunsetTime;
    }

    public String getNote() {
        return note;
    }

    public boolean islPermission() {
        return lPermission;
    }

    public boolean isVehicleAccess() {
        return vehicleAccess;
    }

    public ArrayList<String> getRiskItems() {
        return riskItems;
    }

    public ArrayList<GsonFlight> getFlights() {
        return flights;
    }

    public void setFlights(ArrayList<GsonFlight> flights) {
        this.flights = flights;
    }
}
