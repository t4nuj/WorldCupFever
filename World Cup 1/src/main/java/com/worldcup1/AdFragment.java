package com.worldcup1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by Tanuj on 11/5/14 at 5:01 PM.
 */
public class AdFragment extends Fragment {
     @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceStata)
     {
         View rootView = inflater.inflate(R.layout.ad_fragment,container,false);

         return rootView;


     }


}
