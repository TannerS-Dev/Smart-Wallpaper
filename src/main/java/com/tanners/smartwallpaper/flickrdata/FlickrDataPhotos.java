package com.tanners.smartwallpaper.flickrdata;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;


import com.tanners.smartwallpaper.flickrdata.photodata.FlickrPhotoContainer;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;

public class FlickrDataPhotos extends FlickrData
{
    public FlickrDataPhotos() {
        super();
    }

    public FlickrPhotoContainer populateFlickrPhotos(String tag, int page, int per_page)
    {
        String tag_ = TAG + tag;
        String page_ = GET_PHOTOS_EXTRA_3 + Integer.toString(page);
        String page_per_ = GET_PHOTOS_EXTRA_2 + Integer.toString(per_page);
        FlickrPhotoContainer flickr = null;
        URLConnection connection = null;

        try {

            connection = new URLConnection(BASEURL + METHOD + GET_PHOTOS_METHOD + APP_KEY + tag_ + GET_PHOTOS_PARA + GET_PHOTOS_EXTRA + page_per_ + page_ + FORMAT);
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



