package akshay.tycse.wce.tponew;

import android.app.Activity;
import android.app.AlertDialog;
import android.database.sqlite.*;
import android.database.*;
import android.content.*;
import android.os.Bundle;
import android.widget.EditText;
 import java.sql.Date;

/**
 * Created by akki on 8/12/15.
 */
public class DBhelper extends  SQLiteOpenHelper
{
    String DATABASE_NAME="StudentDB1.db";


    public DBhelper(Context context)
    {
        super(context, "StudentDB1.db", null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase db)
    {

        db.execSQL("CREATE TABLE IF NOT EXISTS student_db(name varchar2(40),roll_no varchar2(10),course varchar2(40),branch varchar2(40),year number,ssc float,hsc float,diploma float,cpi float, dob Date,email  varchar2(40),mono number,address varchar2(400),primary key(Roll_no));");
        db.execSQL("CREATE TABLE IF NOT EXISTS student_passwd(username varchar2(10),password varchar2(20),name varchar2(40),roll_no varchar2(10),primary key(username));");
    }

    public boolean insertPass(String unm,String pwd,String nm,String rn)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("username",unm);
        contentValues.put("password",pwd);
        contentValues.put("name",nm);
        contentValues.put("roll_no", rn);
        db.insert("student_passwd", null, contentValues);
      //  db.close();
        return true;
    }
    public boolean insertData(String name,String roll_no,String course,String branch,int year,Double ssc,Double hsc,Double diploma,Double cpi,String dob,String email,String mono,String address)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("name",name);
        contentValues.put("roll_no",roll_no);
        contentValues.put("course",course);
        contentValues.put("branch",branch);
        contentValues.put("year",year);
        contentValues.put("ssc",ssc);
        contentValues.put("hsc",hsc);
        contentValues.put("diploma",diploma);
        contentValues.put("cpi",cpi);
        contentValues.put("dob",dob);
        contentValues.put("email",email);
        contentValues.put("mono",Long.parseLong(mono));
        contentValues.put("address",address);
        db.insert("student_db",null,contentValues);
      //  db.close();
        return  true;
    }
    public boolean isAuthenticated()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res=db.rawQuery("select * from student_passwd",null);
        try
        {
            res.moveToFirst();
            if(res.isNull(res.getColumnIndex("name")))
            {
                res.close();
                return false;

            }

        }
        catch (Exception e)
        {

            return false;

        }
       // db.close();
        return true;
    }
    public boolean isProfileStored()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res=db.rawQuery("select * from student_db",null);
        try
        {
            res.moveToFirst();
            if(res.isNull(res.getColumnIndex("name")))
            {
                res.close();
                return false;

            }

        }
        catch (Exception e)
        {

            return false;

        }
        //db.close();
        return true;
    }
    public void deleteProfile()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        db.execSQL("delete from student_db");
      //  db.close();
    }
    public Cursor getunmAndpwd()
    {
        String s[]=new String[2];
        Cursor res=null;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            res = db.rawQuery("select * from student_passwd", null);
        //    db.close();

        }
        catch(Exception e)
        {

        }

        return  res;

    }
    public  Cursor getData()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res=db.rawQuery("select * from student_db",null);
        return  res;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }




    @Override
    public void onOpen(SQLiteDatabase db)
    {
        super.onOpen(db);
    }


}
