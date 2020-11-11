package com.heartmeetsoul.visionboard;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;



public  class ChooserFragment  extends DialogFragment {

    private static final int CAMERA_PIC_REQUEST =0 ;
    private static final int SELECT_PICTURE = 1;
    private Dialog mDialog;

    public ChooserFragment()
    {

    }
@NonNull
@Override
public Dialog onCreateDialog(Bundle savedInstannceState)
{DreamActivity dreamActivity=new DreamActivity();

    if (mDialog == null) {
        mDialog = new Dialog(getActivity(), R.style.cart_dialog);

        //  AlertDialog.Builder builder=new AlertDialog.Builder(getActivity(),R.style.MyCustomTheme);

        mDialog.setContentView(R.layout.layout_cart_dialog);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.setTitle("Select Photo from");
        mDialog.getWindow().setGravity(Gravity.BOTTOM);
        View view = mDialog.getWindow().getDecorView();
        //String content=getArguments().getString("content");
//        TextView text=(TextView)view.findViewById(R.id.tt1);
//        text.setText(content);


        ((ImageView)view.findViewById(R.id.camera)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
               startActivityForResult(cameraIntent,CAMERA_PIC_REQUEST);
            }
        });
        ((ImageView)view.findViewById(R.id.gallery)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent=new Intent(Intent.ACTION_GET_CONTENT);
                    galleryIntent.setType("image/*");
                    startActivityForResult(galleryIntent.createChooser(galleryIntent,"Select Photo"),SELECT_PICTURE);
            }
        });

        ((TextView)view.findViewById(R.id.cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        //  return builder.create();
    }
    DreamActivity.drawer.closeDrawers();
    return mDialog;



}

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK)
        {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
//                String selectedImagePath = getPath(selectedImageUri);
//                //tv.setText(selectedImagePath);
               // ((ImageView)DreamActivity.layout.findViewById(R.id.avatar)).setImageURI(selectedImageUri);
            }

            if (requestCode == CAMERA_PIC_REQUEST) {
                try {
                    Bitmap image = (Bitmap) data.getExtras().get("data");
                   // ImageView imageview = (ImageView)DreamActivity.layout.findViewById(R.id.avatar); //sets imageview as the bitmap
                   // imageview.setImageBitmap(image);
                }
                catch (Exception ex)
                {

                }
            }
        }
        else if(resultCode==Activity.RESULT_CANCELED)
        {
            return;
        }


    }
}
