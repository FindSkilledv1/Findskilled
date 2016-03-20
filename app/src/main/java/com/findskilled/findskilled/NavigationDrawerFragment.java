package com.findskilled.findskilled;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragment extends Fragment {
    private View containerView;
    public static final String PREF_FILE_NAME="testpref";
    //key for muserlearned drawer
    public static final String KEY_USER_LEARNED_DRAWER="user_learned_drawer";
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    //just making the user aware of drawer existence
    private boolean mUserlearnedDrawer;
    //fragment displayed for the very first time?
    private boolean mFromeSavedInstanceState;
    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       mUserlearnedDrawer=Boolean.valueOf(readFromPrefrences(getActivity(),KEY_USER_LEARNED_DRAWER,"false"));
        if(savedInstanceState!=null)
        {
            mFromeSavedInstanceState=true;

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
    }
    public void setUp(int fragment_id,DrawerLayout drawerLayout,Toolbar tool)
    {
        containerView=getActivity().findViewById(fragment_id);
       mDrawerLayout=drawerLayout;
        mDrawerToggle=new ActionBarDrawerToggle(getActivity(),drawerLayout,tool,R.string.drawer_open,R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                if(!mUserlearnedDrawer)
                {
                    mUserlearnedDrawer=true;
                    saveToPrefrences(getActivity(),KEY_USER_LEARNED_DRAWER,mUserlearnedDrawer+"");
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }
        };
        if(!mUserlearnedDrawer && !mFromeSavedInstanceState)
        {
            mDrawerLayout.openDrawer(containerView);
        }

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        //for animation indicator from hamburger to arrow
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
             mDrawerToggle.syncState();
            }
        });
    }

    //write sharedprefrences method
public static void saveToPrefrences(Context context,String prefrencName,String prefrencValue)
{
    //  Context.MODE_PRIVATE  current app to modify the content
    SharedPreferences sharedPreferences=context.getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
    SharedPreferences.Editor editor=sharedPreferences.edit();
    editor.putString(prefrencName,prefrencValue);
    editor.apply();
}

    public static String readFromPrefrences(Context context,String prefrencName,String defaultValue)
    {
        //  Context.MODE_PRIVATE  current app to modify the content
        SharedPreferences sharedPreferences=context.getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
       return sharedPreferences.getString(prefrencName,defaultValue);
    }

}
