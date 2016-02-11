package com.tanners.smartwallpaper.clarifaidata;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.net.Uri;
import com.clarifai.api.ClarifaiClient;
import com.clarifai.api.RecognitionRequest;
import com.clarifai.api.RecognitionResult;
import com.clarifai.api.Tag;
import com.clarifai.api.exception.ClarifaiException;
import com.squareup.picasso.Picasso;

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

    public String getAppID()
    {
        return APP_ID;
    }

    public String getAppSecret()
    {
        return APP_SECRET;
    }

    public int getOKCode()
    {
        return OK_CODE;
    }

    public boolean addTags(RecognitionResult result)
    {
        Log.i("error", "start" );


        if (result != null)
        {
            Log.i("error", "not null" );

            if (result.getStatusCode() == RecognitionResult.StatusCode.OK)
            {

                Log.i("error", "stat ok" );
                this.tags = new Tags();

               // int counter = 0;

                for (Tag t : result.getTags())
                {
                    Log.i("error", t.getName() );


                    tags.addTag(t.getName());

                  //  counter++;
                }
            }
            else
            {
                Log.i("error", "badcode");
                return false;
            }
        }
        else
        {
            Log.i("error", "isnull");
            return false;
        }
        return true;
    }

    public Tags getTags()
    {
        Log.i("error" , "return2");
        return this.tags;
    }

    public RecognitionResult recognizeBitmap(Uri uri)
    {
        Log.i("error", "reconizebackground");
        try
        {
            Bitmap image = null;

            try
            {
                image = Picasso.with(this.context).load(uri).resize(320, 320).onlyScaleDown().get();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            // Compress the image as a JPEG.
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, out);
            //byte[] image_bytes = ;


            Log.i("error", "recon results" );

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
