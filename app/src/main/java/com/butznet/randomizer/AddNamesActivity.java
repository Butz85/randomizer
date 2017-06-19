package com.butznet.randomizer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class AddNamesActivity extends AppCompatActivity {
    private EditText inputTextField;
    private Button newNameButton;
    private ListView nameList;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_names);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        inputTextField = (EditText) findViewById(R.id.addNameInputText);
        newNameButton = (Button) findViewById(R.id.addNewNameButton);
        nameList = (ListView) findViewById(R.id.namesListView);
        arrayList = new ArrayList<>();

        newNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.add(inputTextField.getText().toString());
                adapter.notifyDataSetChanged();
            }
        });

        adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        nameList.setAdapter(adapter);

        configureBackButton();
    }

    private void configureBackButton() {
        Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, 0);
            }
        });
    }

}
