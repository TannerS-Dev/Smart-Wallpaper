package com.tanners.smartwallpaper.flickrdata;

import android.app.WallpaperManager;
import android.content.Context;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
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
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.clarifai.api.RecognitionResult;
import com.squareup.picasso.Picasso;
import com.tanners.smartwallpaper.MainActivity;
import com.tanners.smartwallpaper.R;

import com.tanners.smartwallpaper.ResultsActivity;
import com.tanners.smartwallpaper.clarifaidata.ClarifaiTagAdapter;
import com.tanners.smartwallpaper.flickrdata.photodata.FlickrPhotoItem;

import java.io.IOException;
import java.util.List;

//// TODO: 2/16/2016
// https://www.youtube.com/watch?v=fn5OlqQuOCk   pixel dm stuff

public class FlickrImageAdapter extends BaseAdapter
{
    Context context;
    List<FlickrPhotoItem> photos;
    PopupWindow popUp;
    ImageButton image_button;
    FlickrDataUserInfo user_data;

    public FlickrImageAdapter(Context context, int resource, List<FlickrPhotoItem> photos)
    {
        super();
        this.context = context;
        this.photos = photos;
        popUp = new PopupWindow(this.context);
        user_data = new FlickrDataUserInfo();
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

    // TODO optimization on this method
    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        boolean click = true;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.grid_item, parent, false);
        image_button = (ImageButton) view.findViewById(R.id.grid_image);




        final DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int screenWidth = metrics.widthPixels;
        image_button.setLayoutParams(new RelativeLayout.LayoutParams(screenWidth / 2, screenWidth / 2));

        final LayoutInflater newlayoutInflater = layoutInflater;
        final int finalPosition = position;

        image_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // create view to hold content for popup window
                final View popup_view = newlayoutInflater.inflate(R.layout.picture_view, null);
                // grab elements for the view that will be in the popup window
                ImageView image_view = (ImageView) popup_view.findViewById(R.id.background_image);
                Button set_background_btn = (Button) popup_view.findViewById(R.id.background_btn);
                TextView text_view = (TextView) popup_view.findViewById(R.id.background_text);
                LinearLayout main = (LinearLayout) popup_view.findViewById(R.id.background_container);
                // create string to hold image information
                String photo_info = null;
                // call object's generateUserInfo to collect the user's name based on ID
                user_data.generateUserInfo(photos.get(finalPosition).getOwner());
                // call proper classes / methods to collect info for photo
                // and append it to the string




                set_background_btn.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        new setWallpaper().execute(finalPosition);


                    }
                });


                /*
                photo_info += "Name: " + user_data.getFullName() + "\n";
                photo_info += "Username: " + user_data.getUsername() + "\n";
                photo_info += "ID: " + photos.get(finalPosition).getId() + "\n";
                photo_info += "Title: " + photos.get(finalPosition).getTitle() + "\n";
                photo_info += "Owner: " + photos.get(finalPosition).getOwner() + "\n";
                photo_info += "Height: " + photos.get(finalPosition).getHeight_z() + "\n";
                photo_info += "Width: " + photos.get(finalPosition).getWidth_z() + "\n";
                // set the textview in the popup window to show the info
                text_view.setText(photo_info);
                */
                //Log.i("info", photo_info);
                // put image into popup window
                Picasso.with(context).load(photos.get(finalPosition).getUrl_z()).into(image_view);
                // get width and height of screen
                int screen_width = metrics.widthPixels;
                int screen_hight = metrics.heightPixels;
                // create popup window
                final PopupWindow popup_window = new PopupWindow(popup_view, (int)(screen_width *.9) , (int)(screen_hight * .7));
                // TODO use constant ints for width height
                // set popup window properties
                popup_window.setOutsideTouchable(true);
                popup_window.setTouchable(true);
                popup_window.setBackgroundDrawable(new ColorDrawable());
                popup_window.showAtLocation(v, Gravity.CENTER, 0, 0);
                //set click listener when layout holding the popup window is clicked
                main.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        // dismiss popup window
                        popup_window.dismiss();
                    }
                });
            }
        });
        // get tag for image button
        final String tag = this.photos.get(position).getUrl_z();
        // put image into grid
        Picasso.with(context).load(photos.get(position).getUrl_z()).fit().into(image_button);
        // return current view
        return view;
    }






    private class setWallpaper extends AsyncTask<Integer, Void, Bitmap>
    {
        @Override
        protected Bitmap doInBackground(Integer... position)
        {
            // get the Image to as Bitmap
            Bitmap bitmap = null;
            try {
                bitmap = Picasso.with(context).load(photos.get(position[0]).getUrl_z()).get();
            } catch (IOException e) {
                e.printStackTrace();

            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap)
        {
            super.onPostExecute(bitmap);


            final DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            int screenWidth = metrics.widthPixels;

            // get the height and width of screen
            int height = metrics.heightPixels;
            int width = metrics.widthPixels;

            WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);

            try {
                wallpaperManager.setBitmap(bitmap);

                wallpaperManager.suggestDesiredDimensions(width, height);
                //Toast.makeText(this, "Wallpaper Set", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}










