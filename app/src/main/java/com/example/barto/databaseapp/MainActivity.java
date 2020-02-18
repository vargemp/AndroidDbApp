package com.example.barto.databaseapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.MessageFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public Button btnInsert, btnDelete, btnSelect, btnSearch;
    public EditText txtName, txtAge, txtDelete, txtSearch;
    public TextView txtView;

    DataHandle dh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dh = new DataHandle(this);

        btnInsert = (Button)findViewById(R.id.btnInsert);
        btnDelete = (Button)findViewById(R.id.btnDelete);
        btnSelect = (Button)findViewById(R.id.btnSelect);
        btnSearch = (Button)findViewById(R.id.btnSearch);

        txtName = (EditText)findViewById(R.id.txtName);
        txtAge = (EditText)findViewById(R.id.txtAge);
        txtDelete = (EditText)findViewById(R.id.txtDelete);
        txtSearch = (EditText)findViewById(R.id.txtSearch);

        btnInsert.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnSelect.setOnClickListener(this);
        btnSearch.setOnClickListener(this);

        txtView = (TextView) findViewById(R.id.textArea);
    }

    public void showData(Cursor c){
        while(c.moveToNext()){
            Log.i(c.getString(1), c.getString(2));
        }
    }

    public void handleSearchName(String name){
        txtSearch.setText("");
        Cursor c = dh.searchName(name);

        if (c.getCount() < 1){
            Toast.makeText(this, "Row with given name not found", Toast.LENGTH_SHORT).show();
            return;
        }
        while(c.moveToNext()){
            Toast.makeText(this, c.getString(1) + " " + c.getString(2), Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, c.getString(0) + ": " + c.getString(1) + " " + c.getString(2), Toast.LENGTH_SHORT).show();
        }

    }

    public void insertResultToTextarea(Cursor c){
        txtView.setText("");
        while(c.moveToNext()){
            txtView.append(MessageFormat.format("{0}: {1} {2}\n", c.getString(0), c.getString(1), c.getString(2)));
//            txtView.append(" " + c.getString(1) + ", Age: " + c.getString(2) + "\n");
        }
    }

    public void clearInsertTextboxes(){
        txtName.setText("");
        txtAge.setText("");
    }

    public void delete(String name){
        txtDelete.setText("");
//        Cursor c = dh.searchName(name);
        int result = dh.searchName(name).getCount();
//        Boolean result = dh.delete(name);

        if (result < 1) {
            Toast.makeText(this, "Row with given name not found", Toast.LENGTH_SHORT).show();
            return;
        }
        dh.delete(name);
        Toast.makeText(this, "Row with given name succesfully deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btnInsert:
                dh.insert(txtName.getText().toString().trim(),
                        txtAge.getText().toString().trim());
                insertResultToTextarea(dh.selectAll());
                clearInsertTextboxes();
                Toast.makeText(this, "wpisalem", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnSelect:
                showData(dh.selectAll());
                break;
            case R.id.btnSearch:
                handleSearchName(txtSearch.getText().toString().trim());
                break;
            case R.id.btnDelete:
                delete(txtDelete.getText().toString().trim());
                insertResultToTextarea(dh.selectAll());
                break;
        }
    }
}
