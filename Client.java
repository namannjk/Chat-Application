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
public class Client extends JFrame {
    Socket socket;
    BufferedReader br;
    PrintWriter out;
   private JLabel heading = new JLabel("Client Area");
   private JTextArea messageArea = new JTextArea();
   private JTextField messageInput = new JTextField();
    private Font font = new Font("Roboto",Font.BOLD,25);
    public Client()
    {
        try{
            System.out.println("Sending Request to server");
            socket = new Socket("127.0.0.1",7777);
            System.out.println("Connection done");
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            createGUI(); 
            handlleEvents();
             startReading();
             //startWriting();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void createGUI()
    {
        this.setTitle("CLient Message");
        this .setSize(500,500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

       heading.setFont(font);
       messageArea.setFont(font);
        messageInput.setFont(font);
         
        // heading.setIcon(new ImageIcon("naman.png"));
        heading.setHorizontalTextPosition(SwingConstants.CENTER);
        // heading.setVerticalTextPosition(SwingConstants.Bottom);  
        heading.setHorizontalAlignment(SwingConstants.CENTER);
         heading.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

       messageArea.setEditable(false);
        this.setLayout(new BorderLayout());

        this.add(heading,BorderLayout.NORTH); 
        JScrollPane jScrollPane = new JScrollPane(messageArea);
        
        this.add(jScrollPane,BorderLayout.CENTER);
        this.add(messageInput,BorderLayout.SOUTH);



        this.setVisible(true);
    }
    public void handlleEvents()
    {
        messageInput.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                // TODO Auto-generated method stub
               // throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // TODO Auto-generated method stub
                //throw new UnsupportedOperationException("Unimplemented method 'keyPressed'");
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // TODO Auto-generated method stub

             //   System.out.println("Key released"+e.getKeyCode());
                if (e.getKeyCode() == 10)
                {
                   String contentToSend = messageInput.getText();
                   messageArea.append("me :" + contentToSend+"\n");
                   out.println(contentToSend);
                   out.flush(); 
                   messageInput.setText("");
                   messageInput.requestFocus();
                }
                
                throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
            }
            

        });
    }
    public void startReading()
    {
        Runnable r1 = ()->{
            System.out.println("Start Reading");
            try{

            while(true)
            {
                    String msg = br.readLine();
                   if(msg.equals("exit")){
                   System.out.println("server Terminated the chat");
                     
                   JOptionPane.showMessageDialog(this, "server terminated the chat");
                   messageInput.setEnabled(false);
                    socket.close();
                   break;}
                  
                    messageArea.append("Naman : " + msg + "\n");
                   //  System.out.println("server :"+ msg);
            }
          }  
                catch(Exception e)
                {
                    e.printStackTrace();
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
        new Client();
    }
}
