package com.tanners.smartwallpaper;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.widget.GridView;
import android.widget.Toast;

import com.tanners.smartwallpaper.flickrdata.FlickrDataPhotos;
import com.tanners.smartwallpaper.flickrdata.FlickrImageAdapter;
import com.tanners.smartwallpaper.flickrdata.FlickrTagAdapter;
import com.tanners.smartwallpaper.flickrdata.photodata.FlickrPhotoContainer;
import com.tanners.smartwallpaper.flickrdata.photodata.FlickrPhotoItem;

import java.util.List;

public class ResultsActivity extends AppCompatActivity
{
    public final static String EXTRA_MESSAGE = "TAG";
    private String tag;
    private GridView grid_view;
    private final int PHOTOS_PER_PAGE = 55;
    private int CURRENT_PAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tag = getIntent().getStringExtra(EXTRA_MESSAGE);
        grid_view = (GridView) findViewById(R.id.grid_view);

        // TODO take care of number of pages
        // TODO dynamically grosw as scrolling
        new CollectPhotos( PHOTOS_PER_PAGE,CURRENT_PAGE).execute(tag);
    }

    private class CollectPhotos extends AsyncTask<String, Void, FlickrPhotoContainer>
    {
        private FlickrDataPhotos photos = new FlickrDataPhotos();
        private int per_page;
        private int current_page;

        public CollectPhotos(int per_page, int current_page)
        {
            this.per_page = per_page;
            this.current_page = current_page;
        }

        @Override
        protected FlickrPhotoContainer doInBackground(String... str)
        {
            return photos.populateFlickrPhotos(str[0], current_page, per_page);
        }

        @Override
        protected void onPostExecute(FlickrPhotoContainer result)
        {
            super.onPostExecute(result);
            GridView grid = (GridView) findViewById(R.id.grid_view);
            List<FlickrPhotoItem> flickr_objects = result.getPhotos().getPhoto();

            //if(flickr_objects == null || (flickr_objects.size() == 0))
            //{
                //TODO cnstant
              //  NoImagesToast("No Images For This Tag");
                //finish();
            //}
            //else
            //{
                FlickrImageAdapter adapter = new FlickrImageAdapter(ResultsActivity.this, R.layout.content_results, flickr_objects);
                grid.setAdapter(adapter);
            //}
        }

        private void NoImagesToast(String str)
        {
            // getApplicationContext() | Return the context of the single, global Application object of the current process.
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, str, Toast.LENGTH_LONG);
            // public void setGravity (int gravity, int xOffset, int yOffset)
            toast.setGravity(Gravity.BOTTOM | Gravity.LEFT, 0, 0);
            toast.show();
        }
    }
}
