package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import twitter4j.Status;
import twitter4j.Trend;
import twitter4j.TwitterException;
import twitter4j.User;
import engine.TwitterEngine;

/**
 * Creates the GUI and runs the application.
 * 
 * @author Ben, Kevin, Trent, Seth
 */
public class TwitterGUI extends JFrame {
	
	/**
	 * default serial id.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Main gui frame.
	 */
	private JFrame guiFrame;
	/**
	 * Main tabs pane.
	 */
	private JTabbedPane tabs;
	/**
	 * GUI menubar.
	 */
	private JMenuBar menu;
	/**
	 * GUI menu headers.
	 */
	private JMenu file, generate, help, favoritesMenu;
	/**
	 * GUI menu items.
	 */
	private JMenuItem fileExport, fileDeleteTable, fileQuit, fileDeleteStatus,
			generateWordFrequencyList, generateTopTrendingList, helpAbout,
			addFavorites, removeFavorites;
	/**
	 * Login button.
	 */
	private JButton loginButton;
	/**
	 * Signout button.
	 */
	private JButton signoutButton;
	/**
	 * Button updates timeline.
	 */
	private JButton updateTimelineButton;
	/**
	 * Button updates Favorites.
	 */
	private JButton updateFavoritesButton;
	/**
	 * Button updates FriendsList.
	 */
	private JButton updateFriendsList;
	/**
	 * Button updates my tweets list.
	 */
	private JButton updateMyTweetsList;
	/**
	 * Button deletes favorite from favorites list.
	 */
	private JButton deleteFavoriteButton;
	/**
	 * Adds favorite from the timeline tab.
	 */
	private JButton timelineFavoriteButton;
	/**
	 * Add favorite from the my tweets tab.
	 */
	private JButton myTweetsFavoriteButton;
	/**
	 * Make a new tweet/status.
	 */
	private JButton tweetButton;
	/**
	 * Delete a tweet/status.
	 */
	private JButton deleteMyTweetButton;
	/**
	 * View profile of a user.
	 */
	private JButton viewProfile;
	/**
	 * Remove a friend from friends list.
	 */
	private JButton removeFriend;
	/**
	 * View messages.
	 */
	private JButton viewMessagesButton;
	/**
	 * Panel for displaying profile.
	 */
	private JPanel profilePanel;
	/**
	 * Panel for displaying followers.
	 */
	private JPanel twitResults;
	/**
	 * Panel contains items to update twitter status.
	 */
	private JPanel updatePanel;
	/**
	 * Panel contains items of main timeline.
	 */
	private JPanel postTimePanel;
	/**
	 * Panel for the favorites tab.
	 */
	private JPanel favoritesPanel;
	/**
	 * Panel for the mytweets tab.
	 */
	private JPanel myTweetsPanel;
	/**
	 * Panel for the friends list tab.
	 */
	private JPanel friendsListPanel;
	/**
	 * Panel for the trends tab.
	 */
	private JPanel trendsPanel;
	/**
	 * Array to store the trends for the trendlist tab.
	 */
	private Trend[] trendslist;
	/**
	 * Contains list of the statuses for user.
	 */
	private List<Status> statuses;
	/**
	 * Contains list of users.
	 */
	private List<User> users;
	/**
	 * JList for MyTweets tab.
	 */
	private JList<String> list;
	/**
	 * JList for friends tab.
	 */
	private JList<String> friendsList;
	/**
	 * JList for favorites tab.
	 */
	private JList<String> favoritesList;
	/**
	 * JList for timeline tab.
	 */
	private JList<String> timelineList;
	/**
	 * Text area for followers tab.
	 */
	private JTextArea textArea;
	/**
	 * Text area for trends tab.
	 */
	private JTextArea trendsArea;
	/**
	 * Text field for the update tweet panel.
	 */
	private JTextField updateTextBox;
	/**
	 * Contains list of current user status ids.
	 */
	private long[] myTweetsStatusIds;
	/**
	 * Contains list of timeline status ids.
	 */
	private long[] myTimeLineStatusIds;
	/**
	 * Contains a list of favorites status ids.
	 */
	private long[] myFavoriteStatusIds;
	/**
	 * Contains a list of userids.
	 */
	private long[] userIds;
	/**
	 * Contains a list of friends names.
	 */
	private String[] friendsNamesList;
	/**
	 * Model for the favorites JList.
	 */
	private DefaultListModel<String> favModel;
	/**
	 * TwitterEngine instance.
	 */
	private TwitterEngine engine;
	/**
	 * Timeline table.
	 */
	private JTable table;
	/**
	 * Titles of Borders.
	 */
	private TitledBorder tableTitle, textTitle;

	/**
	 * Packs and sets the GUI.
	 * 
	 * @throws Exception 
	 */
	public TwitterGUI() throws Exception {
		guiFrame = new JFrame();
		engine = new TwitterEngine();
		guiFrame.setSize(800, 450);

		engine.login();

		menuInit();
		postTimeTabInit();
		followerTabInit();
		favoritesTabInit();
		myTweetsTabInit();
		friendsListTabInit();
		profileTabInit();
		trendsTabInit();

		tabs = new JTabbedPane();

		tabs.addTab("Profile", profilePanel);
		tabs.addTab("Post Tweet/Timeline", postTimePanel);
		tabs.addTab("Followers", twitResults);
		tabs.addTab("Friends List", friendsListPanel);
		tabs.addTab("Favorites", favoritesPanel);
		tabs.addTab("Your Tweets", myTweetsPanel);
		tabs.addTab("Trends", trendsPanel);

		guiFrame.add(tabs);
		guiFrame.setTitle("Twitter for "
				+ engine.getRealName(engine.getUserId()));

		final URL url = new URL(
				"http://jonbennallick.co.uk/wp-content/uploads"
				+ "/2012/11/Twitter-Logo-Icon-by-Jon-Bennallick-02.png");
		guiFrame.setIconImage(ImageIO.read(url));

		guiFrame.setVisible(true);
	}
	/**
	 * Load the GUI tabs.
	 * @throws TwitterException	
	 * @throws MalformedURLException	
	 */
	private void loadTabs() throws TwitterException, MalformedURLException {
		menuInit();
		postTimeTabInit();
		followerTabInit();
		favoritesTabInit();
		myTweetsTabInit();
		friendsListTabInit();
		profileTabInit();
		trendsTabInit();
		tabs.addTab("Profile", profilePanel);
		tabs.addTab("Post Tweet/Timeline", postTimePanel);
		tabs.addTab("Followers", twitResults);
		tabs.addTab("Friends List", friendsListPanel);
		tabs.addTab("Favorites", favoritesPanel);
		tabs.addTab("Your Tweets", myTweetsPanel);
		tabs.addTab("Trends", trendsPanel);

		guiFrame.add(tabs);
	}
	/**
	 * Initializes the friends list tab.
	 * @throws TwitterException 
	 */
	private void friendsListTabInit() throws TwitterException {
		friendsListPanel = new JPanel();
		friendsListPanel.setLayout(new BorderLayout());

		JPanel buttonsPanel = new JPanel();

		viewProfile = new JButton("View Profile");
		removeFriend = new JButton("Remove Friend");
		updateFriendsList = new JButton("Update");
		ButtonListener listener = new ButtonListener();

		viewProfile.addActionListener(listener);
		removeFriend.addActionListener(listener);
		updateFriendsList.addActionListener(listener);

		users = engine.getFriendsList(engine.getUserId());
		friendsNamesList = new String[users.size()];
		userIds = new long[users.size()];
		int count = 0;

		for (User user : users) {
			friendsNamesList[count] = user.getName();
			userIds[count] = user.getId();
			count++;
		}

		friendsList = new JList<String>(friendsNamesList);

		buttonsPanel.add(updateFriendsList);
		buttonsPanel.add(viewProfile);
		buttonsPanel.add(removeFriend);

		JScrollPane scrollpane = new JScrollPane(friendsList);
		friendsListPanel.add(scrollpane, BorderLayout.CENTER);
		friendsListPanel.add(buttonsPanel, BorderLayout.PAGE_END);
	}

	/**
	 * Initializes trend tab.
	 */
	private void trendsTabInit() {

		trendsPanel = new JPanel();
		trendsPanel.setLayout(new BorderLayout());
		trendsArea = new JTextArea(20, 40);
		trendsArea.setLineWrap(true);
		trendsArea.setEditable(false);

		trendsPanel.add(trendsArea);
	}
	
	/**
	 * Updates trend area.
	 */
	private void updateTrendsArea() {
		trendsArea.setText("");
		for (Trend trend : trendslist) {
			trendsArea.append(trend.getName());
			trendsArea.append("\n");
		}
	}

	/**
	 * Initializes myTweet tab.
	 * @throws TwitterException 
	 */
	private void myTweetsTabInit() throws TwitterException {
		DefaultListModel<String> model = new DefaultListModel<String>();
		list = new JList<String>(model);
		list.setFixedCellHeight(50);

		myTweetsPanel = new JPanel();
		myTweetsPanel.setLayout(new BorderLayout());
		JPanel buttonsPanel = new JPanel();

		String html1 = "<html><body style='width: ";
		String html2 = "px'>";

		myTweetsFavoriteButton = new JButton("Favorite");
		deleteMyTweetButton = new JButton("Delete Tweet");
		updateMyTweetsList = new JButton("Update");
		ButtonListener listener = new ButtonListener();

		myTweetsFavoriteButton.addActionListener(listener);
		deleteMyTweetButton.addActionListener(listener);
		updateMyTweetsList.addActionListener(listener);

		statuses = engine.getMyTweets();
		myTweetsStatusIds = new long[statuses.size()];
		int count = 0;

		for (Status status : statuses) {
			model.addElement(html1 + "550" + html2 + status.getText());
			myTweetsStatusIds[count] = status.getId();
			count++;
		}

		JScrollPane scrollpane = new JScrollPane(list);

		buttonsPanel.add(updateMyTweetsList);
		buttonsPanel.add(myTweetsFavoriteButton);
		buttonsPanel.add(deleteMyTweetButton);

		myTweetsPanel.add(scrollpane, BorderLayout.CENTER);
		myTweetsPanel.add(buttonsPanel, BorderLayout.PAGE_END);

	}

	/**
	 * Initializes the profile tab.
	 * @throws TwitterException 
	 * @throws MalformedURLException 
	 */
	private void profileTabInit() throws
	TwitterException, MalformedURLException {

		Border blackline = BorderFactory.createLineBorder(Color.black);

		profilePanel = new JPanel();

		profilePanel.setLayout(new GridLayout(2, 1));

		JPanel top = new JPanel();
		top.setLayout(new GridLayout(1, 1));
		top.setBorder(blackline);
		top.setSize(200, 200);

		JPanel top2 = new JPanel();
		top2.setLayout(new GridLayout(2, 1));
		top.setBorder(blackline);

		JPanel bottom = new JPanel();
		bottom.setBorder(blackline);
		JPanel user = new JPanel();
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
		JLabel realname = new JLabel(engine.getRealName(engine.getUserId()));
		realname.setBorder(blackline);
		realname.setHorizontalAlignment(SwingConstants.CENTER);
		realname.setSize(20, 12);
		JLabel tweets = new JLabel("Tweets: " + engine.getTweets());
		JLabel followers = new JLabel("Followers: "
				+ engine.getFollowersCount());
		JLabel following = new JLabel("Following: "
				+ engine.getFollowingCount());
		JLabel rateLimit = new JLabel("Rate Limit: " + engine.getRateLimit());
		JLabel rateLimitRemaining = new JLabel("Rate Limit Remaining: "
				+ engine.getRateLimitRemaining());

		top.add(userIcon);
		top2.add(realname);
		top2.add(screenname);

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
		viewMessagesButton = new JButton("View Messages");
		viewMessagesButton.addActionListener(listener);

		login.add(viewMessagesButton);
		login.add(loginButton);
		login.add(signoutButton);

		profilePanel.add(user, BorderLayout.CENTER);
		profilePanel.add(login, BorderLayout.PAGE_END);
	}

	/**
	 * Contains the Panels in the Operation Section. The combobox allows
	 * selection between the different options.
	 * 
	 * @throws TwitterException 
	 */
	private void postTimeTabInit() throws TwitterException {
		ButtonListener listener = new ButtonListener();
		updatePanel = new JPanel();
		postTimePanel = new JPanel();

		postTimePanel.setLayout(new BorderLayout());

		DefaultListModel<String> model = new DefaultListModel<String>();

		String html1 = "<html><body style='width: ";
		String html2 = "px'>";

		updateTextBox = new JTextField(50);

		tweetButton = new JButton("Post Tweet");
		tweetButton.addActionListener((listener));
		updateTimelineButton = new JButton("Update");
		updateTimelineButton.addActionListener(listener);
		timelineFavoriteButton = new JButton("Favorite");
		timelineFavoriteButton.addActionListener(listener);

		updatePanel.add(updateTextBox);
		updatePanel.add(tweetButton);
		updatePanel.add(updateTimelineButton);

		myTimeLineStatusIds = new long[200];
		int count = 0;
		statuses = engine.getTimeline();

		timelineList = new JList<String>(model);
		timelineList.setFixedCellHeight(50);

		for (Status status : statuses) {
			model.addElement(html1 + "550" + html2
					+ engine.getRealName(status.getUser().getId()) + ": "
					+ status.getText() + "\n");
			myTimeLineStatusIds[count] = status.getId();
			count++;
		}

		JScrollPane timeLinePane = new JScrollPane(timelineList);
		postTimePanel.add(updatePanel, BorderLayout.PAGE_START);
		postTimePanel.add(timeLinePane, BorderLayout.CENTER);
		postTimePanel.add(timelineFavoriteButton, BorderLayout.PAGE_END);
	}

	/**
	 * Creates the Eastern Panel using the class TwitterResultsPanel().
	 */
	private void followerTabInit() {

		GridLayout resultLayout = new GridLayout(2, 2, 5, 10);
		twitResults = new JPanel();
		twitResults.setLayout(resultLayout);

		// Table Creation
		table = new JTable();
		table.setToolTipText("");
		table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);

		table.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(final java.awt.event.MouseEvent evt) {
				tableMouseClicked();
			}
		});
		table.setPreferredScrollableViewportSize(new Dimension(500, 140));
		JScrollPane scrollPane = new JScrollPane();
		tableTitle = BorderFactory.createTitledBorder("Statuses");

		table.setModel(engine.getModel());
		scrollPane.setVerticalScrollBarPolicy(
			JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		// attaches table to the scroll pane
		scrollPane.setViewportView(table);
		scrollPane.setBorder(tableTitle);
		twitResults.add(scrollPane);

		// Text Area
		textArea = new JTextArea(7, 40);
		textArea.setEditable(false);
		textTitle = BorderFactory.createTitledBorder("Status Text");
		textArea.setLineWrap(true);
		textArea.setBorder(textTitle);
		JScrollPane textPane = new JScrollPane();
		textPane.setVerticalScrollBarPolicy(
			JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		textPane.setViewportView(textArea);
		twitResults.add(textPane);
	}
	/**
	 * Sets status of text area to status of table selection.
	 */
	private void tableMouseClicked() {
		int index = table.getSelectedRow();
		if (index != -1) {
			String output = engine.getDisplayStatis(index);
			setStatus(output);
		}
	}

	/**
	 * Orders what needs to be displayed with in the Status text area.
	 * 
	 * @param object
	 * 
	 * @param output
	 *            - A string of the users current status.
	 */
	private void setStatus(final String output) {
		textArea.setText(output);
	}

	/**
	 * Initializes the favorites tab.
	 * @throws TwitterException 
	 */
	public final void favoritesTabInit() throws TwitterException {
		favoritesPanel = new JPanel();
		favoritesPanel.setLayout(new BorderLayout());

		JPanel buttonsPanel = new JPanel();

		ButtonListener listener = new ButtonListener();

		updateFavoritesButton = new JButton("Update");
		updateFavoritesButton.addActionListener(listener);
		deleteFavoriteButton = new JButton("Delete");
		deleteFavoriteButton.addActionListener(listener);

		statuses = engine.getFavoriteTweets();
		myFavoriteStatusIds = new long[200];

		favModel = new DefaultListModel<String>();
		favoritesList = new JList<String>(favModel);
		favoritesList.setFixedCellHeight(50);

		generateFavoritesList();
		JScrollPane favoritePane = new JScrollPane(favoritesList);

		buttonsPanel.add(updateFavoritesButton);
		buttonsPanel.add(deleteFavoriteButton);

		favoritesPanel.add(favoritePane, BorderLayout.CENTER);
		favoritesPanel.add(buttonsPanel, BorderLayout.PAGE_END);
	}

	/**
	 * Helper method to generate favorites jlist.
	 * @throws TwitterException 
	 */
	private void generateFavoritesList() throws TwitterException {
		String html1 = "<html><body style='width: ";
		String html2 = "px'>";
		statuses = engine.getFavoriteTweets();
		int count = 0;
		for (Status status : statuses) {
			favModel.addElement(html1 + "400" + html2
					+ status.getUser().getName() + ":" + status.getText());
			myFavoriteStatusIds[count] = status.getId();
			count++;
		}
		favoritesList.updateUI();
	}

	/**
	 * menuInit() geneartes the JMenuBar to be used.
	 */
	private void menuInit() {
		menu = new JMenuBar();
		// Creates the File Menu
		file = new JMenu("File");
		fileDeleteStatus = new JMenuItem("Delete Status");
		fileDeleteTable = new JMenuItem("Delete Table Status");
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
		// Creates the Generate Menu
		generate = new JMenu("Generate");
		generateWordFrequencyList = new JMenuItem("Word Frequency List");
		generateTopTrendingList = new JMenuItem("Top Trending List");
		generateTopTrendingList.addActionListener(menuHandeler);
		generateWordFrequencyList.addActionListener(menuHandeler);
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
		// Create the Help Menu
		help = new JMenu("Help");
		helpAbout = new JMenuItem("About...");
		helpAbout.addActionListener(menuHandeler);
		help.add(helpAbout);
		menu.add(help);
		// Add Menu to the GUI
		guiFrame.setJMenuBar(menu);
	}
	
	/**
	 * ButtonListener directs button commands.
	 *
	 */
	class ButtonListener implements ActionListener {
		/**
		 * Waits for actions to be performed.
		 * @param e the action performed
		 */
		public void actionPerformed(final ActionEvent e) {
			if (e.getSource().equals(loginButton)) {
				try {
					tabs.removeAll();
					engine.logout();

					engine.login();
					loadTabs();

				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IllegalStateException e1) {
					e1.printStackTrace();
				} catch (TwitterException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			if (e.getSource().equals(signoutButton)) {
				engine.logout();
				System.exit(0);
			}

			if (e.getSource().equals(tweetButton)) {
				try {
					String post = updateTextBox.getText();
					engine.postStatus(post);
					updateTextBox.setText("");
				} catch (TwitterException e1) {
					e1.printStackTrace();
				}
			}
			if (e.getSource().equals(updateTimelineButton)) {
				try {
					tabs.remove(1);
					postTimeTabInit();

					tabs.insertTab("Post Tweet/Timeline", null, postTimePanel,
							null, 1);
					tabs.setSelectedIndex(1);
				} catch (IllegalStateException  e1) {
					e1.printStackTrace();
				} catch (TwitterException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if (e.getSource().equals(myTweetsFavoriteButton)) {
				try {

					engine.favoriteTweet(myTweetsStatusIds[list
					  .getSelectedIndex()]);
					tabs.remove(4);
					favoritesTabInit();

					tabs.insertTab("Favorites", null, favoritesPanel, null, 4);

					tabs.setSelectedIndex(4);

				} catch (IllegalStateException e1) {
					e1.printStackTrace();
				} catch (TwitterException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			if (e.getSource().equals(myTweetsFavoriteButton)) {
				try {
					engine.favoriteTweet(myTweetsStatusIds[list
					.getSelectedIndex()]);
				} catch (NumberFormatException  e1) {
					e1.printStackTrace();
				} catch (TwitterException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
			if (e.getSource().equals(deleteMyTweetButton)) {

				try {
					engine.deleteStatus(myTweetsStatusIds[list
							.getSelectedIndex()]);
				} catch (TwitterException e1) {
					e1.printStackTrace();
				}

			}
			if (e.getSource().equals(viewProfile)) {
				try {
					new FriendVeiwer(userIds[friendsList.getSelectedIndex()],
							engine);
				} catch (TwitterException e1) {
					e1.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			if (e.getSource().equals(removeFriend)) {
				try {
					engine.removefriend(
						userIds[friendsList.getSelectedIndex()]);
				} catch (TwitterException e1) {
					e1.printStackTrace();
				}
			}
			if (e.getSource().equals(viewMessagesButton)) {
				try {
					new DirectMessageViewer(engine);
				} catch (TwitterException e1) {
					e1.printStackTrace();
				}
			}
			if (e.getSource().equals(timelineFavoriteButton)) {
				try {
					engine.favoriteTweet(myTimeLineStatusIds[timelineList
							.getSelectedIndex()]);
				} catch (NumberFormatException e1) {
					e1.printStackTrace();
				} catch (TwitterException e1) {
					if (e1.getErrorCode() == 139) {
						JOptionPane.showMessageDialog(guiFrame,
								"You have already favorited that Tweet",
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			if (e.getSource().equals(deleteFavoriteButton)) {
				try {
					engine.deleteStatus(myFavoriteStatusIds[favoritesList
							.getSelectedIndex()]);
					e.setSource(updateFavoritesButton);
				} catch (TwitterException e1) {
					e1.printStackTrace();
				}
			}
			if (e.getSource().equals(updateFavoritesButton)) {

				try {
					tabs.remove(4);
					favoritesTabInit();
					tabs.insertTab("Favorites", null, favoritesPanel, null, 4);
					tabs.setSelectedIndex(4);
				} catch (IllegalStateException | TwitterException e1) {
					e1.printStackTrace();
				}
			}

			if (e.getSource().equals(updateFriendsList)) {
				tabs.remove(3);
				try {
					friendsListTabInit();
				} catch (TwitterException e1) {

					e1.printStackTrace();
				}
				tabs.insertTab("Friends", null, friendsListPanel, null, 3);
				tabs.setSelectedIndex(3);
			}

			if (e.getSource().equals(updateMyTweetsList)) {
				tabs.remove(5);
				try {
					favoritesTabInit();
				} catch (TwitterException e1) {
					e1.printStackTrace();
				}
				tabs.insertTab("My Tweets", null, myTweetsPanel, null, 5);
				tabs.setSelectedIndex(5);
			}
		}
	}

	/**
	 * Handles all of the actions in the JMenuBar.
	 */
	private ActionListener menuHandeler = new ActionListener() {
		@Override
		public void actionPerformed(final ActionEvent e) {
			if (e.getActionCommand().equals("Delete Status")) {
				int i = 0;

				try {
					engine.deleteStatus(i);
				} catch (TwitterException e1) {
					e1.printStackTrace();
				}
			}
			if (e.getActionCommand().equals("Delete Table Status")) {
				engine.deleteTweet();
			}
			if (e.getActionCommand().equals("Quit")) {
				System.exit(0);
			}
			if (e.getActionCommand().equals("Add to Favorites")) {
				engine.addFavorite();
			}
			if (e.getActionCommand().equals("Remove Favorite")) {
				engine.removeFavorite();
			}
			if (e.getActionCommand().equals("About...")) {
				JOptionPane.showMessageDialog(null,
						"Produced by\n Benjamin Summers \n"
								+ "			 Kevin Anderson     \n"
								+ "			 Seth Hilaski       \n"
								+ "			 Trent Newberry     \n"
								+ "          4/14/2013          \n"
								+ "          For a CIS350 Project");
			}
			if (e.getActionCommand().equals("Top Trending List")) {
				String[] options = engine.getTrendsLocations();
				String choice;
				choice = (String) JOptionPane
						.showInputDialog(guiFrame, "Pick a Trend Location",
								"Trend List", JOptionPane.OK_CANCEL_OPTION,
								null, options, options[0]);
				if (choice != null) {
					trendslist = engine.getPlaceTrends(choice).getTrends();
					updateTrendsArea();
				}
			}

		}
	};

	/**
	 * runs the main application starting with TwitterGUI which instantiates all
	 * other components that are part of the GUI.
	 * 
	 * @param args aruguments passed to the program.
	 */
	public static void main(final String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {

				try {
					new TwitterGUI();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
