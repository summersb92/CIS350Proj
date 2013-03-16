package gui;



import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import twitter4j.Status;
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
public class TwitterGUI extends JFrame{

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
	//private TwitterResultsPanel results;
	private JPanel profile;
//	private GetStatusPanel getStatusP;
	private GetTimePanel getTimeP;
	private PostPanel postP;
//	private SearchPanel searchP;
	
	private JButton loginButton;
	private JButton signoutButton;
	private JButton updateTimelineButton;
	
	private TwitterEngine engine;
	
	private JTextArea textArea;
	private JPanel twitResults;
	private TitledBorder tableTitle, textTitle;
	private JTable table;
	private JTextField updateTextBox;
	private JPanel UpdatePanel;
	private JButton tweet;
	private JPanel PostTimePanel;
	private JPanel timeLinePanel;
	private JTextArea timeLineArea;
	private Twitter twitter;
	private List<Status> statuses;
	
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
		PostTimeTabInit();
		FollowerTabInit();
		
		GUI.add(tabs);
		GUI.pack();
		GUI.setVisible(true);
	}
	
	public void ProfileTabInit() throws IllegalStateException, TwitterException{
		profile = new JPanel();
		profile.setLayout(new BorderLayout());
		
		ButtonListener listener = new ButtonListener();

		ImageIcon img = engine.getProfileImage();
		
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
	private void FollowerTabInit() {
		
		GridLayout resultLayout = new GridLayout
				(2, 2, 5, 10);
				twitResults = new JPanel();
				twitResults.setLayout(resultLayout);

				//Table Creation
				table = new JTable();
				table.setToolTipText("");
				table.setSelectionMode(javax.swing.
						ListSelectionModel.SINGLE_SELECTION);
				table.getTableHeader().setReorderingAllowed(false);

				table.addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseClicked(java.awt.event.MouseEvent evt) {
						tableMouseClicked();
					}
				});
				table.setPreferredScrollableViewportSize
				(new Dimension(500, 140));
				//table.setFillsViewportHeight(true);
				JScrollPane scrollPane = new JScrollPane();
				tableTitle = BorderFactory.createTitledBorder
				("Statuses");
				table.setModel(engine.getModel());
				scrollPane.setVerticalScrollBarPolicy(JScrollPane.
						VERTICAL_SCROLLBAR_ALWAYS);
				//attaches table to the scroll pane
				scrollPane.setViewportView(table); 
				scrollPane.setBorder(tableTitle);
				twitResults.add(scrollPane);

				//Text Area
				textArea = new JTextArea(7, 40); 
				textArea.setEditable(true);
				textTitle = BorderFactory.createTitledBorder
				("Status Text");
				textArea.setLineWrap(true);
				textArea.setBorder(textTitle);
				JScrollPane textPane = new JScrollPane();
				textPane.setVerticalScrollBarPolicy(JScrollPane.
						VERTICAL_SCROLLBAR_ALWAYS);
				textPane.setViewportView(textArea);
				twitResults.add(textPane);
		 
		tabs.addTab("Followers", twitResults);
	}
	
	private void tableMouseClicked(){
		int index = table.getSelectedRow();
		if(index!= -1){
			String output = engine.getDisplayStatis(index);
			setStatus(output);
		}
	}
	/**
	 * Orders what needs to be displayed with in the Status
	 * text area.
	 * @param object 
	 * 
	 * @param output - A string of the users current status.
	 */
	private void setStatus(String output) {
		textArea.setText(output);
	}
	/**
	 * Gets the topTrendingOutput
	 * 
	 * @param topTrendingList - what is top trending
	 */
	public void topTrendingOutput(Object topTrendingList) {
		textArea.setText(topTrendingList.toString());
	}
	/**
	 * Gets the wordFrequencyCount
	 * 
	 * @param wordFrequencyList - what is the word frequency
	 */
	public void wordFrequencyCount(Object wordFrequencyList)
	{
		textArea.setText(wordFrequencyList.toString());
	}

	/**
	 * Contains the Panels in the Operation Section.
	 * The combobox allows selection bettween the different
	 * options.
	 * @throws TwitterException 
	 */
	private void PostTimeTabInit() throws TwitterException {
		
		ButtonListener listener = new ButtonListener();
	
		PostTimePanel = new JPanel();
		PostTimePanel.setLayout(new BorderLayout());
		
		UpdatePanel = new JPanel();
		UpdatePanel.setLayout(new FlowLayout());
		tweet = new JButton("Post Tweet");
		updateTimelineButton = new JButton("Update");
		tweet.addActionListener((listener));
		updateTimelineButton.addActionListener(listener);
		
		updateTextBox = new JTextField(35);
		
				
		UpdatePanel.add(updateTextBox);
		UpdatePanel.add(tweet);
		UpdatePanel.add(updateTimelineButton);
		
		timeLinePanel = new JPanel();
		timeLineArea = new JTextArea();
		
		timeLinePanel.add(timeLineArea);

		statuses = engine.getTimeline();
	    
		
	    
	   for (Status status : statuses) {
	       timeLineArea.append(status.getUser().getName() + ":" + status.getText());
	       timeLineArea.append("\n\n");
	    }
		
		PostTimePanel.add(UpdatePanel, BorderLayout.PAGE_START);
		PostTimePanel.add(timeLineArea, BorderLayout.PAGE_END);
		
		/*
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
		*/
		
		tabs.addTab("Post Tweet/Timeline", PostTimePanel);
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
			if(e.getSource().equals(signoutButton)){
				engine.logout();
			}
			if(e.getSource().equals(tweet)){
				try {
					String post = updateTextBox.getText();
					System.out.println(post);
					engine.postStatus(post);
					updateTextBox.setText("");			
					
				} catch (TwitterException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if(e.getSource().equals(updateTimelineButton)){
				try {
					
					
					
					statuses = engine.getTimeline();
					
					for (Status status : statuses) {
					       timeLineArea.append(status.getUser().getName() + ":" + status.getText());
					       timeLineArea.append("\n\n");
					    }
				} catch (TwitterException e1) {
					e1.printStackTrace();
				}
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