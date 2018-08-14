import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;

public class UpdateRecord1 extends JFrame implements ActionListener
{
    static JTextField txtID,txtFname,txtLname,txtMname,txtPhone;
    static JButton btnUpdate,btnSearch,btnCancel;
    static JPanel areapanel,bpanel,tpanel;
    static String username = "anonymous";
    static String password = "guest";
    static String url = "jdbc:odbc:Students";
    static Statement st;
    static Connection con;
    UpdateRecord1()
	{
        super("Search the record to update. . .");
			try {
       UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
	} catch (Exception e) {
		e.printStackTrace();
	}
        areapanel = new JPanel();
        tpanel = new JPanel();
        tpanel.setLayout(new GridLayout(1,1));
        tpanel.add(btnUpdate = new JButton("Update Record"));
		tpanel.add(btnCancel = new JButton("Cancel"));
        areapanel.setLayout(new GridLayout(6,2,5,5));
        areapanel.add(new JLabel("UNIQUE ID"));
        areapanel.add(txtID = new JTextField(10));
        areapanel.add(new JLabel(""));
        areapanel.add(btnSearch = new JButton("Search"));
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
        bpanel.add(tpanel,BorderLayout.CENTER);   
        add(bpanel);
        btnSearch.addActionListener(this);
        btnUpdate.addActionListener(this);
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
      public static void updateRecords()
	  {
        con = getConnection();
        String update1 = "Update tblStaff set FirstName = '" + txtFname.getText() +"' where ID = '" + txtID.getText() + "'" ;
        String update2 = "Update tblStaff set MiddleName = '" + txtMname.getText() +"' where ID = '" + txtID.getText() + "'" ;
        String update3 = "Update tblStaff set LastName = '" + txtLname.getText() +"' where ID = '" + txtID.getText() + "'" ;
        String update4 = "Update tblStaff set Phone = '" + txtPhone.getText() +"' where ID = '" + txtID.getText() + "'" ;
        try
		{
            st = con.createStatement();
            st.executeUpdate(update1);
            st.executeUpdate(update2);
            st.executeUpdate(update3);
            st.executeUpdate(update4);
            st.close();
            con.close();
        }
        catch(SQLException ex)
		{
            System.err.println(ex.getMessage());
        }
        JOptionPane.showMessageDialog(null,"Update Finished!");
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
        if(e.getSource() == btnUpdate)
		{
            updateRecords();
        }
		if(e.getSource() == btnCancel)
		{
			this.dispose();
			new Base();
		}
    }
	public static void main(String args[])
	{
		new UpdateRecord1();
	}
}
