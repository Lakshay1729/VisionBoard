package com.heartmeetsoul.visionboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;


public class SliderShow extends AppCompatActivity {

    private static final int MAX_STEP = 4;

    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private String about_title_array[] ;
    private String about_description_array[];
    private int about_images_array[] = {
            R.drawable.img_wizard_1,
            R.drawable.img_wizard_2,
            R.drawable.img_wizard_3,
            R.drawable.img_wizard_4
    };
    private Drawable bg_images_array[];
    private String tags[];

//    private int bg_images_array[] = {
//            R.drawable.image_15,
//            R.drawable.image_10,
//            R.drawable.image_3,
//            R.drawable.image_12
//    };
ImageButton play;
ImageButton mute;
     MediaPlayer mediaPlayer;
     public static boolean vis;
     View slider;
     int f=0;
    int textColor;
    int backgroundColor;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    public static final String PREF_FILE_NAME = "PrefFile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE);
        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES)
        {
            //restartApp();
            setTheme(R.style.DarkTheme);

        }
        else if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_NO)
        {

            setTheme(R.style.AppTheme);
        }

       // setTheme(R.style.DarkTheme);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_slider_show);
         mediaPlayer=MediaPlayer.create(this,R.raw.tumsehi);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
       // mute=(ImageButton)findViewById(R.id.mute);
        //mute.setBackgroundResource(R.drawable.ic_music);
        //final float log1=(float)(Math.log(50-10)/Math.log(50));
       // Toast.makeText(this, ""+vis, Toast.LENGTH_SHORT).show();
        play=(ImageButton) findViewById(R.id.music);
        play.setVisibility(View.VISIBLE);







        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        // Get deep link from result (may be null if no link is found)
                        Uri deepLink = null;
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink();
                            startActivity(new Intent("android.intent.action.VIEW",deepLink));
                        }


                        // Handle the deep link. For example, open the linked
                        // content, or apply promotional credit to the user's
                        // account.
                        // ...
                        //Intent intent=new Intent(Intent.ACTION_VIEW);


                        // ...
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Log.w(TAG, "getDynamicLink:onFailure", e);
                        Toast.makeText(SliderShow.this, ""+e, Toast.LENGTH_SHORT).show();
                    }
                });

        //sharedPreferences = getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        try{
            if(sharedPreferences.getBoolean("muzic",true))
            {
         play.setVisibility(View.VISIBLE);

                try {
                    play.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (!mediaPlayer.isPlaying()) {
                                mediaPlayer.start();
                                mediaPlayer.setLooping(true);
                                play.setImageResource(R.drawable.slider_pause);
                            } else if (mediaPlayer.isPlaying()) {
                                play.setImageResource(R.drawable.slide_play);
                                mediaPlayer.pause();
                            }
                        }
                    });
                }
                catch (Exception ex)
                {
                    Snackbar.make(findViewById(android.R.id.content),"Error",2000);
                }
            }
            else if(!sharedPreferences.getBoolean("muzic",false))
            {
                play.setVisibility(View.INVISIBLE);
            }
        }catch (Exception ex)
        {
            //Toast.makeText(this, ""+ex, Toast.LENGTH_SHORT).show();
        }

//        mute.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(f==0) {
//                    mediaPlayer.setVolume(0, 0);
//                    f=1;
//                }
//                else if(f==1)
//                {
//                    mediaPlayer.setVolume(log1,log1);
//                    f=0;
//                }
//            }
//        });

        // adding bottom dots
//mediaPlayer.start();
//mediaPlayer.setLooping(true);





        bottomProgressDots(0);
        bg_images_array=new Drawable[DataGenerator.getImageData(this).size()];
        about_title_array=new String[DataGenerator.getImageData(this).size()];
        about_description_array=new String[DataGenerator.getImageData(this).size()];
        tags=new String[DataGenerator.getImageData(this).size()];
        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        for (int i = 0; i <DataGenerator.getImageData(this).size(); i++) {
            bg_images_array[i]=DataGenerator.getImageData(this).get(i).imageDrw;
            about_title_array[i]=DataGenerator.getImageData(this).get(i).name;
            about_description_array[i]=DataGenerator.getImageData(this).get(i).brief;
            tags[i]=DataGenerator.getImageData(this).get(i).tags;
        }
//        Tools.setSystemBarColor(this, R.color.grey_5);
//        Tools.setSystemBarLight(this);
        this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }




    private void bottomProgressDots(int current_index) {
        LinearLayout dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        ImageView[] dots = new ImageView[DataGenerator.getImageData(this).size()];

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(this);
            int width_height = 15;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(width_height, width_height));
            params.setMargins(10, 10, 10, 10);
            dots[i].setLayoutParams(params);
            dots[i].setImageResource(R.drawable.shape_circle);
            dots[i].setColorFilter(getResources().getColor(R.color.grey_20), PorterDuff.Mode.SRC_IN);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[current_index].setImageResource(R.drawable.shape_circle);
            dots[current_index].setColorFilter(getResources().getColor(R.color.light_green_600), PorterDuff.Mode.SRC_IN);
        }
    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(final int position) {
            bottomProgressDots(position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;
        private Button btnNext;

        public MyViewPagerAdapter() {

        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//setToolbarColor(((BitmapDrawable)bg_images_array[position]).getBitmap());
            View view = layoutInflater.inflate(R.layout.slide_show, container, false);
            ((TextView) view.findViewById(R.id.title)).setText(about_title_array[position]);
            ((TextView) view.findViewById(R.id.description)).setText(about_description_array[position]);
            ((TextView)view.findViewById(R.id.pend)).setText(tags[position]);
//            if(DreamList.items.get(position).pending==0)
//            ((TextView)view.findViewById(R.id.pend)).setText("Pending");
//            else if(DreamList.items.get(position).pending==1)
//                ((TextView)view.findViewById(R.id.pend)).setText("Completed");


           // ((ImageView) view.findViewById(R.id.image)).setImageResource(about_images_array[position]);
            ((TextView) view.findViewById(R.id.title)).setTextColor(getResources().getColor(R.color.grey_1000));
            ((TextView) view.findViewById(R.id.description)).setTextColor(getResources().getColor(R.color.grey_1000));


            try {
                ((ImageView) view.findViewById(R.id.image_bg)).setImageDrawable(bg_images_array[position]);
            }
            catch (Exception ex)
            {
              //  Toast.makeText(SliderShow.this, ""+ex, Toast.LENGTH_SHORT).show();
            }


//            btnNext = (Button) view.findViewById(R.id.btn_next);
//            btnNext.setBackgroundColor(getColor(R.color.blue_400));
//
//            if (position == about_title_array.length - 1) {
//                btnNext.setText("Get Started");
//            } else {
//                btnNext.setText("Next");
//            }


//            btnNext.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int current = viewPager.getCurrentItem() + 1;
//                    if (current < MAX_STEP) {
//                        // move to next screen
//                        viewPager.setCurrentItem(current);
//                    } else {
//                        finish();
//                    }
//                }
//            });

            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return about_title_array.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        if(sharedPreferences.getBoolean("muzic",false))
        mediaPlayer.start();
        mediaPlayer.setLooping(true);
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
    @Override
    public void onPause()
    {
        super.onPause();
        try {
            mediaPlayer.stop();
            //mediaPlayer.release();
        }
        catch (Exception ex)
        {

        }
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        mediaPlayer.release();
    }
}