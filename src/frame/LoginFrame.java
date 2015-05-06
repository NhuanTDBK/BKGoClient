package frame;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import controller.LoginForm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import static mydropbox.MyDropboxSwing.userId;
import org.restlet.ext.xml.DomRepresentation;



public class LoginFrame extends JFrame {

	private JPanel contentPane;
	private JTextField userName;
	private JTextField password;
	private JTextField connection;

	public JPanel getContentPane() {
		return contentPane;
	}

	public void setContentPane(JPanel contentPane) {
		this.contentPane = contentPane;
	}

	public JTextField getUserName() {
		return userName;
	}

	public void setUserName(JTextField userName) {
		this.userName = userName;
	}

	public JTextField getPassword() {
		return password;
	}

	public void setPassword(JTextField password) {
		this.password = password;
	}

	public JTextField getConnection() {
		return connection;
	}

	public void setConnection(JTextField connection) {
		this.connection = connection;
	}

	/**
	 * Launch the application.
	 */
	 public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame();
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
	 public LoginFrame() {
		 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 setBounds(100, 100, 450, 300);
		 contentPane = new JPanel();
		 contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		 setContentPane(contentPane);
		 contentPane.setLayout(null);

		 JLabel lblUserName = new JLabel("User name");
		 lblUserName.setBounds(46, 56, 85, 15);
		 contentPane.add(lblUserName);

		 JLabel lblNewLabel = new JLabel("Password");
		 lblNewLabel.setBounds(46, 107, 70, 15);
		 contentPane.add(lblNewLabel);

		 userName = new JTextField();
		 userName.setBounds(181, 54, 114, 19);
		 contentPane.add(userName);
		 userName.setColumns(10);

		 password = new JTextField();
		 password.setBounds(181, 105, 114, 19);
		 contentPane.add(password);
		 password.setColumns(10);

		 JButton btnLogin = new JButton("Login");
		 btnLogin.addActionListener(new ActionListener() {

			 @Override
			 public void actionPerformed(ActionEvent e) {
				 //Kiem tra dang nhap
				 DomRepresentation login = null;
				 try {
					 login = LoginForm.result(userName.getText(), password.getText());
					 if (login == null) {
						 JOptionPane.showMessageDialog(null, "Retry to login");
						 return;
					 }
				 } catch (Exception ex) {
					 ex.printStackTrace();
				 }
				 //Load setting tu file


				 //		userId = Integer.parseInt(login.getText("/User/UserId"));
			 }
		 });
		 btnLogin.setBounds(110, 216, 117, 25);
		 contentPane.add(btnLogin);

		 JButton btnQuit = new JButton("Quit");
		 btnQuit.setBounds(239, 216, 117, 25);
		 contentPane.add(btnQuit);

		 JLabel lblNewLabel_1 = new JLabel("host");
		 lblNewLabel_1.setBounds(46, 155, 70, 15);
		 contentPane.add(lblNewLabel_1);

		 connection = new JTextField();
		 connection.setBounds(181, 153, 114, 19);
		 contentPane.add(connection);
		 connection.setColumns(10);
	 }
}
