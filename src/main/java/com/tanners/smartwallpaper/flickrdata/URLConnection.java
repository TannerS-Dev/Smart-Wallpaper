package com.tanners.smartwallpaper.flickrdata;

import android.util.Log;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class URLConnection
{
    private static int TIMEOUT_CONNECTION_MS = 7000;
    private static int TIMEOUT_READ_MS = 15000;
   // private URL url;
    private HttpURLConnection connection;


    public ByteArrayOutputStream readData(String url_str)
    {
        URL url = null;

        try
        {
            url = new URL(url_str);
            Log.i("tags", "URL: " + url.toString());

            connection = (HttpURLConnection) url.openConnection();
            int response = connection.getResponseCode();

            Log.i("tags","RESPONESE: " + Integer.toString(response));

            if (response == HttpURLConnection.HTTP_OK)
            {
                connection.setConnectTimeout(TIMEOUT_CONNECTION_MS);
                connection.setReadTimeout(TIMEOUT_READ_MS);
            }
        }
        catch(MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        InputStream input_stream = null;

        try
        {
            input_stream = url.openStream();
            //output = new ByteArrayOutputStream();
            byte[] buffer = IOUtils.toByteArray(input_stream);
            output.write(buffer, 0, buffer.length);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(input_stream != null)
            {
                try
                {
                    input_stream.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }

            if(connection != null)
            {
                connection.disconnect();
            }
        }
        return output;
    }


    public InputStream getInputStream()
    {
        try {
            return connection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
