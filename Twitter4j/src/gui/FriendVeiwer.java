package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.User;

import engine.TwitterEngine;
/**
 * Class used to view friends.
 *
 */
public class FriendVeiwer {
	/**
	 * Main frame.
	 */
	private JFrame guiFrame;
	/**
	 * gui tabs.
	 */
	private JTabbedPane tabs;
	/**
	 * Panel for profile contents.
	 */
	private JPanel profilePanel;
	/**
	 * Panel for timeline.
	 */
	private JPanel timelinePanel;
	/**
	 * Panel for friends list.
	 */
	private JPanel friendsListPanel;
	/**
	 * Panel for user favorites.
	 */
	private JPanel favoritesPanel;
	/**
	 * Panel for friend options.
	 */
	private JPanel friendsOptionsPanel;
	/**
	 * Button to add favorite.
	 */
	private JButton favoriteButton;
	/**
	 * Button to view a profile.
	 */
	private JButton viewProfileButton;
	/**
	 * Button to add favorite tweet.
	 */
	private JButton favoriteTweetButton;
	/**
	 * Button to send direct message.
	 */
	private JButton directMessageButton;
	/**
	 * Button to create a new friendship.
	 */
	private JButton createFriendButton;
	/**
	 * JList containting timeline.
	 */
	private JList<String> timelineList;
	/**
	 * JList containing friends list.
	 */
	private JList<String> friendsList;
	/**
	 * JList containing favorites list.
	 */
	private JList<String> favoritesList;
	/**
	 * List of twitter statuses.
	 */
	private List<Status> statuses;
	/**
	 * List of twitter users.
	 */
	private List<User> users;
	/**
	 * Array of friends names.
	 */
	private String[] friendsNamesList;
	/**
	 * Array of user ids.
	 */
	private long[] userIds;
	/**
	 * Array of status ids.
	 */
	private long[] MyTweetStatusIds;
	private long[] TimelineStatusIds;
	/**
	 * Current user id.
	 */
	private long userId;
	/**
	 * TwitterEngine instance.
	 */
	private TwitterEngine engine;
	/**
	 * Constructor.
	 * @param userids current user id.
	 * @param eng TwitterEngine instance
	 * @throws Exception 
	 */
	public FriendVeiwer(final long userids, final TwitterEngine eng) 
	throws Exception {

		userId = userids;

		guiFrame = new JFrame();

		this.engine = eng;

		tabs = new JTabbedPane();

		timelineTabInit();
		favoritesTabInit();
		friendsListTabInit();
		profileTabInit(userids);

		final URL url = new URL(
				"http://cdn-ak.f.st-hatena.com/images/fotolife"
				+ "/r/rinrinbell/20091225/20091225215812.png");
		guiFrame.setIconImage(ImageIO.read(url));

		tabs.addTab("Profile", profilePanel);
		tabs.addTab(this.engine.getfriendsName(userids) + "'s Timeline",
				timelinePanel);
		tabs.addTab(this.engine.getfriendsName(userids) + "'s Favorite Tweets",
				favoritesPanel);
		tabs.addTab("Friends", friendsListPanel);

		guiFrame.setSize(800, 450);

		guiFrame.setTitle("Twitter for " + eng.getfriendsName(userId));

		guiFrame.add(tabs);

		guiFrame.setVisible(true);

	}
	/**
	 * Initialize profile tab.
	 * @param userids user id.
	 * @throws MalformedURLException 
	 * @throws TwitterException 
	 */
	private void profileTabInit(final long userids) 
	throws MalformedURLException, TwitterException {
		Border blackline = BorderFactory.createLineBorder(Color.black);

		ButtonListener listener = new ButtonListener();

		profilePanel = new JPanel();
		friendsOptionsPanel = new JPanel();

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

		ImageIcon img = engine.getfriendProfileImage(userids);

		JLabel userIcon = new JLabel(img);
		userIcon.setBorder(blackline);
		JLabel screenname = new JLabel(engine.getScreenName());
		screenname.setHorizontalAlignment(SwingConstants.CENTER);
		screenname.setBorder(blackline);
		screenname.setSize(20, 12);
		JLabel realname = new JLabel(engine.getRealName(userId));
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

		directMessageButton = new JButton("Send Direct Message");
		directMessageButton.addActionListener(listener);
		createFriendButton = new JButton("Follow");
		createFriendButton.addActionListener(listener);
		friendsOptionsPanel.add(createFriendButton);
		friendsOptionsPanel.add(directMessageButton);

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
		profilePanel.add(user, BorderLayout.CENTER);
		profilePanel.add(friendsOptionsPanel, BorderLayout.PAGE_END);
	}
	/**
	 * Initialize friends list tab.
	 * @throws TwitterException 
	 */
	private void friendsListTabInit() throws TwitterException {
		friendsListPanel = new JPanel();
		viewProfileButton = new JButton("View Profile");
		ButtonListener listener = new ButtonListener();

		viewProfileButton.addActionListener(listener);

		friendsNamesList = new String[200];
		userIds = new long[200];
		int count = 0;
		users = engine.getFriendsList(userId);
		for (User user : users) {
			friendsNamesList[count] = user.getName();
			userIds[count] = user.getId();
			count++;
		}

		friendsList = new JList<String>(friendsNamesList);
		friendsList.setFixedCellHeight(30);

		JScrollPane scrollpane = new JScrollPane(friendsList);
		friendsListPanel.add(scrollpane);
		friendsListPanel.add(viewProfileButton);
	}
	/**
	 * Initialize favorites tab.
	 * @throws TwitterException 
	 */
	private void favoritesTabInit() throws TwitterException {
		favoritesPanel = new JPanel();

		ButtonListener listener = new ButtonListener();

		String html1 = "<html><body style='width: ";
		String html2 = "px'>";

		favoriteTweetButton = new JButton("Favorite");

		favoriteTweetButton.addActionListener(listener);

		statuses = engine.getFriendsFavoriteTweets(userId);
		MyTweetStatusIds = new long[200];

		DefaultListModel<String> model = new DefaultListModel<String>();
		favoritesList = new JList<String>(model);
		favoritesList.setFixedCellHeight(50);

		int count = 0;

		for (Status status : statuses) {
			model.addElement(html1 + "400" + html2 + status.getUser().getName()
					+ ":" + status.getText());
			MyTweetStatusIds[count] = status.getId();
			count++;
		}

		JScrollPane favoritePane = new JScrollPane(favoritesList);

		favoritesPanel.add(favoritePane);
		favoritesPanel.add(favoriteTweetButton);
	}
	/**
	 * Initialize timeline tab.
	 * @throws TwitterException 
	 */
	private void timelineTabInit() throws TwitterException {
		ButtonListener listener = new ButtonListener();

		String html1 = "<html><body style='width: ";
		String html2 = "px'>";

		DefaultListModel<String> model = new DefaultListModel<String>();
		timelineList = new JList<String>(model);
		timelineList.setFixedCellHeight(50);

		timelinePanel = new JPanel();

		favoriteButton = new JButton("Favorite");
		favoriteButton.addActionListener(listener);

		TimelineStatusIds = new long[200];
		int count = 0;
		statuses = engine.getfriendsTimeline(userId);

		for (Status status : statuses) {
			model.addElement(html1 + "500" + html2 + engine.getRealName(userId)
					+ ": " + status.getText() + "\n");
			model.addElement(" ");
			TimelineStatusIds[count] = status.getId();
			count++;

		}

		JScrollPane timeLinePane = new JScrollPane(timelineList);
		timelinePanel.add(timeLinePane);
		timelinePanel.add(favoriteButton);
	}
	/**
	 * Contains button code.
	 *
	 */
	class ButtonListener implements ActionListener {
		/**
		 * Wait for button press.
		 * @param e action performed
		 */
		public void actionPerformed(final ActionEvent e) {

			if (e.getSource().equals(favoriteButton)) {
				try {
					if(timelineList.getSelectedIndex() >= 0){
						engine.favoriteTweet(TimelineStatusIds[timelineList
							.getSelectedIndex()]);
					}

				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(guiFrame,
							"You already have that tweet as a favorite");
				} catch (TwitterException e1) {
					e1.printStackTrace();
				}
			}
			if (e.getSource().equals(favoriteTweetButton)) {
				try {
					if(favoritesList.getSelectedIndex() >= 0){
						engine.favoriteTweet(TimelineStatusIds[favoritesList
							.getSelectedIndex()]);
					}
				} catch (NumberFormatException  e1) {
					JOptionPane.showMessageDialog(guiFrame,

							"You already have that tweet as a favorite");
				} catch (TwitterException e1) {
					e1.printStackTrace();
				}
			}

			if (e.getSource().equals(viewProfileButton)) {
				try {
					new FriendVeiwer(userIds[friendsList.getSelectedIndex()],
							engine);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

			if (e.getSource().equals(createFriendButton)) {
				try {
					engine.createFriendship(userId);
				} catch (TwitterException e1) {
					e1.printStackTrace();
				}
			}

			if (e.getSource().equals(directMessageButton)) {
				new DirectMessageSender(userId, engine);
			}
		}
	}
}
