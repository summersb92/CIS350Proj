package gui;



import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

import engine.TwitterEngine;
import gui.PostPanel.ButtonListener;

import java.io.*;
import java.net.URL;
import java.util.List;


@SuppressWarnings("serial")
/**
 * Creates the GUI and runs the application
 * 
 * @author Ben
 */
public class TwitterGUI extends javax.swing.JFrame{

	private String[] options={"Authenticate User",
			"Get Status", "Get UserTimeline","Post Status",
			"Search for Statuses"};
//	private JComboBox combo;
	private JPanel eastPanel, westPanel;
	private JFrame GUI;
	private JMenuBar menu;
	private JTabbedPane tabs;
	private JMenu file, generate, sort, help; 
	private JMenuItem fileExport, fileDeleteTable, fileQuit,
			fileDeleteStatus, generateWordFrequencyList, 
			generateTopTrendingList, help_About;
	private TwitterResultsPanel results;
	private JPanel profile;
//	private GetStatusPanel getStatusP;
	private GetTimePanel getTimeP;
	private PostPanel postP;
//	private SearchPanel searchP;
	
	private JButton loginButton;
	private JButton signoutButton;
	
	private TwitterEngine engine;
	
	/**
	 * Packs and sets the GUI
	 * @throws TwitterException 
	 */
	public TwitterGUI() throws TwitterException{
		engine = new TwitterEngine();
		GUI = new JFrame("Twitter Lite");
		tabs = new JTabbedPane();
		engine.login();
		menuInit();
		ProfileTabInit();
		WestPanelInit();
		EastPanelInit();
		
		GUI.add(tabs);
		GUI.pack();
		GUI.setVisible(true);
	}
	
	public void ProfileTabInit() throws IllegalStateException, TwitterException{
		profile = new JPanel();
		profile.setLayout(new BorderLayout());
		
		ButtonListener listener = new ButtonListener();
		
		Twitter twitter = TwitterFactory.getSingleton();
		User user = twitter.showUser(twitter.getId());
		@SuppressWarnings("deprecation")
		URL url = user.getProfileImageUrlHttps();
		ImageIcon img = new ImageIcon(url);
		
		JLabel UserIcon = new JLabel(img);
		
		JPanel  user1 = new JPanel();
		JPanel login = new JPanel();
		
		user1.add(UserIcon);
		
		loginButton = new JButton("Log In");
		loginButton.addActionListener(listener);
		signoutButton = new JButton("Sign Out");
		
		
		login.add(loginButton);
		login.add(signoutButton);
		
		profile.add(user1, BorderLayout.PAGE_START);
		profile.add(login, BorderLayout.PAGE_END);
		
		
		
		tabs.addTab("Profile", profile);
		
		GUI.add(tabs);
		
	}
	/**
	 * Creates the Eastern Panel using the
	 * class TwitterResultsPanel();
	 */
	private void EastPanelInit() {
		eastPanel = new JPanel();
		eastPanel.setLayout(new BoxLayout
				(eastPanel, BoxLayout.Y_AXIS));
		results = new TwitterResultsPanel(engine);
		eastPanel.add(results);
		//GUI.add(eastPanel, BorderLayout.EAST);
		tabs.addTab("Followers", eastPanel);
	}
	/**
	 * Contains the Panels in the Operation Section.
	 * The combobox allows selection bettween the different
	 * options.
	 * @throws TwitterException 
	 */
	private void WestPanelInit() throws TwitterException {
		westPanel = new JPanel();
		westPanel.setLayout(new BoxLayout
				(westPanel, BoxLayout.Y_AXIS));
		getTimeP = new GetTimePanel(engine);
		postP = new PostPanel(engine);
//		searchP = new SearchPanel(engine);
		TitledBorder operationTitle = 
				BorderFactory.createTitledBorder
        		("Select Operation");

		westPanel.add(Box.createVerticalGlue());
		westPanel.add(postP);
		westPanel.add(getTimeP);
		
		tabs.addTab("Post Tweet/Timeline", westPanel);
		//GUI.add(westPanel, BorderLayout.WEST);
		//GUI.pack();
	}
	
    protected JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        //panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
    }
    
	class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(e.getSource().equals(loginButton)){
				engine.login();
			}
		}
	}
     
	/**
	 * menuInit() geneartes the JMenuBar to be used.
	 */
	private void menuInit() {
		menu = new JMenuBar();
		//Creates the File Menu
		file = new JMenu("File");
		fileDeleteStatus = new JMenuItem("Delete Status");
		fileDeleteTable = new
			JMenuItem("Delete Table Status");
		fileExport = new JMenuItem("Export to XML ...");
		fileQuit = new JMenuItem("Quit");
		fileDeleteStatus.addActionListener(menuHandeler);
		fileDeleteTable.addActionListener(menuHandeler);
		fileExport.addActionListener(menuHandeler);
		fileQuit.addActionListener(menuHandeler);
		file.add(fileDeleteStatus);
		file.add(fileDeleteTable);
		file.add(fileExport);
		file.add(fileQuit);
		menu.add(file);
		//Creates the Generate Menu
		generate = new JMenu("Generate");
		generateWordFrequencyList = new JMenuItem
				("Word Frequency List");
		generateTopTrendingList = new JMenuItem
				("Top Trending List");
		generateWordFrequencyList.addActionListener
			(menuHandeler);
		generateTopTrendingList.addActionListener
			(menuHandeler);
		generate.add(generateWordFrequencyList);
		generate.add(generateTopTrendingList);
		menu.add(generate);
		//Create the Help Menu
		help = new JMenu("Help");
		help_About = new JMenuItem("About...");
		help_About.addActionListener(menuHandeler);
		help.add(help_About);
		menu.add(help);
		//Add Menu to the GUI
		GUI.setJMenuBar(menu);
	}
	/**
	 * Handels all of the actions in the JMenuBar
	 */
	private ActionListener menuHandeler = new 
			ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			//System.out.println(e);
			//if(e.getActionCommand().equals
				//	("Delete Status")){
				//engine.deleteStatus();
			//}
			if(e.getActionCommand().equals
					("Delete Table Status")){
				engine.deleteTweet();
			}
			if(e.getActionCommand().equals
					("Export to XML ...")){
				saveButtonAction();
			}
			if(e.getActionCommand().equals("Quit")){
				System.exit(0);
			}
			if(e.getActionCommand().equals("About...")){
				JOptionPane.showMessageDialog(null, 
						"Produced by\n Benjamin Summers \n" +
						"			 Kevin Anderson     \n" +
						"			 Seth Hilaski       \n" +
						"			 Trent Newberry     \n" +
						"            2/17/2013          \n" +
						"            For a CIS350 Project");
			}
		}	
	};
	/**
	 * Handels which panel is active bellow the ComboBox
	 */
//	private ActionListener switchHandeler = new 
//			ActionListener(){
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			westPanel.remove(authP);
//			westPanel.remove(getStatusP);
//			westPanel.remove(getTimeP);
//			westPanel.remove(postP);
//			westPanel.remove(searchP);
//			//0 is the combo box, and 1 is whats beneath
//			//westPanel.remove(1);
//			GUI.pack();
//			switch(combo.getSelectedIndex()){
//			case 0: westPanel.add(authP); GUI.pack(); break;
//			case 1: westPanel.add(getStatusP); GUI.pack();
//				break;
//			case 2: westPanel.add(getTimeP); GUI.pack();
//				break; //timeline
//			case 3: westPanel.add(postP); GUI.pack(); break;
//			case 4: westPanel.add(searchP); GUI.pack();
//				break; //search
//			}	
//			GUI.pack();
//		}
//	};
	/**
	 * uses the saveXML action to save a the twitter Status
	 * list as an XML file.
	 */
	private void saveButtonAction() {
        
    }
	/**
	 * runs the main application starting with TwitterGUI
	 * which instantiates all other methods that are part
	 * of the GUI
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                try {
					new TwitterGUI();
					
				} catch (TwitterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });  
    }
	
}