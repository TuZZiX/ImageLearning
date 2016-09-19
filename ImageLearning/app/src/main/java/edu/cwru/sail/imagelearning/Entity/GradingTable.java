package edu.cwru.sail.imagelearning.Entity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.TreeMap;

public class GradingTable {
    private ArrayList<Grading> gradings= new ArrayList<>();

    public void override(String DIRECTORY,
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
        override(DIRECTORY, date, SMILE_LEVEL,
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

    public void override(String DIRECTORY,
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
        override(singleRecord);
    }

    public void override(Grading singleRecord) {
        String dir = singleRecord.getDIRECTORY();
        int index = find(dir);
        if (index >= 0 && index < size()) {
            override(index, singleRecord);
        } else {
            add(singleRecord);
        }
    }

    public void override(int index, Grading singleRecord) {
        gradings.set(index, singleRecord);
    }

    public int find(String DIRECTORY) {
        return find(DIRECTORY, 0, gradings.size() - 1);
    }

    // Note: including start index and end index
    public int find(String DIRECTORY, int start, int end) {
        if (start >=0 && end >= 0 && start < gradings.size() && end < gradings.size()) {
            for (int it = end; it >= start; it--) {
                if (gradings.get(it).getDIRECTORY().compareTo(DIRECTORY) == 0) {
                    return it;
                }
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

    public void add(Grading singleRecord) {
        gradings.add(singleRecord);
    }

    public void sortByDir() {
        Collections.sort(gradings, new directoryComparator());
    }

    public void sortByTime() {
        Collections.sort(gradings, new timeComparator());
    }

    public void mergeAll() {
        for (int i = gradings.size() - 1; i >= 0; i--) {
            int result = find(gradings.get(i).getDIRECTORY(), 0, i - 1);
            while (result >= 0 && result <= i - 1) {
                gradings.remove(result);
                result = find(gradings.get(i).getDIRECTORY(), 0, --i);
            }
        }
    }

    public void mergeAndSortByDir() {
        TreeMap<String, Grading> temp = new TreeMap<>();
        for (int it = 0; it < gradings.size(); it++) {
            temp.put(gradings.get(it).getDIRECTORY(), gradings.get(it));
        }
        gradings.clear();
        for (String key : temp.keySet()) {
            gradings.add(temp.get(key));
        }
    }

    public Grading get(int index) { return gradings.get(index); }

    public void clear() {
        gradings.clear();
    }

    public int size() {
        return gradings.size();
    }

    public boolean isEmpty() { return gradings.isEmpty(); }

    public ArrayList<Grading> getAll() {
        return gradings;
    }

    public void addAll(ArrayList<Grading> gradings) {
        this.gradings = gradings;
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