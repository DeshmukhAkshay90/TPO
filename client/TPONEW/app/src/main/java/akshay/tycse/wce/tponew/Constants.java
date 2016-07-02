package akshay.tycse.wce.tponew;

import java.util.*;

/**
 * Created by akki on 5/4/16.
 */
public class Constants
{
    public  static  final String  siteurl="http://192.168.43.104:8086";
    public  static   int  filename=1;
    public static Date dos=new Date();
    public static String username="aKSHAY p dESHMUKH";
    public static String passoword;
    public static String rollno="2014BCS204";

    public static void setPassoword(String passoword) {
        Constants.passoword = passoword;
    }

    public static void setUsername(String username) {
        Constants.username = username;
    }

    public static String getPassoword() {
        return passoword;
    }

    public static String getUsername() {
        return username;
    }

    public static Date getDos()
    {

        return dos;
    }

    public static void setRollno(String rollno) {
        Constants.rollno = rollno;
    }

    public static String getRollno() {
        return rollno;
    }

    public static void setDos(Date dos)
    {
        Constants.dos = dos;
    }

    public static int getFileName()

    {
       return filename;
    }
}
