import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.*;
import javax.swing.*;
import java.text.*;

public class AddSubject extends JFrame implements ActionListener{
    static JTextField txtID,txtname,txtFees,txtBranch;
    static JButton btnAdd,btnCancel;
	static JComboBox Year,Course,Time,Day; 
	static JCheckBox branch_check[];
	static String[] year,course,time,day,s;
    static JPanel areapanel,bpanel,mpanel;
    static String username = "anonymous";
    static String password = "guest";
    static String url = "jdbc:odbc:Students";
    static Statement st;
    static Connection con;
	static int count=0,tot_fees=0,i=0,count1=0,a=0;
	static String msg="",msg1="";
    AddSubject(){
        super("ADD Subject");
		try {
       UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
	} catch (Exception e) {
		e.printStackTrace();
	}
        areapanel = new JPanel();
        areapanel.setLayout(new GridLayout(12,2,5,5));
        areapanel.add(new JLabel("UNIQUE ID"));
        areapanel.add(txtID = new JTextField(10));
        areapanel.add(new JLabel("Subject Name:"));
        areapanel.add(txtname = new JTextField(10));
        areapanel.add(new JLabel("Fees:"));
        areapanel.add(txtFees = new JTextField(10));
        areapanel.add(new JLabel("Day:"));
		String [] day={"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
        areapanel.add(Day = new JComboBox(day));
		areapanel.add(new JLabel("Course:"));
		String [] course={"Diploma","Degree"};
        areapanel.add(Course = new JComboBox(course));
        areapanel.add(new JLabel("Select Year"));
		String[] y1={"1","2","3"};
		areapanel.add(Year = new JComboBox(y1));
		areapanel.add(new JLabel("Select Time"));
		areapanel.add(Time = new JComboBox());
		areapanel.add(new JLabel("Enter Branch"));
		areapanel.add(txtBranch = new JTextField(10));
        areapanel.add(btnAdd = new JButton("Add Record"));
		areapanel.add(btnCancel = new JButton("Cancel"));
        bpanel = new JPanel();
        bpanel.setLayout(new BorderLayout());
        bpanel.add(areapanel,BorderLayout.NORTH);
        add(bpanel);
		init();
		call_time();
        btnAdd.addActionListener(this);
		btnCancel.addActionListener(this);
		Year.addActionListener(this);
		setVisible(true);
		setSize(500,500);
		//Branch.addActionListener(this);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == btnAdd){
    
			insertRecord();
			
        }
		if(e.getSource() == btnCancel)
		{
			new Base();
			this.dispose();
		}
	
    }
	    public static Connection getConnection(){
        try{
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            con = DriverManager.getConnection(url,username,password);
        }
        catch(java.lang.ClassNotFoundException e){
            System.err.println(e);
        }
        catch(SQLException ex){
            System.err.println(ex);
        }
        return con;
    }

     public static void insertRecord(){
        con = getConnection();
        String q = "Insert into course_details Values('"+ txtname.getText() +"','"+ txtBranch.getText() +"','"+ Day.getSelectedItem() +"','"+ Time.getSelectedItem() +"','"+ txtFees.getText() +"','"+ Year.getSelectedItem() +"','"+ msg1 +"','"+ Course.getSelectedItem() +"')";
        try{
            st= con.createStatement();
            st.executeUpdate(q);
            st.close();
            con.close();
        }
        catch(SQLException ex){
            System.err.println(ex);
        }
        JOptionPane.showMessageDialog(null,"Successfully inserted a record!");
        txtname.setText("");
        txtFees.setText("");
        txtBranch.setText("");
        txtID.setText("");
     }
   
	public static void init()
	{
		System.out.println("Called");
		con = getConnection();
		
		String q1="Select max(ID) as max from course_details";
        try{
            st= con.createStatement();
			ResultSet r1=st.executeQuery(q1);
			while(r1.next())
			{
				msg1=r1.getString("max");
				if(msg1==null)
					a=0;
				else
					a=Integer.parseInt(msg1);
				a++;
				msg1=a+"";
			}	
			txtID.setText(msg1);
			txtID.setEnabled(false);
		}
		catch(Exception e){
			System.err.println(e);
		}
	}
	public void call_time()
	{
				
				try
				{
						con = getConnection();			
						String query="Select distinct(Time) from course_details ";
						try
						{
								st= con.createStatement();
								ResultSet rs=st.executeQuery(query);
								while(rs.next())
								{
									String temp=rs.getString("Time");
									Time.addItem(temp);
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
			catch(Exception ob){System.out.println(ob+"123");}
	}
	
public static void main(String args[])
{
	new AddSubject();
}
}

