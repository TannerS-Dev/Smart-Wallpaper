package com.tanners.smartwallpaper;
// TODO placeholders for imageview and navbar
// TODO fix nav bar lfick not loading

import android.app.TabActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.content.Context;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TabHost;

// Clarifai imports

// java imports
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

// TannerS
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

// picasso
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.tanners.smartwallpaper.clarifaidata.ClarifaiFragment;
import com.tanners.smartwallpaper.firebase.FireBaseUtil;
import com.tanners.smartwallpaper.flickrdata.FlickrDataPhotosSearch;
import com.tanners.smartwallpaper.flickrdata.FlickrPhotoSearchFragment;
import com.tanners.smartwallpaper.flickrdata.FlickrRecentPhotosFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    // hashmap to hold nav bar list view adapters
    //private HashMap<String, ArrayAdapter<String>> tag_adapters;
    private ArrayAdapter<String> firebase_tag_adapter;

    private ListView nav_bar_list_view;
    // number of menu items
    private int tag_selector;
    // number of fragments
    private final int FRAG_COUNT = 3;
    private Toolbar toolbar;
    private NavigationView navigationView;
   // private FlickrDataTags flickr_tags = null;
   // private final String NO_IMAGES = "No images aviable for this tag";
    private DrawerLayout drawer;

    //private Toolbar tabs_tool_bar;
    private TabLayout tab_layout;

    //firebase dir
    private final String FIREBASE_HOME = "https://smartwallpaper.firebaseio.com/";
    // firebase utility class
    private FireBaseUtil fire;
    private Firebase fire_base;
    private HashMap<String, String> tags;
    //private ArrayList<String> list;

    private FlickrViewHolder view_holder;

    private  ViewPager viewPager;

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
       // TODO UNCOMMENT
        // run background task to generate flickr tags for navegation bar
       // new GenerateFireBaseTags(this);
        // TODO UNCOMMENT
        initFireBase();
        // find main tool bar and set title
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_tool_bar);
        toolbar.setTitle("test");

        tag_selector = 0;

        // set up fragment tabs
        setUpTabs();


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
        Collections.sort(list);
       // flickr_tag_adapter
        //TODO tags
        firebase_tag_adapter = new GenericTagAdapter(getApplicationContext(), R.layout.activity_main, list);
        nav_bar_list_view.setAdapter(firebase_tag_adapter);
    }

    private void setUpTabs()
    {
        /*
        VIew:pager Layout manager that allows the user to flip left and right through pages of data.
        You supply an implementation of a PagerAdapter to generate the pages that the view shows.
         */
        // load view pager that allows you to flip through tabs
        viewPager = (ViewPager) findViewById(R.id.view_pager);
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
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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



































    public class GenericTagAdapter extends ArrayAdapter<String>
    {
        private Context context;
        private List<String> taglist;
        private final String EXTRA_MESSAGE = "TAG";

        public GenericTagAdapter(Context context, int resource, List<String> objects)
        {

            super(context, resource, objects);
            this.context = context;
            this.taglist = objects;
            //notifyDataSetChanged();
        }

        @Override
        public int getCount()
        {
            return taglist.size();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent)
        {
            // create custom view holder class to hold views
            //private FlickrViewHolder view_holder;
            //notifyDataSetChanged();

            if(convertView == null)
            {
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.flickr_menu_tags_layout, parent, false);
                view_holder = new FlickrViewHolder();
                view_holder.btn =  (Button) convertView.findViewById(R.id.flickr_tag_button);
                convertView.setTag(view_holder);

            }
            else
            {
                // no need to call findViewById() on resource again
                // just use the custom view holder
                view_holder = (FlickrViewHolder) convertView.getTag();
            }

            final String tag = this.taglist.get(position);
            Log.i("tag1", "TAG :  " + tag);
            view_holder.btn.setText(tag);

            view_holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    viewPager.setCurrentItem(1);


                    drawer.closeDrawer(GravityCompat.START);

                    // http://stackoverflow.com/questions/12739909/send-data-from-activity-to-fragment-in-android

                    List<Fragment> fragments = getFragments();

                    int count = 0;


                    for (Fragment f : fragments)
                    {
                        if (f.getClass().equals(FlickrPhotoSearchFragment.class))
                        {
                            //Bundle bundle = new Bundle();
                            //bundle.putString(EXTRA_MESSAGE, (String) view_holder.btn.getText());
                            FlickrPhotoSearchFragment temp = (FlickrPhotoSearchFragment) fragments.get(count);

                            Log.i("tag", "Test Tag: " + tag);
                            temp.searchByTag(tag);
                            Log.i("tag1", "TAG :  " + tag);
                          //  f.setArguments();
                            //Log.i("tag", "test: " + fragments.get(count).getClass());

                        }
                        count++;


                    }

                    Log.i("tag", "test: " + FlickrPhotoSearchFragment.class);
                    Log.i("tag", "test: " + fragments.get(0).getClass());


                    // FlickrPhotoSearchFragment frag = new FlickrPhotoSearchFragment();

                    // frag.setArguments();
                    // frag.setArguments(bundle);
                    Log.i("tag", "hhere");

                }
            });

            return convertView;
        }


        // https://www.codeofaninja.com/2013/09/android-viewholder-pattern-example.html
        // http://developer.android.com/training/improving-layouts/smooth-scrolling.html


    }

    // http://stackoverflow.com/questions/6102007/is-there-a-way-to-get-references-for-all-currently-active-fragments-in-an-activi

    public List<Fragment> getFragments()
    {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();

        if (fragments == null || fragments.isEmpty())
        {
            return Collections.emptyList();
        }

        //List<Fragment> visibleFragments = new ArrayList<Fragment>();
        //for (Fragment fragment : allFragments) {
          //  if (fragment.isVisible()) {
            //    visibleFragments.add(fragment);
            //}
        //}
        return fragments;// visibleFragments;
    }

    static private class FlickrViewHolder
    {
        Button btn;
    }

}
