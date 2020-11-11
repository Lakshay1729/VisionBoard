package com.heartmeetsoul.visionboard;


import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;


public class DreamList extends AppCompatActivity {
RecyclerView recyclerView;
AdapterList adapterList;


    SharedPreferences.Editor editor;
    public static final String PREF_FILE_NAME = "PrefFile";
   static List<Image> items;
    private Bundle savedInstance;
    SharedPreferences sharedPreferences;
    private ActionBar actionBar;
    private Toolbar toolbar;
    private NavigationView nav;
    private View layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        sharedPreferences = getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
      //  setTheme(R.style.DarkTheme);
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



        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            NotificationChannel channel=new NotificationChannel("VisionBoard","VisionBoard", NotificationManager.IMPORTANCE_HIGH);

            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        FirebaseMessaging.getInstance().subscribeToTopic("general")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {

                        String msg="Successfull";

                        if(!task.isSuccessful())
                        {
                            msg="Failed";
                        }


                        // Log.d(TAG,msg);
                       // Toast.makeText(DreamList.this, "Success", Toast.LENGTH_SHORT).show();
                    }
                });






//        Palette.Swatch vibrant = myPalette.getVibrantSwatch();
//        if(vibrant != null){
//            int titleColor = vibrant.getTitleTextColor();
//            // ...
//        }

//super.initNavigationMenu();
       // themeUtils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_dream_list);
         recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


        initToolbar();

        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {

        } else {
            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE) && shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                Toast.makeText(this, "External Storage and Camera Permission Required", Toast.LENGTH_SHORT).show();
            }

            requestPermissions(new String[]{Manifest.permission.CAMERA}, 9);
        }


        nav = (NavigationView) findViewById(R.id.nav_view);
        layout = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.include_drawer_header_profile_image, nav, false);

        initNavigationMenu();
    }


    // Set the background and text colors of a toolbar given a
// bitmap image to match



//    // Generate palette asynchronously and use it on a different
//// thread using onGenerated()
//    public void createPaletteAsync(Bitmap bitmap) {
//        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
//            public void onGenerated(Palette p) {
//                // Use generated instance
//            }
//        });
//    }

    private void iniComponent() {

        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.addItemDecoration(new SpacingItemDecoration(1, Tools.dpToPx(this, 3), true));
        recyclerView.setHasFixedSize(true);

        items = DataGenerator.getImageData(this);

        adapterList = new AdapterList(this, items);
        recyclerView.setAdapter(adapterList);



        adapterList.setOnItemClickListener(new AdapterList.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Image obj, int position) {
                Intent intent=new Intent(getApplicationContext(),ShowDreamActivity.class);

                editor.putInt("image",obj.id).commit();
//                intent.putExtra("image",position);
//                intent.putExtra("Title",obj.name);
//                intent.putExtra("flag",1);
//                intent.putExtra("description",obj.brief);
//                intent.putExtra("tags",obj.tags);
//                intent.putExtra("selection",obj.selection);
                startActivity(intent);
               // Toast.makeText(DreamList.this, ""+items.get(position).name, Toast.LENGTH_SHORT).show();
                //Snackbar.make(, obj.name + " clicked", Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.setElevation(20);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        toolbar.setNavigationIcon(R.drawable.ic_menu);



        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setSupportActionBar(toolbar);

       // actionBar.setHomeButtonEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//           // finish();
//        }
        if(item.getItemId()==R.id.action_settings){
            //Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(this,SettingsActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.slideshow)
        {
            startActivity(new Intent(this,SliderShow.class));
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onResume() {
        super.onResume();
        this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        getSupportActionBar().setTitle("Dreams");
        //onCreate(savedInstance);
        iniComponent();
        if(!sharedPreferences.getBoolean("layout",true))
        {
            startActivity(new Intent(this,DreamGrid.class));
            finish();
        }

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


    public void initNavigationMenu() {
        NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);
        DreamActivity.drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, DreamActivity.drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        DreamActivity.drawer.setDrawerListener(toggle);
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
                        DreamActivity.drawer.closeDrawers();
                        //Toast.makeText(DreamActivity.this, ""+SettingsActivity.layout, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    case R.id.nav_explore: {
                        startActivity(new Intent(getApplicationContext(), AddDream.class));
                        actionBar.setTitle(item.getTitle());
                        DreamActivity.drawer.closeDrawers();
                        return true;
                    }
                    case R.id.nav_videos: {
                        startActivity(new Intent(getApplicationContext(), SliderShow.class));
                        actionBar.setTitle(item.getTitle());
                        DreamActivity.drawer.closeDrawers();
                        return true;
                    }
                    case R.id.nav_settings: {
                        startActivity(new Intent(getApplicationContext(), AboutUs.class));
                        actionBar.setTitle(item.getTitle());
                        DreamActivity.drawer.closeDrawers();
                        return true;
                    }
                    case R.id.nav_message: {
                        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                        actionBar.setTitle(item.getTitle());
                        DreamActivity.drawer.closeDrawers();
                        return true;
                    }


                }
                actionBar.setTitle(item.getTitle());

                DreamActivity.drawer.closeDrawers();
                return true;
            }
        });

        // open drawer at start
        // drawer.openDrawer(GravityCompat.START);
    }
}
