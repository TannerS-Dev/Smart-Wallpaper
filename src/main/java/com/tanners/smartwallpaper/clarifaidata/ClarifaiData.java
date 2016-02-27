package com.tanners.smartwallpaper.clarifaidata;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.clarifai.api.ClarifaiClient;
import com.clarifai.api.RecognitionRequest;
import com.clarifai.api.RecognitionResult;
import com.clarifai.api.Tag;
import com.clarifai.api.exception.ClarifaiException;
import com.squareup.picasso.Picasso;
import com.tanners.smartwallpaper.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ClarifaiData extends ClarifaiClient
{
    private final static String APP_ID = "Xijy3WCA1l4UwEb_zLDx10Kixh068oitW7PcHMv8";
    private final static String APP_SECRET = "U-e8tQDPSQxKl2MTvoQW1W1MbOE59oepZr8j92Gf";
    private final String ERROR_REC_IMAGE = "Unable to recognize image";
    private final String ERROR_LOAD_IMAGE = "Unable to load image";
    private final int OK_CODE = 1;
    private Context context;
    private Tags tags;

    public ClarifaiData(Context context)
    {
        super(APP_ID, APP_SECRET);
        this.context = context;
        this.tags = null;
    }

    public int getOKCode()
    {
        return OK_CODE;
    }

    public boolean addTags(RecognitionResult result)
    {
        if (result != null)
        {
            if (result.getStatusCode() == RecognitionResult.StatusCode.OK)
            {
                this.tags = new Tags();

                for (Tag t : result.getTags())
                    tags.addTag(t.getName());
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
        return true;
    }

    public Tags getTags()
    {
        return this.tags;
    }

    public RecognitionResult recognizeBitmap(Uri uri)
    {
        try
        {
            Bitmap image = null;

            try
            {
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = layoutInflater.inflate(R.layout.clarifai_main, null, false);
                ImageView image_view = (ImageView) view.findViewById(R.id.image_view);
                image = Picasso.with(this.context).load(uri).get();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            // Compress the image as a JPEG.
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 90, out);
            return recognize(new RecognitionRequest(out.toByteArray())).get(0);
        }
        catch (ClarifaiException e)
        {
            return null;
        }
    }

    public String getRecError()
    {
        return ERROR_REC_IMAGE;
    }
    public String getLoadError()
    {
        return ERROR_LOAD_IMAGE;
    }
}
