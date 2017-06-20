package com.butznet.randomizer;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddNamesActivity extends AppCompatActivity {
    private static final String TAG = "AddNamesActivity";

    DatabaseHelper mDatabaseHelper;
    private EditText inputTextField;
    private Button newNameButton, backButton, clearButton;
    private ListView nameList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_names);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inputTextField = (EditText) findViewById(R.id.addNameInputText);
        newNameButton  = (Button) findViewById(R.id.addNewNameButton);
        inputTextField = (EditText) findViewById(R.id.addNameInputText);
        nameList = (ListView) findViewById(R.id.namesListView);
        mDatabaseHelper = new DatabaseHelper(this);
        populateListView();

        newNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = inputTextField.getText().toString();
                if (inputTextField.length() != 0) {
                    addData(newEntry);
                    inputTextField.setText("");
                } else {
                    toastMessage("You must put something in the text field");
                }
            }
        });

        configureBackButton();
        configureClearButton();

    }

    private void configureClearButton() {
        clearButton = (Button) findViewById(R.id.clearListButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseHelper.removeAll();
            }
        });
    }

    private void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the ListView.");

        Cursor data = mDatabaseHelper.getData();
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()) {
            listData.add(data.getString(1));
        }
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        nameList.setAdapter(adapter);
    }

    public void addData(String newEntry) {
        boolean insertData = mDatabaseHelper.addData(newEntry);

        if(insertData) {
            toastMessage("Data Successfully Inserted");
        } else {
            toastMessage("Something Went Wrong");
        }
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void configureBackButton() {
        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, 0);
            }
        });
    }

}
