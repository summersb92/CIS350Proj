package engine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;

import twitter4j.Status;
import twitter4j.TwitterException;

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
	private String post;
	
	/**
	 * Constructs the Twitter Engine
	 */
	public TwitterEngine(){
		model = new TwitModel();
	}
	/**
	 * gets the model
	 * 
	 * @return model
	 */
	public TwitModel getModel() {
		return model;
	}
	/**
	 * gets a users status
	 * 
	 * @param userName - the user being searched
	 */
	public void getStatus(String userName){
		model.retrieveStatus(userName);
	}
	/**
	 * gets a users timeline
	 * @return 
	 * 
	 * @throws TwitterException 
	 */
	public List<Status> getTimeline() throws TwitterException {
		return model.retriveTimeline();
	}
	/**
	 * addTweets creates a twitter Status in the table
	 * 
	 * @param date - createdAt
	 * @param loginName - user
	 * @param displayName - name that is shown
	 * @param friends - how many friends
	 * @param followers - how many followers
	 * @param text - status text
	 */
	public void addTweet(Date date, String loginName,
			String displayName, int friends,
			int followers, String text){	
		Tweet t = new Tweet(date ,loginName, displayName, 
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
	 * @throws Exception 
	 */
	public void login() throws Exception {
			
		model.Athenticate();
	}
	
	public void logout(){
		
		model.logout();
	}
	/**
	 * gets the current array lists size
	 * 
	 * @return - checks an array lists size
	 */
	public int getArrayListSize(){
		return model.getArrayListSize();
	}
	/**
	 * gets the text for a selected users status
	 * 
	 * @param index - the current spot on a table
	 *  highlighted
	 * @return - the status of that highlighted table row
	 */
	public String getDisplayStatis(int index) {
		this.index = index;
		return model.retriveDisplayStatis(index);
	}
	
	/**
	 * Searches for a particular keyword
	 * 
	 * @param keyWord - keyword for the search
	 */
	public void getWordSearch(String keyWord) {
		model.getWordSearch(keyWord);
	}
	/**
	 * Searches for a phrase
	 * 
	 * @param keyWord - the keyword
	 * @param phrase - the phrase
	 */
	public void getPhraseSearch(String keyWord,
			String phrase){
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
	 * removes a tweet from the table
	 */
	public void deleteTweet() {
		model.remove(index);
	}
	/**
	 * removes current status
	 */
	//public void deleteStatus() {
		//model.destoryStatus();
	//}
	/**
	 * Posts a new status
	 * 
	 * @param post2 - 140 character or less post
	 * @return 
	 * @return 
	 * @throws TwitterException 
	 */
	public void postStatus(String post) throws TwitterException {
		model.updateStatus(post);
	}
	/**
	 * Gathers what is trending on Twitter
	 * 
	 * @return model.topTrendingList();
	 * @throws TwitterException 
	 * @throws IllegalStateException 
	 */
	//public Object topTrendingList() {
		//return model.topTrendingList();	
	//}
	
	public ImageIcon getProfileImage() throws IllegalStateException, TwitterException {
		return model.getProfileImage();
	}
}
