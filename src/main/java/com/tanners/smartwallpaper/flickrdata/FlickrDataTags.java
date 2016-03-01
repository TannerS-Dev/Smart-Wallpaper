package com.tanners.smartwallpaper.flickrdata;

import com.tanners.smartwallpaper.urlutil.URLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class FlickrDataTags extends FlickrData
{
    FlickrURLBuilder url;

    public FlickrDataTags()
    {
        url = new FlickrURLBuilder();
    }

    public List<String> getTagsHotList()
    {
        URLConnection connection = new URLConnection(url.getTrendingTags());
        List<String> tags = null;

        if(connection.isGood())
        {
            ByteArrayOutputStream output = connection.readData();
            String json_tags = output.toString();
            tags = new ArrayList<String>();

            try {
                JSONObject root = new JSONObject(json_tags).getJSONObject("hottags");
                JSONArray tag_arry = root.getJSONArray("tag");


                for (int i = 0; i < tag_arry.length(); i++)
                    tags.add(tag_arry.getJSONObject(i).getString("_content"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return tags;
    }
}
