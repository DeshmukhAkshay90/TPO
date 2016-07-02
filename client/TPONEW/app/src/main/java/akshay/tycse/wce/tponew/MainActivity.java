package akshay.tycse.wce.tponew;

import java.net.HttpURLConnection;
import java.net.Socket;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import java.net.Socket;
import java.io.*;
import java.lang.String;
import java.net.URL;

public class MainActivity extends Activity implements View.OnClickListener
{

    Button sub,can;
    String str[]=new String[16];
    String allbranch[]={"Computer Science and Engg","Information Technology","Electronics Engg","Mechanical Engg","Electrical Engg","Civil","Production Engg"};
    String branchU[]={"Computer Science and Engg","Information Technology","Electronics Engg","Mechanical Engg","Electrical Engg","Civil"};
    String branchP[]={"Computer Science and Engg","Information Technology","Electronics Engg","Production Engg"};
    String course[]={"Under Graduate","Post Graduate"};
    String db;
    AutoCompleteTextView nm,rn,ssc,hsc,dpm,cpi,yr,em,mn;
    EditText ad;
    DatePicker dob;
    int month,year,day;
    Spinner spbr1,spcr1;
    ArrayAdapter<String> list;
    Bundle data;
    boolean flag=false;
    String err="Please Insert Correct :";
    DBhelper mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydb=new DBhelper(MainActivity.this);

        data=getIntent().getExtras();

        sub=(Button)findViewById(R.id.btsub);
        can=(Button)findViewById(R.id.btcan);
        sub.setOnClickListener(this);
        can.setOnClickListener(this);

        nm=(AutoCompleteTextView)findViewById(R.id.ednm);
        rn=(AutoCompleteTextView)findViewById(R.id.edrn);

        nm.setText(data.getString("name"));
        rn.setText(data.getString("roll_no"));

        nm.setKeyListener(null);
        nm.setEnabled(false);

        rn.setKeyListener(null);
        rn.setEnabled(false);

        spbr1=(Spinner)findViewById(R.id.spbr);
        spcr1=(Spinner)findViewById(R.id.spcr);
        yr=(AutoCompleteTextView)findViewById(R.id.edyr);
        ssc=(AutoCompleteTextView)findViewById(R.id.edss);
        hsc=(AutoCompleteTextView)findViewById(R.id.edhs);
        dpm=(AutoCompleteTextView)findViewById(R.id.eddp);

        cpi=(AutoCompleteTextView)findViewById(R.id.edcp);
        em=(AutoCompleteTextView)findViewById(R.id.edem);
        mn=(AutoCompleteTextView)findViewById(R.id.edmn);
        ad=(EditText)findViewById(R.id.etad);
        dob=(DatePicker)findViewById(R.id.dpdob);





        list=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,allbranch);
        spbr1.setAdapter(list);
        spbr1.setSelection(0);

        list=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,course);
        spcr1.setAdapter(list);
        spcr1.setSelection(0);

        spcr1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if((String)spcr1.getSelectedItem()=="Under Graduate")
                {
                    list=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,branchU);
                    spbr1.setAdapter(list);
                }
                else
                {
                    list=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,branchP);
                    spbr1.setAdapter(list);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //sbr.getSelectedItem();
    }


    boolean notNull()
    {

        String msg=" ";
        boolean flag=false;
        String tmp1,tmp=yr.getText().toString();
        if(tmp.length()<1)
        {
            msg=msg+ "  Year";
            flag=true;
        }
        tmp=ssc.getText().toString();
        if( tmp.length()<1)
        {
            msg=msg+ "  SSC";
            flag=true;
        }
        tmp=hsc.getText().toString();
        tmp1=dpm.getText().toString();
        if(tmp.length()<1 && tmp1.length()<1)
        {
            msg=msg+ "   HSC or Diploma marks one of them";
            flag=true;
        }


        tmp=cpi.getText().toString();
        if(tmp.length()<1)
        {
            msg=msg+ "  CPI";
            flag=true;
        }

        tmp=em.getText().toString();
        if(tmp.length()<1)
        {
            msg=msg+ "  Email";
            flag=true;
        }

        tmp=mn.getText().toString();
        if(tmp.length()<1)
        {
            msg=msg+ "  Mo.no.";
            flag=true;
        }

        tmp=ad.getText().toString();
        if(tmp.length()<1)
        {
            msg=msg+ "  Address";
            flag=true;

        }

        if(flag==true)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage(msg);
            builder.setTitle("Please Fill the Fields:");
            builder .setCancelable(false);
            builder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Perform some action such as saving the item
                            dialog.cancel();
                        }
                    });

            builder.create().show();
            return false;
        }




        return  true;
    }

    @Override
    public void onClick(View v)
    {
        String tmp;


            try {

                if (v.getId() == R.id.btsub)
                {
                    if(notNull()) {

                        str[0] = nm.getText().toString();
                        str[1] = rn.getText().toString();
                        str[2] = (String) spcr1.getSelectedItem();
                        str[3] = (String) spbr1.getSelectedItem();

                        tmp = yr.getText().toString();
                        int y = Integer.parseInt(tmp);

                        if (y < 1980 || y > 2016) {
                            err = err + "  " + "Year";
                            flag = true;
                        } else {
                            str[4] = tmp;

                        }
                        tmp = ssc.getText().toString();
                        float z = Float.parseFloat(tmp);

                        if (z < 0.0 || z > 100.0) {
                            err = err + "  " + "SSC";
                            flag = true;
                        } else {
                            str[5] = tmp;

                        }


                        tmp = hsc.getText().toString();

                        try {
                            z = Float.parseFloat(tmp);
                            if (z < 0.0 || z > 100.0) {
                                err = err + "  " + "HSC";
                                flag = true;
                            } else {
                                str[6] = tmp;
                            }


                        } catch (Exception e) {
                            str[6] = "0.0";
                            hsc.setText("--");

                        }


                        tmp = dpm.getText().toString();

                        try {
                            z = Float.parseFloat(tmp);
                            if (z < 0.0 || z > 100.0) {
                                err = err + "  " + "DIPLOMA";
                                flag = true;
                            } else {
                                str[7] = tmp;
                            }


                        } catch (Exception e) {
                            str[7] = "0.0";
                            dpm.setText("--");

                        }


                        tmp = cpi.getText().toString();
                        z = Float.parseFloat(tmp);

                        if (z <= 0.0 || z > 10.0) {
                            err = err + "  " + "CPI";
                            flag = true;
                        } else {
                            str[8] = tmp;

                        }


                        month = dob.getMonth();
                        year = dob.getYear();
                        day = dob.getDayOfMonth();
                        if (year > 2016) {
                            err = err + "  " + "Date";
                            flag = true;
                        } else {
                            db = Integer.toString(year) + "-" + Integer.toString(month) + "-" + Integer.toString(day);
                            str[9] = db;
                        }


                        tmp = em.getText().toString();
                        str[10] = tmp;


                        tmp = mn.getText().toString().trim();
                        if (tmp.length() == 10) {
                            str[11] = tmp;

                        } else {
                            err = err + "  " + "Mobile No.";
                            flag = true;
                        }

                        tmp = ad.getText().toString();
                        str[12] = tmp;


                        if (flag == true)
                        {

                            throw new NumberFormatException(" ");
                        }


                        String qry;
                        if (mydb.isProfileStored()) {
                            qry = "UPDATE";
                        } else {
                            qry = "Edit";
                        }

                        new DataSend(str, qry).start();
                    }
                }
                else
                {
                    nm.setText(data.getString("name"));
                    rn.setText(data.getString("roll_no"));
                    spbr1.setSelection(1);
                    spcr1.setSelection(1);
                    yr.setText("");
                    ssc.setText("");
                    hsc.setText("--");
                    dpm.setText("--");
                    cpi.setText("");

                    em.setText("");
                    mn.setText("");
                    ad.setText("");


                }


            }
            catch (NumberFormatException e)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                String msg = "Enter Valid Data";
                if (flag == true)
                {
                    msg = err;
                    err = "Please Insert Correct :";

                }
                flag = false;
                builder.setMessage(msg);
                builder.setTitle("Invalid Data");
                builder.setCancelable(false);
                builder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Perform some action such as saving the item
                                dialog.cancel();
                            }
                        });

                builder.create().show();
            }
            catch (NullPointerException e)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                String msg = "please fill the fields";
                if (flag == true) {
                    msg = err;
                    err = "Please Insert Correct :";

                }
                flag = false;
                builder.setMessage(msg);
                builder.setTitle("Incorrect Data");
                builder.setCancelable(false);
                builder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Perform some action such as saving the item
                                dialog.cancel();
                            }
                        });

                builder.create().show();
            }
            catch (Exception e)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setMessage("Problem with Net Connection please Try Again");
                builder.setTitle("Ooops..");
                builder.setCancelable(false);
                builder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Perform some action such as saving the item
                                dialog.cancel();
                            }
                        });

                builder.create().show();
            }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar willm
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    class DataSend extends Thread
    {

        String str[],urlParameters;
        String TYPE;
        URL url;
        HttpURLConnection connection = null;

        public DataSend(String s[],String type)
        {
            str=s;
            TYPE=type;
            urlParameters="name="+str[0]+"&rollno="+str[1]+"&course="+str[2]+"&branch="+str[3]
                    +"&year="+str[4]+"&ssc="+str[5]+"&hsc="+str[6]+"&diploma="
                    +str[7]+"&cpi="+str[8]+"&dob="+str[9]+"&email="+str[10]+"&mono="+str[11]+"&address="+str[12];

        }
        public void run()
        {
            try
            {




                url = new URL(Constants.siteurl+"/TPO/UploadData");

                connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
                connection.setRequestProperty("Content-Language", "en-US");

                connection.setUseCaches (false);
                connection.setDoInput(true);
                connection.setDoOutput(true);

                //Send request
                DataOutputStream wr = new DataOutputStream (connection.getOutputStream ());
                wr.writeBytes (urlParameters);
                wr.flush ();
                wr.close ();

                //Get Response
                InputStream is = connection.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                String response =rd.readLine();



                if(response.startsWith("OK"))
                {
                    Intent i = new Intent(MainActivity.this, ViewActivity.class);
                    i.putExtra("name", str[0]);
                    i.putExtra("rollno", str[1]);
                    i.putExtra("course", str[2]);
                    i.putExtra("branch", str[3]);
                    i.putExtra("year", str[4]);
                    i.putExtra("ssc", str[5]);
                    i.putExtra("hsc", str[6]);
                    i.putExtra("diploma", str[7]);
                    i.putExtra("cpi", str[8]);
                    i.putExtra("dob", str[9]);
                    i.putExtra("email", str[10]);
                    i.putExtra("mono", str[11]);
                    i.putExtra("address", str[12]);
                    if(mydb.isProfileStored())
                    {
                        mydb.deleteProfile();
                    }
                    if(mydb.insertData(str[0], str[1], str[2], str[3], Integer.parseInt(str[4]), Double.parseDouble(str[5]), Double.parseDouble(str[6]), Double.parseDouble(str[7]), Double.parseDouble(str[8]), str[9], str[10], str[11], str[12]))
                    {
                        startActivity(i);
                    }


                }
                else
                {

                    Toast.makeText(getApplicationContext(), "Failed to send data", Toast.LENGTH_LONG);
                }

                rd.close();
            }
            catch(Exception e)
            {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Problem with Net Connection please Try Again"+e);
                builder.setTitle("Ooops");
                builder .setCancelable(false);
                builder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Perform some action such as saving the item
                                dialog.cancel();
                            }
                        });

                builder.create().show();



            }
        }
    }
}
