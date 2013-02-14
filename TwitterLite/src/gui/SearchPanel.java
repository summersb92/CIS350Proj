package gui;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import Engine.TwitterEngine;

/**
 * SearchPanel.class
 * 
 * Creates the Search Panel for a user to search Twitter
 * for a particular topic or a users tweets.
 * 
 * @author Ben
 *
 */
@SuppressWarnings("serial")
public class SearchPanel extends JPanel {

	private JPanel searchPanel;
	private JLabel keywordsL, exactPhrasesL, fromUserL,
		toUserL;
	private JTextField keywordsF, exactPhrasesF,
		fromUserF, toUserF;
	private JButton search, cancel;
	private TwitterEngine engine;
	

	/**
	 * Creates the SearchPanel for the user
	 * @param engine
	 */
	public SearchPanel(TwitterEngine engine){
		this.engine = engine;
		
		GridLayout searchLayout = new GridLayout
				(5, 2, 2, 5);
		searchPanel = new JPanel();
		searchPanel.setLayout(searchLayout);
		//keywords
		keywordsL = new JLabel("KeyWords: ");
		keywordsF = new JTextField(20);
		searchPanel.add(keywordsL);
		searchPanel.add(keywordsF);
		//exact phrase
		exactPhrasesL = new JLabel("Exact Phrase: ");
		exactPhrasesF = new JTextField(20);
		searchPanel.add(exactPhrasesL);
		searchPanel.add(exactPhrasesF);
		//form user
		fromUserL = new JLabel("From User: ");
		fromUserF = new JTextField(20);
		searchPanel.add(fromUserL);
		searchPanel.add(fromUserF);
		//to user
		toUserL = new JLabel("To User: ");
		toUserF = new JTextField(20);
		searchPanel.add(toUserL);
		searchPanel.add(toUserF);
		//search , cancel
		search = new JButton("Search");
		cancel = new JButton("Clear");
		search.addActionListener(statusHandeler);
		cancel.addActionListener(statusHandeler);
		searchPanel.add(search);
		searchPanel.add(cancel);
		add(searchPanel);
	}
	private ActionListener statusHandeler = new 
	ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("Search")){
				String keyWord = keywordsF.getText();
				String phrase = exactPhrasesF.getText();
				String fromUser = fromUserF.getText();
				String toUser = toUserF.getText();
				if(keyWord.length()==0){
					JOptionPane.showMessageDialog(null,
					"Key Word is required");	
				}else{
					if(phrase.length()==0 &&
							fromUser.length()==0 &&
							toUser.length()==0)	
						engine.getWordSearch(keyWord);
					else if(fromUser.length()==0 &&
							toUser.length()==0){
						//if from and to are empty
						engine.getPhraseSearch(keyWord,
								phrase);
					}
					else if(phrase.length()==0 &&
							toUser.length()==0)
						//if phrase and to are empty
						engine.getKeyFromSearch(keyWord,
								fromUser);
					else if(phrase.length()==0 &&
							fromUser.length()==0)
						//if phrase and from are empty
						engine.getKeyToSearch(keyWord,
								toUser);
					else if(fromUser.length()==0 &&
							toUser.length()!=0)
						//if from is empty
						engine.getToUserSearch(keyWord,
								phrase, toUser);
					else if(fromUser.length()!=0 &&
							toUser.length()==0)
						//if to is empty
						engine.getFromUserSearch(keyWord,
								phrase, fromUser);
					else 
						engine.getAllSearch(keyWord,
								phrase, toUser, fromUser);			
				}
				engine.sortByDate();
			}
			if(e.getActionCommand().equals("Clear")){
				keywordsF.setText("");
				exactPhrasesF.setText("");
				fromUserF.setText("");
				toUserF.setText("");
			}	
		}	
	};
}
