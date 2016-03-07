package com.tanners.smartwallpaper;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import java.io.IOException;

public class PhotoActivity extends AppCompatActivity
{
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        toolbar = (Toolbar) findViewById(R.id.photo_tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Photo Information");


        Intent intent = getIntent();
        String info = intent.getStringExtra("info");
        final String url = intent.getStringExtra("url");
        ImageView image = (ImageView) findViewById(R.id.image);
        TextView text = (TextView) findViewById(R.id.text_view);
        Button wallpaper_btn = (Button) findViewById(R.id.set_wallpaper);

        wallpaper_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new setWallpaper(getApplicationContext(), url).execute();
                Toast.makeText(PhotoActivity.this, "Wallpaper Set", Toast.LENGTH_LONG).show();
            }
        });

        Glide.with(this).load(url).centerCrop().fitCenter().into(image);
        text.setText(info);


    }

    private class setWallpaper extends AsyncTask<Void, Void, Bitmap>
    {
        private Context context;
        private String url;

        public setWallpaper(Context context, String url)
        {
            this.context = context;
            this.url = url;
        }

        @Override
        protected Bitmap doInBackground(Void... position)
        {
            Bitmap bitmap = null;
            try {

                bitmap = Picasso.with(context).load(url).get();
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
            int height = metrics.heightPixels;
            int width = metrics.widthPixels;
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);

            try
            {
                wallpaperManager.setBitmap(bitmap);
                wallpaperManager.suggestDesiredDimensions(width, height);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
