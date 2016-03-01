package com.tanners.smartwallpaper;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import com.tanners.smartwallpaper.R;
import com.tanners.smartwallpaper.ResultsActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GenericTagAdapter extends ArrayAdapter<String>
{
    private Context context;
    private List<String> taglist;
    public final String EXTRA_MESSAGE = "TAG";

    public GenericTagAdapter(Context context, int resource, List<String> objects)
    {
        //TODO resourcei a passed layout thta is not used
        super(context, resource, objects);
        this.context = context;
        this.taglist = objects;
        notifyDataSetChanged();
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
        FlickrViewHolder view_holder;
        notifyDataSetChanged();
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
        view_holder.btn.setText(tag);
        view_holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(context, ResultsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(EXTRA_MESSAGE, tag);
                context.startActivity(intent);
            }
        });

        return convertView;
    }


    // https://www.codeofaninja.com/2013/09/android-viewholder-pattern-example.html
    // http://developer.android.com/training/improving-layouts/smooth-scrolling.html

    static private class FlickrViewHolder
    {
        Button btn;
    }
}

