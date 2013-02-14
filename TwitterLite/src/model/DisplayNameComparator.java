package model;
import java.util.*;

/**
 * Compares two displayName
 * @author Ben
 *
 */
public class DisplayNameComparator implements
	Comparator<MyTweet> {

	@Override
	/**
	 * @return compareTo(b1.getDisplayName().
		compareTo(b2.getDisplayName())
	 */
	public int compare(MyTweet b1, MyTweet b2) {
		return b1.getDisplayName().
		compareTo(b2.getDisplayName());
	}
}