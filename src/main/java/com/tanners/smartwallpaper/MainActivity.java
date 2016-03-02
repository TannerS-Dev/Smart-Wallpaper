package com.tanners.smartwallpaper;
// TODO placeholders for imageview and navbar

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.view.Gravity;
import android.content.Context;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

// Clarifai imports

// java imports
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TannerS
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

// picasso
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.tanners.smartwallpaper.clarifaidata.ClarifaiFragment;
import com.tanners.smartwallpaper.firebase.FireBaseUtil;
import com.tanners.smartwallpaper.flickrdata.FlickrDataTags;
import com.tanners.smartwallpaper.flickrdata.FlickrPhotoSearchFragment;
import com.tanners.smartwallpaper.flickrdata.FlickrRecentPhotosFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener
{
    // hashmap to hold nav bar list view adapters
    //private HashMap<String, ArrayAdapter<String>> tag_adapters;
    private ArrayAdapter<String> flickr_tag_adapter;
    private ArrayAdapter<String> firebase_tag_adapter;
    private final int LIST_VIEW_COUNT = 2;


    private ListView nav_bar_list_view;

    // number of menu items
    private int tag_selector;
    // number of fragments
    private final int FRAG_COUNT = 3;
    private Toolbar toolbar;
    private NavigationView navigationView;
   // private FlickrDataTags flickr_tags = null;
    private final String NO_IMAGES = "No images aviable for this tag";

    //private Toolbar tabs_tool_bar;
    private TabLayout tab_layout;

    //firebase dir
    private final String FIREBASE_HOME = "https://smartwallpaper.firebaseio.com/";
    // firebase utility class
    private FireBaseUtil fire;
    private Firebase fire_base;
    private HashMap<String, String> tags;
    //private ArrayList<String> list;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //tag_adapters = new  HashMap<String, ArrayAdapter<String>>(LIST_VIEW_COUNT);
        nav_bar_list_view = (ListView) findViewById(R.id.nav_bar_adapter);

        generateNavBar();
        //flickr_tags = new FlickrDataTags();
        // run background task to generate flickr tags for navegation bar
        new GenerateFlickrTags().execute(new FlickrDataTags());
        // run background task to generate flickr tags for navegation bar
       // new GenerateFireBaseTags(this);
        initFireBase();
        // find main tool bar and set title
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_tool_bar);
        toolbar.setTitle("test");

        tag_selector = 0;

        // set up fragment tabs
        setUpTabs();
        setUpTags();
    }

    private void initFireBase()
    {
        Firebase.setAndroidContext(this);
        fire_base = new Firebase(FIREBASE_HOME);

        fire_base.child("tags").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                tags = new HashMap<String, String>();
                Log.i("firebase", "change");
                setUpAdapter(snapshot.getValue(HashMap.class));
            }

            @Override
            public void onCancelled(FirebaseError error)
            {
                Log.i("firebase", error.toString());
            }
        });
    }

    private void setUpAdapter(HashMap<String, String> temp)
    {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.activity_main, null, false);
       // ListView listview = (ListView) view.findViewById(R.id.nav_bar_adapter);
       ArrayList<String>  list = new ArrayList<String>(tags.values());
        list.addAll(temp.values());

        Log.i("shuffle", list.toString());
        Collections.sort(list);
        Log.i("shuffle", list.toString());


        Log.i("firebase", "size: " + Integer.toString(list.size()));
       // flickr_tag_adapter
        firebase_tag_adapter = new GenericTagAdapter(getApplicationContext(), R.layout.activity_main, list);
        nav_bar_list_view.setAdapter(firebase_tag_adapter);
        Log.i("ex", "checkpoint1");
        Log.i("firebase", "list updated from firebase " + list.toString());
    }

    private void setUpTags()
    {
        // create drop down menu to choose which tags to see in nav bar
        Spinner spinner = (Spinner) findViewById(R.id.tag_spinner);
        // apply list array of choices to drop down menu (spinner)
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.tag_sources, android.R.layout.simple_spinner_item);
        // bind the list of choices
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        // set activity to listen to the adapter
        spinner.setOnItemSelectedListener(this);
        spinner.setSelection(0);
        //spinner.
    }

    private void setUpTabs()
    {
        /*
        VIew:pager Layout manager that allows the user to flip left and right through pages of data.
        You supply an implementation of a PagerAdapter to generate the pages that the view shows.
         */
        // load view pager that allows you to flip through tabs
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        // keeps tabs in memory
        viewPager.setOffscreenPageLimit(FRAG_COUNT);
        setUpFragments(viewPager);
        // find tab layout
        tab_layout = (TabLayout) findViewById(R.id.tabs);
        // The one-stop shop for setting up this TabLayout with a ViewPager. set layout with tabs
        tab_layout.setupWithViewPager(viewPager);
    }

    private void setUpFragments(ViewPager viewPager)
    {
        // Return the FragmentManager for interacting with fragments associated with this activity.
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        // add fragment objects to list with a title
        adapter.addTab(new FlickrRecentPhotosFragment(), "Recent");
        adapter.addTab(new FlickrPhotoSearchFragment(), "Search");
        adapter.addTab(new ClarifaiFragment(), "Clarifai");
        // set adapter with layout
        viewPager.setAdapter(adapter);
    }

    private void generateNavBar()
    {
        // AUTO GENERATED
        toolbar = (Toolbar) findViewById(R.id.main_tool_bar);
       setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed()
    {
        // AUTO GENERATED
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // AUTO GENERATED
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // AUTO GENERATED
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // AUTO GENERATED
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        switch(tag_selector)
        {
            case 0:
                // set adapter
                nav_bar_list_view.setAdapter(firebase_tag_adapter);
                //listview.setAdapter((ArrayAdapter) tag_adapters.get(NAV_BAR_ADAPTER_FIREBASE));
                Log.i("firebase", "list updated from menu");
                break;
            case 1:
                //set adapter

                nav_bar_list_view.setAdapter(flickr_tag_adapter);
                Log.i("firebase", "other list updated from menu");
                break;
        }
        tag_selector = (tag_selector + 1) % LIST_VIEW_COUNT;

    }

    //@Override
    public void onNothingSelected(AdapterView<?> parent)
    {
        //do nothing
    }

    private class GenerateFlickrTags extends AsyncTask<FlickrDataTags, Void, List<String>>
    {
        @Override
        protected List<String> doInBackground(FlickrDataTags... flickr_tags)
        {
            // call object to get list of trending tags
            flickr_tags[0] = new FlickrDataTags();
            return flickr_tags[0].getTagsHotList();
        }

        @Override
        protected void onPostExecute(List<String> result)
        {
            super.onPostExecute(result);

            // check if there were possible results or not
            if(result == null || (result.size() == 0))
                // display toast
                noImagesToast(NO_IMAGES);
            else
            {
                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = layoutInflater.inflate(R.layout.activity_main, null, false);
                // load listview
                Log.i("ex" , "checkpoint");
               // nav_bar_list_view = (ListView) view.findViewById(R.id.nav_bar_adapter);

                flickr_tag_adapter = new GenericTagAdapter(getApplicationContext(), R.layout.activity_main, result);


                Log.i("ex" , "checkpoint2");
            }
        }

        private void noImagesToast(String str)
        {
            // set and load toast
            Toast toast = Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM | Gravity.LEFT, 0, 0);
            toast.show();
        }
    }

    class FragmentAdapter extends FragmentPagerAdapter
    {
        // list of fragments and titles
        List<Fragment> frags;
        List<String> titles;

        public FragmentAdapter(FragmentManager manager)
        {
            // init
            super(manager);
            frags = new ArrayList<Fragment>();
            titles = new ArrayList<String>();
        }

        @Override
        public int getCount()
        {
            // return size
            return frags.size();
        }

        @Override
        public Fragment getItem(int position)
        {
           return frags.get(position);
        }

        public void addTab(Fragment frag, String title)
        {
            frags.add(frag);
            titles.add(title);
        }

        public void addTab(Fragment frag)
        {
            frags.add(frag);
        }

        // idk yet
        @Override
        public CharSequence getPageTitle(int position)
        {
            // tab titles
            return titles.get(position);
        }

    }

}
