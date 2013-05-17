package client.windows;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.JButton;

public class MainWindow extends JFrame {

	private JPanel contentPane;
	 
	/**
	 * Create the frame.
	 */
	public MainWindow() {
		setResizable(false);
		setTitle("ROAR");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnConnexion = new JButton("Connexion");
		btnConnexion.setBounds(166, 81, 117, 29);
		btnConnexion.addActionListener(new Connexion(this));
		contentPane.add(btnConnexion);
		
		JButton btnQuitter = new JButton("Quitter");
		btnQuitter.setBounds(166, 160, 117, 29);
		contentPane.add(btnQuitter);
	}
	
	public class Connexion implements ActionListener{
		JFrame frame;
		public Connexion(JFrame f) {
			frame = f;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			ConnexionWindow con = new ConnexionWindow();
			con.setVisible(true);
			this.frame.dispose();
		}
		
	}
}
