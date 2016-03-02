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
            view_holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Intent intent = new Intent(context, ResultsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(EXTRA_MESSAGE, tag);
                    context.startActivity(intent);
                }
            });
        }

        return convertView;
    }

    static class ClarifaiViewHolder
    {
        Button btn;
    }
}