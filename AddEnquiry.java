import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Date;
import javax.swing.*;
public class AddEnquiry extends JFrame implements ActionListener
{
	
    static JTextField txtDate,txtSem,txtSname,txtPhno,txtEmail,txtCollege,txtBranch,txtCourse,txtStaff;
	static String date;
	static JTextArea txtAddr;
    static JButton btnAdd,btnCancel;
	static JComboBox Sem,Branch,Course; 
	static JCheckBox sub_check[]=new JCheckBox[6];
	static String[] sub;
    static JPanel areapanel,bpanel;
    static String username = "anonymous";
    static String password = "guest";
    static String url = "jdbc:odbc:Students";
    static Statement st;
    static Connection con;
	static JCheckBox sub1,sub2,sub3,sub4,sub5,sub6;
	static int count=0,tot_fees=0,i=0,count1=0;
	static String msg="";
    public AddEnquiry()
	{
        super("Enquiry");
		try {
       UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
	} catch (Exception e) {
		e.printStackTrace();
	}
		
        
		areapanel = new JPanel();
        areapanel.setLayout(new GridLayout(6,2,5,5));
        
		areapanel.add(new JLabel("Student Name:"));
		areapanel.add(txtSname = new JTextField(10));
		
		String DATE_FORMAT_NOW = "yyyy-MM-dd";
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		String stringDate = sdf.format(date );
		
		areapanel.add(new JLabel("Date:"));
		areapanel.add(txtDate = new JTextField(10));
		txtDate.setEditable(false);
		txtDate.setText(stringDate);
		
		areapanel.add(new JLabel("Semester:"));
		String [] a={"I","II","III","IV","V","VI"};
        areapanel.add(Sem = new JComboBox(a));
		
		areapanel.add(new JLabel("Course:"));
		String [] c={"Diploma","Degree"};
        areapanel.add(Course = new JComboBox(c));
		
        areapanel.add(new JLabel("Branch:"));
		String [] b={"CM","CO","EJ","ET"};
        areapanel.add(Branch = new JComboBox(b));
		call_branch();
		areapanel.add(new JLabel("Email:"));
		areapanel.add(txtEmail = new JTextField(10));
		
		areapanel.add(new JLabel("College:"));
        areapanel.add(txtCollege = new JTextField(10));
		
		areapanel.add(new JLabel("Address:"));
		areapanel.add(txtAddr = new JTextArea(5,10));
		
		areapanel.add(new JLabel("Phone Number:"));
		areapanel.add(txtPhno = new JTextField(10));
		
        areapanel.add(new JLabel("Guiding Staff:"));
		areapanel.add(txtStaff = new JTextField(10));
		
        areapanel.add(btnAdd = new JButton("Add Enquiry"));
		areapanel.add(btnCancel = new JButton("Cancel"));
        bpanel = new JPanel();
        bpanel.setLayout(new BorderLayout());
        bpanel.add(areapanel,BorderLayout.NORTH);
        add(bpanel);

        btnAdd.addActionListener(this);
	btnCancel.addActionListener(this);
		Branch.addActionListener(this);
		setVisible(true);
		setSize(700,650);
    }
    
	public void actionPerformed(ActionEvent e)
	{
        if(e.getSource() == btnAdd)
			{
				insertEnquiry();
			}
			if(e.getSource()==btnCancel)
			{
				this.dispose();
				new Base();
			}
			
		
    }
	public void itemStateChanged(ItemEvent ie)
	{
			
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

     public static void insertEnquiry()
	 {
		 System.out.println("called");
        con = getConnection();
        String q = "Insert into Enquiry values('" + txtDate.getText() + "','" + txtSname.getText() + "','" + Sem.getSelectedItem() + "','" + Course.getSelectedItem() + "','" + Branch.getSelectedItem() + "','" + txtEmail.getText() + "','"+ txtCollege.getText() + "','" + txtAddr.getText() + "','" + txtPhno.getText() + "','"+ txtStaff.getText() +"')";
        try{
            st= con.createStatement();
            st.executeUpdate(q);
            st.close();
            con.close();
        }
        catch(SQLException ex){
            System.err.println(ex);
        }
        JOptionPane.showMessageDialog(null,"Successfully inserted a enquiry!");
        txtSname.setText("");
        txtEmail.setText("");
        txtCollege.setText("");
        txtAddr.setText("");
        txtPhno.setText("");
		txtStaff.setText("");
     }
	 public void call_branch()
	{
				
				try
				{
						con = getConnection();			
						String query="Select distinct(Branches) from course_details ";
						try
						{
								st= con.createStatement();
								ResultSet rs=st.executeQuery(query);
								while(rs.next())
								{
									String temp=rs.getString("Branches");
									if(temp.equals("All"))
									{}
								else{
									Branch.addItem(temp);
									}
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
			catch(Exception ob){System.out.println(ob);}
	
	}
	public static void main(String args[])
	{
		new AddEnquiry();
	}
}
