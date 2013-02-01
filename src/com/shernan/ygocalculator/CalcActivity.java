/**
 * @author Ethan Shernan
 * @version 1.1
 * The Calculation activity for the YGOCalculator Android App
 */

package com.shernan.ygocalculator;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class CalcActivity extends Activity {
	
	private TextView LP1;
	private TextView LP2;
	private TextView LPEffect;
	private int dmgState;
	private int mathState;
	private int clearState;
	private Integer mathLP;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calc);
		
		//Need to set up duelist names in text boxes
		Intent i = getIntent();
		String duelist1 = i.getStringExtra(IntroActivity.EXTRA_DUELIST1);
		String duelist2 = i.getStringExtra(IntroActivity.EXTRA_DUELIST2);
		
		//Get textboxes to put in duelist names
		TextView duelistView1 = (TextView) findViewById(R.id.duelist1_name);
		TextView duelistView2 = (TextView) findViewById(R.id.duelist2_name);
		
		//Get Lifepoint boxes
		LP1 = (TextView) findViewById(R.id.duelist1_LP);
		LP2 = (TextView) findViewById(R.id.duelist2_LP);
		LPEffect = (TextView) findViewById(R.id.effectLP);
		
		//Set up global variables
		dmgState = 0;
		mathState = 0;
		mathLP = 0;
		clearState = 1;
		
		LPEffect.setText("0");
		
		//Set duelist names
		duelistView1.setText(duelist1);
		duelistView2.setText(duelist2);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_calc, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * Will make LPEffect switch from damage to recovery and sets the dmgState accordingly
	 * @param v
	 */
	
	public void dmgRecSwitch(View v){
		Button dmgRec = (Button) findViewById(R.id.btn_dmgRec);
		
		if(dmgRec.getText().equals("Damage")){
			dmgState = 1;
			dmgRec.setText("Recover");
		}
		else{
			dmgState = 0;
			dmgRec.setText("Damage");
		}
		
	}
	
	/**
	 * Determines the amount of LP to affect, the duelist to affect, and damages/heals them
	 * @param v
	 */
	public void affectDuelist(View v){
		Button btn = (Button) v;
		TextView currLP;
		
		//Determine proper duelist to affect
		if(btn.getText().toString().equals("Duelist 1"))
			currLP = LP1;
		else
			currLP = LP2;
		
		//Get LPeffect
		Integer LP = Integer.parseInt(currLP.getText().toString());
		int effect = Integer.parseInt(LPEffect.getText().toString());
		
		//Add or subtract LP accordingly
		if(dmgState == 0)
			LP -= effect;
		else
			LP += effect;
		
		//Set LP and clear state
		currLP.setText(LP.toString());
		LPEffect.setText("0");
		clearState = 1;
	}
	
	/**
	 * Changes the amount to effect the target duelist
	 * @param v
	 */
	
	public void effectLPChange(View v){
		Button btn = (Button) v;
		
		//Don't go over thousands place
		if(btn.getText().toString().length() == 4)
			return;
		
		String num = btn.getText().toString();
		
		String currText = LPEffect.getText().toString();
		
		//If clear, start the number over, otherwise, append last pressed number to total
		if(clearState == 0)
			currText += num;
		else{
			currText = num;
			if(!(currText.equals("0")))
			clearState = 0;
		}
		
		LPEffect.setText(currText);
		
	}
	
	/**
	 * Do math onto the effectLP
	 * @param v
	 */
	public void effectLPMath(View v){
		Button btn = (Button) v;
		
		String op = btn.getText().toString();
		
		//Determines the mathematical operation to make onto the LP
		if(op.equals("+"))
			mathState = 0;
		if(op.equals("-"))
			mathState = 1;
		if(op.equals("x"))
			mathState = 2;
		if(op.equals("/"))
			mathState = 3;
		
		if(LPEffect.getText().toString().length() != 0)
			mathLP = Integer.parseInt(LPEffect.getText().toString());
		else
			mathLP = 0;
		
		LPEffect.setText("0");
		clearState = 1;
	}
	
	/**
	 * Perform the actual math determined by effectLPMath
	 * @param v
	 */
	public void effectLPEquals(View v){
		Integer op = Integer.parseInt(LPEffect.getText().toString());
		
		//Determine the operation to perform, do the math
		if(mathState == 0)
			mathLP += op;
		if(mathState == 1)
			mathLP -= op;
		if(mathState == 2)
			mathLP *= op;
		if(mathState == 3)
			mathLP /= op;
		
		LPEffect.setText(mathLP.toString());
		mathLP = 0;
		clearState = 1;
	}
	
	/**
	 * Clears the LPEffect text
	 * @param v
	 */
	
	public void clear(View v){
		clearState = 1;
		LPEffect.setText("0");
	}
	
	/**
	 * Randomly flips a coin and shows result on a popup window
	 * @param v
	 */
	public void coinFlip(View v){
		int coin = (int)(Math.random() * 10) % 2;
		String flip;
		
		if(coin == 0)
			flip = "Heads!";
		else
			flip = "Tails!";
		
		//Builds and displays a dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(flip);
	    builder.setTitle("Coin Flip");
	    
	    AlertDialog dialog = builder.create();
	    dialog.show();
		
	}
	
	public void diceRoll(View v){		
		Integer dice = ((int)(Math.random() * 10) % 6) + 1;
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(dice.toString());
	    builder.setTitle("Dice Roll");
	    
	    AlertDialog dialog = builder.create();
	    dialog.show();
		
	}
	
	/**
	 * Resets everything to it's original values
	 * @param v
	 */
	public void reset(View v){
		LPEffect.setText("0");
		LP1.setText("8000");
		LP2.setText("8000");
		clearState = 1;
	}

}
