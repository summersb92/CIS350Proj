import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
	
	public AuthenticatePanel(){
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
	private ActionListener loginHandeler = new 
			ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("Authenticate"))
				userName = userNameF.getText();
				consumerKey = consumerKeyF.getText();
				consumerSecret = consumerSecretF.getText();
				accessToken = acessTokenF.getText();
				accessTokenS = acessTokenSecretF.getText();
				
				
				TwitterEngine.login(userName, consumerKey,
						consumerSecret, accessToken,
						accessTokenS);
				System.out.println(userNameF.getText());
			if(e.getActionCommand().equals("Cancel"))
				System.out.println("You know, that is really futile");	
		}	
	};
}
