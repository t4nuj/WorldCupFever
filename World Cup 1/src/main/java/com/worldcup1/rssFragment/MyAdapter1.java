package com.worldcup1.rssFragment;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.worldcup1.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Tanuj on 29/4/14.
 */
public class MyAdapter1 extends ArrayAdapter<HashMap<String,String>> {
    private Context context;
    private List<HashMap<String,String>> objects;

    public MyAdapter1(Context context, int resource, List<HashMap<String, String>> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.rss_list_view, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textView1 = (TextView) convertView.findViewById(R.id.title);
            viewHolder.textView2 = (TextView) convertView.findViewById(R.id.description);
            viewHolder.textView3 = (TextView) convertView.findViewById(R.id.link);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView1.setText(objects.get(position).get(RssParser.TAG_TITLE));
        viewHolder.textView2.setText(Html.fromHtml(objects.get(position).get(RssParser.TAG_DESRIPTION)));
        viewHolder.textView3.setText(objects.get(position).get(RssParser.TAG_LINK));

        return convertView;

    }

    public class ViewHolder
    {
        TextView textView1,textView2,textView3;
        ViewHolder()
        {

        }
    }
}

