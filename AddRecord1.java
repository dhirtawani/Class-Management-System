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

public class AddRecord1 extends JFrame implements ActionListener{
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
    AddRecord1(){
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
		areapanel.add(new JLabel("PhoneNo:"));
		areapanel.add(txtPhone = new JTextField(10));
		areapanel.add(btnAdd = new JButton("Add Record"));
		areapanel.add(btnCancel = new JButton("Cancel"));
        bpanel = new JPanel();
        bpanel.setLayout(new BorderLayout());
        bpanel.add(areapanel,BorderLayout.NORTH);
        add(bpanel);
		init();

        btnAdd.addActionListener(this);
		btnCancel.addActionListener(this);
		setVisible(true);
		setSize(400,600);
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
        String q = "Insert into tblStaff Values('"+ msg1 +"','" + txtLname.getText() + "','" + txtFname.getText() + "','" + txtMname.getText() + "',"+ txtPhone.getText() +")";
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
     }
	public static void init()
	{
		System.out.println("Called");
		con = getConnection();
		
		String q1="Select max(ID) as max from tblStaff";
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
		new AddRecord1();
	}
}
