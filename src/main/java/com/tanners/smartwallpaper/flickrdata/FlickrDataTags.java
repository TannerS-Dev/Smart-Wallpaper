package com.tanners.smartwallpaper.flickrdata;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class FlickrDataTags extends FlickrData
{
    private final String GET_HOT_LIST_TAGS = "flickr.tags.getHotList";
    private final String GET_HOT_LIST_TAGS_PARA = "&period=week&count=15";

    public FlickrDataTags()
    {
        super();
    }

    public List<String> getTagsHotList()
    {
        URLConnection connection = new URLConnection(BASEURL + METHOD + GET_HOT_LIST_TAGS + APP_KEY + GET_HOT_LIST_TAGS_PARA + FORMAT);
        ByteArrayOutputStream output = connection.readData();
        String json_tags = output.toString();
        List<String> tags = new ArrayList<String>();

        try
        {
            JSONObject root = new JSONObject(json_tags).getJSONObject("hottags");
            JSONArray tag_arry = root.getJSONArray("tag");
            //TODO shorter way to do this?

            JSONObject tag;

            for (int i = 0; i < tag_arry.length(); i++)
                tags.add(tag_arry.getJSONObject(i).getString("_content"));

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return tags;
    }
}
