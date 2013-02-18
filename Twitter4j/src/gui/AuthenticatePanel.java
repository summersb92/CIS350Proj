package gui;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import twitter4j.TwitterException;

import engine.TwitterEngine;
/**
 * AuthenticationPanel.class
 * 
 * Constructs the authentication panel which is palced
 * inside of the west Panel.
 * @author Ben
 *
 */
@SuppressWarnings("serial")
public class AuthenticatePanel extends JPanel{

	private JPanel authPanel;
	private JLabel userNameL, consumerKeyL, consumerSecretL,
		acessTokenL, acessTokenSecretL;
	private JTextField userNameF, consumerKeyF,
		consumerSecretF, acessTokenF,
		acessTokenSecretF;
	private JButton authenticate, cancel;
	
	private String userName, consumerKey, consumerSecret,
			accessToken, accessTokenS;
	
	private TwitterEngine engine;
	
	/**
	 * Creates the Authenication GUI
	 * @param engine 
	 */
	public AuthenticatePanel(TwitterEngine engine){
		this.engine = engine;
		
		GridLayout authLayout = new GridLayout
				(6, 2, 2, 5);
		authPanel = new JPanel();
		authPanel.setLayout(authLayout);
		//username
		userNameL = new JLabel("User Name: ");
		userNameF = new JTextField(20);
		authPanel.add(userNameL);
		authPanel.add(userNameF);
		//consumer key
		consumerKeyL = new JLabel("Consumer Key: ");
		consumerKeyF = new JTextField(20);
		authPanel.add(consumerKeyL);
		authPanel.add(consumerKeyF);
		//consumer secret
		consumerSecretL = new JLabel("Consumer Secret: ");
		consumerSecretF = new JTextField(20);
		authPanel.add(consumerSecretL);
		authPanel.add(consumerSecretF);
		//access token
		acessTokenL = new JLabel("Acess Token: ");
		acessTokenF = new JTextField(20);
		authPanel.add(acessTokenL);
		authPanel.add(acessTokenF);
		//access token secret
		acessTokenSecretL = new JLabel
				("Acess Token Secret: ");
		acessTokenSecretF = new JTextField(20);
		authPanel.add(acessTokenSecretL);
		authPanel.add(acessTokenSecretF);
		//authenticate , cancel
		authenticate = new JButton("Authenticate");
		cancel = new JButton("Cancel");
		authenticate.addActionListener(loginHandeler);
		cancel.addActionListener(loginHandeler);
		authPanel.add(authenticate);
		authPanel.add(cancel);
		add(authPanel);
	}
	/**
	 * login Handeler manages the two buttons that are
	 * present within the Authenticate Panel. Sends Data
	 * to the Engine and error checks here.
	 */
	private ActionListener loginHandeler =
			new 	ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("Authenticate")){
				userName = userNameF.getText();
				consumerKey = consumerKeyF.getText();
				consumerSecret = consumerSecretF.getText();
				accessToken = acessTokenF.getText();
				accessTokenS = acessTokenSecretF.getText();
				//if any thing is empty then the program
				//can't work.
				if(userName.length() == 0 ||
						consumerKey.length() == 0 ||
						consumerSecret.length() == 0 ||
						accessToken.length() == 0 ||
						accessTokenS.length() == 0){
					JOptionPane.showMessageDialog(null, 
							"Fill in all fields");
				}else{
					//engine.login(userName,
						//	consumerKey, consumerSecret,
							//accessToken, accessTokenS);
					
				}
			}
			if(e.getActionCommand().equals("Cancel"))
				userNameF.setText("");
				consumerKeyF.setText("");
				consumerSecretF.setText("");
				acessTokenF.setText("");
				acessTokenSecretF.setText("");
		}	
	};
}
