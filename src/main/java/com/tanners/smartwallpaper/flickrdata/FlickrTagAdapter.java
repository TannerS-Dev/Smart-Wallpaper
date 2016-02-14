package com.tanners.smartwallpaper.flickrdata;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import com.tanners.smartwallpaper.R;
import com.tanners.smartwallpaper.ResultsActivity;

import java.util.List;

public class FlickrTagAdapter extends ArrayAdapter<String>
{
    private Context context;
    private List<String> taglist;
    public final static String EXTRA_MESSAGE = "com.tanners.smartwallpaper.flickrdata.FlickrTagAdapter";

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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        FlickrViewHolder view_holder;

        if(convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.flickr_menu_tags, parent, false);
            view_holder = new FlickrViewHolder();
            view_holder.btn =  (Button) convertView.findViewById(R.id.flickr_tag_button);
            // here are below else statement?
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

            convertView.setTag(view_holder);
        }
        else
        {
            // we've just avoided calling findViewById() on resource everytime
            // just use the viewHolder
            view_holder = (FlickrViewHolder) convertView.getTag();
        }

       // final String tag = this.taglist.get(position);
       // view_holder.btn.setText(tag);

/*
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.flickr_menu_tags, parent, false);

        final String tag = this.taglist.get(position);

        Button btn = (Button) view.findViewById(R.id.flickr_tag_button);

        btn.setText(tag);

        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final Intent intent = new Intent(context, ResultsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(EXTRA_MESSAGE, tag);
                context.startActivity(intent);//startActivityForResult(media_intent, cdata.getOKCode());
            }
        });

        return view;

*/
        //return view;
        return convertView;

    }

    // https://www.codeofaninja.com/2013/09/android-viewholder-pattern-example.html
    // http://developer.android.com/training/improving-layouts/smooth-scrolling.html

    static class FlickrViewHolder
    {
        Button btn;
    }

}

