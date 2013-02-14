

import winterwell.jtwitter.OAuthSignpostClient;
import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.User;

public class TwitModel {

	private static Twitter twit;
	private static User currentUser;
	
	public TwitModel(){
		
		
	}
	public static void authentication(String userName, 
			String userKey, String userSecret,
			String token, String tokenSecret){
		
		OAuthSignpostClient authentication = new 
				OAuthSignpostClient(userKey, userSecret, 
						token, tokenSecret);
		twit = new Twitter(userName, authentication);
		User currentUser = (twit.getSelf());
		System.out.println("Login Successful: " + currentUser);
	}
	public static void retriveStatis(String userName){
//		User searchedUser = twit.getUser(searchedUser)
		System.out.println(twit.getStatus(userName));
	}
}
//	//	JTWITTER_OAUTH_KEY and JTWITTER_OAUTH_SECRET
//	String userKey = "fGgcrXwnaLhwH5P2RwRpVw";
//    String userSecret = "OcS01RmdSTio2CdBrkolvHFPjLvBFehPUuZvGBv6M"; 
//    String token = "390344824-WkRCVAxqUKUjC0KWUSRtipgNAkan6QZH1RoCO2GI";
//    String tokenSecret = "Bx8APa42naHoouTUrH0Jhb37FQjXLGaoFQChKrwH5k0";
//	String userName = "BenjaminSummers1";
//    
////
////
////    	Twitter jtwit = new Twitter("bmsummers@ymail.com", client);
////    	client.authorizeDesktop();
////        // get the pin
////        String v = client.askUser("Please enter the verification PIN from Twitter");
////        client.setAuthorizationCode(v);
////        Object accessToken = client.getAccessToken();
////        System.out.println(jtwit.getStatus());
////        
//    	OAuthSignpostClient authentication = new OAuthSignpostClient
//    			(userKey, userSecret, token, tokenSecret);
//    	Twitter twit = new Twitter(userName, authentication);
//    	User currentUser = (twit.getSelf());
//    	
//    	System.out.println(currentUser.getStatus());
