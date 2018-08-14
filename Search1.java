import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;

public class Search1 extends JFrame implements ActionListener
{
	static String id=null,fname = null,tot = null, paid = null, bal = null,branch=null,pay=null,chq_no=null;
	static JButton btnSubmit,btnCancel,btnSearch;
	static JTextField txtSearch,txtID,txtFname,txtLname,txtMname,txtBranch,txtTot,txtBal,txtPaid,txtPay,txtchqno;
	static JRadioButton cash,chq;
    static JPanel areapanel,bpanel,mpanel;
	static JLabel j1;
    static String username = "anonymous";
    static String password = "guest";
    static String url = "jdbc:odbc:Students";
    static Statement st;
	static int count;
	static String s[]={"CM","CO","IF"};;
    static Connection con;
    Search1()
	{
        super("Pinnacle Management System");
			try {
       UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
	} catch (Exception e) {
		e.printStackTrace();
	}	
		
        areapanel = new JPanel();
        areapanel.setLayout(new GridLayout(14,2,5,5));
		areapanel.add(new JLabel("Enter Id Or Name"));
		areapanel.add(txtSearch=new JTextField());
		btnSearch=new JButton("Search");
		areapanel.add(btnSearch);
		areapanel.add(btnCancel=new JButton("Cancel"));
		bpanel = new JPanel();
		areapanel.add(new JLabel("ID"));
		areapanel.add(txtID=new JTextField());
		areapanel.add(new JLabel("Name"));
		areapanel.add(txtFname=new JTextField());
		areapanel.add(new JLabel("Branch"));
		areapanel.add(txtBranch=new JTextField());
		areapanel.add(new JLabel("Total Fees"));
		areapanel.add(txtTot=new JTextField());
		areapanel.add(new JLabel("Fees Paid"));
		areapanel.add(txtPaid=new JTextField());
		areapanel.add(new JLabel("Balance"));
		areapanel.add(txtBal=new JTextField());
        bpanel.setLayout(new BorderLayout());
        bpanel.add(areapanel,BorderLayout.NORTH);
        add(bpanel);
		btnSearch.addActionListener(this);
		btnCancel.addActionListener(this);
		setVisible(true);
		setSize(500,600);
    }
	public void actionPerformed(ActionEvent ae){
		String str =ae.getActionCommand();
		if(ae.getSource()== btnCancel)
		{
			this.dispose();
			new Base();
		}
		if(ae.getSource()== btnSearch)
		{
			retrieveFees();
		}
		
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
	public static void retrieveFees(){
        con = getConnection();
        String s=txtSearch.getText();
        String query = "Select * from tblStudents where ID = '" + s + "' or FirstName = '" + s + "'";
		System.out.println(query);
        try{
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
				id=rs.getString("ID");
				branch=rs.getString("Branch");
                fname = rs.getString("FirstName");
                tot = rs.getString("tot_fees");
                paid = rs.getString("fees_paid");
                bal = rs.getString("bal");
            }
			
        st.close();
        con.close();
        }

        catch(SQLException ex){
            System.err.println(ex.getMessage());
        }
			txtID.setText(id);
			txtID.setEnabled(false);
            txtFname.setText(fname);
			txtFname.setEnabled(false);
            txtTot.setText(tot);
			txtTot.setEnabled(false);
            txtPaid.setText(paid);
			txtPaid.setEnabled(false);
			txtBal.setText(bal);
			txtBal.setEnabled(false);
            txtBranch.setText(branch);
			txtBranch.setEnabled(false);
           
    }
	public static void main(String args[])
	{
		new Search1();
	}
}

