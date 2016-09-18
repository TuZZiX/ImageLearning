package edu.cwru.sail.imagelearning.Entity;

import java.util.Map;

/**
 * Created by majunqi0102 on 9/15/16.
 */
public class CSVEntity {
    private double TimeStamp;
    private double TYPE_ACCELEROMETER_X;
    private double TYPE_ACCELEROMETER_Y;
    private double TYPE_ACCELEROMETER_Z;
    private double TYPE_MAGNETIC_FIELD_X;
    private double TYPE_MAGNETIC_FIELD_Y;
    private double TYPE_MAGNETIC_FIELD_Z;
    private double TYPE_GYROSCOPE_X;
    private double TYPE_GYROSCOPE_Y;
    private double TYPE_GYROSCOPE_Z;
    private double TYPE_ROTATION_VECTOR_X;
    private double TYPE_ROTATION_VECTOR_Y;
    private double TYPE_ROTATION_VECTOR_Z;
    private double TYPE_LINEAR_ACCELERATION_X;
    private double TYPE_LINEAR_ACCELERATION_Y;
    private double TYPE_LINEAR_ACCELERATION_Z;
    private double TYPE_GRAVITY_X;
    private double TYPE_GRAVITY_Y;
    private double TYPE_GRAVITY_Z;

    private Map<String, Integer> smile_storage;

    public double getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(double timeStamp) {
        TimeStamp = timeStamp;
    }

    public double getTYPE_ACCELEROMETER_X() {
        return TYPE_ACCELEROMETER_X;
    }

    public void setTYPE_ACCELEROMETER_X(double TYPE_ACCELEROMETER_X) {
        this.TYPE_ACCELEROMETER_X = TYPE_ACCELEROMETER_X;
    }

    public double getTYPE_ACCELEROMETER_Y() {
        return TYPE_ACCELEROMETER_Y;
    }

    public void setTYPE_ACCELEROMETER_Y(double TYPE_ACCELEROMETER_Y) {
        this.TYPE_ACCELEROMETER_Y = TYPE_ACCELEROMETER_Y;
    }

    public double getTYPE_ACCELEROMETER_Z() {
        return TYPE_ACCELEROMETER_Z;
    }

    public void setTYPE_ACCELEROMETER_Z(double TYPE_ACCELEROMETER_Z) {
        this.TYPE_ACCELEROMETER_Z = TYPE_ACCELEROMETER_Z;
    }

    public double getTYPE_MAGNETIC_FIELD_X() {
        return TYPE_MAGNETIC_FIELD_X;
    }

    public void setTYPE_MAGNETIC_FIELD_X(double TYPE_MAGNETIC_FIELD_X) {
        this.TYPE_MAGNETIC_FIELD_X = TYPE_MAGNETIC_FIELD_X;
    }

    public double getTYPE_MAGNETIC_FIELD_Y() {
        return TYPE_MAGNETIC_FIELD_Y;
    }

    public void setTYPE_MAGNETIC_FIELD_Y(double TYPE_MAGNETIC_FIELD_Y) {
        this.TYPE_MAGNETIC_FIELD_Y = TYPE_MAGNETIC_FIELD_Y;
    }

    public double getTYPE_MAGNETIC_FIELD_Z() {
        return TYPE_MAGNETIC_FIELD_Z;
    }

    public void setTYPE_MAGNETIC_FIELD_Z(double TYPE_MAGNETIC_FIELD_Z) {
        this.TYPE_MAGNETIC_FIELD_Z = TYPE_MAGNETIC_FIELD_Z;
    }

    public double getTYPE_GYROSCOPE_X() {
        return TYPE_GYROSCOPE_X;
    }

    public void setTYPE_GYROSCOPE_X(double TYPE_GYROSCOPE_X) {
        this.TYPE_GYROSCOPE_X = TYPE_GYROSCOPE_X;
    }

    public double getTYPE_GYROSCOPE_Y() {
        return TYPE_GYROSCOPE_Y;
    }

    public void setTYPE_GYROSCOPE_Y(double TYPE_GYROSCOPE_Y) {
        this.TYPE_GYROSCOPE_Y = TYPE_GYROSCOPE_Y;
    }

    public double getTYPE_GYROSCOPE_Z() {
        return TYPE_GYROSCOPE_Z;
    }

    public void setTYPE_GYROSCOPE_Z(double TYPE_GYROSCOPE_Z) {
        this.TYPE_GYROSCOPE_Z = TYPE_GYROSCOPE_Z;
    }

    public double getTYPE_ROTATION_VECTOR_X() {
        return TYPE_ROTATION_VECTOR_X;
    }

    public void setTYPE_ROTATION_VECTOR_X(double TYPE_ROTATION_VECTOR_X) {
        this.TYPE_ROTATION_VECTOR_X = TYPE_ROTATION_VECTOR_X;
    }

    public double getTYPE_ROTATION_VECTOR_Y() {
        return TYPE_ROTATION_VECTOR_Y;
    }

    public void setTYPE_ROTATION_VECTOR_Y(double TYPE_ROTATION_VECTOR_Y) {
        this.TYPE_ROTATION_VECTOR_Y = TYPE_ROTATION_VECTOR_Y;
    }

    public double getTYPE_ROTATION_VECTOR_Z() {
        return TYPE_ROTATION_VECTOR_Z;
    }

    public void setTYPE_ROTATION_VECTOR_Z(double TYPE_ROTATION_VECTOR_Z) {
        this.TYPE_ROTATION_VECTOR_Z = TYPE_ROTATION_VECTOR_Z;
    }

    public double getTYPE_LINEAR_ACCELERATION_X() {
        return TYPE_LINEAR_ACCELERATION_X;
    }

    public void setTYPE_LINEAR_ACCELERATION_X(double TYPE_LINEAR_ACCELERATION_X) {
        this.TYPE_LINEAR_ACCELERATION_X = TYPE_LINEAR_ACCELERATION_X;
    }

    public double getTYPE_LINEAR_ACCELERATION_Y() {
        return TYPE_LINEAR_ACCELERATION_Y;
    }

    public void setTYPE_LINEAR_ACCELERATION_Y(double TYPE_LINEAR_ACCELERATION_Y) {
        this.TYPE_LINEAR_ACCELERATION_Y = TYPE_LINEAR_ACCELERATION_Y;
    }

    public double getTYPE_LINEAR_ACCELERATION_Z() {
        return TYPE_LINEAR_ACCELERATION_Z;
    }

    public void setTYPE_LINEAR_ACCELERATION_Z(double TYPE_LINEAR_ACCELERATION_Z) {
        this.TYPE_LINEAR_ACCELERATION_Z = TYPE_LINEAR_ACCELERATION_Z;
    }

    public double getTYPE_GRAVITY_X() {
        return TYPE_GRAVITY_X;
    }

    public void setTYPE_GRAVITY_X(double TYPE_GRAVITY_X) {
        this.TYPE_GRAVITY_X = TYPE_GRAVITY_X;
    }

    public double getTYPE_GRAVITY_Y() {
        return TYPE_GRAVITY_Y;
    }

    public void setTYPE_GRAVITY_Y(double TYPE_GRAVITY_Y) {
        this.TYPE_GRAVITY_Y = TYPE_GRAVITY_Y;
    }

    public double getTYPE_GRAVITY_Z() {
        return TYPE_GRAVITY_Z;
    }

    public void setTYPE_GRAVITY_Z(double TYPE_GRAVITY_Z) {
        this.TYPE_GRAVITY_Z = TYPE_GRAVITY_Z;
    }

    public Map<String, Integer> getSmile_storage() {
        return smile_storage;
    }

    public void setSmile_storage(Map<String, Integer> smile_storage) {
        this.smile_storage = smile_storage;
    }
}
