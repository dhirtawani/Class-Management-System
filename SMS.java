import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;
import java.io.*;
import java.net.*;

class SMS extends JFrame implements ActionListener
{
	static JTextArea msg;
	static JComboBox jbranch,jyear,jsubject;
	static String username = "anonymous";
    static String password = "guest";
    static String url = "jdbc:odbc:Students";
    static Statement st;
	static int count;
	static JButton btnSubmit,btnCancel;
	static String s[]={"CM","CO","IF"};;
    static Connection con;
	static StringBuffer phno;
	static String phno1="";
	JPanel areapanel,bpanel,mpanel,tpanel;
	SMS()
	{
		super("Pinnacle Management System");
		msg=new JTextArea(10,20);
        areapanel = new JPanel();
		mpanel=new JPanel();
		tpanel=new JPanel();
		bpanel=new JPanel();
        areapanel.setLayout(new GridLayout(1,6,5,5));
		areapanel.add(new JLabel("Select Year"));
		String[] y={"1","2","3"};
		areapanel.add(jyear=new JComboBox(y));
		areapanel.add(new JLabel("Select Branch"));
		areapanel.add(jbranch =new JComboBox(s));
		areapanel.add(new JLabel("Select Subject"));
		areapanel.add(jsubject=new JComboBox());
		mpanel.add(new JLabel("Enter Message here"));
		mpanel.add(msg);
		msg.setLineWrap(true);
        bpanel.setLayout(new BorderLayout());
        bpanel.add(areapanel,BorderLayout.NORTH);
		bpanel.add(mpanel,BorderLayout.CENTER);
		tpanel.add(btnSubmit=new JButton("Submit"));
		tpanel.add(btnCancel=new JButton("Cancel"));
		bpanel.add(tpanel,BorderLayout.SOUTH);
        add(bpanel);
        btnSubmit.addActionListener(this);
		btnCancel.addActionListener(this);
		jbranch.addActionListener(this);
		jsubject.addActionListener(this);
		jyear.addActionListener(this);
		setVisible(true);
		setSize(300,300);
		
	}
	public static void main(String args[])
	{
		new SMS();
	}
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==jyear)
		{
			call_branch();
		}
		if(ae.getSource()==jbranch)
		{
		int itemCount = jsubject.getItemCount();
		for(int i=0;i<itemCount;i++){
		jsubject.removeItemAt(0);}
			call_Sub();
		}
		if(ae.getSource()==btnCancel)
		{
			new Base();
			this.dispose();
		}
		if(ae.getSource()==btnSubmit)
		{
			con=getConnection();
			String query="Select Phone from tblStudents where id in( select id from subjects where subjects='"+ jsubject.getSelectedItem() + "')";
						try
						{
								st= con.createStatement();
								ResultSet rs=st.executeQuery(query);
								while(rs.next())
								{
										phno1+= "91" +rs.getString("Phone") + ",";
								}
									st.close();
				con.close();
				System.out.println(phno1);
				phno=new StringBuffer(phno1);
				System.out.println(phno.length());
				phno1=phno.substring(0,(phno.length()-1));
						}
						catch(SQLException ex)
						{
							System.out.println("jjks67");
							System.err.println(ex);
						}
						sendMessage();
			}
	
		}
	public void call_branch()
	{
				
				try
				{
						con = getConnection();			
						String query="Select distinct(Branches) from course_details ";
						try
						{
								st= con.createStatement();
								ResultSet rs=st.executeQuery(query);
								while(rs.next())
								{
									count++;
								}
								s=new String[count];
								int i=0;
								rs=st.executeQuery(query);
								while(rs.next())
								{
									String temp=rs.getString("Branches");
									if(temp.equals("All"))
									{}
								else{
									jbranch.addItem(temp);
									}
								} 
						}
						catch(SQLException ex)
						{
							System.out.println("jjks");
							System.err.println(ex);
						}
				
		
				st.close();
				con.close();
			}
			catch(Exception ob){System.out.println(ob);}
	
	}
	public void call_Sub()
	{

				try
				{
						con = getConnection();			
						String query="Select Subjects from course_details where Year='"+jyear.getSelectedItem()+"'and (Branches='"+jbranch.getSelectedItem()+"')";
						try
						{
								st= con.createStatement();
								ResultSet rs=st.executeQuery(query);
								while(rs.next())
								{
									jsubject.addItem(rs.getString("Subjects"));
								} 
						}
						catch(SQLException ex)
						{
							System.out.println("jjks67");
							System.err.println(ex);
						}
				
		
				st.close();
				con.close();
			}
			catch(Exception ob){System.out.println(ob);}
	
	}
	     public static Connection getConnection(){
        try{
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            con = DriverManager.getConnection(url,username,password);
        }
        catch(java.lang.ClassNotFoundException e){
            System.err.println(e.getMessage());
        }
        catch(SQLException ex){
            System.err.println(ex.getMessage());
        }
        return con;
    }
	public void sendMessage()
	{
		//Your authentication key
String authkey = "99471ATtECiptFe5682e1b8";
//Multiple mobiles numbers separated by comma
String mobiles = phno1;
//Sender ID,While using route4 sender id should be 6 characters long.
String senderId = "PINACD";
//Your message to send, Add URL encoding here.
String message = msg.getText();
//define route
String route="4";

//Prepare Url
URLConnection myURLConnection=null;
URL myURL=null;
BufferedReader reader=null;

//encoding message 
String encoded_message=URLEncoder.encode(message);

//Send SMS API
String mainUrl="http://api.msg91.com/api/sendhttp.php?";

//Prepare parameter string 
StringBuilder sbPostData= new StringBuilder(mainUrl);
sbPostData.append("authkey="+authkey); 
sbPostData.append("&mobiles="+mobiles);
sbPostData.append("&message="+encoded_message);
sbPostData.append("&route="+route);
sbPostData.append("&sender="+senderId);

//final string
mainUrl = sbPostData.toString();
try
{
    //prepare connection
    myURL = new URL(mainUrl);
    myURLConnection = myURL.openConnection();
    myURLConnection.connect();
    reader= new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));
    //reading response 
    String response;
    while ((response = reader.readLine()) != null) 
    //print response 
    System.out.println(response);
    
    //finally close connection
    reader.close();
} 
catch (IOException e) 
{ 
	e.printStackTrace();
} 
	}

	
}