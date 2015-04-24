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
import android.widget.EditText;
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

public class SignUpTutorDetailActivity extends ActionBarActivity {

    @InjectView(R.id.tutor_prof_detail1) protected EditText mTutor_Prof_1;
    @InjectView(R.id.tutor_prof_detail2) protected EditText mTutor_Prof_2;
    @InjectView(R.id.tutor_prof_detail3) protected EditText mTutor_Prof_3;
    @InjectView(R.id.signupButton) protected Button mTutor_signup_button;

    protected JSONParser jsonParser = new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_tutor_detail);
        ButterKnife.inject(this);

        mTutor_signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UpgradeUserActivity().execute();

                Intent intent = new Intent(getApplicationContext(),SignUpTutorDetailActivity.class);
                startActivity(intent);
            }
            // ADD STATEMENT FOR EMPTY FIELDS AFTER TESTING
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up_tutor_detail, menu);
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

    class UpgradeUserActivity extends AsyncTask<String, String, String> {
        String phpMessage = "";
        boolean updateSuccess;

        protected void onPreExecute(){
            super.onPreExecute();

            updateSuccess = false;
        }

        protected String doInBackground(String... args) {

            Intent intent = getIntent();

            String currentEmail = SessionManager.getLoggedInEmailUser(getApplicationContext());

            String experience = mTutor_Prof_1.getText().toString() + "," + mTutor_Prof_2.getText().toString() + "," + mTutor_Prof_3.getText().toString();

            
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("major", intent.getStringExtra("major")));
            params.add(new BasicNameValuePair("tags", intent.getStringExtra("tags")));
            params.add(new BasicNameValuePair("price", intent.getStringExtra("price").substring(1)));
            params.add(new BasicNameValuePair("experience", experience));
            params.add(new BasicNameValuePair("bio", intent.getStringExtra("bio")));
            params.add(new BasicNameValuePair("email", currentEmail));

            JSONObject json = jsonParser.makeHttpRequest(getResources().getString(R.string.upgrade_account), "POST", params);

            try{
                int success = json.getInt("success");

                if(success == 1){
                    updateSuccess = true;

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

            if(updateSuccess){
                CharSequence text = "Successfully updated!";
                toast = Toast.makeText(context, text, duration);
                toast.show();
            }else{
                CharSequence text = phpMessage;
                toast = Toast.makeText(context, text, duration);
                toast.show();
            }

            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
    }
}
