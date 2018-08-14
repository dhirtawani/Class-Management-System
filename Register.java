import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.sql.*;

public class Register extends JFrame implements ActionListener{

    static JTextField txtID,txtFname,txtLname,txtPass,txtPass1,txtEmail,txtPhone;
    static JButton btnRegister,btnCancel;
    static JPanel areapanel,bpanel,ipanel;
    static JRadioButton r1,r2;
    static String username = "anonymous";
    static String password = "guest";
    static String url = "jdbc:odbc:Students";
    static Statement st;
    static Connection con;
    static String g;
    Register(){
        super("Student Information System");
			try {
       UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
	} catch (Exception e) {
		e.printStackTrace();
	}
        areapanel = new JPanel();
        areapanel.setLayout(new GridLayout(10,2,5,5));
        areapanel.add(new JLabel("USER ID"));
        areapanel.add(txtID = new JTextField(10));
        areapanel.add(new JLabel("First Name"));
		areapanel.add(txtFname = new JTextField(10));
		areapanel.add(new JLabel("Last Name"));
		areapanel.add(txtLname = new JTextField(10));
		areapanel.add(new JLabel("Password"));
		areapanel.add(txtPass = new JTextField(10));
		areapanel.add(new JLabel("Re-Enter Password"));
		areapanel.add(txtPass1 = new JTextField(10));
		areapanel.add(new JLabel("Gender"));
		areapanel.add(new JLabel());
		areapanel.add(r1=new JRadioButton("Male",true));
		areapanel.add(r2=new JRadioButton("Female"));
		areapanel.add(new JLabel("Email"));
		areapanel.add(txtEmail = new JTextField(10));
		areapanel.add(new JLabel("PhoneNo"));
		areapanel.add(txtPhone = new JTextField(10));
        areapanel.add(btnRegister = new JButton("Register"));
		areapanel.add(btnCancel = new JButton("Cancel"));
        ipanel = new JPanel();
        ipanel.setLayout(new GridLayout(7,2,10,10));
        ipanel.add(new JLabel(""));
        ipanel.add(new JLabel(""));
        ipanel.add(new JLabel(""));
        ipanel.add(new JLabel(""));
        ipanel.add(new JLabel(""));
        ButtonGroup b=new ButtonGroup();
		b.add(r1);
		b.add(r2);

        bpanel = new JPanel();
        bpanel.setLayout(new BorderLayout());
        bpanel.add(areapanel,BorderLayout.NORTH);
        bpanel.add(ipanel,BorderLayout.SOUTH);
        add(bpanel);

        btnRegister.addActionListener(this);
		btnCancel.addActionListener(this);
        r1.addActionListener(this);
        r2.addActionListener(this);
        r1.setActionCommand("Male");
        r2.setActionCommand("Female");
		setVisible(true);
		setSize(500,600);
    }

    public static void main(String[] args) {
        Register rapp = new Register();
        rapp.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
        
      
    }

        public static void insert(){
		if(txtPass.getText().equals(txtPass1.getText()))
        {
		con = getConnection();
        String q = "Insert into register Values('" + txtID.getText() +"','" + txtFname.getText() + "','" + txtLname.getText() + "','" + txtPass.getText() + "','" + txtEmail.getText() + "','" + txtPhone.getText() + "','" + g + "')";
		String q1 = "Insert into login Values('" + txtID.getText() +"','" + txtPass.getText() + "')";
        try{
            st= con.createStatement();
            st.executeUpdate(q);
			st.executeUpdate(q1);
            st.close();
            con.close();
        }
        catch(SQLException ex){
            System.err.println(ex.getMessage());
        }
        JOptionPane.showMessageDialog(null,"Successfully inserted a record!");
     }
	 else
		  JOptionPane.showMessageDialog(null,"The Password didnot matched");
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

    public void actionPerformed(ActionEvent e){
    
        if(e.getSource() == btnRegister){
            if((txtID.getText().equals(""))||(txtFname.getText().equals(""))||(txtLname.getText().equals(""))||(txtPass.getText().equals(""))||(txtPass1.getText().equals(""))||(txtEmail.getText().equals(""))||(txtPhone.getText().equals(""))){
                JOptionPane.showMessageDialog(null,"Enter Appropriate Data");
				
            }
            else{
                    insert();
            }
		}
		if(e.getSource() == btnCancel){
           new Login();
System.exit(1);
		
		}
        if(e.getSource() == r1|| e.getSource() == r2){
             g=((JRadioButton) e.getSource()).getActionCommand();
			System.out.println(g);
        }
         
		
    }
	
}
