package model;
import java.util.Comparator;

/**
 * FreindsCountComparator.class
 * 
 * Compares the friends count between to tweets.
 * @author Ben
 */
public class FriendsCountComparator
	implements Comparator<MyTweet> {
		@Override
		public int compare(MyTweet b1, MyTweet b2) {
			return (int) (b1.getFriendsCount()-
					(b2.getFriendsCount()));
		}
}
