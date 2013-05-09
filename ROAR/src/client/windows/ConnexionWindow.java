package client.windows;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;

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
		
		JButton btnConnexion = new JButton("Connexion");
		btnConnexion.setBounds(253, 226, 117, 29);
		contentPane.add(btnConnexion);
		
		JButton btnAnnuler = new JButton("Annuler");
		btnAnnuler.setBounds(89, 226, 117, 29);
		contentPane.add(btnAnnuler);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(192, 119, 134, 28);
		contentPane.add(passwordField);
	}
}
