package engine;

import java.util.Date;
import java.util.List;

import twitter4j.Status;
import twitter4j.TwitterException;

import model.Tweet;
import model.TwitModel;

/**
 * Intermediary between the GUI and model.
 * 
 * @author Ben
 */
public class TwitterEngine {
	/** Main variable of the model. */
	private TwitModel model;
	/** Stores index of item clicked in table. */
	private int index;
//	/** Stores text of a tweet post. */
//	private String post;
	
	/**
	 * Constructs the Twitter Engine.
	 */
	public TwitterEngine() {
		model = new TwitModel();
	}
	/**
	 * Gets the model.
	 * 
	 * @return model - model that handles program logic
	 */
	public final TwitModel getModel() {
		return model;
	}
	/**
	 * Gets a user's status.
	 * 
	 * @param userName - the user being searched
	 */
	public final void getStatus(final String userName) {
		model.retrieveStatus(userName);
	}
	/**
	 * Gets a users timeline.
	 * @return Status timeline
	 * 
	 * @throws TwitterException 
	 */
	public final List<Status> getTimeline() throws TwitterException {
		return model.retriveTimeline();
	}
	/**
	 * addTweets creates a twitter Status in the table.
	 * 
	 * @param date - createdAt
	 * @param loginName - user
	 * @param displayName - name that is shown
	 * @param friends - how many friends
	 * @param followers - how many followers
	 * @param text - status text
	 */
	public final void addTweet(final Date date, final  String loginName,
			final String displayName, final int friends,
			final int followers, final String text) {	
		Tweet t = new Tweet(date, loginName, displayName, 
				friends, followers, text);
		model.add(t);
	}
	/**
	 * sends the inputs for a user to authenticate their
	 * current session
	 * 
	 * @param userName - user at the screen
	 * @param consumerKey - their consumer key
	 * @param consumerSecret - their consumer secret key
	 * @param accessToken - their access token
	 * @param accessTokenS - their access token secret
	 */
	/*public void login(String userName,
			String consumerKey, String consumerSecret,
			String accessToken, String accessTokenS) {
		model.authentication(userName, consumerKey,
				consumerSecret, accessToken, accessTokenS);
	}*/
	
	/**
	 * 
	 * Gets the current array lists size.
	 * 
	 * @return - checks an array lists size
	 */
	public final int getArrayListSize() {
		return model.getArrayListSize();
	}
	
	/**
	 * Gets the text for a selected users status.
	 * 
	 * @param index - the current spot on a table
	 *  highlighted
	 * @return - the status of that highlighted table row
	 */
	public final String getDisplayStatus(final int index) {
		this.index = index;
		return model.retrieveDisplayStatus(index);
	}
	
	/**
	 * Searches for a particular keyword.
	 * 
	 * @param keyWord - keyword for the search
	 */
	public final void getWordSearch(final String keyWord) {
		model.getWordSearch(keyWord);
	}
	/**
	 * Searches for a phrase.
	 * 
	 * @param keyWord - the keyword
	 * @param phrase - the phrase
	 */
	public final void getPhraseSearch(final String keyWord,
			final String phrase) {
		model.getPhraseSearch(keyWord, phrase);
	}
	/**
	 * Searches for any posts to a user
	 * 
	 * @param keyWord - keyword being searched
	 * @param phrase - phrase being searched
	 * @param toUser - user searching
	 */
	//public void getToUserSearch(String keyWord,
		//	String phrase, String toUser) {
	//	model.getToUserSearch(keyWord, phrase, toUser);
	//}
	/**
	 * Searched for posts from a user
	 * 
	 * @param keyWord - keyword being searched
	 * @param phrase - phrase being searched
	 * @param fromUser - posts from that user
	 */
	//public void getFromUserSearch(String keyWord,
		//	String phrase, String fromUser) {
		//model.getToUserSearch(keyWord, phrase, fromUser);
	//}
	/**
	 * Searched posts that are from a user to a particular
	 * user
	 * 
	 * @param keyWord - keyword being searched
	 * @param phrase - phrase being searched
	 * @param toUser - posts to that user
	 * @param fromUser - posts from that user
	 */
	//public void getAllSearch(String keyWord, String phrase,
		//	String toUser, String fromUser) {
		//model.getAllSearch(keyWord, phrase, toUser,
			//	fromUser);
	//}
	/**
	 * searched a keyword from a user
	 * 
	 * @param keyWord - keyword being searched
	 * @param fromUser - posts from that user
	 */
	//public void getKeyFromSearch(String keyWord,
		//	String fromUser) {
		//model.getKeyFromSearch(keyWord, fromUser);
	//}
	/**
	 * searches a keyword to a user
	 * 
	 * @param keyWord - keyword being searched
	 * @param toUser - post sent to that user
	 */
	//public void getKeyToSearch(String keyWord,
		//	String toUser) {
		//model.getKeyFromSearch(keyWord, toUser);
	//}
	/**
	 * Removes a tweet from the table.
	 */
	public final void deleteTweet() {
		model.remove(index);
	}
	/**
	 * removes current status
	 */
	//public void deleteStatus() {
		//model.destoryStatus();
	//}
	/**
	 * Posts a new status.
	 * 
	 * @param post - 140 character or less post
	 * @return updated model
	 * @throws TwitterException 
	 */
	public final TwitModel postStatus(final String post) 
								throws TwitterException {
		model.updateStatus(post);
		return model;
	}
	/**
	 * Gathers what is trending on Twitter
	 * 
	 * @return model.topTrendingList();
	 */
	//public Object topTrendingList() {
		//return model.topTrendingList();	
	//}
}
