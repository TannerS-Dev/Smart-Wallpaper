package com.tanners.smartwallpaper;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.GridView;

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

        //CollectPhotos photos = new CollectPhotos();
        new CollectPhotos( PHOTOS_PER_PAGE,CURRENT_PAGE).execute(tag);



       // FlickrImageAdapter imageAdapter = new FlickrImageAdapter(MainActivity.this, response.body().getPhotos().getPhoto());
        //gridView.setAdapter(imageAdapter);

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

            if(result == null)
             Log.i("pic", "result is null");

            //if(result == null)
              //  Log.i("pic", "result is null");

            List<FlickrPhotoItem> flickr_objects = result.getPhotos().getPhoto();

           FlickrImageAdapter adapter = new FlickrImageAdapter(ResultsActivity.this, R.layout.content_results, flickr_objects);

           grid.setAdapter(adapter);
        }
    }


}
