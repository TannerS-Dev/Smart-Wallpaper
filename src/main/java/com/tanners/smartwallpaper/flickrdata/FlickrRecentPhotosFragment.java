package com.tanners.smartwallpaper.flickrdata;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.tanners.smartwallpaper.R;
import com.tanners.smartwallpaper.flickrdata.photodata.FlickrPhotoContainer;
import com.tanners.smartwallpaper.flickrdata.photodata.FlickrPhotoItem;

import java.util.List;

public class FlickrRecentPhotosFragment extends Fragment
{
    private Context context;
    private View view;
    GridView grid;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // http://tips.androidhive.info/2013/10/android-getting-application-context-in-fragments/
       // context = getContext();
        context = getActivity().getApplicationContext();
        Log.i("debug", context.toString());
        // get recent photos in a background task
       // new CollectRecentPhotos(context).execute();
        new CollectRecentPhotos().execute();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.flickr_fragment_recent, container, false);
        grid = (GridView) view.findViewById(R.id.fragment_grid_view);
        return view;
    }

    public class CollectRecentPhotos extends AsyncTask<Void, Void, FlickrPhotoContainer>
    {
        // create
        private FlickrDataPhotosRecent photos;
        //private Context context;

        public CollectRecentPhotos()
        {
           // context = con;
            photos = new FlickrDataPhotosRecent();
            Log.i("test","this shoul be called once");
        }

        @Override
        protected FlickrPhotoContainer doInBackground(Void... v)
        {
            // get flickr object with array of photo url's
            Log.i("test", "start");
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
                // TODO check this  and other collect class to see if i need both of these
                if (flickr_objects == null || (flickr_objects.size() == 0))
                {
                    //TODO constant and make activiy ends?
                    NoImagesToast("No Images For This Tag");
                } else {
                    if (grid == null)
                        Log.i("debug", "grid is null");
                    if (context == null)
                        Log.i("debug", "context is null");


                    // set adapter passing in photo objects
                    grid.setAdapter(new FlickrImageAdapter(context, R.layout.activity_results, flickr_objects));
                }
            }
            else
                //TODO constant and make activiy ends?
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
