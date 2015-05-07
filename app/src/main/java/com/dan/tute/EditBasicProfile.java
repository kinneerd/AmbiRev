package com.dan.tute;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class EditBasicProfile extends ActionBarActivity {

    private static final String url_edit_tutee = "http://68.119.36.255/tute/edit_tutee_profile.php";
    private static final String url_load_tutee = "http://68.119.36.255/tute/load_tutee_profile.php";
    protected boolean editSuccess;
    protected String phpMessage;
    protected String currentEmail;
    protected JSONParser jsonParser = new JSONParser();

    @InjectView(R.id.editBioField) protected TextView mBio;
    @InjectView(R.id.editFirstNameField) protected TextView mFirstName;
    @InjectView(R.id.editLastNameField) protected TextView mLastName;
    @InjectView(R.id.editMajorField) protected TextView mMajor;
    @InjectView(R.id.profEmailAddress) protected TextView mEmail;
    @InjectView(R.id.editProfileSubmitButton) protected Button mEditProfileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_basic_profile);

        Intent intent = getIntent();
        //currentEmail = intent.getStringExtra("email");
        currentEmail = SessionManager.getLoggedInEmailUser(getApplicationContext());
        ButterKnife.inject(this);

        mEmail.setText(currentEmail);

        mEditProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = mFirstName.getText().toString().trim();
                String lastName = mLastName.getText().toString().trim();
                String bio = mBio.getText().toString().trim();
                String major = mMajor.getText().toString().trim();

                if (firstName.isEmpty() || lastName.isEmpty() || bio.isEmpty() || major.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditBasicProfile.this);
                    builder.setMessage(R.string.signup_error_message)
                            .setTitle(R.string.signup_error_title)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else {
                    // Success
                    new UpdateUserActivity().execute();
                }
            }
        });

        new LoadUserInformationActivity().execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_basic_profile, menu);
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

    class LoadUserInformationActivity extends AsyncTask<String, String, String> {
        protected void onPreExecute(){
            super.onPreExecute();
        }

        protected String doInBackground(String... args) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("email", currentEmail));

            final JSONObject json = jsonParser.makeHttpRequest(url_load_tutee, "POST", params);

            runOnUiThread(new Runnable() {
                //
                @Override
                public void run() {
                    int success;
                    try {
                        success = json.getInt("success");
                        if (success == 1) {
                            if(!json.getString("bio").equals("null")) {
                                mBio.setText(json.getString("bio"));
                            }
                            if(!json.getString("major").equals("null")) {
                                mMajor.setText(json.getString("major"));
                            }
                            if(!json.getString("firstName").equals("null")) {
                                mFirstName.setText(json.getString("firstName"));
                            }
                            if(!json.getString("lastName").equals("null")) {
                                mLastName.setText(json.getString("lastName"));
                            }

                        } else {
                            phpMessage = json.getString("message");
                        }
                    } catch (JSONException e) {
                    }
                }
            });
            return null;
        }


        protected void onPostExecute(String file_url) {

        }
    }

    class UpdateUserActivity extends AsyncTask<String, String, String> {
        protected void onPreExecute(){
            super.onPreExecute();

            editSuccess = false;
        }

        protected String doInBackground(String... args) {

            String bio = mBio.getText().toString().trim();
            String firstName = mFirstName.getText().toString().trim();
            String lastName = mLastName.getText().toString().trim();
            String major = mMajor.getText().toString().trim();

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("bio", bio));
            params.add(new BasicNameValuePair("firstName", firstName));
            params.add(new BasicNameValuePair("lastName", lastName));
            params.add(new BasicNameValuePair("major", major));
            params.add(new BasicNameValuePair("email", currentEmail));

            JSONObject json = jsonParser.makeHttpRequest(url_edit_tutee, "POST", params);

            try{
                int success = json.getInt("success");

                if(success == 1){
                    editSuccess = true;

                    Intent i = getIntent();
                    setResult(100, i);
                    finish();
                }else{
                    phpMessage = json.getString("message");
                }
            }catch(JSONException e){
                e.printStackTrace();
            }
            return null;
        }


        protected void onPostExecute(String file_url){

            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast toast;

            if(editSuccess){
                CharSequence text = "Successfully updated!";
                toast = Toast.makeText(context, text, duration);
                toast.show();
                finish();
                Intent intent = new Intent(getApplicationContext(),MyProfileActivity.class);
                startActivity(intent);
            }else{
                CharSequence text = phpMessage;
                toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }
    }
}
