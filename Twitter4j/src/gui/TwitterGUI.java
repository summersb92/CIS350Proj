package gui;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
//import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import twitter4j.Status;
//import twitter4j.Twitter;
import twitter4j.TwitterException;
//import twitter4j.TwitterFactory;
//import twitter4j.User;
import engine.TwitterEngine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
//import java.net.URL;
import java.util.List;


@SuppressWarnings("serial")
/**
 * Creates the GUI and runs the application
 * 
 * @author Ben
 */
public class TwitterGUI extends JFrame {

//	private String[] options= {"Authenticate User",
//			"Get Status", "Get UserTimeline","Post Status",
//			"Search for Statuses"};
//	private JComboBox combo;
//	private JPanel eastPanel, westPanel;
	private JFrame gUI;
	private JMenuBar menu;
	private JTabbedPane tabs;
	private JMenu file, generate, sort, help, favoritesMenu; 
	private JMenuItem fileExport, fileDeleteTable, fileQuit,
			fileDeleteStatus, generateWordFrequencyList, 
			generateTopTrendingList, helpAbout, addFavorites, removeFavorites;
  //private TwitterResultsPanel results;
	private JPanel profile;
//	private GetStatusPanel getStatusP;
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
	private JPanel updatePanel;
	private JButton tweet;
	private JPanel postTimePanel;
	private JPanel timeLinePanel;
	private JPanel favorites;
	private JTextArea timeLineArea;
	//private Twitter twitter;
	private List<Status> statuses;
	
	/**
	 * Packs and sets the GUI
	 * @throws Exception 
	 */
	public TwitterGUI() throws Exception {
		engine = new TwitterEngine();
		gUI = new JFrame("Twitter Lite");
		engine.login();
		gUI.setSize(300, 300);
		tabs = new JTabbedPane();
		menuInit();
		profileTabInit();
		postTimeTabInit();
		followerTabInit();
		favoritesTabInit();
		
		tabs.addTab("Profile", profile);
		tabs.addTab("Post Tweet/Timeline", postTimePanel);
		tabs.addTab("Followers", twitResults);
		tabs.addTab("Favorites", favorites);
		
		gUI.add(tabs);
		
		gUI.setVisible(true);
	}
	
	public final void profileTabInit() 
	throws IllegalStateException, TwitterException, MalformedURLException {
		Border blackline = BorderFactory.createLineBorder(Color.black);
		
		profile = new JPanel();		

		profile.setLayout(new GridLayout(2, 1));
		
		JPanel top = new JPanel();
		top.setLayout(new GridLayout(1, 1));
		top.setBorder(blackline);
		gUI.setSize(600, 450);
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
		
		//scrollpane.add(timeLineArea);
		
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
					engine.login();
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
				for (int i = 0; i <= tabs.getTabCount() + 1; i++) {
				tabs.removeTabAt(0);
				}
				//GUI.pack();
				try {
					engine.login();
					menuInit();
					profileTabInit();
					postTimeTabInit();
					followerTabInit();
					
					tabs.addTab("Profile", profile);
					tabs.addTab("Post Tweet/Timeline", postTimePanel);
					tabs.addTab("Followers", twitResults);
					
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
			//if(e.getActionCommand().equals
				//	("Delete Status")){
				//engine.deleteStatus();
			//}
			if (e.getActionCommand().equals("Delete Table Status")) {
				engine.deleteTweet();
			}
			if (e.getActionCommand().equals("Export to XML ...")) {
				saveButtonAction();
			}
			if (e.getActionCommand().equals("Quit")) {
				System.exit(0);
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