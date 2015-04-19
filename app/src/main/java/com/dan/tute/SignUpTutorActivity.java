package com.dan.tute;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Spinner;

import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;

import com.dan.tute.R;

import org.w3c.dom.Text;

import butterknife.InjectView;

public class SignUpTutorActivity extends ActionBarActivity {

      @InjectView(R.id.tutor_prof_name) protected Text mTutor_Name;
      @InjectView(R.id.tutor_prof_name_last) protected Text mTutor_Name_Last;
      @InjectView(R.id.major_spinner) protected Spinner mMajor_List;
    //@InjectView(R.id.tutor_prof_price) protected Text mTutor_Price;
    //@InjectView(R.id.male_tutor) protected Text mTutor_Male;
    //@InjectView(R.id.female_tutor) protected Text mTutor_Female;

      private ArrayList<String> major_list_items;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_tutor);

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

/*
// pulls majors from the major list and adds to string arraylist
    public void createMajorList(ArrayList<String> list){
        try (BufferedReader br = new BufferedReader(new FileReader("major_list.txt"))) {
            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {
              list.add(i,line);
            }
        }catch(IllegalArgumentException e){
            System.err.println("IllegalArgumentException: " + e.getMessage());
        }catch(IOException e){
            System.err.println("IOException:" + e.getMessage());
        }
    } */
}
