package com.tanners.smartwallpaper.flickrdata.urldata;

import android.util.Log;

public class FlickrURLBuilder
{
    private final String APP_KEY = "&api_key=1adb86b105be09a175478f8a4f2945aa";
    private final String BASEURL = "https://api.flickr.com/services/rest/";
    private final String METHOD = "?method=";
    private final String FORMAT = "&format=json&nojsoncallback=1";
    private final String PER_PAGE = "&per_page=";
    private final String PAGE = "&page=";
    private final String EXTRAS = "&extras=url_n%2Curl_z%2Curl_m";

    private final String FLICKR_GROUPS_METHOD = "flickr.groups.pools.getPhotos";
    private final String FLICKR_GROUPS_GROUP_ID = "&group_id=";
   // private final String FLICKR_GROUPS_EXTRAS= "&extras=url_n%2Curl_z%2Curl_m";

    private final String GET_INFO_METHOD = "flickr.people.getInfo";
    private final String GET_INFO_ID ="&user_id=";

    // URL: https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=48c548e1e49409599d8077ceae200efb&in_gallery=957553%40N22%2C+1848625%40N21&format=rest
    private final String FLICKR_SEARCH_METHOD = "flickr.photos.search";
    private final String FLICKR_SEARCH_PARAMETERS = "&privacy_filter=public&content_type=1&safe_search=1&media=photos";
    private final String FLICKR_SEARCH_EXTRA = "&extras=url_z%2C+url_t%2C+url_m%2C+url_n";
    private final String FLICKR_SEARCH_TAG = "&tags=";
    private final String FLICKR_SEARCH_GALLERY = "&in_gallery=";

    private final String FLICKR_INTERESTINGNESS = "flickr.interestingness.getList";

    public String getPhotos(String tag, String gallery, int per_page, int page)
    {
        String tag_ = FLICKR_SEARCH_TAG + tag;
        String page_ = PAGE + Integer.toString(page);
        String page_per_ = PER_PAGE + Integer.toString(per_page);
        String gallery_ = FLICKR_SEARCH_GALLERY + gallery;
        return (BASEURL + METHOD + FLICKR_SEARCH_METHOD + APP_KEY + gallery_ + tag_ + FLICKR_SEARCH_PARAMETERS + FLICKR_SEARCH_EXTRA + page_per_ + page_ + FORMAT);
    }

    public String getPhotos(String id, int per_page, int page)
    {
        String page_ = PAGE + Integer.toString(page);
        String page_per_ = PER_PAGE + Integer.toString(per_page);
        String id_ = FLICKR_GROUPS_GROUP_ID + id;
        return (BASEURL + METHOD + FLICKR_GROUPS_METHOD + APP_KEY + id + FLICKR_SEARCH_EXTRA + EXTRAS + page_per_ + page_ + FORMAT);
        // https://api.flickr.com/services/rest/?method=flickr.groups.pools.getPhotos&api_key=6d2f75219d8cb651caec8ad07c5e40ab&group_id=856094%40N22&extras=url_z&per_page=23&page=1&format=json&nojsoncallback=1
    }

    public String getRecentPhotos(int per_page, int page)
    {
        String page_ = PAGE + Integer.toString(page);
        String page_per_ = PER_PAGE + Integer.toString(per_page);

        return (BASEURL + METHOD + FLICKR_INTERESTINGNESS + APP_KEY + EXTRAS + page_per_ + page_ + FORMAT);
    }

    public String getUserInfo(String user_id )
    {
        String complete_user_id = GET_INFO_ID + user_id;

        return (BASEURL + METHOD + GET_INFO_METHOD + APP_KEY + complete_user_id + FORMAT);
    }
}
