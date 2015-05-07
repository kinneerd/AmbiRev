package com.dan.tute;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
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

public class ViewMessageActivity extends ActionBarActivity {

    @InjectView(R.id.message) protected TextView mMessage;
    @InjectView(R.id.name) protected TextView mName;
    @InjectView(R.id.replyButton) protected Button mButton;

    protected String senderEmail;
    private String newMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_message);

        Intent intent = getIntent();
        ButterKnife.inject(this);

        mMessage.setText(intent.getStringExtra("message"));
        mName.setText(intent.getStringExtra("firstName"));
        senderEmail = intent.getStringExtra("senderEmail");

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_message, menu);
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

    public void sendMessage(){
        newMessage = "";

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Send Message");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override

            public void onClick(DialogInterface dialog, int which) {
                newMessage = input.getText().toString();

                new SendMessageRequestActivity().execute();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    class SendMessageRequestActivity extends AsyncTask<String, String, String> {
        protected void onPreExecute(){
            super.onPreExecute();
        }

        protected String doInBackground(String... args) {


            String currentEmail = SessionManager.getLoggedInEmailUser(getApplicationContext());

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("recieverEmail", senderEmail));
            params.add(new BasicNameValuePair("senderEmail", currentEmail));
            params.add(new BasicNameValuePair("message", newMessage));

            JSONParser jsonParser = new JSONParser();

            final JSONObject json = jsonParser.makeHttpRequest(getResources().getString(R.string.send_message), "POST", params);

            runOnUiThread(new Runnable() {
                //
                @Override
                public void run() {
                    int success;
                    try {
                        success = json.getInt("success");
                        if (success == 1) {


                        } else {

                        }
                    } catch (JSONException e) {
                    }
                }
            });
            return null;
        }

        protected void onPostExecute(String file_url) {
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast toast;

            CharSequence text = "Message Sent!";
            toast = Toast.makeText(context, text, duration);
            toast.show();

        }
    }
}
