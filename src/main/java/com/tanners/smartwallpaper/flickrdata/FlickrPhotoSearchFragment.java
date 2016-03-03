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

import java.util.List;

public class FlickrPhotoSearchFragment extends Fragment
{
    // http://stackoverflow.com/questions/28929637/difference-and-uses-of-oncreate-oncreateview-and-onactivitycreated-in-fra

    private Context context;// = getActivity().getApplicationContext();
    private GridLayoutManager grid;
    private RecyclerView recycle_view;
    private View view;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        context = getActivity().getApplicationContext();
        //handleIntent(getIntent());

        grid = new GridLayoutManager(context, 2);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.flickr_fragment_search, null, false);
        recycle_view = (RecyclerView) view.findViewById(R.id.recycler_view_search);
        recycle_view.setHasFixedSize(true);
        recycle_view.setLayoutManager(grid);
        ///  FlickrRecycleImageAdapter rcAdapter = new FlickrRecycleImageAdapter(context, rowListItem);
        // rView.setAdapter(rcAdapter);
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
                        Intent intent = new Intent(context, ResultsActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        // math up to TAG in results page, i mean you have two osame vlaues, put them into class?

                        Log.i("new", tag);
                        intent.putExtra("TAG", tag);
                        startActivity(intent);
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











}
