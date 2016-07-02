import java.net.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
class Chat extends Thread implements ActionListener
{
BufferedReader br,in;
     PrintStream ps;
Thread1 th1;
Thread2 th2;
boolean flag=false;
 JFrame  f=new JFrame("chat Application");


                 JPanel p=new JPanel(); 
                 TextArea t1=new TextArea(" ",10,10,1);
                 TextArea t2=new TextArea(" ",10,10,1);
                 JLabel l1=new  JLabel("Received: ");
                 JLabel l2=new JLabel("Send: ");
		JLabel l3=new JLabel("Enter your name: ");
                 JButton bt=new JButton("submit");
String nm;


void getNames()
 {
     p.setLayout(null);
     p.setBounds(0,0,500,600); 
    l3.setBounds(150,220,200,30);
    t2.setBounds(50,270,300,130);
      
     bt.setBounds(50,420,150,30);
     bt.addActionListener(this);

    p.add(bt);
    p.add(l3);
    p.add(t2);
    f.add(p);   
    f.show();
    f.setBounds(380,60,500,600);
 }

        Chat() 
	{
                
                
                  
              
                  try
                   {
                       Socket clt=new Socket("10.88.21.12",8030);
                       br=new BufferedReader(new InputStreamReader(clt.getInputStream()));
                       ps=new PrintStream(clt.getOutputStream());
                       
	             }
                      catch(Exception e)
		    {
			System.out.println("soket creation0"+e);
                    } 
                 getNames();  
         
	}

 public void actionPerformed(ActionEvent ae)
  {


     flag=true;
    if(ae.getActionCommand()=="submit")
      {
           l3.setVisible(false);
                p.add(t1);
		p.add(l1);
		p.add(l2);
		p.add(bt);

		
		l1.setBounds(50,50,100,30);
		t1.setBounds(50,80,300,130);
		t1.setEditable(false);
		

		l2.setBounds(50,230,100,30);
		bt.setText("Send");
		
                 

  	       

                f.repaint();

        	th1=new Thread1();
		th1.start();
                th2=new Thread2();
		th2.start();
                
      }
     

  }

  class Thread1 extends Thread
    {

	
    public void run() 
	{

                 
                 
            while(true)
	     {
               try
                   {
                         String str=br.readLine();
	                 t1.setText(t1.getText()+"\n"+str);
	   
        	        

	             }
                      catch(Exception e)
		    {
			System.out.println("soket send"+e);
                    } 
	       }
	}

    } 

   class Thread2 extends Thread
    {

	
    public void run() 
	{

                 
                 
            while(true)
	     {
               try
                   {
                       if(flag==true)
        	        {
                              String str=t2.getText().trim();
	                     ps.println(str);
                             t2.setText(" ");
                             flag=false;
                        }
	             }
                      catch(Exception e)
		    {
 			System.out.println(e);
                    } 
	       }
	}

    } 


}
class Client
{

public static void main(String args[])
 {
   Chat c=new Chat();
 }

}


