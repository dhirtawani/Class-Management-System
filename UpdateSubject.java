import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;

public class UpdateSubject extends JFrame implements ActionListener{

    static JTextField txtID,txtname,txtFees,txtBranch;
	static JComboBox Year,Course,Time1,Day; 
	static JButton btnUpdate,btnSearch,btnCancel;
    static JPanel areapanel,bpanel,tpanel,mpanel;
    static String username = "anonymous";
    static String password = "guest";
    static String url = "jdbc:odbc:Students",msg="",msg1="";
    static Statement st;
    static Connection con;
	static int count=0,c=0,i=0,tot_fees=0,j=0,bal=0;

    UpdateSubject(){
        super("Search the record to update. . .");
		try {
       UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
	} catch (Exception e) {
		e.printStackTrace();
	}
        areapanel = new JPanel();
        areapanel.setLayout(new GridLayout(14,2,5,5));
        areapanel.add(new JLabel("UNIQUE ID"));
        areapanel.add(txtID = new JTextField(10));
		areapanel.add(new JLabel(""));
		areapanel.add(btnSearch = new JButton("Search"));
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
		areapanel.add(Time1 = new JComboBox());
		areapanel.add(new JLabel("Enter Branch"));
		areapanel.add(txtBranch = new JTextField(10));
        areapanel.add(btnUpdate = new JButton("Update Record"));
		areapanel.add(btnCancel = new JButton("Cancel"));
        bpanel = new JPanel();
        bpanel.setLayout(new BorderLayout());
        bpanel.add(areapanel,BorderLayout.NORTH);
        add(bpanel);
		call_time();
        btnUpdate.addActionListener(this);
		btnCancel.addActionListener(this);
		btnSearch.addActionListener(this);
		Year.addActionListener(this);
		setVisible(true);
		setSize(500,500);
    }
    public static void retrieveRecords(){
		System.out.println("Called");
        con = getConnection();
        String result = null;
        String name=null,branch=null,day=null,time=null,fee=null,year=null,id=null,course=null;
        String query = "Select * from course_details where id ='" + txtID.getText() + "'";
        try{
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                name = rs.getString("Subjects");
                branch = rs.getString("Branches");
                day = rs.getString("Days");
                course = rs.getString("Course");
				time = rs.getString("Time");
				fee = rs.getString("Fees");
				year = rs.getString("Year");
				id = rs.getString("id");
            }
        st.close();
        con.close();
        }
         catch(SQLException ex){
            System.err.println(ex.getMessage());
        }
		System.out.println(name+""+branch);
        txtname.setText(name);
        txtBranch.setText(branch);
        txtFees.setText(fee);
        txtID.setText(id);
		
        if(txtname.getText().equals("") && txtFees.getText().equals("") && txtBranch.getText().equals("") && txtID.getText().equals("")){
                JOptionPane.showMessageDialog(null,"Record Not Found!");
            
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

      public static void updateRecords(){
        con = getConnection();
        String update1 = "Update course_details set Subjects = '" + txtname.getText() +"' where ID = '" + txtID.getText() + "'" ;
        String update2 = "Update course_details set Branches = '" + txtBranch.getText() +"' where ID = '" + txtID.getText() + "'" ;
        String update3 = "Update course_details set Fees = '" + txtFees.getText() +"' where ID = '" + txtID.getText() + "'" ;
        String update4 = "Update course_details set Course = '" + Course.getSelectedItem() +"' where ID = '" + txtID.getText() + "'" ;
		String update5 = "Update course_details set Year = '" + Year.getSelectedItem() +"' where ID = '" + txtID.getText() + "'" ;
		String update6 = "Update course_details set Days = '" + Day.getSelectedItem() +"' where ID = '" + txtID.getText() + "'" ;
        try{
			
            st = con.createStatement();
            st.executeUpdate(update1);
            st.executeUpdate(update2);
            st.executeUpdate(update3);
            st.executeUpdate(update4);
			st.executeUpdate(update5);
			st.executeUpdate(update6);
            st.close();
            con.close();
        }
        catch(SQLException ex){
            System.err.println(ex.getMessage());
        }
        JOptionPane.showMessageDialog(null,"Update Finished!");
        txtname.setText("");
        txtFees.setText("");
        txtBranch.setText("");
        txtID.setText("");
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == btnSearch){
            retrieveRecords();
	
        }
        if(e.getSource() == btnUpdate){
		updateRecords();
        }
    }
	public static void main(String args[])
	{
		new UpdateSubject();
	}
	public static void call_time()
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
									Time1.addItem(temp);
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
}
