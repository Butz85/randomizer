package com.butznet.randomizer;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public TextView tvScreen;
    DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        mSQLiteAdapter = new SQLiteAdapter();
        mDatabaseHelper = new DatabaseHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Cursor data = mDatabaseHelper.getData();
                Random random = new Random();
                int n = 10 + random.nextInt(20) + data.getCount();
                tvScreen = (TextView)findViewById(R.id.onScreenView);
                final Handler handler = new Handler();

                for (int i = 0; i < n; i++ ) {
                    final int finalI = i;
                    handler.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            if (data.getCount() == 0 ) {
                                tvScreen.setText(R.string.answer_yes);
                                tvScreen.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorTextFieldBlack));
                                getWindow().getDecorView().setBackgroundColor(Color.GREEN);

                                if ((finalI % 2) == 0) {
                                    getWindow().getDecorView().setBackgroundColor(Color.RED);
                                    tvScreen.setText(R.string.answer_no);
                                    tvScreen.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorTextFieldWhite));
                                }
                            } else {
                                tvScreen.setText(mDatabaseHelper.getRandomData().getString(1));
                                getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                                tvScreen.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorTextFieldBlack));
                                if ((finalI % 2) == 0) {
                                    getWindow().getDecorView().setBackgroundColor(Color.RED);
                                    tvScreen.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorTextFieldWhite));
                                }
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
