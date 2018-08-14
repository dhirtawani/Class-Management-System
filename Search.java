import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;

public class Search extends JFrame implements ActionListener
{
	JScrollPane scrollPane;
	JFrame f1;
	Vector columnNames = new Vector();
	Vector data = new Vector();
    static JTable table;
	static JCheckBox j[];
	static JComboBox jbranch,jyear,jsubject;
	static JButton btnSubmit;
    static JPanel areapanel,bpanel,mpanel;
    static String username = "anonymous";
    static String password = "guest";
    static String url = "jdbc:odbc:Students";
    static Statement st;
	static int count;
	static String s[]={"CM","CO","IF"};
    static Connection con;
    Search()
	{
		super("Pinnacle Management System");
	}
	void GUI()
	{
		f1=new JFrame();
        areapanel = new JPanel();
        areapanel.setLayout(new GridLayout(2,6,5,5));
		areapanel.add(new JLabel("Select Year"));
		String[] y={"1","2","3"};
		areapanel.add(jyear=new JComboBox(y));
		areapanel.add(new JLabel("Select Branch"));
		areapanel.add(jbranch =new JComboBox());
		areapanel.add(new JLabel("Select Subject"));
		areapanel.add(jsubject=new JComboBox());
		areapanel.add(new JLabel());
		areapanel.add(new JLabel());
		areapanel.add(new JLabel());
		areapanel.add(btnSubmit=new JButton("Search"));
		areapanel.add(new JLabel());
		bpanel = new JPanel();
		table = new JTable(data, columnNames);
		scrollPane = new JScrollPane(table);
        bpanel.setLayout(new BorderLayout());
        bpanel.add(areapanel,BorderLayout.NORTH);
		bpanel.add(scrollPane,BorderLayout.CENTER);
        f1.add(bpanel);
        btnSubmit.addActionListener(this);
		jbranch.addActionListener(this);
		jyear.addActionListener(this);
		btnSubmit.addActionListener(this);
		f1.setVisible(true);
		f1.setSize(500,500);
    }
	public void actionPerformed(ActionEvent ae){
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
		if(ae.getSource()==btnSubmit)
			calltable();
		
			
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
						//System.out.println("1");
						String query="Select Subjects from course_details where Year='"+jyear.getSelectedItem()+"'and Branches='"+jbranch.getSelectedItem()+"'";
						try
						{
								st= con.createStatement();
							//	System.out.println("2" );
								ResultSet rs=st.executeQuery(query);
								while(rs.next())
								{
								//	System.out.println("3" );
									String temp=(rs.getString("Subjects"));
									//System.out.println(temp);
									jsubject.addItem(temp);
									//System.out.println("4" );
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
			catch(Exception ob){
				System.out.println( "akakn");
				System.out.println(ob);}
			
	
	}
	public void calltable()
	{
		con=getConnection();
		int flag=0,columns=0;
		String query="select id,FirstName,bal,Phone from tblStudents where id in(select id from subjects where subjects='"+ jsubject.getSelectedItem() + "')";
		try 
		{
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery( query );
			
			if(flag==0)
			{
				columnNames.removeAllElements();
			ResultSetMetaData md = rs.getMetaData();
			columns = md.getColumnCount();
			for (int i = 1; i <= columns; i++) 
			{
				columnNames.addElement( md.getColumnName(i) );
			}
			flag=1;
			}
			data.removeAllElements();
			while (rs.next()) 
			{
				Vector row = new Vector(columns);
				for (int i = 1; i <= columns; i++)
				{
					row.addElement( rs.getObject(i) );
				}
				data.addElement( row );
			}
			rs.close();
			stmt.close();
		}
		catch(Exception e) 
		{
			System.out.println( e );
		}
		
		f1.setVisible(false);
		scrollPane.remove(table);
		
		table = new JTable(data, columnNames);
		scrollPane = new JScrollPane( table );
		f1.dispose();
		GUI();
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
	
	public static void main(String args[])
	{
		Search a=new Search();
		a.GUI();
	}
}
