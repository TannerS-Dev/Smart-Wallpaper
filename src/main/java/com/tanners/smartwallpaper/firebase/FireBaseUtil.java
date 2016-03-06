package com.tanners.smartwallpaper.firebase;


import android.app.Activity;
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
    private HashMap<String, String> child;

    //public FireBaseUtil(FireBase base, Context con, String dir)
    public FireBaseUtil(Activity con, String dir)
    {
        //Firebase.setAndroidContext(con);
        firebase = new Firebase(dir);
    }

    public HashMap<String, String> getChild(String key)
    {
        Log.i("tags", "test" );

        firebase.child(key).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                child = snapshot.getValue(HashMap.class);

                Log.i("tags", "is null: " + child.toString());
            }

            @Override
            public void onCancelled(FirebaseError error) {
                Log.i("firebase", error.toString());
            }
        });

        return child;
    }
}



