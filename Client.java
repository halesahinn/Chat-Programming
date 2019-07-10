
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class Client {
   //ClientSide of the Chat Application
    BufferedReader in;
    PrintWriter out;
    static JFrame frame = new JFrame("Chat Apps"); 
    JTextField textField = new JTextField(10); 
    JTextArea messageArea = new JTextArea(25, 70);
    JTextArea nameArea = new JTextArea(25,5);
	private String name=null;
	private  String password = null;
    static LinkedList<String> list = new LinkedList<String>();
    static String[] users = new String[150];
    static Icon icon = null;

    public Client() {
      
        // GUI part
        textField.setEditable(false);
        messageArea.setEditable(false);
        nameArea.setEditable(false);
        frame.getContentPane().add(textField, "North");
        frame.getContentPane().add(new JScrollPane(messageArea), "Center");
        frame.pack();
        //Events
      
        textField.addActionListener(new ActionListener() {
          
            public void actionPerformed(ActionEvent e) {
                out.println(textField.getText());
                textField.setText("");
                
            }
        });
    }

    //It takes user name
    public String getName() {
     
       String s = JOptionPane.showInputDialog(
            frame,
            "Choose a screen name:",
            "Registration or Login",
            JOptionPane.PLAIN_MESSAGE);
       return s;
    }
    //It takes user name
    public String getPassword() {
     
       String s = JOptionPane.showInputDialog(
            frame,
            "Enter your password :",
            "Registration or Login",
            JOptionPane.PLAIN_MESSAGE);
       return s;
    }
    //It checks the user name, if it exists on "users_name.txt" it returns true value.
    static boolean existence(String u)
    {
    int i=0;
    
    String file_name = "users_name.txt";
    String line = null;
        try
        {
        FileReader file = new FileReader(file_name);   //Read from users_name.txt file
        BufferedReader br = new BufferedReader(file);
        while((line = br.readLine()) != null)
        {
        if(!list.contains(line)){
        list.add(line);
        users[i]=line;
        	}
        i++;
        if (line.equals(u) )  
        { 
        return true;
        }
        }
        br.close();
        file.close();
        }
        
        catch (FileNotFoundException ex)
        {
        System.out.println("Error");
        return false;
        }
        catch (IOException ex)
        {
        System.out.println("Error"); 
        return false;
        }
        return false;
        } 
    //It checks the user name,After checking , it creates list from users
    static boolean invalidity(String u)
    {
    int i=0;
    
    String file_name = "users_name.txt";
    String line = null;
        try
        {
        FileReader file = new FileReader(file_name);   //Read from users_name.txt file
        BufferedReader br = new BufferedReader(file);
        while((line = br.readLine()) != null)
        {
        list.add(line);
        users[i]=line;
        i++;
        if (line.equals(u) )  
        {
        JOptionPane.showMessageDialog(frame,
            "The username exists!",
            "Error",        //Error messages
            JOptionPane.PLAIN_MESSAGE);  
        break;
        }
        }
        br.close();
        file.close();
        }
        
        catch (FileNotFoundException ex)
        {
        System.out.println("Error");
        return false;
        }
        catch (IOException ex)
        {
        System.out.println("Error"); 
        return false;
        }
        return true;
        }
    //it checks password existence at "users_pasw" by finding the name that owns that pasw from users_name.txt
    boolean invalidityPasw(String k,String u)
    {
    
 
    	int a=0;
    	int c=0;     
        String file_name = "users_name.txt";
        String line = null;
            try
            {
            FileReader file = new FileReader(file_name);   //Read from users_info.txt file
            BufferedReader br = new BufferedReader(file);
            while((line = br.readLine()) != null)
            {
            	
            	if(line.equals(k)){
            		break;
            	}else{
            		
            	}
            	c++;                    
            
            }
            br.close();
            file.close();
            }
            catch (FileNotFoundException ex)
            {
            System.out.println("Error");
            return false;
            }
            catch (IOException ex)
            {
            System.out.println("Error"); 
            return false;
            }
    
    	
    String file_ = "users_pasw.txt";
 
        try
        {
        FileReader file1 = new FileReader(file_);   //Read from users_info.txt file
        BufferedReader br = new BufferedReader(file1);
        while( a-1<c)
        {
        line = br.readLine();
        a++;
        }
        if (line.equals(u))  
        {
        JOptionPane.showMessageDialog(frame,
            "The password is correct,youre connecting",
            "Warning!",        //Error messages
            JOptionPane.PLAIN_MESSAGE);         
        }else{
        	JOptionPane.showMessageDialog(frame,
                    "The password is incorrect,Try again",
                    "Error!",        //Error messages
                    JOptionPane.ERROR_MESSAGE); 
                    System.exit(0);
        }
        br.close();
        file1.close();
        }
        
        catch (FileNotFoundException ex)
        {
        System.out.println("Error");
        return false;
        }
        catch (IOException ex)
        {
        System.out.println("Error"); 
        return false;
        }
 
        return true;
        }   
    
    //if the name not exist create new one write it to user_name.txt file 
 public void writingName(String u){
	 String file_name = "users_name.txt";
	 //String line = null;
	  if(!list.contains(u)){
	        try{
	         File f = new File(file_name);
	             FileWriter fw = new FileWriter(f.getAbsoluteFile(),true);
	             BufferedWriter bw = new BufferedWriter(fw);
	             fw.write(u);
	             bw.newLine();
	             bw.close();
	        }
	        catch (IOException ex)
	        {
	        JOptionPane.showMessageDialog(frame,
	    "Something went wrong",
	    "Inane error",
	    JOptionPane.ERROR_MESSAGE);
	        }
	       }
 }
//if the pasw not exist create new one write it to user_pasw.txt file at the same time when you add name
 public void writingPasw(String u){
	 String file_name = "users_pasw.txt";
	 //String line = null
	        try{
	         File f = new File(file_name);
	             FileWriter fw = new FileWriter(f.getAbsoluteFile(),true);
	             BufferedWriter bw = new BufferedWriter(fw);
	             fw.write(u);
	             bw.newLine();
	             bw.close();
	        }
	        catch (IOException ex)
	        {
	        JOptionPane.showMessageDialog(frame,
	    "Something went wrong",
	    "Inane error",
	    JOptionPane.ERROR_MESSAGE);
	        }
	       }
 
 
 
public void run() throws IOException { 
	// It makes connection and initializes streams
    Socket socket = new Socket("localhost", 8000);
    in = new BufferedReader(new InputStreamReader(
        socket.getInputStream()));
    out = new PrintWriter(socket.getOutputStream(), true);
	
	//first option to enter the system, answer keeps in n value
	Object[] options = {"Register",
            "Log In"};
	   int n =  JOptionPane.showOptionDialog(frame,
	    	    "Would you like register or login ? ",
	    	    "Choose what to do!",
	    	    JOptionPane.YES_NO_OPTION,
	    	    JOptionPane.QUESTION_MESSAGE,
	    	    null,
	    	    options,
        	    options[1]);
   
	   //if it is register, take a name control existence 
	   if(n==0){
           
		   while (true) {
		        //String name =null;
		        //String password=null;
		        String line = in.readLine();
		        if (line.startsWith("SUBMITNAME")) {
		             name=getName();
		             
		             //if name not exist register as new user
		             if(!existence(name)){
		             password=getPassword();
		             writingName(name);
		             writingPasw(password);
		             JOptionPane.showMessageDialog(frame,
	        		    	    "You are registered! ",
	        		    	    "Information!",
	        		    	    JOptionPane.PLAIN_MESSAGE );
		             out.println(name);
		             }
		             
		             //if name exist ask user wants to log in or register
		             else if(existence(name)) {
		            	 Object[] options1 = {"Register",
		                 "Log In"};
		     	   int m =  JOptionPane.showOptionDialog(frame,
		     	    	    "This username exists, Would you like register for another username or login ? ",
		     	    	    "Choose what to do!",
		     	    	    JOptionPane.YES_NO_OPTION,
		     	    	    JOptionPane.QUESTION_MESSAGE,
		     	    	    null,
		     	    	    options1,
		             	    options1[1]);
		     	   //if register check if user choose a name not already taken
		     	     if(m==0){
		     	    	name=getName();
			            while(existence(name)){
			            	JOptionPane.showMessageDialog(frame,
		        		    	    "User name exist! ",
		        		    	    "Information!",
		        		    	    JOptionPane.PLAIN_MESSAGE );
			            	name=getName();
			            	
			            }
			             password=getPassword();
			             writingName(name);
			             writingPasw(password);
			             JOptionPane.showMessageDialog(frame,
		        		    	    "You are registered! ",
		        		    	    "Information!",
		        		    	    JOptionPane.PLAIN_MESSAGE );
			             out.println(name);
		     	     
		     	     }//otherwise log in the user
		     	     else{
		     	    	JOptionPane.showMessageDialog(frame,
	        		    	    "You are logged in! ",
	        		    	    "Information!",
	        		    	    JOptionPane.PLAIN_MESSAGE );
		     	    	out.println(name);
		     	     }
		             }
		             //if there is new user add it to list
		             if(!list.contains(name)){
		             list.add(name);
		                 }
		             //prompt that new user connected especially for group chatting
		             out.println(name+" is connected.");
		             
		         //    out.println(list);

		            }  
		        
		             else if (line.startsWith("NAMEACCEPTED")) {   //Acceptance of login activity.
		                textField.setEditable(true);
		            } else if (line.startsWith("MESSAGE")) {      //writing messages from server
		                messageArea.append(line.substring(8) + "\n");
		            }
		        }   
		   //if login was chosen go here
	   }else if(n==1){
		   while (true) {
		       
		        String line = in.readLine();
		        if (line.startsWith("SUBMITNAME")) {
		             name=getName();
		             password=getPassword();
		             if(!existence(name)){   //check existence
		            	 JOptionPane.showMessageDialog(frame,
		                         "The username incorrect,Try again",
		                         "Error!",        //Error messages
		                         JOptionPane.ERROR_MESSAGE); 
		                         System.exit(0);
		             }
		             invalidityPasw(name,password);//check password
		             out.println(name);
		             if(!list.contains(name)){
		             list.add(name);
		             
		                 }
		             out.println(name+" is connected.");
		             
		             
		            // out.println(list);
		              }else if (line.startsWith("NAMEACCEPTED")) {   //Acceptance of login activity.
			                textField.setEditable(true);
			            } else if (line.startsWith("MESSAGE")) {
			                messageArea.append(line.substring(8) + "\n");
			           }
			      
		  
		   
		   } 
                   }
	   
	   
	   

      


}
	   
    
    
   
  
    public static void main(String[] args) throws Exception {
    	Client client = new Client();    //start the execution
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        client.run();
   
   
        
    }
}
