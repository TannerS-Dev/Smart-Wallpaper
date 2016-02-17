package com.tanners.smartwallpaper.flickrdata;

import android.content.Context;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
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
    ImageButton image_button;
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
       // ImageButtonHolder view_holder;
        boolean click = true;

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.grid_item, parent, false);

           // view_holder = new ImageButtonHolder();
        image_button = (ImageButton) view.findViewById(R.id.grid_image);

        // iused to get screen size to set picture size
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            int screenWidth = metrics.widthPixels;
         image_button.setLayoutParams(new RelativeLayout.LayoutParams(screenWidth / 2, screenWidth / 2));

            final LayoutInflater newlayoutInflater = layoutInflater;
            final int finalPosition = position;


            image_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View popup_view;
                    popup_view = newlayoutInflater.inflate(R.layout.picture_view, null);

                    ImageView image_view = (ImageView) popup_view.findViewById(R.id.background_image);
                    Button set_background_btn = (Button) popup_view.findViewById(R.id.background_btn);
                    TextView text_view = (TextView) popup_view.findViewById(R.id.background_text);

                    Picasso.with(context).load(photos.get(finalPosition).getUrl_z()).into(image_view);

                    // final PopupWindow popup_window = new PopupWindow(popup_view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    DisplayMetrics metrics = context.getResources().getDisplayMetrics();
                    int screen_width = metrics.widthPixels;
                    int screen_hight = metrics.heightPixels;


                    final PopupWindow popup_window = new PopupWindow(popup_view, (int)(screen_width *.9) , (int)(screen_hight * .7));

                    // make it so when you click anywhere the window closes

                    popup_window.setOutsideTouchable(true);
                    popup_window.setTouchable(true);
                    //popup_window.showAsDropDown(v);
                    popup_window.setBackgroundDrawable(new ColorDrawable());

                    //popup_window.setFocusable(true);
                   // popup_window.update();

                    //---
                    popup_window.showAtLocation(v, Gravity.CENTER, 0, 0);

                }
            });



        final String tag = this.photos.get(position).getUrl_z();
        Picasso.with(context).load(photos.get(position).getUrl_z()).fit().into(image_button);

        return view;
    }


}

