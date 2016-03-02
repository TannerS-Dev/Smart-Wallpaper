package com.tanners.smartwallpaper.flickrdata;

import android.app.WallpaperManager;
import android.content.Context;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;

import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tanners.smartwallpaper.PhotoActivity;
import com.tanners.smartwallpaper.R;

import com.tanners.smartwallpaper.ResultsActivity;
import com.tanners.smartwallpaper.flickrdata.photodata.FlickrPhotoItem;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

//// TODO: 2/16/2016
// https://www.youtube.com/watch?v=fn5OlqQuOCk   pixel dm stuff

public class FlickrImageAdapter extends BaseAdapter
{
    Context context;
    List<FlickrPhotoItem> photos;
    PopupWindow popUp;
    ImageButton image_button;
    CardView card;
    FlickrDataUserInfo user_data;

    public FlickrImageAdapter(Context context, int resource, List<FlickrPhotoItem> photos)
    {
        super();
        this.context = context;
        this.photos = photos;
        Collections.shuffle(this.photos);
        popUp = new PopupWindow(this.context);
        user_data = new FlickrDataUserInfo();
        //Collections.shuffle(this.photos);
    }

    @Override
    public int getCount()
    {
        Log.i("test","size being returned: " + Integer.toString(this.photos.size()));
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

    // TODO optimization on this method
    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {

        // TODO performacne here, do that convertview = null shit

        boolean click = true;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.flickr_grid_image_layout, parent, false);

        image_button = (ImageButton) view.findViewById(R.id.image_button);
        card = (CardView) view.findViewById(R.id.grid_image);


        final DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int screenWidth = metrics.widthPixels;
        card.setLayoutParams(new RelativeLayout.LayoutParams(screenWidth / 2, screenWidth / 2));
       // image_button.setLayoutParams(new RelativeLayout.LayoutParams(screenWidth / 2, screenWidth / 2));

        final LayoutInflater newlayoutInflater = layoutInflater;
        final int finalPosition = position;

        //RelativeLayout rel = (RelativeLayout) view.findViewById(R.id.cards_container);

        card.setClickable(true);


        image_button.setOnClickListener(new CardView.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final Intent intent = new Intent(context, PhotoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // create string to hold image information
                String photo_info = null;
                // call object's generateUserInfo to collect the user's name based on ID
                user_data.generateUserInfo(photos.get(finalPosition).getOwner());
                // call proper classes / methods to collect info for photo
                // and append it to the string


                photo_info += "Name: " + user_data.getFullName() + "\n";
                photo_info += "Username: " + user_data.getUsername() + "\n";
                photo_info += "ID: " + photos.get(finalPosition).getId() + "\n";
                photo_info += "Title: " + photos.get(finalPosition).getTitle() + "\n";
                photo_info += "Owner: " + photos.get(finalPosition).getOwner() + "\n";
                photo_info += "Height: " + photos.get(finalPosition).getHeight_z() + "\n";
                photo_info += "Width: " + photos.get(finalPosition).getWidth_z() + "\n";

                intent.putExtra("info", photo_info);
                intent.putExtra("position", finalPosition);
                intent.putExtra("url", photos.get(finalPosition).getUrl_z());
                context.startActivity(intent);
            }
        });






        // get tag for image button
        final String tag = this.photos.get(position).getUrl_t();
        // put image into grid
        Picasso.with(context).load(photos.get(position).getUrl_t()).centerInside().into(image_button);
        // return current view
        return view;
    }

    private static class ViewHolder
    {
        private final ImageView image_button;
        private final CardView card;

        public ViewHolder(ImageView image_button, CardView card)
        {
            this.image_button = image_button;
            this.card = card;
        }
    }

}










