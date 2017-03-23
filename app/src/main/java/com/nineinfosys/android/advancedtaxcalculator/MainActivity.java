package com.nineinfosys.android.advancedtaxcalculator;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;


import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    ImageView profilePictureView;

    DecimalFormat f = new DecimalFormat("##.00");
    public Toolbar toolbar;
    TextView first,second,third,fourth,fivth,firstI,secondI,thirdI,fourthI,fivthI;
    Spinner  genderspinner;
    AdvancedTaxMain income;
    EditText incometaxsalary,housingloan,selfoccupied,letablevalue,municipaltaxes,unrealizedrent,netincome,standarddeduction,
            interestonhousing1,totalhousedittextid, shortterm1,shortterm2,lonterm1,longterm2,totalcapitalgain,interest,commision,lotery,
            totalothersources,reliefedittext, surchargeedittext, educationedittext, higherandseceducationcessedittext, totalreliefedittext,totalnet,amountofint;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DecimalFormat f = new DecimalFormat("##.00");
        Configuration config = getResources().getConfiguration();

        genderspinner = (Spinner) findViewById(R.id.genderspinnerid);
        incometaxsalary = (EditText) findViewById(R.id.incometaxedittextid);
        housingloan = (EditText) findViewById(R.id.intersetonhousingloanedittextid);
        housingloan.setText("0");
        selfoccupied = (EditText) findViewById(R.id.interestonselfoccupiededittext);
        selfoccupied.setText("0");
        Button buttonCalculte = (Button) findViewById(R.id.buttoncalculate);
        letablevalue = (EditText) findViewById(R.id.annualliablevalueedittextid);
        letablevalue.setText("0");
        municipaltaxes = (EditText) findViewById(R.id.muncipaltaxesedittextid);
        municipaltaxes.setText("0");
        unrealizedrent = (EditText) findViewById(R.id.unrealizedrentedittextid);
        unrealizedrent.setText("0");
        netincome = (EditText) findViewById(R.id.netincomevalueedittextid);
        netincome.setText("0");
        standarddeduction = (EditText) findViewById(R.id.standarddeductionedittextid);
        standarddeduction.setText("0");
        interestonhousing1 = (EditText) findViewById(R.id.interestonhousingedittextid);
        interestonhousing1.setText("0");
        totalhousedittextid = (EditText) findViewById(R.id.totalhousetextid);
        totalhousedittextid.setText("0");
        shortterm1 = (EditText) findViewById(R.id.shortterm1edittextid);
        shortterm1.setText("0");
        shortterm2 = (EditText) findViewById(R.id.shortterm2edittextid);
        shortterm2.setText("0");
        lonterm1 = (EditText) findViewById(R.id.longterm1edittextid123);
        lonterm1.setText("0");
        longterm2 = (EditText) findViewById(R.id.longterm2edittextid);
        longterm2.setText("0");
        totalcapitalgain = (EditText) findViewById(R.id.totalcapitalgainedittextid);
        totalcapitalgain.setText("0");
        interest = (EditText) findViewById(R.id.interestedittextid);
        interest.setText("0");
        commision = (EditText) findViewById(R.id.Commissionedittextid);
        commision.setText("0");
        lotery = (EditText) findViewById(R.id.Lotteryedittextid);
        lotery.setText("0");
        totalothersources = (EditText) findViewById(R.id.totalotheredittextid);
        totalothersources.setText("0");
        reliefedittext = (EditText) findViewById(R.id.incometaxreliefedittextid);
        reliefedittext.setText("0");
        surchargeedittext = (EditText) findViewById(R.id.surchargeedittextid123);
        surchargeedittext.setText("0");
        educationedittext = (EditText) findViewById(R.id.educationcessedittextid);
        educationedittext.setText("0");
        higherandseceducationcessedittext = (EditText) findViewById(R.id.higherandseceducessedittextid);
        higherandseceducationcessedittext.setText("0");
        totalreliefedittext = (EditText) findViewById(R.id.totalreliefedittextid);
        totalreliefedittext.setText("0");
        totalnet = (EditText) findViewById(R.id.totalnettaxedittextid);
        totalnet.setText("0");
        first = (TextView) findViewById(R.id.firstedittextid);
        second = (TextView) findViewById(R.id.secondedittextid);
        third = (TextView) findViewById(R.id.thirdedittextid);
        fourth = (TextView) findViewById(R.id.fourthedittextid);
        fivth = (TextView) findViewById(R.id.fivthedittext);
        firstI = (TextView) findViewById(R.id.firstIedittext);
        secondI = (TextView) findViewById(R.id.secondIedittext);
        thirdI = (TextView) findViewById(R.id.thirdIedittext);
        fourthI = (TextView) findViewById(R.id.fourthiedittextid);
        fivthI = (TextView) findViewById(R.id.fivthiedittext);



        ArrayList gender = new ArrayList();
        gender.add("Citizen");
        gender.add("seniorCitizen");
        gender.add("SeniorSuperCitizen");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gender);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderspinner.setAdapter(dataAdapter);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff);


        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();


        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();


                //communicate
                if (menuItem.getItemId() == R.id.Share) {
                    final String appPackageName = getPackageName();
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    String shareBodyText = "https://play.google.com/store/apps/details?id=" + appPackageName;
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Subject/Title");
                    intent.putExtra(Intent.EXTRA_TEXT, shareBodyText);
                    startActivity(Intent.createChooser(intent, "Choose sharing method"));

                }

                if (menuItem.getItemId() == R.id.AppStore) {
                    final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://developer?id=GeniusNine+Info+Systems+LLP")));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=GeniusNine+Info+Systems+LLP")));
                    }
                }

                if (menuItem.getItemId() == R.id.GetApps) {

                    /*Intent intent=new Intent(MainActivityDrawer.this,RequestApp.class);
                    startActivity(intent);
*/

                }


                if (menuItem.getItemId() == R.id.RateUs) {
                    final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                    }


                }


                return false;
            }


        });
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        buttonCalculte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(incometaxsalary.getText().toString().equals("")){
                    incometaxsalary.setError("Enter the Salary");
                } else if(letablevalue.getText().toString().equals("")){
                }else if(municipaltaxes.getText().toString().equals("")){

                }else if(unrealizedrent.getText().toString().equals("")){

                }else if(interestonhousing1.getText().toString().equals("")){

                } else if(shortterm1.getText().toString().equals("")){

                } else if(shortterm2.getText().toString().equals("")){

                } else if(lonterm1.getText().toString().equals("")){

                } else if(longterm2.getText().toString().equals("")){

                }else if(interest.getText().toString().equals("")){

                }else if(commision.getText().toString().equals("")){

                }else if(lotery.getText().toString().equals("")){

                }else if(housingloan.getText().toString().equals("")){

                }else{
                    DecimalFormat f = new DecimalFormat("##.00");
                    Integer salaryincome = Integer.parseInt(incometaxsalary.getText().toString());
                    Integer house = Integer.parseInt(housingloan.getText().toString());
                    Integer letable = Integer.parseInt(letablevalue.getText().toString());
                    Integer municipal = Integer.parseInt(municipaltaxes.getText().toString());
                    Integer unrealized = Integer.parseInt(unrealizedrent.getText().toString());
                    Integer intersthousing = Integer.parseInt(interestonhousing1.getText().toString());
                    Integer short1 = Integer.parseInt(shortterm1.getText().toString());
                    Integer short2 = Integer.parseInt(shortterm2.getText().toString());
                    Integer long1 = Integer.parseInt(lonterm1.getText().toString());
                    Integer long2 = Integer.parseInt(longterm2.getText().toString());
                    Integer interestt = Integer.parseInt(interest.getText().toString());
                    Integer commisionn = Integer.parseInt(commision.getText().toString());
                    Integer loteryy = Integer.parseInt(lotery.getText().toString());
                    int capitalgainvaluetotal = (short1 + short2 + long1 + long2);
                    totalcapitalgain.setText(String.valueOf(capitalgainvaluetotal));
                    selfoccupied.setText(String.valueOf(-(house)));
                    int NAV = (letable - (municipal + unrealized));
                    netincome.setText(String.valueOf(NAV));
                    standarddeduction.setText(String.valueOf(NAV * 30 / 100));
                    int valuetotalhouse = ((-house)+(NAV - ((NAV * 30 / 100) + intersthousing)));
                    int incomeother = interestt + commisionn + loteryy;
                    totalhousedittextid.setText(String.valueOf(valuetotalhouse));
                    totalothersources.setText(String.valueOf(interestt + commisionn + loteryy));
                    totalnet.setText(String.valueOf(salaryincome + valuetotalhouse + capitalgainvaluetotal + incomeother));
                    double nettax = (salaryincome + valuetotalhouse + capitalgainvaluetotal + incomeother);
                    String spinnerGender = genderspinner.getSelectedItem().toString().trim();
                    income = new AdvancedTaxMain(nettax, spinnerGender);
                    double incomerelief = income.calculateIncomeTaxAfterRelief();
                    double surchargeValue = income.calculateSurcharge();
                    double educationalcess = income.calculateIncomeTaxAfterRelief() * 0.02;
                    double highereducationalcess = income.calculateIncomeTaxAfterRelief() * 0.01;
                    reliefedittext.setText(String.valueOf((f.format(incomerelief))));
                    educationedittext.setText(String.valueOf((f.format(educationalcess))));
                    surchargeedittext.setText(String.valueOf((f.format(surchargeValue))));
                    higherandseceducationcessedittext.setText(String.valueOf((f.format(highereducationalcess))));
                    totalreliefedittext.setText(String.valueOf((f.format(incomerelief + educationalcess + highereducationalcess + surchargeValue))));
                   double i= income.uptoJune();
                    first.setText(String.valueOf(f.format(i)));
                    second.setText(String.valueOf(f.format(income.uptoSep())));
                    third.setText(String.valueOf(f.format(income.uptoDec())));
                    fourth.setText(String.valueOf(f.format(income.uptoMarch())));
                    fivth.setText(String.valueOf(f.format(income.uptoMarch())));

                    firstI.setText(String.valueOf(f.format(i)));
                    secondI.setText(String.valueOf(f.format(income.uptoSep()-(income.uptoJune()))));
                    thirdI.setText(String.valueOf(f.format(income.uptoDec()-(income.uptoSep()))));
                    fourthI.setText(String.valueOf(f.format(income.uptoMarch()-(income.uptoDec()))));
                    fivthI.setText(String.valueOf(f.format(income.uptoMarch()-(income.uptoMarch()))));


                }
            }
        });

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage("Are you sure you want to close App?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                finish();

                            }
                        });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {


                    }
                });

                //Showing the alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}

