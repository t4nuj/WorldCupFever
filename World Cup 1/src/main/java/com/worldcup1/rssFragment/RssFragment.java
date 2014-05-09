package com.worldcup1.rssFragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.worldcup1.R;

import java.io.IOException;

/**
 * Created by Tanuj on 29/4/14.
 */

public class RssFragment extends Fragment{
    TextView textView;
    RssFeed rssFeed;
    ListView listView;
    String rss_url;
    static RssFeed rssFeedCached ;

    public RssFragment() {

    }
    public void setRss_url(String rssUrl)
    {
        this.rss_url = rssUrl;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        if (savedInstanceState==null)
        {
            View rootView = inflater.inflate(R.layout.fragment_rss, container, false);
            textView = (TextView) rootView.findViewById(R.id.container);
            GetRssInBackground getRssInBackground = new GetRssInBackground();
            getRssInBackground.execute();
            return rootView;
        }
        else return super.onCreateView(inflater,container,savedInstanceState);

    }

    public class GetRssInBackground extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            RssParser rssParser = null;
            try {
                rssParser = new RssParser(rss_url,getActivity());
            } catch (IOException e) {
                e.printStackTrace();
            }
            rssFeed = rssParser.getRssFeed();






            return null;
        }

        @Override
        public void onPostExecute(Void result)
        {

            super.onPostExecute(result);

            if (rssFeed!=null)
            {   rssFeedCached = rssFeed;
                textView.setText(rssFeed.Title);
                listView = (ListView) getView().findViewById(R.id.listView1);

            /*ListAdapter adapter;
            adapter = new SimpleAdapter(getActivity(), rssFeed.Items ,R.layout.rss_list_view_layout,new String[] {RssParser.TAG_TITLE,RssParser.TAG_DESRIPTION,RssParser.TAG_LINK},new int[] {R.id.title,R.id.description,R.id.link} );
            listView.setAdapter(adapter);*/
                MyAdapter1 myAdapter1 = new MyAdapter1(getActivity(),R.layout.rss_list_view,rssFeed.Items);
                listView.setAdapter(myAdapter1);
            }
            else
            {
                Toast toast = Toast.makeText(getActivity().getApplicationContext(),"No Internet Connection",25);
                toast.show();
                if (rssFeedCached != null)
                {
                    textView.setText(rssFeedCached.Title);
                    listView = (ListView) getView().findViewById(R.id.listView1);
                    MyAdapter1 myAdapter1 = new MyAdapter1(getActivity(),R.layout.rss_list_view,rssFeedCached.Items);
                    listView.setAdapter(myAdapter1);
                }
            }
        }
    }

}