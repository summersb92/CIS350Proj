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
 * TwitterEngine.class
 * 
 * Intermediary between the GUI and model
 * 
 * @author Ben
 */
public class TwitterEngine {

	private TwitModel model;
	private int index;

	// private String post;

	/**
	 * Constructs the Twitter Engine
	 * 
	 * @throws TwitterException
	 */
	public TwitterEngine() throws TwitterException {
		model = new TwitModel();
	}

	/**
	 * gets the model
	 * 
	 * @return model
	 */
	public final TwitModel getModel() {
		return model;
	}

	/**
	 * gets a users status
	 * 
	 * @param userName
	 *            - the user being searched
	 */
	public final void getStatus(final String userName) {
		model.retrieveStatus(userName);
	}

	/**
	 * gets a users timeline
	 * 
	 * @return
	 * 
	 * @throws TwitterException
	 */
	public final List<Status> getTimeline() throws TwitterException {
		return model.retriveTimeline();
	}

	/**
	 * addTweets creates a twitter Status in the table
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
	 */
	public final void addTweet(final Date date, final String loginName,
			final String displayName, final int friends, final int followers,
			final String text, final boolean fave) {
		Tweet t = new Tweet(date, loginName, displayName, friends, followers,
				text, fave);
		model.add(t);
	}

	/**
	 * sends the inputs for a user to authenticate their current session
	 * 
	 * @param userName
	 *            - user at the screen
	 * @param consumerKey
	 *            - their consumer key
	 * @param consumerSecret
	 *            - their consumer secret key
	 * @param accessToken
	 *            - their access token
	 * @param accessTokenS
	 *            - their access token secret
	 * @throws Exception
	 */
	public final void login() throws Exception {

		model.authenticate();
	}

	public final void logout() {

		model.logout();
	}

	/**
	 * gets the current array lists size
	 * 
	 * @return - checks an array lists size
	 */
	public final int getArrayListSize() {
		return model.getArrayListSize();
	}

	/**
	 * gets the text for a selected users status
	 * 
	 * @param index
	 *            - the current spot on a table highlighted
	 * @return - the status of that highlighted table row
	 */
	public final String getDisplayStatis(final int idex) {
		this.index = idex;
		return model.retriveDisplayStatis(index);
	}

	/**
	 * Searches for a particular keyword
	 * 
	 * @param keyWord
	 *            - keyword for the search
	 */
	public final void getWordSearch(final String keyWord) {
		model.getWordSearch(keyWord);
	}

	/**
	 * Searches for a phrase
	 * 
	 * @param keyWord
	 *            - the keyword
	 * @param phrase
	 *            - the phrase
	 */
	public final void getPhraseSearch(final String keyWord, final String phrase) {
		model.getPhraseSearch(keyWord, phrase);
	}

	/**
	 * removes a tweet from the table
	 */
	public final void deleteTweet() {
		model.remove(index);
	}

	/**
	 * removes current status
	 * 
	 * @throws TwitterException
	 */
	public void deleteStatus(long statusId) throws TwitterException {
		model.destoryStatus(statusId);
	}

	/**
	 * Posts a new status
	 * 
	 * @param post2
	 *            - 140 character or less post
	 * @return
	 * @return
	 * @throws TwitterException
	 */
	public final void postStatus(final String post) throws TwitterException {
		model.updateStatus(post);
	}

	public final ImageIcon getProfileImage() throws IllegalStateException,
			TwitterException, MalformedURLException {
		return model.getProfileImage();
	}

	public final String getRealName(long userId) throws TwitterException {
		return model.getRealName(userId);
	}

	public final String getScreenName() {
		return model.getScreenName();
	}

	public final int getTweets() {
		return model.getTweets();
	}

	public final int getFollowersCount() {
		return model.getFollowersCount();
	}

	public final int getFollowingCount() {
		return model.getFollowingCount();
	}

	public final int getRateLimit() {
		return model.getRateLimit();
	}

	public final int getRateLimitRemaining() {
		return model.getLimitRemaining();
	}

	public final boolean getFavoriteStatus() {
		return model.getFavoriteStatus();
	}

	public final void addFavorite() {
		model.addFavoriteUser(index);
	}

	public final void removeFavorite() {
		model.removeFavoriteUser(index);
	}

	public List<Status> getFavoriteTweets() throws TwitterException {
		return model.getfavoriteTweets();
	}

	public AccountSettings getAccountSettings() throws TwitterException {
		return model.getAccountSettings();
	}

	public List<Status> getMyTweets() throws TwitterException {
		return model.getMyTweets();
	}

	public void favoriteTweet(long statusIds) throws NumberFormatException,
			TwitterException {
		model.favoriteTweet(statusIds);
	}

	public List<User> getFriendsList(long userId) throws TwitterException {
		return model.getFriendsList(userId);
	}

	public void removefriend(long userId) throws TwitterException {
		model.removeFriend(userId);
	}

	public String getfriendsName(long UserId) throws TwitterException {
		return model.getfriendsName(UserId);
	}

	public ImageIcon getfriendProfileImage(long UserId)
			throws MalformedURLException, TwitterException {
		return model.getfriendProfileImage(UserId);
	}

	public List<Status> getfriendsTimeline(long userIds)
			throws TwitterException {
		return model.getfriendsTimeline(userIds);
	}

	public List<Status> getFriendsFavoriteTweets(long userId)
			throws TwitterException {
		return model.getFriendsFavoriteTweets(userId);
	}

	public Long getuserid() throws IllegalStateException, TwitterException {
		return model.getuserId();
	}

	public void CreateFriendship(long userId) throws TwitterException {
		model.createFriendship(userId);
	}

	public void sendDirectMessage(long userId, String message)
			throws TwitterException {
		model.sendDirectMessage(userId, message);
	}

	public final String[] getTrendsLocations() {
		return model.getTrendsLocationNames();
	}

	public final Trends getPlaceTrends(String location) {
		return model.getLocationTrends(location);
	}

	public List<DirectMessage> getDirectMessages() throws TwitterException {
		return model.getDirectMessages();
	}

	public void deleteMessage(long MessageId) throws TwitterException {
		model.deleteMessage(MessageId);
	}

}
