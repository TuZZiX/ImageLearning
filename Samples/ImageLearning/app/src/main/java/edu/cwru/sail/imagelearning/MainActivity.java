package edu.cwru.sail.imagelearning;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class MainActivity extends Activity {

    private int smile_level;

    private ImageView photo;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn_before;
    private Button btn_next;

    private final String imgDir = Environment.getExternalStorageDirectory().toString() + "/DCIM/sample1";

    private final String csvDir = Environment.getExternalStorageDirectory().toString()+ File.separator + "result.csv";
    //Make sure that this part is dynamically defined by the Browse Folder and
    // your CSV file name is "THE_SAME_FOLDER_NAME.csv"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        photo = (ImageView) findViewById(R.id.photo);

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn_before = (Button) findViewById(R.id.btn_before);
        btn_next = (Button) findViewById(R.id.btn_next);

        btn1.setOnClickListener(smileListener);
        btn2.setOnClickListener(smileListener);
        btn3.setOnClickListener(smileListener);
        btn4.setOnClickListener(smileListener);
        btn_before.setOnClickListener(scrollListener);
        btn_next.setOnClickListener(scrollListener);

        File img = new File(imgDir + File.separator + "smile1.jpg");

        File csv = new File(csvDir);

        if (img.exists()) {
            //Loading Image from URL
            Picasso.with(this)
                    //.load("https://www.simplifiedcoding.net/wp-content/uploads/2015/10/advertise.png")
                    .load(img)
                    //.placeholder(R.drawable.placeholder)   // optional
                    //.error(R.drawable.error)      // optional
                    .resize(1000, 1000)                        // optional
                    .into(photo);
        }


    }

    View.OnClickListener smileListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn1:
                    smile_level = 1;
                    break;
                case R.id.btn2:
                    smile_level = 2;
                    break;
                case R.id.btn3:
                    smile_level = 3;
                    break;
                case R.id.btn4:
                    smile_level = 4;
                    break;
            }
            writeToCSV(imgDir,smile_level);
        }
    };

    View.OnClickListener scrollListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_before:
                    //TODO
                    break;
                case R.id.btn_next:
                    //TODO
                    break;
            }
        }
    };

    //TODO
    public void writeToCSV(final String img, int smile_level) {
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

    public int getSmileLevelCSV(final String img) {
        String[] reading;
        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(csvDir));
            List csvRead = reader.readAll();
            for (int it = csvRead.size(); it > 0; it --) {
                reading =  ((String[])(csvRead.get(it)));
                if (reading[0] == img) {
                    return Integer.parseInt(reading[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
