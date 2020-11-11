package com.heartmeetsoul.visionboard;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;



public class ViewDream extends AppCompatActivity
{
    SharedPreferences sharedPreferences;

TextView pend;
    ImageView ig;
    TextView title,brief1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences(DreamGrid.PREF_FILE_NAME, MODE_PRIVATE);
        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES)
        {
            //restartApp();
            setTheme(R.style.DarkTheme);

        }
        else if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_NO)
        {

            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.slide_show);
        this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        title=(TextView)findViewById(R.id.title);
        ig=(ImageView)findViewById(R.id.image_bg);
        pend=(TextView)findViewById(R.id.pend);
        brief1=(TextView)findViewById(R.id.description);
        int pos=sharedPreferences.getInt("image",0);
       // intent.putExtra("image",pos);

        @SuppressLint("WrongConstant")
        SQLiteDatabase db = openOrCreateDatabase("lakshay.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        String sql="SELECT Student.title,Student.description,Student.path,Student3.tag,Student.id from Student INNER JOIN Student3 ON Student.tagid=Student3.id  where Student.id= "+pos;
Cursor c=db.rawQuery(sql,null);
c.moveToFirst();
        String text=c.getString(0);
          String brief=c.getString(1);
        Drawable draw = new BitmapDrawable(BitmapFactory.decodeFile(c.getString(2)));
        String tags=c.getString(3);
        c.close();
//        if(getIntent().getIntExtra("flag",0)==0)
//        {
//            draw=  DreamGrid.items.get(pos).imageDrw;
//            text=DreamGrid.items.get(pos).name;
//            brief=DreamGrid.items.get(pos).brief;
//tags=DreamGrid.items.get(pos).tags;
//            //intent.putExtra("flag",0);
//        }
//        else if(getIntent().getIntExtra("flag",1)==1)
//        {
//            draw = DreamList.items.get(pos).imageDrw;
//           // intent.putExtra("flag",1);
//            text=DreamList.items.get(pos).name;
//            brief=DreamList.items.get(pos).brief;
//            tags=DreamList.items.get(pos).tags;
//        }

       // intent.putExtra("Title",text);
pend.setText(tags);

//        if(DreamGrid.items.get(pos).pending==0)
//        {
//            pend.setText("Pending");
//
//        }
//        else if(DreamGrid.items.get(pos).pending==1)
//        {
//            pend.setText("Completed");
//        }

        ig.setImageDrawable(draw);
        title.setText(text);
        brief1.setText((brief));


}

    @Override
    public void onResume()
    {
        super.onResume();
        this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        if (sharedPreferences.getBoolean("theme", true)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            //editor.putBoolean("theme",false);

            // restartApp();



        } else if (!sharedPreferences.getBoolean("theme", false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            // editor.putBoolean("theme",true);

            // restartApp();

        }
    }
    }
