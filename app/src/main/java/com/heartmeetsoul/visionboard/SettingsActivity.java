package com.heartmeetsoul.visionboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.messaging.FirebaseMessaging;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;

//import static FingerPrintLock.condition;

public class SettingsActivity extends AppCompatActivity {
    public static final String MY_PREFS_NAME = "true";
    SwitchCompat lock,layout1,theme,finger,reminder;
    public static final String PREF_FILE_NAME = "PrefFile";
SharedPreferences sharedPreferences;
    private static final String JOB_Tag="my_Job_Tag";
    private FirebaseJobDispatcher jobDispatcher;
public static boolean layout=true;
ImageView image,setpin;
TimePickerDialog.OnTimeSetListener listener;
private  int startTime;
public static boolean lockscreen=false;
    boolean themeb=false  ,music=false ,pinlock=false ,fingerlock=false,layoutb=true ,reminderb=false ;

private  int windowTime;
int ho,shour;
int min,smin;
SwitchCompat muzic;
    public static  boolean startingActivity=true;
    private View slider;
    private int visible;
     SharedPreferences.Editor editor;
     ImageView settings;
     LinearLayout fingerlayout,pinlayout,adjust_time;

     TextView timing;
     TextView default_pin;
TextView faq;
    // SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();

    //    SharedPreferences sharedpreferences = getSharedPreferences(condition, Context.MODE_PRIVATE);
//    SharedPreferences.Editor editor = sharedpreferences.edit();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        if (new App().isNightModeEnabled()) {
//            setTheme(R.style.FeedActivityThemeDark);
//        }
//        else
//        {
//            setTheme(R.style.FeedActivityThemeLight);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES)
        {
            //restartApp();
           setTheme(R.style.DarkTheme);
           // AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        }
        else if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_NO)
        {
           // AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

            setTheme(R.style.AppTheme);
        }
//        }
       // setTheme(R.style.DarkTheme);
        super.onCreate(savedInstanceState);
       // setTheme(android.R.style.Theme_Material);

       // themeUtils.onActivityCreateSetTheme(this)
        setContentView(R.layout.activity_settings);
        Tools.setSystemBarColor(this, R.color.transparent);
        Tools.setSystemBarLight(this);
        settings=(ImageView)findViewById(R.id.adjust);
        sharedPreferences = getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE);
         editor = sharedPreferences.edit();
         muzic=(SwitchCompat)findViewById(R.id.muzic);
         setpin=(ImageView)findViewById(R.id.setpin);
        //Toast.makeText(this, "Theme: "+sharedPreferences.getBoolean("theme",true), Toast.LENGTH_SHORT).show();






  findViewById(R.id.faq).setOnClickListener(new View.OnClickListener() {
         @Override
     public void onClick(View v) {
        startActivity(new Intent(getApplicationContext(),FAQActivity.class));
    }
});

        default_pin=(TextView)findViewById(R.id.default_pin);
if(!sharedPreferences.getString("pin","1234").equals(null))
{
    default_pin.setVisibility(View.INVISIBLE);
}

        theme=(SwitchCompat)findViewById(R.id.theme);

        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES)
        {
           // editor.putBoolean("theme",true);
            theme.setChecked(true);
        }


//        if (sharedPreferences.getBoolean("theme", true)) {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//            //editor.putBoolean("theme",false);
//           // tf=0;
//            restartApp();
//
//
//
//        } else if (!sharedPreferences.getBoolean("theme", false)) {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//            // editor.putBoolean("theme",true);
//           // tf=0;
//            restartApp();
//
//        }

        slider= ((LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.activity_slider_show,null);

     fingerlayout=(LinearLayout)findViewById(R.id.fingerlayout);
     pinlayout=(LinearLayout)findViewById(R.id.pinlayout);
     adjust_time=(LinearLayout)findViewById(R.id.adjust_time);

        timing=(TextView)findViewById(R.id.timing);
        muzic.setOnCheckedChangeListener(new SwitchCompat.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                   // slider.findViewById(R.id.music).setVisibility(View.VISIBLE);
                   // SliderShow.vis=true;
                    music=true;
                    muzic.setChecked(true);

                }
                else if(!isChecked)
                {
                  //slider.findViewById(R.id.music).setVisibility(View.INVISIBLE);
                   //SliderShow.vis=false;
                    music=false;
                    muzic.setChecked(false);
                }
                editor.putBoolean("muzic",music);
            }
        });



        try {
//            String r = title.getText().toString().trim();
//            // String a=tags.getText().toString().trim();
//            String a=spinner.getSelectedItem().toString();
//            String b=description.getText().toString().trim();
//            String date=end_date.getText().toString().trim();

//            @SuppressLint("WrongConstant")
//            SQLiteDatabase db=openOrCreateDatabase("lakshay.db",SQLiteDatabase.CREATE_IF_NECESSARY,null);
//           // String st="Update  Settings set theme ="
//            String st="CREATE TABLE IF NOT EXISTS Settings "+"(theme bool ,music bool,pinlock bool,fingerlock bool,layout bool,date Date,reminder bool)";
//            db.execSQL(st);
//            ContentValues values = new ContentValues();
//
//            try {
////                if(f==1) {
////                    if (!(r.equals(null) ||a.equals(null) || b.equals(null))) {
//
//                values.put("theme", themeb);
//                //values.put("music",music);
//                values.put("lock", pinlock);
//                values.put("fingerlock", fingerlock);
//                values.put("reminder",reminderb);
//                values.put("layout",layoutb);
//
//
//
//
////                    }
////                }
////                else
////                {
////                    Snackbar.make(findViewById(android.R.id.content),"Put Image First",Snackbar.LENGTH_SHORT).show();
////                }
//
//            }
//            catch (Exception ex)
//            {
//                //Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
//                Snackbar.make(findViewById(android.R.id.content),"Error",Snackbar.LENGTH_SHORT).show();
//
//            }
//
//
//           // int re=db.update("Student",values,"title='"++"'",null);
//
////            if(re>0)
////            {
////                // Toast.makeText(this,"Values inserted",Toast.LENGTH_LONG).show();
////                Snackbar.make(findViewById(android.R.id.content),"Values Inserted",Snackbar.LENGTH_SHORT).show();
////
////            }


//            theme.setChecked(sharedPreferences.getBoolean("theme",false));
//            finger.setChecked(sharedPreferences.getBoolean("fingerlock",false));
//            layout1.setChecked(sharedPreferences.getBoolean("layout",true));
//            lock.setChecked(sharedPreferences.getBoolean("pinlock",false));
//            reminder.setChecked(sharedPreferences.getBoolean("reminder",false));
        } catch (Exception ex) {

             // Toast.makeText(this, "Error"+ex.toString(), Toast.LENGTH_SHORT).show();
            Snackbar.make(findViewById(android.R.id.content),"Error"+ex,Snackbar.LENGTH_SHORT).show();
        }



       // image=(ImageView)findViewById(R.id.picker);
        listener=new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//            editor.putInt("hour",hourOfDay);
//            editor.putInt("minute",minute);


                Date currentTime = Calendar.getInstance().getTime();
                 ho=currentTime.getHours();
                 min=currentTime.getMinutes();
                try {
                    startTime = (hourOfDay * 3600 + minute * 60)-(ho*3600+min*60);
                    windowTime = 24 * 3600  ;
                    if (startTime <0) {

                        startTime +=24 * 3600 ;
                    }
                    startJob();
                    Toast.makeText(SettingsActivity.this, ""+startTime, Toast.LENGTH_SHORT).show();
                }
                catch (Exception ex)
                {
                    Toast.makeText(SettingsActivity.this, ""+ex, Toast.LENGTH_SHORT).show();
                }
            }
        };
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog=new TimePickerDialog(SettingsActivity.this,listener,startTime,windowTime,true);
                timePickerDialog.create();
                timePickerDialog.show();
            }
        });

//        image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TimePickerDialog timePickerDialog=new TimePickerDialog(SettingsActivity.this,listener,0,0,true);
//                timePickerDialog.create();
//                timePickerDialog.show();
//            }
//        });


         reminder=(SwitchCompat)findViewById(R.id.reminder);


//        final SharedPreferences sharedpreferences = getSharedPreferences("key", Context.MODE_PRIVATE);
//        final SharedPreferences.Editor editor = sharedpreferences.edit();
       // editor.putString("key", condition);
        this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
         finger=(SwitchCompat)findViewById(R.id.finger);
        jobDispatcher=new FirebaseJobDispatcher(new GooglePlayDriver(SettingsActivity.this));
        reminder.setOnCheckedChangeListener(new SwitchCompat.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked)
        {
            reminderb=true;
            reminder.setChecked(true);
            adjust_time.setVisibility(View.VISIBLE);
            adjust_time.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.animate));
            startTime=21*3600-(ho*3600+min*60);
            windowTime=24*3600;
            if(startTime<0)
            {
                startTime+=24*3600;
            }
            startJob();
            timing.setVisibility(View.VISIBLE);
            timing.setText("(Default Reminder Set)");



        }
        else
            if(!isChecked)
            {
                reminderb=false;
                stopJob();
                reminder.setChecked(false);
                adjust_time.setVisibility(View.GONE);
                timing.setVisibility(View.GONE);

            }

        editor.putBoolean("reminder",reminderb );
        //Toast.makeText(SettingsActivity.this, ""+sharedPreferences.getBoolean("reminder",true), Toast.LENGTH_LONG).show();
    }
});

        // value to store




finger.setOnCheckedChangeListener(new SwitchCompat.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked)
        {
            finger.setChecked(true);
          //  editor.putString("lockscreen","true");
            fingerlock=true;

        }
        else if(!isChecked)
        {
            finger.setChecked(false);
            fingerlock=false;
        }
        editor.putBoolean("fingerlock",fingerlock);
        //Toast.makeText(SettingsActivity.this, ""+sharedPreferences.getBoolean("fingerlock",true), Toast.LENGTH_LONG).show();
    }
});





        theme.setOnCheckedChangeListener(new SwitchCompat.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {
                        theme.setChecked(true);
                       // setTheme(R.style.DarkTheme);
                       // themeUtils.changeToTheme(SettingsActivity.this, themeUtils.BLACK);
                        themeb=true;
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        //editor.putBoolean("theme",false);
                        // tf=0;
                        //restartApp();
                        //setTheme(R.style.DreActivityThemeDark);
                       // DreamActivity.tf=1;
                       // new ContextThemeWrapper(, android.R.style.Theme_Dialog);
                    }
                    else if(!isChecked)
                    {
                        themeb=false;
                        theme.setChecked(false);
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        //editor.putBoolean("theme",false);
                        // tf=0;
                        //restartApp();
                        //setTheme(R.style.DreActivityThemeLight);
                        //DreamActivity.tf=0;
                        //themeUtils.changeToTheme(SettingsActivity.this, themeUtils.BLUE);
                    }

                editor.putBoolean("theme",themeb );
               // Toast.makeText(SettingsActivity.this, ""+sharedPreferences.getBoolean("theme",true), Toast.LENGTH_LONG).show();
            }

        });
       layout1=(SwitchCompat)findViewById(R.id.layout);


       layout1.setOnCheckedChangeListener(new SwitchCompat.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if(isChecked){
                   layout=true;
                   layout1.setChecked(true);
                   layoutb=true;
                   //Toast.makeText(SettingsActivity.this, ""+layout, Toast.LENGTH_SHORT).show();
                   }
               else if(!isChecked) {
                   layout = false;
                   layout1.setChecked(false);
                   layoutb=false;
                 //  Toast.makeText(SettingsActivity.this, ""+layout, Toast.LENGTH_SHORT).show();
               }
               editor.putBoolean("layout",layoutb );
           }
       });
       lock=(SwitchCompat)findViewById(R.id.lock);
       lock.setOnCheckedChangeListener(new SwitchCompat.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked)
        {
            if(!sharedPreferences.getString("pin","1234").equals(null))
            lock.setChecked(true);
            pinlock=true;
            editor.putBoolean("pinlock",pinlock );
            findViewById(R.id.default_pin).setVisibility(View.VISIBLE);

//
            if(!sharedPreferences.contains("pin"))
            {
                Intent intent=new Intent(getApplicationContext(),FingerPrintLock.class);
                intent.putExtra("pin", "1234");
                startActivity(intent);
            }


           // default_pin.setVisibility(View.VISIBLE);
            finger.setVisibility(View.VISIBLE);
            setpin.setVisibility(View.VISIBLE);
            pinlayout.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.animate));
            fingerlayout.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.animate));
            finger.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.animate));
            setpin.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.animate));
            pinlayout.setVisibility(View.VISIBLE);
            fingerlayout.setVisibility(View.VISIBLE);



//            editor.putString("starting", "false");
//            editor.apply();
           // startingActivity=false;
        }
        else if(!isChecked)
        {
            lock.setChecked(false);
            pinlock=false;
            editor.putBoolean("pinlock",pinlock );
            finger.setVisibility(View.GONE);
            setpin.setVisibility(View.GONE);
            pinlayout.setVisibility(View.GONE);
            fingerlayout.setVisibility(View.GONE);

        }




            setpin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), FingerPrintLock.class);
                    intent.putExtra("pin", "1234");

                    findViewById(R.id.default_pin).setVisibility(View.INVISIBLE);
                    startActivity(intent);

                }
            });




    }
});
//       lock.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//              startActivity(new Intent(getApplicationContext(),FingerPrintLock.class));
////               if (sharedpreferences.getString("key",null).equals("false"))
////                   editor.putString("key", "true");
////               else
////                   editor.putString("key","false");
//           }
//       });
        try{
            theme.setChecked(sharedPreferences.getBoolean("theme",false));
          //  Toast.makeText(this, ""+sharedPreferences.getBoolean("theme",false)+"theme", Toast.LENGTH_SHORT).show();
            finger.setChecked(sharedPreferences.getBoolean("fingerlock",false));
           // Toast.makeText(this, ""+sharedPreferences.getBoolean("fingerlock",false)+"fingerlock", Toast.LENGTH_SHORT).show();
            layout1.setChecked(sharedPreferences.getBoolean("layout",true));
           // Toast.makeText(this, ""+sharedPreferences.getBoolean("layout",false)+"layout", Toast.LENGTH_SHORT).show();

            lock.setChecked(sharedPreferences.getBoolean("pinlock", false));

           // Toast.makeText(this, ""+sharedPreferences.getBoolean("pinlock",false)+"pinlock", Toast.LENGTH_SHORT).show();
            reminder.setChecked(sharedPreferences.getBoolean("reminder",false));
           // Toast.makeText(this, ""+sharedPreferences.getBoolean("reminder",false)+"reminder", Toast.LENGTH_SHORT).show();
            muzic.setChecked(sharedPreferences.getBoolean("muzic",true));
        }
        catch (Exception ex)
        {
            Snackbar.make(findViewById(android.R.id.content),"Error"+ex,Snackbar.LENGTH_LONG);
           // Toast.makeText(this, ""+ex, Toast.LENGTH_SHORT).show();
        }


    }


    public void startJob()
    {

        Job job=jobDispatcher.newJobBuilder().setService(MyService.class).setConstraints(Constraint.ON_ANY_NETWORK).setLifetime(Lifetime.FOREVER).setRecurring(true).setTag(JOB_Tag).setTrigger(Trigger.executionWindow(startTime,windowTime)).setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL).setReplaceCurrent(false).build();

        jobDispatcher.mustSchedule(job);
       // Toast.makeText(this, "Job Scheduled :"+startTime, Toast.LENGTH_SHORT).show();
    }

    public void stopJob()
    {
        jobDispatcher.cancel(JOB_Tag);
        //Toast.makeText(this, "Job Cancelled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
       // getSupportActionBar().setTitle("Settings");
    }
    @Override
    public void onPause()
    {
        super.onPause();
        editor.apply();
       editor.commit();

    }



}

