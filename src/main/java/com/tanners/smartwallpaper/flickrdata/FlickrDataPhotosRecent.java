package com.tanners.smartwallpaper.flickrdata;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tanners.smartwallpaper.flickrdata.photodata.FlickrPhotoContainer;
import com.tanners.smartwallpaper.urlutil.URLConnection;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.MalformedURLException;

public class FlickrDataPhotosRecent
{
    FlickrURLBuilder url;

    public FlickrDataPhotosRecent()
    {
        url = new FlickrURLBuilder();
    }

    public FlickrPhotoContainer populateFlickrPhotos()
    {
        FlickrPhotoContainer flickr = null;
        URLConnection connection = null;

        try
        {
            Log.i("test", "getting urls, should be called once?");
            // TODO fix hard coding
            connection = new URLConnection(url.getRecentPhotos(50, 1));

            if(connection.isGood())
            {
                String responseStr = IOUtils.toString(connection.getHttpURLConnection().getInputStream());
                ObjectMapper objectMapper = new ObjectMapper();
                flickr = objectMapper.readValue(responseStr, FlickrPhotoContainer.class);
                Log.i("test", "CUR SIZE:  "+ Integer.toString(flickr.getPhotos().getPhoto().size()) );

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
        return flickr;
    }



}



