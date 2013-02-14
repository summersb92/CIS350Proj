package model;

import java.util.Comparator;

/**
 * ListComparator compares two words from the Word arrayList
 * and returns the one with the highest frequency of a word
 * first
 * 
 * @author Ben
 *
 */
public class ListComparator implements Comparator<Word> {
	@Override
	public int compare(Word a1, Word a2) {
		return a2.count-(a1.count);
	}
}
