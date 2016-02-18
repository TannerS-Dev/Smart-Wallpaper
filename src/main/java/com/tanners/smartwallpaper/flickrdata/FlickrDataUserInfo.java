package com.tanners.smartwallpaper.flickrdata;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tanners.smartwallpaper.flickrdata.FlickrData;

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

/**
 * Created by Tanner Summers on 2/17/2016.
 */
public class FlickrDataUserInfo extends FlickrData
{
    private final String GET_PHOTOS_METHOD = "flickr.people.getInfo";
    private final String GET_PHOTOS_PARA = "&privacy_filter=public&content_type=1&media=photos";
    private final String GET_USER_ID =" &user_id=";
    private final String TAG ="&tags=";
    private String real_name;
    private String username;
    //private

    public FlickrDataUserInfo()
    {
        super();
        real_name = "unknown" ;
        username = real_name;
    }

    public void genrateUserInfo(String user_id)
    {
        String complete_user_id = GET_USER_ID + user_id;
        URL url = null;
        ByteArrayOutputStream output = url_connection.readData(BASEURL + METHOD + GET_PHOTOS_METHOD + APP_KEY + complete_user_id + GET_PHOTOS_PARA + FORMAT);
        Log.i("info", BASEURL + METHOD + GET_PHOTOS_METHOD + APP_KEY + complete_user_id + GET_PHOTOS_PARA + FORMAT);
        String json_info = output.toString();

        String name = null;

        try
        {
            JSONObject root = new JSONObject(json_info).getJSONObject("person");
            JSONObject name_object = root.getJSONObject("realname");
            name_object = name_object.getJSONObject("_content");
            this.real_name = name_object.toString();

            name_object = root.getJSONObject("username");
            name_object = name_object.getJSONObject("_content");
            this.username = name_object.toString();
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return this.real_name;
    }

    public void setFullName(String fullname) {
        this.real_name = fullname;
    }
}
