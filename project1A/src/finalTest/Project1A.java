package finalTest;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.JToggleButton;

public class Project1A extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JLabel lblPort;
	private JTextField textField_1;
	private JLabel lblNewLabel;
	private JTextField textField_2;
	private JButton btnSend;
	private JLabel lblNewLabel_1;
	private JTextArea textArea;
	private JScrollPane scrollPane;
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	private boolean connectionLost;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Project1A frame = new Project1A();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Project1A() {

	
		
		setTitle("Project 1A");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 500);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblIpAddress = new JLabel("IP Address");
		lblIpAddress.setBounds(10, 14, 161, 14);
		contentPane.add(lblIpAddress);

		textField = new JTextField();
		textField.setBounds(111, 11, 161, 20);
		textField.setText("constance.cs.rutgers.edu");
		contentPane.add(textField);
		textField.setColumns(10);

		lblPort = new JLabel("Port Address");
		lblPort.setBounds(10, 39, 141, 14);
		contentPane.add(lblPort);

		textField_1 = new JTextField();
		textField_1.setText("5520");
		textField_1.setColumns(10);
		textField_1.setBounds(111, 36, 161, 20);
		contentPane.add(textField_1);

		connectionLost = false;
		
		JButton btnNewButton = new JButton("Connect");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btnNewButton) {
					if (btnNewButton.getText() == "Connect") {
						// if connected then change to disconnect at the very end
						try {
							
							clientSocket = new Socket(textField.getText(), Integer.parseInt(textField_1.getText()));
							textArea.append("Connected to Server\n");
							btnNewButton.setText("Disconnect");
							connectionLost = true;
						} catch (UnknownHostException e1) {
							// TODO Auto-generated catch block
							textArea.append("Cannot Connect to server\n");
							//e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							textArea.append("Cannot Connect to server\n");
							//e1.printStackTrace();
						}
					} else {
						try {
							clientSocket.close();
						} catch (IOException e1) {
							textArea.append("Cannot Disconnect from server\n");
							// TODO Auto-generated catch block
							//e1.printStackTrace();
						}
						btnNewButton.setText("Connect");
						textArea.append("Disconnected!\n");
						connectionLost = false;
					}
				}
			}
		});
		btnNewButton.setBounds(307, 35, 117, 23);
		contentPane.add(btnNewButton);

		lblNewLabel = new JLabel("Message to Server");
		lblNewLabel.setBounds(10, 80, 141, 14);
		contentPane.add(lblNewLabel);

		textField_2 = new JTextField();
		textField_2.setBounds(10, 105, 414, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);

		btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btnSend) {
						try {
							out = new PrintWriter(clientSocket.getOutputStream(), true);
							in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
							//String test = in.readLine();
							out.println(textField_2.getText());
							String resp = in.readLine();
							textArea.append("Client: " + textField_2.getText() + "\n");
							textArea.append("Server: " + resp + "\n");

							if (resp.equals("Good Bye!")) {
								clientSocket.close();
								btnNewButton.setText("Connect");
								out.close();
								in.close();
								textArea.append("Disconnected!\n");
								connectionLost = false;
							}
						} catch (Exception e1) {
							textArea.append("Not Connected to server\n");
							btnNewButton.setText("Connect");
							if (clientSocket.isBound() && connectionLost == true) {
								textArea.append("Disconnected!\n");
								connectionLost = false;
							}
							// TODO Auto-generated catch block
							//e1.printStackTrace();
						}
				}
			}
		});
		btnSend.setBounds(10, 130, 89, 23);
		contentPane.add(btnSend);

		lblNewLabel_1 = new JLabel("Client/Server Communication");
		lblNewLabel_1.setBounds(10, 164, 204, 14);
		contentPane.add(lblNewLabel_1);

		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 179, 414, 271);
		contentPane.add(scrollPane);

		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setEditable(false);
		textArea.setLineWrap(true);

	}
}
