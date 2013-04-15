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
/**
 * Class used to view a direct message.
 *
 */
public class DirectMessageViewer {
	/**
	 * Frame for the message viewer.
	 */
	private JFrame messageViewer;
	/**
	 * Panel for the message viewer.
	 */
	private JPanel messagePanel;
	/**
	 * List of direct messages.
	 */
	private List<DirectMessage> messages;
	/**
	 * JList to display messages.
	 */
	private JList<String> messagesList;
	/**
	 * Button to delete a message.
	 */
	private JButton deleteMessageButton;
	/**
	 * Array containing message ids.
	 */
	private long[] messageIds;
	/**
	 * TwitterEngine instance.
	 */
	private TwitterEngine engine;
	/**
	 * Constructor.
	 * @param eng TwitterEngine instance
	 * @throws TwitterException 
	 */
	public DirectMessageViewer(final TwitterEngine eng) 
	throws TwitterException {
		this.engine = eng;
		
		DefaultListModel<String> model = new DefaultListModel<String>();
		ButtonListener listener = new ButtonListener();
		
		messageViewer = new JFrame("Messages");
		
		messagePanel = new JPanel();
		messagePanel.setLayout(new BorderLayout());
		
		JPanel buttonsPanel = new JPanel();
		
		messages = eng.getDirectMessages();
		
		messagesList = new JList<String>(model);
		messagesList.setFixedCellHeight(40);
		
		
		messageIds = new long[50];
		
		int count = 0;
		
		for (DirectMessage message : messages) {
			model.addElement(message.getText());
			messageIds[count] = message.getId();
			count++;
		}
		
		JScrollPane scrollpane = new JScrollPane(messagesList);
		
		deleteMessageButton = new JButton("Delete Message");
		deleteMessageButton.addActionListener(listener);
		
		buttonsPanel.add(deleteMessageButton, BorderLayout.PAGE_END);	
		
		messagePanel.add(scrollpane, BorderLayout.CENTER);
		messagePanel.add(buttonsPanel, BorderLayout.PAGE_END);
		
		messageViewer.add(messagePanel);
		
		messageViewer.setSize(800, 450);
		
		messageViewer.setVisible(true);
	}
	/**
	 * ButtonListener delegates button commands.
	 *
	 */
	class ButtonListener implements ActionListener {
		/**
		 * Waits for button press.
		 * @param e action performed
		 */
		public void actionPerformed(final ActionEvent e) {
			if (e.getSource().equals(deleteMessageButton)) {
				try {
					if(messagesList.getSelectedIndex() >= 0){
						engine.deleteMessage(
						messageIds[messagesList.getSelectedIndex()]);
					}
				} catch (TwitterException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
