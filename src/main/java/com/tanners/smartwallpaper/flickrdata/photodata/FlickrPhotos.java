package com.tanners.smartwallpaper.flickrdata.photodata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FlickrPhotos
{
    private List<FlickrPhotoItem> photo;
    private int page;
    private int pages;
    private int perpage;
    private int total;

    public List<FlickrPhotoItem> getPhoto() {
        return photo;
    }

    public void setPhoto(List<FlickrPhotoItem> photo) {
        this.photo = photo;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPerpage() {
        return perpage;
    }

    public void setPerpage(int perpage) {
        this.perpage = perpage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
