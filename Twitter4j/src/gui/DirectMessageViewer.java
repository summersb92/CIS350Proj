package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import twitter4j.DirectMessage;
import twitter4j.TwitterException;

import engine.TwitterEngine;

public class DirectMessageViewer {

	private JFrame MessageViewer;
	
	private JPanel MessagePanel;
	
	private List<DirectMessage> Messages;
	
	private JList<String> MessagesList;
	
	private JButton DeleteMessageButton;
	
	private long[] MessageIds;
	
	TwitterEngine engine;
	
	public DirectMessageViewer(TwitterEngine engine) throws TwitterException{
		this.engine = engine;
		
		DefaultListModel<String> model = new DefaultListModel<String>();
		ButtonListener listener = new ButtonListener();
		
		MessageViewer = new JFrame("Messages");
		
		MessagePanel = new JPanel();
		MessagePanel.setLayout(new BorderLayout());
		
		JPanel ButtonsPanel = new JPanel();
		
		Messages = engine.getDirectMessages();
		
		MessagesList = new JList<String>(model);
		MessagesList.setFixedCellHeight(40);
		
		
		MessageIds = new long[50];
		
		int count = 0;
		
		for(DirectMessage message : Messages){
			model.addElement(message.getText());
			MessageIds[count] = message.getId();
			count++;
		}
		
		JScrollPane scrollpane = new JScrollPane(MessagesList);
		
		DeleteMessageButton = new JButton("Delete Message");
		DeleteMessageButton.addActionListener(listener);
		
		ButtonsPanel.add(DeleteMessageButton, BorderLayout.PAGE_END);	
		
		MessagePanel.add(scrollpane, BorderLayout.CENTER);
		MessagePanel.add(ButtonsPanel, BorderLayout.PAGE_END);
		
		MessageViewer.add(MessagePanel);
		
		MessageViewer.setSize(800, 450);
		
		MessageViewer.setVisible(true);
	}
	
	class ButtonListener implements ActionListener {
		public void actionPerformed(final ActionEvent e) {
			if(e.getSource().equals(DeleteMessageButton)){
				try {
					engine.deleteMessage(MessageIds[MessagesList.getSelectedIndex()]);
				} catch (TwitterException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
