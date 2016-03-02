package com.tanners.smartwallpaper.flickrdata;

import android.app.WallpaperManager;
import android.content.Context;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;

import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;
import com.tanners.smartwallpaper.PhotoActivity;
import com.tanners.smartwallpaper.R;

import com.tanners.smartwallpaper.ResultsActivity;
import com.tanners.smartwallpaper.flickrdata.photodata.FlickrPhotoContainer;
import com.tanners.smartwallpaper.flickrdata.photodata.FlickrPhotoItem;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FlickrImageAdapter extends BaseAdapter
{
    Context context;
    List<FlickrPhotoItem> photos;

    FlickrDataUserInfo user_data;
    DisplayMetrics metrics;


    public FlickrImageAdapter(Context context, int resource, List<FlickrPhotoItem> photos, DisplayMetrics metrics)
    {
        super();
        this.context = context;
        this.photos = photos;
        this.metrics = metrics;
        Collections.shuffle(this.photos);
      //  popUp = new PopupWindow(this.context);
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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {

        final ImageButton image_button;
        //CardView card;

        if (convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.flickr_grid_image_layout, parent, false);
            image_button = (ImageButton) convertView.findViewById(R.id.image_button);
            //card = (CardView) convertView.findViewById(R.id.grid_image);
            int screen_width = metrics.widthPixels;
            image_button.setLayoutParams(new RelativeLayout.LayoutParams(screen_width / 2, screen_width / 2));
//image_button.smoothScrollToPosition


            Log.i("image", Integer.toString(image_button.getWidth()) + " " + Integer.toString(image_button.getHeight()));
            //card.setLayoutParams(new RelativeLayout.LayoutParams(screen_width / 2, screen_width / 2));
            //card.setClickable(true);
           // convertView.setTag(new ImageViewHolder(image_button, card));




            convertView.setTag(new ImageViewHolder(image_button));
        }
        else
        {
            ImageViewHolder viewHolder = (ImageViewHolder) convertView.getTag();
            image_button = viewHolder.image_button;
           // card = viewHolder.card;
        }


        image_button.setOnClickListener(new CardView.OnClickListener()
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












// TODO volley

        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        imgNetWorkView.setImageUrl(photos.get(position).getUrl_z(), imageLoader);

        /*
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();

// If you are using normal ImageView
        imageLoader.get(Const.URL_IMAGE, new ImageListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Image Load Error: " + error.getMessage());
            }

            @Override
            public void onResponse(ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {
                    // load image into imageview
                    imageView.setImageBitmap(response.getBitmap());
                }
            }
        });

*/






       //Picasso.with(context).load(photos.get(position).getUrl_z()).fit().into(image_button);
        // return current view
        return convertView;
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


















    private static class ImageViewHolder
    {
        private final ImageButton image_button;

        //private final CardView card;


        public ImageViewHolder(ImageButton image_button)
//        public ImageViewHolder(ImageButton image_button, CardView card)
        {
            this.image_button = image_button;

            //this.card = card;
        }


    }

}










