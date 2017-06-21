package com.butznet.randomizer;

import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
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
import android.widget.TextView;
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
        nameList = (ListView) findViewById(R.id.namesListView);
        mDatabaseHelper = new DatabaseHelper(this);

        newNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = inputTextField.getText().toString();
                if (inputTextField.length() != 0) {
                    addData(newEntry);
                    inputTextField.setText("");
                    populateListView();
                } else {
                    toastMessage("You must put something in the text field");
                }
            }
        });
        configureBackButton();
        configureClearButton();
        populateListView();

    }

    private void configureClearButton() {
        clearButton = (Button) findViewById(R.id.clearListButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseHelper.removeAll();
                populateListView();
            }
        });
    }

    private void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the ListView.");

        Cursor cursor = mDatabaseHelper.getData();
        ArrayList<String> listData = new ArrayList<>();
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        nameList.setAdapter(adapter);

        while(cursor.moveToNext()) {
            listData.add(cursor.getString(1));
        }
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
                getWindow().getDecorView().setBackgroundColor(Color.WHITE);
                finish();
                overridePendingTransition(0, 0);
            }
        });
    }

}
