package engine;
import javax.swing.JTextField;

import model.TwitModel;


/**
 * 
 * @author Ben
 *
 */
//TODO Place rest of the Engine functions. Actuall Logic.
public class TwitterEngine {

	
	public static void login(String userName,
			String consumerKey, String consumerSecret,
			String accessToken, String accessTokenS) {
		
		TwitModel.authentication(userName, consumerKey,
				consumerSecret, accessToken, accessTokenS);
	}
	public static void getStatus(String userName){
		TwitModel.retriveStatis(userName);
	}

}
