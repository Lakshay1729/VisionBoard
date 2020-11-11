package com.heartmeetsoul.visionboard;

import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import androidx.appcompat.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity {

    PaintView paintView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        paintView = (PaintView) findViewById(R.id.paintview);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        paintView.init(metrics);

        paintView.emboss();


    }
    @Override
    public void onSaveInstanceState(Bundle outState)
    { super.onSaveInstanceState(outState);


       // outState.putInt(SELECTED_ITEM_POSITION, mPosition);
    }
@Override
    public void onRestoreInstanceState(Bundle onSavedInstanceState)
{
    super.onRestoreInstanceState(onSavedInstanceState);
}


//   @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.menu, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.normal:
//                paintView.normal();
//                return true;
//            case R.id.emboss:
//                paintView.emboss();
//                return true;
//            case R.id.blur:
//                paintView.blur();
//                return true;
//            case R.id.clear:
//                paintView.clear();
//                return true;
//
//            case R.id.brush:
//                paintView.brush();
//                return true;
//
//            case R.id.color:
//                paintView.paintColor();
//                return true;
//            case R.id.dream:
//                startActivity(new Intent(MainActivity.this,DreamActivity.class));
//
//        }
//        return super.onOptionsItemSelected(item);
//    }
}