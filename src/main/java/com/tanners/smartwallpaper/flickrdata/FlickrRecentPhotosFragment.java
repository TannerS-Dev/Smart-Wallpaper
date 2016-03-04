package com.tanners.smartwallpaper.flickrdata;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.tanners.smartwallpaper.R;
import com.tanners.smartwallpaper.ResultsActivity;
import com.tanners.smartwallpaper.flickrdata.photodata.FlickrPhotoContainer;
import com.tanners.smartwallpaper.flickrdata.photodata.FlickrPhotoItem;

import java.util.List;

public class FlickrRecentPhotosFragment extends Fragment
{
    private Context context;
    private View view;
    //GridView grid;
    private GridLayoutManager grid;
    private RecyclerView recycle_view;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // http://tips.androidhive.info/2013/10/android-getting-application-context-in-fragments/
       // context = getContext();
        context = getActivity().getApplicationContext();
        // get recent photos in a background task


        new CollectRecentPhotos().execute();


        grid = new GridLayoutManager(context, 2);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.flickr_fragment_recent, null, false);
        recycle_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        recycle_view.setHasFixedSize(true);
        recycle_view.setLayoutManager(grid);


      ///  FlickrRecycleImageAdapter rcAdapter = new FlickrRecycleImageAdapter(context, rowListItem);
       // rView.setAdapter(rcAdapter);

        Log.i("fuck", " here 1");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
       //view = inflater.inflate(R.layout.flickr_fragment_recent, container, false);
      //  grid = (GridView) view.findViewById(R.id.fragment_grid_view);
        return view;
    }

    public class CollectRecentPhotos extends AsyncTask<Void, Void, FlickrPhotoContainer>
    {
        // create
        private FlickrDataPhotosRecent photos;
        //private Context context;

        public CollectRecentPhotos()
        {
            photos = new FlickrDataPhotosRecent();
            Log.i("test","this shoul be called once");
        }

        @Override
        protected FlickrPhotoContainer doInBackground(Void... v)
        {
            return photos.populateFlickrPhotos();
        }

        @Override
        protected void onPostExecute(FlickrPhotoContainer result)
        {
            super.onPostExecute(result);
            // inflate fragment layout
            Log.i("test", "done gathering photos");
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.flickr_grid_image_layout, null, false);
           // GridView grid = (GridView) view.findViewById(R.id.grid_view);
            // get list of photo objects
            if(result != null)
            {

                List<FlickrPhotoItem> flickr_objects = result.getPhotos().getPhoto();

                Log.i("test", "size og photos: " +Integer.toString(flickr_objects.size()));
                // check if any results were returned
                if (flickr_objects == null || (flickr_objects.size() == 0))
                {
                    NoImagesToast("No Images For This Tag");
                }
                else
                {

// blic FlickrRecycleImageAdapter(Context context, int resource, List<FlickrPhotoItem> photos, DisplayMetrics metrics)
                    final DisplayMetrics metrics = context.getResources().getDisplayMetrics();

                    //Log.i("err", flickr_objects.get(0).getUrl_z());
                    FlickrRecycleImageAdapter adapter = new FlickrRecycleImageAdapter(context, flickr_objects, metrics);
                    recycle_view.setAdapter(adapter);
                }
            }
            else
                NoImagesToast("No Images For This Tag");
        }

        private void NoImagesToast(String str)
        {
            // set up toast
            Toast toast = Toast.makeText(context, str, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM | Gravity.LEFT, 0, 0);
            toast.show();
        }
    }

}
