package model;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.table.AbstractTableModel;

import twitter4j.AccountSettings;
import twitter4j.DirectMessage;
import twitter4j.Location;
import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Trends;
import twitter4j.User;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

/**
 * . TwitModel.class
 * 
 * Does all of the calculating for the TwitterLite
 * 
 * @author Ben
 */
@SuppressWarnings("serial")
public class TwitModel extends AbstractTableModel implements HyperlinkListener,
		ActionListener {
	/** Stores the current date. */
	private Date date;
	/** Stores the username. */
	private String loginName;
	/** Stores a user's actual name. */
	private String displayName;
	/** Stores the number of followers. */
	private int followers;
	/** Stores the number of friends. */
	private int friends;
	/** Store whether or not is a favorite. */
	private boolean fave;
	/** Stores the text of a Status. */
	private String text;
	/** Stores a Tweet sent/received. */
	private Tweet t;
	/** Stores an ArrayList of tweets. */
	private ArrayList<MyTweet> myTweets;
	/** Main twitter variable holds a twitter instance. */
	private Twitter twitter;
	/** String array contains names of column headings. */
	private String[] columnNames = { "Date", "Login Name", "Display Name",
			"Freinds", "Followers", "Favorite" };
	/** Frame of the Login OptionPane. */
	private JFrame frame;
	/** Configuration Builder for Authentication Method. */
	private ConfigurationBuilder cb;
	/** TwitterFactor gets twitter instance for Authentication. */
	private TwitterFactory tf;
	/** RequestToken for login authentication. */
	private RequestToken requestToken;
	/** AccessToken for login authentication. */
	private AccessToken accessToken;
	/** User to use as the login within authentication. */
	private User user;
	/** Custom class for storing favorite users. */
	private FavoritesUtility favorites;
	/**
	 * Array of trend locations.
	 */
	private Location[] trendsLocations;

	/**
	 * The Constructor for TwitModel().
	 * 
	 * @throws TwitterException 
	 */
	public TwitModel() throws TwitterException {
		favorites = new FavoritesUtility();

		myTweets = new ArrayList<MyTweet>();
		// wordCounter = new ArrayList<Word>();

	}

	/**
	 * Gets the values to be displayed within the Table.
	 * 
	 * @return val
	 * @param row
	 *            specified row of the table
	 * @param col
	 *            specified column of the table
	 */
	public final Object getValueAt(final int row, final int col) {
		Object val = null;
		switch (col) {
		case 0:
			val = myTweets.get(row).getCreatedAt();
			break;
		case 1:
			val = myTweets.get(row).getLoginName();
			break;
		case 2:
			val = myTweets.get(row).getDisplayName();
			break;
		case 3:
			val = myTweets.get(row).getFriendsCount();
			break;
		case 4:
			val = myTweets.get(row).getFollowersCount();
			break;
		case 5:
			if ((!getFavorite(myTweets.get(row).getDisplayName()))) {
				val = "false";
			} else {
				val = "true";
			}
		default:
		}
		return val;
	}

	/**
	 * Creates the column Names.
	 * 
	 * @param column
	 *            specific column to get the name of
	 * @return String containing the column name
	 */
	@Override
	public final String getColumnName(final int column) {
		return columnNames[column];
	}

	/**
	 * Adds a tweet to the table from the ArrayList.
	 * 
	 * @param tweet
	 *            - arrayList being passed into the Table
	 */
	public final void add(final MyTweet tweet) {
		if (tweet != null) {
			myTweets.add(tweet);
			fireTableRowsInserted(myTweets.size() - 1, myTweets.size());
		}
	}

	/**
	 * Removes a Tweets from the array List.
	 * 
	 * @param index
	 *            - Position of the value
	 */
	public final void remove(final int index) {
		try {
			myTweets.remove(index);
			fireTableRowsDeleted(index, index);
			return;
		} catch (IndexOutOfBoundsException e) {
			JOptionPane.showMessageDialog(null, "Invalid Selection",
					"Invalid action", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Gets the Status of the specified userName.
	 * 
	 * @param userName
	 *            - user that is being searched for
	 * @return Status - status of the given userName
	 */
	public final Status retrieveStatus(final String userName) {
		User tempUser = null;
		try {
			tempUser = twitter.showUser(userName);

		} catch (TwitterException e) {
			JOptionPane.showMessageDialog(null,
					"Could not get Status of user \"" + userName + "\"");
		}

		return tempUser.getStatus();
	}

	/**
	 * Gets the status timeline of a particular user.
	 * 
	 * @return List of Timeline Statuses
	 * @throws TwitterException 
	 */
	public final List<Status> retriveTimeline() throws TwitterException {

		List<Status> statuses = twitter.getHomeTimeline();

		for (Status status : statuses) {
			arrayListGenerator(status);
		}
		return statuses;
	}

	/**
	 * Gets the size of an Array List.
	 * 
	 * @return - myTweets.size() - size of myTweets
	 */
	public final int getArrayListSize() {
		return myTweets.size();
	}

	/**
	 * Creates the ArrayList Generator.
	 * 
	 * @param status
	 *            - the current status of a twitter user
	 */
	public final void arrayListGenerator(final Status status) {
		date = status.getCreatedAt();
		loginName = status.getUser().getScreenName();
		displayName = status.getUser().getName();
		followers = status.getUser().getFollowersCount();
		friends = status.getUser().getFriendsCount();
		text = status.getText();
		fave = getFavorite(status.getUser().getName());
		t = new Tweet(date, loginName, displayName, followers, friends, text,
				fave);
		add(t);
	}

	/**
	 * Retrieves the status of the selected index.
	 * 
	 * @param index
	 *            - index that is selected
	 * @return - the text of that index.
	 */
	public final String retriveDisplayStatis(final int index) {
		return myTweets.get(index).getText();
	}

	/**
	 * getWordSearch gets what is being searched and produces an arrayList.
	 * 
	 * @param keyWord
	 *            - word being searched
	 */
	public void getWordSearch(final String keyWord) {

	}

	/**
	 * Gets the phrase that is being searched.
	 * 
	 * @param keyWord
	 *            - keyword
	 * @param phrase
	 *            - phrase
	 */
	public void getPhraseSearch(final String keyWord, final String phrase) {

	}
	/**
	 * Add a favorite user.
	 * @param dsplayName name of user to add
	 */
	public final void addFavorite(final String dsplayName) {
		favorites.addFavoriteUser(dsplayName);
	}	
	/**
	 * Delete a favorite user.
	 * @param dsplayName name of user to delete
	 */
	public final void deleteFavorite(final String dsplayName) {
		favorites.removeFavorite(dsplayName);
	}
	/**
	 * Clear favorites list.
	 */
	public final void clearFavoriteList() {
		favorites.clearList();
	}
	/**
	 * Get favorites string list.
	 */
	public final void getFStringList() {
		favorites.getStringList();
	}
	/**
	 * Save list of favorites.
	 */
	public final void saveFavorites() {
		favorites.saveFavorites();
	}
	/**
	 * Load list of favorites.
	 * @param username username of favorites list owner
	 */
	public final void loadFavorites(final String username) {
		favorites.loadFavorites(username);
	}
	/**
	 * Determine if a user is a favorite.
	 * @param username user to determine favorite of
	 * @return true if favorite false otherwise
	 */
	private boolean getFavorite(final String username) {
		if (favorites.getStringList().contains(username)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Gets the number of people a particular user has following them.
	 * 
	 * @param usr
	 *            - The user to get to get the number of followers for
	 * @return the number of people a particular user has following them
	 */
	public final int getFriendsNumber(final User usr) {
		int length = 0;
		try {
			length = twitter.getFriendsIDs(usr.getId(), -1).getIDs().length;
		} catch (TwitterException ex) {
			System.err.println("Problem getting number of friends");
		}
		return length;
	}

	/**
	 * Authenticates a user.
	 * @throws TwitterException 
	 * @throws IOException 
	 */
	public final void authenticate() throws TwitterException, IOException {
		String username = "";
		
		cb = new ConfigurationBuilder();

		cb.setDebugEnabled(true)
				.setOAuthConsumerKey("UP8vf0xlwUkPHvikkEBXQ")
				.setOAuthConsumerSecret(
						"62H0idR3HypsRitEUQI3j2ugqTINXybjeBSLr4QH78");

		tf = new TwitterFactory(cb.build());
		twitter = tf.getInstance();

		while(username == null || username.length() <= 0){
			username = JOptionPane.showInputDialog(frame, "User Name");
			if (username == null) {
				System.exit(0);
			}
		}
		File file = new File("loginInformation.txt");

		Scanner scanner = new Scanner(file);

		while (scanner.hasNextLine()) {

			String line = scanner.nextLine();

			String[] s = line.split(", ");
			// s[1].trim();
			// s[2].trim();

			if (s[0].equals(username)) {
				accessToken = new AccessToken(s[1], s[2]);
				twitter.setOAuthAccessToken(accessToken);
				user = twitter.showUser(twitter.getId());
			}

		}
		favorites.loadFavorites(username);
		if (accessToken != null) {
			scanner.close();
			return;
		} else {

			try {

				try {
					// get request token.
					// this will throw IllegalStateException if access token is
					// already available
					requestToken = twitter.getOAuthRequestToken();

					accessToken = null;

					while (null == accessToken) {

						URL url = new URL(requestToken.getAuthenticationURL());

						openWebpage(url);

						String pin = JOptionPane.showInputDialog(frame,
								"Enter the PIN(if available) and hit ok.");

						try {
							if (pin.length() > 0) {
								accessToken = twitter.getOAuthAccessToken(
										requestToken, pin);
							} else {
								accessToken = twitter
										.getOAuthAccessToken(requestToken);
							}
						} catch (TwitterException te) {
							if (401 == te.getStatusCode()) {

								JOptionPane.showMessageDialog(frame,
										"Unable to get the access token.",
										"Inane error",
										JOptionPane.ERROR_MESSAGE);
								te.printStackTrace();

								System.out.println(
									"Unable to get the access token.");
							} else {
								te.printStackTrace();
							}
						}
					}

				} catch (IllegalStateException ie) {
					// access token is already available, or consumer key/secret
					// is not set.
					if (!twitter.getAuthorization().isEnabled()) {
						System.out
							.println("OAuth consumer key/secret is not set.");
						System.exit(-1);
					}
				}

			} catch (NullPointerException np) {
				System.out.println("Exiting");
				System.exit(0);
			}

			// user = twitter.showUser(twitter.getId());

			PrintWriter out = new PrintWriter(new FileWriter(
					"./loginInformation.txt", true));
			String saveFile = "";

			user = twitter.showUser(twitter.getId());
			System.out.println(user.getName());

			saveFile += user.getName() + ", " + accessToken.getToken() + ", "
					+ accessToken.getTokenSecret();
			scanner.close();
			out.println();
			out.print(saveFile);
			out.close();
		}
		user = twitter.showUser(twitter.getId());
	}
	/**
	 * Opens a webpage of specific URI.
	 * @param uri 
	 */
	public static void openWebpage(final URI uri) {
		Desktop desktop = null;
		if (Desktop.isDesktopSupported()) {
			desktop = Desktop.getDesktop();
		}
		if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
			try {
				desktop.browse(uri);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * Opens a webpage of specified URL.
	 * @param url URL of webpage
	 */
	public static void openWebpage(final URL url) {
		try {
			openWebpage(url.toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Logs the user out.
	 */
	public final void logout() {
		twitter.setOAuthAccessToken(null);
		favorites.saveFavorites();
		accessToken = null;
	}

	/**
	 * sets a new status for the current user.
	 * 
	 * @param post
	 *            - String containing text of updated status
	 * @throws TwitterException 
	 */
	public final void updateStatus(final String post) throws TwitterException {

		int count = 0;
		int start = 0;
		int end = 140;

		String[] splitMessage = new String[30];

		System.out.println("Message Length: " + post.length());

		if (post.length() > 140) {
			while (end < post.length()) {
				splitMessage[count] = post.substring(start, end);
				count++;
				start += 140;
				end += 140;
				System.out.println("Start: " + start);
				System.out.println("End: " + end);
			}

			end -= 140;
			splitMessage[count] = post.substring(end, post.length());
		}

		count = 0;

		while (splitMessage[count] != null) {
			twitter.updateStatus(splitMessage[count]);
			count++;
			System.out.println("Test2");
		}
		if (splitMessage[0] == null) {
			twitter.updateStatus(post);
			System.out.println("Test3");
		}
	}

	/**
	 * Gets he column length.
	 * 
	 * @return columnName.length();
	 */
	@Override
	public final int getColumnCount() {
		return columnNames.length;
	}

	/**
	 * gets the rowCount.
	 * 
	 * @return myTweets.size();
	 */
	@Override
	public final int getRowCount() {
		return myTweets.size();
	}
	/**
	 * Gets profile image of current user.
	 * @return profile image icon
	 * @throws TwitterException 
	 * @throws MalformedURLException 
	 */
	public final ImageIcon getProfileImage() 
	throws TwitterException, MalformedURLException {

		URL url = new URL(twitter.showUser(twitter.getId())
				.getBiggerProfileImageURL());
		ImageIcon icon = new ImageIcon(url);

		return icon;
	}

	@Override
	public void actionPerformed(final ActionEvent arg0) {

	}

	@Override
	public void hyperlinkUpdate(final HyperlinkEvent arg0) {

	}
	/**
	 * Get real name of a user.
	 * @param userId user id of user
	 * @return real name of suer
	 * @throws TwitterException 
	 */
	public final String getRealName(final long userId) throws TwitterException {
		return twitter.showUser(userId).getName();
	}
	/**
	 * Get screen name of current user.
	 * @return screen name of current user
	 */
	public final String getScreenName() {
		return user.getScreenName();
	}
	/**
	 * Get tweets of current user.
	 * @return tweets of current user
	 */
	public final int getTweets() {
		return user.getStatusesCount();
	}
	/**
	 * Get follower count of current user.
	 * @return follower count of current user
	 */
	public final int getFollowersCount() {
		return user.getFollowersCount();
	}
	/**
	 * Get following count of current user.
	 * @return following count of current user
	 */
	public final int getFollowingCount() {
		return user.getFriendsCount();
	}
	/**
	 * Get rate limit from twitter.
	 * @return rate limit
	 */
	public final int getRateLimit() {
		return user.getRateLimitStatus().getLimit();
	}
	/**
	 * Get remaining rate limit from twitter.
	 * @return remaining rate limit
	 */
	public final int getLimitRemaining() {
		return user.getRateLimitStatus().getRemaining();
	}
	/**
	 * Remove a favorite user of specific index.
	 * @param index index of user to remove
	 */
	public final void removeFavoriteUser(final int index) {
		try {

			fireTableDataChanged();
			return;
		} catch (IndexOutOfBoundsException e) {
			JOptionPane.showMessageDialog(null, "Invalid Selection",
					"Invalid action", JOptionPane.ERROR_MESSAGE);
		}
	}
	/**
	 * Add favorite user of specified index in myTweets.
	 * @param index index of user to add
	 */
	public final void addFavoriteUser(final int index) {
		try {
			String name = myTweets.get(index).getDisplayName();
			favorites.addFavoriteUser(name);
			fireTableDataChanged();
			return;
		} catch (IndexOutOfBoundsException e) {
			JOptionPane.showMessageDialog(null, "Invalid Selection",
					"Invalid action", JOptionPane.ERROR_MESSAGE);
		}
	}
	/**
	 * Get list of favorite tweets.
	 * @return list of favorite tweets
	 * @throws TwitterException 
	 */ 
	public final List<Status> getfavoriteTweets() throws TwitterException {
		return twitter.getFavorites();
	}
	/**
	 * Destroy a favorite tweet.
	 * @param statusId status id to destroy
	 * @throws TwitterException 
	 */
	public final void destoryStatus(final long statusId)
	throws TwitterException {
		// twitter.destroyStatus(statusId);
		twitter.destroyFavorite(statusId);
	}
	/**
	 * Get account settings of current user.
	 * @return account settings
	 * @throws TwitterException 
	 */
	public final AccountSettings getAccountSettings() throws TwitterException {
		return twitter.getAccountSettings();
	}
	/**
	 * List of current users tweets.
	 * @return list of users tweets
	 * @throws TwitterException 
	 */
	public final List<Status> getMyTweets() throws TwitterException {
		Paging paging = new Paging(1, 200);
		List<Status> statuses = twitter.getUserTimeline(paging);
		return statuses;
	}
	/**
	 * Creates a favorite tweet.
	 * @param statusIds status id of tweet to create a favorite of
	 * @throws TwitterException 
	 */
	public final void favoriteTweet(final long statusIds) 
	throws TwitterException {
		twitter.createFavorite(statusIds);
	}
	/**
	 * Gets friends list of a user.
	 * @param userId user to get list of
	 * @return friends list of user
	 * @throws TwitterException 
	 */
	public final List<User> getFriendsList(final long userId) 
	throws TwitterException {
		return twitter.getFriendsList(userId, -1);
	}
	/**
	 * Remove a friend.
	 * @param userId user id of friend
	 * @throws TwitterException 
	 */
	public final void removeFriend(final long userId) throws TwitterException {
		twitter.destroyFriendship(userId);

	}
	/**
	 * Get name of friend.
	 * @param userId user id of friend
	 * @return name of friend
	 * @throws TwitterException 
	 */
	public final String getfriendsName(final long userId)
	throws TwitterException {
		return twitter.showUser(userId).getName();
	}
	/**
	 * Image icon of friend profile.
	 * @param userId user id of friend
	 * @return Image Icon of friend profile
	 * @throws TwitterException 
	 * @throws MalformedURLException 
	 */
	public final ImageIcon getfriendProfileImage(final long userId)
			throws TwitterException, MalformedURLException {
		URL url = new URL(twitter.showUser(userId).getBiggerProfileImageURL());
		ImageIcon icon = new ImageIcon(url);

		return icon;
	}
	/**
	 * Twitter timeline of a friend.
	 * @param userIds user id of friend
	 * @return twitter timeline of friend
	 * @throws TwitterException 
	 */
	public final List<Status> getfriendsTimeline(final long userIds)
			throws TwitterException {
		Paging paging = new Paging(1, 50);
		return twitter.getUserTimeline(userIds, paging);
	}
	/**
	 * Favorite tweets of friend.
	 * @param userId user id of friend
	 * @return Favorite tweets of friend
	 * @throws TwitterException 
	 */
	public final List<Status> getFriendsFavoriteTweets(final long userId)
			throws TwitterException {
		Paging paging = new Paging(1, 50);
		return twitter.getFavorites(userId, paging);
	}
	/**
	 * Get current user id.
	 * @return current user id
	 * @throws TwitterException 
	 */
	public final long getUserId()throws  TwitterException {
		return user.getId();
	}
	/**
	 * Create a friendship.
	 * @param userId user id of new friend
	 * @throws TwitterException 
	 */
	public final void createFriendship(final long userId)
	throws TwitterException {
		twitter.createFriendship(userId);
	}
	/**
	 * Send a direct message to a user.
	 * @param userId user id to send message to
	 * @param message message to send to the user
	 * @throws TwitterException 
	 */
	public final void sendDirectMessage(final long userId, final String message)
			throws TwitterException {
		int count = 0;
		int start = 0;
		int end = 140;

		String[] splitMessage = new String[30];

		System.out.println("Message Length: " + message.length());

		if (message.length() > 140) {
			while (end < message.length()) {
				splitMessage[count] = message.substring(start, end);
				count++;
				start += 140;
				end += 140;
				System.out.println("Start: " + start);
				System.out.println("End: " + end);
			}

			end -= 140;
			splitMessage[count] = message.substring(end, message.length());
		}

		count = 0;

		while (splitMessage[count] != null) {
			twitter.sendDirectMessage(userId, splitMessage[count]);
			count++;
			System.out.println("Test2");
		}
		if (splitMessage[0] == null) {
			twitter.sendDirectMessage(userId, message);
			System.out.println("Test3");
		}
	}
	/**
	 * Get list of trends locations.
	 * @return list of trends locations
	 */
	private Location[] getTrendsLocations() {
		ResponseList<Location> locations = null;
		try {
			locations = twitter.getAvailableTrends();
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		trendsLocations = new Location[locations.size()];
		for (int i = 0; i < locations.size(); i++) {
			trendsLocations[i] = locations.get(i);
		}

		return trendsLocations;
	}
	/**
	 * Get String array of trend location names.
	 * @return trends location names
	 */
	public final String[] getTrendsLocationNames() {
		this.getTrendsLocations();
		String[] locationnames = new String[trendsLocations.length];
		for (int i = 0; i < trendsLocations.length; i++) {
			locationnames[i] = trendsLocations[i].getName();
		}
		return locationnames;
	}
	/**
	 * The Trends a a specified location.
	 * @param locationName name of location to get trends of
	 * @return Trends of a location
	 */
	public final Trends getLocationTrends(final String locationName) {
		for (int i = 0; i < trendsLocations.length; i++) {
			if (locationName.equals(trendsLocations[i].getName())) {
				try {
					return twitter
							.getPlaceTrends(trendsLocations[i].getWoeid());
				} catch (TwitterException e) {
					e.printStackTrace(); // ////////////////////////TO-DO ERROR
											// HANDLING
				}
			}
		}
		return null;
	}
	/**
	 * Get the direct messages of the current user.
	 * @return list of current user direct messages
	 * @throws TwitterException 
	 */
	public final List<DirectMessage> getDirectMessages()
	throws TwitterException {
		Paging paging = new Paging(1, 50);

		return twitter.getDirectMessages(paging);
	}
	/**
	 * Delete a message of the current user's.
	 * @param messageId message id of message to delete
	 * @throws TwitterException 
	 */
	public final void deleteMessage(final long messageId)
	throws TwitterException {
		twitter.destroyDirectMessage(messageId);
	}
}