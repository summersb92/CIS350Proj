package gui;
import engine.TwitterEngine;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;


@SuppressWarnings("serial")
public class PostPanel extends JPanel{
	
	
	private Twitter twitter = TwitterFactory.getSingleton();
	
	private JTextField updateTextBox;
	private JPanel UpdatePanel;
	private JButton tweet;
	private TwitterEngine engine;

	
	public PostPanel(TwitterEngine engine) throws TwitterException{
		this.engine = engine;
		ButtonListener listener = new ButtonListener();
		
		
		UpdatePanel = new JPanel();
		UpdatePanel.setLayout(new FlowLayout());
		tweet = new JButton("Post Tweet");
		tweet.addActionListener((listener));
		
		updateTextBox = new JTextField(35);
		
				
		UpdatePanel.add(updateTextBox);
		UpdatePanel.add(tweet);
		
		UpdatePanel.updateUI();
		
		add(UpdatePanel);		
}
	
	class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(e.getSource().equals(tweet)){
				try {
					String post = updateTextBox.getText();
					System.out.println(post);
					engine.postStatus(post);
					tweet.setText("");
					UpdatePanel.updateUI();
				} catch (TwitterException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
}

