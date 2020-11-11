package com.heartmeetsoul.visionboard;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.facebook.shimmer.ShimmerFrameLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

class AdapterList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Image> items = new ArrayList<>();
ImageButton share;
    private AdapterList.OnLoadMoreListener onLoadMoreListener;

    private Context ctx;
    private AdapterList.OnItemClickListener mOnItemClickListener;
    private int backgroundColor;
    public int selection;
    private String path;

    public void setOnItemClickListener(AdapterList.OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, Image obj, int position);
    }

ShimmerFrameLayout shimmerFrameLayout;

    public AdapterList(Context context, List<Image> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView name;
        public TextView brief;
      public View lyt_parent;
public  TextView pend;

        public OriginalViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.image2);
             name = (TextView) v.findViewById(R.id.text21);
          brief = (TextView) v.findViewById(R.id.brief);
         lyt_parent = (View) v.findViewById(R.id.lyt_parent);
         share=(ImageButton)v.findViewById(R.id.share_button);
         pend=(TextView)v.findViewById(R.id.pend);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        vh = new AdapterList.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final Image obj = items.get(position);
        if (holder instanceof AdapterList.OriginalViewHolder) {
            AdapterList.OriginalViewHolder view = (AdapterList.OriginalViewHolder) holder;
             view.name.setText(obj.name);
             view.brief.setText(obj.brief);
             view.brief.setSelected(true);
//            view.name.setTextColor(ctx.getResources().getColor(R.color.grey_800));
//            view.brief.setTextColor(ctx.getResources().getColor(R.color.grey_400));
           view.image.setImageDrawable(obj.imageDrw);
           view.pend.setText(obj.tags);
//           if(obj.pending==0)
//           view.pend.setText("Pending");
//           else if(obj.pending==1)
//           {
//               view.pend.setText("Completed");
//           }

           selection=obj.selection;
           path=obj.path;
            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bitmap bitmap;
                    OutputStream output;

                    // Retrieve the image from the res folder
                    bitmap =  ((BitmapDrawable)obj.imageDrw).getBitmap();

                    // Find the SD Card path
                    File filepath = Environment.getExternalStorageDirectory();

                    // Create a new folder AndroidBegin in SD Card
                    File dir = new File(filepath.getAbsolutePath() + "/Share Image Tutorial/");
                    dir.mkdirs();


                    File file = new File(dir, "sample_wallpaper.png");

                    try {

                        // Share Intent
                        Intent share = new Intent(Intent.ACTION_SEND);

                        // Type of file to share
                        share.setType("*/*");

                        output = new FileOutputStream(file);

                        // Compress into png format image from 0% - 100%
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
                        output.flush();
                        output.close();

                        // Locate the image to Share
                        Uri uri = Uri.fromFile(file);

                        // Pass the image into an Intnet
                        share.putExtra(Intent.EXTRA_STREAM, uri);
                        share.putExtra(Intent.EXTRA_TEXT,ctx.getResources().getString(R.string.share_string));
                        share.putExtra(Intent.EXTRA_SUBJECT," Share Vision Board");

                        // Show the social share chooser list
                        ctx.startActivity(Intent.createChooser(share, "Share Dream"));

                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        // e.printStackTrace();
                       // Toast.makeText(ctx, ""+e, Toast.LENGTH_SHORT).show();
                    }
                }
            });
          //  setToolbarColor(((BitmapDrawable)obj.imageDrw).getBitmap());
           shimmerFrameLayout=view.lyt_parent.findViewById(R.id.shimmer_view_container1);
           shimmerFrameLayout.setBaseAlpha(.4f);
           shimmerFrameLayout.setDuration(2000);

          // shimmerFrameLayout.startShimmerAnimation();
            Tools.displayImageOriginal(ctx, view.image, obj.imageDrw);
            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, items.get(position), position);
                    }
                }
            });
        }
    }





    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setOnLoadMoreListener(AdapterList.OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public interface OnLoadMoreListener {
        void onLoadMore(int current_page);
    }

}
