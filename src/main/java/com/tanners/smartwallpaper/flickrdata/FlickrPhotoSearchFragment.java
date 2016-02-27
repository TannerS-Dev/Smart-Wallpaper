package com.tanners.smartwallpaper.flickrdata;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tanners.smartwallpaper.R;

public class FlickrPhotoSearchFragment  extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.i("text", "HHHHE");
        return inflater.inflate(R.layout.flickr_fragment_search, container, false);
    }

}
