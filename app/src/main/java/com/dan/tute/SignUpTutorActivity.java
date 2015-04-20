package com.dan.tute;

import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SignUpTutorActivity extends ActionBarActivity {

      @InjectView(R.id.tutor_tag_1) protected TextView mTutor_Tag_1;
      @InjectView(R.id.tutor_tag_2) protected TextView mTutor_Tag_2;
      @InjectView(R.id.tutor_tag_3) protected TextView mTutor_Tag_3;
      @InjectView(R.id.major_spinner) protected Spinner mDropdown_Major_list;
      @InjectView(R.id.price_spinner) protected Spinner mDropdown_Price_list;
      @InjectView(R.id.tutor_continue_button) protected Button mTutor_continue_button;
     @InjectView(R.id.tutor_cancel_button) protected Button mTutor_cancel_button;

      private String[] majors;
      private String[] prices;
      private TypedArray major_type;
      private TypedArray price_type;
      private String major;
      private String price;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_tutor);
        ButterKnife.inject(this);

        //creating major spinner
        majors = getResources().getStringArray(R.array.major_list);
        major_type = getResources().obtainTypedArray(R.array.major_list);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,majors);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mDropdown_Major_list.setAdapter(dataAdapter);

        //create price spinner
        prices = getResources().getStringArray(R.array.price_list);
        price_type = getResources().obtainTypedArray(R.array.price_list);
        ArrayAdapter<String> dataAdapterPrice = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,prices);
        dataAdapterPrice.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mDropdown_Price_list.setAdapter(dataAdapterPrice);


        // SET MAJOR AND PRICE SELECTION
        mDropdown_Major_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                major = major_type.getString(mDropdown_Major_list.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mDropdown_Price_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                price = price_type.getString(mDropdown_Price_list.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //SEND DATA TO SIGN UP TUTOR DETAIL
        mTutor_continue_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SignUpTutorDetailActivity.class);
                String tags = mTutor_Tag_1.getText().toString() + " " + mTutor_Tag_2.getText().toString() + " " + mTutor_Tag_3.getText().toString();
                intent.putExtra("tags",tags);
                intent.putExtra("major",major);
                intent.putExtra("price",price);
                startActivity(intent);
            }
            // ADD STATEMENT FOR EMPTY FIELDS AFTER TESTING
        });

        mTutor_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });





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

