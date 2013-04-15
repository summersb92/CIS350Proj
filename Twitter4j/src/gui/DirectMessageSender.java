package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import twitter4j.TwitterException;

import engine.TwitterEngine;

/**
 * Class used to send direct message to twitter users.
 */
public class DirectMessageSender {

	/**
	 * Frame for message editor.
	 */
	private JFrame messageEditor;
	/**
	 * Panel for contents of message sender.
	 */
	private JPanel messagePanel;
	/**
	 * Text area to create a message.
	 */
	private JTextArea messageArea;
	/**
	 * Button used to send a message.
	 */
	private JButton sendMessageButton;
	/**
	 * Instance of TwitterEngine.
	 */
	private TwitterEngine engine;
	/**
	 * Message to send.
	 */
	private String message;
	/**
	 * userId to send to.
	 */
	private long userId;
	/**
	 * Constructor for DirectMessageSender.
	 * @param userid user id
	 * @param eng TwitterEngine instance
	 */
	public DirectMessageSender(final long userid, final TwitterEngine eng) {
		this.engine = eng;
		this.userId = userid;
		ButtonListener listener = new ButtonListener();

		messageEditor = new JFrame("Send Direct Message");

		messagePanel = new JPanel();
		messagePanel.setLayout(new BorderLayout());

		messageArea = new JTextArea();
		messageArea.setLineWrap(true);

		JScrollPane scrollpane = new JScrollPane(messageArea);

		sendMessageButton = new JButton("Send Message");
		sendMessageButton.addActionListener(listener);

		messagePanel.add(scrollpane, BorderLayout.CENTER);
		messagePanel.add(sendMessageButton, BorderLayout.PAGE_END);

		messageEditor.add(messagePanel);

		messageEditor.setSize(300, 200);

		messageEditor.setVisible(true);
	}
	/**
	 * Waits for button press and delegates button commands.
	 *
	 */
	class ButtonListener implements ActionListener {
		/**
		 * Waits for action to be performed.
		 * @param e action performed
		 */
		public void actionPerformed(final ActionEvent e) {

			if (e.getSource().equals(sendMessageButton)) {
				message = messageArea.getText();
				try {
					if(message.length() > 0 && message != null){
						engine.sendDirectMessage(userId, message);
						messageEditor.dispatchEvent(new WindowEvent(messageEditor,
							WindowEvent.WINDOW_CLOSING));
					}
				} catch (TwitterException e1) {
					e1.printStackTrace();
					}
				}
			}
		}
	}