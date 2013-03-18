package model;


import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.io.BufferedReader;
import java.io.File;
//import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
//import java.io.InputStreamReader;
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
//import javax.swing.JEditorPane;
//import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
//import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.table.AbstractTableModel;
//import javax.swing.table.TableModel;

import twitter4j.Status;
import twitter4j.User;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

/**.
 * TwitModel.class
 * 
 * Does all of the calculating for the TwitterLite
 * @author Ben
 */
@SuppressWarnings("serial")
public class TwitModel extends AbstractTableModel 
	implements HyperlinkListener, ActionListener {
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
	private String[] columnNames = {"Date", "Login Name",
			"Display Name", "Freinds", "Followers", "Favorite"};
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
	 * The Constructor for TwitModel().
	 * @throws TwitterException 
	 */

	public TwitModel() throws TwitterException  { 
		favorites = new FavoritesUtility();
		
		myTweets = new ArrayList<MyTweet>();
		//wordCounter = new ArrayList<Word>();
	
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
		case 5:
			if(getFavoriteStatus()==false){
				val = "false";
			}else{
				val = "true";
			}
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
		
	    List<Status> statuses = twitter.getHomeTimeline();
	    
	    for (Status status : statuses) {
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
		fave = getFavorite(status.getUser().getName());
		//fave = getFavorite(displayName);
		t = new Tweet(date , loginName , displayName , 
				followers , friends , text, fave);
		add(t);
	}

	/**
	 * Retrieves the status of the selected index.
	 * @param index - index that is selected
	 * @return - the text of that index.
	 */
	public final String retriveDisplayStatis(final int index) {
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
	
	public final void addFavorite(final String dsplayName) {
		favorites.addFavoriteUser(dsplayName);
	}
	public final void delteFavorite(final String dsplayName) {
		favorites.removeFavorite(dsplayName);
	}
	public final void clearFavoriteList() {
		favorites.clearList();
	}
	public final void getFStringList() {
		favorites.getStringList();
	}
	public final void saveFavorites(final String username) {
		favorites.saveFavorites(username);
	}
	public final void loadFavorites(final String username) {
		favorites.loadFavorites(username);
	}	
	private boolean getFavorite(final String username) {
	//	favorites.
		return false;
	}
	public boolean getFavoriteStatus() {
		// TODO Auto-generated method stub
		return false;
	}
	/**
	 * Gets the number of people a particular user has following them.
	 * @param user - The user to get to get the number of followers for
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
	 * @param userName
	 * @param userKey
	 * @param userSecret
	 * @param token
	 * @param tokenSecret
	 * @throws TwitterException 
	 * @throws IllegalStateException 
	 * @throws IOException 
	 */
	public final void authenticate() 
	throws IllegalStateException, TwitterException, IOException {
				 
		cb = new ConfigurationBuilder();
	        
	    cb.setDebugEnabled(true)
	    .setOAuthConsumerKey("UP8vf0xlwUkPHvikkEBXQ")
	    .setOAuthConsumerSecret("62H0idR3HypsRitEUQI3j2ugqTINXybjeBSLr4QH78");
	        
	    tf = new TwitterFactory(cb.build());
	    twitter = tf.getInstance();
	        
	    String username = JOptionPane.showInputDialog(frame, "User Name");
	        
	    File file = new File("loginInformation.txt");
	        
	    Scanner scanner = new Scanner(file);
	    
		while (scanner.hasNextLine()) {
				
			String line = scanner.nextLine();
				
			String[] s = line.split(", ");
			//s[1].trim();
			//s[2].trim();
				
			if (s[0].equals(username)) {
				accessToken = new AccessToken(s[1], s[2]);
				twitter.setOAuthAccessToken(accessToken);
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
// this will throw IllegalStateException if access token is already available
	            requestToken = twitter.getOAuthRequestToken();
	 
	            accessToken = null;
	 
	                
	                 
	            while (null == accessToken) {
	                	
	            	URL url = new URL(requestToken.getAuthenticationURL());
	                	
	                openWebpage(url);

	                String pin = JOptionPane.showInputDialog(frame, 
	                	"Enter the PIN(if available) and hit ok.");
	                
	                try {
	                    if (pin.length() > 0) {
	                    	accessToken = twitter
	                    		.getOAuthAccessToken(requestToken, pin);
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
	                        	
	                  // System.out.println("Unable to get the access token.");
	                        } else {
	                            te.printStackTrace();
	                        }
	                    }
	            }
	                 
	        } catch (IllegalStateException ie) {
	    // access token is already available, or consumer key/secret is not set.
	        	if (!twitter.getAuthorization().isEnabled()) {
	                System.out.println("OAuth consumer key/secret is not set.");
	                System.exit(-1);
	            }
	        }
	             
	    	} catch (NullPointerException np) {
	        	System.out.println("Exiting");
	        	System.exit(0);
	        }
	        
	        //user = twitter.showUser(twitter.getId());
	        
	    	PrintWriter out = new PrintWriter(
	    		new FileWriter("./loginInformation.txt", true));
	    	String saveFile = "";

	    	user = twitter.showUser(twitter.getId());
			
			
	    	saveFile += user.getName() + ", " 
	    		+ accessToken.getToken() + ", " + accessToken.getTokenSecret();
			scanner.close();
	    	out.println();
	    	out.print(saveFile);
	    	out.close();
		}
	}
		
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

	public static void openWebpage(final URL url) {
	    try {
	        openWebpage(url.toURI());
	    } catch (URISyntaxException e) {
	        e.printStackTrace();
	    }
	}
	
	public final void logout() {
		twitter.setOAuthAccessToken(null);
		accessToken = null;
	}
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

	public final ImageIcon getProfileImage() 
		throws IllegalStateException, TwitterException, MalformedURLException {
			
		user = twitter.showUser(twitter.getId());
		URL url = new URL(user.getBiggerProfileImageURL());
		ImageIcon icon = new ImageIcon(url);
		
		return icon;
	}
	@Override
	public void actionPerformed(final ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void hyperlinkUpdate(final HyperlinkEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public final String getRealName() {
		return user.getName();
	}
	public final String getScreenName() {
		return user.getScreenName();
	}
	public final int getTweets() {
		return user.getStatusesCount();
	}
	public final int getFollowersCount() {
		return user.getFollowersCount();
	}
	public final int getFollowingCount() {
		return user.getFriendsCount();
	}
	public final int getRateLimit() {
		return user.getRateLimitStatus().getLimit();
	}
	public final int getLimitRemaining() {
		return user.getRateLimitStatus().getRemaining();
	}
	public void removeFavoriteUser(final int index) {
		try{
			
			fireTableDataChanged();
			return;
		}catch(IndexOutOfBoundsException e){
			JOptionPane.showMessageDialog(null , 
				"Invalid Selection",
				"Invalid action",
				JOptionPane.ERROR_MESSAGE);
		}
	}
	public void addFavoriteUser(final int index) {
		try {
		//	myTweets.
			//myTweets.remove(index);
			fireTableDataChanged();
			return;
		} catch (IndexOutOfBoundsException e) { 
			JOptionPane.showMessageDialog(null , 
				"Invalid Selection",
				"Invalid action",
				JOptionPane.ERROR_MESSAGE);
		}
	}

	
}


	

