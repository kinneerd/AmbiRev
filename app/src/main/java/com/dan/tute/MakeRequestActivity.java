package com.dan.tute;

import android.content.res.TypedArray;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import butterknife.InjectView;

public class MakeRequestActivity extends ActionBarActivity {

    @InjectView(R.id.offer_spinner) protected Spinner mDropdown_Offer_list;

    private String[] prices;
    private TypedArray price_type;
    private String price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_request_);



        //create price spinner
        prices = getResources().getStringArray(R.array.price_list);
        price_type = getResources().obtainTypedArray(R.array.price_list);
        ArrayAdapter<String> dataAdapterPrice = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,prices);
        dataAdapterPrice.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mDropdown_Offer_list.setAdapter(dataAdapterPrice);

        //added prices to spinner
        mDropdown_Offer_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                price = price_type.getString(mDropdown_Offer_list.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_make__request_, menu);
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
