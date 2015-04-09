package com.tifinnearme.priteshpatel.tifinnearme;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by PRITESH on 09-04-2015.
 */
public class Map extends android.support.v4.app.Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try{

        View mapview=inflater.inflate(R.layout.mapview,container,false);
        //main_map.checkLocation();
        return mapview;
    }catch (Exception e){e.printStackTrace();}
        return null;
    }
}
