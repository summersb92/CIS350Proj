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

@SuppressWarnings("serial")
/**
 * Creates the GUI and runs the application
 * 
 * @author Ben, Kevin, Trent, Seth
 */
public class TwitterGUI extends JFrame {

	private JFrame guiFrame;

	private JTabbedPane tabs;

	private JMenuBar menu;
	private JMenu file, generate, help, favoritesMenu;
	private JMenuItem fileExport, fileDeleteTable, fileQuit, fileDeleteStatus,
			generateWordFrequencyList, generateTopTrendingList, helpAbout,
			addFavorites, removeFavorites;

	private JButton LoginButton;
	private JButton SignoutButton;
	private JButton updateTimelineButton;
	private JButton UpdateFavoritesButton;
	private JButton UpdateFriendsList;
	private JButton UpdateMyTweetsList;
	private JButton DeleteFavoriteButton;
	private JButton TimelineFavoriteButton;
	private JButton MyTweetsFavoriteButton;
	private JButton TweetButton;
	private JButton DeleteMyTweetButton;
	private JButton VeiwProfile;
	private JButton RemoveFriend;
	private JButton ViewMessagesButton;

	private JPanel ProfilePanel;
	private JPanel TwitResults;
	private JPanel UpdatePanel;
	private JPanel PostTimePanel;
	private JPanel FavoritesPanel;
	// private JPanel ProfileSettingsPanel;
	private JPanel MyTweetsPanel;
	private JPanel FriendsListPanel;
	private JPanel trendsPanel;

	private Trend[] trendslist;

	private List<Status> statuses;
	private List<User> users;

	private JList<String> list;
	private JList<String> FriendsList;
	private JList<String> FavoritesList;
	private JList<String> TimelineList;

	private JTextArea textArea;
	private JTextArea trendsArea;

	private JTextField updateTextBox;

	private long[] MyTweetsStatusIds;
	private long[] MyTimeLineStatusIds;
	private long[] MyFavoriteStatusIds;
	private long[] userIds;
	private String[] FriendsNamesList;

	private DefaultListModel<String> favModel;

	private TwitterEngine engine;

	private JTable table;

	private TitledBorder tableTitle, textTitle;

	/**
	 * Packs and sets the GUI
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
		MyTweetsTabInit();
		FriendsListTabInit();
		profileTabInit();
		trendsTabInit();

		tabs = new JTabbedPane();

		tabs.addTab("Profile", ProfilePanel);
		tabs.addTab("Post Tweet/Timeline", PostTimePanel);
		tabs.addTab("Followers", TwitResults);
		tabs.addTab("Friends List", FriendsListPanel);
		tabs.addTab("Favorites", FavoritesPanel);
		tabs.addTab("Your Tweets", MyTweetsPanel);
		tabs.addTab("Trends", trendsPanel);

		guiFrame.add(tabs);
		guiFrame.setTitle("Twitter for "
				+ engine.getRealName(engine.getuserid()));

		final URL url = new URL(
				"http://jonbennallick.co.uk/wp-content/uploads/2012/11/Twitter-Logo-Icon-by-Jon-Bennallick-02.png");
		guiFrame.setIconImage(ImageIO.read(url));

		guiFrame.setVisible(true);
	}

	private void FriendsListTabInit() throws TwitterException {
		FriendsListPanel = new JPanel();
		FriendsListPanel.setLayout(new BorderLayout());

		JPanel ButtonsPanel = new JPanel();

		VeiwProfile = new JButton("View Profile");
		RemoveFriend = new JButton("Remove Friend");
		UpdateFriendsList = new JButton("Update");
		ButtonListener listener = new ButtonListener();

		VeiwProfile.addActionListener(listener);
		RemoveFriend.addActionListener(listener);
		UpdateFriendsList.addActionListener(listener);

		users = engine.getFriendsList(engine.getuserid());
		FriendsNamesList = new String[users.size()];
		userIds = new long[users.size()];
		int count = 0;

		for (User user : users) {
			FriendsNamesList[count] = user.getName();
			userIds[count] = user.getId();
			count++;
		}

		FriendsList = new JList<String>(FriendsNamesList);

		ButtonsPanel.add(UpdateFriendsList);
		ButtonsPanel.add(VeiwProfile);
		ButtonsPanel.add(RemoveFriend);

		JScrollPane scrollpane = new JScrollPane(FriendsList);
		FriendsListPanel.add(scrollpane, BorderLayout.CENTER);
		FriendsListPanel.add(ButtonsPanel, BorderLayout.PAGE_END);
	}

	private final void trendsTabInit() {

		trendsPanel = new JPanel();
		trendsPanel.setLayout(new BorderLayout());
		trendsArea = new JTextArea(20, 40);
		trendsArea.setLineWrap(true);
		trendsArea.setEditable(false);

		trendsPanel.add(trendsArea);
	}

	private final void updateTrendsArea() {
		trendsArea.setText("");
		for (Trend trend : trendslist) {
			trendsArea.append(trend.getName());
			trendsArea.append("\n");
		}
	}

	private void MyTweetsTabInit() throws TwitterException {
		DefaultListModel<String> model = new DefaultListModel<String>();
		list = new JList<String>(model);
		list.setFixedCellHeight(50);

		MyTweetsPanel = new JPanel();
		MyTweetsPanel.setLayout(new BorderLayout());
		JPanel ButtonsPanel = new JPanel();

		String html1 = "<html><body style='width: ";
		String html2 = "px'>";

		MyTweetsFavoriteButton = new JButton("Favorite");
		DeleteMyTweetButton = new JButton("Delete Tweet");
		UpdateMyTweetsList = new JButton("Update");
		ButtonListener listener = new ButtonListener();

		MyTweetsFavoriteButton.addActionListener(listener);
		DeleteMyTweetButton.addActionListener(listener);
		UpdateMyTweetsList.addActionListener(listener);

		statuses = engine.getMyTweets();
		MyTweetsStatusIds = new long[statuses.size()];
		int count = 0;

		for (Status status : statuses) {
			model.addElement(html1 + "550" + html2 + status.getText());
			MyTweetsStatusIds[count] = status.getId();
			count++;
		}

		JScrollPane scrollpane = new JScrollPane(list);

		ButtonsPanel.add(UpdateMyTweetsList);
		ButtonsPanel.add(MyTweetsFavoriteButton);
		ButtonsPanel.add(DeleteMyTweetButton);

		MyTweetsPanel.add(scrollpane, BorderLayout.CENTER);
		MyTweetsPanel.add(ButtonsPanel, BorderLayout.PAGE_END);

	}

	private final void profileTabInit() throws IllegalStateException,
			TwitterException, MalformedURLException {

		Border blackline = BorderFactory.createLineBorder(Color.black);

		ProfilePanel = new JPanel();

		ProfilePanel.setLayout(new GridLayout(2, 1));

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
		JLabel realname = new JLabel(engine.getRealName(engine.getuserid()));
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

		LoginButton = new JButton("Log In");
		LoginButton.addActionListener(listener);
		SignoutButton = new JButton("Sign Out");
		SignoutButton.addActionListener(listener);
		ViewMessagesButton = new JButton("View Messages");
		ViewMessagesButton.addActionListener(listener);

		login.add(ViewMessagesButton);
		login.add(LoginButton);
		login.add(SignoutButton);

		ProfilePanel.add(user, BorderLayout.CENTER);
		ProfilePanel.add(login, BorderLayout.PAGE_END);
	}

	/**
	 * Contains the Panels in the Operation Section. The combobox allows
	 * selection bettween the different options.
	 * 
	 * @throws TwitterException
	 */
	private void postTimeTabInit() throws TwitterException {
		ButtonListener listener = new ButtonListener();
		UpdatePanel = new JPanel();
		PostTimePanel = new JPanel();

		PostTimePanel.setLayout(new BorderLayout());

		DefaultListModel<String> model = new DefaultListModel<String>();

		String html1 = "<html><body style='width: ";
		String html2 = "px'>";

		updateTextBox = new JTextField(50);

		TweetButton = new JButton("Post Tweet");
		TweetButton.addActionListener((listener));
		updateTimelineButton = new JButton("Update");
		updateTimelineButton.addActionListener(listener);
		TimelineFavoriteButton = new JButton("Favorite");
		TimelineFavoriteButton.addActionListener(listener);

		UpdatePanel.add(updateTextBox);
		UpdatePanel.add(TweetButton);
		UpdatePanel.add(updateTimelineButton);

		MyTimeLineStatusIds = new long[200];
		int count = 0;
		statuses = engine.getTimeline();

		TimelineList = new JList<String>(model);
		TimelineList.setFixedCellHeight(50);

		for (Status status : statuses) {
			model.addElement(html1 + "550" + html2
					+ engine.getRealName(status.getUser().getId()) + ": "
					+ status.getText() + "\n");
			MyTimeLineStatusIds[count] = status.getId();
			count++;
		}

		JScrollPane TimeLinePane = new JScrollPane(TimelineList);
		PostTimePanel.add(UpdatePanel, BorderLayout.PAGE_START);
		PostTimePanel.add(TimeLinePane, BorderLayout.CENTER);
		PostTimePanel.add(TimelineFavoriteButton, BorderLayout.PAGE_END);
	}

	/**
	 * Creates the Eastern Panel using the class TwitterResultsPanel();
	 */
	private void followerTabInit() {

		GridLayout resultLayout = new GridLayout(2, 2, 5, 10);
		TwitResults = new JPanel();
		TwitResults.setLayout(resultLayout);

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
		scrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		// attaches table to the scroll pane
		scrollPane.setViewportView(table);
		scrollPane.setBorder(tableTitle);
		TwitResults.add(scrollPane);

		// Text Area
		textArea = new JTextArea(7, 40);
		textArea.setEditable(false);
		textTitle = BorderFactory.createTitledBorder("Status Text");
		textArea.setLineWrap(true);
		textArea.setBorder(textTitle);
		JScrollPane textPane = new JScrollPane();
		textPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		textPane.setViewportView(textArea);
		TwitResults.add(textPane);
	}

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

	public void favoritesTabInit() throws TwitterException {
		FavoritesPanel = new JPanel();
		FavoritesPanel.setLayout(new BorderLayout());

		JPanel ButtonsPanel = new JPanel();

		ButtonListener listener = new ButtonListener();

		UpdateFavoritesButton = new JButton("Update");
		UpdateFavoritesButton.addActionListener(listener);
		DeleteFavoriteButton = new JButton("Delete");
		DeleteFavoriteButton.addActionListener(listener);

		statuses = engine.getFavoriteTweets();
		MyFavoriteStatusIds = new long[200];

		favModel = new DefaultListModel<String>();
		FavoritesList = new JList<String>(favModel);
		FavoritesList.setFixedCellHeight(50);

		updateFavoritesList();
		JScrollPane FavoritePane = new JScrollPane(FavoritesList);

		ButtonsPanel.add(UpdateFavoritesButton);
		ButtonsPanel.add(DeleteFavoriteButton);

		FavoritesPanel.add(FavoritePane, BorderLayout.CENTER);
		FavoritesPanel.add(ButtonsPanel, BorderLayout.PAGE_END);
	}

	private final void updateFavoritesList() throws TwitterException {
		String html1 = "<html><body style='width: ";
		String html2 = "px'>";
		statuses = engine.getFavoriteTweets();
		int count = 0;
		for (Status status : statuses) {
			favModel.addElement(html1 + "400" + html2
					+ status.getUser().getName() + ":" + status.getText());
			MyFavoriteStatusIds[count] = status.getId();
			count++;
		}
		FavoritesList.updateUI();
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

	class ButtonListener implements ActionListener {
		public void actionPerformed(final ActionEvent e) {
			if (e.getSource().equals(LoginButton)) {
				try {
					tabs.removeAll();
					engine.logout();

					engine.login();
					profileTabInit();
					postTimeTabInit();
					followerTabInit();
					favoritesTabInit();
					MyTweetsTabInit();
					FriendsListTabInit();

					tabs.addTab("Profile", ProfilePanel);
					tabs.addTab("Post Tweet/Timeline", PostTimePanel);
					tabs.addTab("Followers", TwitResults);
					tabs.addTab("Friends List", FriendsListPanel);
					tabs.addTab("Favorites", FavoritesPanel);
					tabs.addTab("Your Tweets", MyTweetsPanel);

					guiFrame.add(tabs);

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
			if (e.getSource().equals(SignoutButton)) {
				engine.logout();

				System.exit(0);
			}

			if (e.getSource().equals(TweetButton)) {
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

					tabs.insertTab("Post Tweet/Timeline", null, PostTimePanel,
							null, 1);

					tabs.setSelectedIndex(1);
				} catch (IllegalStateException | TwitterException e1) {
					e1.printStackTrace();
				}
			}

			if (e.getSource().equals(UpdateFavoritesButton)) {

				try {

					tabs.remove(4);
					favoritesTabInit();

					tabs.insertTab("Favorites", null, FavoritesPanel, null, 4);

					tabs.setSelectedIndex(4);

				} catch (IllegalStateException | TwitterException e1) {
					e1.printStackTrace();
				}
			}

			if (e.getSource().equals(MyTweetsFavoriteButton)) {
				try {
					engine.favoriteTweet(MyTweetsStatusIds[list
							.getSelectedIndex()]);
				} catch (NumberFormatException | TwitterException e1) {
					e1.printStackTrace();
				}

			}

			if (e.getSource().equals(DeleteMyTweetButton)) {

				try {
					engine.deleteStatus(MyTweetsStatusIds[list
							.getSelectedIndex()]);
				} catch (TwitterException e1) {
					e1.printStackTrace();
				}

			}

			if (e.getSource().equals(VeiwProfile)) {
				try {
					new FriendVeiwer(userIds[FriendsList.getSelectedIndex()],
							engine);
				} catch (TwitterException e1) {
					e1.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

			if (e.getSource().equals(RemoveFriend)) {
				try {
					engine.removefriend(userIds[FriendsList.getSelectedIndex()]);
				} catch (TwitterException e1) {
					e1.printStackTrace();
				}
			}

			if (e.getSource().equals(ViewMessagesButton)) {
				try {
					new DirectMessageViewer(engine);
				} catch (TwitterException e1) {
					e1.printStackTrace();
				}
			}

			if (e.getSource().equals(TimelineFavoriteButton)) {
				try {
					engine.favoriteTweet(MyTimeLineStatusIds[TimelineList
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

			if (e.getSource().equals(DeleteFavoriteButton)) {
				try {
					engine.deleteStatus(MyFavoriteStatusIds[FavoritesList
							.getSelectedIndex()]);
				} catch (TwitterException e1) {
					e1.printStackTrace();
				}
			}

			if (e.getSource().equals(UpdateFriendsList)) {
				tabs.remove(3);
				try {
					FriendsListTabInit();
				} catch (TwitterException e1) {

					e1.printStackTrace();
				}

				tabs.insertTab("Friends", null, FriendsListPanel, null, 3);

				tabs.setSelectedIndex(3);
			}

			if (e.getSource().equals(UpdateMyTweetsList)) {
				tabs.remove(5);
				try {
					favoritesTabInit();
				} catch (TwitterException e1) {
					e1.printStackTrace();
				}

				tabs.insertTab("My Tweets", null, MyTweetsPanel, null, 5);

				tabs.setSelectedIndex(5);
			}
		}
	}

	/**
	 * Handles all of the actions in the JMenuBar
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
	 * other components that are part of the GUI
	 * 
	 * @param args
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