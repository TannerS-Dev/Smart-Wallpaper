package com.tanners.smartwallpaper.firebase;

import android.content.Context;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FireBaseUtil
{
    private Context context;
    private Firebase fb;
    private final String FIREBASE_HOME = "https://smartwallpaper.firebaseio.com/";
    private final String PARENT = "tags";
    private HashMap<String, HashMap<String, HashMap<String,String>>> root;
    private StringBuilder str;
    private String tag;
    private String flickr_group;

    public FireBaseUtil(Context context)
    {
        Firebase.setAndroidContext(context);
        fb = new Firebase(FIREBASE_HOME);
    }

    private String pickRandomHashValue(String tag, HashMap<String, String> hash)
    {
        Random generator = new Random();
        String[] values = (String[]) hash.values().toArray();
        return values[generator.nextInt(values.length)];
    }

    public String searchGroupByTag(String tag_)
    {
        this.tag = tag_;

        fb.child(PARENT).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                root = snapshot.getValue(HashMap.class);

                str = new StringBuilder();
                HashMap<String, HashMap<String,String>> temp = root.get(PARENT);
                // search keys which are the name of the category
                for(String id : temp.keySet())
                {
                    if(tag.equals(id))
                    {
                        // randomly pick a gallery

                        HashMap<String,String> tag_hash = temp.get(tag);
                        String value = pickRandomHashValue(tag, tag_hash);
                        flickr_group = tag_hash.get(value);
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });

        return flickr_group;
    }

    public String getPhotoGroupIds()
    {
        fb.child(PARENT).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                root = snapshot.getValue(HashMap.class);
                HashMap<String, HashMap<String, String>> temp = root.get(PARENT);
                str = new StringBuilder();

                for(String id : temp.keySet())
                {
                    str.append(id);
                    str.append(",");
                }
                str.deleteCharAt(str.length()-1);
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });

        return str.toString();
    }


}
