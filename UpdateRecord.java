import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;

public class UpdateRecord extends JFrame implements ActionListener{

    static JTextField txtID,txtFname,txtLname,txtMname,txtCourse,txtBranch;
	static JCheckBox sub_check[]=new JCheckBox[6];
	static JButton btnUpdate,btnSearch,btnCancel;
    static JPanel areapanel,bpanel,tpanel,mpanel;
    static String username = "anonymous";
    static String password = "guest";
    static String url = "jdbc:odbc:Students",msg="",msg1="";
    static Statement st;
    static Connection con;
	static int count=0,c=0,i=0,tot_fees=0,j=0,bal=0;

    UpdateRecord(){
        super("Search the record to update. . .");
		try {
       UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
	} catch (Exception e) {
		e.printStackTrace();
	}
        areapanel = new JPanel();
        tpanel = new JPanel();
		mpanel=new JPanel();
        tpanel.setLayout(new GridLayout(1,2,5,5));
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
        areapanel.add(new JLabel("Course:"));
        areapanel.add(txtCourse = new JTextField(10));
		for(int i=0;i<sub_check.length;i++){
		sub_check[i]=new JCheckBox();	
		mpanel.add(sub_check[i]);
		sub_check[i].setVisible(false);}
        bpanel = new JPanel();
        bpanel.setLayout(new BorderLayout());
        bpanel.add(areapanel,BorderLayout.NORTH);
		bpanel.add(mpanel,BorderLayout.CENTER);
        bpanel.add(tpanel,BorderLayout.SOUTH);
        
        add(bpanel);
        btnSearch.addActionListener(this);
        btnUpdate.addActionListener(this);
		btnCancel.addActionListener(this);
		setVisible(true);
		setSize(400,380);
    }
    public static void retrieveRecords(){
        con = getConnection();
        String result = null;
        String fname = null,mname = null, lname = null, course = null;
        String query = "Select * from tblStudents where ID ='" + txtID.getText() + "' or FirstName='" + txtID.getText() + "'";
        try{
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                lname = rs.getString("LastName");
                fname = rs.getString("FirstName");
                mname = rs.getString("MiddleName");
                course = rs.getString("Course");
            }
        st.close();
        con.close();
        }
         catch(SQLException ex){
            System.err.println(ex.getMessage());
        }
        txtFname.setText(fname);
        txtCourse.setText(course);
        txtLname.setText(lname);
        txtMname.setText(mname);

        if(txtFname.getText().equals("") && txtMname.getText().equals("") && txtLname.getText().equals("") && txtCourse.getText().equals("")){
                JOptionPane.showMessageDialog(null,"Record Not Found!");
            
        txtFname.setText("");
        txtCourse.setText("");
        txtLname.setText("");
        txtMname.setText("");
        txtID.setText("");}
    }
	public static void retrieveRecords1(){
        con = getConnection();

		String[] sub=new String[6];
		String[] s=new String[6];
		for(i=0;i<sub_check.length;i++)
		{
			sub[i]="";
			s[i]="";
		}
		String q="select * from subjects where ID='"+txtID.getText()+"'"; 
        String query = "Select * from course_details where Year = (select CYear from tblStudents where ID='"+txtID.getText()+"' or FirstName='"+txtID.getText()+"') and Branches = (select Branch from tblStudents where ID='"+txtID.getText()+"' or FirstName='"+txtID.getText()+"')";
        try{
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
				
				sub[count]=new String(rs.getString("Subjects"));
                System.out.println(sub[count++]);
            }
			ResultSet r1 = st.executeQuery(q);
			while(r1.next()){
				s[c]=new String(r1.getString("Subjects"));
				System.out.println(s[c++]);
			}
			try	{
			System.out.println(count+""+c);
		for (i = 0; i <count; i++) 
            { 
				
				
					sub_check[i].setText(sub[i]);
					sub_check[i].setVisible(true);
					
						
					for(j=0;j<c;j++)
					{
						
						if(sub[i].equals(s[j]))
						{
							System.out.println(" ssf");
							sub_check[i].setSelected(true);
						}
					}
			}
			
			}
		catch(Exception e)
		{}
		}
		catch(SQLException ex){
            System.err.println(ex);
        }
     
		
			}
	public static void insert()
	{
		tot_fees=0;
					for(int i=0;i<count;i++)
				{
					if(sub_check[i].isSelected())
					{
						msg=sub_check[i].getText()+"";			
						String q1="Select fees_paid as paid from tblStudents where ID='"+txtID.getText()+"'";
						String a="delete from subjects where ID='"+txtID.getText()+"'";
						String q = "Insert into subjects values('" + txtID.getText() + "','" + msg + "')";
						System.out.println(q);
						String query="Select Fees from course_details where Subjects='" + msg + "' and Branches=(select Branch from tblStudents where ID='"+txtID.getText()+"')";
						System.out.println(query);
						try
						{
								st= con.createStatement();
								st.executeUpdate(a);
								st.executeUpdate(q);
								System.out.println("1");
								ResultSet rs=st.executeQuery(query);
								System.out.println("2");
								while(rs.next())
								{System.out.println("3");
									int fees=Integer.parseInt(rs.getString("Fees"));
									System.out.println(fees);
									tot_fees  = tot_fees + fees;
								} 
								rs=st.executeQuery(q1);
								while(rs.next())
								{
									int fees=Integer.parseInt(rs.getString("paid"));
									bal=tot_fees-fees;
								}
						}
						catch(SQLException ex)
						{
							System.err.println(ex);
						}
					}
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
        String update1 = "Update tblStudents set FirstName = '" + txtFname.getText() +"' where ID = '" + txtID.getText() + "'" ;
        String update2 = "Update tblStudents set MiddleName = '" + txtMname.getText() +"' where ID = '" + txtID.getText() + "'" ;
        String update3 = "Update tblStudents set LastName = '" + txtLname.getText() +"' where ID = '" + txtID.getText() + "'" ;
        String update4 = "Update tblStudents set Course = '" + txtCourse.getText() +"' where ID = '" + txtID.getText() + "'" ;
		String update5 = "Update tblStudents set tot_fees = '" + tot_fees +"' where ID = '" + txtID.getText() + "'" ;
		String update6 = "Update tblStudents set bal = '" + bal +"' where ID = '" + txtID.getText() + "'" ;
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
        txtFname.setText("");
        txtCourse.setText("");
        txtLname.setText("");
        txtMname.setText("");
        txtID.setText("");
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == btnSearch){
            retrieveRecords();
			retrieveRecords1();
        }
        if(e.getSource() == btnUpdate){
		insert();
		updateRecords();
        }
		if(e.getSource()== btnCancel){
			this.dispose();
			new Base();
		}
    }
	public static void main(String args[])
	{
		new UpdateRecord();
	}

}
