package akshay.tycse.wce.tponew.chat;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

import akshay.tycse.wce.tponew.Constants;
import akshay.tycse.wce.tponew.R;

public class MessageActivity extends AppCompatActivity {
    private static final String TAG = "ChatActivity";

    private ChatArrayAdapter chatArrayAdapter;
    private ListView listView;
    private EditText chatText;
    private Button buttonSend;

    Date tmpdate=new Date(111111);
SendOperation so;
    private boolean side;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        //Intent i = getIntent();
        //pass here database date
        // RetriveOperation ro= new RetriveOperation(Constants.getUsername(),Constants.getPassoword(),this,new Date());

        RetriveOperation ro= new RetriveOperation("Akshay P. Deshmukh",this,tmpdate);
        ro.execute();
        so=new SendOperation();
        so.execute();

        buttonSend = (Button) findViewById(R.id.buttonSend);

        listView = (ListView) findViewById(R.id.listView1);

        chatArrayAdapter = new ChatArrayAdapter(getApplicationContext(), R.layout.activity_chat_single_message);
        listView.setAdapter(chatArrayAdapter);

        chatText = (EditText) findViewById(R.id.chatText);
        chatText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)) {
                    Date cdate=new Date();
                   so.addToList(new MessageToSend(chatText.getText().toString(),cdate));
                   so.setHit(true);
                    return sendChatMessage(false, chatText.getText().toString());
                }
                return false;
            }
        });

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Date cdate=new Date();
               so.addToList(new MessageToSend(chatText.getText().toString(),cdate));
                so.setHit(true);
                sendChatMessage(false, chatText.getText().toString());
            }
        });

        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setAdapter(chatArrayAdapter);

        //to scroll the list view to bottom on data change
        chatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(chatArrayAdapter.getCount() - 1);
            }
        });
    }


    private boolean sendChatMessage(boolean sd, String chttext) {
        String inp[] = chttext.split("---");
        String msg = "";
        String tm = tmpdate.toString();
        msg = inp[0];
        if (inp.length > 1)
        {
            tm = inp[1];
            //tmpdate.setTime(Long.parseLong(inp[1]));
        }
        chatArrayAdapter.add(new ChatMessage(sd, msg, tm));
        chatText.setText("");
        return true;
    }











    private class SendOperation extends AsyncTask<String, Void, String> {
        URL url;
        HttpURLConnection connection = null;
        // DBhelper mydb = new DBhelper(UserAuth.this);
        String  urlParameters;
        Context context;
        String name,roll;
        boolean hit=false;
        HashSet<MessageToSend> ls=new HashSet<MessageToSend>();

        public SendOperation()
        {

            name=Constants.getUsername();
            roll=Constants.getRollno();
            displayMsg("sen created");
            // urlParameters ="date="+ String.valueOf(inpdate.getTime());
        }
        void addToList(MessageToSend ms)
        {
            ls.add(ms);
        }
        synchronized  public void setHit(boolean hit) {
            this.hit = hit;
        }
        boolean getHit()
        {
            return hit;


        }



        @Override
        protected String doInBackground(String... params) {


            try {


                DataOutputStream wr;

                while (true) {

                    displayMsg("chk ht"+so.getHit());
                    while (!so.getHit()) ;

                    displayMsg("chk enter"+so.getHit());
                    so.setHit(false);
                    Iterator<MessageToSend> it = ls.iterator();
                    MessageToSend ms;
                    while (it.hasNext())
                    {
                        ms=it.next();
                        try {


                            url = new URL(Constants.siteurl + "/TPO/ReceiveUserMessages");

                            connection = (HttpURLConnection) url.openConnection();

                            displayMsg("cn crtd");
                            connection.setRequestMethod("POST");

                            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                            connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));

                            connection.setRequestProperty("Content-Language", "en-US");
                            connection.setRequestProperty("charset", "utf-8");
                            connection.setUseCaches(false);
                            connection.setDoInput(true);
                            connection.setDoOutput(true);

                            if (connection == null) {
                                displayMsg("no Network connection");
                            }

                        } catch (Exception e) {
                            // displayMsg("Exception at stream crt"+e.toString());
                            onStop();
                        }


                        wr = new DataOutputStream(connection.getOutputStream());
                        urlParameters = "name=" + name + "&msg=" + ms.getMsg() + "&rollno=" + roll + "&date=" + ms.getDate().getTime();
                        wr.writeBytes(urlParameters);


                        wr.flush();
                        wr.close();


                        displayMsg("msg send ");


                        connection.disconnect();
                        connection.connect();

                    }
                    ls.clear();

                    //Send request

                }
            } catch (Exception e) {
                so.setHit(false);
                displayMsg("Exception   "+e.toString());

            }


            return null;
        }

        @Override
        protected void onPostExecute(String result) {
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

    private  class MessageToSend {
        private String msg;
        private Date date;

        public MessageToSend(String msg, Date d) {
            this.msg = msg;
            this.date = d;
        }

        public Date getDate() {
            return date;
        }

        public String getMsg() {
            return msg;
        }
    }
    private class RetriveOperation extends AsyncTask<String, Void, String> {
        URL url;
        HttpURLConnection connection = null;
        // DBhelper mydb = new DBhelper(UserAuth.this);
        String username, urlParameters;
        Context context;

        public RetriveOperation(String n, Context c,Date inpdate)
        {

            username = n;
            context = c;
            //urlParameters ="date="+ String.valueOf(inpdate.getTime());

        }

        @Override
        protected String doInBackground(String... params) {

            try {


                DataOutputStream wr;
               int  cnt=1;
                while (true)
                {


                    try {
                        url = new URL(Constants.siteurl + "/TPO/SendMessages");

                        connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("POST");
                        // displayMsg("Connection established and st po"+cnt);
                        urlParameters ="date="+tmpdate.getTime();

                        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                        connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));

                        connection.setRequestProperty("Content-Language", "en-US");
                        connection.setRequestProperty("charset", "utf-8");
                        connection.setUseCaches(false);
                        connection.setDoInput(true);
                        connection.setDoOutput(true);

                        if (connection == null)
                        {
                            displayMsg("no Network connection");
                        }

                    } catch (Exception e)
                    {
                        // displayMsg("Exception at stream crt"+e.toString());
                        onStop();
                    }

                    wr = new DataOutputStream(connection.getOutputStream());
                    wr.writeBytes(urlParameters);
                    wr.flush();
                    wr.close();


                    //Get Response
                    InputStream is = connection.getInputStream();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                    String response;
                    while((response = rd.readLine())!=null)
                    {
                        final String rs=response;
                      //  displayMsg("from server " + response);
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                sendChatMessage(true, rs);
                            }
                        });

                    }
                    tmpdate.setTime(new Date().getTime());
                 //   displayMsg("msg no"+cnt);
                 //   ++cnt;

                    connection.disconnect();
                    connection.connect();

                }
                //Send request


            } catch (Exception e) {
                displayMsg("Exception At RO  "+e.toString());

            }


            return null;
        }

        @Override
        protected void onPostExecute(String result) {
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
    public void displayMsg(final String message)
    {

        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                Toast.makeText(MessageActivity.this, message, Toast.LENGTH_LONG).show();

            }
        });


    }
}
