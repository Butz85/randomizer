package com.butznet.randomizer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public TextView tvScreen;
    public TextView tvButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random random = new Random();
                int n = 10 + random.nextInt(20);
                tvScreen = (TextView)findViewById(R.id.onScreenView);
//                tvScreen = (TextView)findViewById(R.id.buttonTextView);
                final Handler handler = new Handler();

//                tvScreen.setText("");

                for (int i = 0; i < n; i++ ) {
                    final int finalI = i;
                    handler.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            tvScreen.setText(R.string.answer_yes);
                            tvScreen.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorTextFieldBlack));
                            getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                            if ((finalI % 2) == 0) {
                                getWindow().getDecorView().setBackgroundColor(Color.RED);
                                tvScreen.setText(R.string.answer_no);
                                tvScreen.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorTextFieldWhite));
                            }
                        }
                    }, (100 * i + i * i ));
                }
            }
        });
        configureAddNamesButton();
    }


    private void configureAddNamesButton() {
        Button addNamesButton = (Button) findViewById(R.id.addNamesButton);
        addNamesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddNamesActivity.class));
                overridePendingTransition(0, 0);
            }
        });
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
