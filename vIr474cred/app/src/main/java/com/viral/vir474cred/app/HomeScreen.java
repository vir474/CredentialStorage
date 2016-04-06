package com.viral.vir474cred.app;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

import encrypto.Creds;
import encrypto.DBHelper;
import encrypto.EncryptoHelp;

public class HomeScreen extends Activity {

    DBHelper vircreddb;
    private Spinner spinner_fctn;
    private List<Creds> list_creds;
    private List<String> functionList;
    private ArrayAdapter<String> functionAdapter;
    private TextView tv_credname;
    private TextView tv_creduname;
    private TextView tv_credpw;
    private ToggleButton toggle_encrypt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lo_homescreen);
        final EncryptoHelp mcrypt = new EncryptoHelp();


         spinner_fctn = (Spinner)findViewById(R.id.spinner_function);
        tv_credname = (TextView)findViewById(R.id.tv_credname);
        tv_creduname = (TextView)findViewById(R.id.tv_creduname);
        tv_credpw = (TextView)findViewById(R.id.tv_credpw);
        toggle_encrypt = (ToggleButton)findViewById(R.id.toggle_credpw);

        initfunctionList();
        functionAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, functionList);
        functionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_fctn.setAdapter(functionAdapter);

        spinner_fctn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                tv_credname.setText(list_creds.get(position).getName());
                tv_creduname.setText(list_creds.get(position).getUserName());
                tv_credpw.setText(list_creds.get(position).getPassword());
//                Log.e("PW fb",list_creds.get(position).getPassword());

                final String encrypt_pw = list_creds.get(position).getPassword();
                toggle_encrypt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean ischecked) {
                        if(ischecked)
                        {
                            try{
                                tv_credpw.setText(new String(mcrypt.decrypt(encrypt_pw)));}
                            catch (Exception e)
                            {
                                Log.e("Homescreen PW decrypt", e.getMessage());
                            }
                        }
                        else
                        {
                            tv_credpw.setText(encrypt_pw);
                        }
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }

    //Initialize list fom DB
    void initfunctionList(){
        //get an instance of DB
        vircreddb = new DBHelper(this);
        //to get all creds
        list_creds  = vircreddb.getAllCreds();
        functionList = new ArrayList<String>();

        for(int i=0;i<list_creds.size();i++)
        {
            functionList.add(list_creds.get(i).getName().toString());
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
