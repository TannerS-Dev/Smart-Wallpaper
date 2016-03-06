package com.tanners.smartwallpaper;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.tanners.smartwallpaper.clarifaidata.ClarifaiFragment;
import com.tanners.smartwallpaper.flickrdata.FlickrDataPhotosSearch;
import com.tanners.smartwallpaper.flickrdata.FlickrPhotoSearchFragment;
import com.tanners.smartwallpaper.flickrdata.FlickrRecentPhotosFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    private ArrayAdapter<String> firebase_tag_adapter;
    private ListView nav_bar_list_view;
    private int tag_selector;
    private final int FRAG_COUNT = 3;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private TabLayout tab_layout;
    private final String FIREBASE_HOME = "https://smartwallpaper.firebaseio.com/";
    private Firebase fire_base;
    private HashMap<String, HashMap<String, HashMap<String, String>>> tags;
    private FlickrViewHolder view_holder;
    private ViewPager view_pager;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nav_bar_list_view = (ListView) findViewById(R.id.nav_bar_adapter);
        generateNavBar();
        initFireBase();
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_tool_bar);
        tag_selector = 0;
        setUpTabs();
    }

    private void initFireBase()
    {
        Firebase.setAndroidContext(this);
        fire_base = new Firebase(FIREBASE_HOME);

        // TODO firebase cal
        fire_base.child("tags").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                tags = snapshot.getValue(HashMap.class);
                setUpAdapter(tags.keySet());
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });
    }

    private void setUpAdapter(Set<String> temp)
    {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.activity_main, null, false);
        ArrayList<String>  list = new ArrayList<String>();
        list.addAll(temp);
        Collections.sort(list);
        firebase_tag_adapter = new GenericTagAdapter(getApplicationContext(), R.layout.activity_main, list);
        nav_bar_list_view.setAdapter(firebase_tag_adapter);
    }

    private void setUpTabs()
    {
        view_pager = (ViewPager) findViewById(R.id.view_pager);
        view_pager.setOffscreenPageLimit(FRAG_COUNT);
        setUpFragments(view_pager);
        tab_layout = (TabLayout) findViewById(R.id.tabs);
        tab_layout.setupWithViewPager(view_pager);
    }

    private void setUpFragments(ViewPager viewPager)
    {
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addTab(new FlickrRecentPhotosFragment(), "Recent");
        adapter.addTab(new FlickrPhotoSearchFragment(), "Search");
        adapter.addTab(new ClarifaiFragment(), "Clarifai");
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
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public ViewPager getViewPager()
    {
        if (null == view_pager)
        {
            view_pager = (ViewPager) findViewById(R.id.view_pager);
        }
        return view_pager;
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

        @Override
        public CharSequence getPageTitle(int position)
        {
            return titles.get(position);
        }
    }

    public class GenericTagAdapter extends ArrayAdapter<String>
    {
        private Context context;
        private List<String> taglist;
        private FlickrPhotoSearchFragment search_frag;

        public GenericTagAdapter(Context context, int resource, List<String> objects)
        {
            super(context, resource, objects);
            this.context = context;
            this.taglist = objects;
            int count = 0;

            List<Fragment> fragments = getFragments();

            for (Fragment f : fragments)
            {
                if (f.getClass().equals(FlickrPhotoSearchFragment.class))
                {
                    search_frag = (FlickrPhotoSearchFragment) fragments.get(count);
                }
                count++;
            }
        }

        @Override
        public int getCount()
        {
            return taglist.size();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent)
        {
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
                view_holder = (FlickrViewHolder) convertView.getTag();
            }

            final String tag = this.taglist.get(position);
            view_holder.btn.setText(tag);

            view_holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    view_pager.setCurrentItem(1);
                    drawer.closeDrawer(GravityCompat.START);
                    search_frag.searchByTag(tag, FlickrDataPhotosSearch.GROUP_SEARCH);
                }

            });

            return convertView;
        }
    }

    public List<Fragment> getFragments()
    {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();

        if (fragments == null || fragments.isEmpty())
        {
            return Collections.emptyList();
        }
        return fragments;
    }

    static private class FlickrViewHolder
    {
        Button btn;
    }
}
