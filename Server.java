import java.util.*;
import java.util.logging.SocketHandler;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
class Server
{
    ServerSocket server;
    Socket socket;
    BufferedReader br;
    PrintWriter out;

   // private JLabel heading = new JLabel("Client Area");
   // private JTextArea messageArea = new JTextArea();
   // private JTextField messageInput = new JTextField();
   // private Font font = new Font("Roboto",Font.BOLD,25);

    
    Server()
    {
        try{
            server = new ServerSocket(7777);
            System.out.println("Waiting for Connection");
            socket = server.accept();
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            //createGUI();
            //handlleEvents();
            startReading();
           startWriting();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void startReading()
    {
        Runnable r1 = ()->{
            System.out.println("Start Reading");

            while(true)
            {
                try{
                    String msg = br.readLine();
                    if(msg.equals("exit")){
                    System.out.println("Client Terminated the chat");
                    break;}
                    System.out.println("client :"+ msg);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                
            }
        };
        new Thread(r1).start();
    }
   
    public void startWriting()
    {
        Runnable r2 = ()->{
            while(true)
            {
                try{
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        };
        new Thread(r2).start();
    }
    public static void main(String args[])
    {
        new Server();
    }
}