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

public class FriendVeiwer {

	private JFrame GUI;

	private JTabbedPane tabs;

	private JPanel ProfilePanel;
	private JPanel TimelinePanel;
	private JPanel FriendsListPanel;
	private JPanel FavoritesPanel;
	private JPanel FriendsOptionsPanel;

	private JButton FavoriteButton;
	private JButton ViewProfileButton;
	private JButton FavoriteTweetButton;
	private JButton DirectMessageButton;
	private JButton CreateFriendButton;

	private JList<String> TimelineList;
	private JList<String> FriendsList;
	private JList<String> FavoritesList;

	private List<Status> statuses;
	private List<User> users;

	private String[] FriendsNamesList;
	private long[] UserIds;
	private long[] statusIds;

	private long userId;

	private TwitterEngine engine;

	public FriendVeiwer(long userIds, TwitterEngine engine) throws Exception {

		userId = userIds;

		GUI = new JFrame();

		this.engine = engine;

		tabs = new JTabbedPane();

		TimelineTabInit();
		favoritesTabInit();
		FriendsListTabInit();
		profileTabInit(userIds);

		final URL url = new URL(
				"http://cdn-ak.f.st-hatena.com/images/fotolife/r/rinrinbell/20091225/20091225215812.png");
		GUI.setIconImage(ImageIO.read(url));

		tabs.addTab("Profile", ProfilePanel);
		tabs.addTab(this.engine.getfriendsName(userIds) + "'s Timeline",
				TimelinePanel);
		tabs.addTab(this.engine.getfriendsName(userIds) + "'s Favorite Tweets",
				FavoritesPanel);
		tabs.addTab("Friends", FriendsListPanel);

		GUI.setSize(800, 450);

		GUI.setTitle("Twitter for " + engine.getfriendsName(userId));

		GUI.add(tabs);

		GUI.setVisible(true);

	}

	private void profileTabInit(long userIds) throws IllegalStateException,
			MalformedURLException, TwitterException {
		Border blackline = BorderFactory.createLineBorder(Color.black);

		ButtonListener listener = new ButtonListener();

		ProfilePanel = new JPanel();
		FriendsOptionsPanel = new JPanel();

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

		ImageIcon img = engine.getfriendProfileImage(userIds);

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

		DirectMessageButton = new JButton("Send Direct Message");
		DirectMessageButton.addActionListener(listener);
		CreateFriendButton = new JButton("Follow");
		CreateFriendButton.addActionListener(listener);
		FriendsOptionsPanel.add(CreateFriendButton);
		FriendsOptionsPanel.add(DirectMessageButton);

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
		ProfilePanel.add(user, BorderLayout.CENTER);
		ProfilePanel.add(FriendsOptionsPanel, BorderLayout.PAGE_END);
	}

	private void FriendsListTabInit() throws TwitterException {
		FriendsListPanel = new JPanel();
		ViewProfileButton = new JButton("View Profile");
		ButtonListener listener = new ButtonListener();

		ViewProfileButton.addActionListener(listener);

		FriendsNamesList = new String[200];
		UserIds = new long[200];
		int count = 0;
		users = engine.getFriendsList(userId);
		for (User user : users) {
			FriendsNamesList[count] = user.getName();
			UserIds[count] = user.getId();
			count++;
		}

		FriendsList = new JList<String>(FriendsNamesList);
		FriendsList.setFixedCellHeight(30);

		JScrollPane scrollpane = new JScrollPane(FriendsList);
		FriendsListPanel.add(scrollpane);
		FriendsListPanel.add(ViewProfileButton);
	}

	private void favoritesTabInit() throws TwitterException {
		FavoritesPanel = new JPanel();

		ButtonListener listener = new ButtonListener();

		String html1 = "<html><body style='width: ";
		String html2 = "px'>";

		FavoriteTweetButton = new JButton("Favorite");

		FavoriteTweetButton.addActionListener(listener);

		statuses = engine.getFriendsFavoriteTweets(userId);
		long[] statusIds = new long[200];

		DefaultListModel<String> model = new DefaultListModel<String>();
		FavoritesList = new JList<String>(model);
		FavoritesList.setFixedCellHeight(50);

		int count = 0;

		for (Status status : statuses) {
			model.addElement(html1 + "400" + html2 + status.getUser().getName()
					+ ":" + status.getText());
			statusIds[count] = status.getId();
			count++;
		}

		JScrollPane FavoritePane = new JScrollPane(FavoritesList);

		FavoritesPanel.add(FavoritePane);
		FavoritesPanel.add(FavoriteTweetButton);
	}

	private void TimelineTabInit() throws TwitterException {
		ButtonListener listener = new ButtonListener();

		String html1 = "<html><body style='width: ";
		String html2 = "px'>";

		DefaultListModel<String> model = new DefaultListModel<String>();
		TimelineList = new JList<String>(model);
		TimelineList.setFixedCellHeight(50);

		TimelinePanel = new JPanel();

		FavoriteButton = new JButton("Favorite");
		FavoriteButton.addActionListener(listener);

		statusIds = new long[200];
		int count = 0;
		statuses = engine.getfriendsTimeline(userId);

		for (Status status : statuses) {
			model.addElement(html1 + "500" + html2 + engine.getRealName(userId)
					+ ": " + status.getText() + "\n");
			model.addElement(" ");
			statusIds[count] = status.getId();
			count += 2;

		}

		JScrollPane TimeLinePane = new JScrollPane(TimelineList);
		TimelinePanel.add(TimeLinePane);
		TimelinePanel.add(FavoriteButton);
	}

	class ButtonListener implements ActionListener {
		public void actionPerformed(final ActionEvent e) {

			if (e.getSource().equals(FavoriteButton)) {
				try {
					engine.favoriteTweet(statusIds[TimelineList
							.getSelectedIndex()]);
				} catch (NumberFormatException | TwitterException e1) {
					JOptionPane.showMessageDialog(GUI,
							"Please do not select the white space.  Thank you");
				}
			}
			if (e.getSource().equals(FavoriteTweetButton)) {
				try {
					engine.favoriteTweet(statusIds[FavoritesList
							.getSelectedIndex()]);
				} catch (NumberFormatException | TwitterException e1) {
					JOptionPane.showMessageDialog(GUI,
							"Please do not select the white space.  Thank you");
				}
			}

			if (e.getSource().equals(ViewProfileButton)) {
				try {
					new FriendVeiwer(UserIds[FriendsList.getSelectedIndex()],
							engine);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

			if (e.getSource().equals(CreateFriendButton)) {
				try {
					engine.CreateFriendship(userId);
				} catch (TwitterException e1) {
					e1.printStackTrace();
				}
			}

			if (e.getSource().equals(DirectMessageButton)) {
				new DirectMessageSender(userId, engine);
			}
		}
	}
}
