package client;
import java.io.*; 
import java.net.*; 
import java.util.Scanner;

import project.LogInScreen;


public class client extends Thread
{ 
	/**
	 * Client code 
	 */
    public static void main(String[] args) throws IOException  
    {
        try
        { 
            Scanner scan = new Scanner(System.in); 
            InetAddress ip = InetAddress.getByName("localhost"); //gets host ip address
 
            //Socket s = new Socket("192.168.56.1", 5056); //need to change IP address since it differs with each user (Alternatively)

            
            Socket s = new Socket(ip, 5056);
            DataInputStream dis = new DataInputStream(s.getInputStream()); 
            DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
      
            LogInScreen.main(null); //launch from each client independently to enable multiple accounts in different instances to open
            while (true)  
            { 
                System.out.println(dis.readUTF()); 
                String line = scan.nextLine(); 
                dos.writeUTF(line); 
                
               
                if(line.equals("Exit")) //need to type exit in console before closing the log in screen or the server will crash since client will be considered as leaving the server without closing the connection to the server properly
                { 
                    s.close(); 
                    System.out.println("Connection closed"); 
                    break; 
                }   
                String received = dis.readUTF(); 
                System.out.println(received);
            } 
            
           
            scan.close(); 
            dis.close(); 
            dos.close(); 
        }
        catch(Exception e){ 
            e.printStackTrace(); 
        } 
    } 
} 
