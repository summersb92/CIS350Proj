package gui;
import engine.TwitterEngine;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;


@SuppressWarnings("serial")
public class PostPanel extends JPanel {
	
	/** Twitter fields holds a twitter instance. */
	private Twitter twitter = TwitterFactory.getSingleton();
	/** GUI text field for new twitter status. */
	private JTextField updateTextBox;
	/** GUI panel for the updateTextBox. */
	private JPanel updatePanel;
	/** Button for posting a tweet. */
	private JButton tweet;
	/**  */
	private TwitterEngine engine;

	/**
	 * Creates the post panel for a new making a new twitter status.
	 * @param engine - engine variable that communicates with model
	 * @throws TwitterException - twitter exception
	 *   
	 */
	public PostPanel(final TwitterEngine engine) throws TwitterException {
		this.engine = engine;
		ButtonListener listener = new ButtonListener();
		
		
		updatePanel = new JPanel();
		updatePanel.setLayout(new FlowLayout());
		tweet = new JButton("Post Tweet");
		tweet.addActionListener((listener));
		
		updateTextBox = new JTextField(35);
		
				
		updatePanel.add(updateTextBox);
		updatePanel.add(tweet);
		
		updatePanel.updateUI();
		
		add(updatePanel);		
}
	/**
	 * Buttonlistener for the post tweet button.
	 * 
	 *
	 */
	class ButtonListener implements ActionListener {
		
		/**
		 * Contains commands when tweet button is pressed.
		 * @param e - an event (button is pressed)
		 */
		public void actionPerformed(final ActionEvent e) {
			if (e.getSource().equals(tweet)) {
				try {
					String post = updateTextBox.getText();
					System.out.println(post);
					engine.postStatus(post);
					tweet.setText("");
				} catch (TwitterException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
}

