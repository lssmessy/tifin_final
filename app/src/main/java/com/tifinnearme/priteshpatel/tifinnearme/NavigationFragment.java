package com.tifinnearme.priteshpatel.tifinnearme;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tifinnearme.priteshpatel.tifinnearme.cutomer.Mytifins;
import com.tifinnearme.priteshpatel.tifinnearme.cutomer.Notifications;
import com.tifinnearme.priteshpatel.tifinnearme.cutomer.Profile;
import com.tifinnearme.priteshpatel.tifinnearme.cutomer.Requests;
import com.tifinnearme.priteshpatel.tifinnearme.cutomer.Search;
import com.tifinnearme.priteshpatel.tifinnearme.cutomer.Settings;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class NavigationFragment extends Fragment implements MyAdapter.ClikListener {

    private RecyclerView recyclerView;
    public static final String PREF_FILE_NAME="testprefs";
    public static final String KEY_USER_LEARNED_DRAWER="User learned drawer";
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout,drawerLayout;
    Toolbar toolbar;
    private boolean mUserLearnedDrawer;//indicate whether user has opened drawer first time or not(
    private boolean mFromSavedInstanceState;
    private View containerView;
    int drawer_frag;
    private MyAdapter myAdapter;
    public NavigationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setUp(drawer_frag,drawerLayout,toolbar);
        mUserLearnedDrawer=Boolean.valueOf(readFromPrefs(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));//false indicates user doesn't know about drawer
        if(savedInstanceState!=null){
            mFromSavedInstanceState=true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout=inflater.inflate(R.layout.fragment_navigation, container, false);//define root for xml
        recyclerView=(RecyclerView)layout.findViewById(R.id.drawer_list);//Register recyclerview
        myAdapter=new MyAdapter(getActivity(),getData());
        myAdapter.setOnClikListener(this);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));//set layout manager before executing as
        // it is the key parameter to show the data

        return layout;
        }

    public static List<DataList> getData(){
        List<DataList> data=new ArrayList<>();
        int icon[]={R.drawable.home,R.drawable.account,R.drawable.arrow_up,R.drawable.bell,R.drawable.tifin_icon,R.drawable.magnify,R.drawable.settings};
        String[] texts={"Home","Profile","Requests","Notifications","My Tifins","Search","Settings"};
        for(int i=0; i<texts.length && i<icon.length; i++)

        {
            DataList ob=new DataList();
            ob.iconId=icon[i];
            ob.iconName=texts[i];
            data.add(ob);
        }
        return  data;
    }
    public void setUp(int drawer_fragment, DrawerLayout drawerLayout, final Toolbar toolbar) {
        //Initialize the drawer layout and toggle
        containerView=getActivity().findViewById(drawer_fragment);
        mDrawerLayout=drawerLayout;
        mDrawerToggle=new ActionBarDrawerToggle(getActivity(),mDrawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if(!mUserLearnedDrawer)
                {
                    mUserLearnedDrawer=true;
                    savedPreferences(getActivity(),KEY_USER_LEARNED_DRAWER,mUserLearnedDrawer+"");
                    //Save this value into our Key to indicate that user has seen drawer
                }
                getActivity().invalidateOptionsMenu();//It creates Actionbar again when drawer toggles


            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if(slideOffset<0.5)
                {
                    toolbar.setAlpha(1-slideOffset);
                }

            }
        };
        if(!mUserLearnedDrawer && !mFromSavedInstanceState)
        {
            mDrawerLayout.openDrawer(containerView);
        }
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        //to show the navigation icon
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();//keep track of drawer open/close activities
            }
        });
    }
    public static void savedPreferences(Context context,String prefName,String prefValue){
        SharedPreferences sharedPrefs=context.getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPrefs.edit();
        editor.putString(prefName,prefValue);
        /*editor.commit();*///It wait for the operation to become successful
        editor.apply();//It doesn't wait for the operation to become successful
    }
    public static String readFromPrefs(Context context,String prefName,String prefDefaultValue){
        SharedPreferences getPrefs=context.getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
        return  getPrefs.getString(prefName,prefDefaultValue);
    }


    @Override
    public void itemClicked(View view, int position) {


        DataList ob= myAdapter.data.get(position);
        MainActivity.toolbar.setTitle(ob.iconName);
        mDrawerLayout.closeDrawers();

        Fragment fragment;

      //  startActivity(new Intent(getActivity(), Subactivity.class));
        FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();

        switch (position){
            case 0:
                fragment=new Map();
                fragmentTransaction.replace(R.id.mainContent,fragment);

                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                startActivity(new Intent(getActivity(),Main_Map.class));

                break;
            case 1:
                fragment=new Profile();
                fragmentTransaction.replace(R.id.mainContent,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case 2:
                fragment=new Requests();
                fragmentTransaction.replace(R.id.mainContent,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case 3:
                fragment=new Notifications();
                fragmentTransaction.replace(R.id.mainContent,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case 4:
                fragment=new Mytifins();
                fragmentTransaction.replace(R.id.mainContent,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case 5:
                fragment=new Search();
                fragmentTransaction.replace(R.id.mainContent,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case 6:
                fragment=new Settings();
                fragmentTransaction.replace(R.id.mainContent,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
        }


    }
}
