package com.tanners.smartwallpaper.flickrdata;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;


import com.tanners.smartwallpaper.flickrdata.photodata.FlickrPhotoContainer;
import com.tanners.smartwallpaper.flickrdata.photodata.FlickrPhotoItem;
import com.tanners.smartwallpaper.flickrdata.photodata.FlickrPhotos;
import com.tanners.smartwallpaper.urlutil.URLConnection;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FlickrDataPhotosSearch
{
    FlickrURLBuilder url;
    int per_page;
    int page;
    int total;

    public FlickrDataPhotosSearch(int total, int per_pages, int page)
    {
        url = new FlickrURLBuilder();
        this.page = page;
        this.per_page = per_pages;
        this.total = total;
    }

    public FlickrDataPhotosSearch()
    {
        url = new FlickrURLBuilder();
        this.page = 1;
        this.per_page = 100;
        this.total = 2000;
    }

    public List<FlickrPhotoItem> populateFlickrPhotos(String tag)
    {
        FlickrPhotoContainer flickr = null;
        List<FlickrPhotoItem> photos = new ArrayList<FlickrPhotoItem>();
        URLConnection connection = null;

        Log.i("new", "Tag: " + tag);

        try {

            int i = (int) Math.ceil(total / per_page);
            Log.i("new", Integer.toString(i));

            for(int loop = 0; loop < i; loop++)
            {

                Log.i("new", "looping : " + loop + " out of : " + i);
                // TODO default hard codded values
                connection = new URLConnection(url.getPhotos(tag, per_page, page++));
                String responseStr = IOUtils.toString(connection.getHttpURLConnection().getInputStream());
                ObjectMapper objectMapper = new ObjectMapper();
                flickr = objectMapper.readValue(responseStr, FlickrPhotoContainer.class);
                photos.addAll(flickr.getPhotos().getPhoto());
            }
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return photos;
    }
}



