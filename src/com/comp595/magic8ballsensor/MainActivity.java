package com.comp595.magic8ballsensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener {

	final static String EXTRA_MESSAGE = "com.comp595.MainActivity.ANSWER";
	ImageButton ib1;
	TextView t;
	Toast popup;
	SensorManager manager;
	Sensor accel;
	static String ANSWER = "";

	String[] words = { "It is certain", "It is decidedly so",
			"Without a doubt", "Yes definitely", "You may rely on it",
			"As I see it yes", "Most likely", "Outlook good", "Yes",
			"Signs point to yes", "Reply hazy try again", "Ask again later",
			"Better not tell you now", "Cannot predict now",
			"Concentrate and ask again", "Don't count on it", "My reply is no",
			"My sources say no", "Outlook not so good", "Very doubtful" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		customtoast("Ask a question then flip");
		ib1 = (ImageButton) findViewById(R.id.imageButton1);
		t = (TextView) findViewById(R.id.text);

		manager = (SensorManager) getSystemService(SENSOR_SERVICE);
		accel = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		manager.registerListener(this, accel, SensorManager.SENSOR_DELAY_UI);
	}

	public void customtoast(String inputText) {
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.toast_layout,
				(ViewGroup) findViewById(R.id.toast_layout_root));

		TextView tv1 = (TextView) layout.findViewById(R.id.text);
		tv1.setText(inputText);

		Toast toast = new Toast(getApplicationContext());
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 20);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layout);
		toast.show();
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		int speedZ = (int) event.values[2];
		int index = (int) (Math.random() * 19 + 1);
		ANSWER = words[index].toString();
		
		if (speedZ < 0) {
			Intent intent = new Intent(this,SecondActivity.class);
			intent.putExtra(EXTRA_MESSAGE, ANSWER);
			startActivity(intent);
			manager.unregisterListener(this);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.about:
			Toast t = Toast.makeText(this,
					"Magic 8 Ball + Sensor \nby Oliver Barreto\nVersion 0.2",
					Toast.LENGTH_LONG);
			t.show();
			break;
		case R.id.exit:
			finish();
			System.exit(0);
			break;
		}
		return false;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		manager.unregisterListener(this);
		finish();
	}

	@Override
	protected void onPause() {
		super.onPause();
		manager.unregisterListener(this);
		finish();
	}

	@Override
	protected void onResume() {
		super.onResume();
		manager.registerListener(this, accel, SensorManager.SENSOR_DELAY_UI);
	}
}
