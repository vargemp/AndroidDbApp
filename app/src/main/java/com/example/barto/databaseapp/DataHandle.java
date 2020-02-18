package com.example.barto.databaseapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Message;
import android.util.Log;
import android.database.Cursor;
import android.widget.Toast;

import java.text.MessageFormat;

/**
 * Created by barto on 27.11.2017.
 */


public class DataHandle {
    private SQLiteDatabase db;

    public static final String TABLE_ROW_ID = "_id";
    public static final String TABLE_ROW_NAME = "name";
    public static final String TABLE_ROW_AGE = "age";

    private static final String DB_NAME = "address_book_db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_N_AND_A = "names_and_addresses";

    private class CustomSQLiteOpenHelper extends SQLiteOpenHelper{
        public CustomSQLiteOpenHelper(Context context){
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            String newTableQueryString = "create table "
                    + TABLE_N_AND_A + " ("
                    + TABLE_ROW_ID
                    + " integer primary key autoincrement not null,"
                    + TABLE_ROW_NAME
                    + " text not null,"
                    + TABLE_ROW_AGE
                    + " text not null);";

            Log.i("Create:", newTableQueryString);
            db.execSQL(newTableQueryString);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

        }
    }

    public DataHandle(Context context){
        CustomSQLiteOpenHelper helper = new CustomSQLiteOpenHelper(context);
        db = helper.getWritableDatabase();
        clearTable();
    }

    public void clearTable(){
        String clearTableQuery = "DELETE FROM " + TABLE_N_AND_A;
        String resetIndentityQuery = "DELETE FROM sqlite_sequence WHERE name='" + TABLE_N_AND_A + "';";
        db.execSQL(clearTableQuery);
        db.execSQL(resetIndentityQuery);
        Log.i("res", "----------DELETING ALL FROM MAIN TABLE-----------");
    }

    public void insert(String name, String age){
        String query = "INSERT INTO " + TABLE_N_AND_A + " (" +
                TABLE_ROW_NAME + ", " + TABLE_ROW_AGE + ") " +
                "VALUES (" +
                "'" + name + "'" + ", " + "'" + age + "'" + ");";

        Log.i("insert() = ", query);
        db.execSQL(query);
    }

    public void delete(String name){
        String query = "DELETE FROM " + TABLE_N_AND_A +
                " WHERE " + TABLE_ROW_NAME +
                " = '" + name + "';";
        Log.i("delete() = ", query);
        db.execSQL(query);
    }

    public Cursor selectAll(){
        Log.i("", "---------SELECTING ALL FROM TABLE--------------------");
        Cursor c = db.rawQuery("SELECT *" + " FROM " + TABLE_N_AND_A, null);
        return c;
    }

    public Cursor searchName(String name){
        name = name.trim();
        String query = "SELECT " +
                TABLE_ROW_ID + ", " +
                TABLE_ROW_NAME +
                ", " + TABLE_ROW_AGE +
                " FROM " +
                TABLE_N_AND_A + " WHERE " +
                TABLE_ROW_NAME + " = '" + name + "';";
        Log.i("searchName() = ", query);
        Cursor c = db.rawQuery(query, null);
        return c;
    }
}
