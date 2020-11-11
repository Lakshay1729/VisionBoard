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
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
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
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

import pl.aprilapps.easyphotopicker.EasyImage;


public class AddDream extends AppCompatActivity {
    private static final int CAMERA_PIC_REQUEST = 0;
    private static final int SELECT_PICTURE = 1;
    Toolbar toolbar;
    ImageView personImage;
    EditText title,description,tags;
    //NachoTextView tags;
    private ActionBar actionBar;
    EasyImage easyImage1;
    SQLiteDatabase 	db;
    MyDataBase mdb;
    static int i=0;
    View v;
    ImageView mic,mic1;
    DataBaseHandler dataBaseHandler;
    private int REQ_CODE_SPEECH_INPUT=100;
    int flag=1;
    ArrayList tagslist;
    ImageView datepicker;
    private DatePickerDialog.OnDateSetListener myDateListener;
    EditText end_date;
    Button pending,completed;
    String path;
    private int f=0;
    private int pendingf=0;
     Spinner spinner;
int selection;
String serialize;
    SharedPreferences sharedPreferences;
    public static final String PREF_FILE_NAME = "PrefFile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setTheme(R.style.DarkTheme);
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
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
       // themeUtils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_add_dream);
        initToolbar();

        iniComponent();

         title=(EditText)findViewById(R.id.title);
         description=(EditText)findViewById(R.id.description);
         tags=(EditText)findViewById(R.id.tags);

         tagslist=new ArrayList<>();

          datepicker=(ImageView)findViewById(R.id.date_picker);
          pending=(Button)findViewById(R.id.pending);
          completed=(Button)findViewById(R.id.completed);


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
         spinner = (Spinner) findViewById(R.id.tags_spinner);

        myDateListener=new DatePickerDialog.OnDateSetListener() {

            @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        end_date.setText("End Date: "+year+"-"+month+"-"+dayOfMonth);
               }
};
        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(AddDream.this,myDateListener,Calendar.getInstance().get( Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });



       spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(final AdapterView<?> parent, View view, final int position, long id)
           {
               selection=position;
               spinner.setSelection(position);
                  if(parent.getItemAtPosition(position).toString().equals("Add New Category"))
                  {
                      final CoordinatorLayout rela=(CoordinatorLayout) findViewById(R.id.add_dream);
                      AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(AddDream.this,R.style.Dialog_Theme));


                      builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {
                              dialog.dismiss();


                          }
                      });
                      v = LayoutInflater.from(AddDream.this).inflate(R.layout.layout_alert,null);
                      builder.setView(v);


                      builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
                      {
                          @Override
                          public void onClick(DialogInterface dialog, int which)
                          {

                              String str=   ((EditText) v.findViewById(R.id.edt)).getText().toString();
                              tagslist.add(str);
                              parent.setSelection(tagslist.size()-1);
                              selection=tagslist.size()-1;
                            //  serialize+=str;



                              String a=spinner.getSelectedItem().toString();
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

        spinner.setAdapter(adapter);

        spinner.setSelection(tagslist.size()-1);

         //tags=(NachoTextView)findViewById(R.id.et_tag);
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


        //initNavigationMenu();
//        DreamActivity dreamActivity=new DreamActivity();
//        dreamActivity.initNavigationMenu();
            dataBaseHandler=new DataBaseHandler(this);
            personImage=(ImageView)findViewById(R.id.personImage);
            personImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK,Uri.parse("content://media/internal/images/media"));
////                intent.setType("image/*");
////                intent.setAction(Intent.ACTION_GET_CONTENT);
//
//                startActivityForResult(intent, SELECT_PICTURE);

//                EasyImage.Builder easyImage = new EasyImage.Builder(getBaseContext());
//                easyImage.setChooserTitle("Pick Image");
//                easyImage.setChooserType(ChooserType.CAMERA_AND_DOCUMENTS);
//                easyImage.setChooserType(ChooserType.CAMERA_AND_GALLERY);
//               easyImage .setCopyImagesToPublicGalleryFolder(false);
//               easyImage.setFolderName("EasyImage sample").allowMultiple(false);
//                easyImage1=easyImage.build();
//                easyImage1.openChooser(AddDream.this);


                Intent intent=new Intent(getApplicationContext(),EditImageActivity.class);
                intent.putExtra("flag1",0);
                startActivity(intent);
                finish();







// Chooser only
// Will appear as a system chooser title, DEFAULT empty string
//.setChooserTitle("Pick media")
// Will tell chooser that it should show documents or gallery apps
//.setChooserType(ChooserType.CAMERA_AND_DOCUMENTS)  you can use this or the one below
//.setChooserType(ChooserType.CAMERA_AND_GALLERY)

            }
        });


    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
       // toolbar.setNavigationIcon(R.drawable.ic_menu);

//        actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setHomeButtonEnabled(true);
//        actionBar.setTitle("Drawer Image");
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Dream");
        getSupportActionBar().setElevation(10.0f);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this);
    }

    private void iniComponent() {
//        NachoTextView et_tag = (NachoTextView) findViewById(R.id.et_tag);
//        List<String> items = new ArrayList<>();
//        items.add("black");
//        items.add("building");
//        items.add("city");
//        et_tag.setText(items);
//        et_tag.addChipTerminator('\n', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_ALL);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_done, menu);
        return true;
    }

    @SuppressLint("WrongConstant")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home)
        {
            finish();
//            DreamActivity dreamActivity=new DreamActivity();
//            dreamActivity.initNav,igationMenu();
        }
//        else
        if(item.getItemId()==R.id.action_done)
            {
           // Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();

                try {
                    String r = title.getText().toString().trim();
                   // String a=tags.getText().toString().trim();
                  //  String a=spinner.getSelectedItem().toString();
                    String b=description.getText().toString().trim();
                    String date=end_date.getText().toString().trim();

                    @SuppressLint("WrongConstant")
                    SQLiteDatabase db=openOrCreateDatabase("lakshay.db",SQLiteDatabase.CREATE_IF_NECESSARY,null);
//                    String st="CREATE TABLE IF NOT EXISTS Student "+"(id primary key AUTOINCREMENT,title TEXT ,tag Integer,description TEXT,path Text,date Text,pending Integer)";
//                    db.execSQL(st);
//
//                    String string="CREATE TABLE IF NOT EXISTS Student3 "+"(id primary key AUTOINCREMENT,tag TEXT)";
//                    db.execSQL(string);
                    ContentValues values = new ContentValues();
                    ContentValues values1=new ContentValues();
                    try {
                        if(f==1)
                        {
                            if (!(r.length()==0)  && !(b.length()==0)&&!(date.length()==0))
                            {

                                values.put("title", r);
                                values.put("tagid", selection+1);

                                values.put("description", b);
                                values.put("path", path);
                                values.put("date",date);
                                values.put("pending",pendingf);
                               // Toast.makeText(this, ""+selection, Toast.LENGTH_SHORT).show();
                                Log.i("selection",selection+"");
                            }
                            else
                            {
                                Snackbar.make(findViewById(android.R.id.content),"All fields are mandatory",Snackbar.LENGTH_LONG).show();
                            }
                        }
                        else
                        {
                            Snackbar.make(findViewById(android.R.id.content),"Put Image First",Snackbar.LENGTH_SHORT).show();
                        }
                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                    }


                    Long re=db.insert("Student",null,values);
                    //Long re1=db.insert("Student3",null,values1);

                    if(re>0)
                    {
                       // Toast.makeText(this,"Values inserted",Toast.LENGTH_LONG).show();
                        finish();

                    }
                    db.close();
                } catch (Exception ex) {

                   // Toast.makeText(this, "Error"+ex.toString(), Toast.LENGTH_SHORT).show();
                }


  }
        return super.onOptionsItemSelected(item);
    }



//    public void Camera(View view) {
//        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//      startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
//
//    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
              //  Toast.makeText(this, "" + selectedImageUri, Toast.LENGTH_LONG).show();
                String x = getPath(selectedImageUri);

              //  Toast.makeText(this, "" + x, Toast.LENGTH_LONG).show();
//                if (dataBaseHandler.insertimage(x, i++)) {
//                   // Toast.makeText(this, "Images Inserted", Toast.LENGTH_LONG).show();
//                } else {
//                    //Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
//                }

                personImage.setImageURI(selectedImageUri);

            }


            if (requestCode == CAMERA_PIC_REQUEST) {
                try {
                    Bitmap image = (Bitmap) data.getExtras().get("data");
//                    Toast.makeText(this, "" + requestCode, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(this, "" + data.getData(), Toast.LENGTH_LONG).show();

                    String x = getPath(data.getData());


                   // Toast.makeText(this, "" + x, Toast.LENGTH_LONG).show();
//                    if (dataBaseHandler.insertimage(x, i++)) {
//                        Toast.makeText(this, "Images Inserted", Toast.LENGTH_LONG).show();
//                    } else {
//                        Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
//                    }
                    personImage.setImageBitmap(image);
                } catch (Exception ex) {
                   // Toast.makeText(this, "" + ex, Toast.LENGTH_LONG).show();
                }
            }
        }
        if (requestCode == REQ_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && null != data) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//                ArrayList list = new ArrayList();
//                list.add(result);
//                Iterator itr = list.listIterator();

                    if(flag==1)
                    description.setText(result.get(0));
                    else if(flag==0)
                        title.append(result.get(0));


            }
        }



    }

    private String getPath(Uri uri) {

    if(uri==null) return null;

    String[]projection={MediaStore.Images.Media.DATA};
    Cursor cursor=managedQuery(uri,projection,null,null,null);
    if(cursor!=null)
    {
        int column_index=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    return uri.getPath();
    }


    @Override
    public void onStart() {
        super.onStart();
        //personImage.setImageDrawable(new BitmapDrawable(new DataBaseHandler(this).getImage(3)));
        try {
            path = getIntent().getStringExtra("path");
            //Toast.makeText(this, "" + path, Toast.LENGTH_SHORT).show();
            personImage.setImageURI(Uri.fromFile(new File(path)));
            if(!path.equals(null))
            {
                f=1;
            }
        }
        catch (Exception ex)
        {

        }

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
