package com.worldcup1.rssFragment;

/**
 * Created by Tanuj on 29/4/14.
 */
public class RssFeedItem {
    String Title;
    String Description;
    String Link;
    /*ArrayList<String> Categories;
    String EnclosureLink;
    String Tag_Title = "";
    String Tag_Description  = "";
    String Tag_Link = "";
    String Tag_Categories = "";
    String Tag_EnclosureLink = "";*/

    public RssFeedItem(String title, String description, String link) {
        Title = title;
        Description = description;
        Link = link;
    }
}
