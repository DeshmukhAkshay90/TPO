package akshay.tycse.wce.tponew;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Multimedia extends Activity {

    GridLayout gridLayout;
    ImageView imageView;
    TextView msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multimedia);
        gridLayout = (GridLayout) findViewById(R.id.multimedia_grid_layout);
        msg=(TextView)findViewById(R.id.mul_msg);
        msg.setText("onclick");
        new DataSend().start();
    }
   public  void addView(File file)
   {
       imageView=new ImageView(Multimedia.this);
       imageView.setImageURI(Uri.fromFile(file));
       gridLayout.addView(imageView);
   }
    class DataSend extends Thread {



        URL url;
        HttpURLConnection connection = null;
        File file;
        FileOutputStream fos;
        public void run() {

            try {

                msg.setText("run");
                url = new URL(Constants.siteurl + "/TPO/Multimedia");

                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setRequestProperty("Content-Language", "en-US");

                connection.setUseCaches(false);
                connection.setDoInput(true);
                connection.setDoOutput(true);

                //Send request
                InputStream is = connection.getInputStream();
                BufferedReader br=new BufferedReader(new InputStreamReader(is));
                int imagecount=Integer.parseInt(br.readLine());
                msg.setText(imagecount);



                byte[] buf = new byte[1024];
                for(int i=0;i<imagecount;i++)
                {
                     file=new File(String.valueOf(Constants.getFileName()));
                    fos = new FileOutputStream(file);
                    int count = 0;
                    while ((count = is.read(buf)) >= 0)
                    {
                        fos.write(buf);
                    }
                    Multimedia.this.addView(file);

                }
                fos.close();
                is.close();



            } catch (Exception e) {

            msg.setText(e.toString());


            }
        }
    }
}
