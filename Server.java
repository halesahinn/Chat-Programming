import java.awt.Component;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class Server {

  
    private static final int PORT = 8000; //port 8000 is used.

    
    public static HashSet<String> names = new HashSet<String>(); //It keeps name of users on server part

    
    public static HashSet<PrintWriter> chatters = new HashSet<PrintWriter>(); //It keeps users on server part.
 
    public static HashSet<PrintWriter> chattersGroup = new HashSet<PrintWriter>(); //It keeps users on server part.
    static LinkedList<String> list = new LinkedList<String>();  //it keeps users names from users_name.txt
  
    public static void main(String[] args) throws Exception {
        System.out.println("The chat server is ready for client from port 8000"); //When the server is executes successfully
        ServerSocket listener = new ServerSocket(PORT);
        try {
            while (true) {
                new Handler(listener.accept()).start(); //After execution , the server will start to listen to the client
            }
        } finally {
            listener.close();
        }
    }

    
    private static class Handler extends Thread {
        private String name;
        private Socket socket;
        private BufferedReader in;   //to get input
        private PrintWriter out;     //to write output

        public Handler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {

                in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                while (true) {
                    out.println("SUBMITNAME"); //Before entering user name
                    name = in.readLine();    
                    if (name == null) {
                        return;
                    }
                    synchronized (names) {
                        if (!names.contains(name)) {   //if user didnt online add it to online user list(names)

                            names.add(name);
                            break;
                        }
                        else{
                        	JFrame frame = new JFrame("Error"); 
							JOptionPane.showMessageDialog(frame,    //if user already opened prompt that
                				    "The user is already online",
                				    "Error",        //Error messages
                				    JOptionPane.PLAIN_MESSAGE);
                        }                    }
                }

                out.println("NAMEACCEPTED"); //The message of acceptance of registry
                JFrame frame = new JFrame("Decide");
                Object[] options = {"p2p Chat",
                        "Group Chat"};
            	   int n =  JOptionPane.showOptionDialog(frame,
            	    	    "Would you prefer peer to peer chat or group chat? ",
            	    	    "Choose what to do!",
            	    	    JOptionPane.YES_NO_OPTION,
            	    	    JOptionPane.QUESTION_MESSAGE,           //take an option to continue one of them
            	    	    null,
            	    	    options,
            	    	    options[1]);
                
                if(n==0){    //if it is p2p, ask the user name it asks for to chat
                String s=  JOptionPane.showInputDialog(frame,
        	    	    "Enter the name are you searching for? ",
        	    	    "Choose what to do!",
        	    	    JOptionPane.QUESTION_MESSAGE);
                //take existence of all users name
                String file_name = "users_name.txt";
                String line = null;
                    try
                    {
                    FileReader file = new FileReader(file_name);   //Read from users_name.txt file
                    BufferedReader br = new BufferedReader(file);
                    while((line = br.readLine()) != null)
                    {
					list.add(line);
                                       }
                    br.close();
                    file.close();
                    }
                    
                    catch (FileNotFoundException ex)
                    {
                    System.out.println("Error");
                    
                    }
                    catch (IOException ex)
                    {
                    System.out.println("Error"); 
                    
                    }
                
                //if user not registered at all
                if(!list.contains(s)){
                	JOptionPane.showMessageDialog(frame,
        				    "Requested user is not found!",
        				    "Connection Rejected!",        
        				    JOptionPane.PLAIN_MESSAGE);
                }else if(list.contains(s) && names.contains(s)){  //if user registered, and online prompt
                	JOptionPane.showMessageDialog(frame,
        				    "Requested user is online for p2p chat!",
        				    "Connection Accepted!",        
        				    JOptionPane.PLAIN_MESSAGE);
                }else if(list.contains(s)&& !names.contains(s)){ //if user registered, but not online prompt
                	JOptionPane.showMessageDialog(frame,
        				    "Requested user is offline for p2p chat!",
        				    "Connection Rejected!",        
        				    JOptionPane.PLAIN_MESSAGE);
                	System.exit(0);//exit because of error
                }
                	
                if(chatters.size()>1){   //if there is more than 2 user in p2p chat, dont take anybody else
					JOptionPane.showMessageDialog(frame,
        				    "It is a peer to peer connection you cannot involve!",
        				    "BUSY",        //Error messages
        				    JOptionPane.PLAIN_MESSAGE);
                }else{
                chatters.add(out);   //add new online user to the online user list of chatters
                }
                
                
                
                //if group chatting chosen
                }else if(n==1){
                	chattersGroup.add(out);
                	 while (true) {
                         String input = in.readLine();
                         if (input == null) {    //if message is empty, dont do anything
                             return;
                         }else if(input.equals("LOGOUT")){
                        	 out.println(name+"is removed!");  //if user wants logout, then remove the user
                        	 names.remove(name);
                        	 chattersGroup.remove(out);
                        	// Client.frame.dispose();
                        	// System.exit(0);
                         }
                	for (PrintWriter writer : chattersGroup) {  //write the message as user name : message to the frame
                        writer.println("MESSAGE " + name + " :" + input);
                    }}
                }
                while (true) {
                    String input = in.readLine();
                    if (input == null) {
                        return;
                    }else if(input.equals("LOGOUT")){
                    	out.println(name+"is removed!");    //logout for p2p
                    	names.remove(name);
                    	 chatters.remove(out);
                    	// Client.frame.dispose();   
                    	// System.exit(0);
                    }
                    for (PrintWriter writer : chatters) {   //message for p2p
                        writer.println("MESSAGE " + name + " :" + input);
                    }
                   
                }
            } catch (IOException e) {
                System.out.println(e);
            } finally {
                
                if (name != null) {
                    names.remove(name);   //at last remove names and close connection
                }
                if (out != null) {
                    chatters.remove(out);
                    chattersGroup.remove(out);
                }
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }
    }
}