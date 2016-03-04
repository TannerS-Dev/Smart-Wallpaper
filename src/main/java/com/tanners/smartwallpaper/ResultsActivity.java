package com.tanners.smartwallpaper;

import android.content.Context;
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
import com.tanners.smartwallpaper.flickrdata.photodata.FlickrPhotos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.transform.Result;

public class ResultsActivity extends AppCompatActivity {
    // key to access the intent extra (this key will return the tag)
    private final String EXTRA_MESSAGE = "TAG";
    private String tag;
    private GridLayoutManager grid;
    private RecyclerView recycle_view;
   // private List<FlickrPhotoItem> photos;

    // dynamic grid
    private boolean loading = true;

    private int per_page;
    private int page;
    private int total_pics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // load UI elements
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        //photos = new ArrayList<FlickrPhotoItem>(100);
        per_page = 500;
        page = 1;
        total_pics = 2000;

       // photos = new ArrayList<FlickrPhotoItem>();

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_tool_bar);
        // set toolbar as action bar (has back button)
        setSupportActionBar(toolbar);
        tag = getIntent().getStringExtra(EXTRA_MESSAGE);

        grid = new GridLayoutManager(this, 2);
        // LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View view = inflater.inflate(R.layout.activity_results, null, false);
        recycle_view = (RecyclerView) findViewById(R.id.recycler_view_results);
        recycle_view.setHasFixedSize(true);
        // TODO move after collect imgaes?
        recycle_view.setLayoutManager(grid);
        new CollectTaggedPhotos(recycle_view, this).execute(tag);


    }


    private class CollectTaggedPhotos extends AsyncTask<String, Void, List<FlickrPhotoItem>>
    {
        private FlickrDataPhotosSearch flickr_object;
        private RecyclerView recycler_view;
        private Context context;

        public CollectTaggedPhotos(RecyclerView recycler_view, Context context) {
            // set context
            flickr_object = new FlickrDataPhotosSearch(total_pics, per_page, page);
            this.recycler_view = recycler_view;
            this.context = context;
        }

        @Override
        protected List<FlickrPhotoItem> doInBackground(String... str) {
            // run background task to get photo oobjects based on tag

            Log.i("new", "TAGL : " + str[0]);
            return flickr_object.populateFlickrPhotos(str[0]);
        }

        @Override
        protected void onPostExecute(List<FlickrPhotoItem> result) {
            super.onPostExecute(result);


            Collections.shuffle(result);

            if (result == null || (result.size() <= 0)) {
                NoImagesToast("No Images For This Tag");
            } else {
                // set adapter passing in photo objects
                Log.i("new  ", "does it get here");
                final DisplayMetrics metrics = context.getResources().getDisplayMetrics();
                // TODO may needto change context, it was resultsactivity,this, not sure what it is now, do log
                FlickrRecycleImageAdapter adapter = new FlickrRecycleImageAdapter(context, result, metrics);
                recycler_view.setAdapter(adapter);
            }
        }

        private void NoImagesToast(String str) {
            // set up toast
            Toast toast = Toast.makeText(context, str, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM | Gravity.LEFT, 0, 0);
            toast.show();
        }
    }
}