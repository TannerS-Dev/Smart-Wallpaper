package com.tanners.smartwallpaper.flickrdata.photodata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FlickrPhotoItem
{
    private float id;
    private String title;
    private String url_z;
    private String url_c;
    private String url_b;
    private String url_h;
    private String url_k;
    private String url_o;
    private String owner;


    public String getUrl_z()
    {
        return url_z;
    }

    public String getTitle()
    {
        return title;
    }

    public float getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }


    public String getUrl_c() {
        return url_c;
    }

    public String getUrl_b() {
        return url_b;
    }

    public String getUrl_h() {
        return url_h;
    }

    public String getUrl_k() {
        return url_k;
    }

    public String getUrl_o() {
        return url_k;
    }
}
