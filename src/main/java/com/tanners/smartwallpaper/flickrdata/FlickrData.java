package com.tanners.smartwallpaper.flickrdata;

import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FlickrData
{
    protected final String APP_KEY = "&api_key=1adb86b105be09a175478f8a4f2945aa";
    protected final String BASEURL = "https://api.flickr.com/services/rest/";
    protected final String METHOD = "?method=";
    protected final String FORMAT = "&format=json&nojsoncallback=1";
    protected URLConnection url_connection;

    public FlickrData()
    {
        url_connection = new URLConnection();
    }



}