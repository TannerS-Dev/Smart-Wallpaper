package com.tanners.smartwallpaper;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.ListView;
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
import android.widget.Button;

// Clarifai imports
import com.clarifai.api.RecognitionResult;

// java imports
import java.util.ArrayList;
import java.util.List;

// TannerS
import com.tanners.smartwallpaper.clarifaidata.ClarifaiData;

// picasso
import com.squareup.picasso.Picasso;
import com.tanners.smartwallpaper.clarifaidata.ClarifaiFragment;
import com.tanners.smartwallpaper.clarifaidata.ClarifaiTagAdapter;
import com.tanners.smartwallpaper.flickrdata.FlickrDataTags;
import com.tanners.smartwallpaper.flickrdata.FlickrPhotoSearchFragment;
import com.tanners.smartwallpaper.flickrdata.FlickrRecentPhotosFragment;
import com.tanners.smartwallpaper.flickrdata.FlickrTagAdapter;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    private static final String CLASS = MainActivity.class.getSimpleName();
    private Toolbar toolbar;
    private NavigationView navigationView;
    private FlickrDataTags flickr_tags = null;
    private final String NO_IMAGES = "No images aviable for this tag";


    private Toolbar tabs_tool_bar;
    private TabLayout tab_layout;
    private ViewPager view_pager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        generateNavBar();
        flickr_tags = new FlickrDataTags();
        new GenerateTags().execute(flickr_tags);


        Toolbar toolbar = (Toolbar) findViewById(R.id.main_tool_bar);
        toolbar.setTitle("test");

        setUpTabs();
    }

    private void setUpTabs()
    {
        // Support library version of getActionBar(). : Retrieve a reference to this activity's ActionBar.

        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*
        VIew:pager Layout manager that allows the user to flip left and right through pages of data.
        You supply an implementation of a PagerAdapter to generate the pages that the view shows.
         */
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        setUpFragments(viewPager);
        // find tablayout
        tab_layout = (TabLayout) findViewById(R.id.tabs);
        // The one-stop shop for setting up this TabLayout with a ViewPager.
        tab_layout.setupWithViewPager(viewPager);

    }

    private void setUpFragments(ViewPager viewPager)
    {
        // Return the FragmentManager for interacting with fragments associated with this activity.
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        // add fragment objects to list


        //FlickrRecentPhotosFragment tab1 = new FlickrRecentPhotosFragment();
        // args.putInt("tab1", 1);
        //tab1.setArguments(args);
        // FlickrPhotoSearchFragment tab2 = new FlickrPhotoSearchFragment();
        // args.putInt("tab2", 2);
        // tab2.setArguments(args);
        // return tab2;
        //ClarifaiFragment tab3 = new ClarifaiFragment();
        //args.putInt("tab3", 3);
        // tab3.setArguments(args);
        // return tab3;



        adapter.addTab(new FlickrRecentPhotosFragment(), "TAB1");
        adapter.addTab(new FlickrPhotoSearchFragment(), "TAB2");
        adapter.addTab(new ClarifaiFragment(), "TAB3");
        // set view pager with adapter class of tab objects
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
      //  if (id == R.id.action_settings) {
        //    return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    private class GenerateTags extends AsyncTask<FlickrDataTags, Void, List<String>>
    {
        @Override
        protected List<String> doInBackground(FlickrDataTags... flickr_tags)
        {
            flickr_tags[0] = new FlickrDataTags();
            return flickr_tags[0].getTagsHotList();
        }

        @Override
        protected void onPostExecute(List<String> result)
        {
            super.onPostExecute(result);

            if(result == null || (result.size() == 0))
                noImagesToast(NO_IMAGES);
            else
            {
                ListView listview = (ListView) findViewById(R.id.flickrtagview);
                List<String> tags = result;
                FlickrTagAdapter adapter = new FlickrTagAdapter(getApplicationContext(), R.layout.nav_bar_header, tags);
                listview.setAdapter(adapter);
            }
        }

        private void noImagesToast(String str)
        {
            Toast toast = Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM | Gravity.LEFT, 0, 0);
            toast.show();
        }
    }

    class FragmentAdapter extends FragmentPagerAdapter
    {
        List<Fragment> frags;
        List<String> titles;

        public FragmentAdapter(FragmentManager manager)
        {
            super(manager);
            frags = new ArrayList<Fragment>();
            titles = new ArrayList<String>();
        }

        @Override
        public int getCount()
        {
            return frags.size();
        }

        @Override
        public Fragment getItem(int position)
        {
            // Bundle args = new Bundle();
            // Our object is just an integer :-P
            Bundle args = new Bundle();

           return frags.get(position);



           /* // a way to switch rto  a different fragment based on position and  pass a bundle of paramtaers to it's oncreate function
            switch(position) {
                case 0:
                    //FlickrRecentPhotosFragment tab1 = new FlickrRecentPhotosFragment();
                   // args.putInt("tab1", 1);
                    //tab1.setArguments(args);
                    return tab1;
                case 1:
                   // FlickrPhotoSearchFragment tab2 = new FlickrPhotoSearchFragment();
                   // args.putInt("tab2", 2);
                   // tab2.setArguments(args);
                   // return tab2;
                case 2:
                    //ClarifaiFragment tab3 = new ClarifaiFragment();
                    //args.putInt("tab3", 3);
                   // tab3.setArguments(args);
                   // return tab3;
            }
            */
           // return null;
        }

        public void addTab(Fragment frag, String title)
        {
            frags.add(frag);
            titles.add(title);
        }


        // idk yet
        @Override
        public CharSequence getPageTitle(int position)
        {
            return "OBJECT " + (position + 1);
        }

    }
}
