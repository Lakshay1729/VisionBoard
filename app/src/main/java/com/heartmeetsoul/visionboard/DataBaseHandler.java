package com.heartmeetsoul.visionboard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

class DataBaseHandler  extends SQLiteOpenHelper {
    Context ctx;
    public DataBaseHandler(@Nullable Context context) {
        super(context, "lakshay.db", null, 1);
        ctx=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
           db.execSQL("create table if not exists Student2 (id integer primary key,img blob not null)");
      //  Toast.makeText(ctx, "Created", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
           db.execSQL("drop table if exists Student2");
       // Toast.makeText(ctx, "Upgrade", Toast.LENGTH_SHORT).show();
    }

    public Boolean insertimage(String x,Integer i)
    {
        Toast.makeText(ctx, ""+x, Toast.LENGTH_SHORT).show();
        SQLiteDatabase db=this.getWritableDatabase();
        new DataBaseHandler(ctx).onCreate(db);
        //new DataBaseHandler(ctx).onUpgrade(db,1,2);
        //Toast.makeText(ctx, "", Toast.LENGTH_SHORT).show();
        try
        {
           // Toast.makeText(ctx, ""+db, Toast.LENGTH_SHORT).show();
            FileInputStream fs=new FileInputStream(x);
           // Toast.makeText(ctx, ""+fs, Toast.LENGTH_SHORT).show();
            byte[] imgbyte=new byte[fs.available()];
            //Toast.makeText(ctx, ""+imgbyte, Toast.LENGTH_SHORT).show();
            fs.read(imgbyte);
            ContentValues contentValues=new ContentValues();
            contentValues.put("id",i);
            contentValues.put("img",imgbyte);
            //Toast.makeText(ctx, ""+contentValues, Toast.LENGTH_SHORT).show();
           long r= db.update("Student2",contentValues,"id=1",null);


           // Toast.makeText(ctx, ""+r, Toast.LENGTH_SHORT).show();
           if(r>0)
           {
             //  Toast.makeText(ctx, "Image Dali", Toast.LENGTH_SHORT).show();
           }
            fs.close();
            return true;
        }
        catch (FileNotFoundException ex)
        {
           // Toast.makeText(, ""+ex, Toast.LENGTH_SHORT).show();
            //Toast.makeText(ctx, ""+ex, Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
            return false;
        }
        catch (IOException ex)
        {
           // Toast.makeText(ctx, ""+ex, Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
            return false;
        }
    }

    public Bitmap getImage(Integer id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Bitmap bt=null;
        Cursor cursor=db.rawQuery("select * from Student2 where id="+id ,new String[]{String.valueOf(id)});
        if(cursor.moveToNext())
        {
            byte[] imag=cursor.getBlob(1);
            bt= BitmapFactory.decodeByteArray(imag,0,imag.length);

        }
        return  bt;

    }


}
