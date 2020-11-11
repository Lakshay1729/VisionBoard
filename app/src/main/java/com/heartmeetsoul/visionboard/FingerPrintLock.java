package com.heartmeetsoul.visionboard;



import android.app.KeyguardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

//import com.example.visionboard.pin_lock.IndicatorDots;
//import com.example.visionboard.pin_lock.PinLockListener;
//import com.example.visionboard.pin_lock.PinLockView;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class FingerPrintLock extends AppCompatActivity  {

    // Declare a string variable for the key we’re going to use in our fingerprint authentication
    private static final String KEY_NAME = "yourKey";
    private Cipher cipher;
   public static  final String key="false";
    SharedPreferences sharedPreferences;
    private KeyStore keyStore;
    private KeyGenerator keyGenerator;
    private LinearLayout layout_dots;
    private TextView textView;
    private FingerprintManager.CryptoObject cryptoObject;
    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;

ImageButton ib,ib1;
int i=0;

    SharedPreferences.Editor editor;
    public static final String PREF_FILE_NAME = "PrefFile";


    public static final String TAG = "PinLockView";

    private PinLockView mPinLockView;
    private IndicatorDots mIndicatorDots;

    private PinLockListener mPinLockListener = new PinLockListener() {
        @Override
        public void onComplete(String pin) {
            Log.d(TAG, "Pin complete: " + pin);
            try {
               // Toast.makeText(FingerPrintLock.this, "" + pin, Toast.LENGTH_SHORT).show();

                if(getIntent().hasExtra("pin"))
                {
                    if(pin!=null)
                    editor.putString("pin",pin);
                    finish();
                }

                    else
                if (pin.equals(sharedPreferences.getString("pin","1234"))) {

                    startActivity(new Intent(getApplicationContext(), DreamList.class));
                }
                else {
                      profile_id1.setText("Wrong Pin");
                      profile_id1.setTextColor(getResources().getColor(R.color.red_400));
                    mPinLockView = (PinLockView) findViewById(R.id.pin_lock_view);
                    mIndicatorDots = (IndicatorDots) findViewById(R.id.indicator_dots);

                    mPinLockView.attachIndicatorDots(mIndicatorDots);
                    mPinLockView.setPinLockListener(mPinLockListener);
                    //mPinLockView.setCustomKeySet(new int[]{2, 3, 1, 5, 9, 6, 7, 0, 8, 4});
                    //mPinLockView.enableLayoutShuffling();

                    mPinLockView.setPinLength(4);
                    mPinLockView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

                    mIndicatorDots.setIndicatorType(IndicatorDots.IndicatorType.FILL_WITH_ANIMATION);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = getIntent();
                    finish();
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    startActivity(intent);

                        }
                    },100);

                }
            }
            catch(Exception ex)
            {
                Toast.makeText(FingerPrintLock.this, ""+ex, Toast.LENGTH_SHORT).show();
            }
        }


        @Override
        public void onEmpty() {
            Log.d(TAG, "Pin empty");
        }

        @Override
        public void onPinChange(int pinLength, String intermediatePin) {
            Log.d(TAG, "Pin changed, new length " + pinLength + " with intermediate pin " + intermediatePin);
           // Toast.makeText(FingerPrintLock.this, ""+intermediatePin, Toast.LENGTH_SHORT).show();
//            if(pinLength==4)
//            {
//                onComplete(intermediatePin);
//            }
        }
    };

       @Override
       public void onDestroy()
       {
    super.onDestroy();
    this.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
}
Bundle savedIntance;
       String pinlock;
       String lockscreen;
    TextView profile_id1;
ShimmerFrameLayout shimmerFrameLayout;
RelativeLayout rel;
ImageView profile_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(!getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE).getBoolean("pinlock",false))
        {
            startActivity(new Intent(this,DreamList.class));
        }
      sharedPreferences = getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE);
//        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES)
//        {
//            //restartApp();
//            setTheme(R.style.DarkTheme);
//
//        }
//        else if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_NO)
//        {
//
//            setTheme(R.style.AppTheme);
//        }

        if(sharedPreferences.getString("pin","1234").equals(null))
        {
            finish();
        }
        super.onCreate(savedInstanceState);
        savedIntance=savedInstanceState;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_finger_print_lock);


       if(getIntent().hasExtra("pin"))
       {
           ((TextView)findViewById(R.id.title_text)).setText("Lock Your Dreams");
           ((TextView)findViewById(R.id.profile_name1)).setText("Change Your Pin");
       }
        profile_image=findViewById(R.id.profile_image);
        if(sharedPreferences.getBoolean("fingerlock",false))
        {
            profile_image.setVisibility(View.VISIBLE);
        }
        else if(!sharedPreferences.getBoolean("fingerlock",false))

        profile_image.setVisibility(View.INVISIBLE);

         rel=(RelativeLayout)findViewById(R.id.fingery);
        if(sharedPreferences.getBoolean("theme",false))
        {
            rel.setBackgroundColor(getColor(R.color.grey_1000));
        }
        else if(!sharedPreferences.getBoolean("theme",false))
        {
            rel.setBackgroundColor(getColor(R.color.blue_A200));
        }

        shimmerFrameLayout=(ShimmerFrameLayout)findViewById(R.id.profile_name2);
        shimmerFrameLayout.setDuration(1800);
        shimmerFrameLayout.startShimmerAnimation();
         profile_id1=(TextView)findViewById(R.id.profile_name1);
         profile_id1.setAnimation(AnimationUtils.loadAnimation(this,R.anim.animate));
        //sharedPreferences = getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        Tools.setSystemBarColor(this,R.color.cyan_300);
        Tools.setSystemBarLight(this);


       // SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
//        try {
//            if (getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE).contains("starting")) {
//                Toast.makeText(this, "Shared Preference", Toast.LENGTH_SHORT).show();
//            }
//        }
//        catch (Exception ex)
//        {
//            Toast.makeText(this, ""+ex, Toast.LENGTH_SHORT).show();
//        }
       // String restoredText = prefs.getString("text", null);

//             pinlock = prefs.getString("starting", "false");//"No name defined" is the default value.
//        lockscreen=prefs.getString("lockscreen","false");


//        if(pinlock.equals("true"))
//        {
//            startActivity(new Intent(this,DreamActivity.class));
//        }
        this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


        mPinLockView = (PinLockView) findViewById(R.id.pin_lock_view);
        mIndicatorDots = (IndicatorDots) findViewById(R.id.indicator_dots);

       mPinLockView.attachIndicatorDots(mIndicatorDots);
       mPinLockView.setPinLockListener(mPinLockListener);
        //mPinLockView.setCustomKeySet(new int[]{2, 3, 1, 5, 9, 6, 7, 0, 8, 4});
       // mPinLockView.enableLayoutShuffling();

        mPinLockView.setPinLength(4);
        mPinLockView.setTextColor(ContextCompat.getColor(this, R.color.white));

        mIndicatorDots.setIndicatorType(IndicatorDots.IndicatorType.FILL_WITH_ANIMATION);
       // Intent intent;
//        if (sharedPreferences.getString("key",null).equals(true)) {
//            intent = new Intent(this, DreamActivity.class);
//        }


        //layout_dots = (LinearLayout) findViewById(R.id.layout_dots);
        //addBottomDots(layout_dots,4,0);

        //textView = (TextView) findViewById(R.id.textview);
//        ((ImageView)findViewById(R.id.imageView)).setAnimation(AnimationUtils.loadAnimation(this,R.anim.anima));
//        enter=(TextView)findViewById(R.id.enter);
//        enter.setAnimation(AnimationUtils.loadAnimation(this,R.anim.animate));

//        t1=(TextView)findViewById(R.id.t1);
//        t2=(TextView)findViewById(R.id.t2);
//        t3=(TextView)findViewById(R.id.t3);
//        t4=(TextView)findViewById(R.id.t4);
//        t5=(TextView)findViewById(R.id.t5);
//        t6=(TextView)findViewById(R.id.t6);
//        t7=(TextView)findViewById(R.id.t7);
//        t8=(TextView)findViewById(R.id.t8);
//        t9=(TextView)findViewById(R.id.t9);
//        t0=(TextView)findViewById(R.id.t0);


//        ib=(ImageButton)findViewById(R.id.backscape);
//        ib1=(ImageButton)findViewById(R.id.done);

//        t1.setOnClickListener(this);
//        t2.setOnClickListener(this);
//        t3.setOnClickListener(this);
//        t4.setOnClickListener(this);
//        t4.setOnClickListener(this);
//        t5.setOnClickListener(this);
//        t6.setOnClickListener(this);
//        t7.setOnClickListener(this);
//        t8.setOnClickListener(this);
//        t9.setOnClickListener(this);
//        t0.setOnClickListener(this);



//        ib.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    addBottomDots(layout_dots, 4,--i);
////                    String str = textView.getText().toString();
////                    textView.setText(str.substring(0, str.length() - 2));
//                } catch (Exception ex)
//                {
//
//                }
//            }
//        });


//        ib1.setOnClickListener(new View.OnClickListener() {
//            // String key=textView.getText().toString();
//            Cursor c;
//
//            @Override
//            public void onClick(View v) {
//
//                Toast.makeText(FingerPrintLock.this, "Done", Toast.LENGTH_SHORT).show();
////                    try {
////                        @SuppressLint("WrongConstant")
////                        SQLiteDatabase db = openOrCreateDatabase("Hpe.db",
////                                SQLiteDatabase.OPEN_READWRITE, null);
////
////                        String sql = "SELECT * FROM Student WHERE key=" + key;
////
////                        c = db.rawQuery(sql, null);
////                        c.moveToFirst();
//////                    if (!c.isAfterLast()) {
//////                        tx2.setText(c.getString(1));
//////                        tx3.setText(c.getString(2));
//////                    }
//////                    else
//////                    {
//////                        Toast.makeText(getApplicationContext(),"No Record found!",
//////                                Toast.LENGTH_LONG).show();
//////                    }
////                    } catch (Exception ex) {
////                        Toast.makeText(getApplicationContext(), "Error " + ex,
////                                Toast.LENGTH_LONG).show();
////                    }
//
//            }
//        });


//
//
//
//                try{
//
//                @SuppressLint("WrongConstant")
//                SQLiteDatabase db = openOrCreateDatabase
//                        ("Hpe.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
//                String sql = "CREATE TABLE IF NOT EXISTS Student " +
//                        "( roll INTEGER PRIMARY KEY,name TEXT, course TEXT )";
//                db.execSQL(sql);
//                ContentValues values = new ContentValues();
//                if(c.equals(null)) {
//
//                    values.put("key", key);
//
//                    Long re = db.insert("Student", null, values);
//                    //if you want to insert an empty row into a table student(id,name)
//                    //, which id is auto generated and name is null.
//                    // db.insert("student","name",values);
//                    if (re > 0) {
//                        Toast.makeText(getApplicationContext(), "Insert success",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                }
//                else
//                {
//                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
//
//                }
//            }
//            catch (Exception ex)
//            {
//                Toast.makeText(getApplicationContext(), "Error " + ex.toString(),
//                        Toast.LENGTH_SHORT).show();
//
//            }
//
//
//
//
//
//
//
//            }
//
//        });


        // If you’ve set your app’s minSdkVersion to anything lower than 23, then you’ll need to verify that the device is running Marshmallow
        // or higher before executing any fingerprint-related code
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Get an instance of KeyguardManager and FingerprintManager//
            keyguardManager =
                    (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
            fingerprintManager =
                    (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);



            //Check whether the device has a fingerprint sensor//
            if (!fingerprintManager.isHardwareDetected()) {
                // If a fingerprint sensor isn’t available, then inform the user that they’ll be unable to use your app’s fingerprint functionality//
                textView.setText("Your device doesn't support fingerprint authentication");
            }
            //Check whether the user has granted your app the USE_FINGERPRINT permission//
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                // If your app doesn't have this permission, then display the following text//
                textView.setText("Please enable the fingerprint permission");
                Toast.makeText(this, "Please enable the fingerprint permission", Toast.LENGTH_SHORT).show();
            }

            //Check that the user has registered at least one fingerprint//
            if (!fingerprintManager.hasEnrolledFingerprints()) {
                // If the user hasn’t configured any fingerprints, then display the following message//
                textView.setText("No fingerprint configured. Please register at least one fingerprint in your device's Settings");
            }

            //Check that the lockscreen is secured//
            if (!keyguardManager.isKeyguardSecure()) {
                // If the user hasn’t secured their lockscreen with a PIN password or pattern, then display the following text//
                textView.setText("Please enable lockscreen security in your device's Settings");
            } else {
                try {        generateKey();
                } catch (FingerprintException e) {
                    e.printStackTrace();
                }

                if (initCipher()) {
                    //If the cipher is initialized successfully, then create a CryptoObject instance//
                    cryptoObject = new FingerprintManager.CryptoObject(cipher);

                    // Here, I’m referencing the FingerprintHandler class that we’ll create in the next section. This class will be responsible
                    // for starting the authentication process (via the startAuth method) and processing the authentication process events//

                    if(sharedPreferences.getBoolean("fingerlock",false)) {
                        FingerprintHandler helper = new FingerprintHandler(this);
                        helper.startAuth(fingerprintManager, cryptoObject);
                    }
                }
            }
        }
    }

//Create the generateKey method that we’ll use to gain access to the Android keystore and generate the encryption key//

    private void generateKey() throws FingerprintException {
        try {
            // Obtain a reference to the Keystore using the standard Android keystore container identifier (“AndroidKeystore”)//
            keyStore = KeyStore.getInstance("AndroidKeyStore");

            //Generate the key//
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");

            //Initialize an empty KeyStore//
            keyStore.load(null);

            //Initialize the KeyGenerator//
            keyGenerator.init(new

                    //Specify the operation(s) this key can be used for//
                    KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)

                    //Configure this key so that the user has to confirm their identity with a fingerprint each time they want to use it//
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());

            //Generate the key//
            keyGenerator.generateKey();

        } catch (KeyStoreException | NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException | CertificateException | IOException exc) {
            exc.printStackTrace();
            throw new FingerprintException(exc);
        }
    }

    //Create a new method that we’ll use to initialize our cipher//
    public boolean initCipher() {
        try {
            //Obtain a cipher instance and configure it with the properties required for fingerprint authentication//
            cipher = Cipher.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES + "/"
                            + KeyProperties.BLOCK_MODE_CBC + "/"
                            + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        }
        catch (NoSuchAlgorithmException |
                NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }

        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                    null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            //Return true if the cipher has been initialized successfully//
            return true;
        }
        catch (KeyPermanentlyInvalidatedException e) {

            //Return false if cipher initialization failed//
            return false;
        }
        catch (KeyStoreException | CertificateException | UnrecoverableKeyException | IOException | NoSuchAlgorithmException | InvalidKeyException e)
        {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }

//    @Override
//    public void onClick(View v)
//    {
//
////       TextView text=(TextView)v;
////       textView.append(text.getText().toString()+" ");
//        try {
//            if(i<=4)
//            addBottomDots(layout_dots, 4, ++i);
//        }catch (Exception ex)
//        {
//
//        }
//
//
//
//    }

    private class FingerprintException extends Exception {
        public FingerprintException(Exception e) {
            super(e);
        }
    }
    @Override
    public void onPause() {

        super.onPause();
        editor.apply();
        editor.commit();
        finish();
    }

//    private void addBottomDots(LinearLayout layout_dots, int size, int current) {
//        ImageView[] dots = new ImageView[size];
//
//        layout_dots.removeAllViews();
//        for (int i = 0; i < dots.length; i++) {
//            dots[i] = new ImageView(this);
//            int width_height = 38;
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(width_height, width_height));
//            params.setMargins(200, 30, 10, 0);
//            dots[i].setLayoutParams(params);
//            dots[i].setImageResource(R.drawable.shape_circle_outline);
//            dots[i].setColorFilter(ContextCompat.getColor(this, R.color.blue_400), PorterDuff.Mode.SRC_ATOP);
//            layout_dots.addView(dots[i]);
//        }
//        try {
//
//            if (dots.length > 0) {
//                for (int i = 0; i < current; i++) {
//
//                    dots[i].setImageResource(R.drawable.shape_circle);
//                    dots[i].setColorFilter(ContextCompat.getColor(this, R.color.blue_grey_600), PorterDuff.Mode.SRC_ATOP);
//                }
//            }
//        }
//        catch (Exception ex)
//        {
//
//        }
//    }
   // @SuppressLint("MissingSuperCall")
//  @Override
//    public void onPause() {
//        super.onPause();
//        this.finish();
//    }
@Override
public void onResume() {
    super.onResume();
    this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

//    if (sharedPreferences.getBoolean("theme", true)) {
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//        //editor.putBoolean("theme",false);
//        // restartApp();
//    }
//    else if (!sharedPreferences.getBoolean("theme", false)) {
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        // editor.putBoolean("theme",true);
//
//        // restartApp();
//
//    }
}

}