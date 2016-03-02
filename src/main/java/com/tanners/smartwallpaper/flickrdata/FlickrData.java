package com.tanners.smartwallpaper.flickrdata;

public class FlickrData
{
    protected final String APP_KEY = "&api_key=1adb86b105be09a175478f8a4f2945aa";
    protected final String BASEURL = "https://api.flickr.com/services/rest/";
    protected final String METHOD = "?method=";
    protected final String FORMAT = "&format=json&nojsoncallback=1";
    // trending tags
    protected final String GET_HOT_LIST_TAGS = "flickr.tags.getHotList";
    protected final String GET_HOT_LIST_TAGS_PARA = "&period=week&count=15";
    // get photos
    protected final String GET_PHOTOS_METHOD = "flickr.photos.search";
    protected final String GET_PHOTOS_PARA = "&privacy_filter=public&content_type=1&safe_search=1&media=photos";
    protected final String GET_PHOTOS_EXTRA = "&extras=url_z%2C+url_t%2C+url_m%2C+url_n";
    protected final String GET_PHOTOS_EXTRA_2 = "&per_page=";
    protected final String GET_PHOTOS_EXTRA_3 = "&page=";
    protected final String TAG = "&tags=";
    //User info
    protected final String GET_INFO_METHOD = "flickr.people.getInfo";
    protected final String GET_INFO_ID ="&user_id=";
    //recent
    protected final String GET_RECENT_PHOTOS_METHOD = "flickr.photos.getRecent";
    // url builder
}