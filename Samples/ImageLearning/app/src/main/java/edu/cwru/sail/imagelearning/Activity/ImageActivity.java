package edu.cwru.sail.imagelearning.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
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

import org.apache.commons.lang3.ObjectUtils;

import edu.cwru.sail.imagelearning.Util.FileDialog;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageActivity extends Activity {

    private int smile_level = 0;
    private int imgCounter = 0;

    private static final int FILE_SELECT_CODE = 0;

    private ImageView photo;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn_previous;
    private Button btn_skip;
    private TextView textInd;
    private TextView textCount;

    private String imgDir = Environment.getExternalStorageDirectory().toString() + "/lab01/Data";
    private String csvDir = Environment.getExternalStorageDirectory().toString() + File.separator + "lab01" + File.separator + "result.csv";

    private ArrayList<String> goalPath;
    FileDialog fileDialog;
    //Make sure that this part is dynamically defined by the Browse Folder and
    // your CSV file name is "THE_SAME_FOLDER_NAME.csv"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*  variable name should be like btn_rate1
            btnRate1 seems like function name
        */
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

        //Log.i("csv", csvDir);
        //Log.i("img", imgDir);

        browserFolder();

//        if (changeImg()) {
//            Toast.makeText(getApplicationContext(), "Failed to load image at:" + imgDir, Toast.LENGTH_SHORT).show();
//        }

        File csv = new File(csvDir);
        if (!csv.exists()) {
            Toast.makeText(getApplicationContext(), "CSV File not exit, create new", Toast.LENGTH_SHORT).show();
            CSVWriter writer = null;
            try {
                writer = new CSVWriter(new FileWriter(csvDir));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    View.OnClickListener smileListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnRate1:
                    smile_level = 1;
                    Toast.makeText(getApplicationContext(), String.valueOf(goalPath.size()), Toast.LENGTH_LONG).show();
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
            writeToCSV(imgDir, smile_level);
        }
    };

    View.OnClickListener scrollListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnPrv:
                    //TODO
                    break;
                case R.id.btnSkip:
                    //TODO
                    break;
            }
        }
    };

    public boolean changeImg() {

        File img = new File(goalPath.get(imgCounter));
        Toast.makeText(getApplicationContext(), goalPath.get(imgCounter), Toast.LENGTH_SHORT).show();
        if (img.exists()) {
            //Loading Image from URL
            Picasso.with(this)
                    //.load(Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera/" + "IMG_20160910_154021.jpg")
                    .load(img)
                    //.placeholder(R.drawable.placeholder)   // optional
                    //.error(R.drawable.error)      // optional
                    .resize(1000, 1000)                        // optional
                    .into(photo);

            String temp = goalPath.get(imgCounter);
            String[] temp1 = temp.split("/");
            textInd.setText(getText(R.string.textNowGrad_default) + temp1[temp1.length - 1]);
            textCount.setText(String.valueOf(imgCounter + 1) + "/" + String.valueOf(goalPath.size()));
        } else {
            return false;
        }
        return true;
    }

    public void writeToCSV(String img, int smile_level) {
        CSVWriter writer = null;
        try {
            writer = new CSVWriter(new FileWriter(csvDir));
            String[] nextLine = (img + "," + smile_level).split(",");
            writer.writeNext(nextLine);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getSmileLevelCSV(String img) {
        String[] reading;
        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(csvDir));
            List csvRead = reader.readAll();
            for (int it = csvRead.size(); it > 0; it--) {
                reading = ((String[]) (csvRead.get(it)));
                if (reading[0].equals(img)) {
                    return Integer.parseInt(reading[1]);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean updateFileList(String Directory, ArrayList<String> goalPath) {
        File currentPath = new File(Directory);
        if (currentPath != null) {
            for (File file : currentPath.listFiles()) {
                goalPath.add(currentPath.getAbsoluteFile() + "/" + file.getName());
            }
            return true;
        }
        return false;
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
            case R.id.setting_changeLoc:

                return true;
            case R.id.setting_openResult:

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
