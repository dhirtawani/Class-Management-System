import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;

public class Fees extends JFrame implements ActionListener
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
    Fees()
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
		areapanel.add(new JLabel());
		btnSearch=new JButton("Search");
		areapanel.add(btnSearch);
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
		areapanel.add(new JLabel("Enter the amount Paid"));
		areapanel.add(txtPay=new JTextField());
		areapanel.add(new JLabel("Paymode"));
		areapanel.add(new JLabel());
		areapanel.add(cash=new JRadioButton("Cash",true));
		areapanel.add(chq=new JRadioButton("Cheque"));
		areapanel.add(j1=new JLabel("Enter Cheque Number"));
		j1.setVisible(false);
		areapanel.add(txtchqno=new JTextField());
		txtchqno.setVisible(false);
		btnSubmit=new JButton("Submit");
		areapanel.add(btnSubmit);
		ButtonGroup b=new ButtonGroup();
		b.add(cash);
		b.add(chq);
		areapanel.add(btnCancel=new JButton("Cancel"));
		bpanel = new JPanel();
        bpanel.setLayout(new BorderLayout());
        bpanel.add(areapanel,BorderLayout.NORTH);
        add(bpanel);
		btnSearch.addActionListener(this);
		btnCancel.addActionListener(this);
		chq.addActionListener(this);
		cash.addActionListener(this);
		btnSubmit.addActionListener(this);
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
		if(ae.getSource()== btnSubmit)
		{
			pay_fees();
		}
		if(ae.getSource()== btnSearch)
		{
			retrieveFees();
		}
		if(ae.getSource()==cash)
		{
			chq_no=null;
			j1.setVisible(false);
			txtchqno.setVisible(false);
		}
		if(ae.getSource()==chq)
		{
			chq_no=txtchqno.getText();
			j1.setVisible(true);
			txtchqno.setVisible(true);
		}
	}
	public void pay_fees()
	{
		chq_no=txtchqno.getText();
		System.out.println("Exec");
			try
				{
				System.out.println(txtPay.getText());
				pay=txtPay.getText();
				
				int paid1=Integer.parseInt(paid)+Integer.parseInt(pay);
				paid=Integer.toString(paid1);
				int bal1=Integer.parseInt(tot)-Integer.parseInt(paid);
				bal=Integer.toString(bal1);
			System.out.println("56");
						con = getConnection();			
						String query="Update tblStudents set fees_paid='"+ paid + "',bal='"+ bal +"',Cheque_no='" +chq_no+ "' where ID='"+txtID.getText()+"'";
						try
						{
								st= con.createStatement();
								st.executeUpdate(query);
								
						}
						catch(SQLException ex)
						{
							System.out.println("jjks");
							System.err.println(ex);
						}
				
				txtchqno.setText("");
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
		new Fees();
	}
}

