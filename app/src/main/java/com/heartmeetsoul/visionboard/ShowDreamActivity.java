package com.heartmeetsoul.visionboard;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.Toolbar;



import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class ShowDreamActivity extends AppCompatActivity {

    ImageView imageView;
TextView tx1;
ImageButton ig;
ImageButton eye;
ImageButton share;
ImageButton delete;
    Drawable draw;
    int pos;
    SharedPreferences sharedPreferences;
    public static final String PREF_FILE_NAME = "PrefFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setTheme(R.style.DarkTheme);
        sharedPreferences = getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            //restartApp();
            setTheme(R.style.DarkTheme);

        } else if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {

            setTheme(R.style.AppTheme);
        }
       super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show_dream);
        initToolbar();
    }

    private void initToolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.setBackgroundColor(getResources().getColor(R.color.transparent));
        getSupportActionBar().setTitle(null);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Tools.setSystemBarColor(this,R.color.transparent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_basic, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else
            {
           // Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
             }
        return super.onOptionsItemSelected(item);
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
        }

        else if (!sharedPreferences.getBoolean("theme", false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            // editor.putBoolean("theme",true);
            // restartApp();

        }

        imageView = (ImageView) findViewById(R.id.image);
        tx1 = (TextView) findViewById(R.id.tx1);
        eye = (ImageButton) findViewById(R.id.eye);

        share = (ImageButton) findViewById(R.id.share);

        delete = (ImageButton) findViewById(R.id.delete);




        this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        ig = (ImageButton) findViewById(R.id.edit);



//        if(getIntent().getIntExtra("flag",0)==0)
//        {
//            draw = DreamGrid.items.get(pos).imageDrw;
//            ((TextView) findViewById(R.id.description)).setText(DreamGrid.items.get(pos).brief);
//            ((TextView) findViewById(R.id.tag)).setText(DreamGrid.items.get(pos).tags);
//            intent.putExtra("flag", 0);
//        }
//        else if(getIntent().getIntExtra("flag",1)==1)
//        {
//            draw = DreamList.items.get(pos).imageDrw;
//            ((TextView)findViewById(R.id.description)).setText(DreamList.items.get(pos).brief);
//            ((TextView)findViewById(R.id.tag)).setText(DreamList.items.get(pos).tags);
//            intent.putExtra("flag",1);
//        }

        pos = sharedPreferences.getInt("image",0);
        @SuppressLint("WrongConstant") SQLiteDatabase db = openOrCreateDatabase("lakshay.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        String sql="SELECT Student.title,Student.description,Student.path,Student3.tag,Student.id from Student INNER JOIN Student3 ON Student.tagid=Student3.id where Student.id= "+pos;
        Cursor c=db.rawQuery(sql,null);

c.moveToFirst();
        draw=new BitmapDrawable(BitmapFactory.decodeFile(c.getString(2)));
        ((TextView)findViewById(R.id.description)).setText(c.getString(1));
        ((TextView) findViewById(R.id.tag)).setText(c.getString(3));
        final Intent intent = new Intent(this, EditDreamActivity.class);

        c.close();
        intent.putExtra("image", pos);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Bitmap bitmap;
                OutputStream output;

                // Retrieve the image from the res folder
                bitmap =  ((BitmapDrawable)draw).getBitmap();


                File file = new File(Environment.getExternalStorageDirectory()
                        + File.separator + ""
                        + System.currentTimeMillis() + ".png");

                try {

                    // Share Intent
                    Intent share = new Intent(Intent.ACTION_SEND);
                    // Type of file to share
                    share.setType("*/*");

                    output = new FileOutputStream(file);

                     //Compress into png format image from 0% - 100%
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
                    output.flush();
                    output.close();

                    // Locate the image to Share
                    Uri uri = Uri.fromFile(file);

                    // Pass the image into an Intnet
                    share.putExtra(Intent.EXTRA_STREAM, uri);
                    share.putExtra(Intent.EXTRA_TEXT, "Hey there,I want to share one of my goals with you which I created on this amazing app https://www.playstore.com/com.heartmeetsoul.visionboard ,which helps me to maintain a mindset for my dreams and goals in long term.\n\nStart using Vision Board and don\'t let your dreams be,just dreams ,make them reality");
                    share.putExtra(Intent.EXTRA_SUBJECT," Share Vision Board");

                    // Show the social share chooser list
                    startActivity(Intent.createChooser(share, "Share Dream"));

                }
                catch (Exception e) {
                    // TODO Auto-generated catch block
                    // e.printStackTrace();
                   // Toast.makeText(ShowDreamActivity.this, ""+e, Toast.LENGTH_SHORT).show();
                }
            }
        });
        final String text=getIntent().getStringExtra("Title");
        intent.putExtra("Title",text);

        eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getApplicationContext(),ViewDream.class);
//                intent1.putExtra("title",text);
//                intent1.putExtra("pos",pos);
//                intent1.putExtra("flag",getIntent().getIntExtra("flag",0));
                startActivity(intent1);

            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder=new AlertDialog.Builder( new ContextThemeWrapper(ShowDreamActivity.this, R.style.Dialog_Theme));
                builder.setMessage("Are you sure you want to delete this dream");
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try
                        {
                            @SuppressLint("WrongConstant")
                            SQLiteDatabase db=openOrCreateDatabase("lakshay.db",
                                    SQLiteDatabase.CREATE_IF_NECESSARY, null);
                            int re=db.delete("Student","id= "+sharedPreferences.getInt("image",0),null);

                            if(re>0) {
                                finish();
                            }

                        }
                        catch(Exception ex){

                            Log.d("Exception",ex.toString());
                        }
                    }
                });
                builder.create();
                builder.show();

            }
        });

        imageView.setImageDrawable(draw);
        tx1.setText(text);


        ig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
                finish();
            }
        });
        initToolbar();
    }

}
