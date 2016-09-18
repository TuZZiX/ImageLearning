package edu.cwru.sail.imagelearning.Entity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by tianshipei on 16/9/18.
 */
public class GradingTable {
    private ArrayList<Grading> gradings;

    public GradingTable() {
        gradings = new ArrayList<>();
    }

    public void overideAdd (String DIRECTORY) {


    }

    public int find (String DIRECTORY) {
        for (int it = gradings.size() - 1; it >= 0; it--) {
            if (gradings.get(it).getDIRECTORY().compareTo(DIRECTORY) == 0) {
                return it;
            }
        }
        return -1;
    }

    public void add(String DIRECTORY,
                    Date TimeStamp,
                    int SMILE_LEVEL,
                    double TYPE_ACCELEROMETER_X,
                    double TYPE_ACCELEROMETER_Y,
                    double TYPE_ACCELEROMETER_Z,
                    double TYPE_MAGNETIC_FIELD_X,
                    double TYPE_MAGNETIC_FIELD_Y,
                    double TYPE_MAGNETIC_FIELD_Z,
                    double TYPE_GYROSCOPE_X,
                    double TYPE_GYROSCOPE_Y,
                    double TYPE_GYROSCOPE_Z,
                    double TYPE_ROTATION_VECTOR_X,
                    double TYPE_ROTATION_VECTOR_Y,
                    double TYPE_ROTATION_VECTOR_Z,
                    double TYPE_LINEAR_ACCELERATION_X,
                    double TYPE_LINEAR_ACCELERATION_Y,
                    double TYPE_LINEAR_ACCELERATION_Z,
                    double TYPE_GRAVITY_X,
                    double TYPE_GRAVITY_Y,
                    double TYPE_GRAVITY_Z) {
        DateFormat df =  new SimpleDateFormat("yyyy.MM.dd E HH:mm:ss a zzz");
        String date = df.format(TimeStamp);
        add(DIRECTORY, date, SMILE_LEVEL,
                TYPE_ACCELEROMETER_X,
                TYPE_ACCELEROMETER_Y,
                TYPE_ACCELEROMETER_Z,
                TYPE_MAGNETIC_FIELD_X,
                TYPE_MAGNETIC_FIELD_Y,
                TYPE_MAGNETIC_FIELD_Z,
                TYPE_GYROSCOPE_X,
                TYPE_GYROSCOPE_Y,
                TYPE_GYROSCOPE_Z,
                TYPE_ROTATION_VECTOR_X,
                TYPE_ROTATION_VECTOR_Y,
                TYPE_ROTATION_VECTOR_Z,
                TYPE_LINEAR_ACCELERATION_X,
                TYPE_LINEAR_ACCELERATION_Y,
                TYPE_LINEAR_ACCELERATION_Z,
                TYPE_GRAVITY_X,
                TYPE_GRAVITY_Y,
                TYPE_GRAVITY_Z);
    }

    public void add(String DIRECTORY,
                    String TimeStamp,
                    int SMILE_LEVEL,
                    double TYPE_ACCELEROMETER_X,
                    double TYPE_ACCELEROMETER_Y,
                    double TYPE_ACCELEROMETER_Z,
                    double TYPE_MAGNETIC_FIELD_X,
                    double TYPE_MAGNETIC_FIELD_Y,
                    double TYPE_MAGNETIC_FIELD_Z,
                    double TYPE_GYROSCOPE_X,
                    double TYPE_GYROSCOPE_Y,
                    double TYPE_GYROSCOPE_Z,
                    double TYPE_ROTATION_VECTOR_X,
                    double TYPE_ROTATION_VECTOR_Y,
                    double TYPE_ROTATION_VECTOR_Z,
                    double TYPE_LINEAR_ACCELERATION_X,
                    double TYPE_LINEAR_ACCELERATION_Y,
                    double TYPE_LINEAR_ACCELERATION_Z,
                    double TYPE_GRAVITY_X,
                    double TYPE_GRAVITY_Y,
                    double TYPE_GRAVITY_Z) {
        Grading singleRecord = new Grading();
        singleRecord.setDIRECTORY(DIRECTORY);
        singleRecord.setTimeStamp(TimeStamp);
        singleRecord.setSMILE_LEVEL(SMILE_LEVEL);
        singleRecord.setTYPE_ACCELEROMETER_X(TYPE_ACCELEROMETER_X);
        singleRecord.setTYPE_ACCELEROMETER_Y(TYPE_ACCELEROMETER_Y);
        singleRecord.setTYPE_ACCELEROMETER_Z(TYPE_ACCELEROMETER_Z);
        singleRecord.setTYPE_MAGNETIC_FIELD_X(TYPE_MAGNETIC_FIELD_X);
        singleRecord.setTYPE_MAGNETIC_FIELD_Y(TYPE_MAGNETIC_FIELD_Y);
        singleRecord.setTYPE_MAGNETIC_FIELD_Z(TYPE_MAGNETIC_FIELD_Z);
        singleRecord.setTYPE_GYROSCOPE_X(TYPE_GYROSCOPE_X);
        singleRecord.setTYPE_GYROSCOPE_Y(TYPE_GYROSCOPE_Y);
        singleRecord.setTYPE_GYROSCOPE_Z(TYPE_GYROSCOPE_Z);
        singleRecord.setTYPE_ROTATION_VECTOR_X(TYPE_ROTATION_VECTOR_X);
        singleRecord.setTYPE_ROTATION_VECTOR_Y(TYPE_ROTATION_VECTOR_Y);
        singleRecord.setTYPE_ROTATION_VECTOR_Z(TYPE_ROTATION_VECTOR_Z);
        singleRecord.setTYPE_LINEAR_ACCELERATION_Z(TYPE_LINEAR_ACCELERATION_X);
        singleRecord.setTYPE_LINEAR_ACCELERATION_Y(TYPE_LINEAR_ACCELERATION_Y);
        singleRecord.setTYPE_LINEAR_ACCELERATION_Z(TYPE_LINEAR_ACCELERATION_Z);
        singleRecord.setTYPE_GRAVITY_X(TYPE_GRAVITY_X);
        singleRecord.setTYPE_GRAVITY_Y(TYPE_GRAVITY_Y);
        singleRecord.setTYPE_GRAVITY_Z(TYPE_GRAVITY_Z);
        gradings.add(singleRecord);
    }

    void clear() {
        gradings.clear();
    }

    int size() {
        return gradings.size();
    }

    public ArrayList<Grading> get() {
        return gradings;
    }

    public void set(ArrayList<Grading> gradings) {
        this.gradings = gradings;
    }
}
