package com.heartmeetsoul.visionboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;


import java.util.List;

public class DreamGrid extends AppCompatActivity {
   static List<Image> items;
    private View parent_view;

    private RecyclerView recyclerView;
    private AdapterGridTwoLine mAdapter;
    private Toolbar toolbar;
    private ActionBar actionBar;
    NavigationView nav_view;
    Bundle savedInstance;
    ShimmerFrameLayout container;

    SharedPreferences.Editor editor;
    public static final String PREF_FILE_NAME = "PrefFile";
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //savedInstance=savedInstanceState;
       // setTheme(R.style.DarkTheme);
        sharedPreferences = getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES)
        {
            //restartApp();
            setTheme(R.style.DarkTheme);

        }
        else if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_NO)
        {

            setTheme(R.style.AppTheme);
        }

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //themeUtils.onActivityCreateSetTheme(this);
       // super.initNavigationMenu();
        setContentView(R.layout.activity_dream_grid);
        parent_view = findViewById(android.R.id.content);

        this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        initToolbar();

       //initNavigationMenu();

        actionBar=getSupportActionBar();
//        View v=((LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_grid_image_two_line,null);
//        container = (ShimmerFrameLayout)v.findViewById(R.id.shimmer_view_container1);
//
//        container.setDuration(4000);
//        container.setAngle(ShimmerFrameLayout.MaskAngle.CW_90);
//        container.setBaseAlpha(5.0f);
//        container.setAutoStart(true);
//        container.startShimmerAnimation();

initNavigationMenu();
    }

    public void initNavigationMenu() {
        //DrawerLayout layout=(DrawerLayout)findViewById(R.id.drawer_layout);
       // View v=((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.activity_dream,layout,false);
         nav_view = (NavigationView) findViewById(R.id.nav_view);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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

                Toast.makeText(getApplicationContext(), item.getTitle() + " Selected", Toast.LENGTH_SHORT).show();
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

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Dream Grid");
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // Tools.setSystemBarColor(this, R.color.grey_1000);
    }

    private void initComponent() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addItemDecoration(new SpacingItemDecoration(2, Tools.dpToPx(this, 3), true));
        recyclerView.setHasFixedSize(true);

         items = DataGenerator.getImageData(this);
//        items.addAll(DataGenerator.getImageData(this));
//        items.addAll(DataGenerator.getImageData(this));
//        items.addAll(DataGenerator.getImageData(this));

        //set data and list adapter
       mAdapter = new AdapterGridTwoLine(this, items);
        recyclerView.setAdapter(mAdapter);


        // on item list clicked
        mAdapter.setOnItemClickListener(new AdapterGridTwoLine.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Image obj, int position) {
                Intent intent=new Intent(getApplicationContext(),ShowDreamActivity.class);

//                intent.putExtra("image",position);
//                intent.putExtra("Title",obj.name);
//                intent.putExtra("flag",0);
//                intent.putExtra("selection",obj.selection);
                editor.putInt("image",obj.id).commit();
                startActivity(intent);
                Snackbar.make(parent_view, obj.name + " clicked", Snackbar.LENGTH_SHORT).show();
            }
        });

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
        initComponent();
        if(sharedPreferences.getBoolean("layout",true))
        {
            startActivity(new Intent(this,DreamList.class));
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

}
