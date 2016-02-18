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
   // ImageButton image_button;
    FlickrDataUserInfo user_data;


//    final LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    public FlickrImageAdapter(Context context, int resource, List<FlickrPhotoItem> photos)
    {
        super();
        this.context = context;
        this.photos = photos;
        popUp = new PopupWindow(this.context);
        user_data = new FlickrDataUserInfo();
        //image_button = null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater layoutInflater = null;
       final  PopUpWindowViewerHolder view_holder;
       // final String tag = this.photos.get(position).getUrl_z();
       // ImageView image_view = null
        //Button set_background_btn = null
        //TextView text_view = null;
        //final PopupWindow popup_window = null;

        if(convertView == null)
        {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //View view = layoutInflater.inflate(R.layout.grid_item, parent, false);
            view_holder = new PopUpWindowViewerHolder();

            convertView = layoutInflater.inflate(R.layout.picture_view, parent, false);
            view_holder.image_view = (ImageView) convertView.findViewById(R.id.background_image);
            view_holder.set_background_btn = (Button) convertView.findViewById(R.id.background_btn);
            view_holder.text_view = (TextView) convertView.findViewById(R.id.background_text);

           // convertView.setTag(view_holder);

            convertView = layoutInflater.inflate(R.layout.grid_item, parent, false);
           // view_holder = new PopUpWindowViewerHolder();
            view_holder.image_button = (ImageButton) convertView.findViewById(R.id.grid_image);

           // convertView = null;
           // layoutInflater = (LayoutInflater) context.getSystemService(Context.LAY
           //
           // OUT_INFLATER_SERVICE);
            //convertView.setTag(view_holder);
            //convertView.setTag(view_holder);
            convertView.setTag(view_holder);
        }
        else
        {
            view_holder = (PopUpWindowViewerHolder) convertView.getTag();
        }
        Picasso.with(context).load(photos.get(position).getUrl_z()).fit().into(view_holder.image_button);
       // layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //convertView = layoutInflater.inflate(R.layout.grid_item, parent, false);
      //  Picasso.with(context).load(photos.get(position).getUrl_z()).fit().into(view_holder.image_button);




        // is used to get screen size to set picture size
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int screenWidth = metrics.widthPixels;

        view_holder.image_button.setLayoutParams(new RelativeLayout.LayoutParams(screenWidth / 2, screenWidth / 2));



            // must use final for inner class to use these values
           // final LayoutInflater newlayoutInflater = layoutInflater;
            //int finalPosition = position;
            final int finalPosition = position;
          //  final ImageView final_image_view = view_holder.image_view;

        //convertView = layoutInflater.inflate(R.layout.picture_view, parent, false);
       // final View[] finalConvertView = {convertView};


        view_holder.image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View popup_view = layoutInflater.inflate(R.layout.picture_view, null);

                //ImageView image_view = (ImageView) popup_view.findViewById(R.id.background_image);
                //Button set_background_btn = (Button) popup_view.findViewById(R.id.background_btn);
                //TextView text_view = (TextView) popup_view.findViewById(R.id.background_text);
                // final PopupWindow popup_window = new PopupWindow(popup_view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                DisplayMetrics metrics = context.getResources().getDisplayMetrics();
                int screen_width = metrics.widthPixels;
                int screen_height = metrics.heightPixels;
                final PopupWindow popup_window = new PopupWindow(popup_view, (int) (screen_width * .9), (int) (screen_height * .7));

                // make it so when you click anywhere the window closes
                popup_window.setOutsideTouchable(true);
                popup_window.setTouchable(true);
                popup_window.setBackgroundDrawable(new ColorDrawable());
                popup_window.showAtLocation(popup_view, Gravity.CENTER, 0, 0);


                //finalConvertView[0] = layoutInflater.inflate(R.layout.picture_view, null);
                Picasso.with(context).load(photos.get(finalPosition).getUrl_z()).into(view_holder.image_view);
                Log.i("pic", photos.get(finalPosition).getUrl_z());
                Log.i("pic",Integer.toString(screen_width) + " " + Integer.toString(screen_height));
            }

            private void setPictureInfo() {
                String photo_info = null;

                    /*
                    user_data.genrateUserInfo(photos.get(finalPosition).getOwner());

                    photo_info += "Name: " + user_data.getFullName() + "/n";
                    photo_info += "Username: " + user_data.getUsername() + "/n";
                    photo_info += "ID: " + photos.get(finalPosition).getId() + "/n";
                    photo_info += "Title: " + photos.get(finalPosition).getTitle() + "/n";
                    photo_info += "Owner: " + photos.get(finalPosition).getOwner() + "/n";
                    photo_info += "Height: " + photos.get(finalPosition).getHeight_z() + "/n";
                    photo_info += "Width: " + photos.get(finalPosition).getWidth_z() + "/n";

                   // text_view.setText(photo_info);
                   */
            }


        });




        //final String tag = this.photos.get(position).getUrl_z();

        //Log.i("pic", photos.get(position).getUrl_z());

        return convertView;
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

    static private class PopUpWindowViewerHolder
    {
        ImageView image_view = null;
        Button set_background_btn = null;
        TextView text_view = null;
        ImageButton image_button = null;
    }


}

