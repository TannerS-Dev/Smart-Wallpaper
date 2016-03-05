package com.tanners.smartwallpaper.clarifaidata;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import com.tanners.smartwallpaper.R;
import com.tanners.smartwallpaper.ResultsActivity;
import com.tanners.smartwallpaper.flickrdata.FlickrPhotoSearchFragment;

import java.util.List;

//public class ClarifaiTagAdapter extends ArrayAdapter<String>
//{
    /*
    private Context context;
    private List<String> taglist;
    public final static String EXTRA_MESSAGE = "TAG";

    public ClarifaiTagAdapter(Context context, int resource, List<String> objects)
    {
        super(context, resource, objects);
        this.context = context;
        this.taglist = objects;
    }

    @Override
    public int getCount()
    {
        return taglist.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        final ClarifaiViewHolder view_holder;

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.clarifai_tags_layout, parent, false);
            view_holder = new ClarifaiViewHolder();
            view_holder.btn = (Button) convertView.findViewById(R.id.clarifai_tag_button);

            convertView = layoutInflater.inflate(R.layout.activity_main, parent, false);
            view_holder.view_pager = (ViewPager) convertView.findViewById(R.id.view_pager);
            view_holder.drawer = (DrawerLayout) convertView.findViewById(R.id.drawer_layout);


            convertView.setTag(view_holder);
        }
        else
        {
            view_holder = (ClarifaiViewHolder) convertView.getTag();
        }

        final String tag = this.taglist.get(position);

        if(this.taglist != null)
        {
            view_holder.btn.setText(tag);
            final View finalConvertView = convertView;
            view_holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //final Intent intent = new Intent(context, ResultsActivity.class);
                   // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   // intent.putExtra(EXTRA_MESSAGE, tag);
                   // context.startActivity(intent);

                    view_holder.view_pager.setCurrentItem(1);



                   // drawer.closeDrawer(GravityCompat.START);

                  //  view_holder.get

                            Activity temp = (Activity) finalConvertView.getContext();
                            temp.getFragments();
                    context.getFragments();

                    context.getSupportFragmentManager()

                    List<Fragment> fragments = context.getFragments();
                    int count = 0;

                    for (Fragment f : fragments)
                    {
                        if (f.getClass().equals(FlickrPhotoSearchFragment.class))
                        {
                            FlickrPhotoSearchFragment temp = (FlickrPhotoSearchFragment) fragments.get(count);
                            temp.searchByTag(tag);
                        }
                        count++;
                    }
                }
            });
        }

        return convertView;
    }

    static class ClarifaiViewHolder
    {
        private Button btn;
        private ViewPager view_pager;
        private DrawerLayout drawer;
    }
    */
//}