package edu.cwru.sail.imagelearning.DAO;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import edu.cwru.sail.imagelearning.Entity.Grading;
import edu.cwru.sail.imagelearning.Entity.GradingTable;
import edu.cwru.sail.imagelearning.Util.Util;

public class GradingDao {

    static private String[] string_smile_level = {"not_graded","not_smile","slightly_smile","smile","big_smile"};

    public boolean writeToCSV(GradingTable gradingTable, String csvDir) {

        if (gradingTable.isEmpty()) {
            return false;
        }
        List<String[]> formatted = new ArrayList<>();   // CSV writer only accept this type, so we have to do conversion
        String[] nextLine;          // Each row in CSV
        nextLine = ("TimeStamp"+","+"ACCELEROMETER_X"+","+"ACCELEROMETER_Y"+","+"ACCELEROMETER_Z"+","
                +"MAGNETIC_FIELD_X"+","+"MAGNETIC_FIELD_Y"+","+"MAGNETIC_FIELD_Z"+","
                +"GYROSCOPE_X"+","+"GYROSCOPE_Y"+","+"GYROSCOPE_Z"+","
                +"ROTATION_VECTOR_X"+","+"ROTATION_VECTOR_Y"+","+"ROTATION_VECTOR_Z"+","
                +"LINEAR_ACCELERATION_X"+","+"LINEAR_ACCELERATION_Y"+","+"LINEAR_ACCELERATION_Z"+","
                +"GRAVITY_X"+","+"GRAVITY_Y"+","+"GRAVITY_Z"+","+"POSITION_X"+","+"POSITION_Y"+","
                +"VELOCITY_X"+","+"VELOCITY_Y"+","+"PRESSURE"+","+"SIZE"+","+"PICTURE"+","+"SMILE_LEVEL").split(",");
        formatted.add(nextLine);
        for (int it = 0; it < gradingTable.size(); it++) {
            Grading record = gradingTable.get(it);      // Get every record from grading table
            nextLine = (record.getTimeStamp() + "," +
                    record.getTYPE_ACCELEROMETER_X() + "," + record.getTYPE_ACCELEROMETER_Y() + "," + record.getTYPE_ACCELEROMETER_Z() + "," +
                    record.getTYPE_MAGNETIC_FIELD_X() + "," + record.getTYPE_MAGNETIC_FIELD_Y() + "," + record.getTYPE_MAGNETIC_FIELD_Z() + "," +
                    record.getTYPE_GYROSCOPE_X() + "," + record.getTYPE_GYROSCOPE_Y() + "," + record.getTYPE_GYROSCOPE_Z() + "," +
                    record.getTYPE_ROTATION_VECTOR_X() + "," + record.getTYPE_ROTATION_VECTOR_Y() + "," + record.getTYPE_ROTATION_VECTOR_Z() + "," +
                    record.getTYPE_LINEAR_ACCELERATION_X() + "," + record.getTYPE_LINEAR_ACCELERATION_Y() + "," + record.getTYPE_LINEAR_ACCELERATION_Z() + "," +
                    record.getTYPE_GRAVITY_X() + "," + record.getTYPE_GRAVITY_Y() + "," + record.getTYPE_GRAVITY_Z() + "," +
                    record.getPOSITION_X() + "," + record.getPOSITION_Y() + "," + record.getVELOCITY_X() + "," + record.getVELOCITY_Y() + "," +
                    record.getPRESSURE() + "," + record.getSIZE() + "," +
                    Util.truncateFileName(record.getDIRECTORY()) + "," + string_smile_level[record.getSMILE_LEVEL()]).split(",");   // format each record
            formatted.add(nextLine);
        }
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(csvDir));
            writer.writeAll(formatted, false);          // Write all records in to CSV
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

//    public boolean readCSV(GradingTable gradingTable, String csvDir) {    // old method
//        CSVReader reader;
//        String[] reading;
//        File csv = new File(csvDir);
//        if (csv.exists()) {
//            try {
//                String suffix = csvDir.substring(0, csvDir.lastIndexOf("/"));       // Get path of the folder that contains this csv file
//                reader = new CSVReader(new FileReader(csvDir));
//                List<String[]> csvRead = reader.readAll();                          // read all records from this csv file
//                for (int it = csvRead.size() - 1; it >= 0; it--) {
//                    reading = csvRead.get(it);
//                    gradingTable.add(suffix + "/" + reading[25], reading[0], Integer.parseInt(reading[26]), Double.parseDouble(reading[1]),
//                            Double.parseDouble(reading[2]), Double.parseDouble(reading[3]), Double.parseDouble(reading[4]),
//                            Double.parseDouble(reading[5]), Double.parseDouble(reading[6]), Double.parseDouble(reading[7]),
//                            Double.parseDouble(reading[8]), Double.parseDouble(reading[9]), Double.parseDouble(reading[10]),
//                            Double.parseDouble(reading[11]), Double.parseDouble(reading[12]), Double.parseDouble(reading[13]),
//                            Double.parseDouble(reading[14]), Double.parseDouble(reading[15]), Double.parseDouble(reading[16]),
//                            Double.parseDouble(reading[17]), Double.parseDouble(reading[18]),
//                            Double.parseDouble(reading[19]), Double.parseDouble(reading[20]), Double.parseDouble(reading[21]),
//                            Double.parseDouble(reading[22]), Double.parseDouble(reading[23]), Double.parseDouble(reading[24])
//                    );     // Restore image file list and smile levels
//                }
//                reader.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//                return false;
//            }
//        } else {
//            return false;
//        }
//        return true;
//    }

    public boolean readCSV(GradingTable gradingTable, String csvDir) {
        CSVReader reader;
        String[] reading;
        File csv = new File(csvDir);
        if (csv.exists()) {
            try {
                String suffix = csvDir.substring(0, csvDir.lastIndexOf("/"));       // Get path of the folder that contains this csv file
                reader = new CSVReader(new FileReader(csvDir));
                List<String[]> csvRead = reader.readAll();                          // read all records from this csv file
                for (int it = 1; it < csvRead.size(); it++) {
                    reading = csvRead.get(it);
                    gradingTable.add(suffix + "/" + reading[25], reading[0], toSmileLevel(reading[26]), Double.parseDouble(reading[1]),
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

    private int toSmileLevel(String level) {
        for (int i = 0; i < string_smile_level.length; i++) {
            if (string_smile_level[i].compareTo(level) == 0) {
                return i;
            }
        }
        return 0;
    }

    public int getSmileLevelCSV(String img, String csvDir) {
        String[] reading;
        CSVReader reader;
        try {
            reader = new CSVReader(new FileReader(csvDir));
            List csvRead = reader.readAll();
            for (int it = csvRead.size() - 1; it >= 0; it--) {
                reading = ((String[]) (csvRead.get(it)));
                if (reading[0].equals(img)) {
                    return (Integer.parseInt(reading[1]));
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;     // Return -1 if not found
    }

    public ArrayList<Integer> getSmileLevelwIndexCSV(String img, String csvDir) {
        ArrayList<Integer> index_smile = new ArrayList<>();
        String[] reading;
        CSVReader reader;
        try {
            reader = new CSVReader(new FileReader(csvDir));
            List csvRead = reader.readAll();
            for (int it = csvRead.size() - 1; it >= 0; it--) {
                reading = ((String[]) (csvRead.get(it)));
                if (reading[0].equals(img)) {
                    index_smile.add(it);
                    index_smile.add(Integer.parseInt(reading[1]));
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return index_smile;     // Return empty if not found
    }
}
