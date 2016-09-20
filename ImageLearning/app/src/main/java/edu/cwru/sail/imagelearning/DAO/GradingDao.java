package edu.cwru.sail.imagelearning.DAO;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.cwru.sail.imagelearning.Entity.Grading;
import edu.cwru.sail.imagelearning.Entity.GradingTable;
import edu.cwru.sail.imagelearning.Util.Util;

public class GradingDao {

    public boolean writeToCSV(GradingTable gradingTable, String csvDir) {

        if (gradingTable.isEmpty()) {
            return false;
        }
        List<String[]> formatted = new ArrayList<>();
        String[] nextLine;
        for (int it = 0; it < gradingTable.size(); it++) {
            Grading record = gradingTable.get(it);
            nextLine = (record.getTimeStamp() + "," +
                    record.getTYPE_ACCELEROMETER_X() + "," + record.getTYPE_ACCELEROMETER_Y() + "," + record.getTYPE_ACCELEROMETER_Z() + "," +
                    record.getTYPE_MAGNETIC_FIELD_X() + "," + record.getTYPE_MAGNETIC_FIELD_Y() + "," + record.getTYPE_MAGNETIC_FIELD_Z() + "," +
                    record.getTYPE_GYROSCOPE_X() + "," + record.getTYPE_GYROSCOPE_Y() + "," + record.getTYPE_GYROSCOPE_Z() + "," +
                    record.getTYPE_ROTATION_VECTOR_X() + "," + record.getTYPE_ROTATION_VECTOR_Y() + "," + record.getTYPE_ROTATION_VECTOR_Z() + "," +
                    record.getTYPE_LINEAR_ACCELERATION_X() + "," + record.getTYPE_LINEAR_ACCELERATION_Y() + "," + record.getTYPE_LINEAR_ACCELERATION_Z() + "," +
                    record.getTYPE_GRAVITY_X() + "," + record.getTYPE_GRAVITY_Y() + "," + record.getTYPE_GRAVITY_Z() + "," +
                    record.getPOSITION_X() + "," + record.getPOSITION_Y() + "," + record.getVELOCITY_X() + "," + record.getVELOCITY_Y() + "," +
                    record.getPRESSURE() + "," + record.getSIZE() + "," +
                    Util.truncateFileName(record.getDIRECTORY()) + "," + record.getSMILE_LEVEL()).split(",");
            formatted.add(nextLine);
        }

        try {
            CSVWriter writer = new CSVWriter(new FileWriter(csvDir));
            writer.writeAll(formatted, false);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean readCSV(GradingTable gradingTable, String csvDir) {
        CSVReader reader;
        String[] reading;
        File csv = new File(csvDir);
        if (csv.exists()) {
            try {
                gradingTable.clear();
                String suffix = csvDir.substring(0, csvDir.lastIndexOf("/"));       // Get path of the folder that contain this csv file
                reader = new CSVReader(new FileReader(csvDir));
                List<String[]> csvRead = reader.readAll();
                for (int it = csvRead.size() - 1; it >= 0; it--) {
                    reading = csvRead.get(it);
                    gradingTable.add(suffix + "/" + reading[25], reading[0], Integer.parseInt(reading[26]), Double.parseDouble(reading[1]),
                            Double.parseDouble(reading[2]), Double.parseDouble(reading[3]), Double.parseDouble(reading[4]),
                            Double.parseDouble(reading[5]), Double.parseDouble(reading[6]), Double.parseDouble(reading[7]),
                            Double.parseDouble(reading[8]), Double.parseDouble(reading[9]), Double.parseDouble(reading[10]),
                            Double.parseDouble(reading[11]), Double.parseDouble(reading[12]), Double.parseDouble(reading[13]),
                            Double.parseDouble(reading[14]), Double.parseDouble(reading[15]), Double.parseDouble(reading[16]),
                            Double.parseDouble(reading[17]), Double.parseDouble(reading[18]),
                            Double.parseDouble(reading[19]), Double.parseDouble(reading[20]), Double.parseDouble(reading[21]),
                            Double.parseDouble(reading[22]), Double.parseDouble(reading[23]), Double.parseDouble(reading[24])
                    );     // Restore image file list and smile levels
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
        return true;
    }
}
