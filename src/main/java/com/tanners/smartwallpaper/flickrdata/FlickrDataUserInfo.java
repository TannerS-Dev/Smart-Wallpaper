package com.tanners.smartwallpaper.flickrdata;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tanners.smartwallpaper.flickrdata.FlickrData;
import com.tanners.smartwallpaper.urlutil.URLConnection;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class FlickrDataUserInfo
{
    private String real_name;
    private String username;
    FlickrURLBuilder url;

    public FlickrDataUserInfo()
    {
        real_name ="";
        username = "";
        url = new FlickrURLBuilder();
    }

    public void generateUserInfo(String temp)
    {
        URLConnection connection = new URLConnection(url.getUserInfo(temp));

        if(connection.isGood())
        {
            ByteArrayOutputStream output = connection.readData();
            String json_info = output.toString();

            try
            {
                JSONObject root = new JSONObject(json_info).getJSONObject("person");
                JSONObject username_attribute = root.getJSONObject("username");
                this.username = username_attribute.getString("_content");
                JSONObject realname_attribute = root.getJSONObject("realname");
                this.real_name = realname_attribute.getString("_content");
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }

    public String getUsername()
    {
        return username;
    }

    public String getFullName()
    {
        return this.real_name;
    }
}
