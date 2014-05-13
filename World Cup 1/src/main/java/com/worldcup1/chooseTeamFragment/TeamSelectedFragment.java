package com.worldcup1.chooseTeamFragment;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.BoringLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.worldcup1.R;
import com.worldcup1.rssFragment.RssFragment;

/**
 * Created by Tanuj on 30/4/14 at 11:16 AM.
 */
public class TeamSelectedFragment extends Fragment {
    String teamName;
    String teamTrivia;
    int teamIndex;
     static int mSupporters;
    TextView supporters;
    public TeamSelectedFragment(String teamName,String teamTrivia,int teamIndex) {
        this.teamName = teamName;
        this.teamTrivia = teamTrivia;
        this.teamIndex = teamIndex;

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView;
        rootView = inflater.inflate(R.layout.team_selected_fragment,container,false);
        TextView tv1 = (TextView) rootView.findViewById(R.id.teamName);
        TextView tv2 = (TextView) rootView.findViewById(R.id.teamTrivia);
        tv1.setText(teamName);
        tv2.setText(teamTrivia);
        supporters = (TextView) rootView.findViewById(R.id.supporters);
        GetSupporters getSupporters = new GetSupporters();
        getSupporters.execute();
//        RssFragment rssFragment = new RssFragment();
//        rssFragment.setRss_url();





        return rootView;
    }
    public void setmSupporters(int mSupporters1)
    {
        mSupporters = mSupporters1;
        supporters.setText(Integer.toString(mSupporters1));
    }
    private class GetSupporters extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {
            ParseQuery<ParseObject> parseObjectParseQuery = ParseQuery.getQuery("Supporters");
            parseObjectParseQuery.getInBackground("JOlsnmKivl", new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject parseObject, ParseException e) {
                    if(e == null)
                    {
                        Log.v("supporters : ",Integer.toString(parseObject.getInt(teamName)));
                        setmSupporters(parseObject.getInt(teamName));
                    }
                }
            });
            return null;
        }
        @Override
        protected void onPostExecute(Void result)
        {

        }

    }
}
