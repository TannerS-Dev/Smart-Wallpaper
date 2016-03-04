package com.tanners.smartwallpaper.flickrdata;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.tanners.smartwallpaper.R;
import com.tanners.smartwallpaper.ResultsActivity;
import com.tanners.smartwallpaper.flickrdata.photodata.FlickrPhotoContainer;
import com.tanners.smartwallpaper.flickrdata.photodata.FlickrPhotoItem;

import java.util.Collections;
import java.util.List;

public class FlickrPhotoSearchFragment extends Fragment
{
    // http://stackoverflow.com/questions/28929637/difference-and-uses-of-oncreate-oncreateview-and-onactivitycreated-in-fra

    private Context context;// = getActivity().getApplicationContext();
    private GridLayoutManager grid;
    private RecyclerView recycle_view;
    private View view;

    private FlickrRecycleImageAdapter adapter;


// TODO uses same class, and these vars for results tag and ehre
    private int per_page;
    private int page;
    private int total_pics;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        context = getActivity().getApplicationContext();
        //handleIntent(getIntent());
        per_page = 500;
        page = 1;
        total_pics = 2000;

       // String tag = null;

        //if(savedInstanceState != null)
          //  tag = savedInstanceState.getString(EXTRA_MESSAGE);

       // String tag = getArguments().getString(EXTRA_MESSAGE);

       // Log.i("tag", "is tag null?: " + tag );
       // return inflater.inflate(R.layout.fragment, container, false);



        grid = new GridLayoutManager(context, 2);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // TODO this needed if this pages inflates view?
        view = inflater.inflate(R.layout.flickr_fragment_search, null, false);
        recycle_view = (RecyclerView) view.findViewById(R.id.recycler_view_search);
        recycle_view.setHasFixedSize(true);
        recycle_view.setLayoutManager(grid);
        //FlickrRecycleImageAdapter rcAdapter = new FlickrRecycleImageAdapter(context, rowListItem);
        //rView.setAdapter(rcAdapter);

    }

    // http://stackoverflow.com/questions/15653737/oncreateoptionsmenu-inside-fragments

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.search_bar, menu);

        final MenuItem search_bar = menu.findItem(R.id.menu_search);
        // Associate searchable configuration with the search_view
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(getActivity().SEARCH_SERVICE);
        final SearchView search_view = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        search_view.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        search_view.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            // http://stackoverflow.com/questions/23928253/how-to-dismiss-close-collapse-searchview-in-actionbar-in-mainactivity
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                // a fix to get these settings to work...

                final String tag = search_view.getQuery().toString();
                new Runnable()
                {
                    @Override
                    public void run()
                    {
                        MenuItemCompat.collapseActionView(search_bar);
                        search_bar.collapseActionView();
                        search_view.clearFocus();
                        search_view.setQuery("", false);
                        search_view.setFocusable(false);
                        // generate images based on tag
                        //new CollectTaggedPhotos().execute(tag);
                        // PASS TO RESULTS PAGE
                        // TODO SEARCH FRAGMENT -> RESULTSACTIVITY
                       // Intent intent = new Intent(context, ResultsActivity.class);
                        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        // math up to TAG in results page, i mean you have two osame vlaues, put them into class?

                        Log.i("new", tag);
                        // TODO backup..
                       // intent.putExtra("TAG", tag);
                       // startActivity(intent);

                        /*
                        final Intent intent = new Intent(context, ResultsActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra(EXTRA_MESSAGE, tag);
                        context.startActivity(intent);
                        */

                        new CollectTaggedPhotos(recycle_view, context).execute(tag);

                        final DisplayMetrics metrics = context.getResources().getDisplayMetrics();
                        //FlickrRecycleImageAdapter adapter = new FlickrRecycleImageAdapter(context, photos, metrics);
                        //recycle_view.setAdapter(adapter);
                    }
                }.run();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return view;
    }

    public void searchByTag(String tag)
    {
        Log.i("tag", "Test Tag 2: " + tag);
        if(adapter != null)
            adapter.clear();


        new CollectTaggedPhotos(recycle_view, context).execute(tag);
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
            Log.i("tag", "Test Tag 1.0: " );

        }

        @Override
        protected List<FlickrPhotoItem> doInBackground(String... str) {
            // run background task to get photo oobjects based on tag
            Log.i("tag", "Test Tag 1.1: ");

            Log.i("new", "TAGL : " + str[0]);
            return flickr_object.populateFlickrPhotos(str[0]);
        }

        @Override
        protected void onPostExecute(List<FlickrPhotoItem> result)
        {
            super.onPostExecute(result);
            Collections.shuffle(result);

            Log.i("tag", "Test Tag 3");


            if (result == null || (result.size() <= 0)) {
                NoImagesToast("No Images For This Tag");
            } else {
                // set adapter passing in photo objects
                Log.i("new  ", "does it get here");
                final DisplayMetrics metrics = context.getResources().getDisplayMetrics();
                // TODO may needto change context, it was resultsactivity,this, not sure what it is now, do log
                adapter = new FlickrRecycleImageAdapter(context, result, metrics);
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
