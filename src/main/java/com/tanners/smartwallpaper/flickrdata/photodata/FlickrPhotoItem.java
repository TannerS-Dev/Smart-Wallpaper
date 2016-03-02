package com.tanners.smartwallpaper.flickrdata.photodata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class FlickrPhotoItem
{
    private float id;
    private String title;
    private String url_q;
    private String url_t;
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

    public void setUrl_z(String url_z)
    {
        this.url_z = url_z;
    }

    public String getUrl_t()
    {
        return url_t;
    }

    public void setUrl_t(String url_t)
    {
        this.url_t = url_t;
    }

    public String getUrl_q()
    {
        return url_q;
    }

    public void setUrl_s(String url_q)
    {
        this.url_q = url_q;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public float getId() {
        return id;
    }

    public void setId(float id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getHeight_z() {
        return height_z;
    }

    public void setHeight_z(int height_z) {
        this.height_z = height_z;
    }

    public int getWidth_z() {
        return width_z;
    }

    public void setWidth_z(int width_z) {
        this.width_z = width_z;
    }

    public String getUrl_n() {
        return url_n;
    }

    public void setUrl_n(String url_n) {
        this.url_n = url_n;
    }

    public String getUrl_m() {
        return url_m;
    }

    public void setUrl_m(String url_m) {
        this.url_m = url_m;
    }
}
