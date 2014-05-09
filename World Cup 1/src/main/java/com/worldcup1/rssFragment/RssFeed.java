package com.worldcup1.rssFragment;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Tanuj on 29/4/14.
 */
public class RssFeed {
    String Language;
    String Title;
    String Link;
    String Description;
    //List<RssFeedItem> Items;
    List<HashMap<String,String>> Items;

    public RssFeed(String title, String language, String link, String description) {
        Title = title;
        Language = language;
        Link = link;
        Description = description;
    }
    public void setItems(List<HashMap<String,String>> items)
    {
        Items = items;
    }
}

