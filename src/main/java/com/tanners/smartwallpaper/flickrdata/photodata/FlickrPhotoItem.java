package com.tanners.smartwallpaper.flickrdata.photodata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FlickrPhotoItem
{
    private float id;
    private String title;
    private String url_z;
    private String url_n;
    private String url_m;
    private int height_z;
    private int width_z;
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

    public int getHeight_z() {
        return height_z;
    }

    public int getWidth_z() {
        return width_z;
    }

    public String getUrl_n() {
        return url_n;
    }

    public String getUrl_m() {
        return url_m;
    }
}
