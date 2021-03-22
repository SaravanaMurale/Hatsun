package com.sosaley.hatsun.model;

import com.google.gson.annotations.SerializedName;

public class ValidateBatteryDTO extends BaseDTO{

    @SerializedName("batteryid")
    private String batteryId;
    @SerializedName("clientname")
    private String clientName;
    @SerializedName("plantname")
    private String plantName;
    @SerializedName("batteryroom")
    private String batteryRoom;
    @SerializedName("ups")
    private String ups;
    @SerializedName("rackid")
    private String rackId;
    @SerializedName("slaveid")
    private String slaveId;
    @SerializedName("slavetype")
    private String slaveType;

    public ValidateBatteryDTO(String ups, String rackId, String slaveId, String slaveType) {
        this.ups = ups;
        this.rackId = rackId;
        this.slaveId = slaveId;
        this.slaveType = slaveType;
    }

    public ValidateBatteryDTO(String batteryId, String clientName, String plantName, String batteryRoom, String ups, String rackId, String slaveId, String slaveType) {
        this.batteryId = batteryId;
        this.clientName = clientName;
        this.plantName = plantName;
        this.batteryRoom = batteryRoom;
        this.ups = ups;
        this.rackId = rackId;
        this.slaveId = slaveId;
        this.slaveType = slaveType;
    }

    public String getBatteryId() {
        return batteryId;
    }

    public void setBatteryId(String batteryId) {
        this.batteryId = batteryId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getBatteryRoom() {
        return batteryRoom;
    }

    public void setBatteryRoom(String batteryRoom) {
        this.batteryRoom = batteryRoom;
    }

    public String getUps() {
        return ups;
    }

    public void setUps(String ups) {
        this.ups = ups;
    }

    public String getRackId() {
        return rackId;
    }

    public void setRackId(String rackId) {
        this.rackId = rackId;
    }

    public String getSlaveId() {
        return slaveId;
    }

    public void setSlaveId(String slaveId) {
        this.slaveId = slaveId;
    }

    public String getSlaveType() {
        return slaveType;
    }

    public void setSlaveType(String slaveType) {
        this.slaveType = slaveType;
    }
}
