package com.butznet.randomizer;

import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.cuboid.cuboidcirclebutton.CuboidButton;

import java.util.ArrayList;

public class AddNamesActivity extends AppCompatActivity {
    private static final String TAG = "AddNamesActivity";

    DatabaseHelper mDatabaseHelper;
    private EditText inputTextField;
    private CuboidButton newNameButton, backButton, clearButton;
    private ListView nameList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_names);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inputTextField = (EditText) findViewById(R.id.addNameInputText);
        newNameButton  = (CuboidButton) findViewById(R.id.addNewNameButton);
        nameList = (ListView) findViewById(R.id.namesListView);
        mDatabaseHelper = new DatabaseHelper(this);

        inputTextField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

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
        final AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }

        clearButton = (CuboidButton) findViewById(R.id.clearListButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("Delete entry")
                .setMessage("Are you sure you want to delete this entry?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mDatabaseHelper.removeAll();
                        populateListView();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_notification_clear_all)
                .show();
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
        backButton = (CuboidButton) findViewById(R.id.backButton);
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
