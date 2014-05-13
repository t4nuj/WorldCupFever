package com.worldcup1.fixturesFragment;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.worldcup1.MainActivity;
import com.worldcup1.R;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Created by Tanuj on 4/5/14 at 12:47 PM.
 */
public class FixtureFragment extends Fragment {
    ExpandableListView listView ;
    List<DateFixture> fixtures;
    public FixtureFragment()
    {}
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container ,Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fixture_fragment_layout,container,false);

        listView = (ExpandableListView) rootView.findViewById(R.id.fixturesListView);
//        MyAdapter2 myAdapter2 = new MyAdapter2(getActivity(),R.layout.fixture_fragment_list_item,fixtures);
//        listView.setAdapter(myAdapter2);
        PopulateView populateView = new PopulateView();
        populateView.execute();

        return rootView;
    }

   /* public class MyAdapter2 extends ArrayAdapter<DateFixture>
    {
        Context context;
        int resource;
        List<DateFixture> fixtures;

        public MyAdapter2(Context context, int resource, List<DateFixture> objects) {
            super(context, resource, objects);
            this.context = context;
            this.resource = resource;
            this.fixtures = objects;
        }


        @Override
        public View getView (int position,View convertView, ViewGroup parent)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ViewHolder viewHolder;
            if (convertView == null)
            {
                convertView = inflater.inflate(resource,parent,false);
                viewHolder = new ViewHolder();
                viewHolder.textView1 = (TextView) convertView.findViewById(R.id.date);
                viewHolder.textView2 = (TextView) convertView.findViewById(R.id.matches);
                convertView.setTag(viewHolder);

            }
            else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            Log.v("Here"," here");

            viewHolder.textView1.setText(fixtures.get(position).Date);
            viewHolder.textView2.setText((CharSequence) fixtures.get(position).matches.toString());

            return convertView;
        }
        public class ViewHolder
        {
            TextView textView1,textView2;
            ViewHolder()
            {

            }
        }
    }*/
    public class MyAdapter3 extends BaseExpandableListAdapter
    {   Context context;
        int resGroup,resChild;
        List<DateFixture> fixtures;
        public  MyAdapter3(Context context,int resGroup,int resChild,List<DateFixture> fixtures)
        {
            this.context =context;
            this.resChild = resChild;
            this.resGroup = resGroup;
            this.fixtures = fixtures;

        
        }

        @Override
        public int getGroupCount() {
            return  fixtures.size();
        }

        @Override
        public int getChildrenCount(int i) {
            return fixtures.get(i).matches.size();
        }

        @Override
        public Object getGroup(int i) {
            return fixtures.get(i).Date;
        }

        @Override
        public Object getChild(int i, int i2) {
            return fixtures.get(i).matches.get(i2);
        }

        @Override
        public long getGroupId(int i) {
            return i;
        }

        @Override
        public long getChildId(int i, int i2) {
            return i2;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
            ViewHolderGroup viewHolderGroup;
            if (view == null)
            {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(resGroup,viewGroup,false);
                viewHolderGroup = new ViewHolderGroup();
                if (view != null) {
                    viewHolderGroup.textView1 = (TextView) view.findViewById(R.id.date);
                }
                view.setTag(viewHolderGroup);

            }
            else
            {
                viewHolderGroup = (ViewHolderGroup) view.getTag();
            }
            viewHolderGroup.textView1.setText(fixtures.get(i).Date);
            return view;
        }

        @Override
        public View getChildView(int i, int i2, boolean b, View view, ViewGroup viewGroup) {
            ViewHolderChild viewHolderChild;
            if (view == null)
            {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(resChild,viewGroup,false);
                viewHolderChild  = new ViewHolderChild();

                viewHolderChild.textView1 = (TextView) view.findViewById(R.id.textViewTeam1);
                viewHolderChild.textView2 = (TextView) view.findViewById(R.id.textViewTeam2);
                viewHolderChild.textView3 = (TextView) view.findViewById(R.id.textViewGroup);
                Log.v("Here : here"," here");


                view.setTag(viewHolderChild);

            }
            else
            {
                viewHolderChild = (ViewHolderChild) view.getTag();
            }
            viewHolderChild.textView1.setText(fixtures.get(i).matches.get(i2).team1);
            viewHolderChild.textView2.setText(fixtures.get(i).matches.get(i2).team2);
            viewHolderChild.textView3.setText("Group  - "+fixtures.get(i).matches.get(i2).group);

            return view;
        }

        @Override
        public boolean isChildSelectable(int i, int i2) {
            return true;
        }
    }
    public class ViewHolderGroup
    {
        TextView textView1;
        ViewHolderGroup()
        {

        }
    }
    public class ViewHolderChild
    {
        TextView textView1,textView2,textView3;
        ViewHolderChild()
        {

        }
    }

    public class PopulateView extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {
            FixtureGetter fixtureGetter = new FixtureGetter(getActivity());
            fixtures  = fixtureGetter.parseJsonObject();
            return null;
        }

        @Override
        public void onPostExecute(Void result)
        {
            MyAdapter3 myAdapter3 = new MyAdapter3(getActivity(),R.layout.fixture_fragment_list_item,R.layout.fixture_fragment_list_child,fixtures);
            listView.setAdapter(myAdapter3);
        }

    };

}
