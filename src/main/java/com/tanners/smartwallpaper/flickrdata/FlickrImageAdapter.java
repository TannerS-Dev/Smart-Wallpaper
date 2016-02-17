package com.tanners.smartwallpaper.flickrdata;

import android.content.Context;

import android.content.Intent;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;

import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tanners.smartwallpaper.R;

import com.tanners.smartwallpaper.ResultsActivity;
import com.tanners.smartwallpaper.flickrdata.photodata.FlickrPhotoItem;

import java.util.List;


//*************************************************************************************************************************88
//// TODO: 2/16/2016
// https://www.youtube.com/watch?v=fn5OlqQuOCk   pixel dm stuff


public class FlickrImageAdapter extends BaseAdapter
{
    Context context;
    List<FlickrPhotoItem> photos;
    PopupWindow popUp;
//    final LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    public FlickrImageAdapter(Context context, int resource, List<FlickrPhotoItem> photos)
    {
        super();
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
        ImageButtonHolder view_holder;
        boolean click = true;
        //int position = 0;

        if (convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate(R.layout.grid_item, parent, false);
            view_holder = new ImageButtonHolder();
            view_holder.Image_button = (ImageButton) convertView.findViewById(R.id.grid_image);

            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            int screenWidth = metrics.widthPixels;
            view_holder.Image_button.setLayoutParams(new RelativeLayout.LayoutParams(screenWidth / 2, screenWidth / 2));

            final LayoutInflater newlayoutInflater = layoutInflater;
            final int finalPosition = position;

            view_holder.Image_button.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    //LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View popup_view;
                    popup_view = newlayoutInflater.inflate(R.layout.picture_view, null);

                    ImageView image_view = (ImageView) popup_view.findViewById(R.id.background_image);
                    Button set_background_btn = (Button) popup_view.findViewById(R.id.background_btn);
                    TextView text_view = (TextView) popup_view.findViewById(R.id.background_text);

                    Picasso.with(context).load(photos.get(finalPosition).getUrl_z()).into(image_view);

                   // final PopupWindow popup_window = new PopupWindow(popup_view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    final PopupWindow popup_window = new PopupWindow(popup_view, 1200, 1200);

                    popup_window.showAtLocation(v, Gravity.CENTER, 0, 0);

                    set_background_btn.setOnClickListener(new Button.OnClickListener() {
                        @Override
                        public void onClick(View v2) {
                            popup_window.dismiss();
                        }
                    });
                }
            });


            convertView.setTag(view_holder);
        }
        else
        {
            view_holder = (ImageButtonHolder) convertView.getTag();
        }


        final String tag = this.photos.get(position).getUrl_z();
        Picasso.with(context).load(photos.get(position).getUrl_z()).fit().into(view_holder.Image_button);

        return convertView;
    }


    // https://www.codeofaninja.com/2013/09/android-viewholder-pattern-example.html
    // http://developer.android.com/training/improving-layouts/smooth-scrolling.html

    static public class ImageButtonHolder
    {
        ImageButton Image_button;
    }
}

