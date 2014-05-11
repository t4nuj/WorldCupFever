package com.worldcup1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.revmob.RevMob;
import com.revmob.RevMobTestingMode;
import com.revmob.ads.banner.RevMobBanner;


/**
 * Created by Tanuj on 11/5/14 at 5:01 PM.
 */
public class AdFragment extends Fragment {

    RevMob revMob;
    RevMobBanner banner;

    @Override
     public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceStata)
     {
         View rootView = inflater.inflate(R.layout.ad_fragment,container,false);

         return rootView;


     }
    public void addBanner()
    {   revMob = RevMob.start(getActivity());
        revMob.setTestingMode(RevMobTestingMode.WITH_ADS);
        banner = revMob.createBanner(getActivity());
        View view = (View) getView().findViewById(R.id.p1);
        ((ViewGroup) view).addView(banner);
    }


}
