package model;

import java.util.*;

/**
 * DateComparator compares two dates
 * 
 * @author Ben
 */
public class DateComparator implements Comparator<MyTweet> {
	@Override
	/**
	 * compares two instances of MyTweet and returns which
	 * one was created earlier
	 * 
	 * @param MyTweet
	 * 
	 * @return t2.getCreatedAt().
	 * 		compareTo(t1.getCreatedAt())
	 */
	public int compare(MyTweet t1, MyTweet t2) {
		return t2.getCreatedAt().
			compareTo(t1.getCreatedAt());
	}
}
