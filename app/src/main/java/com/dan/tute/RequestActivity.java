package com.dan.tute;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.dan.tute.R;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RequestActivity extends ListActivity {

    protected ArrayList<HashMap<String,String>> requests = new ArrayList<HashMap<String,String>>();

    //@InjectView(R.id.tool_bar) protected Toolbar toolbar;
    @InjectView(R.id.requestButton) protected Button mRequestButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        ButterKnife.inject(this);

        //setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("Active Requests");

        new LoadRequestsList().execute();

        mRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MakeRequestActivity.class);
                startActivity(intent);
            }
        });

        ListView lv = getListView();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String emailClicked = ((TextView) view.findViewById(R.id.email)).getText().toString();
                String firstName = ((TextView) view.findViewById(R.id.firstName)).getText().toString();
                String description = ((TextView) view.findViewById(R.id.description)).getText().toString();
                String offer = ((TextView) view.findViewById(R.id.offer)).getText().toString();
                String requestID = ((TextView) view.findViewById(R.id.requestID)).getText().toString();

                Intent in = new Intent(getApplicationContext(), ViewRequestActivity.class);
                in.putExtra("email", emailClicked);
                in.putExtra("offer", offer);
                in.putExtra("firstName", firstName);
                in.putExtra("description", description);
                in.putExtra("requestID", requestID);

                startActivityForResult(in, 100);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_request, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class LoadRequestsList extends AsyncTask<String, String, String> {
        protected void onPreExecute(){
            super.onPreExecute();
        }

        protected String doInBackground(String... args) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            JSONParser jsonParser = new JSONParser();

            final JSONObject json = jsonParser.makeHttpRequest(getResources().getString(R.string.get_requests), "POST", params);

            runOnUiThread(new Runnable() {
                //
                @Override
                public void run() {
                    int success;
                    try {
                        success = json.getInt("success");
                        if (success == 1) {

                            JSONArray jsonTutors = json.getJSONArray("requests");

                            for(int i = 0; i < jsonTutors.length(); i++){
                                JSONObject t = jsonTutors.getJSONObject(i);

                                HashMap m = new HashMap<String,String>();
                                m.put("firstName", t.getString("firstName"));
                                m.put("email", t.getString("email"));
                                m.put("offer", t.getString("offer"));
                                m.put("description", t.getString("description"));
                                m.put("requestID", t.getString("requestID"));

                                requests.add(m);
                            }

                        } else {

                        }
                    } catch (JSONException e) {
                    }
                }
            });
            return null;
        }


        protected void onPostExecute(String file_url) {
            runOnUiThread(new Runnable() {
                public void run() {

                    ListAdapter adapter = new SimpleAdapter(
                            RequestActivity.this, requests,
                            R.layout.request_list_item, new String[] {"email", "firstName", "offer", "description", "requestID"},
                            new int[] { R.id.email,R.id.firstName,R.id.offer,R.id.description,R.id.requestID});

                    setListAdapter(adapter);
                }
            });
        }
    }

}
