package akshay.tycse.wce.tponew;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

/**
 * Created by akki on 28/10/15.
 */
public class ViewActivity extends Activity
{
    TextView tnm,trn,tcr,tbr,tyr,tssc,thsc,tdpm,tdob,tmn,tem,tad,tcpi;
    Button ed,bc;
    DBhelper mydb;
    @Override

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mydb=new DBhelper(ViewActivity.this);

        setContentView(R.layout.content_view);
        Bundle data=getIntent().getExtras();
        tnm=(TextView)findViewById(R.id.tvnm1);
        trn=(TextView)findViewById(R.id.tvrn1);



        tcr=(TextView)findViewById(R.id.tvcr1);
        tbr=(TextView)findViewById(R.id.tvbr1);

        tyr=(TextView)findViewById(R.id.tvyr1);
        tssc=(TextView)findViewById(R.id.tvssc);
        thsc=(TextView)findViewById(R.id.tvhsc);
        tdpm=(TextView)findViewById(R.id.tvdpm);
        tcpi=(TextView)findViewById(R.id.tvcp1);
        tdob=(TextView)findViewById(R.id.tvdb1);
        tmn=(TextView)findViewById(R.id.tvmn1);
        tem=(TextView)findViewById(R.id.tvem1);
        tad=(TextView)findViewById(R.id.tvad1);
        ed=(Button)findViewById(R.id.bed1);
        bc=(Button)findViewById(R.id.bbc1);

        ed.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent i=new Intent(ViewActivity.this,MainActivity.class);
                startActivity(i);
            }
        });

        bc.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent i=new Intent(ViewActivity.this,StartingClass.class);
                startActivity(i);


            }
        });
        if(data!=null)
        {
            tnm.setText(tnm.getText()+"    "+data.getString("name"));
            trn.setText(trn.getText()+"    "+data.getString("rollno"));
            tcr.setText(tcr.getText()+"    "+data.getString("course"));
            tbr.setText(tbr.getText()+"    "+data.getString("branch"));
            tyr.setText(tyr.getText()+"    "+data.getString("year"));

            tssc.setText(tssc.getText()+"    "+data.getString("ssc"));
            thsc.setText(thsc.getText()+"    "+data.getString("hsc"));
            tdpm.setText(tdpm.getText()+"    "+data.getString("diploma"));

            tcpi.setText(tcpi.getText()+"    "+data.getString("cpi"));
            tdob.setText(tdob.getText()+"    "+data.getString("dob"));
            tmn.setText(tmn.getText()+"    "+data.getString("mono"));
            tem.setText(tem.getText()+"    "+data.getString("email"));
            tad.setText(tad.getText()+"    "+data.getString("address"));



        }
        else
        {
            Cursor rs=mydb.getData();
            rs.moveToFirst();
            tnm.setText(tnm.getText() + "    " + rs.getString(rs.getColumnIndex("name")));
            trn.setText(trn.getText()+"    "+rs.getString(rs.getColumnIndex("roll_no")));
            tcr.setText(tcr.getText()+"    "+rs.getString(rs.getColumnIndex("course")));
            tbr.setText(tbr.getText()+"    "+rs.getString(rs.getColumnIndex("branch")));
            tyr.setText(tyr.getText()+"    "+rs.getInt(rs.getColumnIndex("year")));

            tssc.setText(tssc.getText()+"    "+rs.getFloat(rs.getColumnIndex("ssc")));
            thsc.setText(thsc.getText()+"    "+rs.getFloat(rs.getColumnIndex("hsc")));
            tdpm.setText(tdpm.getText()+"    "+rs.getFloat(rs.getColumnIndex("diploma")));

            tcpi.setText(tcpi.getText()+"    "+rs.getFloat(rs.getColumnIndex("cpi")));
            tdob.setText(tdob.getText()+"    "+rs.getString(rs.getColumnIndex("dob")));
            tmn.setText(tmn.getText()+"    "+rs.getLong(rs.getColumnIndex("mono")));
            tem.setText(tem.getText()+"    "+rs.getString(rs.getColumnIndex("email")));
            tad.setText(tad.getText()+"    "+rs.getString(rs.getColumnIndex("address")));

            if (!rs.isClosed())
            {
                rs.close();
            }
        }


    }
}

