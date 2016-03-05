package com.tanners.smartwallpaper.flickrdata;

import android.util.Log;

public class FlickrURLBuilder extends FlickrData
{
    public String getPhotos(String tag, int per_page, int page)
    {
        String tag_ = TAG + tag;
        String page_ = GET_PHOTOS_EXTRA_3 + Integer.toString(page);
        String page_per_ = GET_PHOTOS_EXTRA_2 + Integer.toString(per_page);
        Log.e("url", BASEURL + METHOD + GET_PHOTOS_METHOD + APP_KEY + tag_ + GET_PHOTOS_PARA + GET_PHOTOS_EXTRA + page_per_ + page_ + FORMAT);
        return (BASEURL + METHOD + GET_PHOTOS_METHOD + APP_KEY + tag_ + GET_PHOTOS_PARA + GET_PHOTOS_EXTRA + page_per_ + page_ + FORMAT);
    }

    public String getRecentPhotos(int per_page, int page)
    {
        String page_ = GET_PHOTOS_EXTRA_3 + Integer.toString(page);
        String page_per_ = GET_PHOTOS_EXTRA_2 + Integer.toString(per_page);
        Log.e("url", BASEURL + METHOD + GET_RECENT_PHOTOS_METHOD + APP_KEY + GET_PHOTOS_EXTRA + page_per_ + page_ + FORMAT);
        return (BASEURL + METHOD + GET_RECENT_PHOTOS_METHOD + APP_KEY + GET_PHOTOS_EXTRA + page_per_+ page_ + FORMAT);
    }

    public String getUserInfo(String user_id )
    {
        String complete_user_id = GET_INFO_ID + user_id;
        return (BASEURL + METHOD + GET_INFO_METHOD + APP_KEY + complete_user_id + GET_PHOTOS_PARA + FORMAT);
    }
}
