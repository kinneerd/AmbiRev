package com.dan.tute;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import org.w3c.dom.Text;
import butterknife.InjectView;

public class SignUpTutorActivity extends ActionBarActivity {

      @InjectView(R.id.tutor_prof_name) protected Text mTutor_Name;
      @InjectView(R.id.tutor_prof_name_last) protected Text mTutor_Name_Last;
    //@InjectView(R.id.tutor_prof_price) protected Text mTutor_Price;
    //@InjectView(R.id.male_tutor) protected Text mTutor_Male;
    //@InjectView(R.id.female_tutor) protected Text mTutor_Female;

      private String[] majors;
      private String[] prices;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_tutor);

        //creating major spinner
        Spinner dropdown = (Spinner)findViewById(R.id.major_spinner);
        majors = getResources().getStringArray(R.array.major_list);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,majors);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(dataAdapter);

        //create price spinner
        Spinner dropdownPrice = (Spinner)findViewById(R.id.price_spinner);
        prices = getResources().getStringArray(R.array.price_list);
        ArrayAdapter<String> dataAdapterPrice = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,prices);
        dataAdapterPrice.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdownPrice.setAdapter(dataAdapterPrice);




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up_tutor, menu);
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



}

