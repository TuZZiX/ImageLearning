package edu.cwru.sail.imagelearning.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.cwru.sail.imagelearning.Util.FileDialog;

public class ImageActivity extends Activity {

    private int smile_level = 0;
    private int img_counter = 0;
    private CSVWriter writer;

    private ImageView photo;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn_previous;
    private Button btn_skip;
    private TextView textInd;
    private TextView textCount;

    private String csvDir = Environment.getExternalStorageDirectory().toString() + File.separator + "lab01" + File.separator + "result.csv";

    private ArrayList<String> goalPath;
    FileDialog fileDialog;
    //Make sure that this part is dynamically defined by the Browse Folder and
    // your CSV file name is "THE_SAME_FOLDER_NAME.csv"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        photo = (ImageView) findViewById(R.id.photo);
        btn1 = (Button) findViewById(R.id.btnRate1);
        btn2 = (Button) findViewById(R.id.btnRate2);
        btn3 = (Button) findViewById(R.id.btnRate3);
        btn4 = (Button) findViewById(R.id.btnRate4);
        btn_previous = (Button) findViewById(R.id.btnPrv);
        btn_skip = (Button) findViewById(R.id.btnSkip);
        textInd = (TextView) findViewById(R.id.textNowGrad);
        textCount = (TextView) findViewById(R.id.textProgressInd);
        btn1.setOnClickListener(smileListener);
        btn2.setOnClickListener(smileListener);
        btn3.setOnClickListener(smileListener);
        btn4.setOnClickListener(smileListener);
        btn_previous.setOnClickListener(scrollListener);
        btn_skip.setOnClickListener(scrollListener);

        browserFolder();

        File csv = new File(csvDir);
        if (!csv.exists()) {
            Toast.makeText(getApplicationContext(), "CSV File not exist, create new", Toast.LENGTH_SHORT).show();
        }
        try {
            writer = new CSVWriter(new FileWriter(csvDir));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onStop();
    }

    View.OnClickListener smileListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnRate1:
                    smile_level = 1;
                    break;
                case R.id.btnRate2:
                    smile_level = 2;
                    break;
                case R.id.btnRate3:
                    smile_level = 3;
                    break;
                case R.id.btnRate4:
                    smile_level = 4;
                    break;
            }
            writeToCSV();
            gradeNext();
        }
    };

    View.OnClickListener scrollListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnPrv:
                    gradePrv();
                    break;
                case R.id.btnSkip:
                    gradeNext();
                    break;
            }
        }
    };

    private void gradeNext() {
        img_counter++;
        if (changeImg()) {
            ArrayList<Integer> history = getSmileLevelCSV(goalPath.get(img_counter));
            if (history.size() == 2) {
                smile_level = history.get(1);

            } else {
                smile_level = 0;
            }
            btn1.setBackgroundColor(getResources().getColor(R.color.smile_1));
            btn2.setBackgroundColor(getResources().getColor(R.color.smile_2));
            btn3.setBackgroundColor(getResources().getColor(R.color.smile_3));
            btn4.setBackgroundColor(getResources().getColor(R.color.smile_4));
            switch (smile_level) {
                case 1:
                    btn1.setBackgroundColor(getResources().getColor(R.color.smile_1_sel));
                    break;
                case 2:
                    btn2.setBackgroundColor(getResources().getColor(R.color.smile_2_sel));
                    break;
                case 3:
                    btn3.setBackgroundColor(getResources().getColor(R.color.smile_3_sel));
                    break;
                case 4:
                    btn4.setBackgroundColor(getResources().getColor(R.color.smile_4_sel));
                    break;
            }
        } else {
            img_counter--;  // If failed, roll back to previous
            Toast.makeText(getApplicationContext(), "No next image", Toast.LENGTH_LONG).show();
        }
    }
    private void gradePrv() {
        img_counter--;
        if (changeImg()) {
            ArrayList<Integer> history = getSmileLevelCSV(goalPath.get(img_counter));
            if (history.size() == 2) {
                smile_level = history.get(1);

            } else {
                smile_level = 0;
            }
            btn1.setBackgroundColor(getResources().getColor(R.color.smile_1));
            btn2.setBackgroundColor(getResources().getColor(R.color.smile_2));
            btn3.setBackgroundColor(getResources().getColor(R.color.smile_3));
            btn4.setBackgroundColor(getResources().getColor(R.color.smile_4));
            switch (smile_level) {
                case 1:
                    btn1.setBackgroundColor(getResources().getColor(R.color.smile_1_sel));
                    break;
                case 2:
                    btn2.setBackgroundColor(getResources().getColor(R.color.smile_2_sel));
                    break;
                case 3:
                    btn3.setBackgroundColor(getResources().getColor(R.color.smile_3_sel));
                    break;
                case 4:
                    btn4.setBackgroundColor(getResources().getColor(R.color.smile_4_sel));
                    break;
            }
        } else {
            img_counter++;  // If failed, roll back to previous
            Toast.makeText(getApplicationContext(), "No previous image", Toast.LENGTH_LONG).show();
        }
    }
    public boolean changeImg() {
        if (goalPath.size() <= img_counter || img_counter < 0) {
            //Toast.makeText(getApplicationContext(), "Out of index", Toast.LENGTH_SHORT).show();
            return false;
        }
        File img = new File(goalPath.get(img_counter));
        //Toast.makeText(getApplicationContext(), goalPath.get(img_counter), Toast.LENGTH_SHORT).show();
        if (img.exists()) {
            //Loading Image from URL
            Picasso.with(getApplicationContext())
                    .load(img)
                    //.placeholder(R.drawable.placeholder)   // optional
                    .error(R.drawable.smile_fail)
                    .resize(1000, 1000)                        // optional
                    .into(photo);

            String temp = goalPath.get(img_counter);
            String[] temp1 = temp.split("/");
            textInd.setText(getText(R.string.textNowGrad_default) + temp1[temp1.length - 1]);
            textCount.setText(String.valueOf(img_counter + 1) + "/" + String.valueOf(goalPath.size()));
        } else {
            Toast.makeText(getApplicationContext(), "Image not exist: " + goalPath.get(img_counter), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void writeToCSV() {
        String[] nextLine = (goalPath.get(img_counter) + "," + smile_level).split(",");
        writer.writeNext(nextLine);
    }

    private ArrayList<Integer> getSmileLevelCSV(String img) {
        ArrayList<Integer> index_smile = new ArrayList<>();
        String[] reading;
        CSVReader reader;
        try {
            reader = new CSVReader(new FileReader(csvDir));
            List csvRead = reader.readAll();
            for (int it = csvRead.size(); it > 0; it--) {
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
        return index_smile;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.setting_openImg:
//                Intent intent = new Intent();
//                intent.setType("file/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                try {
//                    startActivityForResult(intent, FILE_SELECT_CODE);
//                } catch (android.content.ActivityNotFoundException ex) {
//                    // Potentially direct the user to the Market with a Dialog
//                    Toast.makeText(this, "Failed to open file browser", Toast.LENGTH_SHORT).show();
//                }
                browserFolder();
                return true;
            case R.id.setting_openResult:
                File file = new File(csvDir);
                Uri path = Uri.fromFile(file);
                Intent csvOpenintent = new Intent(Intent.ACTION_VIEW);
                csvOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                csvOpenintent.setDataAndType(path, "text/csv");
                try {
                    startActivity(csvOpenintent);
                }
                catch (ActivityNotFoundException e) {

                }
                return true;
            case R.id.setting_exit:
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void browserFolder() {
        img_counter = 0;
        /**
         * Requesting Permissions at Run Time
         */
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        File mPath = new File(String.valueOf(Environment.getExternalStorageDirectory()));
//                File mPath = new File(System.getenv("SECONDARY_STORAGE"));
        fileDialog = new FileDialog(this, mPath, ".jpg");
        fileDialog.addDirectoryListener(new FileDialog.DirectorySelectedListener() {
            @Override
            public void directorySelected(File directory) {
                Log.d(getClass().getName(), "selected file " + directory.toString());
            }
        });
        fileDialog.addFileListener(new FileDialog.FileSelectedListener() {
            public void fileSelected(File file) {
                Log.d(getClass().getName(), "selected file " + file.toString());
            }
        });
        fileDialog.setSelectDirectoryOption(true);
        goalPath = new ArrayList<>();
        fileDialog.showDialog(goalPath);
    }
}
