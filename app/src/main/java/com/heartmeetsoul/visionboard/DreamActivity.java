package com.heartmeetsoul.visionboard;

//import android.support.design.widget.NavigationView;
//import android.support.v4.view.GravityCompat;
//import android.support.v4.widget.DrawerLayout;
//import android.support.v7.app.ActionBar;
//import android.support.v7.app.ActionBarDrawerToggle;
//import android.support.v7.app.AppCompatActivity;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
//import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import com.google.android.material.navigation.NavigationView;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.Arrays;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.aprilapps.easyphotopicker.MediaFile;
import pl.aprilapps.easyphotopicker.MediaSource;


public class DreamActivity extends AppCompatActivity {

    static   DrawerLayout drawer;
    private static final int CAMERA_PIC_REQUEST = 0;
    private static final int SELECT_PICTURE = 1;
    CircularImageView cim;
    public static final String MY_PREFS_NAME = "true";
    private ActionBar actionBar;
  //  RelativeLayout layout;
    private Toolbar toolbar;
    ImageView ig;
    NavigationView nav;
    public static  View layout;
    private EasyImage easyImage1;
    SharedPreferences.Editor editor;
    public static final String PREF_FILE_NAME = "PrefFile";
    SharedPreferences sharedPreferences;
    static int tf;
int firsttime=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {

            setTheme(R.style.DarkTheme);

        } else if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {

            setTheme(R.style.AppTheme);
        }
        tf = 0;


        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_dream);
        Tools.setSystemBarColor(this, R.color.blue_A200);
        Tools.setSystemBarLight(this);
        this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        firsttime = 1;
        sharedPreferences = getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            //editor.putBoolean("theme",true);
        }


        initToolbar();


        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {

        } else {
            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE) && shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                Toast.makeText(this, "External Storage and Camera Permission Required", Toast.LENGTH_SHORT).show();
            }

            requestPermissions(new String[]{Manifest.permission.CAMERA}, 9);
        }

        initNavigationMenu();
        nav = (NavigationView) findViewById(R.id.nav_view);
        layout = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.include_drawer_header_profile_image, nav, false);


    }


    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
       // toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.setElevation(20);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        toolbar.setNavigationIcon(R.drawable.ic_menu);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setSupportActionBar(toolbar);

        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Vision Board");
    }

    public void initNavigationMenu() {
        NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);
         drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(final MenuItem item) {

                //Toast.makeText(getApplicationContext(), item.getTitle() + " Selected", Toast.LENGTH_SHORT).show();
                switch (item.getItemId()) {
                    case R.id.nav_activity: {

                        if (SettingsActivity.layout)
                            startActivity(new Intent(getApplicationContext(), DreamList.class));
                        else if (!SettingsActivity.layout)
                            startActivity(new Intent(getApplicationContext(), DreamGrid.class));
                        actionBar.setTitle(item.getTitle());
                        drawer.closeDrawers();
                        //Toast.makeText(DreamActivity.this, ""+SettingsActivity.layout, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    case R.id.nav_explore: {
                        startActivity(new Intent(getApplicationContext(), AddDream.class));
                        actionBar.setTitle(item.getTitle());
                        drawer.closeDrawers();
                        return true;
                    }
                    case R.id.nav_videos: {
                        startActivity(new Intent(getApplicationContext(), SliderShow.class));
                        actionBar.setTitle(item.getTitle());
                        drawer.closeDrawers();
                        return true;
                    }
                    case R.id.nav_settings: {
                        startActivity(new Intent(getApplicationContext(), AboutUs.class));
                        actionBar.setTitle(item.getTitle());
                        drawer.closeDrawers();
                        return true;
                    }
                    case R.id.nav_message: {
                        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                        actionBar.setTitle(item.getTitle());
                        drawer.closeDrawers();
                        return true;
                    }


                }
                actionBar.setTitle(item.getTitle());

                drawer.closeDrawers();
                return true;
            }
        });

        // open drawer at start
        // drawer.openDrawer(GravityCompat.START);
    }

//        @Override
//        public boolean onCreateOptionsMenu(Menu menu) {
//            getMenuInflater().inflate(R.menu.menu_search_setting, menu);
//            return true;
//        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != android.R.id.home) {
           // Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();

        }
        return super.onOptionsItemSelected(item);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK)
        {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
//                String selectedImagePath = getPath(selectedImageUri);
//                //tv.setText(selectedImagePath);
               // ((ImageView) findViewById(R.id.avatar)).setImageURI(selectedImageUri);
            }

            if (requestCode == CAMERA_PIC_REQUEST) {
                try {
                    Bitmap image = (Bitmap) data.getExtras().get("data");
                    //ImageView imageview = (ImageView) findViewById(R.id.avatar); //sets imageview as the bitmap
                  //  imageview.setImageBitmap(image);
                }
                catch (Exception ex)
                {

                }
            }

            easyImage1.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
                @Override
                public void onMediaFilesPicked(@NonNull MediaFile[] imageFiles, MediaSource source)
                { View v=(View)findViewById(android.R.id.content);
//                    try {
//                        Bitmap bit = new DataBaseHandler(DreamActivity.this).getImage(1);
//
//                        Glide.with(DreamActivity.this).load(bit).into(cim);
//                    }
//                    catch (Exception ex)
//                    {
//                        Snackbar.make(v,"Upload a pic"+ex,Snackbar.LENGTH_LONG).show();
//
//                    }
                    // onPhotosReturned(imageFiles);

                    Picasso.get().load(Arrays.asList(imageFiles).get(0).getFile()).into(cim);
                    //new DataBaseHandler(DreamActivity.this).onCreate();
                    try {
                        @SuppressLint("WrongConstant") SQLiteDatabase db = openOrCreateDatabase("lakshay.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
                        String st = "CREATE TABLE IF NOT EXISTS Student1 " + "(id Integer,path1 text)";
                        db.execSQL(st);
                        ContentValues values = new ContentValues();
                        values.put("id",1);
                        values.put("path1", Arrays.asList(imageFiles).get(0).getFile().getAbsolutePath());
                        Long re = db.insert("Student", null, values);
                        if (re > 0) {
                            Toast.makeText(DreamActivity.this, "Inserted", Toast.LENGTH_SHORT).show();
                        }
                        new DataBaseHandler(DreamActivity.this).insertimage(Arrays.asList(imageFiles).get(0).getFile().getAbsolutePath(), 1);
                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(DreamActivity.this, ""+ex, Toast.LENGTH_SHORT).show();
                    }



                }

                @Override
                public void onImagePickerError(@NonNull Throwable error, @NonNull MediaSource source) {
                    //Some error handling
                    error.printStackTrace();
                }

                @Override
                public void onCanceled(@NonNull MediaSource source) {
                    //Not necessary to remove any files manually anymore
                }
            });
        }
        else if(resultCode==Activity.RESULT_CANCELED)
        {
            return;
        }


    }

    @Override
    public void onPause() {
        super.onPause();
        editor.apply();
        editor.commit();
    }
    @Override
    public void onResume() {
        super.onResume();
//        if(firsttime==1)
//        restartApp();
        this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        getSupportActionBar().setTitle("Vision Board");



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


