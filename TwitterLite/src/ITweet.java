
public interface ITweet {
	java.lang.BigInteger getId();
	
	java.util.Date getCreatedAt();
	
	String getLoginName();
	
	String getDisplayName();
	
	String getText();
	
	int getFriendsCount();
	
	int getFollowersCount();
}
