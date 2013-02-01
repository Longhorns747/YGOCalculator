/**
 * @author Ethan Shernan
 * @version 1.1
 * IntroActivity for the YGOCalculator Android application
 */

package com.shernan.ygocalculator;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class IntroActivity extends Activity {
	
	public static final String EXTRA_DUELIST1 = "com.shernan.EXTRA_DUELIST1";
	public static final String EXTRA_DUELIST2 = "com.shernan.EXTRA_DUELIST2";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intro);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_intro, menu);
		return true;
	}
	
	public void startCalc(View v){
		//Create Intent
		Intent i = new Intent(this, CalcActivity.class);
		
		//Get Duelist Names
		EditText d1 = (EditText) findViewById(R.id.duelist1);
		String duelist1 = d1.getText().toString();
		
		EditText d2 = (EditText) findViewById(R.id.duelist2);
		String duelist2 = d2.getText().toString();
		
		//Prepare Data for Activity Transfer
		i.putExtra(EXTRA_DUELIST1, duelist1);
		i.putExtra(EXTRA_DUELIST2, duelist2);
		
		//Switch activities
		startActivity(i);
		
	}

}
