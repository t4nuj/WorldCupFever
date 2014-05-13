package com.worldcup1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.revmob.RevMob;
import com.revmob.RevMobTestingMode;
import com.revmob.ads.banner.RevMobBanner;


/**
 * Created by Tanuj on 11/5/14 at 5:01 PM.
 */
public class AdFragment extends Fragment {

    RevMob revMob;
    RevMobBanner banner;
    AdView adView;

    @Override
     public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceStata)
     {
         View rootView = inflater.inflate(R.layout.ad_fragment,container,false);

         return rootView;


     }
    public void addBanner()
    {
//      revMob = RevMob.start(getActivity());
//        revMob.setTestingMode(RevMobTestingMode.WITH_ADS);
//        banner = revMob.createBanner(getActivity());
        View view = (View) getView().findViewById(R.id.p1);
        adView = new AdView(getActivity());
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-9019119684545766/6857439134");

        ((ViewGroup) view).addView(adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("A11589D53CC07A3741607F3F273223EA")
                .build();
        adView.loadAd(adRequest);
    }
    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    /** Called before the activity is destroyed. */
    @Override
    public void onDestroy() {
        // Destroy the AdView.
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }


}
