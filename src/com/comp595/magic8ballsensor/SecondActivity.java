package com.comp595.magic8ballsensor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends Activity {

	TextView t;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);

		t = (TextView) findViewById(R.id.textView1);
		Intent i = getIntent();
		t.setText(i.getStringExtra(MainActivity.EXTRA_MESSAGE));
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
		Intent i = new Intent(this,MainActivity.class);
		startActivity(i);
	}

}
