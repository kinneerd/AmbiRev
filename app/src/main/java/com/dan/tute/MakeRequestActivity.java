package com.dan.tute;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MakeRequestActivity extends ActionBarActivity {

    @InjectView(R.id.offer_spinner) protected Spinner mDropdown_Offer_list;
    @InjectView(R.id.descriptionField) protected TextView mRequest_description;
    @InjectView(R.id.request_tag_1) protected TextView mRequest_Tag_1;
    @InjectView(R.id.request_tag_2) protected TextView mRequest_Tag_2;
    @InjectView(R.id.request_tag_3) protected TextView mRequest_Tag_3;
    @InjectView(R.id.tutor_continue_button) protected TextView mContinue;

    private String[] prices;
    private TypedArray price_type;
    private String offer;

    protected JSONParser jsonParser = new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_request_);
        ButterKnife.inject(this);


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
                offer = price_type.getString(mDropdown_Offer_list.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RequestActivity.class);
                new AddRequestActivity().execute();

                startActivity(intent);
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

    class AddRequestActivity extends AsyncTask<String, String, String> {
        String phpMessage = "";
        boolean updateSuccess;

        protected void onPreExecute(){
            super.onPreExecute();

            updateSuccess = false;
        }

        protected String doInBackground(String... args) {

            Intent intent = getIntent();

            String currentEmail = SessionManager.getLoggedInEmailUser(getApplicationContext());

            String tags = mRequest_Tag_1.getText().toString() + "," + mRequest_Tag_2.getText().toString() + "," + mRequest_Tag_3.getText().toString();


            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("offer", offer.substring(1)));
            params.add(new BasicNameValuePair("tags", tags));
            params.add(new BasicNameValuePair("description", mRequest_description.getText().toString()));
            params.add(new BasicNameValuePair("email", currentEmail));

            JSONObject json = jsonParser.makeHttpRequest(getResources().getString(R.string.add_request), "POST", params);

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
