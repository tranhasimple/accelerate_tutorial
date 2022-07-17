package com.example.accelerate_tutorial;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Data {
//    @JsonProperty("x")
    private double valueX;

//    @JsonProperty("y")
    private double valueY;

//    @JsonProperty("z")
    private double valueZ;

    private String timestamp;

    public Data() {
    }

    public Data(double valueX, double valueY, double valueZ, String timestamp) {
        this.valueX = valueX;
        this.valueY = valueY;
        this.valueZ = valueZ;
        this.timestamp = timestamp;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public double getValueX() {
        return valueX;
    }

    public void setValueX(double valueX) {
        this.valueX = valueX;
    }

    public double getValueY() {
        return valueY;
    }

    public void setValueY(double valueY) {
        this.valueY = valueY;
    }

    public double getValueZ() {
        return valueZ;
    }

    public void setValueZ(double valueZ) {
        this.valueZ = valueZ;
    }

    @Override
    public String toString() {
        return "Data{" +
                "valueX=" + valueX +
                ", valueY=" + valueY +
                ", valueZ=" + valueZ +
                '}';
    }
}
