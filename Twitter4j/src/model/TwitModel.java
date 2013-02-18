package model;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import twitter4j.Status;
import twitter4j.User;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**.
 * TwitModel.class
 * 
 * Does all of the calculating for the TwitterLite
 * @author Ben
 */
@SuppressWarnings("serial")
public class TwitModel extends AbstractTableModel {
	/** Stores the current date.*/
	private Date date;
	/** Stores the username.*/
	private String loginName;
	/** Stores a user's actual name. */
	private String displayName;
	/** Stores the number of followers. */
	private int followers;
	/** Stores the number of friends. */
	private int friends;
	/** Stores the text of a Status. */
	private String text;
	/** Stores a Tweet sent/received. */
	private Tweet t;
	/** Stores an ArrayList of tweets. */
	private ArrayList<MyTweet> myTweets;
	/** Main twitter variable holds a twitter instance. */
	private Twitter twitter;
	/** String array contains names of column headings. */
	private String[] columnNames = {"Date", "Login Name",
			"Display Name", "Freinds", "Followers"};
	/**
	 * The Constructor for TwitModel().
	 */
	@SuppressWarnings("static-access")
	public TwitModel()  { 
		myTweets = new ArrayList<MyTweet>();
		//wordCounter = new ArrayList<Word>();
		twitter = new TwitterFactory().getSingleton();
	}
	/**
	 * Gets the values to be displayed within the Table.
	 * @return val
	 * @param row
	 * 			specified row of the table
	 * @param col
	 * 			specified column of the table
	 */
	public final Object getValueAt(final int row, final int col) {
		Object val = null;
		switch(col) {
		case 0:
			val = myTweets.get(row).getCreatedAt(); break;
		case 1:
			val = myTweets.get(row).getLoginName(); break;
		case 2:
			val = myTweets.get(row).getDisplayName();
			break;
		case 3:
			val = myTweets.get(row).getFriendsCount();
			break;
		case 4:
			val = myTweets.get(row).getFollowersCount();
			break;
		default:
		}
		return val;
	}
	
	/**
	 * Creates the column Names.
	 * @param column
	 * 					specific column to get the name of
	 * @return String containing the column name
	 */
	@Override
	public final String getColumnName(final int column) {
		return columnNames[column];
	}
	/**
	 * Adds a tweet to the table from the ArrayList.
	 * @param tweet - arrayList being passed into the Table
	 */
	public final void add(final MyTweet tweet) {
		if (tweet != null) { 
			myTweets.add(tweet);
			fireTableRowsInserted(myTweets.size() - 1,
					myTweets.size());
		}
	}
	/**
	 * Removes a Tweets from the array List.
	 * @param index - Position of the value
	 */
	public final void remove(final int index) {
		try {
			myTweets.remove(index);
			fireTableRowsDeleted(index, index);
			return;
		} catch (IndexOutOfBoundsException e) { 
			JOptionPane.showMessageDialog(null , 
				"Invalid Selection",
				"Invalid action",
				JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Gets the Status of the specified userName.
	 * @param userName - user that is being searched for
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
	 * @return List of Timeline Statuses
	 * @throws TwitterException 
	 */
	public final List<Status> retriveTimeline() throws TwitterException {
		twitter = new TwitterFactory().getSingleton();
	    List<Status> statuses = twitter.getHomeTimeline();
	    System.out.println("Showing home timeline.");
	    for (Status status : statuses) {
	        System.out.println(status.getUser().getName() + ":" 
	        								+ status.getText());
	        arrayListGenerator(status);
	    }
		return statuses;
	}
	/**
	 * Gets the size of an Array List.
	 * @return - myTweets.size() - size of myTweets
	 */
	public final int getArrayListSize() {
		return myTweets.size();
	}
	/**
	 * Creates the ArrayList Generator.
	 * @param status - the current status of a twitter user
	 */
	public final void arrayListGenerator(final Status status) {
		date = status.getCreatedAt();
		loginName = status.getUser().getScreenName();
		displayName = status.getUser().getName();
		followers = status.getUser().getFollowersCount();
		friends = status.getUser().getFriendsCount();
		text = status.getText();
		t = new Tweet(date , loginName , displayName , 
				followers , friends , text);
		add(t);
	}
	/**
	 * Retrieves the status of the selected index.
	 * @param index - index that is selected
	 * @return - the text of that index.
	 */
	public final String retrieveDisplayStatus(final int index) {
		return myTweets.get(index).getText();	
	}
	/**
	 * getWordSearch gets what is being
	 * searched and produces an arrayList.
	 * @param keyWord - word being searched
	 */
	public void getWordSearch(final String keyWord) {

	}
	/**
	 * Gets the phrase that is being searched.
	 * @param keyWord - keyword 
	 * @param phrase - phrase
	 */
	public void getPhraseSearch(final String keyWord ,
			final String phrase) {

	}
	/**
	 * gets the statues to a user with a specific input.
	 * @param keyWord
	 * @param phrase
	 * @param toUser
	 */
//	public void getToUserSearch(final String keyWord ,
//			final String phrase , final String toUser) {
//
//	}
	/**
	 * get the status from a user.
	 * @param keyWord - 
	 * @param phrase
	 * @param fromUser
	 */
//	public void getFromUserSearch(String keyWord ,
//			String phrase , String fromUser) {
//
//	}
	/**
	 * gets a statuses that meet all parameters.
	 * @param keyWord
	 * @param phrase
	 * @param toUser
	 * @param fromUser
	 */
//	public void getAllSearch(String keyWord ,
//			String phrase , String toUser , String fromUser) {
//
//	}
	/**
	 * gets keywords from a particular user.
	 * @param keyWord
	 * @param fromUser
	 */
//	public void getKeyFromSearch(String keyWord ,
//			String fromUser) {
//
//	}
	/**
	 * Gets keywords to a specified user.
	 * @param keyWord
	 * @param toUser
	 */
//	public void getKeyToSearch(String keyWord ,
//			String toUser) {
//
//	}
	/**
	 * Authenticates a user.
	 * @param userName
	 * @param userKey
	 * @param userSecret
	 * @param token
	 * @param tokenSecret
	 */
//	public void authentication(String userName , 
//			String userKey , String userSecret , 
//			String token , String tokenSecret) {
//
//	}
	/**
	 * Destroys the status of the current user
	 */
//	public void destoryStatus() {
//
//	}
	/**
	 * sets a new status for the current user.
	 * @param post - String containing text of updated status
	 * @throws TwitterException 
	 */
	public final void updateStatus(final String post) throws TwitterException {
		
		twitter.updateStatus(post);
	}
	/**
	 * Gets he column length.
	 * @return columnName.length();
	 */
	@Override
	public final int getColumnCount() {
		return columnNames.length;
	}
	/**
	 * gets the rowCount.
	 * @return myTweets.size();
	 */
	@Override
	public final int getRowCount() {
		return myTweets.size();
	}
	/**
	 * loads a string a splits it into a bunch of tokens.
	 * @return inputwords
	 */
//	public List<String> loadString(){
//		return null;
//	}
	/**
	 * Gets the top trending list.
	 * @return twitter.getTrends();
	 */
//	public Object topTrendingList() {
//		return null;
//	} 
}
