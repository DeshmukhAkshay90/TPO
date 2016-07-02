import java.net.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.*;
import  java.awt.List ;
import jxl.*;
import jxl.write.*;
import jxl.write.Label;
import jxl.write.Boolean;
import jxl.write.Number;
import jxl.write.biff.RowsExceededException;
class ClientObj  implements ActionListener
{
	
	Object names[],names1[];
	Object data[][],data1[][];
	Object data2[][],data3[][];

	String uname,passwd,stu_nm;
        int cnt=1;
	JFrame  f=new JFrame("TPO");
	JTabbedPane tp=new JTabbedPane();
	JPanel p=new JPanel(); 

	JPanel vppanel;
	JLabel txt1,txt2,txt3,txt4,txt,txt8,txt9,txt10,hlp;
	JTextField yr,cp;
	List lst,lst1;
	JButton fnd,cnl,bk,exc;

       
        Connection con=null;
	Statement st;
   	JLabel wc=new JLabel("Walchand College of Engineering ,Sangli"); 
   	JLabel tpo=new JLabel("Training and placement Office");
	

	String nm;

        JButton vr=new JButton("view overall Report");
        JButton vr1=new JButton("view Report");
        JButton sm=new JButton("send messages");
          
	ClientObj()
 	{
   		  try
			{
		          	  Class.forName("oracle.jdbc.driver.OracleDriver");
			 	  System.out.println("drv connected"); 
        	         	  con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","SYSTEM","akshay");
                	 	  System.out.println("connected"); 
			  	  st=con.createStatement();
			
			}
			catch(Exception e)
			{
        		      System.out.println("Exception: "+e);
			      e.printStackTrace();
			}
    		  wc.setBounds(200,30,400,30);
		  tpo.setBounds(230,70,400,30);
  		  vr.setBounds(200,150,200,60);
  		  vr1.setBounds(480,150,200,60);
  		  sm.setBounds(200,250,200,60);
  		  vr.addActionListener(this);
  		  vr1.addActionListener(this);
 	          sm.addActionListener(this);


  		   p.setLayout(null);
 	           p.setBounds(0,0,500,600);
 		   p.add(vr);
 		   p.add(vr1);
 		  // p.add(sm);
		   p.add(wc);
		   p.add(tpo);
                  
	
  		   tp.addTab("    TPO  ",p);
                  
 		   f.add(tp);   
                   f.setBounds(90,20,1250,700);
  		   f.show();
  		       

       }

       void addTabCmp(String title,String tooltip)
	  {
		 JPanel p=new JPanel();
		 title+="  ";
		 JLabel l=new JLabel(title);
		 JButton bt=new JButton("*");
		
		
		 bt.addActionListener(this);
		
		 p.setBounds(0,0,100,30);
		 p.add(l);
		 p.add(bt);
                
	
  		   
		 tp.setTabComponentAt(cnt,p);
		 tp.setSelectedIndex(cnt);
		 tp.setToolTipTextAt(cnt,tooltip);		 
		 cnt++;
	  }
    boolean addTPaneTab(String nm,JPanel pn)
	{
               int cnt=tp.getTabCount();
               boolean result=false;

		for(int i=1;i<cnt;i++)
		  {
			if(tp.getTitleAt(i)==nm)
			 {
			          tp.setComponentAt(i,pn);
				  tp.repaint();
				  tp.setSelectedIndex(i); 
				  result=true;
				 break;
				
			 }
		  }
          
            return result;

	}

 public void actionPerformed(ActionEvent ae)
  {
	

	ResultSet  rs,rs1;
	ResultSetMetaData rm;
	int rowcount=0;

	int m,i,n,j,a;
	a=tp.getSelectedIndex();

        if(ae.getSource()==vr)
	{
        	 try
		{		
		   		  st=con.createStatement();
		  		  rs=st.executeQuery("select * from student_db order by cpi desc");

                    
		   		  //retrive column count
		   		  rm=rs.getMetaData();
                   		  int colcount=rm.getColumnCount();	
   	
                    		  data=new Object[2000][colcount];
		    		  names=new Object[colcount];
		 
	            		 //retrive column names
				
		     		  m=0;
				  for(i=1;i<=colcount;i++)
					{
			  
	                		   names[m]=rm.getColumnName(i);
					   //System.out.println(names[m]);
			 		  m++;
					}
			  	  System.out.println("colnames created");
 
		  		 //retrive row data
		  		   m=0;
		   		   n=0;
                   		   while(rs.next())
					{

					     rowcount++;
			 
			 		    for(j=1;j<=colcount;j++)
			 		    {
						 data[m][n]=rs.getString(j);
						 //System.out.println(data[m][n]);
					     	 ++n;
			     		     }	

			 	             n=0;
			                     m++;
					}



		      		    data1=new Object[rowcount][colcount];
		      		    for(i=0;i<rowcount;i++)
				     {
			  
	                  		 for(j=0;j<colcount;j++)
			  		 {
			   		   data1[i][j]=data[i][j];
	                      
			 		  }
				    }
 				   data=null;
				   System.out.println(data1.length);


		  //create a table
		   		  JTable jt=new JTable(data1,names);
		    		  jt.setEnabled(false);
		  		  TableColumn  tc=jt.getColumn("NAME");
		  		  tc.setPreferredWidth(180);

				 

				  TableColumn  tc6=jt.getColumn("SSC");
 		  		   tc6.setPreferredWidth(35);

                  		  TableColumn  tc7=jt.getColumn("HSC");
 		   		  tc7.setPreferredWidth(35);

 		   		  TableColumn  tc8=jt.getColumn("DIPLOMA");
 		    		  tc8.setPreferredWidth(35);

				  TableColumn  tc9=jt.getColumn("COURSE");
 		    		  tc9.setPreferredWidth(100);
				  
				  TableColumn  tc4=jt.getColumn("BRANCH");
 		   		  tc4.setPreferredWidth(162);

		  		  TableColumn  tc1=jt.getColumn("YEAR");
		 		  tc1.setPreferredWidth(25);

				  TableColumn  tc2=jt.getColumn("CPI");
		 	          tc2.setPreferredWidth(20);

		   		  TableColumn  tc3=jt.getColumn("DOB");
 		  		   tc3.setPreferredWidth(55);
                  		  
 		   		  

          			 
				 

		   		 jt.setRowHeight(30);
  				 
				 exc=new JButton("Create Excel Sheet");
	         		 JScrollPane js=new JScrollPane(jt,22,32);
		    		 JPanel p1=new JPanel();
		     
		   		  p1.setLayout(null);
				  exc.setBounds(1000,10,200,30);
				  exc.addActionListener(this);
 
		   		  js.setBounds(10,70,1200,600);
 	            		  p1.setBounds(0,0,1200,640);
 		    		  p1.add(js); 
             			  p1.add(exc);


				//if allready tab is open then add panel to it otherwise create a new tab

				  if(addTPaneTab("Report",p1)!=true)
				    {
					//create a new tab with tab component
	  		    		  tp.addTab("Report",p1);
					  addTabCmp("Report","Overall Report");
				    }
		    		   
		    		
		}
		catch(Exception e)
		{
           		 	  System.out.println("Exception: "+e);
	    		  	  e.printStackTrace();
		}
		
	}

	//  create a panel for retrive data by using cpi or year
       else if(ae.getSource()==vr1)
	 {
	 	 	   vppanel=new JPanel();
                         
		  	  txt=new JLabel("Enter Search Criteria ");
		  	  txt1=new JLabel("Year:");
			  txt4=new JLabel("Course");
	 	  	  txt2=new JLabel("Branch:");
	 	   	  txt3=new JLabel("CPI:");
			  
			   hlp=new JLabel("Please select the branch from list"); 
			  hlp.setForeground(Color.red);
			  txt8=new JLabel(" Please Enter Correct Year");
			  txt8.setForeground(Color.red);

			  txt9=new JLabel(" Please select course");
			  txt9.setForeground(Color.red);

			  txt10=new JLabel(" Please Enter Correct CPI");
			  txt10.setForeground(Color.red);

 				 hlp.setVisible(false);
			         txt8.setVisible(false);
			         txt9.setVisible(false);
			         txt10.setVisible(false);
			

		   	    yr=new JTextField("2015");
		            cp=new JTextField("8.0");

		              lst1=new List(2,true);
                              lst1.add("Under Graduate");
                              lst1.add("Post Graduate");
     			      lst1.select(0);

		             lst=new List(6,true);
                              lst.add("Computer Science and Engg");
                              lst.add("Information Technology");
                              lst.add("Electrical Engg");
                              lst.add("Electronics Engg");
                              lst.add("Mechanical Engg");
                              lst.add("Civil");
                              lst.select(0);
 			    
	 	 	 fnd=new JButton("View");
	 	 	 cnl=new JButton("Cancel");
			

         	 	   vppanel.setLayout(null);
	  	  	   vppanel.setBounds(0,0,1200,640);

		  	  txt.setBounds(400,50,200,30);

			    txt4.setBounds(300,120,100,30);
		  	    lst1.setBounds(450,120,250,50);
			    txt9.setBounds(720,120,300,30);

		   	  txt1.setBounds(300,220,100,30);
		  	  yr.setBounds(450,220,250,30);
			  txt8.setBounds(720,220,300,30);

		    	  txt2.setBounds(300,300,100,30);
		    	  lst.setBounds(450,300,250,107);
                          hlp.setBounds(720,300,400,20); 

		   	  txt3.setBounds(300,430,100,30);
		   	  cp.setBounds(450,430,250,30);
			  txt10.setBounds(720,430,300,30);
			  txt3.setToolTipText("find records with cpi grater than given cpi");
		    
		    	 fnd.setBounds(300,490,100,30);
		         cnl.setBounds(550,490,100,30);
		        
                          fnd.addActionListener(this);	
			  cnl.addActionListener(this);
                       
			          

				vppanel.add(txt);
				vppanel.add(txt1);
				vppanel.add(txt2);
				vppanel.add(txt3);
				vppanel.add(txt4);
				vppanel.add(yr);
				vppanel.add(lst);
				vppanel.add(lst1);
                                vppanel.add(hlp);
                                vppanel.add(txt8);
				vppanel.add(txt9);
				vppanel.add(txt10);
				vppanel.add(cp);
				
				vppanel.add(fnd);
				vppanel.add(cnl);
			    
				

				//if allready serach tab is open then add panel to it otherwise create a new tab

				  if(addTPaneTab("Search",vppanel)!=true)
				    {
					//create a new tab with tab component
	  		    		  tp.addTab("Search",vppanel);
				          addTabCmp("Search Records","search Perticular Records");
				    }
				
	
				

	    
		 }	


 		else if(ae.getSource()==sm)
			{
	
			}

	
		 else if(ae.getSource()==fnd)
			  {
				 int year;
			         String branch[],qry,course[];
				 float c;


				  
				  year=Integer.parseInt(yr.getText().trim()) ;
                                 branch=lst.getSelectedItems();
	  			 course=lst1.getSelectedItems();
				 c=Float.parseFloat(cp.getText().trim()) ;
				System.out.println("In fnd");

				if(validate(course.length,branch.length))
				{	
				 try
				 {
					 hlp.setVisible(false);
			                 txt8.setVisible(false);
			                 txt9.setVisible(false);
			                 txt10.setVisible(false);

				   	 st=con.createStatement();
					 qry="select * from student_db where course in (";
					 for(i=0;i<(course.length-1);i++)
					 {
						qry=qry+"'"+course[i]+"'"+",";		
					 }
					 qry=qry+"'"+course[i]+"'"+") and branch in (";
					
					for(i=0;i<(branch.length-1);i++)
					 {
						qry=qry+"'"+branch[i]+"'"+",";		
					 }
					qry=qry+"'"+branch[i]+"'"+") and year="+year+" and cpi >= "+c+" order by cpi desc";


				
	
					  System.out.println(qry);
		  		   	 rs=st.executeQuery(qry);

                    
		   		 	 //retrive column count
		   		 	 rm=rs.getMetaData();
                   		 	 int colcount=rm.getColumnCount();	
   	
                    			 data2=new Object[2000][colcount];
		    			 names1=new Object[colcount];
		 
	            			 //retrive column names
				
		     			 m=0;
					 for(i=1;i<=colcount;i++)
						{
			  		
	                			   names1[m]=rm.getColumnName(i);
						   //System.out.println(names1[m]);
			 		    	   m++;
						}
			  		 System.out.println("colnames created");
 
		  			 //retrive row data
		  		 	  m=0;
		   		  	  n=0;
                   		  	  while(rs.next())
					    {

					        rowcount++;
			 
			 		         for(j=1;j<=colcount;j++)
			 		          {
						      data2[m][n]=rs.getString(j);
						     // System.out.println(data2[m][n]);
					     	      ++n;
			     		          }	
   
			 	             	  n=0;
			                     	  m++;
					     }



		      			     data3=new Object[rowcount][colcount];
		      		 	     for(i=0;i<rowcount;i++)
				  	       {
			  
	                  			 for(j=0;j<colcount;j++)
			  			 {
			   			   data3[i][j]=data2[i][j];
	                      	
			 			  }
				   	       }
 				 	      data2=null;
				 	       System.out.println("data3 len:"+data3.length);

						
					}
					catch(Exception e)
					   {

						System.out.println("Exception: "+e);
	    		  	                e.printStackTrace();
					   }
 					 bk=new JButton(" Back ");
					 bk.setBounds(5,10,100,30);
					 bk.addActionListener(this);



		                        //create a table
		   		 	 JTable jt=new JTable(data3,names1);
		    		 	 jt.setEnabled(false);
		  		 	 TableColumn  tc=jt.getColumn("NAME");
		  		 	 tc.setPreferredWidth(200);
		  		 	 TableColumn  tc1=jt.getColumn("YEAR");
		 		 	 tc1.setPreferredWidth(25);
 		 			  TableColumn  tc2=jt.getColumn("CPI");
		 	        	  tc2.setPreferredWidth(20);
		   			  TableColumn  tc3=jt.getColumn("DOB");
 		  			   tc3.setPreferredWidth(55);
                  			  TableColumn  tc4=jt.getColumn("BRANCH");
 		   			  tc4.setPreferredWidth(168);
 		   			  TableColumn  tc5=jt.getColumn("EMAIL");
 		    			  tc5.setPreferredWidth(168);
					  TableColumn  tc6=jt.getColumn("SSC");
 		  		  	  tc3.setPreferredWidth(25);
                  		  	  TableColumn  tc7=jt.getColumn("HSC");
 		   		 	  tc4.setPreferredWidth(25);
 		   		  	  TableColumn  tc8=jt.getColumn("DIPLOMA");
 		    		  	  tc5.setPreferredWidth(25);
                		
				 	jt.setRowHeight(30);

					exc=new JButton("Create Excel Sheet");
	         		  	exc.setBounds(1000,10,200,30);
				  	exc.addActionListener(this);	
		   			 JScrollPane js=new JScrollPane(jt,22,32);
		    			 JPanel p1=new JPanel();
		     
		   		  	p1.setLayout(null);
		   		  	js.setBounds(10,70,1200,600);
 	            		 	 p1.setBounds(0,0,1200,640);
 		    		  	p1.add(js);
					p1.add(bk);
					p1.add(exc);
				 
				  	tp.setComponentAt(a,p1);
				       
				}

			  }


                  else if(ae.getSource()==bk)
			 {
                                  tp.setComponentAt(a,vppanel);
				  tp.repaint();
				
			 }	
		  else if(ae.getSource()==exc)
			 {
				
				Object nm[];
				Object dt[][];
				Label l=null;
				File el=null;	
				String tt=tp.getTitleAt(tp.getSelectedIndex());
				System.out.println(tt);
				JLabel path=new JLabel();
			     try
				{

				if(tt=="Report")
				 {
			            nm=names;
				    dt=data1;
				    el=new File("/home/akki/server/Tproject/Report.xls");
				    path.setText("File stored in Location: /home/akki/server/Tproject/Report.xls");
				 }
				else
				{
					nm=names1;
					dt=data3;
					el=new File("/home/akki/server/Tproject/Search.xls");
					path.setText("File stored in Location: /home/akki/server/Tproject/Search.xls");
			        }

				System.out.println(nm.length);
				
                                 
				  WritableWorkbook wrb = Workbook.createWorkbook(el);

				 WritableSheet ws= wrb.createSheet("sheet1",0);
				 //ws.getCell(1,0).columnwidth=15;

				

				for(i=0;i<nm.length;i++)
				 {		
				     l=new Label(i,0,String.valueOf(nm[i]));
				     ws.addCell(l);
				    
				 }
				ws.setColumnView(0,35);
	 			ws.setColumnView(1,15);
				ws.setColumnView(2,25);
 				ws.setColumnView(3,32);
				ws.setColumnView(4,7);
 				ws.setColumnView(5,7);
                                ws.setColumnView(6,7);
				ws.setColumnView(8,7);
				ws.setColumnView(9,10);
 				ws.setColumnView(10,40);
				ws.setColumnView(11,12);
 				ws.setColumnView(12,80);
				m=1;
				 for(i=0;i<dt.length;i++)
				 {		
				    for(j=0;j<nm.length;j++)
				      {		
				          l=new Label(j,m,String.valueOf(dt[i][j]));
				          ws.addCell(l);
					
				       }
				    m++;
				 }   
				
				   
				 path.setBounds(700,40,600,30);
				JPanel tmp=(JPanel)tp.getComponentAt(tp.getSelectedIndex());
				tmp.add(path);
				tmp=null;
				tp.repaint();
				wrb.write();
				wrb.close();
			
				}
				catch(Exception e)
				{
					System.out.println("Exception :"+e);
					e.printStackTrace();
				}	
			 }
		  else
			  {
	   
	    			    a=tp.getSelectedIndex();
	   			    System.out.println("Index="+a);
	    			    tp.remove(a);
	     			    cnt--;
			}

  }

	//method for checking whether user selected any branch,course or not and entered correct cpi and year
	boolean validate(int course,int branch)
	 {
		boolean flag=true;
		                 int year=Integer.parseInt(yr.getText().trim()) ;
                          	 Float c=Float.parseFloat(cp.getText().trim()) ;
				if(year<1965 || year >2016)
				 {
					flag=false;
					txt8.setVisible(true);
					
				 }
				if(c <0.0 || c >10.0)
				{
					flag=false;
					txt10.setVisible(true);
					
				}
				if(branch==0)
				{		
					flag=false;
					hlp.setVisible(true);
				
				}
				if(course==0)
				{
					flag=false;
					txt9.setVisible(true);
						
				}
			
			
			
			
		return flag;
	 }
}
  
//thread for storing the client profile data
class Thread1 extends Thread
    
  {
          
     	 Socket clt;
     	 BufferedReader br;
    	 PrintStream ps;
 	 String str;
	 String data[]=new String[16];
	 String uname,passwd;

	Connection con;
	ResultSet  rs;
	Statement st;
        public Thread1(Socket input)
	 {
	    clt=input;
	    System.out.println("socket init");
         }
	
        public void run() 
	{
          
                   try
                   {
                         br=new BufferedReader(new InputStreamReader(clt.getInputStream()));
                         ps=new PrintStream(clt.getOutputStream());
     			System.out.println("streams created");

			   
	             }
                      catch(Exception e)
		    {
                                          System.out.println("Exception: "+e);
				  	 e.printStackTrace();
                    } 
      
                 
                 
              while(true)
	       {
                   try
                   {
			if(clt.isClosed()==true)
			 {
				System.out.println("SOCKET CLOSED");
				break;
			
			 }
                          str=br.readLine();
			 //System.out.println(str); 
	                 if(str.startsWith("Edit"))
			  {
				str=" ";
				System.out.println("In edit"); 
                                 for(int i=0;i<13;i++)
       			         {
               				data[i]=br.readLine();
					//System.out.println(data[i]);
                                 }
                             
                          //add profile data to database
                               try
   			       {
    			   	 
     
                           System.out.println("connecting to database.....");
                           Class.forName("oracle.jdbc.driver.OracleDriver");
			   System.out.println("drv connected"); 
        	           con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","SYSTEM","akshay");
                	   System.out.println("connected to Database"); 
           PreparedStatement st=con.prepareStatement("insert into student_db values(?,?,?,?,?,?,?,?,?,?,?,?,?)");

					st.setString(1,data[0]);
          			        st.setString(2,data[1]);
	   			        st.setString(3,data[2]);
					st.setString(4,data[3]);

	    				st.setInt(5,Integer.parseInt(data[4]));

					st.setFloat(6,Float.parseFloat(data[5]));
					
					//check wheteher hsc or diploma field is null 
				        if(data[6].startsWith("null"))
					  {
						//if hsc is null set then insert null to db
					    st.setFloat(7,(float)0.0);
					    st.setFloat(8,Float.parseFloat(data[7]));
					  }
					else
					 {
						st.setFloat(7,Float.parseFloat(data[6]));
					       st.setFloat(8,(float)0.0);
					 }
					
					


				        st.setFloat(9,Float.parseFloat(data[8]));


				        st.setDate(10,java.sql.Date.valueOf(data[9]));
                                        st.setString(11,data[10]);
					st.setLong(12,Long.parseLong(data[11]));
					st.setString(13,data[12]);
					
           				st.executeUpdate();
                 
					ps.println("OK"); 
 					System.out.println("Row inserted");
				        st.close();
      		  			con.close();
 					
                               }
      			       catch(Exception e)
       			       {
        				 System.out.println("Exception: "+e);
					 ps.println("ERROR");
				  	 e.printStackTrace();
         			} 
			  }
	                 else if(str.startsWith("MSG"))
			{
                            str=" ";
                            Thread2 th2=new Thread2(clt);
                            th2.start(); 
			}
        	        else if(str.startsWith("Auth"))
			{

				//verify student username and password

				uname=br.readLine();
				passwd=br.readLine();	
				try
				{
				  System.out.println("connecting to database.....");
                                  Class.forName("oracle.jdbc.driver.OracleDriver");
			          System.out.println("drv connected"); 
        	                  con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","SYSTEM","akshay");
                	          System.out.println("connected to Database");    
		   		  st=con.createStatement();
				  System.out.println("select * from student_passwd where username='"+uname+"'");

		  rs=st.executeQuery("select * from student_passwd where username='"+uname+"' and password='"+passwd+"'");
 				  if(rs.next())
				   {
					System.out.println(rs.getString(1)+" "+rs.getString(2)+ " "+uname+" "+passwd);
					String str=rs.getString(2);
					
						ps.println("VERIFIED");
						
						ps.println(rs.getString(3));
						ps.println(rs.getString(4));
						System.out.println("verified");
				   }	
				  else
				   {
					System.out.println("ERRor");
					ps.println("ERROR");
				   }
				  st.close();
      		  		  con.close();
				}
				catch(Exception e)
				{
				         System.out.println("Exception: "+e);
				  	 e.printStackTrace();
				}
			}
			else if(str.startsWith("UPDATE"))
			{
				str=" ";
				System.out.println("In UPDATE"); 
                                 for(int i=0;i<13;i++)
       			         {
               				data[i]=br.readLine();
					//System.out.println(data[i]);
                                 }
                             
                          //add profile data to database
                               try
   			       {
    			   	 
     
                           System.out.println("connecting to database.....");
                           Class.forName("oracle.jdbc.driver.OracleDriver");
			   System.out.println("drv connected"); 
        	           con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","SYSTEM","akshay");
                	   System.out.println("connected to Database"); 
				String qr="delete from student_db where roll_no='"+data[1]+"'";
			   PreparedStatement st=con.prepareStatement(qr);
			   System.out.println(qr);
			   st.executeUpdate();

                          st=con.prepareStatement("insert into student_db values(?,?,?,?,?,?,?,?,?,?,?,?,?)");

					st.setString(1,data[0]);
          			        st.setString(2,data[1]);
	   			        st.setString(3,data[2]);
					st.setString(4,data[3]);

	    				st.setInt(5,Integer.parseInt(data[4]));

					st.setFloat(6,Float.parseFloat(data[5]));
					
					//check wheteher hsc or diploma field is null 
				        if(data[6].startsWith("null"))
					  {
						//if hsc is null set then insert null to db
					    st.setFloat(7,(float)0.0);
					    st.setFloat(8,Float.parseFloat(data[7]));
					  }
					else
					 {
						st.setFloat(7,Float.parseFloat(data[6]));
					       st.setFloat(8,(float)0.0);
					 }
					
					


				        st.setFloat(9,Float.parseFloat(data[8]));


				        st.setDate(10,java.sql.Date.valueOf(data[9]));
                                        st.setString(11,data[10]);
					st.setLong(12,Long.parseLong(data[11]));
					st.setString(13,data[12]);
					
           				st.executeUpdate();
                 
					ps.println("OK"); 
 					System.out.println("Row inserted");
				        st.close();
      		  			con.close();
 					
                               }
      			       catch(Exception e)
       			       {
        				 System.out.println("Exception: "+e);
					 ps.println("ERROR");
				  	 e.printStackTrace();
         			} 
			}

	             }
                      catch(Exception e)
		    {
			 System.out.println("Exception At Thread: "+e);
				  	 e.printStackTrace();
				break;

                    } 
	       }
	}

    } 

//Thread2 checking whether client has any massage or not

   class Thread2 extends Thread
    {
      Socket clt;
    
     PrintStream ps;

        public Thread2(Socket input)
	 {
	    clt=input;
         }
	
        public void run() 
	{

                 
                 
              try

                   {
                      
                       ps=new PrintStream(clt.getOutputStream());
                       //check in database for messages and send it





                       System.out.println("thread 2");
                      
	             }
                      catch(Exception e)
		    {
                    } 
      
	}

    } 





class chatServer
{


        public static void main(String args[]) throws Exception
        {



              ServerSocket s=new ServerSocket(8030,2000);
    
	      System.out.println(s.getInetAddress());
              ClientObj cobj=new ClientObj();
 
 
               while(true)
	 	{
                   
                      Socket clt=s.accept();
                       System.out.println("socket connected");

                      Thread1 th1=new Thread1(clt);
                         th1.start();

                      
	          }
}

}			
