import java.awt.image.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener{

    static JTextField txtID;
	static JPasswordField txtPass;
    static JButton btnLogin,btnRegister;
    static JLabel lbID,lbPass;
    static JPanel areapanel,bpanel,ipanel;
    static JCheckBox sp;
    static String username = "anonymous";
    static String password = "guest";
    static String url = "jdbc:odbc:Students";
    static Statement st;
    static Connection con;

    Login(){
        super("Login");
			try {
       UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
		} catch (Exception e) {
		e.printStackTrace();
		}
			
        areapanel = new JPanel();
        areapanel.setLayout(new GridLayout(6,2,5,5));
        areapanel.add(lbID = new JLabel("ID"));
        areapanel.add(txtID = new JTextField(10));
        areapanel.add(lbPass = new JLabel("Password"));
		areapanel.add(txtPass = new JPasswordField(10));
		txtPass.setEchoChar('*');
		areapanel.add(sp=new JCheckBox("Show Password"));
		areapanel.add(new JLabel());
        areapanel.add(btnLogin = new JButton("Login"));
		areapanel.add(btnRegister = new JButton("Register"));
		
        ipanel = new JPanel();
		ImageIcon i=new ImageIcon("logo.jpg");
		ipanel.add(new JLabel(i));
		
        bpanel = new JPanel();
        bpanel.setLayout(new BorderLayout());
        bpanel.add(areapanel,BorderLayout.CENTER);
        bpanel.add(ipanel,BorderLayout.NORTH);
        add(bpanel);

        sp.addActionListener(this);
        btnLogin.addActionListener(this);
		btnRegister.addActionListener(this);
    setVisible(true);
		setSize(520,600);
	}



   public void retrieve(){
        con = getConnection();
        String id = null,pass = null;
        String query = "Select * from login where ID like '" + txtID.getText() + "'";
        try{
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                pass = rs.getString("Password");
            }
        st.close();
        con.close();
        }

        catch(SQLException ex){
            System.err.println(ex.getMessage());
        }

            if((txtPass.getText()).equals(pass)){
				System.out.println("Valid");
				this.dispose();
				final Base m = new Base();
			}
            else
                JOptionPane.showMessageDialog(null,"Invalid Id Or Password`");
           
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
    

        if(e.getSource() == btnLogin){
            if(txtID.getText().equals("") || txtPass.getText().equals("")){
                JOptionPane.showMessageDialog(null,"Enter Valid Id And Password");
            }
            else{
                 retrieve();
            }
		}
		if(e.getSource() == btnRegister){
                JOptionPane.showMessageDialog(null,"You Clicked Register");
				new Register();
            
		}
        if(e.getSource()==sp)
		{
			if(!sp.isSelected())
				txtPass.setEchoChar('*');
			else
				txtPass.setEchoChar((char)0);
		}
		
    }
	public static void main(String args[])
	{new Login();	}
}
