package project;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

//import project.LogInScreen; //when using Bluej remove this line, if using eclipse keep this line

public class Server  
{ 
   public static void main(String[] args) throws IOException  
   { 
       
       ServerSocket ss = new ServerSocket(5056); 	//new socket with port 5056 for the client to connect to
         
       
       while (true)  
       { 
           Socket s = null; 
             
           try 
           { 
         
               s = ss.accept(); //accept client connection
                 
               System.out.println("A new client has connected : " + s); 	//shows client info (ip, port)
                 
              
               DataInputStream dis = new DataInputStream(s.getInputStream()); 	//define the input and output streams
               DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 

               Thread thr = new Multithread(s, dis, dos); 		//run thread using multithreaded class which enables one thread for one client, which allows multiple clients to connect

               thr.start(); //start thread for the client
                 
           } 
           catch (Exception e){ 
               s.close(); 
               e.printStackTrace(); 
           } 
       } 
   } 
} 
class Multithread extends Thread
{ 

   final DataInputStream dis; 
   final DataOutputStream dos; 
   final Socket s; 
     
 
   
   public Multithread(Socket s, DataInputStream in, DataOutputStream out)  
   { 
       this.s = s; 
       this.dis = in; 
       this.dos = out; 
   } 
 
  
   public void run()
   { 
       String received; 
       String send; 
       while (true)  
       { 
           try { 
 
               dos.writeUTF("Type \"Exit\" to terminate connection.\n"); 	//set of commands we put to prompt to LogIn or Exit
                            
               																													//Type \"LogIn\" to Log In or Sign Up\n
               received = dis.readUTF();
               System.out.println("User says: " + received);	//shows what the user types
                 
               if(received.equals("Exit")) 
               {  
                   
                   this.s.close(); 
                   System.out.println(this.s + " closed the connection"); 	//client closes connection
                   break; 
               } 
              
                switch(received) {   
               //case("LogIn"):
                	//LogInScreen.main(null);	//launches LogIn screen
                	
                       
                default:
                	dos.writeUTF(""); //if user inputs anything other than the set of commands nothing will happen
                    break;
                }    
                     
                   
           } catch (IOException e) { 
               e.printStackTrace(); 
           } 
       } 
         
       try
       { 
           this.dis.close(); 	//close IO streams
           this.dos.close(); 
             
       }catch(IOException e){ 
           e.printStackTrace(); 
       } 
   } 
} 
 

