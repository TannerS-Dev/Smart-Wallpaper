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
        ByteArrayOutputStream output = url_connection.readData(BASEURL + METHOD + GET_HOT_LIST_TAGS + APP_KEY + GET_HOT_LIST_TAGS_PARA + FORMAT);
        String json_tags = output.toString();
        List<String> tags = new ArrayList<String>();

        try
        {
            JSONObject root = new JSONObject(json_tags).getJSONObject("hottags");
            //JSONObject tag_array = root.getJSONObject("hottags");
            JSONArray tag_arry = root.getJSONArray("tag");
            //JSONArray size = sizes.getJSONArray("size")

            Log.i("tags", "len: " +String.valueOf(tag_arry.length()));

            JSONObject tag;

            for (int i = 0; i < tag_arry.length(); i++)
            {
                tag = tag_arry.getJSONObject(i);
                tags.add(tag.getString("_content"));
            }


        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return tags;
    }
}
