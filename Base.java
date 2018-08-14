import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;

public class Base extends JFrame implements ActionListener
{
    static JButton btnAttendance,btnStudent,btnStaff,btnSMS,btnFees,btnEnquiry,btnStudAdd,btnStudUp,btnStudDel,btnStaffUp,btnStaffDel,btnStaffAdd,btnSubAdd,btnSubDel,btnSubUp,btnSubject,btnReport,btnAbout,btnStuds,btnStaffs,btnLogout,btnSearch;
    static JPanel areapanel,bpanel,mpanel,studpanel,staffpanel,subpanel,panel1;
    static String username = "anonymous";
    static String password = "guest";
    static String url = "jdbc:odbc:Students";
    static Statement st;
    static Connection con;
	CardLayout c1 ;
    Base()
	{
        super("Pinnacle Management System");
			try {
       UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
	} catch (Exception e) {
		e.printStackTrace();
	}
        areapanel = new JPanel();
        areapanel.setLayout(new GridLayout(12,1,5,5));
		btnAttendance=new JButton("Attendance");
		btnStudent=new JButton("Student");
		btnStaff=new JButton("Staff");
		btnSMS=new JButton("SMS");
		btnFees=new JButton("Fees");
		btnEnquiry=new JButton("Enquiry");
		btnSubject=new JButton("Subject");
		btnReport=new JButton("Daily Report");
		btnAbout=new JButton("About");
		btnSearch=new JButton("Search");
		btnLogout=new JButton("Logout");
        areapanel.add(btnAttendance);
		areapanel.add(btnStudent);
		areapanel.add(btnStaff);
		areapanel.add(btnSMS);
		areapanel.add(btnFees);
		areapanel.add(btnEnquiry);
		areapanel.add(btnSubject);
		areapanel.add(btnReport);
		areapanel.add(btnAbout);
		areapanel.add(btnSearch);
		areapanel.add(btnLogout);
        bpanel = new JPanel();
		mpanel = new JPanel();
		panel1=new JPanel();
		mpanel.setLayout(new CardLayout());
		c1= (CardLayout)(mpanel.getLayout());
		studpanel=new JPanel();
		studpanel.add(btnStudAdd=new JButton("Add Student"));
		studpanel.add(btnStudDel=new JButton("Delete Student"));
		studpanel.add(btnStudUp=new JButton("Update Student"));		
		studpanel.add(btnStuds=new JButton("Search Student"));		
		staffpanel=new JPanel();
		staffpanel.add(btnStaffAdd=new JButton("Add Staff"));
		staffpanel.add(btnStaffDel=new JButton("Delete Staff"));
		staffpanel.add(btnStaffUp=new JButton("Update Staff"));
		staffpanel.add(btnStaffs=new JButton("Search Staff"));		
		subpanel=new JPanel();
		subpanel.add(btnSubAdd=new JButton("Add Subject"));
		subpanel.add(btnSubDel=new JButton("Delete Subject"));
		subpanel.add(btnSubUp=new JButton("Update Subject"));
		mpanel.add(studpanel,"student");
		mpanel.add(subpanel,"subjects");
		mpanel.add(staffpanel,"staff");
		mpanel.add(panel1,"Empty");
        bpanel.setLayout(new BorderLayout());
        bpanel.add(areapanel,BorderLayout.LINE_START);
		bpanel.add(mpanel,BorderLayout.LINE_END);
        add(bpanel);
		
        btnAttendance.addActionListener(this);
		btnStudent.addActionListener(this);
		btnStaff.addActionListener(this);
		btnSMS.addActionListener(this);
		btnFees.addActionListener(this);
		btnEnquiry.addActionListener(this);
		btnAbout.addActionListener(this);
		btnSubject.addActionListener(this);
		btnReport.addActionListener(this);
		btnStudAdd.addActionListener(this);
		btnStudDel.addActionListener(this);
		btnStudUp.addActionListener(this);
		btnStaffAdd.addActionListener(this);
		btnStaffDel.addActionListener(this);
		btnStaffUp.addActionListener(this);
		btnSubAdd.addActionListener(this);
		btnSubDel.addActionListener(this);
		btnSubUp.addActionListener(this);
		btnStuds.addActionListener(this);
		btnStaffs.addActionListener(this);
		btnSearch.addActionListener(this);
		btnLogout.addActionListener(this);
		setVisible(true);
		setSize(600,500);
		c1.show(mpanel,"Empty");
    }
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==btnStudent)
		{
			c1.show(mpanel,"student");
			
		}	
		if(ae.getSource()==btnStaff)
		{
			c1.show(mpanel,"staff");
			
		}
		if(ae.getSource()==btnSubject)
		{
			c1.show(mpanel,"subjects");
		}
		if(ae.getSource()==btnFees)
		{
			new Fees();
			this.dispose();
		}
		if(ae.getSource()==btnAbout)
		{
			new About();
			this.dispose();
			new Base();
		}
		
		/*if(ae.getSource()==btnAttendance);
		{
			new attendance().GUI();
			this.dispose();
		}*/
		if(ae.getSource()==btnEnquiry)
		{
			new AddEnquiry();
			this.dispose();
		}
		if(ae.getSource()==btnSMS)
		{
			new SMS();
			this.dispose();
		}
		if(ae.getSource()==btnReport)
		{
			new DailyRecord().GUI();
			this.dispose();
		}
		if(ae.getSource()==btnStaffAdd)
		{
			new AddRecord1();
			this.dispose();
		}
		if(ae.getSource()==btnStaffDel)
		{	
			new DeleteRecord1();
			this.dispose();
		}
		if(ae.getSource()==btnStaffUp)
		{
			new UpdateRecord1();
			this.dispose();
		}
		if(ae.getSource()==btnStudAdd)
		{
			new AddRecord();
			this.dispose();
		}
		if(ae.getSource()==btnStudDel)
		{
			new DeleteRecord();
			this.dispose();
		}
		if(ae.getSource()==btnStudUp)
		{
			new UpdateRecord();
			this.dispose();
		}
		if(ae.getSource()==btnSubAdd)
		{
			new AddSubject();
			this.dispose();
		}
		if(ae.getSource()==btnSubDel)
		{
			new DeleteSubject();
			this.dispose();
		}
		if(ae.getSource()==btnSubUp)
		{	
			new UpdateSubject();
			this.dispose();
		}
		if(ae.getSource()==btnStuds)
		{
			new Search1();
			this.dispose();
		}
		if(ae.getSource()==btnStaffs)
		{
			new SearchStaff();
			this.dispose();
		}
		if(ae.getSource()==btnLogout)
		{
			new Login();
			this.dispose();
		}
		if(ae.getSource()==btnSearch)
		{
			new Search().GUI();
			this.dispose();
		}
	}
	public static void main(String[] args) {
		Base lapp =new Base();
        lapp.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
      
    }
}
