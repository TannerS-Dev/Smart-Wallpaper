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
    private int height_z;
    private int width_z;
    private int height_t;
    private int width_t;
    private int height_q;
    private int width_q;
    private String owner;
    private String secret;
    private int server;
    private int farm;
    private boolean ispublic;
    private boolean isfreind;
    private boolean isfamily;


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

    public boolean ispublic() {
        return ispublic;
    }

    public void setIspublic(boolean ispublic) {
        this.ispublic = ispublic;
    }

    public boolean isfreind() {
        return isfreind;
    }

    public void setIsfreind(boolean isfreind) {
        this.isfreind = isfreind;
    }

    public boolean isfamily() {
        return isfamily;
    }

    public void setIsfamily(boolean isfamily) {
        this.isfamily = isfamily;
    }

    public int getFarm() {
        return farm;
    }

    public void setFarm(int farm) {
        this.farm = farm;
    }

    public int getServer() {
        return server;
    }

    public void setServer(int server) {
        this.server = server;
    }

    public int getWidth_q() {
        return width_q;
    }

    public void setWidth_q(int width_q) {
        this.width_q = width_q;
    }

    public int getHeight_q() {
        return height_q;
    }

    public void setHeight_s(int height_q) {
        this.height_q = height_q;
    }

    public int getWidth_t() {
        return width_t;
    }

    public void setWidth_t(int width_t) {
        this.width_t = width_t;
    }

    public int getHeight_t() {
        return height_t;
    }

    public void setHeight_t(int height_t) {
        this.height_t = height_t;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
