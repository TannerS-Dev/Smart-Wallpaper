package com.tanners.smartwallpaper.flickrdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tanners.smartwallpaper.flickrdata.photodata.FlickrPhotoContainer;
import com.tanners.smartwallpaper.flickrdata.urldata.FlickrURLBuilder;
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
            connection = new URLConnection(url.getRecentPhotos(700, 1));

            if(connection.isGood())
            {
                String responseStr = IOUtils.toString(connection.getHttpURLConnection().getInputStream());
                ObjectMapper objectMapper = new ObjectMapper();
                flickr = objectMapper.readValue(responseStr, FlickrPhotoContainer.class);
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



