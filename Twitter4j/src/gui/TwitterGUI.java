package gui;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
//import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;
import engine.TwitterEngine;
import twitter4j.AccountSettings;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
//import java.net.URL;
import java.util.List;


@SuppressWarnings("serial")
/**
 * Creates the GUI and runs the application
 * 
 * @author Ben, Kevin, Trent, Seth
 */
public class TwitterGUI extends JFrame {

	private JFrame gUI;
	
	private JTabbedPane tabs;
	
	private JMenuBar menu;
	private JMenu file, generate, sort, help, favoritesMenu; 
	private JMenuItem fileExport, fileDeleteTable, fileQuit,
			fileDeleteStatus, generateWordFrequencyList, 
			generateTopTrendingList, helpAbout, addFavorites, removeFavorites;
	
	private JButton loginButton;
	private JButton signoutButton;
	private JButton updateTimelineButton;
	private JButton updateFavoritesButton;
	private JButton tweet;
	private JButton favoriteButton;
	private JButton deleteButton;
	
	private JPanel profile;
	private JPanel twitResults;
	private JPanel updateFav;
	private JPanel FavoritesPanel;
	private JPanel updatePanel;	
	private JPanel postTimePanel;
	private JPanel timeLinePanel;
	private JPanel favorites;
	private JPanel ProfileSettingsPanel;
	private JPanel AccountSettingsPanel;
	private JPanel MyTweetsPanel;	
	private JPanel FreindsListPanel;

	private List<Status> statuses;
	private List<User> FriendsLists;
	
	private JList<String> list;
	private JList<String> FriendsList;
	
	private JTextArea timeLineArea;
	private JTextArea FavoritesArea;
	private JTextArea textArea;
	
	private JTextField updateTextBox;
	
	private long[] statusIds;
	private long[] userIds;
	private String[] myTweets;
	private String[] FriendsNamesList;
	
	private TwitterEngine engine;
	
	private JTable table;
	
	private TitledBorder tableTitle, textTitle;
	
	/**
	 * Packs and sets the GUI
	 * @throws Exception 
	 */
	public TwitterGUI() throws Exception {
		engine = new TwitterEngine();
		gUI = new JFrame("Twitter Lite");
		engine.login();
		gUI.setSize(800, 450);
		tabs = new JTabbedPane();
		menuInit();
		profileTabInit();
		postTimeTabInit();
		followerTabInit();
		favoritesTabInit();
		ProfileSettingsTabInit();
		AccountSettingsTabInit();
		MyTweetsTabInit();
		FriendsListTabInit();
		
		tabs.addTab("Profile", profile);
		tabs.addTab("Post Tweet/Timeline", postTimePanel);
		tabs.addTab("Followers", twitResults);
		tabs.addTab("Friends List", FreindsListPanel);
		tabs.addTab("Favorites", favorites);
		tabs.addTab("Your Tweets", MyTweetsPanel);
		tabs.addTab("Account/Profile Settings", AccountSettingsPanel);
		

		gUI.add(tabs);
		
		gUI.setVisible(true);
	}
	
	private void FriendsListTabInit() throws TwitterException {
		FreindsListPanel = new JPanel();
		
		FriendsNamesList = new String[200];
		userIds = new long[200];
		int count = 0;
		FriendsLists = engine.getFriendsList();
		for(User user : FriendsLists){
			FriendsNamesList[count] = user.getName();
			userIds[count] = user.getId();
			count++;
		}
		
		FriendsList = new JList<String>(FriendsNamesList);
		
		JScrollPane scrollpane = new JScrollPane(FriendsList);
		FreindsListPanel.add(scrollpane);
	}

	private void MyTweetsTabInit() throws TwitterException {
		
		MyTweetsPanel = new JPanel();
		favoriteButton = new JButton("Favorite");
		deleteButton = new JButton("Delete");
		ButtonListener listener = new ButtonListener();
		
		favoriteButton.addActionListener(listener);
		deleteButton.addActionListener(listener);
		
		myTweets = new String[200];
		statusIds = new long[200];
		int count = 0;
		statuses = engine.getMyTweets();
		for(Status status : statuses){
			myTweets[count] = status.getText();
			statusIds[count] = status.getId();
			count++;
		}
		
		list = new JList<String>(myTweets);
		
		JScrollPane scrollpane = new JScrollPane(list);
		MyTweetsPanel.add(scrollpane);
		MyTweetsPanel.add(favoriteButton);
		MyTweetsPanel.add(deleteButton);
		
	}

	private void AccountSettingsTabInit() throws TwitterException {
	
		AccountSettingsPanel = new JPanel();
		AccountSettingsPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		
		String[] languages = { "English", "French", "Russian", "Spanish", "Polish", "Portugese", "German", "Sweedish",
								"Thai", "Turkish", "Greek", "Simplified Chinese", "Traditional Chinese", "Korean"};
		
		String[] Timezones = {};
		String newTimezone = "";
		String newLanguage = "";
		
		String CurrentTimezone = "";
		String CurrentLanguage = "";
		
		AccountSettings AccountSettings = engine.getAccountSettings();
		
		CurrentTimezone = "Timezone: " + AccountSettings.getTimeZone().getName();
		CurrentLanguage = "Language: " + AccountSettings.getLanguage();
		
		JLabel CurrentLanguageLabel = new JLabel(CurrentLanguage.toUpperCase());
		JLabel CurrentTimezoneLabel = new JLabel(CurrentTimezone);
		
		JComboBox<String> LanguagePicker = new JComboBox<String>(languages);
		JComboBox<String> TimeZonePicker = new JComboBox<String>(Timezones);	
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		
		c.gridx = 0;
		c.gridy = 0;
		AccountSettingsPanel.add(CurrentLanguageLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		//c.ipady = 40;      //make this component tall
		c.weightx = .5;
		//c.gridwidth = 3;
		c.gridx = 3;
		c.gridy = 0;
		AccountSettingsPanel.add(CurrentTimezoneLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 0;       //reset to default
		//c.weighty = 1.0;   //request any extra vertical space
		//c.anchor = GridBagConstraints.PAGE_END; //bottom of space
		//c.insets = new Insets(10,0,0,0);  //top padding
		c.gridx = 0;       //aligned with button 2
		//c.gridwidth = 1;   //2 columns wide
		c.gridy = 1;       //third row
		AccountSettingsPanel.add(LanguagePicker, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 0;       //reset to default
		c.weightx = .5;
		//c.weighty = 1.0;   //request any extra vertical space
		//c.anchor = GridBagConstraints.PAGE_END; //bottom of space
		//c.insets = new Insets(10,0,0,0);  //top padding
		c.gridx =  3;       //aligned with button 2
		//c.gridwidth = 1;   //2 columns wide
		c.gridy = 1;       //third row
		AccountSettingsPanel.add(TimeZonePicker, c);
		
	}

	private void ProfileSettingsTabInit() {
		// TODO Auto-generated method stub
		
	}

	public final void profileTabInit() 
	throws IllegalStateException, TwitterException, MalformedURLException {
		Border blackline = BorderFactory.createLineBorder(Color.black);
		
		profile = new JPanel();		

		profile.setLayout(new GridLayout(2, 1));
		
		JPanel top = new JPanel();
		top.setLayout(new GridLayout(1, 1));
		top.setBorder(blackline);
		top.setSize(200, 200);
		
		JPanel top2 = new JPanel();
		top2.setLayout(new GridLayout(2, 1));
		top.setBorder(blackline);
		
		JPanel bottom = new JPanel();
		bottom.setBorder(blackline);
		JPanel  user = new JPanel();
		user.setLayout(new BorderLayout());
		user.setBorder(blackline);
		JPanel login = new JPanel();
		login.setBorder(blackline);
		
		ButtonListener listener = new ButtonListener();

		ImageIcon img = engine.getProfileImage();
		
		JLabel userIcon = new JLabel(img);
		userIcon.setBorder(blackline);
		JLabel screenname = new JLabel(engine.getScreenName());
		screenname.setHorizontalAlignment(SwingConstants.CENTER);
		screenname.setBorder(blackline);
		screenname.setSize(20, 12);
		JLabel realname = new JLabel(engine.getRealName());
		realname.setBorder(blackline);
		realname.setHorizontalAlignment(SwingConstants.CENTER);
		realname.setSize(20, 12);
		JLabel tweets = new JLabel("Tweets: " + engine.getTweets());
		JLabel followers = new JLabel("Followers: " 
		+ engine.getFollowersCount());
		JLabel following = new JLabel("Following: " 
		+ engine.getFollowingCount());
		JLabel rateLimit = new JLabel("Rate Limit: "
		+ engine.getRateLimit());
		JLabel rateLimitRemaining = new JLabel("Rate Limit Remaining: " 
		+ engine.getRateLimitRemaining());
		
		
		top.add(userIcon);
		top2.add(realname);
		top2.add(screenname);
		
		//bottom.add(space);
		bottom.add(tweets);
		bottom.add(followers);
		bottom.add(following);
		bottom.add(rateLimit);
		bottom.add(rateLimitRemaining);	
		
		user.add(top, BorderLayout.PAGE_START);
		user.add(top2, BorderLayout.CENTER);
		user.add(bottom, BorderLayout.PAGE_END);
		
		
		loginButton = new JButton("Log In");
		loginButton.addActionListener(listener);
		signoutButton = new JButton("Sign Out");
		signoutButton.addActionListener(listener);
		
		
		login.add(loginButton);
		login.add(signoutButton);
		
		profile.add(user, BorderLayout.CENTER);
		profile.add(login, BorderLayout.PAGE_END);
	
	}
	
	
	/**
	 * Contains the Panels in the Operation Section.
	 * The combobox allows selection bettween the different
	 * options.
	 * @throws TwitterException 
	 */
	private void postTimeTabInit() throws TwitterException {
		
		ButtonListener listener = new ButtonListener();
	
		postTimePanel = new JPanel();
		postTimePanel.setLayout(new BorderLayout());
		
		updatePanel = new JPanel();
		updatePanel.setLayout(new FlowLayout());
		
		tweet = new JButton("Post Tweet");
		tweet.addActionListener((listener));
		updateTimelineButton = new JButton("Update");
		updateTimelineButton.addActionListener(listener);
		
		JScrollPane timeLinepane = new JScrollPane();
		
		updateTextBox = new JTextField(30);
		
				
		updatePanel.add(updateTextBox);
		updatePanel.add(tweet);
		updatePanel.add(updateTimelineButton);
		
		timeLinePanel = new JPanel();
		timeLineArea = new JTextArea(20, 40);
		timeLineArea.setLineWrap(true);
		timeLineArea.setEditable(false);
		
		timeLinepane.setVerticalScrollBarPolicy(JScrollPane.
				VERTICAL_SCROLLBAR_ALWAYS);
		timeLinepane.setViewportView(timeLineArea);
		
		timeLinePanel.add(timeLinepane);

		statuses = engine.getTimeline();
	    
		for (Status status : statuses) {
			timeLineArea.append(status.getUser().getName() 
			+ ":" + status.getText());
			timeLineArea.append("\n\n");
	    }
	   
	   	timeLineArea.setCaretPosition(0);
		
		postTimePanel.add(updatePanel, BorderLayout.PAGE_START);
		postTimePanel.add(timeLinepane, BorderLayout.PAGE_END);
	}
	/**
	 * Creates the Eastern Panel using the
	 * class TwitterResultsPanel();
	 */
	private void followerTabInit() {
		
		GridLayout resultLayout = new GridLayout(2, 2, 5, 10);
			twitResults = new JPanel();
			twitResults.setLayout(resultLayout);

			//Table Creation
			table = new JTable();
			table.setToolTipText("");
			table.setSelectionMode(javax.swing.
				ListSelectionModel.SINGLE_SELECTION);
			table.getTableHeader().setReorderingAllowed(false);

			table.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(final java.awt.event.MouseEvent evt) {
				tableMouseClicked();
				}
			});
			table.setPreferredScrollableViewportSize(new Dimension(500, 140));
			//table.setFillsViewportHeight(true);
			JScrollPane scrollPane = new JScrollPane();
			tableTitle = BorderFactory.createTitledBorder("Statuses");
				
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
			textTitle = BorderFactory.createTitledBorder("Status Text");
			textArea.setLineWrap(true);
			textArea.setBorder(textTitle);
			JScrollPane textPane = new JScrollPane();
			textPane.setVerticalScrollBarPolicy(JScrollPane.
				VERTICAL_SCROLLBAR_ALWAYS);
			textPane.setViewportView(textArea);
			twitResults.add(textPane);
	}
	
	private void tableMouseClicked() {
		int index = table.getSelectedRow();
		if (index != -1) {
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
	private void setStatus(final String output) {
		textArea.setText(output);
	}
	/**
	 * Gets the topTrendingOutput
	 * 
	 * @param topTrendingList - what is top trending
	 */
	public final void topTrendingOutput(final Object topTrendingList) {
		textArea.setText(topTrendingList.toString());
	}
	/**
	 * Gets the wordFrequencyCount
	 * 
	 * @param wordFrequencyList - what is the word frequency
	 */
	public final void wordFrequencyCount(final Object wordFrequencyList) {
		textArea.setText(wordFrequencyList.toString());
	}
	
	public void favoritesTabInit() throws TwitterException {
		favorites = new JPanel();
		
		favorites.setLayout(new BorderLayout());
		
		ButtonListener listener = new ButtonListener();

		updateFav = new JPanel();
		updateFav.setLayout(new FlowLayout());
		
		updateFavoritesButton = new JButton("Update");
		updateFavoritesButton.addActionListener(listener);
		
		updateFav.add(updateFavoritesButton);
		
		JScrollPane favoritepane = new JScrollPane();
		
		FavoritesPanel = new JPanel();
		FavoritesArea = new JTextArea(20, 40);
		FavoritesArea.setLineWrap(true);
		FavoritesArea.setEditable(false);
		
		favoritepane.setVerticalScrollBarPolicy(JScrollPane.
				VERTICAL_SCROLLBAR_ALWAYS);
		favoritepane.setViewportView(FavoritesArea);
		
		timeLinePanel.add(favoritepane);

		statuses = engine.getFavoriteTweets();
	    
		
	    
	   for (Status status : statuses) {
	       FavoritesArea.append(status.getUser().getName() 
	       + ":" + status.getText());
	       FavoritesArea.append("\n\n");
	    }
	   
		FavoritesArea.setCaretPosition(0);
		
		favorites.add(updateFav, BorderLayout.PAGE_START);
		favorites.add(favoritepane, BorderLayout.PAGE_END);
		
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
		generateWordFrequencyList = new JMenuItem("Word Frequency List");
		generateTopTrendingList = new JMenuItem("Top Trending List");
		generateWordFrequencyList.addActionListener(menuHandeler);
		generateTopTrendingList.addActionListener(menuHandeler);
		generate.add(generateWordFrequencyList);
		generate.add(generateTopTrendingList);
		menu.add(generate);
		
		favoritesMenu = new JMenu("Favorites");
		
		addFavorites = new JMenuItem("Add to Favorites");
		addFavorites.addActionListener(menuHandeler);
		removeFavorites = new JMenuItem("Remove Favorite");
		removeFavorites.addActionListener(menuHandeler);
		favoritesMenu.add(addFavorites);
		favoritesMenu.add(removeFavorites);
		menu.add(favoritesMenu);
		//Create the Help Menu
		help = new JMenu("Help");
		helpAbout = new JMenuItem("About...");
		helpAbout.addActionListener(menuHandeler);
		help.add(helpAbout);
		menu.add(help);
		//Add Menu to the GUI
		gUI.setJMenuBar(menu);
	}
    
	class ButtonListener implements ActionListener {
		public void actionPerformed(final ActionEvent e) {
			if (e.getSource().equals(loginButton)) {
				try {
					engine.logout();
							
					tabs.removeAll();
					
					engine.login();
					
							engine.login();
							profileTabInit();
							postTimeTabInit();
							followerTabInit();
							favoritesTabInit();
							ProfileSettingsTabInit();
							AccountSettingsTabInit();
							MyTweetsTabInit();
							
							tabs.addTab("Profile", profile);
							tabs.addTab("Post Tweet/Timeline", postTimePanel);
							tabs.addTab("Followers", twitResults);
							tabs.addTab("Favorites", favorites);
							tabs.addTab("Your Tweets", MyTweetsPanel);
							tabs.addTab("AccountSettings", AccountSettingsPanel);
							
							
							gUI.add(tabs);
					
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalStateException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (TwitterException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if (e.getSource().equals(signoutButton)) {
				engine.logout();
				tabs.removeAll();
				//GUI.pack();
				try {
					engine.login();
					profileTabInit();
					postTimeTabInit();
					followerTabInit();
					favoritesTabInit();
					ProfileSettingsTabInit();
					AccountSettingsTabInit();
					MyTweetsTabInit();
					
					tabs.addTab("Profile", profile);
					tabs.addTab("Post Tweet/Timeline", postTimePanel);
					tabs.addTab("Followers", twitResults);
					tabs.addTab("Favorites", favorites);
					tabs.addTab("Your Tweets", MyTweetsPanel);
					tabs.addTab("AccountSettings", AccountSettingsPanel);
					
					gUI.add(tabs);
					//GUI.repaint();
					
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalStateException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (TwitterException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			if (e.getSource().equals(tweet)) {
				try {
					String post = updateTextBox.getText();
					engine.postStatus(post);
					updateTextBox.setText("");			
					
				} catch (TwitterException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if (e.getSource().equals(updateTimelineButton)) {
				try {			
					timeLineArea.setText("");
					
					statuses = engine.getTimeline();
					
					for (Status status : statuses) {
					       timeLineArea.append(status.getUser().getName() 
					    		   + ":" + status.getText());
					       timeLineArea.append("\n\n");
					    }
					
					
					timeLineArea.setCaretPosition(0);
				
					tabs.remove(0);
					tabs.remove(1);
					
					profileTabInit();
					followerTabInit();
					tabs.insertTab("Profile", null, profile, null, 0);
					tabs.insertTab("Followers", null, twitResults, null, 2);
					
					//tabs.add(profile, 0);
				} catch (TwitterException e1) {
					e1.printStackTrace();
				} catch (IllegalStateException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if(e.getSource().equals(updateFavoritesButton)){
					try {
						statuses = engine.getFavoriteTweets();
					} catch (TwitterException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				    
				   for (Status status : statuses) {
					     FavoritesArea.append(status.getUser().getName() 
					     + ":" + status.getText());
					     FavoritesArea.append("\n\n");
				    }
				}
			}
			
			if(e.getSource().equals(favoriteButton)){
				
				try {
					engine.favoriteTweet(statusIds[list.getSelectedIndex()]);
				} catch (NumberFormatException | TwitterException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			
			if(e.getSource().equals(deleteButton)){
				
				try {
					engine.deleteStatus(statusIds[list.getSelectedIndex()]);
				} catch (TwitterException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				
				
			}
			
		}
	}
     
	
	/**
	 * Handels all of the actions in the JMenuBar
	 */
	private ActionListener menuHandeler = new 
			ActionListener() {
		@Override
		public void actionPerformed(final ActionEvent e) {
			//System.out.println(e);
			if(e.getActionCommand().equals("Delete Status")){
				int i = 0;
				//engine.getT;
				try {
					engine.deleteStatus(i);
				} catch (TwitterException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if (e.getActionCommand().equals("Delete Table Status")) {
				engine.deleteTweet();
			}
			if (e.getActionCommand().equals("Export to XML ...")) {
				saveButtonAction();
			}
			if (e.getActionCommand().equals("Quit")) {
				System.exit(0);
			}
			if (e.getActionCommand().equals("Add to Favorites")){
				engine.addFavorite();
			}
			if (e.getActionCommand().equals("Remove Favorite")){
				engine.removeFavorite();
			}
			if (e.getActionCommand().equals("About...")) {
				JOptionPane.showMessageDialog(null, 
						"Produced by\n Benjamin Summers \n" 
						+ "			 Kevin Anderson     \n"
						+ "			 Seth Hilaski       \n"
						+ "			 Trent Newberry     \n"
						+ "            3/16//2013          \n"
						+ "            For a CIS350 Project");
			}

		}	
	};

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
	public static void main(final String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                try {
					new TwitterGUI();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });  
    }
	
}