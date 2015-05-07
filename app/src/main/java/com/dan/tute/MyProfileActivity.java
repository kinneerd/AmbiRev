package com.dan.tute;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.dan.tute.R;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MyProfileActivity extends ActionBarActivity {

    private String currentEmail;

    private String url_load_tutee_profile = "http://68.119.36.255/tute/load_tutee_profile.php";

    @InjectView(R.id.profName) protected TextView mName;
    @InjectView(R.id.profMajor) protected TextView mMajor;
    @InjectView(R.id.profDesc) protected TextView mDesc;

    @InjectView(R.id.tool_bar) protected Toolbar toolbar;

    protected Drawer.Result drawer = null;

    protected JSONParser jsonParser;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_my_profile);

            ButterKnife.inject(this);

            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("My Profile");

            // Creating Navigation Drawer
            drawer = new Drawer()
                    .withActivity(this)
                    .withToolbar(toolbar)
                    .addDrawerItems(
                            new SecondaryDrawerItem().withName(R.string.drawer_item_home).withIcon(R.drawable.ic_home).withIdentifier(1),
                            new DividerDrawerItem(),
                            //new PrimaryDrawerItem().withName(R.string.drawer_item_search).withIcon(R.drawable.ic_search),
                            //new PrimaryDrawerItem().withName(R.string.drawer_item_request).withIcon(R.drawable.ic_request),
                            //new PrimaryDrawerItem().withName(R.string.drawer_item_recent).withIcon(R.drawable.ic_recent),
                            new SecondaryDrawerItem().withName(R.string.drawer_item_settings).withIcon(R.drawable.ic_settings).withIdentifier(2)
                    )
                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                            if (drawerItem != null) {
                                Intent intent = null;
                                if (drawerItem.getIdentifier() == 1) {
                                    intent = new Intent(getApplicationContext(), MainActivity.class);
                                } else if (drawerItem.getIdentifier() == 2) {
                                    intent = new Intent(getApplicationContext(), SettingsActivity.class);
                                }
                                if (intent != null) {
                                    startActivity(intent);
                                }
                            }
                        }
                    })
                    .withSavedInstance(savedInstanceState)
                    .build();

            currentEmail = SessionManager.getLoggedInEmailUser(getApplicationContext());

            jsonParser =new JSONParser();

            new LoadProfileInformationActivity().execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id) {
            case R.id.action_logout:
                SessionManager.clearUserSharedPreferences(getApplicationContext());
                SessionManager.navigateToLogin(getApplicationContext());
                break;
            case R.id.action_edit_profile:
                Intent intent = new Intent(getApplicationContext(),EditBasicProfile.class);
                String user_email = SessionManager.getLoggedInEmailUser(getApplicationContext());
                intent.putExtra("email",user_email);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    class LoadProfileInformationActivity extends AsyncTask<String, String, String> {
        protected void onPreExecute(){
            super.onPreExecute();
        }

        protected String doInBackground(String... args) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("email", currentEmail));

            final JSONObject json = jsonParser.makeHttpRequest(url_load_tutee_profile, "POST", params);

            runOnUiThread(new Runnable() {
                //
                @Override
                public void run() {
                    int success;
                    try {
                        success = json.getInt("success");
                        if (success == 1) {
                            if(!json.getString("firstName").equals("null")) {
                                mName.setText(json.getString("firstName"));
                            }
                            if(!json.getString("bio").equals("null")) {
                                mDesc.setText(json.getString("bio"));
                            }
                            if(!json.getString("major").equals("null")) {
                                mMajor.setText(json.getString("major"));
                            }

                        } else {
                            //phpMessage = json.getString("message");
                        }
                    } catch (JSONException e) {}
                }
            });
            return null;
        }

        protected void onPostExecute(String file_url) {

        }
    }
}
