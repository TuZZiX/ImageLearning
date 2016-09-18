package edu.cwru.sail.imagelearning.DAO;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import edu.cwru.sail.imagelearning.Entity.Grading;
import edu.cwru.sail.imagelearning.Util.Util;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by majunqi0102 on 9/15/16.
 */
public class GradingDao {
    //TODO
    public boolean writeToCSV(Map<String, Grading> smile_storage, String csvDir) {

        if (smile_storage.isEmpty()) {
            return false;
        }
        List<String[]> formatted = new ArrayList<>();
        String[] nextLine;
        for (String key : smile_storage.keySet()) {
            nextLine = (Util.truncateFileName(key) + "," + smile_storage.get(key)).split(",");
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

    //TODO should return CSVEntiry
    //why all public?       Because when new image folder opened, history data need to restore from CSV by calling this function
    public boolean readCSV(Map<String, Integer> smile_storage, String csvDir) {
        CSVReader reader;
        String[] reading;
        File csv = new File(csvDir);
        if (csv.exists()) {
            try {
                smile_storage.clear();
                String suffix = csvDir.substring(0, csvDir.lastIndexOf("/"));       // Get path of the folder that contain this csv file
                reader = new CSVReader(new FileReader(csvDir));
                List<String[]> csvRead = reader.readAll();
                for (int it = csvRead.size() - 1; it >= 0; it--) {
                    reading = csvRead.get(it);
                    smile_storage.put(suffix + "/" + reading[0], Integer.parseInt(reading[1]));     // Restore image file list and smile levels
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
