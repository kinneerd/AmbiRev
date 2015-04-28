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
            case R.id.action_edit_profile:
                Intent intent = new Intent(getApplicationContext(),EditBasicProfile.class);
                String user_email = SessionManager.getLoggedInEmailUser(getApplicationContext());
                intent.putExtra("email",user_email);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
