package gui;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import Engine.TwitterEngine;
import winterwell.jtwitter.TwitterException;

/**
 * PostPanel.class
 * 
 * Allows a user to post a new status
 * 
 * @author Ben
 */
@SuppressWarnings("serial")
public class PostPanel extends JPanel{

	private JPanel postPanel;
	private JLabel postPanelL;
	private JTextArea postPanelF;
	private JButton post, cancelB;
	private TwitterEngine engine;

	/**
	 * Generates the PostPanel
	 * 
	 * @param engine
	 */
	PostPanel(TwitterEngine engine){
		this.engine = engine;
		
		GridLayout postLayout = new GridLayout
				(2, 2, 2, 5);
		postPanel = new JPanel();
		postPanel.setLayout(postLayout);

		postPanelL = new JLabel("Status: ");
		postPanelF = new JTextArea(4, 20);
        postPanelF.setLineWrap(true);
        JScrollPane scrollPane =
        	new JScrollPane(postPanelF);
		postPanel.add(postPanelL);
		postPanel.add(scrollPane);
		
		post = new JButton("Post");
		cancelB = new JButton("Clear");
		post.addActionListener(statusHandeler);
		cancelB.addActionListener(statusHandeler);
		postPanel.add(post);
		postPanel.add(cancelB);
		add(postPanel);
	}
	
	private ActionListener statusHandeler = new 
	ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("Post")){
				String post = postPanelF.getText();
				
				if(post.length() == 0){
					JOptionPane.showMessageDialog(null,
					"You did not enter a post to upload");		    
				}
				if(post.length() > 140){
					JOptionPane.showMessageDialog(null,
					"The message is longer than 140" +
					"characters. Remove "+
					(post.length()-140)+" characters.");
				}
				else{
					try {
						engine.postStatus(post);
					} catch (TwitterException e1) {
						// TODO Auto-generated catch block
						//e1.printStackTrace();
						//System.out.println("failed");
					}
				}
			}
			if(e.getActionCommand().equals("Clear")){
				postPanelF.setText("");	}
		}
	};
}
