package com.dan.tute;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.dan.tute.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MyProfileActivity extends ActionBarActivity {

    @InjectView(R.id.tool_bar) protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        ButterKnife.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Profile");
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
                //navigateToLogin();
                Intent intent = new Intent(this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.action_edit_profile:
                Intent intent1 = new Intent(getApplicationContext(),EditBasicProfile.class);
                String user_email = SessionManager.getLoggedInEmailUser(getApplicationContext());
                intent1.putExtra("email",user_email);
                startActivity(intent1);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
