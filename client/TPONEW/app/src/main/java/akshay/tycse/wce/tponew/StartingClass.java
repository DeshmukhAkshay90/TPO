package akshay.tycse.wce.tponew;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import akshay.tycse.wce.tponew.chat.MessageActivity;

/**
 * Created by akki on 29/10/15.
 */
public class StartingClass extends Activity
{

    GridView st;
    TextView tv;
    EditText e1;
    ImageView editProfile, viewProfile, viewMessages, viewImages;
    String name,rollno;
    String [] steps={"Edit Profile","view Profile","View Messages","View Schedule"};
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_starting_class);

        final Bundle data=getIntent().getExtras();
        DBhelper mydb=new DBhelper(this);
        Cursor c=mydb.getunmAndpwd();
        c.moveToFirst();



        if(data!=null)
        {
            name=data.getString("name");
            rollno=data.getString("roll_no");
        }
        else
        {
            name= c.getString(c.getColumnIndex("name"));
            rollno=c.getString(c.getColumnIndex("roll_no"));

        }
        Constants.setUsername(name);
        Constants.setRollno(rollno);
        c.close();

        e1=(EditText)findViewById(R.id.address);
        editProfile =(ImageView)findViewById(R.id.edit_profile);
        viewProfile =(ImageView)findViewById(R.id.view_profile);

        viewMessages =(ImageView)findViewById(R.id.view_messages);


        viewImages =(ImageView)findViewById(R.id.view_images);




        tv=(TextView)findViewById(R.id.tmp);



        tv.setText("Welcome "+name);

        editProfile.setOnClickListener(new View.OnClickListener() {
            Intent i;

            @Override
            public void onClick(View v) {

                i = new Intent(StartingClass.this, MainActivity.class);
                i.putExtra("add", String.valueOf(e1.getText()));
                i.putExtra("name", name);
                i.putExtra("roll_no", rollno);
                startActivity(i);

            }
        });
        viewProfile.setOnClickListener(new View.OnClickListener() {
            Intent i;

            @Override
            public void onClick(View v) {


                i = new Intent(StartingClass.this, ViewActivity.class);
                startActivity(i);

            }
        });
        viewMessages.setOnClickListener(new View.OnClickListener() {
            Intent i;

            @Override
            public void onClick(View v)
            {
                i = new Intent(StartingClass.this, MessageActivity.class);
                startActivity(i);

            }
        });
        viewImages.setOnClickListener(new View.OnClickListener() {
            Intent i;

            @Override
            public void onClick(View v) {
                i = new Intent(StartingClass.this, Multimedia.class);
                startActivity(i);

            }
        });
    }


}
