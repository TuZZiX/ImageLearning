package edu.cwru.sail.imagelearning.Entity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.TreeMap;

public class GradingTable {
    private ArrayList<Grading> gradingList = new ArrayList<>();

    // add new record and replaceAdd exist record (if exist)
    public void replaceAdd(String DIRECTORY,
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
                           double TYPE_GRAVITY_Z,
                           double POSITION_X,
                           double POSITION_Y,
                           double VELOCITY_X,
                           double VELOCITY_Y,
                           double PRESSURE,
                           double SIZE) {
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd E HH:mm:ss a zzz");
        String date = df.format(TimeStamp);
        replaceAdd(DIRECTORY, date, SMILE_LEVEL,
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
                TYPE_GRAVITY_Z,
                POSITION_X,
                POSITION_Y,
                VELOCITY_X,
                VELOCITY_Y,
                PRESSURE,
                SIZE);
    }

    public void replaceAdd(String DIRECTORY,
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
                           double TYPE_GRAVITY_Z,
                           double POSITION_X,
                           double POSITION_Y,
                           double VELOCITY_X,
                           double VELOCITY_Y,
                           double PRESSURE,
                           double SIZE) {
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
        singleRecord.setTYPE_LINEAR_ACCELERATION_X(TYPE_LINEAR_ACCELERATION_X);
        singleRecord.setTYPE_LINEAR_ACCELERATION_Y(TYPE_LINEAR_ACCELERATION_Y);
        singleRecord.setTYPE_LINEAR_ACCELERATION_Z(TYPE_LINEAR_ACCELERATION_Z);
        singleRecord.setTYPE_GRAVITY_X(TYPE_GRAVITY_X);
        singleRecord.setTYPE_GRAVITY_Y(TYPE_GRAVITY_Y);
        singleRecord.setTYPE_GRAVITY_Z(TYPE_GRAVITY_Z);
        singleRecord.setPOSITION_X(POSITION_X);
        singleRecord.setPOSITION_Y(POSITION_Y);
        singleRecord.setVELOCITY_X(VELOCITY_X);
        singleRecord.setVELOCITY_Y(VELOCITY_Y);
        singleRecord.setPRESSURE(PRESSURE);
        singleRecord.setSIZE(SIZE);
        replaceAdd(singleRecord);
    }

    public void replaceAdd(Grading singleRecord) {
        String dir = singleRecord.getDIRECTORY();
        int index = find(dir);
        if (index >= 0 && index < gradingList.size()) {
            replaceAdd(index, singleRecord);
        } else {
            add(singleRecord);
        }
    }

    public void replaceAdd(int index, Grading singleRecord) {
        gradingList.set(index, singleRecord);
    }

    // only find last index
    public int find(String DIRECTORY) {
        return find(DIRECTORY, 0, gradingList.size() - 1);
    }

    // Note: including start index and end index
    public int find(String DIRECTORY, int start, int end) {
        if (start >= 0 && end >= 0 && start < gradingList.size() && end < gradingList.size()) {
            for (int it = end; it >= start; it--) {
                if (gradingList.get(it).getDIRECTORY().compareTo(DIRECTORY) == 0) {
                    return it;
                }
            }
        }
        return -1;
    }

    // add new record, do not replaceAdd the old one
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
                    double TYPE_GRAVITY_Z,
                    double POSITION_X,
                    double POSITION_Y,
                    double VELOCITY_X,
                    double VELOCITY_Y,
                    double PRESSURE,
                    double SIZE) {
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd E HH:mm:ss a zzz");
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
                TYPE_GRAVITY_Z,
                POSITION_X,
                POSITION_Y,
                VELOCITY_X,
                VELOCITY_Y,
                PRESSURE,
                SIZE);
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
                    double TYPE_GRAVITY_Z,
                    double POSITION_X,
                    double POSITION_Y,
                    double VELOCITY_X,
                    double VELOCITY_Y,
                    double PRESSURE,
                    double SIZE) {
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
        singleRecord.setTYPE_LINEAR_ACCELERATION_X(TYPE_LINEAR_ACCELERATION_X);
        singleRecord.setTYPE_LINEAR_ACCELERATION_Y(TYPE_LINEAR_ACCELERATION_Y);
        singleRecord.setTYPE_LINEAR_ACCELERATION_Z(TYPE_LINEAR_ACCELERATION_Z);
        singleRecord.setTYPE_GRAVITY_X(TYPE_GRAVITY_X);
        singleRecord.setTYPE_GRAVITY_Y(TYPE_GRAVITY_Y);
        singleRecord.setTYPE_GRAVITY_Z(TYPE_GRAVITY_Z);
        singleRecord.setPOSITION_X(POSITION_X);
        singleRecord.setPOSITION_Y(POSITION_Y);
        singleRecord.setVELOCITY_X(VELOCITY_X);
        singleRecord.setVELOCITY_Y(VELOCITY_Y);
        singleRecord.setPRESSURE(PRESSURE);
        singleRecord.setSIZE(SIZE);
        gradingList.add(singleRecord);
    }

    public void add(Grading singleRecord) {
        gradingList.add(singleRecord);
    }

    public void sortByDir() {
        Collections.sort(gradingList, new directoryComparator());
    }

    public void sortByTime() {
        Collections.sort(gradingList, new timeComparator());
    }

    // merge all deduplicate grades, new record replaceAdd older one
    public void merge() {
        for (int i = gradingList.size() - 1; i >= 0; i--) {
            int result = find(gradingList.get(i).getDIRECTORY(), 0, i - 1);
            while (result >= 0 && result <= i - 1) {
                gradingList.remove(result);
                result = find(gradingList.get(i).getDIRECTORY(), 0, --i);
            }
        }
    }

    // may have a better performance
    public void mergeAndSortByDir() {
        TreeMap<String, Grading> temp = new TreeMap<>();
        for (int it = 0; it < gradingList.size(); it++) {
            temp.put(gradingList.get(it).getDIRECTORY(), gradingList.get(it));
        }
        gradingList.clear();
        for (String key : temp.keySet()) {
            gradingList.add(temp.get(key));
        }
    }

    public Grading get(int index) {
        return gradingList.get(index);
    }

    public void clear() {
        gradingList.clear();
    }

    public int size() {
        return gradingList.size();
    }

    public boolean isEmpty() {
        return gradingList.isEmpty();
    }
}

class timeComparator implements Comparator<Grading> {
    @Override
    public int compare(Grading o1, Grading o2) {
        return o1.getTimeStamp().compareTo(o2.getTimeStamp());
    }
}

class directoryComparator implements Comparator<Grading> {
    @Override
    public int compare(Grading o1, Grading o2) {
        return o1.getDIRECTORY().compareTo(o2.getDIRECTORY());
    }
}