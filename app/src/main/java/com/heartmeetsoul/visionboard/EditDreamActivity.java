package com.heartmeetsoul.visionboard;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;




import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class EditDreamActivity extends AppCompatActivity {
    private static final int CAMERA_PIC_REQUEST = 1;
    private static final int SELECT_PICTURE = 0;
    Toolbar toolbar;
    PaintView paintView;
    EditText title,description;
    EditText et_tag;
    ImageView editperson;
    private ActionBar actionBar;
    View v;
    int flag=1;
    ArrayList tagslist;
    ImageView datepicker;
    private DatePickerDialog.OnDateSetListener myDateListener;
    EditText end_date;
    Button pending,completed;
    String path;
    private int f;
    private int pendingf=0;
    Spinner tags;
    String tilti = null;
    private int REQ_CODE_SPEECH_INPUT=100;
    ImageView mic,mic1;
    int posit;
    SharedPreferences sharedPreferences;
    public static final String PREF_FILE_NAME = "PrefFile";
    ArrayList<HashMap<String,String>> ar;
    private int selection1;
    private String tag;
    public int prefer_pos=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPreferences = getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE);
        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES)
        {

            setTheme(R.style.DarkTheme);

        }
        else if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_NO)
        {

            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
       getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_edit_dream);
        ar=new ArrayList<HashMap<String, String>>();
        this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        initToolbar();


        editperson=(ImageView)findViewById(R.id.editperson);
        title=(EditText)findViewById(R.id.title);
        description=(EditText)findViewById(R.id.description);

        tags=(Spinner)findViewById(R.id.tags_spinner);


        title=(EditText)findViewById(R.id.title);
        description=(EditText)findViewById(R.id.description);
        et_tag=(EditText)findViewById(R.id.tags);
        tagslist=new ArrayList<>();
        datepicker=(ImageView)findViewById(R.id.date_picker);
        pending=(Button)findViewById(R.id.pending);
        end_date=(EditText)findViewById(R.id.end_date);
        completed=(Button)findViewById(R.id.completed);
        prefer_pos=sharedPreferences.getInt("image",0);



        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pending.setBackgroundColor(getResources().getColor(R.color.blue_grey_700));
                completed.setBackgroundColor(getResources().getColor(R.color.blue_A200));
                pendingf=0;

            }
        });


        completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                completed.setBackgroundColor(getResources().getColor(R.color.blue_grey_700));
                pending.setBackgroundColor(getResources().getColor(R.color.blue_A200));
                pendingf=1;
            }
        });


        end_date=(EditText)findViewById(R.id.end_date);


        myDateListener=new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                end_date.setText("End Date: "+year+"-"+month+"-"+dayOfMonth);
            }
        };
        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(EditDreamActivity.this,myDateListener, Calendar.getInstance().get( Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });





        //tags.setSelection(Integer.parseInt(ar.get(0).get("k2")));


        tags.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, View view, final int position, long id)
            {
                selection1=position;
                tags.setSelection(position);
                if(parent.getItemAtPosition(position).toString().equals("Add New Category"))
                {
                    final CoordinatorLayout rela=(CoordinatorLayout) findViewById(R.id.add_dream);
                    AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(EditDreamActivity.this,R.style.Dialog_Theme));


                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();


                        }
                    });
                    v = LayoutInflater.from(EditDreamActivity.this).inflate(R.layout.layout_alert,null);
                    builder.setView(v);


                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {

                            String str=   ((EditText) v.findViewById(R.id.edt)).getText().toString();
                            tagslist.add(str);
                            parent.setSelection(tagslist.size()-1);
                            selection1=tagslist.size()-1;
                            //  serialize+=str;



                            String a=tags.getSelectedItem().toString();
                            try
                            {
                                @SuppressLint("WrongConstant")
                                SQLiteDatabase db=openOrCreateDatabase("lakshay.db",SQLiteDatabase.CREATE_IF_NECESSARY,null);
//                    String st="CREATE TABLE IF NOT EXISTS Student3 "+"(id primary key Autoincrement,tag text)";
//                    db.execSQL(st);
                                ContentValues values = new ContentValues();
                                values.put("tag",a);

                                Long re=db.insert("Student3",null,values);
                                //Toast.makeText(this, ""+values.get("tags")+re, Toast.LENGTH_SHORT).show();
                                if(re>0)
                                {
                                    //Toast.makeText(this, "Insertde", Toast.LENGTH_SHORT).show();
                                }
                                db.close();


                            }
                            catch (Exception ex)
                            {
                                //  Toast.makeText(this, ""+ex, Toast.LENGTH_SHORT).show();

                            }


                        }
                    });

                    builder.create();


                    builder.show();
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });




        try
        {
            @SuppressLint("WrongConstant")
            SQLiteDatabase db=openOrCreateDatabase("lakshay.db",SQLiteDatabase.CREATE_IF_NECESSARY,null);
            String sql="SELECT * from Student3";
            Cursor c=db.rawQuery(sql,null);
            c.moveToFirst();
            while(!c.isAfterLast()) {


                tagslist.add(c.getString(1));

                c.moveToNext();

            }
            c.close();
            db.close();

        }
        catch (Exception ex)
        {
            // Toast.makeText(this, ""+ex, Toast.LENGTH_SHORT).show();
        }

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_item,tagslist);

        //adapter.setDropDownViewResource(android.R.layout.simple_selectable_list_item);

        tags.setAdapter(adapter);



        mic=(ImageView)findViewById(R.id.mic);
        mic1=(ImageView)findViewById(R.id.mic1);

        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=0;

                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello, How can I help you?");
                try {
                    startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
                } catch (ActivityNotFoundException a) {

                }
            }
        });
        mic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=1;

                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello, How can I help you?");
                try {
                    startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
                } catch (ActivityNotFoundException a) {

                }
            }
        });



        Bitmap bitmap=null;
        editperson.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {

                                              Intent intent=new Intent(getApplicationContext(),EditImageActivity.class);
                                              intent.putExtra("flag1",1);
                                              intent.putExtra("image",getIntent().getIntExtra("image",0));
                                              startActivity(intent);
                                              finish();

                                          }
                                      });

    }



    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Dream");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this,R.color.transparent);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_done, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } if(item.getItemId()==R.id.action_done)
        {


            try {
                @SuppressLint("WrongConstant") SQLiteDatabase db = openOrCreateDatabase("lakshay.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
                String r = title.getText().toString().trim();
                //String a=et_tag.getText().toString().trim();
                String b=description.getText().toString().trim();
                String date=end_date.getText().toString().trim();

                ContentValues values=new ContentValues();

                        values.put("title", r);
                        values.put("tagid",selection1+1 );
                        values.put("description", b);
                        values.put("path", path);
                        values.put("date",date);
                        values.put("pending",pendingf);


                int re=db.update("Student",values,"id= "+sharedPreferences.getInt("image",0),null);
                //int re1=db.update("Student3",values1,"title='"+r+"'",null);
                Log.d("re",""+re);
                if((re>0))
                {

                    finish();
                }

                db.close();

            }
            catch (Exception ex){
                //Toast.makeText(this,"Error:"+ex.toString(),Toast.LENGTH_LONG).show();
            }

        }
        return super.onOptionsItemSelected(item);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                editperson.setImageURI(selectedImageUri);
            }

        }
        if (requestCode == CAMERA_PIC_REQUEST) {
            try {
                Bitmap image = (Bitmap) data.getExtras().get("data");

                ImageView imageview = (ImageView) findViewById(R.id.editperson);
                imageview.setImageBitmap(image);
            }
            catch (Exception ex) {
               // Toast.makeText(this, ""+ex, Toast.LENGTH_SHORT).show();

            }
        }

        if (requestCode == REQ_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && null != data) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                if (flag == 1)
                    description.setText(result.get(0));
                else if (flag == 0)
                    title.append(result.get(0));
            }
        }
    }

@Override
public void onResume() {
    super.onResume();
    this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    getSupportActionBar().setTitle("Edit Dream");

     posit=sharedPreferences.getInt("image",0);

    if (sharedPreferences.getBoolean("theme", true)) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

    } else if (!sharedPreferences.getBoolean("theme", false)) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

    }



   // path=ar.get(0).get("k5");

   // tags.setSelection(Integer.parseInt(ar.get(0).get("k2")));
//try {
//    if (f == 1) {
//
//        title.setText(DreamList.items.get(getIntent().getIntExtra("image", 0)).name);
//
//        description.setText(DreamList.items.get(getIntent().getIntExtra("image", 0)).brief);
//        end_date.setText(DreamList.items.get(getIntent().getIntExtra("image", 0)).date);
//        try {
//            tags.setSelection(DreamList.items.get(getIntent().getIntExtra("image", 0)).selection);
//        } catch (Exception ex) {
//            // Toast.makeText(this, "" + ex, Toast.LENGTH_SHORT).show();
//        }
//        path = DreamList.items.get(getIntent().getIntExtra("image", 0)).path;
//        if(DreamList.items.get(getIntent().getIntExtra("image",0)).pending==0)
//        {
//            pending.setBackgroundColor(getResources().getColor(R.color.blue_grey_700));
//        }
//        else if(DreamList.items.get(getIntent().getIntExtra("image",0)).pending==1)
//        {
//            completed.setBackgroundColor(getResources().getColor(R.color.blue_grey_700));
//        }
//
//        // tag=tags.getSelectedItem().toString();
//
//    } else if (f == 0) {
//
//        title.setText(DreamList.items.get(getIntent().getIntExtra("image", 0)).name);
//
//        description.setText(DreamList.items.get(getIntent().getIntExtra("image", 0)).brief);
//        end_date.setText(DreamList.items.get(getIntent().getIntExtra("image", 0)).date);
//        try {
//            tags.setSelection(DreamList.items.get(getIntent().getIntExtra("image", 0)).selection);
//        } catch (Exception ex) {
//            // Toast.makeText(this, "" + ex, Toast.LENGTH_SHORT).show();
//        }
//        path = DreamList.items.get(getIntent().getIntExtra("image", 0)).path;
//        if(DreamList.items.get(getIntent().getIntExtra("image",0)).pending==0)
//        {
//            pending.setBackgroundColor(getResources().getColor(R.color.blue_grey_700));
//        }
//        else if(DreamList.items.get(getIntent().getIntExtra("image",0)).pending==1)
//        {
//            completed.setBackgroundColor(getResources().getColor(R.color.blue_grey_700));
//        }
//
//        //  tag=tags.getSelectedItem().toString();
//
//    }
//}
//catch (Exception ex){
//
//}

    if(getIntent().hasExtra("editimage"))
    {
        if(getIntent().getIntExtra("editimage",2)==2)
        {
            path=getIntent().getStringExtra("path");
        }
    }
    editperson.setImageDrawable(new BitmapDrawable(BitmapFactory.decodeFile(path)));

//    try {
//
//        title.setText(ar.get(0).get("k1"));
//        description.setText(ar.get(0).get("k4"));
//        end_date.setText(ar.get(0).get("k6"));
//        tags.setSelection(Integer.parseInt(ar.get(0).get("k2")));
//        if(DreamList.items.get(getIntent().getIntExtra("image",0)).pending==0)
//        {
//            pending.setBackgroundColor(getResources().getColor(R.color.blue_grey_700));
//        }
//        else if(DreamList.items.get(getIntent().getIntExtra("image",0)).pending==1)
//        {
//            completed.setBackgroundColor(getResources().getColor(R.color.blue_grey_700));
//        }
//
//    }
//    catch (Exception ex)
//    {
//       // Toast.makeText(this, ""+ex, Toast.LENGTH_SHORT).show();
//    }
}

@Override
public void onSaveInstanceState(Bundle savedInstance) {
    super.onSaveInstanceState(savedInstance);
    savedInstance.putString("title", tilti);
}
@Override
public void onRestoreInstanceState(Bundle savedInstance)
{
super.onRestoreInstanceState(savedInstance);
tilti=savedInstance.getString("title");
   // Toast.makeText(this, ""+tilti, Toast.LENGTH_SHORT).show();
}

    @Override
     public void onStart() {
        super.onStart();

//        try
//        {
//            @SuppressLint("WrongConstant")
//            SQLiteDatabase db=openOrCreateDatabase("lakshay.db",SQLiteDatabase.CREATE_IF_NECESSARY,null);
//            String sql="SELECT * from Student3";
//            Cursor c=db.rawQuery(sql,null);
//            c.moveToFirst();
//            while(!c.isAfterLast()) {
//                  tagslist.add(c.getString(1));
//
//                c.moveToNext();
//            }
//            c.close();
//            db.close();
//
//        }
//        catch (Exception ex)
//        {
//            //Toast.makeText(this, ""+ex, Toast.LENGTH_SHORT).show();
//        }


        try
        {


            @SuppressLint("WrongConstant")
            SQLiteDatabase db = openOrCreateDatabase("lakshay.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
            String sql="SELECT Student.title,Student.description,Student.path,Student3.tag,Student.id,Student.tagid,Student.pending ,Student.date from Student INNER JOIN Student3 ON Student.tagid=Student3.id where Student.id= "+prefer_pos;
            Cursor c=db.rawQuery(sql,null);
            c.moveToFirst();
            while(!c.isAfterLast()) {
                HashMap<String, String> hm = new HashMap<String, String>();
                hm.put("k1", c.getString(0));
                hm.put("k2", c.getString(1));
                hm.put("k3", c.getString(2));
                hm.put("k4",c.getString(3));
                hm.put("k5",c.getString(4));
                hm.put("k6",c.getString(5));
                hm.put("k7",c.getString(6));
                hm.put("k8",c.getString(7));
                ar.add(hm);

                c.moveToNext();
            }
            c.close();
            db.close();

            // Toast.makeText(this, ""+ar.get(0).get("k7"), Toast.LENGTH_SHORT).show();

        }

        catch(Exception ex)
        {
            // Toast.makeText(this,"Error:"+ex.toString(),Toast.LENGTH_LONG).show();
        }

        try {
            title.setText(ar.get(0).get("k1"));
            editperson.setImageDrawable(new BitmapDrawable(BitmapFactory.decodeFile(ar.get(0).get("k3"))));
            description.setText(ar.get(0).get("k2"));
            path=ar.get(0).get("k3");
            selection1=(Integer.parseInt(ar.get(0).get("k6")));
            pendingf=Integer.parseInt(ar.get(0).get("k7"));
            if(Integer.parseInt(ar.get(0).get("k7"))==0)
            {
                pending.setBackgroundColor(getResources().getColor(R.color.blue_grey_700));
            }
            else if(Integer.parseInt(ar.get(0).get("k7"))==1)
            {
                completed.setBackgroundColor(getResources().getColor(R.color.blue_grey_700));
            }
            tilti=DreamList.items.get(posit).name;
            end_date.setText(ar.get(0).get("k8"));
            tags.setSelection(Integer.parseInt(ar.get(0).get("k6"))-1);
          //  et_tag.setText(ar.get(0).get("k4"));

        }
        catch (Exception ex)
        {
            //Toast.makeText(this, ""+ex, Toast.LENGTH_SHORT).show();
        }
    }
    }
