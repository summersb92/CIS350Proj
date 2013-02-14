package gui;



import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import Engine.TwitterEngine;


/**
 * GetTimePanel.class
 * 
 * Gathers a usertime line of of up to the past 20 statuses
 * of the searched user
 * 
 * @author Ben
 *
 */
@SuppressWarnings("serial")
public class GetTimePanel extends JPanel{

	JPanel getTimePanel;
	JLabel getTimeLineL;
	JTextField getTimeLineF;
	JButton getB, cancelB;
	
	private TwitterEngine engine;
	
	/**
	 * Produces the GetTimePanel
	 * 
	 * @param engine
	 */
	GetTimePanel(TwitterEngine engine){
		this.engine = engine;
		GridLayout tiemLayout = new GridLayout
				(2, 2, 2, 5);
		getTimePanel = new JPanel();
		getTimePanel.setLayout(tiemLayout);

		getTimeLineL = new JLabel("UserName");
		getTimeLineF = new JTextField(20);
		getTimePanel.add(getTimeLineL);
		getTimePanel.add(getTimeLineF);

		getB = new JButton("Get");
		cancelB = new JButton("Clear");
		getB.addActionListener(statusHandeler);
		cancelB.addActionListener(statusHandeler);
		getTimePanel.add(getB);
		getTimePanel.add(cancelB);
		add(getTimePanel);
	}
	private ActionListener statusHandeler = new 
	ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("Get")){
				String userName = getTimeLineF.getText();
				
				if(userName.length() == 0){
					JOptionPane.showMessageDialog(null,
							"No name entered");		    
				}
				else{
				engine.getStatusList(userName);
			}
			
			}	
			if(e.getActionCommand().equals("Clear"))
				getTimeLineF.setText("");
		}
	};
}
