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

public class AddRecord extends JFrame implements ActionListener{
	static JTextField txtID,txtFname,txtLname,txtMname,txtFees,txtBal,txtTot,txtCollege,txtPhone;
    static JButton btnAdd,btnCancel;
	static JComboBox year,Branch,Course; 
	static JCheckBox sub_check[]=new JCheckBox[6];
	static String[] sub;
    static JPanel areapanel,bpanel;
    static String username = "anonymous";
    static String password = "guest";
    static String url = "jdbc:odbc:Students";
    static Statement st;
    static Connection con;
	static JCheckBox sub1,sub2,sub3,sub4,sub5,sub6;
	static int count=0,tot_fees=0,i=0,count1=0,a=0;
	static String msg="",msg1="";
    AddRecord(){
        super("ADD RECORD");
		try {
       UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
	} catch (Exception e) {
		e.printStackTrace();
	}
        areapanel = new JPanel();
        areapanel.setLayout(new GridLayout(13,2,5,5));
        areapanel.add(new JLabel("UNIQUE ID"));
        areapanel.add(txtID = new JTextField(10));
        areapanel.add(new JLabel("Last Name:"));
        areapanel.add(txtLname = new JTextField(10));
        areapanel.add(new JLabel("First Name:"));
        areapanel.add(txtFname = new JTextField(10));
        areapanel.add(new JLabel("Middle Name:"));
        areapanel.add(txtMname = new JTextField(10));
        areapanel.add(new JLabel("Branch:"));
		String [] b={"CM","CO","EJ","ET"};
        areapanel.add(Branch = new JComboBox(b));
		areapanel.add(new JLabel("Course:"));
		String [] s={"Diploma","Degree"};
        areapanel.add(Course = new JComboBox(s));
		areapanel.add(new JLabel("College:"));
        areapanel.add(txtCollege = new JTextField(10));
		areapanel.add(new JLabel("PhoneNo:"));
		areapanel.add(txtPhone = new JTextField(10));
        areapanel.add(new JLabel("Select Year"));
		String[] y1={"1","2","3"};
		areapanel.add(year = new JComboBox(y1));
		for(i=0;i<sub_check.length;i++){
		sub_check[i]=new JCheckBox();	
		areapanel.add(sub_check[i]);
		sub_check[i].setVisible(false);
		}
		
        areapanel.add(btnAdd = new JButton("Add Record"));
		areapanel.add(btnCancel = new JButton("Cancel"));
        bpanel = new JPanel();
        bpanel.setLayout(new BorderLayout());
        bpanel.add(areapanel,BorderLayout.NORTH);
        add(bpanel);
		init();

        btnAdd.addActionListener(this);
		btnCancel.addActionListener(this);
		year.addActionListener(this);
		Branch.addActionListener(this);
		setVisible(true);
		setSize(400,600);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == btnAdd){
            select();
			insertRecord();
			
        }
		if(e.getSource() == btnCancel)
		{
			new Base();
			this.dispose();
		}
		if(e.getSource() == year){
			for(i=0;i<count1;i++)
				sub[i]="";
			System.out.println(year.getSelectedItem());
			retrieveRecords();
		}
		if(e.getSource() == Branch){
			for(i=0;i<count1;i++)
				sub[i]="";
			System.out.println(Branch.getSelectedItem());
			retrieveRecords();
		}
    }
	public void select()
	{
			tot_fees=0;
			try
			{
				con = getConnection();
				System.out.println(count1);
				for(int i=0;i<count1;i++)
				{
					if(sub_check[i].isSelected())
					{
						msg=sub_check[i].getText()+"";			
						String q = "Insert into subjects values('" + txtID.getText() +"','" + msg + "')";
						System.out.println(q);
						String query="Select Fees from course_details where Subjects='" + msg + "' and Branches='" + Branch.getSelectedItem() + "'";
						System.out.println(query);
						try
						{
								st= con.createStatement();
								st.executeUpdate(q);
								ResultSet rs=st.executeQuery(query);
								while(rs.next())
								{
									int fees=Integer.parseInt(rs.getString("Fees"));
									System.out.println(fees);
									tot_fees  = tot_fees + fees;
								} 
						}
						catch(SQLException ex)
						{
							System.err.println(ex);
						}
					}
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
            System.err.println(e);
        }
        catch(SQLException ex){
            System.err.println(ex);
        }
        return con;
    }

     public static void insertRecord(){
        con = getConnection();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd ");
        String q = "Insert into tblStudents Values('"+ msg1 +"','" + txtLname.getText() + "','" + txtFname.getText() + "','" + txtMname.getText() + "','" + Branch.getSelectedItem() + "','" + Course.getSelectedItem() + "','"+ tot_fees + "','" + txtCollege.getText() + "','"+0+"','"+tot_fees+"','" + year.getSelectedItem() + "','"+dateFormat.format(new Date())+"','"+ txtPhone.getText() +"','"+0+"')";
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
        txtFname.setText("");
        txtLname.setText("");
        txtMname.setText("");
        txtID.setText("");
		txtPhone.setText("");
		txtCollege.setText("");
     }
	 public static void retrieveRecords(){
        con = getConnection();

		sub=new String[5];
		for(i=0;i<5;i++)
		{		
			sub[i]="";
			sub_check[i].setVisible(false);
		}
        String query = "Select * from course_details where Year = '" + year.getSelectedItem() + "' and Branches = '" + Branch.getSelectedItem() + "' ";
        try{
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
				
				sub[count]=new String(rs.getString("Subjects"));
                System.out.println(sub[count++]);
            }
        st.close();
        con.close();
        }
         catch(SQLException ex){
            System.err.println(ex);
        }
     try{
		 System.out.println(count+"  "+sub.length);
			count1=count;
			System.out.println(count1);
		for (int i = 0; i <count; i++) 
            { 
					sub_check[i].setText(sub[i]);
					sub_check[i].setVisible(true);
            }
			count=0;
		}
		catch(Exception e)
		{}

        
    }
	public static void init()
	{
		System.out.println("Called");
		con = getConnection();
		
		String q1="Select max(ID) as max from tblStudents";
        try{
            st= con.createStatement();
			System.out.println("Called1");
			ResultSet r1=st.executeQuery(q1);
			System.out.println("Called2");
			while(r1.next())
			{
				msg1=r1.getString("max");
				if(msg1==null)
					a=0;
				else
					a=Integer.parseInt(msg1);
				System.out.println(a);
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
	public static void main(String args[])
	{
		new AddRecord();
	}
}
