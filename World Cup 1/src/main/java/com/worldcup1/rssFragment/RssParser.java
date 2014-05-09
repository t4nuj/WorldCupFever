package com.worldcup1.rssFragment;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import com.worldcup1.R;
import com.worldcup1.MainActivity;

/**
 * Created by Tanuj on 29/4/14.
 */
public class RssParser {
    //String xml;
    String rss_url;
    //List<RssFeedItem> rss_item_list;
    Document document;
    String rss_xml;
    Activity act;


    public static String TAG_CHANNEL = "channel";
    public static String TAG_TITLE = "title";
    public static String TAG_LINK = "link";
    public static String TAG_DESRIPTION = "description";
    private static String TAG_LANGUAGE = "language";
    private static String TAG_ITEM = "item";
    private static String TAG_PUB_DATE = "pubDate";

    public RssParser(String rss_url,Activity activity) throws IOException {
        this.rss_url = rss_url;
        RssGetter rssGetter = new RssGetter(rss_url);
        rss_xml = rssGetter.getRss();
        document  = this.getDom(rss_xml);
        this.act = activity;
    }

    public Document getDom(String xml)
    {
        Document doc = null;
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        try
        {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            InputSource is = new InputSource();
            if (xml!=null)
            {
                is.setCharacterStream(new StringReader(xml));
                doc = (Document) documentBuilder.parse(is);
            }


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;


    }
    public final String getElementValue(Node elem) {
        Node child;
        if (elem != null) {
            if (elem.hasChildNodes()) {
                for (child = elem.getFirstChild(); child != null; child = child
                        .getNextSibling()) {

                    if (child.getNodeType() == Node.TEXT_NODE || ( child.getNodeType() == Node.CDATA_SECTION_NODE)) {
                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }

    /**
     * Getting node value
     *
     *  Element node
     * key  string
     * */
    public String getValue(Element item, String str) {
        NodeList n = item.getElementsByTagName(str);
        return this.getElementValue(n.item(0));
    }

    public RssFeed getRssFeed()
    {
        RssFeed rssFeed = null;
        List<HashMap<String,String>> rssFeedItemList = new ArrayList<HashMap<String, String>>();

        if(rss_xml!=null)
        {
            try
            {
                NodeList nodeList = document.getElementsByTagName(TAG_CHANNEL);
                Element e = (Element) nodeList.item(0);
                String title = this.getValue(e, TAG_TITLE);
                String link = this.getValue(e, TAG_LINK);
                String description = this.getValue(e, TAG_DESRIPTION);
                String language = this.getValue(e, TAG_LANGUAGE);

                rssFeed = new RssFeed(title,language,link,description);

                NodeList nodeList1 = e.getElementsByTagName(TAG_ITEM);
                for (int i = 0; i<nodeList1.getLength(); i++)
                {
                    Element e1 = (Element) nodeList1.item(i);
                    String title1 = this.getValue(e1, TAG_TITLE);
                    String link1 = this.getValue(e1, TAG_LINK);
                    String description1 = "<html>"+this.getValue(e1, TAG_DESRIPTION)+"</html>";

                    RssFeedItem rssFeedItem = new RssFeedItem(title1,description1,link1);
                    HashMap<String, String> temp = new HashMap<String, String>();
                    temp.put(TAG_TITLE,title1);
                    temp.put(TAG_DESRIPTION,description1);
                    temp.put(TAG_LINK,link1);
                    rssFeedItemList.add(temp);
                }

                rssFeed.setItems(rssFeedItemList);



            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else
        {
            //did not get xml response
            Log.v("No response", "No response received : RssParser");
        }
        return rssFeed;

    }

}

