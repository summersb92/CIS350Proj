package model;

import java.util.Comparator;

/**
 * FollowersCountComparator compares two followers and return
 * the one with the lowest amoutn first
 * @author Ben
 *
 */
public class FollowersCountComparator implements
	Comparator<MyTweet> {

	public int compare(MyTweet b1, MyTweet b2) {
		return (int) (b1.getFollowersCount()-
				(b2.getFollowersCount()));
	}
}
