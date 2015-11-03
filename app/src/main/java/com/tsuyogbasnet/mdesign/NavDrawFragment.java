package com.tsuyogbasnet.mdesign;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavDrawFragment extends Fragment {
    private static final String PREF_FILE_NAME="mDesign";
    private static final String KEY_USER_IS_AWARE="isAware";
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private View containerView;
    private boolean userIsAware;
    private boolean isFromSavedInstance;
    public NavDrawFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userIsAware = Boolean.getBoolean(readSharedPref(getActivity(), KEY_USER_IS_AWARE, "false"));
        if (savedInstanceState != null){
            isFromSavedInstance = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nav_draw, container, false);
    }


    public void setUpFragment(int fragmentId, DrawerLayout layout, Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = layout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), layout, toolbar,R.string.drawer_open, R.string.drawer_open){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isFromSavedInstance){
                    userIsAware = true;
                    saveToSharedPrefs(getActivity(), KEY_USER_IS_AWARE, userIsAware+"");
                }
                getActivity().invalidateOptionsMenu();

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }
        };
        if (!userIsAware && !isFromSavedInstance){
            mDrawerLayout.openDrawer(containerView);
        }
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }
    public static void saveToSharedPrefs(Context context, String key, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor  editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();

    }
    public static String readSharedPref(Context context, String key, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, value);
    }
}
