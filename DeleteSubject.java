import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;

public class DeleteSubject extends JFrame implements ActionListener{

    static JTextField txtID;
	static JTextField txtname,txtYear,txtCourse,txtBranch,txtFees,txtid1;
    static JButton btnDelete,btnSearch;
    static JPanel areapanel,bpanel,tpanel;
    static String username = "anonymous";
    static String password = "guest";
    static String url = "jdbc:odbc:Students";
    static Statement st;
    static Connection con;

    DeleteSubject(){
        super("Search the record to Delete. . .");
			try {
       UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
	} catch (Exception e) {
		e.printStackTrace();
	}
        areapanel = new JPanel();
        tpanel = new JPanel();
        tpanel.setLayout(new GridLayout(1,1));
        tpanel.add(btnDelete = new JButton("Delete Record"));

        areapanel.setLayout(new GridLayout(8,2,5,5));
        areapanel.add(new JLabel("Enter UNIQUE ID or Name"));
        areapanel.add(txtID = new JTextField(10));
        areapanel.add(new JLabel(""));
        areapanel.add(btnSearch = new JButton("Search"));
        areapanel.add(new JLabel("Name:"));
        areapanel.add(txtname = new JTextField());
        areapanel.add(new JLabel("Fees"));
        areapanel.add(txtFees = new JTextField());
        areapanel.add(new JLabel("Branch"));
        areapanel.add(txtBranch = new JTextField());
        areapanel.add(new JLabel("Course:"));
        areapanel.add(txtCourse = new JTextField());
		areapanel.add(new JLabel("Year"));
		areapanel.add(txtYear=new JTextField());
		areapanel.add(new JLabel("Id"));
		areapanel.add(txtid1=new JTextField());
        bpanel = new JPanel();
        bpanel.setLayout(new BorderLayout());
        bpanel.add(areapanel,BorderLayout.NORTH);
        bpanel.add(tpanel,BorderLayout.SOUTH);
        
        add(bpanel);
        btnSearch.addActionListener(this);
        btnDelete.addActionListener(this);
		setVisible(true);
		setSize(500,500);
    }
    public static void retrieveRecords(){
        con = getConnection();
        String result = null;
        String name = null,branch = null,course = null,year=null,id=null,fees=null;
        String query = "Select * from course_details where id ='" + txtID.getText() + "'";
        try{
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                name = rs.getString("Subjects");
                fees = rs.getString("Fees");
                branch = rs.getString("Branches");
                course = rs.getString("Course");
                year = rs.getString("Year");
				id=rs.getString("id");
            }
        st.close();
        con.close();
        }
         catch(SQLException ex){
            System.err.println(ex.getMessage());
        }
        txtname.setText(name);
		txtname.setEnabled(false);
        txtCourse.setText(course);
		txtCourse.setEnabled(false);
        txtFees.setText(fees+"");
		txtFees.setEnabled(false);
        txtBranch.setText(branch);
		txtBranch.setEnabled(false);
		txtYear.setText(year+"");
		txtYear.setEnabled(false);
		txtid1.setText(id+"");
		txtid1.setEnabled(false);

        if(txtname.getText().equals("") && txtFees.getText().equals("") && txtBranch.getText().equals("") && txtCourse.getText().equals("") && txtid1.getText().equals("")){
                JOptionPane.showMessageDialog(null,"Record Not Found!");
            
        txtname.setText("");
        txtCourse.setText("");
        txtFees.setText("");
        txtBranch.setText("");
		txtYear.setText("");
        txtID.setText("");
		txtid1.setText("");}
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

      public static void deleteRecords(){
        con = getConnection();
        String delete1 = "Delete from course_details where id ='"+ txtid1.getText() +"'" ;
        
        try{
            st = con.createStatement();
            st.executeUpdate(delete1);
            st.close();
            con.close();
        }
        catch(SQLException ex){
            System.err.println(ex.getMessage());
        }
        JOptionPane.showMessageDialog(null,"Recordd successsfully Deleted");
        txtname.setText("");
        txtCourse.setText("");
        txtFees.setText("");
        txtBranch.setText("");
		txtYear.setText("");
        txtID.setText("");
		txtid1.setText("");
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == btnSearch){
            retrieveRecords();
        }
        if(e.getSource() == btnDelete){
            deleteRecords();
        }
    }
	public static void main(String args[])
	{
		new DeleteSubject();
	}

}
