package com.tanners.smartwallpaper.flickrdata;

import com.fasterxml.jackson.databind.ObjectMapper;


import com.tanners.smartwallpaper.flickrdata.photodata.FlickrPhotoContainer;
import com.tanners.smartwallpaper.urlutil.URLConnection;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.MalformedURLException;

public class FlickrDataPhotos extends FlickrData
{
    public FlickrDataPhotos()
    {
        super();
    }

    public FlickrPhotoContainer populateFlickrPhotos(String tag)
    {
        FlickrPhotoContainer flickr = null;
        URLConnection connection = null;

        try {
            connection = new URLConnection(url_builder.getPhotos(tag, 12, 1));
            String responseStr = IOUtils.toString(connection.getHttpURLConnection().getInputStream());
            ObjectMapper objectMapper = new ObjectMapper();
            flickr = objectMapper.readValue(responseStr, FlickrPhotoContainer.class);
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



