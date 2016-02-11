package com.tanners.smartwallpaper.clarifaidata;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tags
{
    private List<String> tags;

    private int counter;

    public Tags()
    {
        this.counter = 0;
        this.tags = new ArrayList<String>();
    }

    public boolean addTag(String str)
    {
        if((str.length() <= 0) || (str == null))
        {
            return false;
        }
        else
        {
            this.tags.add(str);
            return true;
        }
    }

    public String popTag()
    {
        String temp = this.tags.get(0);
        this.tags.remove(0);
        return temp;
    }

    public int getSize()
    {
        return this.tags.size();
    }

    public String getTag(int pos)
    {
        if(pos < 0)
            return null;
        else
            return this.tags.get(pos);
    }

    public List<String> getTagList()
    {
        Log.i("error", "return");
        return this.tags;
    }
}
