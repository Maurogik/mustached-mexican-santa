package client.windows;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;

import client.Client;

public class ConnexionWindow extends JFrame {

	private JPanel contentPane;
	private JTextField textField; 
	private JPasswordField passwordField;

	/**
	 * Create the frame.
	 */
	public ConnexionWindow() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLogin = new JLabel("Login :");
		lblLogin.setBounds(106, 50, 74, 16);
		contentPane.add(lblLogin);
		
		JLabel lblPassword = new JLabel("Password :");
		lblPassword.setBounds(106, 125, 74, 16);
		contentPane.add(lblPassword);
		
		textField = new JTextField();
		textField.setBounds(192, 44, 134, 28);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnAnnuler = new JButton("Annuler");
		btnAnnuler.setBounds(89, 226, 117, 29);
		btnAnnuler.addActionListener(new annuler(this));
		contentPane.add(btnAnnuler);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(192, 119, 134, 28);
		contentPane.add(passwordField);
		
		JButton btnConnexion = new JButton("Connexion");
		btnConnexion.setBounds(253, 226, 117, 29);
		btnConnexion.addActionListener(new connexion(textField, passwordField, this));
		contentPane.add(btnConnexion);
	}
	
	public class connexion implements ActionListener{
		JTextField connexion;
		JPasswordField mdp;
		JFrame frame;
		
		public connexion(JTextField login, JPasswordField pwd, JFrame f){
			connexion = login;
			mdp = pwd;
			frame = f;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				if((Client.iPriv = Client.iPub.login(connexion.getText(), String.valueOf(mdp.getPassword()))) != null) {
					frame.dispose();
				}
			} 
			catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
	public class annuler implements ActionListener{
		JFrame frame;
		public annuler(JFrame f){
			frame = f;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.dispose();
		}
		
	}
}
