package gui;
import engine.TwitterEngine;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import twitter4j.*;

@SuppressWarnings("serial")
public class PostPanel extends JPanel{
	
	
	private Twitter twitter = TwitterFactory.getSingleton();
	
	private JTextField updateTextBox;
	private JPanel UpdatePanel;
	private JButton tweet;
	private TwitterEngine engine;
	
	
	public PostPanel(engine.TwitterEngine engine2) throws TwitterException{
		ButtonListener listener = new ButtonListener();
		
		
		UpdatePanel = new JPanel();
		UpdatePanel.setLayout(new FlowLayout());
		tweet = new JButton("Post Tweet");
		tweet.addActionListener((listener));
		
		updateTextBox = new JTextField();;
		
				
		UpdatePanel.add(updateTextBox);
		UpdatePanel.add(tweet);
		
		UpdatePanel.updateUI();
		
		add(UpdatePanel);		
}
	
	
	public void postTweet(){
		
		String post = updateTextBox.getText();
		System.out.println(post);
		engine.postStatus(post);
		tweet.setText("");
	}
	


	class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(e.getSource().equals(tweet)){
				postTweet();
			}
		}
	}
}

