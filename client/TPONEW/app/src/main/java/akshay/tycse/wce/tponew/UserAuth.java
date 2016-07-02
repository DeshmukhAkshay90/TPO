package akshay.tycse.wce.tponew;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.io.*;
import java.lang.String;
import java.net.URL;
import java.util.logging.Handler;

public class UserAuth extends Activity implements View.OnClickListener
{

    Button sub, can;
    EditText un, pw, add1;
    String n, p;
   TextView ut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_auth);

        sub = (Button) findViewById(R.id.ubtlg);
        can = (Button) findViewById(R.id.ubtcn);
        un = (EditText) findViewById(R.id.uedunm);
        pw = (EditText) findViewById(R.id.uedpwd);
        add1 = (EditText) findViewById(R.id.edaddress1);

        ut = (TextView) findViewById(R.id.utv);


        sub.setOnClickListener(this);
        can.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.ubtlg)
        {
            n = un.getText().toString().trim();
            p = pw.getText().toString().trim();

            new LongOperation(n,p,getApplicationContext()).execute("");


        }
        else
        {
            un.setText("");
            pw.setText("");

        }
    }

    private class LongOperation extends AsyncTask<String, Void, String>
    {
        URL url;
        HttpURLConnection connection = null;
        DBhelper mydb = new DBhelper(UserAuth.this);
        String username, password, urlParameters;
        Context context;
        public LongOperation(String n, String p,Context c)
        {

            username = n;
            password = p;
            context=c;
            urlParameters = "username=" + username + "&password=" + password;


            try {
                url = new URL(Constants.siteurl+"/TPO/LogIn");
                connection = (HttpURLConnection) url.openConnection();


                if (connection == null) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            ut.setText("connection problem");
                        }
                    });
                }

            } catch (Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        ut.setText("connection exception");
                    }
                });
            }
        }

            @Override
        protected String doInBackground(String... params)
        {

                try {


                        connection.setRequestMethod("POST");
                        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                        connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
                        connection.setRequestProperty("Content-Language", "en-US");
                        connection.setRequestProperty("charset", "utf-8");
                        connection.setUseCaches(false);
                        connection.setDoInput(true);
                        connection.setDoOutput(true);



                        //Send request
                        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
                        wr.writeBytes(urlParameters);
                        wr.flush();
                        wr.close();




                        //Get Response
                        InputStream is = connection.getInputStream();
                        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                        String response = rd.readLine();




                        if (response.startsWith("VERIFIED"))
                        {
                            //store name of student and login info


                            final String response1= rd.readLine();
                            final String rn = rd.readLine();



                            if (mydb.insertPass(username, password, response1, rn))
                            {
                                Constants.setUsername(username);
                                mydb.close();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        Intent i = new Intent(context, StartingClass.class);
                                        i.putExtra("name", response1);
                                        i.putExtra("roll_no", rn);
                                        startActivity(i);
                                    }
                                });

                            }

                        } else {

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        ut.setText("Enter valid username and password");
                                    }
                                });
                        }

                        rd.close();





                }
                catch (Exception e)
                {
                    runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        ut.setText("Exception");
                    }
                });
                }


            return null;
        }

        @Override
        protected void onPostExecute(String result)
        {
            if (connection != null) {
                connection.disconnect();
            }
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }




}