package com.tanners.smartwallpaper;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import com.tanners.smartwallpaper.flickrdata.FlickrDataPhotos;
import com.tanners.smartwallpaper.flickrdata.FlickrImageAdapter;
import com.tanners.smartwallpaper.flickrdata.photodata.FlickrPhotoContainer;
import com.tanners.smartwallpaper.flickrdata.photodata.FlickrPhotoItem;

import java.util.List;

import javax.xml.transform.Result;

public class ResultsActivity extends AppCompatActivity
{
    // key to access the intent extra (this key will return the tag)
    private final String EXTRA_MESSAGE = "TAG";
    private String tag;
    private GridView grid_view;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // load UI elements
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_tool_bar);
        // set toolbar as action bar (has back button)
        setSupportActionBar(toolbar);

        // TODO insteado f creaitng new nav, call one form main but change title
        tag = getIntent().getStringExtra(EXTRA_MESSAGE);


        // generate images based on tag
        new CollectTaggedPhotos().execute(tag);
    }

    public class CollectTaggedPhotos extends AsyncTask<String, Void, FlickrPhotoContainer>
    {
        private FlickrDataPhotos photos;

        public CollectTaggedPhotos()
        {
            // set context
            photos = new FlickrDataPhotos();
        }

        @Override
        protected FlickrPhotoContainer doInBackground(String... str)
        {
            // run background task to get photo oobjects based on tag
            return photos.populateFlickrPhotos(str[0]);
        }

        @Override
        protected void onPostExecute(FlickrPhotoContainer result)
        {


            super.onPostExecute(result);


            grid_view = (GridView) findViewById(R.id.grid_view);
            // inflate fragment layout
           // LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           // View view = layoutInflater.inflate(R.layout.activity_results, null, false);
            Log.i("debug", "checkpoint");
            //GridView grid = (GridView) view.findViewById(R.id.grid_view);
            // get list of photo objects
            List<FlickrPhotoItem> flickr_objects = result.getPhotos().getPhoto();
            // check if any results were returned
            // TODO check this  and other collect class to see if i need both of these



            if(flickr_objects == null || (flickr_objects.size() == 0))
            {
                //TODO cnstant & make activity end?
                NoImagesToast("No Images For This Tag");
            }
            else
            {
                // set adapter passing in photo objects
                Log.i("debug", ResultsActivity.this.toString());
                grid_view.setAdapter(new FlickrImageAdapter(ResultsActivity.this, R.layout.activity_results, flickr_objects));
            }
        }

        private void NoImagesToast(String str)
        {
            // set up toast
            Toast toast = Toast.makeText(ResultsActivity.this, str, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM | Gravity.LEFT, 0, 0);
            toast.show();
        }
    }
}
