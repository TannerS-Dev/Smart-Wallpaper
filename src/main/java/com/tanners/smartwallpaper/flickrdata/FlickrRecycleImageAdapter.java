package com.tanners.smartwallpaper.flickrdata;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tanners.smartwallpaper.PhotoActivity;
import com.tanners.smartwallpaper.R;
import com.tanners.smartwallpaper.flickrdata.photodata.FlickrPhotoItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.support.v7.widget.RecyclerView.*;

public class FlickrRecycleImageAdapter extends RecyclerView.Adapter<FlickrRecycleImageAdapter.ImageViewHolder>
{
    Context context;
    List<FlickrPhotoItem> photos;
    FlickrDataUserInfo user_data;
    DisplayMetrics metrics;
    View view;

    public FlickrRecycleImageAdapter(Context context, List<FlickrPhotoItem> photos, DisplayMetrics metrics)
    {
        super();
        this.context = context;
        this.photos = photos;
        this.metrics = metrics;
        Collections.shuffle(this.photos);
        user_data = new FlickrDataUserInfo();
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        final DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.flickr_grid_image_layout, null);
        ImageViewHolder holder = new ImageViewHolder(view, metrics);
        return holder;
    }

    public String getInfo(int position)
    {
        StringBuilder photo_info = new StringBuilder("");
        FlickrPhotoItem data = photos.get(position);
        new GetUserInfo().execute(data.getOwner());
        photo_info.append("Name: " + user_data.getFullName() + "\n");
        photo_info.append("Username: " + user_data.getUsername() + "\n");
        photo_info.append("ID: " + data.getId() + "\n");
        photo_info.append("Title: " + data.getTitle() + "\n");
        photo_info.append("Owner: " + data.getOwner() + "\n");
        return photo_info.toString();
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder holder, final int position) {
        holder.image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  final Intent intent = new Intent(context, PhotoActivity.class);
               // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                FlickrPhotoItem data = photos.get(position);

                // TODO

                ArrayList<FlickrPhotoItem> url_list = new ArrayList<FlickrPhotoItem>();
                url_list.add(data);
                Bundle urls = new Bundle();
                urls.putSerializable("urls", url_list);

                final Intent extra_intent = new Intent(context, PhotoActivity.class);
                extra_intent.putExtra("extra", urls);
                extra_intent.putExtra("info", getInfo(position));
                extra_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                /*

                if (data.getUrl_k() == null || (data.getUrl_k().length() <= 0)) {
                    if (data.getUrl_h() == null || (data.getUrl_h().length() <= 0)) {
                        if (data.getUrl_b() == null || (data.getUrl_b().length() <= 0)) {
                            if (data.getUrl_c() == null || (data.getUrl_c().length() <= 0)) {
                                if (data.getUrl_z() == null || (data.getUrl_z().length() <= 0)) {
                                            intent.putExtra("url", data.getUrl_o());

                                } else {
                                    intent.putExtra("url", data.getUrl_z());
                                }
                            } else {
                                intent.putExtra("url", data.getUrl_c());
                            }
                        } else {
                            intent.putExtra("url", data.getUrl_b());
                        }
                    } else {
                        intent.putExtra("url", data.getUrl_h());
                    }
                } else {
                    intent.putExtra("url", data.getUrl_k());
                }

                */
                context.startActivity(extra_intent);
            }
        });

        FlickrPhotoItem data = photos.get(position);
        String url = "";

        /*
        if (data.getUrl_k() == null || (data.getUrl_k().length() <= 0)) {
            if (data.getUrl_h() == null || (data.getUrl_h().length() <= 0)) {
                if (data.getUrl_b() == null || (data.getUrl_b().length() <= 0)) {
                    if (data.getUrl_c() == null || (data.getUrl_c().length() <= 0)) {
                        if (data.getUrl_z() == null || (data.getUrl_z().length() <= 0)) {
                            url = data.getUrl_o();
                        } else {
                            url = data.getUrl_z();
                        }
                    } else {
                        url = data.getUrl_c();
                    }
                } else {
                    url = data.getUrl_b();
                }
            } else {
                url = data.getUrl_h();
            }
        } else {
            url = data.getUrl_k();
        }
               */

        if (data.getUrl_z() == null || (data.getUrl_z().length() <= 0))
        {
            if (data.getUrl_n() == null || (data.getUrl_n().length() <= 0))
            {
                url = data.getUrl_o();
            }
            else
            {
                url = data.getUrl_n();
            }
        }
        else
        {
            url = data.getUrl_z();
        }

        //Glide.with(context).load(url).thumbnail(.9f).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.image_button);
        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.image_button);
    }

    @Override
    public int getItemCount() {
        return this.photos.size();
    }

    public void clear()
    {
        photos.clear();
        notifyDataSetChanged();
    }

    public static class ImageViewHolder extends ViewHolder
    {
        private final ImageButton image_button;
        public ImageViewHolder(View view, DisplayMetrics metrics)
        {
            super(view);
            image_button = (ImageButton) view.findViewById(R.id.image_button);
            int screen_width = (metrics.widthPixels / 2);
            image_button.setLayoutParams(new RelativeLayout.LayoutParams(screen_width, screen_width));
        }
    }

    public class GetUserInfo extends AsyncTask<String, Void, FlickrDataUserInfo>
    {
        @Override
        protected FlickrDataUserInfo doInBackground(String... str)
        {
            user_data.generateUserInfo(str[0]);
            return user_data;
        }
    }
}

