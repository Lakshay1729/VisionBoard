package com.heartmeetsoul.visionboard;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;



public class SplashScreen extends AppCompatActivity {
    SharedPreferences.Editor editor;
    public static final String PREF_FILE_NAME = "PrefFile";
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sharedPreferences = getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE);
        if(sharedPreferences.getBoolean("pinlock",false))
        {
            startActivity(new Intent(getApplicationContext(),FingerPrintLock.class));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        try {
            @SuppressLint("WrongConstant")
            SQLiteDatabase db = openOrCreateDatabase("lakshay.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
            String st = "CREATE TABLE IF NOT EXISTS Student " + "(id integer primary key AUTOINCREMENT ,title TEXT ,tagid Integer,description TEXT,path Text,date Text,pending Integer)";
            db.execSQL(st);

            String string = "CREATE TABLE IF NOT EXISTS Student3 " + "  (id integer primary key AUTOINCREMENT,tag TEXT)";
            db.execSQL(string);

//            db.execSQL("insert into Student3 (id integer primary key AUTOINCREMENT,tag TEXT)" + "values("+"'Add New Category'"+","++","
//                    + "\"" + docnam + "\"" + ") ;");

            String count = "SELECT count(*) FROM Student3";
            Cursor mcursor = db.rawQuery(count, null);
            mcursor.moveToFirst();
            int icount = mcursor.getInt(0);
            if(icount==0) {
                db.execSQL("insert into 'Student3'('tag') values ('Add New Category'),('Career'),('Education'),('Visit'),('Relationship')");

            }
//            ContentValues values = new ContentValues();
//            values.put( "tag","Add New Category");

           // Long re = db.insert("Student3", null, values);
//            if (re > 0) {
//                Log.d("Table Created", "Created");
//            }
        }
        catch (Exception ex)
        {
            Log.d("Exception",ex.toString());
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(!sharedPreferences.getBoolean("pinlock",false))
                {
                    startActivity(new Intent(getApplicationContext(),DreamList.class));
                    finish();
                }
            }
        },1800);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        finish();
    }
}
