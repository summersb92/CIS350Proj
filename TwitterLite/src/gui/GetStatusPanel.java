package gui;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import Engine.TwitterEngine;

/**
 * GetStatusPanel.class is used in the westPanel to
 * gather a user name to search for a particular users
 * current status
 * 
 * @author Ben
 *
 */
@SuppressWarnings("serial")
public class GetStatusPanel extends JPanel{
	
	JPanel getStatusPanel;
	JLabel getStatusL;
	JTextField getStatusF;
	JButton getB, cancelB;
	private TwitterEngine engine;
	
	/**
	 * gets the engine so that the GetStatusPanel may
	 * send an input to the engine and through to the model
	 * and TwitterResultsPanel.
	 * 
	 * @param engine - engine for the program
	 */
	GetStatusPanel(TwitterEngine engine){
		
		this.engine = engine;
		
		GridLayout statusLayout = new GridLayout
				(2, 2, 2, 5);
		getStatusPanel = new JPanel();
		getStatusPanel.setLayout(statusLayout);
		
		getStatusL = new JLabel("UserName");
		getStatusF = new JTextField(20);
		getStatusPanel.add(getStatusL);
		getStatusPanel.add(getStatusF);
		
		getB = new JButton("Get");
		cancelB = new JButton("Clear");
		getB.addActionListener(statusHandeler);
		cancelB.addActionListener(statusHandeler);
		getStatusPanel.add(getB);
		getStatusPanel.add(cancelB);
		add(getStatusPanel);
	}
	/**
	 * Handels all of the actions
	 */
	private ActionListener statusHandeler = new 
	ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("Get")){
				String userName = getStatusF.getText();
				
				//here is an idea.
				if(userName.length() == 0){
					JOptionPane.showMessageDialog(null,
							"No user input");		    
				}
				else{
					engine.getStatus(userName);
				}
				engine.sortByDate();
			}
			if(e.getActionCommand().equals("Clear"))
				getStatusF.setText("");
		}	
	};
}
