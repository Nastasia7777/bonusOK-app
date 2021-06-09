package org.hse.bonusokapplication;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RootActivity extends AppCompatActivity {

    private static final String TAG = RootActivity.class.getName();
    private static ArrayList<Activity> activities=new ArrayList<Activity>();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        activities.add(this);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        activities.remove(this);
    }

    public static void finishAll()
    {
        for(Activity activity:activities)
            activity.finish();
    }

    public static void finishActivity(String name){
        for(Activity activity: activities) {
            String s = activity.getComponentName().toString();
            if (activity.getComponentName().toString().contains(name))
            activity.finish();
        }
    }
}
