import java.util.Date;

public class Tweet implements ITweet {
	
	private java.lang.BigInteger id;
	private java.util.Date createdAt;
	private String loginName;
	private String displayName;
	private String text;
	private int friendsCount;
	private int followersCount;

//	LoginNameComparator to compare two ITweet objects on login name
//	DisplayNameComparator to compare two ITweet objects on display name
//	CreatedAtDateComparator to compare two ITweet objects on creation date
//	FriendsCountComparator to compare two ITweet objects on friends count
//	FollowersCountComparator to compare two ITweet objects on followers count

	// implement the methods in the ITweet interface
	@Override
	public java.lang.BigInteger getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getCreatedAt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLoginName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDisplayName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getFriendsCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getFollowersCount() {
		// TODO Auto-generated method stub
		return 0;
	}
}
