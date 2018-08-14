import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;

public class SearchStaff extends JFrame implements ActionListener
{
    static JTextField txtID,txtFname,txtLname,txtMname,txtPhone;
    static JButton btnUpdate,btnSearch,btnCancel;
    static JPanel areapanel,bpanel,tpanel;
    static String username = "anonymous";
    static String password = "guest";
    static String url = "jdbc:odbc:Students";
    static Statement st;
    static Connection con;
    SearchStaff()
	{
        super("Search the record");
			try {
       UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
	} catch (Exception e) {
		e.printStackTrace();
	}
        areapanel = new JPanel();

		
        areapanel.setLayout(new GridLayout(8,2,5,5));
        areapanel.add(new JLabel("UNIQUE ID"));
        areapanel.add(txtID = new JTextField(10));
        areapanel.add(btnSearch = new JButton("Search"));
		areapanel.add(btnCancel = new JButton("Cancel"));
        areapanel.add(new JLabel("Last Name:"));
        areapanel.add(txtLname = new JTextField(10));
        areapanel.add(new JLabel("First Name:"));
        areapanel.add(txtFname = new JTextField(10));
        areapanel.add(new JLabel("Middle Name:"));
        areapanel.add(txtMname = new JTextField(10));
        areapanel.add(new JLabel("Phone:"));
        areapanel.add(txtPhone = new JTextField(10));
        bpanel = new JPanel();
        bpanel.setLayout(new BorderLayout());
        bpanel.add(areapanel,BorderLayout.NORTH);
        add(bpanel);
        btnSearch.addActionListener(this);
		btnCancel.addActionListener(this);
		setVisible(true);
		setSize(500,500);
    }
    public static void retrieveRecords()
	{
        con = getConnection();
        String result = null;
        String fname = null,mname = null, lname = null, phone = null;
        String query = "Select * from tblStaff where ID like '" + txtID.getText() + "' or FirstName='" +txtID.getText()+ "'";
        try
		{
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next())
			{
                lname = rs.getString("LastName");
                fname = rs.getString("FirstName");
                mname = rs.getString("MiddleName");
                phone = rs.getString("Phone");
            }
        st.close();
        con.close();
        }
         catch(SQLException ex)
		 {
            System.err.println(ex.getMessage());
        }
        txtFname.setText(fname);
		txtFname.setEnabled(false);
        txtPhone.setText(phone);
		txtPhone.setEnabled(false);
        txtLname.setText(lname);
		txtLname.setEnabled(false);
        txtMname.setText(mname);
		txtMname.setEnabled(false);

        if(txtFname.getText().equals("") && txtMname.getText().equals("") && txtLname.getText().equals("") && txtPhone.getText().equals("")){
                JOptionPane.showMessageDialog(null,"Record Not Found!");
            
        txtFname.setText("");
        txtPhone.setText("");
        txtLname.setText("");
        txtMname.setText("");
        txtID.setText("");}
    }
     public static Connection getConnection()
	 {
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
    public void actionPerformed(ActionEvent e)
	{
        if(e.getSource() == btnSearch)
		{
            retrieveRecords();
        }
		if(e.getSource() == btnCancel)
		{
			this.dispose();
			new Base();
		}
    }
	public static void main(String args[])
	{
		new SearchStaff();
	}
}
