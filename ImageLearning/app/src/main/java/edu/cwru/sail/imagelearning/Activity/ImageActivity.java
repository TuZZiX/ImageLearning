package edu.cwru.sail.imagelearning.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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
import com.squareup.picasso.Picasso;
import edu.cwru.sail.imagelearning.DAO.CSVDao;
import edu.cwru.sail.imagelearning.Entity.GradingTable;
import edu.cwru.sail.imagelearning.Util.Util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImageActivity extends Activity implements SensorEventListener{

    private int smile_level = 0;
    private int img_counter = 0;

    private double CURRENT_ACCELEROMETER_X;
    private double CURRENT_ACCELEROMETER_Y;
    private double CURRENT_ACCELEROMETER_Z;
    private double CURRENT_MAGNETIC_FIELD_X;
    private double CURRENT_MAGNETIC_FIELD_Y;
    private double CURRENT_MAGNETIC_FIELD_Z;
    private double CURRENT_GYROSCOPE_X;
    private double CURRENT_GYROSCOPE_Y;
    private double CURRENT_GYROSCOPE_Z;
    private double CURRENT_ROTATION_VECTOR_X;
    private double CURRENT_ROTATION_VECTOR_Y;
    private double CURRENT_ROTATION_VECTOR_Z;
    private double CURRENT_LINEAR_ACCELERATION_X;
    private double CURRENT_LINEAR_ACCELERATION_Y;
    private double CURRENT_LINEAR_ACCELERATION_Z;
    private double CURRENT_GRAVITY_X;
    private double CURRENT_GRAVITY_Y;
    private double CURRENT_GRAVITY_Z;

    private ImageView photoView;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn_previous;
    private Button btn_skip;
    private TextView textInd;
    private TextView textCount;
    private TextView textLast;

    private String csvDir = Environment.getExternalStorageDirectory().toString() + File.separator + "lab01" + File.separator + "result.csv";

    private ArrayList<String> image_list = new ArrayList<>();

    protected CSVDao CSV = new CSVDao();
    protected GradingTable gradings = new GradingTable();

    FileDialog fileDialog;

    SensorManager mSensorManager;
    Sensor mAccelerometer;
    Sensor mMagnetometer;
    Sensor mGyroscope;
    Sensor mRotation;
    Sensor mLinearAcc;
    Sensor mGravity;
    // Make sure that this part is dynamically defined by the Browse Folder and
    // your CSV file name is "THE_SAME_FOLDER_NAME.csv"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        photoView = (ImageView) findViewById(R.id.photoView);
        btn1 = (Button) findViewById(R.id.btnRate1);
        btn2 = (Button) findViewById(R.id.btnRate2);
        btn3 = (Button) findViewById(R.id.btnRate3);
        btn4 = (Button) findViewById(R.id.btnRate4);
        btn_previous = (Button) findViewById(R.id.btnPrv);
        btn_skip = (Button) findViewById(R.id.btnSkip);
        textInd = (TextView) findViewById(R.id.textNowGrad);
        textCount = (TextView) findViewById(R.id.textProgressInd);
        textLast = (TextView) findViewById(R.id.textLastGrad);
        btn1.setOnClickListener(smileListener);
        btn2.setOnClickListener(smileListener);
        btn3.setOnClickListener(smileListener);
        btn4.setOnClickListener(smileListener);
        btn_previous.setOnClickListener(scrollListener);
        btn_skip.setOnClickListener(scrollListener);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mRotation = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        mLinearAcc = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        mGravity = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);

        setFinishOnTouchOutside(false);
        browserFolder();
    }

    View.OnClickListener smileListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (image_list == null || image_list.size() == 0) {
                showImages("/", 1000, 1000);
                return;
            }
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
            gradings.replaceAdd(image_list.get(img_counter), new Date(), smile_level, CURRENT_ACCELEROMETER_X, CURRENT_ACCELEROMETER_Y, CURRENT_ACCELEROMETER_Z, CURRENT_MAGNETIC_FIELD_X, CURRENT_MAGNETIC_FIELD_Y, CURRENT_MAGNETIC_FIELD_Z, CURRENT_GYROSCOPE_X, CURRENT_GYROSCOPE_Y, CURRENT_GYROSCOPE_Z, CURRENT_ROTATION_VECTOR_X, CURRENT_ROTATION_VECTOR_Y, CURRENT_ROTATION_VECTOR_Z, CURRENT_LINEAR_ACCELERATION_X, CURRENT_LINEAR_ACCELERATION_Y, CURRENT_LINEAR_ACCELERATION_Z, CURRENT_GRAVITY_X, CURRENT_GRAVITY_Y, CURRENT_GRAVITY_Z);        // Data override behavior is inherit from hash map
            gradeNext();
            // gradings.mergeAndSortByDir();
            CSV.writeToCSV(gradings, csvDir);
        }
    };

    View.OnClickListener scrollListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (image_list == null || image_list.size() == 0) {
                Toast.makeText(getApplicationContext(), getText(R.string.errMsg_noImage), Toast.LENGTH_SHORT).show();
                return;
            }
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
            updateSmileLevel();
        } else {
            img_counter--;  // If failed, roll back to previous
            Toast.makeText(getApplicationContext(), getText(R.string.errMsg_noNext), Toast.LENGTH_SHORT).show();
        }
        updateButtonSelect();
    }

    private void gradePrv() {
        img_counter--;
        if (changeImg()) {
            updateSmileLevel();
        } else {
            img_counter++;  // If failed, roll back to previous
            Toast.makeText(getApplicationContext(), getText(R.string.errMsg_noPrv), Toast.LENGTH_SHORT).show();
        }
        updateButtonSelect();
    }

    public void updateSmileLevel() {
        if (image_list.size() != 0) {
            int last_grade = getSmileLevel(image_list.get(img_counter));
            if (last_grade != -1) {
                smile_level = last_grade;   // The second element is the graded smile level
            } else {
                smile_level = 0;
            }
        }
    }

    public void updateButtonSelect() {
        // restore all button to default color
        btn1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.smile_1));
        btn2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.smile_2));
        btn3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.smile_3));
        btn4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.smile_4));
        // recolor the graded button
        switch (smile_level) {
            case 1:
                btn1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.smile_1_sel));
                break;
            case 2:
                btn2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.smile_2_sel));
                break;
            case 3:
                btn3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.smile_3_sel));
                break;
            case 4:
                btn4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.smile_4_sel));
                // needs break?
                break;
        }
        String text;
        if (1 <= smile_level && smile_level <= 4) {
            switch (smile_level) {
                case 1:
                    text = getText(R.string.textLastGrad_suffix) + " " + getText(R.string.btnRate1_name);
                    break;
                case 2:
                    text = getText(R.string.textLastGrad_suffix) + " " + getText(R.string.btnRate2_name);
                    break;
                case 3:
                    text = getText(R.string.textLastGrad_suffix) + " " + getText(R.string.btnRate3_name);
                    break;
                case 4:
                    text = getText(R.string.textLastGrad_suffix) + " " + getText(R.string.btnRate4_name);
                    break;
                default:
                    text = getText(R.string.textLastGrad_suffix) + " " + String.valueOf(smile_level);
                    break;
            }
        } else {
            text = getText(R.string.textLastGrad_default).toString();
        }
        textLast.setText(text);
    }

    public boolean changeImg() {
        if (image_list.size() == 0 || image_list.size() - 1 < img_counter || img_counter < 0) {
            return false;
        }
        File img = new File(image_list.get(img_counter));
        if (img.exists()) {
            //Loading Image from list
            showImages(img, 1000, 1000);
            String text1 = getText(R.string.textNowGrad_default) + " " + Util.truncateFileName(image_list.get(img_counter));
            textInd.setText(text1);
            String text2 = String.valueOf(img_counter + 1) + "/" + String.valueOf(image_list.size());
            textCount.setText(text2);
        } else {
            Toast.makeText(getApplicationContext(), getText(R.string.errMsg_imgNotExist_default) + image_list.get(img_counter), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void showImages(File img, int width, int height) {
        Picasso.with(getApplicationContext())
                .load(img)
                //.placeholder(R.drawable.placeholder)   // optional
                .error(R.drawable.smile_fail)
                .resize(width, height)
                .into(photoView);
    }

    private void showImages(String img, int width, int height) {
        Picasso.with(getApplicationContext())
                .load(img)
                //.placeholder(R.drawable.placeholder)   // optional
                .error(R.drawable.smile_fail)
                .resize(width, height)
                .into(photoView);
    }

    private int getSmileLevel(String img) {
        int result = gradings.find(img);
        if (result >= 0 && result < gradings.size()) {
            return (gradings.get(result)).getSMILE_LEVEL();
        } else {
            return -1;
        }
    }

    private int getSmileLevelCSV(String img) {
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

    private ArrayList<Integer> getSmileLevelwIndexCSV(String img) {
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
                browserFolder();
                return true;
            case R.id.setting_openResult:
                File file = new File(csvDir);
                Uri path = Uri.fromFile(file);
                Intent csvOpenIntent = new Intent(Intent.ACTION_VIEW);
                csvOpenIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                csvOpenIntent.setDataAndType(path, "text/csv");
                startActivity(csvOpenIntent);
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
        fileDialog = new FileDialog(this, mPath, ".jpg");
        fileDialog.addDirectoryListener(new FileDialog.DirectorySelectedListener() {
            @Override
            public void directorySelected(File directory) {
                Log.d(getClass().getName(), "selected file " + directory.toString());
            }
        });
//        fileDialog.addFileListener(new FileDialog.FileSelectedListener() {
//            public void fileSelected(File file) {
//                Log.d(getClass().getName(), "selected file " + file.toString());
//            }
//        });
//        fileDialog.setSelectDirectoryOption(true);
        fileDialog.showDialog(image_list);
    }

    public String getCsvDir() {
        return csvDir;
    }

    public void setCsvDir(String csvDir) {
        this.csvDir = csvDir;
    }

    public void setImg_counter(int img_counter) {
        this.img_counter = img_counter;
    }

    public int getImg_counter() {
        return img_counter;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //TODO get sensor data

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //leave blank
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer,SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mMagnetometer,SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mGyroscope,SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mRotation,SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mLinearAcc,SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mGravity,SensorManager.SENSOR_DELAY_NORMAL);
    }
}