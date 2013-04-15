package engine;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;

import twitter4j.AccountSettings;
import twitter4j.DirectMessage;
import twitter4j.Status;
import twitter4j.Trends;
import twitter4j.TwitterException;
import twitter4j.User;

import model.Tweet;
import model.TwitModel;

/**
 * 
 * Intermediary between the GUI and model.
 * 
 * @author Ben
 */
public class TwitterEngine {

	/**
	 * TwitModel Instance.
	 */
	private TwitModel model;
	/**
	 * Current index of gui display.
	 */
	private int index;

	// private String post;

	/**
	 * Constructs the Twitter Engine.
	 * 
	 * @throws TwitterException 
	 */
	public TwitterEngine() throws TwitterException {
		model = new TwitModel();
	}

	/**
	 * Returns the model.
	 * 
	 * @return model
	 */
	public final TwitModel getModel() {
		return model;
	}

	/**
	 * Returns a users status.
	 * 
	 * @param userName
	 *            - the user being searched
	 */
	public final void getStatus(final String userName) {
		model.retrieveStatus(userName);
	}

	/**
	 * Returns a users timeline.
	 * 
	 * @return user timeline
	 * 
	 * @throws TwitterException 
	 */
	public final List<Status> getTimeline() throws TwitterException {
		return model.retriveTimeline();
	}

	/**
	 * addTweets creates a twitter Status in the table.
	 * 
	 * @param date
	 *            - createdAt
	 * @param loginName
	 *            - user
	 * @param displayName
	 *            - name that is shown
	 * @param friends
	 *            - how many friends
	 * @param followers
	 *            - how many followers
	 * @param text
	 *            - status text
	 * @param fave 
	 * 			  - is a favorite or not
	 */
	public final void addTweet(final Date date, final String loginName,
			final String displayName, final int friends, final int followers,
			final String text, final boolean fave) {
		Tweet t = new Tweet(date, loginName, displayName, friends, followers,
				text, fave);
		model.add(t);
	}

	/**
	 * Calls the authenticate method of model to login.
	 * @throws Exception 
	 */
	public final void login() throws Exception {

		model.authenticate();
	}
	
	/**
	 * Calls logout method of model to logout.
	 */
	public final void logout() {

		model.logout();
	}

	/**
	 * gets the current array lists size.
	 * 
	 * @return - checks an array lists size
	 */
	public final int getArrayListSize() {
		return model.getArrayListSize();
	}

	/**
	 * gets the text for a selected users status.
	 * 
	 * @param idex
	 *            - the current spot on a table highlighted
	 * @return - the status of that highlighted table row
	 */
	public final String getDisplayStatis(final int idex) {
		this.index = idex;
		return model.retriveDisplayStatis(index);
	}

	/**
	 * Searches for a particular keyword.
	 * 
	 * @param keyWord
	 *            - keyword for the search
	 */
	public final void getWordSearch(final String keyWord) {
		model.getWordSearch(keyWord);
	}

	/**
	 * Searches for a phrase.
	 * 
	 * @param keyWord
	 *            - the keyword
	 * @param phrase
	 *            - the phrase
	 */
	public final void getPhraseSearch(
	final String keyWord, final String phrase) {
		model.getPhraseSearch(keyWord, phrase);
	}

	/**
	 * removes a tweet from the table.
	 */
	public final void deleteTweet() {
		model.remove(index);
	}

	/**
	 * removes current status.
	 * @param statusId id of status to delete
	 * @throws TwitterException 
	 */
	public final void deleteStatus(final long statusId)
	throws TwitterException {
		model.destoryStatus(statusId);
	}

	/**
	 * Posts a new status.
	 * 
	 * @param post
	 *            - 140 character or less post
	 * @throws TwitterException 
	 */
	public final void postStatus(final String post) throws TwitterException {
		model.updateStatus(post);
	}
	/**
	 * Gets profile image of a twitter user.
	 * @return profile image
	 * @throws TwitterException 
	 * @throws MalformedURLException 
	 */
	public final ImageIcon getProfileImage()
	throws TwitterException, MalformedURLException {
		return model.getProfileImage();
	}

	/**
	 * Real name of user.
	 * @param userId user id of user 
	 * @return name of user
	 * @throws TwitterException 
	 */
	public final String getRealName(final long userId) throws TwitterException {
		return model.getRealName(userId);
	}
	/**
	 * Returns current users screen name.
	 * @return current users screen name
	 */
	public final String getScreenName() {
		return model.getScreenName();
	}
	/**
	 * Gets current users tweets.
	 * @return current users tweets
	 */
	public final int getTweets() {
		return model.getTweets();
	}
	/**
	 * Gets current users followers count.
	 * @return current user followers count
	 */
	public final int getFollowersCount() {
		return model.getFollowersCount();
	}
	/**
	 * Gets number of users current user is following.
	 * @return number of following users.
	 */
	public final int getFollowingCount() {
		return model.getFollowingCount();
	}
	/**
	 * Get the rate limit allows by twitter.
	 * @return rate limit of twitter
	 */
	public final int getRateLimit() {
		return model.getRateLimit();
	}
	/**
	 * Get remaining rate limit from twitter.
	 * @return remaining rate limit
	 */
	public final int getRateLimitRemaining() {
		return model.getLimitRemaining();
	}
	/**
	 * Add favorite user.
	 */
	public final void addFavorite() {
		model.addFavoriteUser(index);
	}
	/**
	 * Remove favorite user.
	 */
	public final void removeFavorite() {
		model.removeFavoriteUser(index);
	}
	/**
	 * Get the favorite tweets of current user.
	 * @return List of favorite tweets.
	 * @throws TwitterException 
	 */
	public final List<Status> getFavoriteTweets() throws TwitterException {
		return model.getfavoriteTweets();
	}
	/**
	 * Account setting of current user.
	 * @return account settings
	 * @throws TwitterException 
	 */
	public final AccountSettings getAccountSettings() throws TwitterException {
		return model.getAccountSettings();
	}
	/**
	 * Get the tweet of current user.
	 * @return current tweets of user
	 * @throws TwitterException 
	 */
	public final List<Status> getMyTweets() throws TwitterException {
		return model.getMyTweets();
	}
	/**
	 * Make a tweet status a favorite tweet.
	 * @param statusIds id of favorite status/tweet
	 * @throws TwitterException 
	 */
	public final void favoriteTweet(final long statusIds) 
	throws TwitterException {
		model.favoriteTweet(statusIds);
	}
	/**
	 * Gets the friends list of a user.
	 * @param userId user id to get the friends of
	 * @return list of friends of specific user
	 * @throws TwitterException 
	 */
	public final List<User> getFriendsList(final long userId)
	throws TwitterException {
		return model.getFriendsList(userId);
	}
	/**
	 * Removes a friend of specific user id.
	 * @param userId id of friend to remove
	 * @throws TwitterException 
	 */
	public final void removefriend(final long userId) throws TwitterException {
		model.removeFriend(userId);
	}
	/**
	 * Gets the name a friend user.
	 * @param userId user id of friend user
	 * @return the name of the user
	 * @throws TwitterException 
	 */
	public final String getfriendsName(final long userId)
	throws TwitterException {
		return model.getfriendsName(userId);
	}
	/**
	 * Gets the profile image of a friend user.
	 * @param userId user id of a friend
	 * @return Profile image of friend
	 * @throws MalformedURLException 
	 * @throws TwitterException 
	 */
	public final ImageIcon getfriendProfileImage(final long userId)
			throws MalformedURLException, TwitterException {
		return model.getfriendProfileImage(userId);
	}
	/**
	 * Gets the twitter timeline of a friend.
	 * @param userId friend user id
	 * @return List of statuses representing friends timeline
	 * @throws TwitterException 
	 */
	public final List<Status> getfriendsTimeline(final long userId)
			throws TwitterException {
		return model.getfriendsTimeline(userId);
	}
	/**
	 * Gets favorite tweets of friend.
	 * @param userId user id of friend
	 * @return List of friends favorite tweets
	 * @throws TwitterException 
	 */
	public final List<Status> getFriendsFavoriteTweets(final long userId)
			throws TwitterException {
		return model.getFriendsFavoriteTweets(userId);
	}
	/**
	 * Returns user id of current user.
	 * @return user id of current user
	 * @throws TwitterException 
	 */
	public final Long getUserId() throws TwitterException {
		return model.getUserId();
	}
	/**
	 * Creates friendship of specified user.
	 * @param userId user to create friendship with
	 * @throws TwitterException 
	 */
	public final void createFriendship(final long userId)
	throws TwitterException {
		model.createFriendship(userId);
	}
	/**
	 * Sends a direct message to user.
	 * @param userId user id to send message to
	 * @param message the message to send the user
	 * @throws TwitterException 
	 */
	public final void sendDirectMessage(final long userId, final String message)
	throws TwitterException {
		model.sendDirectMessage(userId, message);
	}
	/**
	 * Gets location names of trends.
	 * @return names of trend locations
	 */
	public final String[] getTrendsLocations() {
		return model.getTrendsLocationNames();
	}
	/**
	 * Gets the trend at a specific location.
	 * @param location location to get trends of
	 * @return Trends of the location specified
	 */
	public final Trends getPlaceTrends(final String location) {
		return model.getLocationTrends(location);
	}
	/**
	 * Gets the direct messages of the current user.
	 * @return list of direct messages
	 * @throws TwitterException 
	 */
	public final List<DirectMessage> getDirectMessages() 
	throws TwitterException {
		return model.getDirectMessages();
	}
	/**
	 * Deletes a message using message id.
	 * @param messageId message id of message to delete
	 * @throws TwitterException 
	 */
	public final void deleteMessage(final long messageId)
	throws TwitterException {
		model.deleteMessage(messageId);
	}

}
