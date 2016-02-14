package com.tanners.smartwallpaper.imagedata;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public class ImageAdapter extends BaseAdapter
{
    Context context;
    List<PhotoObject> photos;

    public ImageAdapter(Context context, List<PhotoObject> photos)
    {
        this.context = context;
        this.photos = photos;
    }

    @Override
    public int getCount()
    {
        return this.photos.size();
    }

    @Override
    public Object getItem(int position)
    {
        return this.photos.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {





        return null;
    }
}
