import javax.swing.JTextField;


public class TwitterEngine {

	
	public static void login(String userName,
			String consumerKey, String consumerSecret,
			String accessToken, String accessTokenS) {
		// TODO Auto-generated method stub
		
		TwitModel.authentication(userName, consumerKey,
				consumerSecret, accessToken, accessTokenS);
	}
	public static void getStatus(String userName){
		TwitModel.retriveStatis(userName);
	}

}
