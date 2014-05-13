package com.worldcup1.chooseTeamFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.worldcup1.R;

/**
 * Created by Tanuj on 30/4/14.
 */
public class ChooseTeamFragment extends Fragment {
    ListView listView;
    teamClass[] team;
    String[] teams;
    static boolean mteamSelected;
    int mSupporters;
    teamClass mTeam;
    AlertDialog alertDialog =null;
    AlertDialog alertDialog2 = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.choose_fragment_layout,container,false);
        setTeam();
        listView = (ListView) rootView.findViewById(R.id.listView2);
        listView.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.list_choose_team, R.id.label, teams));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int j, long l) {


                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                //builder.setView();
               // builder.setView(getLayoutInflater(savedInstanceState).inflate())
                builder.setMessage("Number of Supporters of "+teams[j]+" is "+getSupporters(teams[j]))
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mteamSelected = true;

                                SharedPreferences settings;
                                settings = getActivity().getSharedPreferences("Selected_Team", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = settings.edit();
                                editor.putBoolean("isTeamSelected", true);
                                editor.putInt("mTeamIndex", j);
                                editor.putString("mTeamName", teams[j]);
                                editor.putString("mTeamTrivia", team[j].trivia);
                                editor.commit();

                                UpdateSupporters updateSupporters = new UpdateSupporters(teams[j]);
                                updateSupporters.execute();

                                TeamSelectedFragment teamSelectedFragment = new TeamSelectedFragment(teams[j], team[j].trivia, j);

                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, teamSelectedFragment).commit();
                            }
                        })
                .setNegativeButton("choose another", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //do nothing;
                    }
                });
                alertDialog2 = builder.create();
                alertDialog2.show();



            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Choose Team Message")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        alertDialog = builder.create();
        alertDialog.show();

        //BackgroundTasks1 backgroundTasks1 = new BackgroundTasks1();
//        backgroundTasks1.execute();


        return rootView;
    }
    @Override
    public void onPause()
    {
        super.onPause();
        if(alertDialog!= null && alertDialog.isShowing()) alertDialog.dismiss();
        if(alertDialog2!=null && alertDialog2.isShowing()) alertDialog2.dismiss();
    }

    public void setTeam()
    {   team = new teamClass[32];
        for (int i=0;i<32;i++) {
            team[i] = new teamClass();
        }


        team[0].name = "Algeria";
        team[0].trivia = "Fifa Ranking:32\n" +
                "Previous best Performance: Group stage (1982, 1986, 2010)";

        team[1].name = "Argentina";
        team[1].trivia = "Fifa Ranking:3\n" +
                "Previous best Performance: Winners (1978, 1986)\t\n";

        team[2].name = "Australia";
        team[2].trivia = "Fifa Ranking:57\n" +
                "Previous best Performance: Round of 16 (2006)";

        team[3].name = "Belgium";
        team[3].trivia = "Fifa Ranking:16\n" +
                "Previous best Performance: - (First Apperance)";

        team[4].name = "Bosnia";
        team[4].trivia = "Fifa Ranking:57\n" +
                "Previous best Performance: Round of 16 (2006)";

        team[5].name = "Brazil";
        team[5].trivia = "Fifa Ranking:11\n" +
                "Previous best Performance: Winners (1958, 1962, 1970, 1994, 2002)";

        team[6].name = "Cameroon";
        team[6].trivia = "Fifa Ranking:59\n" +
                "Previous best Performance: Quarter-finals (1990)";

        team[7].name = "Chile";
        team[7].trivia = "Fifa Ranking:12\n" +
                "Previous best Performance: Third place (1962)";

        team[8].name = "Columbia";
        team[8].trivia = "Fifa Ranking:4\n" +
                "Previous best Performance: Round of 16 (1990)\n";

        team[9].name = "Costa";
        team[9].trivia = "Fifa Ranking:31\n" +
                "Previous best Performance: Round of 16 (1990)";

        team[10].name = "Croatia";
        team[10].trivia = "Fifa Ranking:18\n" +
                "Previous best Performance: Third place (1998)";

        team[11].name = "Ecuador";
        team[11].trivia = "Fifa Ranking:22\n" +
                "Previous best Performance: Round of 16 (2006)";

        team[12].name = "France";
        team[12].trivia = "Fifa Ranking:21\n" +
                "Previous best Performance: Winners (1998)";

        team[13].name = "Germany";
        team[13].trivia = "Fifa Ranking:2\n" +
                "Previous best Performance: Winners (1954, 1974, 1990)\n";

        team[14].name = "Ghana";
        team[14].trivia = "Fifa Ranking:23\n" +
                "Previous best Performance: Quarter-finals (2010)";

        team[15].name = "Greece";
        team[15].trivia = "Fifa Ranking:15\n" +
                "Previous best Performance: Group stage (1994, 2010)";

        team[16].name = "Honduras";
        team[16].trivia = "Fifa Ranking:34\n" +
                "Previous best Performance: Group stage (1982, 2010)";

        team[17].name = "Iran";
        team[17].trivia = "Fifa Ranking:49\n" +
                "Previous best Performance: Group stage (1978, 1998, 2006)\t";

        team[18].name = "Italy";
        team[18].trivia = "Fifa Ranking:9\n" +
                "Previous best Performance: Winners (1934, 1938, 1982, 2006)";

        team[19].name = "Ivory";
        team[19].trivia = "Fifa Ranking:17\n" +
                "Previous best Performance: Group stage (2006, 2010)";

        team[20].name = "Japan";
        team[20].trivia = "Fifa Ranking:44\n" +
                "Previous best Performance: Round of 16 (2002, 2010)";

        team[21].name = "Korea";
        team[21].trivia = "Fifa Ranking:56\n" +
                "Previous best Performance: Fourth place (2002)";

        team[22].name = "Mexico";
        team[22].trivia = "Fifa Ranking:24\n" +
                "Previous best Performance: Quarter-finals (1970, 1986)";

        team[23].name = "Netherlands";
        team[23].trivia = "Fifa Ranking:8\n" +
                "Previous best Performance: Runners-up (1974, 1978, 2010)";

        team[24].name = "Nigeria";
        team[24].trivia = "Fifa Ranking:33\n" +
                "Previous best Performance: Round of 16 (1994, 1998)";

        team[25].name = "Portugal";
        team[25].trivia = "Fifa Ranking:14\n" +
                "Previous best Performance: Third place (1966)";

        team[26].name = "Russia";
        team[26].trivia = "Fifa Ranking:19\n" +
                "Previous best Performance: Fourth place (1966)";

        team[27].name = "Spain";
        team[27].trivia = "Fifa Ranking:1\n" +
                "Previous best Performance: Winners (2010)";

        team[28].name = "Switzerland";
        team[28].trivia = "Fifa Ranking:7\n" +
                "Previous best Performance: Quarter-finals (1934, 1938, 1954)";

        team[29].name = "UK";
        team[29].trivia = "Fifa Ranking:10\n" +
                "Previous best Performance: Winners (1966)";

        team[30].name = "Uruguay";
        team[30].trivia = "Fifa Ranking:6\n" +
                "Previous best Performance: Winners (1930, 1950)";

        team[31].name = "USA";
        team[31].trivia = "Fifa Ranking:13\n" +
                "Previous best Performance: Third place (1930)";


        teams = new String[32];
//        ParseObject parseObject = new ParseObject("Supporters");

        for(int i=0;i<32;i++)
        {
            teams[i] = new String();
            teams[i] = team[i].name;

//            parseObject.put(teams[i], 0);
        }
//        parseObject.saveEventually();

    }
    public void updateSupporters(final String team) {

    }
    public void setmSupporters(int mSupporters1)
    {
        this.mSupporters = mSupporters1;
    }

    private class UpdateSupporters extends AsyncTask<Void,Void,Void>
    {   String team;
        UpdateSupporters(String team)
        {
         this.team = team;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ParseQuery<ParseObject> parseObjectParseQuery = ParseQuery.getQuery("Supporters");
            parseObjectParseQuery.getInBackground("JOlsnmKivl",new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject parseObject, ParseException e) {
                    parseObject.increment(team);
                    setmSupporters(parseObject.getInt(team));
                    parseObject.saveEventually();

                }
            });
            return null;
        }
    }

    public class BackgroundTasks1 extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {
            //listView = (ListView) getView().findViewById(R.id.listView2);

            return null;
        }
    }

    public String getSupporters(final String teamName)
    {   final String i = "";
        ParseQuery<ParseObject> parseObjectParseQuery = ParseQuery.getQuery("Supporters");
        parseObjectParseQuery.getInBackground("JOlsnmKivl", new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if(e == null)
                {
                    //Log.v("supporters : ", Integer.toString(parseObject.getInt(teamName)));
                    i.concat(Integer.toString(parseObject.getInt(teamName)));
                }
            }
        });
        return  i;
    }




}
