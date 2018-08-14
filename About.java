import java.awt.*;
import javax.swing.*;

public class About extends JFrame{

      About(){
          super("Pinnacle Management System");
          setLayout(new GridLayout(5,1));
          add(new JLabel("Developer: Ashish Ingale-19,Gaurav Harwani-16,Shubham Thorat-54,Dhiraj Tawani-52"));
          add(new JLabel("Institute - S.H.M. I.T."));
          add(new JLabel("Instructor: Ms. Bharti Khemani "));
		  setVisible(true);
		  setSize(500,200);
		  setResizable(false);
      }
}
