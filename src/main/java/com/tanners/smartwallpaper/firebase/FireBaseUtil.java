package com.tanners.smartwallpaper.firebase;


import android.content.Context;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class FireBaseUtil
{
    private Firebase firebase;
    private HashMap<String, String> tags;

    public FireBaseUtil(Context con, String dir)
    {
        Firebase.setAndroidContext(con);
        firebase = new Firebase(dir);

        firebase.child("tags").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                tags = snapshot.getValue(HashMap.class);
            }

            @Override
            public void onCancelled(FirebaseError error) {
                Log.i("firebase", error.toString());
            }
        });
    }

    public HashMap<String, String> getTags()
    {
        return tags;
    }

    public void setTags(HashMap<String, String> tags)
    {
        this.tags = tags;
    }
}



