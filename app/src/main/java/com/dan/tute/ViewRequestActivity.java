package com.dan.tute;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dan.tute.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class ViewRequestActivity extends ActionBarActivity {

    @InjectView(R.id.name) protected TextView mFirstName;
    @InjectView(R.id.offer) protected TextView mOffer;
    @InjectView(R.id.description) protected TextView mDescription;
    @InjectView(R.id.accept_button) protected ImageButton mAccept;

    protected String requestID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_request);

        Intent intent = getIntent();
        ButterKnife.inject(this);

        requestID = intent.getStringExtra("requestID");

        mFirstName.setText(intent.getStringExtra("firstName"));
        mOffer.setText(intent.getStringExtra("offer"));
        mDescription.setText(intent.getStringExtra("description"));

        mAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UpdateRequestActivity().execute();
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

    class UpdateRequestActivity extends AsyncTask<String, String, String> {
        protected void onPreExecute(){
            super.onPreExecute();
        }

        protected String doInBackground(String... args) {

            String currentEmail = SessionManager.getLoggedInEmailUser(getApplicationContext());
            Log.d("ADDA", requestID);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("requestID", requestID));
            params.add(new BasicNameValuePair("currentEmail", currentEmail));

            JSONParser jsonParser = new JSONParser();

            JSONObject json = jsonParser.makeHttpRequest(getResources().getString(R.string.accept_request), "POST", params);

            try{
                int success = json.getInt("success");

                if(success == 1){

                    Intent i = getIntent();
                    setResult(100, i);
                    finish();
                }else{
                }
            }catch(JSONException e){
                e.printStackTrace();
            }
            return null;
        }


        protected void onPostExecute(String file_url){

            Intent in = new Intent(getApplicationContext(), RequestActivity.class);
            startActivity(in);

        }
    }
}