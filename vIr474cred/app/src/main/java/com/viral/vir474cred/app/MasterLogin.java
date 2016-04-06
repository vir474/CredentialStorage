package com.viral.vir474cred.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import encrypto.Creds;
import encrypto.DBHelper;
import encrypto.EncryptoHelp;

public class MasterLogin extends ActionBarActivity {

    private ImageButton ibtn_master;
    private EditText et_pw;
    String temp_master_pw = "";
    private TextView tv_display;
    private Boolean newuser_flag = false;
    DBHelper db;
    Creds temp_cred;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.lo_masterlogin);
        final EncryptoHelp mcrypt = new EncryptoHelp();

        db = new DBHelper(this);
        ibtn_master = (ImageButton) findViewById(R.id.ibtn_master_submit);
        et_pw = (EditText) findViewById(R.id.et_pw_main);
        tv_display = (TextView) findViewById(R.id.tv_display);

        //Check if Seed is set or not !
        SharedPreferences pref = getApplicationContext().getSharedPreferences("VirCredApp", 0);
        if (pref.getBoolean("isFirstTime", true)) {
            newuser_flag = true;
            //Set first time app use flag to false
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("isFirstTime", false);
            editor.commit();

            //Ask user to set a Master Password
            tv_display.setText("Select a Master Password");

        }


        ibtn_master.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp_master_pw = et_pw.getText().toString().trim();
                String encrypt_pw = null;
                try {
                    encrypt_pw = mcrypt.bytesToHex(mcrypt.encrypt(temp_master_pw));
                } catch (Exception e) {
                    Log.e("Main Activity Error ", e.getMessage());
                }

                if (newuser_flag) {
                        temp_cred = new Creds("Master_PW", null, encrypt_pw);

                    //Store the entered master pw in DB
                    db.addCred(temp_cred);

                    try {
                        temp_cred = new Creds("Facebook", "VIR474", new String(mcrypt.bytesToHex(mcrypt.encrypt("viralL916"))));
                    } catch (Exception e) {
                        Log.e("Master Login Activity", e.getMessage());
                    }
                    //Store the entered master pw in DB
                    db.addCred(temp_cred);

                    Intent in = new Intent(MasterLogin.this, HomeScreen.class);
                    startActivity(in);
                    finish();
                } else {
                    // get master pw from db
                    temp_cred = db.getCred("Master_PW");
                    Log.e("GET CRED", temp_cred.getPassword());
                    Log.e("Encrypted text", encrypt_pw);

                    if (temp_cred.getPassword().equals(encrypt_pw)) {
                        Intent in = new Intent(MasterLogin.this, HomeScreen.class);
                        startActivity(in);
                        finish();
                    }


                }


            }
        });

    }
            @Override
            public boolean onCreateOptionsMenu(Menu menu) {

                // Inflate the menu; this adds items to the action bar if it is present.
                getMenuInflater().inflate(R.menu.main, menu);
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
