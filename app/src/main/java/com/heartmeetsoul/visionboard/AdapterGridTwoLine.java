package com.heartmeetsoul.visionboard;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;



import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

public class AdapterGridTwoLine extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
ShimmerFrameLayout container;
    private List<Image> items = new ArrayList<>();

    private OnLoadMoreListener onLoadMoreListener;

    private Context ctx;
    int textColor;
    int backgroundColor;
    private OnItemClickListener mOnItemClickListener;
    public int selection;
    private String path;

    public interface OnItemClickListener {
        void onItemClick(View view, Image obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public AdapterGridTwoLine(Context context, List<Image> items) {
        this.items = items;
        ctx = context;

    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView name;
        public TextView brief;
        public View lyt_parent;
        public TextView pend;

        public OriginalViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.image);
           name = (TextView) v.findViewById(R.id.name);
            brief = (TextView) v.findViewById(R.id.brief);
            lyt_parent = (View) v.findViewById(R.id.lyt_parent);
            pend=(TextView)v.findViewById(R.id.pend);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_image_two_line, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Image obj = items.get(position);
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;
           // setToolbarColor(((BitmapDrawable)obj.imageDrw).getBitmap());
           view.name.setText(obj.name);
            view.brief.setText(obj.brief);
//            view.name.setTextColor(ctx.getResources().getColor(R.color.grey_800));
//            view.brief.setTextColor(ctx.getResources().getColor(R.color.grey_800));
            view.brief.setSelected(true);
            selection=obj.selection;
            path=obj.path;

            view.image.setImageDrawable(obj.imageDrw);
            view.pend.setText(obj.tags);
//            if(obj.pending==0)
//                view.pend.setText("Pending");
//            else if(obj.pending==1)
//            {
//                view.pend.setText("Completed");
//            }
          //  Tools.displayImageOriginal(ctx, view.image, obj.imageDrw);
            container=view.lyt_parent.findViewById(R.id.shimmer_view_container2);
            container.setBaseAlpha(.4f);


          //  container.setDuration(1400);
            container.setDropoff(0.9f);

           // container.startShimmerAnimation();
            view.image.setImageDrawable(obj.imageDrw);
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

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public interface OnLoadMoreListener {
        void onLoadMore(int current_page);
    }

}