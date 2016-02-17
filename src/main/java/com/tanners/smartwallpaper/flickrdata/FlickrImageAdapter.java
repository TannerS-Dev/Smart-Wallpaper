package com.tanners.smartwallpaper.flickrdata;

import android.content.Context;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;

import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;
import com.tanners.smartwallpaper.R;

import com.tanners.smartwallpaper.flickrdata.photodata.FlickrPhotoItem;

import java.util.List;



public class FlickrImageAdapter extends BaseAdapter
{
    Context context;
    List<FlickrPhotoItem> photos;
    PopupWindow popUp;

    public FlickrImageAdapter(Context context, int resource, List<FlickrPhotoItem> photos)
    {
        this.context = context;
        this.photos = photos;
        popUp = new PopupWindow(this.context);
    }

    @Override
    public int getCount()
    {
        return this.photos.size();
    }

    @Override
    public Object getItem(int position)
    {
        return this.photos.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ImageViewHolder view_holder;
        //ImageView image_view;

        if (convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.grid_item, parent, false);
            view_holder = new ImageViewHolder();
           // image_view = new ImageView(context);

            //view_holder.image_view = new ImageView(context);

            view_holder.image_view = (ImageView) convertView.findViewById(R.id.grid_image);

            //view_holder.image_view = new ImageView(context);

            // this cant be realtive

            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            int screenWidth = metrics.widthPixels;

           // LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            //convertView.setLayoutParams(new GridView.LayoutParams(params));

             view_holder.image_view.setLayoutParams(new RelativeLayout.LayoutParams(screenWidth/2, screenWidth/2));
            //view_holder.image_view.setLayoutParams(new RelativeLayout.LayoutParams(params));
            convertView.setTag(view_holder);

            but.setOnClickListener(new OnClickListener() {

                public void onClick(View v) {
                    if (click) {
                        popUp.showAtLocation(mainLayout, Gravity.BOTTOM, 10, 10);
                        popUp.update(50, 50, 300, 80);
                        click = false;
                    } else {
                        popUp.dismiss();
                        click = true;
                    }
                }

            });



        }
        else {
            view_holder = (ImageViewHolder) convertView.getTag();
        }


        final String tag = this.photos.get(position).getUrl_z();
        Picasso.with(context).load(photos.get(position).getUrl_z()).fit().into(view_holder.image_view);

       // return  view_holder.image_view;
        return convertView;


    }

    // https://www.codeofaninja.com/2013/09/android-viewholder-pattern-example.html
    // http://developer.android.com/training/improving-layouts/smooth-scrolling.html

    static class ImageViewHolder
    {
        ImageView image_view;
    }
}