package com.tanners.smartwallpaper;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.tanners.smartwallpaper.flickrdata.FlickrDataPhotosSearch;
import com.tanners.smartwallpaper.flickrdata.FlickrRecycleImageAdapter;
import com.tanners.smartwallpaper.flickrdata.photodata.FlickrPhotoContainer;
import com.tanners.smartwallpaper.flickrdata.photodata.FlickrPhotoItem;

import java.util.List;

public class ResultsActivity extends AppCompatActivity
{
    // key to access the intent extra (this key will return the tag)
    private final String EXTRA_MESSAGE = "TAG";
    private String tag;
    private GridLayoutManager grid;
    private RecyclerView recycle_view;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // load UI elements
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_tool_bar);
        // set toolbar as action bar (has back button)
        setSupportActionBar(toolbar);
        tag = getIntent().getStringExtra(EXTRA_MESSAGE);

        grid = new GridLayoutManager(this, 2);
       // LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View view = inflater.inflate(R.layout.activity_results, null, false);
       recycle_view = (RecyclerView) findViewById(R.id.recycler_view_results);
        recycle_view.setHasFixedSize(true);
       recycle_view.setLayoutManager(grid);


        // generate images based on tag
        new CollectTaggedPhotos().execute(tag);
    }

    public class CollectTaggedPhotos extends AsyncTask<String, Void, FlickrPhotoContainer>
    {
        private FlickrDataPhotosSearch photos;

        public CollectTaggedPhotos()
        {
            // set context
            photos = new FlickrDataPhotosSearch();
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

            //recycle_view = (RecyclerView) view.findViewById(R.id.recycler_view);
           // grid_view = (GridView) findViewById(R.id.grid_view);
            // inflate fragment layout
           // LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           // View view = layoutInflater.inflate(R.layout.activity_results, null, false);

            //GridView grid = (GridView) view.findViewById(R.id.grid_view);
            // get list of photo objects
            List<FlickrPhotoItem> flickr_objects = result.getPhotos().getPhoto();
            // check if any results were returned
            Log.i("debug", "checkpoint : " + flickr_objects.size());
            if(flickr_objects == null || (flickr_objects.size() == 0))
            {
                NoImagesToast("No Images For This Tag");
            }
            else
            {
                // set adapter passing in photo objects
                Log.i("debug", ResultsActivity.this.toString());
                final DisplayMetrics metrics = getResources().getDisplayMetrics();

                FlickrRecycleImageAdapter adapter = new FlickrRecycleImageAdapter(ResultsActivity.this, flickr_objects, metrics);
                recycle_view.setAdapter(adapter);
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
