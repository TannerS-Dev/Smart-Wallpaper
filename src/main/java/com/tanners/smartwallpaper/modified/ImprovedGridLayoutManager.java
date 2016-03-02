// https://androiddevx.wordpress.com/2014/12/05/recycler-view-pre-cache-views/
package com.tanners.smartwallpaper.modified;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public class ImprovedGridLayoutManager extends GridLayoutManager
{
    private static final int DEFAULT_EXTRA_LAYOUT_SPACE = 600;
    private int extra_space = -1;
    private Context context;

    public ImprovedGridLayoutManager(Context context, int cols, int extra_space)
    {
        super(context, cols);
        this.context = context;
        this.extra_space = extra_space;
    }

    @Override
    protected int getExtraLayoutSpace(RecyclerView.State state)
    {
        if (extra_space > 0)
        {
            return extra_space;
        }
        return DEFAULT_EXTRA_LAYOUT_SPACE;
    }
}