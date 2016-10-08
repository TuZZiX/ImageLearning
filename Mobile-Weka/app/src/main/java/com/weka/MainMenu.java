package com.weka;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class MainMenu extends Activity{
	
	private Button classifier, clusterer, associate, exit;
	private Button csv2arffex1, csv2arffex2; // AB CB Adding the CSV conversion button in the main menu
	private Convert2Arff converter; //AB an external class method to convert all CSV files at once
	private final String csvDir = android.os.Environment.getExternalStorageDirectory()+"/DCIM/CSV/";
	private String oneCsv = "AnalysisDataWeka.csv";


	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);	
        setContentView(R.layout.mainmenu);
        findViewById();
        addbuttonlistener();
		converter = new Convert2Arff();
	}
    
    public void findViewById() {
    	classifier = (Button)findViewById(R.id.classifier_button);
    	clusterer = (Button)findViewById(R.id.clusterer_button);
    	associate = (Button)findViewById(R.id.associate_button);  	
    	exit = (Button)findViewById(R.id.exit_button);
		csv2arffex1 = (Button) findViewById(R.id.csv2arff_button_ex1); // AB Added button handler
		csv2arffex2 = (Button) findViewById(R.id.csv2arff_button_ex2); // CB Added button handler
    }
    
    public void addbuttonlistener (){
    	OnClickListener listener = new OnClickListener() {
			@Override
			public void onClick(View arg) {
				setTitle("DDDF");
				
				if (arg == classifier) {
					Intent intent = new Intent();
					intent.setClass(MainMenu.this, BuildClassifier.class);
					startActivity(intent);
				}else if (arg == clusterer) {  
					Intent intent = new Intent();
					intent.setClass(MainMenu.this, BuildClusterer.class);
					startActivity(intent);
				}else if (arg == associate) {  
					Intent intent = new Intent();
					intent.setClass(MainMenu.this, BuildAssociate.class);
					startActivity(intent);
				}else if (arg == exit) {
					Intent intent=new Intent(Intent.ACTION_MAIN);
					intent.addCategory(Intent.CATEGORY_HOME);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
				}
				/*
				Experimental, please read and adjust carefully.
				* */
				//AB Why not handle a quick CSV 2 ARFF conversion here :)
				else if (arg == csv2arffex1) {
					Context context = getApplicationContext();
					CharSequence text = "";
					int duration = Toast.LENGTH_SHORT;
					try {
						boolean fab = new File(csvDir+oneCsv).isFile();
						boolean ab = false;
						if (fab) ab = converter.ReadParseCSV(oneCsv);
						if (ab) text = "Converted and Saved Weka CSV";
						else if (!ab) text = "There were less than 50 entries of data!";
						else if (!fab) text = "No Weka CSV files were found in the CSV Folder!";
					} catch (Exception e) {
						e.printStackTrace();
					}
					Toast toast = Toast.makeText(context, text, duration); //AB CB HW1 just a simple pop-out msg for the user
					toast.show();
				}
				//CB CSV 2 ARFF conversion here for experiment 2
				else if (arg == csv2arffex2) {
					Context context = getApplicationContext();
					CharSequence text = "";
					int duration = Toast.LENGTH_SHORT;
					try {
						boolean fab = new File(csvDir+oneCsv).isFile();
						boolean ab = false;
						if (fab) ab = converter.ReadParseCSVex2(oneCsv);
						if (ab) text = "Converted and Saved Weka CSV";
						else if (!ab) text = "There were less than 50 entries of data!";
						else if (!fab) text = "No Weka CSV files were found in the CSV Folder!";
					} catch (Exception e) {
						e.printStackTrace();
					}
					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
				}
			}    		
    	};
    	classifier.setOnClickListener(listener);
    	clusterer.setOnClickListener(listener);
    	associate.setOnClickListener(listener);
    	exit.setOnClickListener(listener);

		csv2arffex1.setOnClickListener(listener); //AB adding listener
		csv2arffex2.setOnClickListener(listener); //AB adding listener
    }
}
