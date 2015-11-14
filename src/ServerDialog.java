
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
import java.util.Queue;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;


public class ServerDialog extends JDialog {
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	static JLabel lblDatabase = new JLabel("Connecting to Database...");
	static JLabel lblPhone = new JLabel("0 phone(s) are connected");
	static ServerDialog dialog = new ServerDialog();
	static JCheckBox chckbxAlwaysOnTop;
	static Map<String, PrintWriter> client_out = new HashMap<String, PrintWriter>();
	static int portNumber = 8088;
	static ServerSocket serverSocket = null;
    static Socket socket = null;
    static int connected_phone_count = 0;
    
	
    public static void main(String[] args) {
		try {
			
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//connect to database
		if (DatabaseClass.ConnectDB() == true)
		{
			lblDatabase.setText("Database is Online");
			lblDatabase.setIcon(new ImageIcon(ServerDialog.class.getResource("data_ok.png")));
		}
		else
		{
			lblDatabase.setText("Database could not be connected");
			lblDatabase.setIcon(new ImageIcon(ServerDialog.class.getResource("Warning.png")));
		}
		//DatabaseClass.get_locations();
		//Queue<String> Q = DatabaseClass.get_routes("azimpur", "science lab");
		//System.out.println(Q.size()/6);
		//connect to server
		try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }
		//System.out.println("Server is connected");
		
		//listen to port
		while (true) {
            try {
                socket = serverSocket.accept();
                connected_phone_count++;
                lblPhone.setText(String.valueOf(connected_phone_count) +  " Phone(s) are connected");
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
	        //String ID = null;
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
	                else if (line.equals(ConnectionClass.LOCATION_LIST))
	                {
	                	String[] s = DatabaseClass.get_locations();
	                	//System.out.println("go location req, total " + s.length);
	                	out.println(ConnectionClass.LOCATION_LIST);
	                	out.println(s.length);
	                	for (int i = 0; i<s.length; i++)
	                	{
	                		out.println(s[i]);
	                	}
	                }
	                else if (line.equals(ConnectionClass.ROUTE_RESULT))
	                {
	                	String src = in.readLine();
	                	String dest = in.readLine();
	                	Queue<String> Q = DatabaseClass.get_routes(src, dest);
	                	
	                	int len = Q.size()/6;
	                	
	                	out.println(ConnectionClass.ROUTE_RESULT);
	                	out.println(len);
	                	for (int i = 0; i<len; i++)
	                	{
	                		for (int j = 1;j <=6; j++)
	                			out.println(Q.remove());
	                	}
	                }
	                
	            } 
	            catch (NullPointerException e1)
	            {
	            	//System.out.println("Client Terminated in Nullpointer.");
	            	try {
	            		out.close();
						in.close();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
	            	connected_phone_count--;
	                lblPhone.setText(String.valueOf(connected_phone_count) +  " Phone(s) are connected");
	            	break;
	            }
	            catch (IOException e) {
	            	//System.out.println("Client Terminated in IOException.");
	            	try {
	            		out.close();
						in.close();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
	            	connected_phone_count--;
	                lblPhone.setText(String.valueOf(connected_phone_count) +  " Phone(s) are connected");
	            	break;
	                //return;
	            }
	            
	        }
	    }
	}
	/**
	 * Create the dialog.
	 */
	public ServerDialog() {
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Server Manager and Communicator");
		setResizable(false);
		setBounds(100, 100, 400, 250);
		getContentPane().setLayout(null);
		
		JLabel lblOnline = new JLabel("Connection Socket is open at port: 8088");
		lblOnline.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblOnline.setBounds(26, 25, 372, 14);
		getContentPane().add(lblOnline);
		
		lblDatabase.setIcon(new ImageIcon(ServerDialog.class.getResource("connecting.png")));
		lblPhone.setIcon(new ImageIcon(ServerDialog.class.getResource("android.png")));
		
		lblDatabase.setBounds(37, 50, 372, 23);
		getContentPane().add(lblDatabase);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DatabaseClass.closeDB();
				System.exit(1);
			}
		});
		btnExit.setBounds(295, 190, 89, 23);
		getContentPane().add(btnExit);
		
		
		lblPhone.setBounds(36, 72, 373, 23);
		getContentPane().add(lblPhone);
		
		chckbxAlwaysOnTop = new JCheckBox("Always on top");
		chckbxAlwaysOnTop.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				setAlwaysOnTop(chckbxAlwaysOnTop.isSelected());
			}
		});
		chckbxAlwaysOnTop.setSelected(true);
		chckbxAlwaysOnTop.setBounds(6, 190, 204, 23);
		getContentPane().add(chckbxAlwaysOnTop);

	}
}
