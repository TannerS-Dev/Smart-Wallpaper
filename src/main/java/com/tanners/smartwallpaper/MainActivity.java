package com.tanners.smartwallpaper;

// android imports
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.tanners.smartwallpaper.clarifaidata.ClarifaiTagAdapter;
import com.tanners.smartwallpaper.flickrdata.FlickrDataTags;
import com.tanners.smartwallpaper.flickrdata.FlickrTagAdapter;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    private static final String CLASS = MainActivity.class.getSimpleName();
    private Button selectButton;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ClarifaiData cdata = null;
    private ImageView imageview;
    private FlickrDataTags flickr_tags = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        generateNavBar();
        cdata = new ClarifaiData(MainActivity.this);
        imageview = (ImageView) findViewById(R.id.image_view);
        flickr_tags = new FlickrDataTags();
        new GenerateTags().execute(flickr_tags);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == cdata.getOKCode() && resultCode == RESULT_OK)
        {
            Uri image = intent.getData();

            if (image != null)
            {
                Picasso.with(this).load(image).centerInside().fit().into(imageview);
                selectButton.setText("Please wait");
                selectButton.setEnabled(false);

                new GetTags().execute(image);
            }
            else
                bottomToast(cdata.getLoadError());
        }
    }

    private void generateNavBar()
    {
        // AUTO GENERATED
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        selectButton = (Button) findViewById(R.id.select_button);

        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent media_intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // START API OVER NET
                Log.i("error", "start act");
                startActivityForResult(media_intent, cdata.getOKCode());
            }

        });
    }

    private void bottomToast(String str)
    {
        // getApplicationContext() | Return the context of the single, global Application object of the current process.
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, str, Toast.LENGTH_LONG);
        // public void setGravity (int gravity, int xOffset, int yOffset)
        toast.setGravity(Gravity.BOTTOM | Gravity.LEFT, 0, 0);
        toast.show();
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }

    private class GenerateTags extends AsyncTask<FlickrDataTags, Void, List<String>>
    {
        @Override
        protected List<String> doInBackground(FlickrDataTags... flickr_tags)
        {
            Log.i("tags", Integer.toString(flickr_tags.length));
            flickr_tags[0] = new FlickrDataTags();
            return flickr_tags[0].getTagsHotList();
        }

        @Override
        protected void onPostExecute(List<String> result)
        {
            super.onPostExecute(result);
            ListView listview = (ListView) findViewById(R.id.flickrtagview);
            List<String> tags = result;
            FlickrTagAdapter adapter = new FlickrTagAdapter(getApplicationContext(), R.layout.nav_header_main, tags);
            listview.setAdapter(adapter);
        }
    }

    private class GetTags extends AsyncTask<Uri, Void, RecognitionResult>
    {
        @Override
        protected RecognitionResult doInBackground(Uri... image)
        {
            return cdata.recognizeBitmap(image[0]);
        }

        @Override
        protected void onPostExecute(RecognitionResult result)
        {
            super.onPostExecute(result);

            if (cdata.addTags(result))
            {
                selectButton.setEnabled(true);
                selectButton.setText("Select a photo");
            }
            else
                bottomToast(cdata.getRecError());

            ListView listview = (ListView) findViewById(R.id.clarifaitagview);
            List<String> tags = cdata.getTags().getTagList();

            //no tags!
            if(tags == null || (tags.size() == 0))
            {
                // TODO make constant
                noTagsToast("No Tags For This Image");
            }
            else
            {
                ClarifaiTagAdapter adapter = new ClarifaiTagAdapter(MainActivity.this, R.layout.clarifai_tags, tags);
                listview.setAdapter(adapter);
            }
        }

        private void noTagsToast(String str)
        {
            Toast toast = Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM | Gravity.LEFT, 0, 0);
            toast.show();
        }
    }
}
