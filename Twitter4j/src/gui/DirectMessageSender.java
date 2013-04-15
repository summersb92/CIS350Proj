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

public class DirectMessageSender {

	private JFrame MessageEditor;

	private JPanel MessagePanel;

	private JTextArea MessageArea;

	private JButton SendMessageButton;

	private TwitterEngine engine;

	private String message;

	private long userId;

	public DirectMessageSender(long userId, TwitterEngine engine) {
		this.engine = engine;
		this.userId = userId;
		ButtonListener listener = new ButtonListener();

		MessageEditor = new JFrame("Send Direct Message");

		MessagePanel = new JPanel();
		MessagePanel.setLayout(new BorderLayout());

		MessageArea = new JTextArea();
		MessageArea.setLineWrap(true);

		JScrollPane scrollpane = new JScrollPane(MessageArea);

		SendMessageButton = new JButton("Send Message");
		SendMessageButton.addActionListener(listener);

		MessagePanel.add(scrollpane, BorderLayout.CENTER);
		MessagePanel.add(SendMessageButton, BorderLayout.PAGE_END);

		MessageEditor.add(MessagePanel);

		MessageEditor.setSize(300, 200);

		MessageEditor.setVisible(true);
	}

	class ButtonListener implements ActionListener {
		public void actionPerformed(final ActionEvent e) {

			if (e.getSource().equals(SendMessageButton)) {
				message = MessageArea.getText();
				try {
					engine.sendDirectMessage(userId, message);
					MessageEditor.dispatchEvent(new WindowEvent(MessageEditor,
							WindowEvent.WINDOW_CLOSING));
				} catch (TwitterException e1) {
					if (e1.exceededRateLimitation()) {
						JOptionPane.showMessageDialog(MessageEditor,
								"You message is longer than 140 characters."
										+ "  Please make your message shorter");
					}
					e1.printStackTrace();

				}
			}
		}
	}
}