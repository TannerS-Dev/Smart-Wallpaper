package com.tanners.smartwallpaper.flickrdata;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import com.tanners.smartwallpaper.R;
import java.util.List;

public class FlickrTagAdapter extends ArrayAdapter<String>
{
    private Context context;
    private List<String> taglist;

    public FlickrTagAdapter(Context context, int resource, List<String> objects)
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

    // http://developer.android.com/reference/android/widget/ArrayAdapter.html#getView(int, android.view.View, android.view.ViewGroup)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        if(convertView == null)
            Log.i("tags", "fuck its null");


        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.flickr_menu_tags, parent, false);

        final String tag = this.taglist.get(position);

        Button btr_one = (Button) view.findViewById(R.id.flickr_tag_button);
        Log.i("tags", tag);
        btr_one.setText(tag);

        return view;

    }
}

