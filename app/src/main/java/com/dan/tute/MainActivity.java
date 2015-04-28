package com.dan.tute;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends ActionBarActivity {

    @InjectView(R.id.create_request) protected ImageButton mCreate_Request;
    @InjectView(R.id.search_tutor) protected ImageButton mSearch_tutor;
   // @InjectView(R.id.edit_profile) protected Button mEdit_Profile;
    @InjectView(R.id.tool_bar) protected Toolbar toolbar;
    @InjectView(R.id.tutorSignUp) protected TextView mSignUpTutor;

    protected Drawer.Result drawer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        setSupportActionBar(toolbar);   // Setting toolbar as the ActionBar with setSupportActionBar() call

        /*
        // Creating Navigation Drawer Header
        AccountHeader headerResult = new AccountHeader()
                .withActivity(this)
                .withHeaderBackground()
        */

        // Creating Navigation Drawer
        drawer = new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        new SecondaryDrawerItem().withName(R.string.drawer_item_home).withIcon(R.drawable.ic_home),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_search).withIcon(R.drawable.ic_search),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_request).withIcon(R.drawable.ic_request),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_recent).withIcon(R.drawable.ic_recent),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_settings).withIcon(R.drawable.ic_settings)
                )/*
                .withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View drawerView) {
                        Toast.makeText(MainActivity.this, "onDrawerOpened", Toast.LENGTH_SHORT).show();
                        //drawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(false);
                        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        Toast.makeText(MainActivity.this, "onDrawerClosed", Toast.LENGTH_SHORT).show();
                        //getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                        //drawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
                    }
                })
                */
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                    }
                })
                .build();

        setupUser();

        // request button listener
        mCreate_Request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RequestActivity.class);
                startActivity(intent);
            }
        });

        mSearch_tutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SearchActivity.class);
                startActivity(intent);
            }
        });
/*
        mEdit_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),EditBasicProfile.class);
                String user_email = SessionManager.getLoggedInEmailUser(getApplicationContext());
                intent.putExtra("email",user_email);
                startActivity(intent);
            }
        });
*/
        mSignUpTutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SignUpTutorActivity.class);
                startActivity(intent);
            }
        });


    }

    private void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    /*
    Uses SessionManager to check whether user is currently logged in
    Navigates to appropriate screen
     */
    private void setupUser() {
        if(SessionManager.getUserLoggedInStatus(getApplicationContext())) {
            String email = SessionManager.getLoggedInEmailUser(getApplicationContext());
            //Intent intent = new Intent(getApplicationContext(), EditBasicProfile.class);
            //intent.putExtra("email", email);
            //startActivity(intent);
        }else {
            // Not logged in
            navigateToLogin();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
                navigateToLogin();
                break;
            case R.id.action_user:
                Intent intent = new Intent(getApplicationContext(),MyProfileActivity.class);
                startActivity(intent);
                break;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
