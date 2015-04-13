package com.dan.tute;

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
import android.widget.RatingBar;
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

public class ProfileActivity extends ActionBarActivity {

    private String currentEmail;
    private String profileEmail;

    private String url_load_tutor_profile = "http://68.119.36.255/tute/load_tutor_profile.php";
    private String url_send_request = "http://68.119.36.255/tute/requestEmailer.php";

    @InjectView(R.id.profEmailAddress) protected TextView mEmail;
    @InjectView(R.id.profMajor) protected TextView mMajor;
    @InjectView(R.id.profDesc) protected TextView mDesc;
    @InjectView(R.id.ratingBar) protected RatingBar mRating;
    @InjectView(R.id.sendEmailText) protected TextView mSendEmail;

    protected JSONParser jsonParser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        profileEmail = intent.getStringExtra("email");

        currentEmail = SessionManager.getLoggedInEmailUser(getApplicationContext());

        ButterKnife.inject(this);

        mEmail.setText(profileEmail);

        jsonParser = new JSONParser();

        new LoadProfileInformationActivity().execute();

        mSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SendEmailRequestActivity().execute();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
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

    class LoadProfileInformationActivity extends AsyncTask<String, String, String> {
        protected void onPreExecute(){
            super.onPreExecute();
        }

        protected String doInBackground(String... args) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("email", profileEmail));

            final JSONObject json = jsonParser.makeHttpRequest(url_load_tutor_profile, "POST", params);

            runOnUiThread(new Runnable() {
                //
                @Override
                public void run() {
                    int success;
                    try {
                        success = json.getInt("success");
                        if (success == 1) {
                            if(!json.getString("bio").equals("null")) {
                                mDesc.setText(json.getString("bio"));
                            }
                            if(!json.getString("major").equals("null")) {
                                mMajor.setText(json.getString("major"));
                            }
                            //if(!json.getString("name").equals("null")) {
                            //    mEmail.setText(json.getString("name"));
                            //}

                        } else {
                            //phpMessage = json.getString("message");
                        }
                    } catch (JSONException e) {
                        Log.d("Boogityboo", "bby");
                    }
                }
            });
            return null;
        }


        protected void onPostExecute(String file_url) {

        }
    }

    class SendEmailRequestActivity extends AsyncTask<String, String, String> {
        protected void onPreExecute(){
            super.onPreExecute();
        }

        protected String doInBackground(String... args) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("emailTo", profileEmail));
            params.add(new BasicNameValuePair("emailFrom", currentEmail));
            params.add(new BasicNameValuePair("message", "FILLER MESSAGE UNTIL WE MAKE A TEXTBOX."));

            final JSONObject json = jsonParser.makeHttpRequest(url_send_request, "POST", params);

            runOnUiThread(new Runnable() {
                //
                @Override
                public void run() {
                    int success;
                    try {
                        success = json.getInt("success");
                        if (success == 1) {


                        } else {

                        }
                    } catch (JSONException e) {
                        Log.d("Boogityboo", "bby");
                    }
                }
            });
            return null;
        }


        protected void onPostExecute(String file_url) {
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast toast;


            CharSequence text = "FILLER TEXT!";
            toast = Toast.makeText(context, text, duration);
            toast.show();

        }
    }

}
