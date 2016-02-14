package com.tanners.smartwallpaper.clarifaidata;

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

public class ClarifaiTagAdapter extends ArrayAdapter<String>
{
    private Context context;
    private List<String> taglist;
    public final static String EXTRA_MESSAGE = "com.tanners.smartwallpaper.clarifaidata.ClarifaiTagAdapter";

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

    // https://www.codeofaninja.com/2013/09/android-viewholder-pattern-example.html
    // http://lucasr.org/2012/04/05/performance-tips-for-androids-listview/

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        ClarifaiViewHolder view_holder;

        if(convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.clarifai_tags, parent, false);
            view_holder = new ClarifaiViewHolder();
            view_holder.btn =  (Button) convertView.findViewById(R.id.clarifai_tag_button);
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
            view_holder = (ClarifaiViewHolder) convertView.getTag();
        }

        //final String tag = this.taglist.get(position);
       // view_holder.btn.setText(tag);

/*

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.clarifai_tags, parent, false);

        final String tag = this.taglist.get(position);

        //*******************************************************************************************************DO THIS
        // http://developer.android.com/training/improving-layouts/smooth-scrolling.html

        Button btn = (Button) view.findViewById(R.id.clarifai_tag_button);
        btn.setText(tag);

        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final Intent intent = new Intent(context, ResultsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(EXTRA_MESSAGE, tag);
                context.startActivity(intent);
            }
        });
*/
        //return view;
        return convertView;

    }

    // https://www.codeofaninja.com/2013/09/android-viewholder-pattern-example.html
    // http://developer.android.com/training/improving-layouts/smooth-scrolling.html

    static class ClarifaiViewHolder
    {
        Button btn;
    }










}






/*
// https://www.codeofaninja.com/2013/09/android-viewholder-pattern-example.html

    // http://developer.android.com/reference/android/widget/ArrayAdapter.html#getView(int, android.view.View, android.view.ViewGroup)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.clarifai_tags, parent, false);

        final String tag = this.taglist.get(position);

        //*******************************************************************************************************DO THIS
        // http://developer.android.com/training/improving-layouts/smooth-scrolling.html

        Button btn = (Button) view.findViewById(R.id.clarifai_tag_button);
        btn.setText(tag);





        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final Intent intent = new Intent(context, ResultsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(EXTRA_MESSAGE, tag);
                context.startActivity(intent);
            }
        });

        return view;

    }

 */