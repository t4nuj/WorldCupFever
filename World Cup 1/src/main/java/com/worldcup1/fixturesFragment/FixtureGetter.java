package com.worldcup1.fixturesFragment;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.worldcup1.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Tanuj on 4/5/14 at 12:01 PM.
 */
public class FixtureGetter {
    private static String JsonRaw = null;
    Context context;
    FixtureGetter(Context context)
    {
        this.context = context;
    }

    public void getJsonRaw() throws IOException {
        InputStream inputStream = context.getResources().openRawResource(R.raw.fixtures);
        int ctr;
        OutputStream outputStream = new ByteArrayOutputStream();
        ctr = inputStream.read();
        while(ctr!=-1)
        {
           outputStream.write(ctr);
            ctr  =inputStream.read();
        }
        JsonRaw = outputStream.toString();
//        Log.v("JsonRaw",JsonRaw);
    }

    public JSONObject parseJsonRaw() throws IOException, JSONException {
        getJsonRaw();
        JSONObject jsonObject = new JSONObject(JsonRaw);
        return jsonObject;
    }

    public List<DateFixture> parseJsonObject()
    {
        List<DateFixture> fixtures = new ArrayList<DateFixture>();
        JSONObject jsonObject = null;
        try {
            jsonObject = parseJsonRaw();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray array = null;
        if (jsonObject != null) {
            try {
                array = jsonObject.getJSONArray("Fixtures");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (array!=null)
        {
            for (int i = 0 ; i < array.length(); i++)
            {   JSONObject element = null;
                try {
                    element = array.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (element != null )
                {
                    Iterator<String> iterator = element.keys();
                    if(iterator.hasNext())
                    {   String key = iterator.next();
                        try {
                            DateFixture dateFixture = new DateFixture();
                            dateFixture.Date = key;
                            dateFixture.matches = new ArrayList<Matches>();
                            Log.v("JsonParse : ","Date "+key);
                            JSONArray matchArray = element.getJSONArray(key);
                            for (int c = 0; c < matchArray.length(); c++)
                            {   JSONObject matchArrayElement = matchArray.getJSONObject(c);
                                Matches match = new Matches();
                                match.group = matchArrayElement.getString("group");
                                match.team1 = matchArrayElement.getString("team1");
                                match.team2 = matchArrayElement.getString("team2");
                                match.venue = matchArrayElement.getString("venue");
                                Log.v("JsonParse : ","match "+match.group+" "+match.team1+" "+match.team2);
                                dateFixture.matches.add(match);
                            }
//                            Log.v("Date : ",key);
//                            Log.v("Matches: ", dateFixture.matches.toString());
                            fixtures.add(dateFixture);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }

        return fixtures;
    }
}
