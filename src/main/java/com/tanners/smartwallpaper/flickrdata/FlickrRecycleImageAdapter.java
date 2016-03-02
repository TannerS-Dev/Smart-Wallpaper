


package com.tanners.smartwallpaper.flickrdata;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;
import com.tanners.smartwallpaper.PhotoActivity;
import com.tanners.smartwallpaper.R;
import com.tanners.smartwallpaper.flickrdata.photodata.FlickrPhotoItem;

import java.util.Collections;
import java.util.List;

public class FlickrRecycleImageAdapter extends RecyclerView.Adapter<FlickrRecycleImageAdapter.ImageViewHolder>
{
    Context context;
    List<FlickrPhotoItem> photos;
    FlickrDataUserInfo user_data;
    DisplayMetrics metrics;

    public FlickrRecycleImageAdapter(Context context, List<FlickrPhotoItem> photos, DisplayMetrics metrics)
    {
        super();
        this.context = context;
        this.photos = photos;
        this.metrics = metrics;
        // TODO uncomments
        //Collections.shuffle(this.photos);
        user_data = new FlickrDataUserInfo();
    }


    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        final DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.flickr_grid_image_layout, null);
        ImageViewHolder holder = new ImageViewHolder(view, metrics);
        return holder;

    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, final int position)
    {
        holder.image_button.setOnClickListener(new CardView.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final Intent intent = new Intent(context, PhotoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                StringBuilder photo_info = new StringBuilder("");
                FlickrPhotoItem data = photos.get(position);

                Log.i("person", data.getOwner());

                //user_data.generateUserInfo();


                new GetUserInfo().execute(data.getOwner());

                photo_info.append("Name: " + user_data.getFullName() + "\n");
                photo_info.append("Username: " + user_data.getUsername() + "\n");
                photo_info.append("ID: " + data.getId() + "\n");
                photo_info.append("Title: " + data.getTitle() + "\n");
                photo_info.append("Owner: " + data.getOwner() + "\n");
                photo_info.append("Height: " + data.getHeight_z() + "\n");
                photo_info.append("Width: " + data.getWidth_z() + "\n");
                intent.putExtra("info", photo_info.toString());
                intent.putExtra("position", position);
                intent.putExtra("url", data.getUrl_z());
                context.startActivity(intent);
            }
        });



        // get tag for image button
        // final String tag = this.photos.get(position).getUrl_t();
        // put image into grid
        int w = metrics.widthPixels;
        int h = metrics.heightPixels;

        //final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        //final int cacheSize = maxMemory / 4;

        //  Picasso p = new Picasso.Builder(context)
        //        .memoryCache(new LruCache(cacheSize))
        //      .build();

        Picasso.with(context).load(photos.get(position).getUrl_z()).fit().into(holder.image_button);
        // return current view
    }

    @Override
    public int getItemCount() {
        return this.photos.size();
    }


    public static class ImageViewHolder extends RecyclerView.ViewHolder
    {
        private final ImageButton image_button;

        public ImageViewHolder(View view, DisplayMetrics metrics)
        {
            super(view);
            image_button = (ImageButton) view.findViewById(R.id.image_button);
           // final DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            int screen_width = metrics.widthPixels;
            image_button.setLayoutParams(new RelativeLayout.LayoutParams(screen_width / 2, screen_width / 2));
            //this.image_button = image_button;
        }
    }









    public class GetUserInfo extends AsyncTask<String, Void, FlickrDataUserInfo>
    {
        @Override
        protected FlickrDataUserInfo doInBackground(String... str)
        {
            user_data.generateUserInfo(str[0]);

            return user_data;
        }
    }


}

