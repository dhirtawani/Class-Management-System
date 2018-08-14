
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import java.util.Calendar;
import java.util.Date;
import org.jdatepicker.impl.*;
import org.jdatepicker.util.*;
import org.jdatepicker.*;
import javax.swing.JFormattedTextField.*;
import java.text.*;
import javax.swing.table.*;

    //import org.jdatepicker.graphics.*;
class DailyRecord implements ActionListener
{
	JTable table;
	JScrollPane scrollPane;
	JDatePickerImpl datePicker;
	JButton search,cancel;
	JFrame f1;
	JTable t;
	Vector columnNames = new Vector();
Vector data = new Vector();
	static String username = "anonymous";
    static String password = "guest";
    static String url = "jdbc:odbc:Students";
    static Statement st;
    static Connection con;
	ArrayList cols=new ArrayList();
    void GUI() {
        f1 = new JFrame();
		search=new JButton("Search");
        cancel=new JButton("Cancel");
		f1.setLayout(new FlowLayout());
        
		UtilDateModel model = new UtilDateModel();
		table = new JTable(data, columnNames);
scrollPane = new JScrollPane(table);
Properties p = new Properties();
p.put("text.today", "Today");
p.put("text.month", "Month");
p.put("text.year", "Year");
JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
 datePicker= new JDatePickerImpl(datePanel, new DateLabelFormatter());
f1.add(datePicker);
f1.add(search);
f1.add(cancel);
search.addActionListener(this);
cancel.addActionListener(this);
f1.setSize(500, 500);
        f1.setVisible(true);
		f1.add(scrollPane);
	}
	public static void main(String args[])
	{
		DailyRecord d=new DailyRecord();
		d.GUI();
	}
public void actionPerformed(ActionEvent ae)
{
	int flag=0,columns=0;
	if(ae.getSource()==cancel)
	{
	f1.dispose();
	new Base();
	}
	if(ae.getSource()==search)
	{
		
		con=getConnection();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date d=(Date)datePicker.getModel().getValue();
		String str=dateFormat.format(d);
		String query="select id,FirstName,bal from tblStudents where DOA=#"+str+"#";
		try 
		{
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery( query );
			
			if(flag==0)
			{
				columnNames.removeAllElements();
			ResultSetMetaData md = rs.getMetaData();
			columns = md.getColumnCount();
			for (int i = 1; i <= columns; i++) 
			{
				columnNames.addElement( md.getColumnName(i) );
			}
			flag=1;}
			data.removeAllElements();
			while (rs.next()) 
			{
				Vector row = new Vector(columns);
				for (int i = 1; i <= columns; i++)
				{
					row.addElement( rs.getObject(i) );
				}
				data.addElement( row );
			}
			rs.close();
			stmt.close();
		}
		catch(Exception e) 
		{
			System.out.println( e );
		}
		
		f1.setVisible(false);
		scrollPane.remove(table);
		
		table = new JTable(data, columnNames);
		scrollPane = new JScrollPane( table );
		f1.dispose();
		GUI();
	}
	
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
}
class DateLabelFormatter extends AbstractFormatter {

    private String datePattern = "yyyy/MM/dd";
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text);
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null) {
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }

        return "";
    }

}