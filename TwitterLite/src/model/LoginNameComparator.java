package model;

import java.util.*;

/**
 * LoginNameComparator compares two login names and returns
 * the one that comes first alphabetically
 * @author Ben
 *
 */
public class LoginNameComparator implements
	Comparator<MyTweet> {

	@Override
	public int compare(MyTweet t1, MyTweet t2) {
		return t1.getLoginName().
			compareTo(t2.getLoginName());
	}
}
