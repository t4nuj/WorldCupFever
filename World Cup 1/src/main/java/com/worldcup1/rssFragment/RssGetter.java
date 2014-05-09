package com.worldcup1.rssFragment;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Tanuj on 29/4/14.
 */
public class RssGetter {
    String rss_url;

    public RssGetter(String rss_url) {
        this.rss_url = rss_url;
    }

    public String getRss() throws IOException {
        /*HttpResponse httpResponse = null;
        HttpEntity httpEntity = null;
        HttpClient httpClient = new DefaultHttpClient();*/
        String Response = null;
        InputStream in = null;

        try {   URL url = new URL(rss_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection)  url.openConnection();
            byte[] bytes = new byte[1024];
            in = httpURLConnection.getInputStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            for (int count; (count = in.read(bytes))!=-1;)
            {
                out.write(bytes,0,count);
            }

            byte[] byteResponse = out.toByteArray();
            Response = new String(byteResponse,"UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.d("lelel","  lel");
       if(Response != null) Log.d("response >", Response);

        /*HttpGet httpGet = new HttpGet("http://www.fifa.com/rss/index.xml");*/
        /*try {
            httpResponse = httpClient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        httpEntity = httpResponse.getEntity();*/


        return  Response;


    }
}
