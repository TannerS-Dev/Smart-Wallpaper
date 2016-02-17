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
    private final String GET_PHOTOS_METHOD = "flickr.photos.search";
    private final String GET_PHOTOS_PARA = "&privacy_filter=public&content_type=1&media=photos";
    private final String GET_PHOTOS_EXTRA = "&extras=url_t%2C+url_q%2C+url_z";
    private final String GET_PHOTOS_EXTRA_2 = "&per_page=";
    private final String GET_PHOTOS_EXTRA_3 = "&page=";
    private final String TAG ="&tags=";
    //private FlickrPhotoContainer flickr;

    public FlickrDataPhotos()
    {
        super();
    }

    public FlickrPhotoContainer populateFlickrPhotos(String tag, int page, int per_page)
    {
        String tag_ =  TAG + tag;
        String page_ = GET_PHOTOS_EXTRA_3 + Integer.toString(page);
        String page_per_ = GET_PHOTOS_EXTRA_2 + Integer.toString(per_page);
        FlickrPhotoContainer flickr = null;

         /*
        ByteArrayOutputStream output = url_connection.readData(BASEURL + METHOD + GET_PHOTOS_METHOD + APP_KEY + tag_ + GET_PHOTOS_PARA + GET_PHOTOS_EXTRA + page_per_ + page_ + FORMAT);
        ObjectMapper object_mapper = new ObjectMapper();
        FlickrPhotoContainer flickr = null;

        Log.i("pic", BASEURL + METHOD  + GET_PHOTOS_METHOD + APP_KEY + tag_ + GET_PHOTOS_PARA + GET_PHOTOS_EXTRA + page_per_ + page_ + FORMAT);



        try {
            flickr = object_mapper.readValue(output.toByteArray(), FlickrPhotoContainer.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(flickr == null)
                Log.i("pic", "flickr object in flickrdataphotos is null");

            return flickr;
        }
        */


        URL url = null;
        try {
            url = new URL(BASEURL + METHOD  + GET_PHOTOS_METHOD + APP_KEY + tag_ + GET_PHOTOS_PARA + GET_PHOTOS_EXTRA + page_per_ + page_ + FORMAT);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.i("pic", BASEURL + METHOD  + GET_PHOTOS_METHOD + APP_KEY + tag_ + GET_PHOTOS_PARA + GET_PHOTOS_EXTRA + page_per_ + page_ + FORMAT);

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            urlConnection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int code = 0;
        try {
            code = urlConnection.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
       // Log.i("pic", "Response code: " + code);

        String responseStr = null;
        try {
            Log.i("pic", "checkpoint0");
            responseStr = IOUtils.toString(urlConnection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.i("pic", responseStr);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
       // getObjectMapper().getDeserializationConfig().setDateFormat(dateFormat);

        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.setDateFormat(dateFormat);

        try {
            Log.i("pic", "checkpoint");
            flickr = objectMapper.readValue(responseStr, FlickrPhotoContainer.class);

            if(flickr == null)
                Log.i("pic", "flickr is nul!!!!!!!!!!!!!!!!!!!");
            else
                Log.i("pic", "flickr is NOT null!!!!!!!!!!!!!");


            Log.i("pic", flickr.getStat());

        } catch (IOException e) {
            e.printStackTrace();
        }


        if(flickr == null)
            Log.i("pic", "flickr object in flickrdataphotos is null");

        return flickr;
    }


 /*
    public List<String> getThumbsFromTag(String tag, int page, int per_page)
    {
        String tag_ =  TAG + tag;
        String page_ = GET_PHOTOS_EXTRA_3 + Integer.toString(page);
        String page_per_ = GET_PHOTOS_EXTRA_2 + Integer.toString(per_page);
        ByteArrayOutputStream output = url_connection.readData(BASEURL + METHOD + APP_KEY + tag_ + GET_PHOTOS_PARA + GET_PHOTOS_EXTRA + page_per_ + page_ + FORMAT);
        String json_photos = output.toString();
        List<FlickrPhotoItem> photos = new ArrayList<FlickrPhotoItem>();

        try
        {












            JSONObject root = new JSONObject(json_photos).getJSONObject("photos");
            JSONObject url_array = root.getJSONObject("hottags");
            int pages = root.getInt("pages");
            int current_page = root.getInt("page");
            JSONArray url_arry = root.getJSONArray("photo");

            JSONObject url;
            FlickrPhotoItem temp;

            for (int i = 0; i < url_arry.length(); i++)
            {
                url = url_arry.getJSONObject(i);
                temp = new FlickrPhotoItem();
                temp.setId();
                temp.setOwner();
                temp.setTitle();
                temp.setUrl_z();
                temp.setUrl_t();
                temp.getUrl_s();

                photos.add(tag.getString("_content"));
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return tags;

    }

*/





}
