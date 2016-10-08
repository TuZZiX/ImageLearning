package com.weka;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import java.util.List;

import com.opencsv.CSVReader;

/**
 * Created by aab119 on 9/21/2015.
 */
public class Convert2Arff {

    public Convert2Arff() {
        // Constructor
    }

    /*
    A. Badokhon

     This function is a CSV to Text (ARFF Format) parser that converts CSV to ARFF.
     It will take one CSV file and generate two ARFF files. One for training and the other is for testing
     As default naming; it will add a "TEST" to the end of the testing file name to distinguish between
      the two...
     As for default entries; 35 counter values will be as training values and the rest (min 15) will be for testing.

     Please note that this class is highly experimental and require a lot of changes in order to properly suit your conversion needs.
     Thus, please read the code carefully and understand inputs and outputs and make changes accordingly.
    */
    public boolean ReadParseCSV(String csvStr) throws Exception {
        String csvDir = android.os.Environment.getExternalStorageDirectory()+"/DCIM/CSV/";
        String csv2ArffDir = android.os.Environment.getExternalStorageDirectory()+"/DCIM/CSV2ARFF/";
        String test = "Test";

        CSVReader reader = new CSVReader(new FileReader(csvDir+csvStr));
        BufferedWriter writer = null;
        BufferedWriter writerT = null;
        List<String[]> csvAll = reader.readAll(); //  Here we will have our csv file converted to a local ArrayList of Strings for processing.

        Integer firstCounter = Integer.parseInt(csvAll.get(1)[csvAll.get(csvAll.size() - 1).length - 3]); //  The first counter value
        Integer lastCounter = Integer.parseInt(csvAll.get(csvAll.size()-1)[csvAll.get(csvAll.size()-1).length-3])+1; //  The last counter value

        String initials = "{U1,U2}";

        // For data to comply, it has to be more than 50 pattern trials
        if (lastCounter-firstCounter<50) return false;
        // 50 --> 35 training, 15 or more is testing.

        //  At first we will create an empty arff file
        String csvNoExt = csvStr.replaceFirst("[.][^.]+$", ""); //  temp.csv --> temp (removes extension)
        //String initials = csvNoExt.substring(Math.max(csvNoExt.length() - 2, 0));

        File arffFile = new File(csv2ArffDir+csvNoExt+".arff");
        File arffTest = new File(csv2ArffDir+csvNoExt+test+".arff");
        String arffConstructor = ""; // This string will hold all the lines befor @data, because
        String arffConstTest = "";   // the same is going to be used for both training and testing

        //  Then we will start preparing the file arff as per format.
        /*
        ARFF File Format
        EX)
        @relation sensorAndMotion

        @attribute TYPE_ACCELEROMETER_X numeric
        @attribute TYPE_ACCELEROMETER_Y numeric
        @attribute TYPE_ACCELEROMETER_Z numeric
        @attribute TYPE_MAGNETIC_FIELD_X numeric
        @attribute TYPE_MAGNETIC_FIELD_Y numeric
        @attribute TYPE_MAGNETIC_FIELD_Z numeric
        ....

        @data
        -2.74614,6.727692,6.46434,37.27722,-27.62909,-30.218506....
        ......
         */
        //  We will not be appending to this file, it will just be a conversion (no true for FileWriter)

        writer = new BufferedWriter(new FileWriter(arffFile));
        writerT = new BufferedWriter(new FileWriter(arffTest));

        //writer.write("@relation "+initials+"sensorAndMotion\n\n"); //  The first line, leaving a line empty
        arffConstructor = arffConstructor+"@relation "+"sensorAndMotion\n\n";
        arffConstTest = arffConstTest + "@relation "+"sensorAndMotion\n\n";
        //  The first line of CSV is for attributes SO;
        String[] attr = csvAll.get(0);
        for (int i = 0; i<attr.length; i++) {
            if (i==6 || i==7 || i==8 || i==attr.length-3 || i==attr.length-1) continue; //   skip Gyroscope and Pattern data since it is not used in this assignment // and Counter for now

/*            if (i==attr.length-2) { // meaning Counter column
                arffConstructor = arffConstructor + "@attribute " + attr[i] + " "+counterFormat+"\n";
                arffConstTest = arffConstTest + "@attribute " + attr[i] + " "+TcounterFormat+"\n";
            }*/
            if (i==attr.length-2) { // meaning initials column
                arffConstructor = arffConstructor + "@attribute " + attr[i] + " "+initials+"\n";
                arffConstTest = arffConstTest + "@attribute " + attr[i] + " "+initials+"\n";
            }
            else {
                arffConstructor = arffConstructor + "@attribute " + attr[i] + " numeric\n";
                arffConstTest = arffConstTest +  "@attribute " + attr[i] + " numeric\n";
            }
        }
        arffConstructor = arffConstructor + "\n@data\n";//  leaving two lines before the Data.
        arffConstTest = arffConstTest + "\n@data\n";//  leaving two lines before the Data.

        writer.write(arffConstructor);
        writerT.write(arffConstTest);

        //writer.write("\n@data\n");
        //  for the data, it is going to be a ArrayList of Strings, one way is to make
        // two for loops to handle all entries...
        for (int i = 1; i<csvAll.size()-1; i++) { // Since 0 is for attr, we will start with 1
            String[] dataLine = csvAll.get(i);
            // Training loop..
            // if initials equals  or  then this is training data
            if (dataLine[dataLine.length-2].equals("") || dataLine[dataLine.length-2].equals("U2")) {
                for (int j = 0; j<dataLine.length; j++) {
                    if (j==6 || j==7 || j==8 || j==dataLine.length-3 || j==dataLine.length-1) continue; //   skip Gyroscope and pattern data since it is not used in this assignment // and Counter for now
                    if (j<dataLine.length-2) writer.write(dataLine[j]+","); // if it was not the last element of the array
                    else writer.write(dataLine[j]+"\n"); //the last element with no comma. but go to next line
                }
            }
            //Testing loop
            // if initials were anonymous then it is training data.
            else  {
                for (int j = 0; j<dataLine.length; j++) {
                    if (j==6 || j==7 || j==8 || j==dataLine.length-3 || j==dataLine.length-1) continue; //   skip Gyroscope and pattern data since it is not used in this assignment
                    else if (j==dataLine.length-2) {
                        if (dataLine[j].equals("U1") ) writerT.write(""+"\n"); //  A2 Changing the initials to the correct user
                        else writerT.write(""+"\n"); //  A2 changing the initials to the correct user
                    }
                    else if (j<dataLine.length-3) writerT.write(dataLine[j]+","); // if it was not the last element of the array
                }
            }
        }
        writer.close(); // Close and save the ARFF file
        writerT.close(); // Close and save the TEST ARFF file
        return true;
    }

    // A2 ARFF Generator for Experiment 2. Adds the pattern column as an attribute within the ARFF. This serves as unknown for when the mode is test.
    public boolean ReadParseCSVex2(String csvStr) throws Exception {
        String csvDir = android.os.Environment.getExternalStorageDirectory()+"/DCIM/CSV/";
        String csv2ArffDir = android.os.Environment.getExternalStorageDirectory()+"/DCIM/CSV2ARFF/";
        String test = "Test";
        CSVReader reader = new CSVReader(new FileReader(csvDir+csvStr));
        BufferedWriter writer = null;
        BufferedWriter writerT = null;
        List<String[]> csvAll = reader.readAll(); //  Here we will have our csv file converted to a local ArrayList of Strings for processing.
        Integer firstCounter = Integer.parseInt(csvAll.get(1)[csvAll.get(csvAll.size() - 1).length - 3]); //  The first counter value
        Integer lastCounter = Integer.parseInt(csvAll.get(csvAll.size()-1)[csvAll.get(csvAll.size()-1).length-3])+1; //  The last counter value
        String[] patterns = {"A", "B", "C", "D"}; // A2 All possible pattern values
        String patternAttr = "{A,B,C,D}"; //  A2


        // For data to comply, it has to be more than 50 pattern trials
        if (lastCounter-firstCounter<50) return false;
        // 50 --> 35 training, 15 or more is testing.

        //  to produce a string with the format {0,1,2,...,lastCounter}
        String TcounterFormat = "";
        String counterFormat = "{";
        for (int i = firstCounter; i<lastCounter-1; i++) counterFormat = counterFormat + i +",";
        counterFormat = counterFormat + (lastCounter-1)+"}";

        String[] temp = counterFormat.split(",36,");
        counterFormat = temp[0]+"}";
        TcounterFormat = "{36,"+temp[1];

        //  At first we will create an empty arff file
        String csvNoExt = csvStr.replaceFirst("[.][^.]+$", ""); //  temp.csv --> temp (removes extension)

        File arffFile = new File(csv2ArffDir+csvNoExt+"EX2"+".arff");
        File arffTest = new File(csv2ArffDir+csvNoExt+test+"EX2"+".arff");
        String arffConstructor = ""; // This string will hold all the lines befor @data, because
        String arffConstTest = "";   // the same is going to be used for both training and testing

        //  Then we will start preparing the file arff as per format.
        /*
        ARFF File Format
        EX)
        @relation sensorAndMotion

        @attribute TYPE_ACCELEROMETER_X numeric
        @attribute TYPE_ACCELEROMETER_Y numeric
        @attribute TYPE_ACCELEROMETER_Z numeric
        @attribute TYPE_MAGNETIC_FIELD_X numeric
        @attribute TYPE_MAGNETIC_FIELD_Y numeric
        @attribute TYPE_MAGNETIC_FIELD_Z numeric
        ....

        @data
        -2.74614,6.727692,6.46434,37.27722,-27.62909,-30.218506....
        ......
         */
        //  We will not be appending to this file, it will just be a conversion (no true for FileWriter)

        writer = new BufferedWriter(new FileWriter(arffFile));
        writerT = new BufferedWriter(new FileWriter(arffTest));

        arffConstructor = arffConstructor+"@relation "+"sensorAndMotion\n\n";
        arffConstTest = arffConstTest + "@relation "+"sensorAndMotion\n\n";
        //  The first line of CSV is for attributes SO;
        String[] attr = csvAll.get(0);
        for (int i = 0; i<attr.length; i++) {
            if (i==6 || i==7 || i==8 || i==attr.length-2 || i==attr.length-3) continue; //  A2 skip Gyroscope, initials, and Counter for now

            if (i==attr.length-1) { //  A2 meaning pattern column
                arffConstructor = arffConstructor + "@attribute " + attr[i] + " "+patternAttr+"\n";
                arffConstTest = arffConstTest + "@attribute " + attr[i] + " "+patternAttr+"\n";
            }
            else {
                arffConstructor = arffConstructor + "@attribute " + attr[i] + " numeric\n";
                arffConstTest = arffConstTest +  "@attribute " + attr[i] + " numeric\n";
            }
        }
        arffConstructor = arffConstructor + "\n@data\n";//  leaving two lines before the Data.
        arffConstTest = arffConstTest + "\n@data\n";//  leaving two lines before the Data.

        writer.write(arffConstructor);
        writerT.write(arffConstTest);

        //  for the data, it is going to be a ArrayList of Strings, one way is to make
        // two for loops to handle all entries...
        for (int i = 1; i<csvAll.size()-1; i++) { // Since 0 is for attr, we will start with 1
            String[] dataLine = csvAll.get(i);
            // Training loop..
            // if initials equals  or  then this is training data
            if (dataLine[dataLine.length-2].equals("") || dataLine[dataLine.length-2].equals("")) {
                for (int j = 0; j<dataLine.length; j++) {
                    if (j==6 || j==7 || j==8 || j==dataLine.length-2 || j==dataLine.length-3) continue; //  A2 skip Gyroscope and initials data since it is not used in this assignment // and Counter for now
                        // use A B C D instead of 1 2 3 4 && last element
                    else if (j==dataLine.length-1) writer.write(patterns[Integer.parseInt(dataLine[j])-1]+"\n");
                    else if (j<dataLine.length-3) writer.write(dataLine[j]+","); // if it was not the last element of the array
                }
            }
            //Testing loop
            // if initials were anonymous then it is training data.
            else  {
                for (int j = 0; j<dataLine.length; j++) {
                    if (j==6 || j==7 || j==8 || j==dataLine.length-2 || j==dataLine.length-3) continue; //   A2 skip Gyroscope and initials data since it is not used in this assignment
                    else if (j==dataLine.length-1) writerT.write(patterns[Integer.parseInt(dataLine[j])-1]+"\n"); // use A B C D instead of 1 2 3 4
                    else if (j<dataLine.length-3) writerT.write(dataLine[j]+","); // if it was not the last element of the array
                }
            }
        }
        writer.close(); // Close and save the ARFF file
        writerT.close(); // Close and save the TEST ARFF file
        return true;
    }


}
