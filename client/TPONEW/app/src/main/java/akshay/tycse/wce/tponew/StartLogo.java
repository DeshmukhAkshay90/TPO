package akshay.tycse.wce.tponew;
        import android.app.Activity;
        import android.content.Intent;
        import android.database.Cursor;
        import android.os.Bundle;
        import android.os.PersistableBundle;

/**
 * Created by akki on 31/10/15.
 */
public class StartLogo extends Activity
{


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.content_start_logo);
        final DBhelper mydb=new DBhelper(StartLogo.this);

         Thread t=new Thread() {
            public  void run()
            {
                try

                {

                    Thread.sleep(2000);
                }

                catch( Exception e)

                {
                    System.out.println("Exception :" + e);
                }
                finally
                {
                    if(mydb.isAuthenticated())
                    {
                        mydb.close();
                        Intent i = new Intent(StartLogo.this,StartingClass.class);
                        startActivity(i);
                    }
                    else
                    {
                        mydb.close();
                        Intent i = new Intent(StartLogo.this,UserAuth.class);
                        startActivity(i);
                    }
                }


            }

        };

        t.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}