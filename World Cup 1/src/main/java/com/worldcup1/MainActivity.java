package com.worldcup1;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.revmob.RevMob;
import com.revmob.RevMobTestingMode;
import com.worldcup1.chooseTeamFragment.ChooseTeamFragment;
import com.worldcup1.chooseTeamFragment.TeamSelectedFragment;
import com.worldcup1.fixturesFragment.FixtureFragment;
import com.worldcup1.rssFragment.RssFragment;

import org.json.JSONException;
import org.json.JSONObject;

import javax.xml.transform.Result;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private String mTeamUrl = "http://www.fifa.com/worldcup/news/rss.xml";
    //RevMob revmob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        AdFragment adFragment = (AdFragment) getSupportFragmentManager().findFragmentById(R.id.container2);
//        getSupportFragmentManager().beginTransaction().add(R.id.container2,adFragment).commit();

        if (adFragment != null && adFragment.isInLayout()) {
            adFragment.addBanner();
        }
        InitializeParse initializeParse = new InitializeParse();
        initializeParse.execute();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments

        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (position)
        {
            case 0:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                        .commit();
                break;
            case 1:
                FixtureFragment fixtureFragment = new FixtureFragment();
                fragmentManager.beginTransaction().replace(R.id.container,fixtureFragment ).commit();
                this.onSectionAttached(2);
                break;
            case 2:
            {
                SharedPreferences sharedPreferences = getSharedPreferences("Selected_Team",0);
                if ( sharedPreferences.contains("isTeamSelected"))
                {
                    fragmentManager.beginTransaction().replace(R.id.container,new TeamSelectedFragment(sharedPreferences.getString("mTeamName",""),
                            sharedPreferences.getString("mTeamTrivia",""),
                            sharedPreferences.getInt("mTeamIndex",MODE_PRIVATE)))
                            .commit();
                }
                else {
                    ChooseTeamFragment chooseTeamFragment = new ChooseTeamFragment();
                    fragmentManager.beginTransaction().replace(R.id.container,chooseTeamFragment).commit();
                }
                this.onSectionAttached(3);

            }
            break;
            case 3:
            {RssFragment rssFragment = new RssFragment();
                rssFragment.setRss_url(mTeamUrl);
                fragmentManager.beginTransaction()
                        .replace(R.id.container,rssFragment)
                        .commit();
            this.onSectionAttached(4);}
            break;

        }

    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = "FIFA.com News";
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }
    private class InitializeParse extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {
            Parse.initialize(MainActivity.this,"Xbj97Y9tSrByJKkTLzfBlG8u4heGa78N1bRNvHOi","ddOlzDeD1IEslrPVhhQqZSf1EeAwn7WFtZvDnAkQ");
            //revmob = RevMob.start(MainActivity.this);
            //revmob.setTestingMode(RevMobTestingMode.WITH_ADS); // with this line, RevMob will always deliver a sample ad

            /*ParseQuery<ParseObject> parseObjectParseQuery = ParseQuery.getQuery("MyJson");
            final JSONObject[] jsonObjectTemp = {new JSONObject()};
            GetCallback<ParseObject> getCallback = new GetCallback<ParseObject>() {
                JSONObject jsonObject;

                @Override
                public void done(ParseObject parseObject, ParseException e) {
                    final String json = parseObject.getString("Json");
                    try {
                        jsonObjectTemp[0] = new JSONObject(json);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    Log.v("String Json",json);

                }

                public JSONObject getJsonObject()
                {
                    return jsonObject;
                }
            };
            parseObjectParseQuery.getInBackground("nxXc6G0gDm",getCallback);

            Log.v("Json > :", jsonObjectTemp[0].toString());*/
            return null;
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            Toast toast = Toast.makeText(getApplicationContext(),"ParseInitialized",25);
            toast.show();

        }
    }
}
