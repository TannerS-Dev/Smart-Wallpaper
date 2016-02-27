package com.tanners.smartwallpaper.clarifaidata;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.clarifai.api.RecognitionResult;
import com.squareup.picasso.Picasso;
import com.tanners.smartwallpaper.MainActivity;
import com.tanners.smartwallpaper.R;


import java.util.List;

public class ClarifaiFragment extends Fragment
{
    private ClarifaiData cdata = null;
    private Context context;
    private ImageView image_view;
    private Button selectButton;
    private final String NO_TAGS = "No tags aviable for this image";
    private View view;

    // way to attache current activity (context) to the class
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
    }

    // get layout for fragment
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

       // view = inflater.inflate(R.layout.clarifai_fragment_main, container, false);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        cdata = new ClarifaiData(context);


        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.clarifai_fragment_main, null, false);

        image_view = (ImageView) view.findViewById(R.id.image_view);
        selectButton = (Button) view.findViewById(R.id.select_button);

        selectButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final Intent media_intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // START API OVER NET
                startActivityForResult(media_intent, cdata.getOKCode());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == cdata.getOKCode() && resultCode == Activity.RESULT_OK)
        {
            Uri image = intent.getData();

            if (image != null)
            {
                Picasso.with(context).load(image).centerInside().fit().into(image_view);
                selectButton.setText("Please wait");
                selectButton.setEnabled(false);

                new GetTags().execute(image);
            }
            else
                bottomToast(cdata.getLoadError());
        }
    }

    private class GetTags extends AsyncTask<Uri, Void, RecognitionResult>
    {
        @Override
        protected RecognitionResult doInBackground(Uri... image)
        {
            return cdata.recognizeBitmap(image[0]);
        }

        @Override
        protected void onPostExecute(RecognitionResult result)
        {
            super.onPostExecute(result);

            if (cdata.addTags(result))
            {
                selectButton.setEnabled(true);
                selectButton.setText("Select a photo");
            }
            else
                bottomToast(cdata.getRecError());

            ListView listview = (ListView) view.findViewById(R.id.clarifaitagview);
            List<String> tags = cdata.getTags().getTagList();

            //no tags!
            if(tags == null || (tags.size() == 0))
                noTagsToast(NO_TAGS);
            else
            {
                ClarifaiTagAdapter adapter = new ClarifaiTagAdapter(context, R.layout.clarifai_tags_layout, tags);
                listview.setAdapter(adapter);
            }
        }

        private void noTagsToast(String str)
        {
            Toast toast = Toast.makeText(context, str, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM | Gravity.LEFT, 0, 0);
            toast.show();
        }
    }

    private void bottomToast(String str)
    {
        Toast toast = Toast.makeText(context, str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM | Gravity.LEFT, 0, 0);
        toast.show();
    }


}
