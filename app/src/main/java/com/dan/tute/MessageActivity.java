package com.dan.tute;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.dan.tute.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;

public class MessageActivity extends ListActivity {

    protected ArrayList<HashMap<String,String>> requests = new ArrayList<HashMap<String,String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        ButterKnife.inject(this);

        new LoadMessagesList().execute();

        ListView lv = getListView();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String firstName = ((TextView) view.findViewById(R.id.firstName)).getText().toString();
                String senderEmail = ((TextView) view.findViewById(R.id.senderEmail)).getText().toString();
                String message = ((TextView) view.findViewById(R.id.message)).getText().toString();

                Intent in = new Intent(getApplicationContext(), ViewMessageActivity.class);
                in.putExtra("firstName", firstName);
                in.putExtra("senderEmail", senderEmail);
                in.putExtra("message", message);

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

    class LoadMessagesList extends AsyncTask<String, String, String> {
        protected void onPreExecute(){
            super.onPreExecute();
        }

        protected String doInBackground(String... args) {
            String currentEmail = SessionManager.getLoggedInEmailUser(getApplicationContext());

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("currentEmail", currentEmail));

            JSONParser jsonParser = new JSONParser();

            final JSONObject json = jsonParser.makeHttpRequest(getResources().getString(R.string.get_messages), "POST", params);

            runOnUiThread(new Runnable() {
                //
                @Override
                public void run() {
                    int success;
                    try {
                        success = json.getInt("success");
                        if (success == 1) {

                            JSONArray jsonTutors = json.getJSONArray("messages");

                            for(int i = 0; i < jsonTutors.length(); i++){
                                JSONObject t = jsonTutors.getJSONObject(i);

                                HashMap m = new HashMap<String,String>();
                                m.put("firstName", t.getString("firstName"));
                                m.put("senderEmail", t.getString("senderEmail"));
                                m.put("message", t.getString("message"));

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
                            MessageActivity.this, requests,
                            R.layout.message_list_item, new String[] {"firstName","message", "senderEmail"},
                            new int[] {R.id.firstName,R.id.message,R.id.senderEmail});

                    setListAdapter(adapter);
                }
            });
        }
    }

}
