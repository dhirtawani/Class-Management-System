import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;

public class DeleteRecord1 extends JFrame implements ActionListener
{
    static JTextField txtID;
	static JLabel txtFname,txtLname,txtMname,txtPhone;
    static JButton btnDelete,btnSearch,btnCancel;
    static JPanel areapanel,bpanel,tpanel;
    static String username = "anonymous";
    static String password = "guest";
    static String url = "jdbc:odbc:Students";
    static Statement st;
    static Connection con;
    DeleteRecord1()
	{
        super("Search the record to Delete. . .");
			try {
       UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
	} catch (Exception e) {
		e.printStackTrace();
	}
        areapanel = new JPanel();
        tpanel = new JPanel();
        tpanel.setLayout(new GridLayout(1,2,5,5));
        tpanel.add(btnDelete = new JButton("Delete Record"));
		tpanel.add(btnCancel = new JButton("Cancel Record"));
        areapanel.setLayout(new GridLayout(6,2,5,5));
        areapanel.add(new JLabel("UNIQUE ID"));
        areapanel.add(txtID = new JTextField(10));
        areapanel.add(new JLabel(""));
        areapanel.add(btnSearch = new JButton("Search"));
        areapanel.add(new JLabel("Last Name:"));
        areapanel.add(txtLname = new JLabel());
        areapanel.add(new JLabel("First Name:"));
        areapanel.add(txtFname = new JLabel());
        areapanel.add(new JLabel("Middle Name:"));
        areapanel.add(txtMname = new JLabel());
        areapanel.add(new JLabel("PhoneNo:"));
        areapanel.add(txtPhone = new JLabel());
        bpanel = new JPanel();
        bpanel.setLayout(new BorderLayout());
        bpanel.add(areapanel,BorderLayout.NORTH);
        bpanel.add(tpanel,BorderLayout.CENTER);
        add(bpanel);
        btnSearch.addActionListener(this);
		btnCancel.addActionListener(this);
        btnDelete.addActionListener(this);
		setVisible(true);
		setSize(500,500);
    }
    public static void retrieveRecords()
	{
        con = getConnection();
        String result = null;
        String fname = null,mname = null, lname = null, phone = null;
        String query = "Select * from tblStaff where ID like '" + txtID.getText() + "' or FirstName='" +txtID.getText()+ "'";
        try{
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                lname = rs.getString("LastName");
                fname = rs.getString("FirstName");
                mname = rs.getString("MiddleName");
                phone = rs.getString("Phone");
                //result = name;
            }
        st.close();
        con.close();
        }
        catch(SQLException ex)
		{
            System.err.println(ex.getMessage());
        }
        txtFname.setText(fname);
        txtPhone.setText(phone);
        txtLname.setText(lname);
        txtMname.setText(mname);

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
        try
		{
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
      public static void deleteRecords()
	  {
        con = getConnection();
        String delete1 = "Delete from tblStaff where ID ='"+ txtID.getText() +"' or FirstName='" +txtID.getText()+ "'" ;
        try
		{
            st = con.createStatement();
            st.executeUpdate(delete1);
            st.close();
            con.close();
        }
        catch(SQLException ex)
		{
            System.err.println(ex.getMessage());
        }
        JOptionPane.showMessageDialog(null,"Delete Finished!");
        txtFname.setText("");
        txtPhone.setText("");
        txtLname.setText("");
        txtMname.setText("");
        txtID.setText("");
    }
    public void actionPerformed(ActionEvent e)
	{
        if(e.getSource() == btnSearch)
		{
            retrieveRecords();
        }
        if(e.getSource() == btnDelete)
		{
            deleteRecords();
        }
		if(e.getSource() ==btnCancel)
		{
			this.dispose();
			new Base();
		}
    }
	public static void main(String args[])
	{
		new DeleteRecord1();
	}
}
