
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;


public class ServerDialog extends JDialog {
	static JLabel lblDatabase = new JLabel("Database");
	static JLabel lblPhone = new JLabel("0 phone(s) are connected");
	static ServerDialog dialog = new ServerDialog();
	
	static Map<String, PrintWriter> client_out = new HashMap<String, PrintWriter>();
	static int portNumber = 8088;
	static ServerSocket serverSocket = null;
    static Socket socket = null;
    
    
	
    public static void main(String[] args) {
		try {
			
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }
		System.out.println("Server is connected");
		
		if (DatabaseClass.ConnectDB() == true)
			lblDatabase.setText("Database is Online");
		else
			lblDatabase.setText("Database could not be connected");
		
		while (true) {
            try {
                socket = serverSocket.accept();
                lblPhone.setText("Phone is connected");
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            }
            // new threa for a client
            new ClientThread(socket).start();
            
        }
		/*EventQueue.invokeLater(new Runnable() {
			public void run() {*/
				
				/**/
				
				/*
				if (DatabaseClass.New_cred("onix1", "abc") == true)
					System.out.println("New user created");
				else
					System.out.println("Sorry, username already exists");
				//DatabaseClass.Check_cred("oni", "sddsf");
				//DatabaseClass.User_Exists("boib");
				*/
				
				/**
				 * Open a server socket
				 */
				
				
				//DatabaseClass.closeDB();
			
		
	}

	public static class ClientThread extends Thread {
	    protected Socket socket;
	    public ClientThread(Socket clientSocket) {
	        this.socket = clientSocket;
	    }

	    public void run() {
	        //InputStream inp = null;
	        //BufferedReader brinp = null;
	        //DataOutputStream out = null;
	    	PrintWriter out = null;
            BufferedReader in = null;
	        try {
	        	out = new PrintWriter(socket.getOutputStream(), true);
	            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	        } catch (IOException e) {
	            return;
	        }
	        String line;
	        String ID = null;
	        while (true) {
	            try {
	                line = in.readLine();
	                
	                if (line.equals(ConnectionClass.LOGIN_SQ))
	                {
	                	String user = in.readLine();
	                	String pass = in.readLine();
	                	String success = "0";
	                	if (DatabaseClass.Check_cred(user, pass)==true)
	                		success = "1";
	                	else
	                		success = "0";
	                	out.println("LOGIN");
	                	out.println(success);
	                }
	                else if (line.equals(ConnectionClass.TEST_CONN))
	                {
	                	System.out.println("Android said hi");
	                	out.println("PING");
	                }
	                else if (line.equals(ConnectionClass.SIGNUP_SQ))
	                {
	                	String user = in.readLine();
	                	String pass = in.readLine();
	                	String success = "0";
	                	if (DatabaseClass.New_cred(user, pass)==true)
	                		success = "1";
	                	else
	                		success = "0";
	                	out.println("SIGNUP");
	                	out.println(success);
	                }
	                
	            } catch (IOException e) {
	            	System.out.println("Client Terminated");
	                return;
	            }
	        }
	    }
	}
	/**
	 * Create the dialog.
	 */
	public ServerDialog() {
		setTitle("Server Manager and Communicator");
		setResizable(false);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JLabel lblOnline = new JLabel("Connection Socket is open at 8088");
		lblOnline.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblOnline.setBounds(62, 55, 221, 14);
		getContentPane().add(lblOnline);
		
		
		lblDatabase.setBounds(62, 80, 221, 14);
		getContentPane().add(lblDatabase);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//DatabaseClass.closeDB();
				System.exit(1);
			}
		});
		btnExit.setBounds(345, 240, 89, 23);
		getContentPane().add(btnExit);
		
		
		lblPhone.setBounds(62, 105, 160, 14);
		getContentPane().add(lblPhone);

	}
}
