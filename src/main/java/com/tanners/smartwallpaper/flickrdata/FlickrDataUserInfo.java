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

public class FlickrDataUserInfo extends FlickrData
{
    private String real_name;
    private String username;

    public FlickrDataUserInfo()
    {
        super();
        real_name = username = "unknown" ;
    }

    // TODO left off
    public void generateUserInfo(String user_id)
    {
        String complete_user_id = GET_INFO_ID + user_id;
        Log.i("info", BASEURL + METHOD + GET_PHOTOS_METHOD + APP_KEY + complete_user_id + GET_PHOTOS_PARA + FORMAT);

        /*
        ByteArrayOutputStream output = url_connection.readData(BASEURL + METHOD + GET_PHOTOS_METHOD + APP_KEY + complete_user_id + GET_PHOTOS_PARA + FORMAT);
        //Log.i("info", BASEURL + METHOD + GET_PHOTOS_METHOD + APP_KEY + complete_user_id + GET_PHOTOS_PARA + FORMAT);

        //String json_info = output.toString();

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
        }*/
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
