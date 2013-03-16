package gui;


import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import engine.TwitterEngine;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.Twitter;

@SuppressWarnings("serial")
public class GetTimePanel extends JPanel{

		private JPanel timeLinePanel;
		private JTextArea timeLineArea;
		private Twitter twitter;
		private List<Status> statuses;
		
		public GetTimePanel(TwitterEngine engine) throws TwitterException {		
		
		twitter = TwitterFactory.getSingleton();
			
		timeLinePanel = new JPanel();
		timeLineArea = new JTextArea();
		
		timeLinePanel.add(timeLineArea);

		statuses = engine.getTimeline();
	    
	    
	    for (Status status : statuses) {
	       timeLineArea.append(status.getUser().getName() + ":" + status.getText());
	       timeLineArea.append("\n\n");
	    }
	 
	    add(timeLineArea);
	}
	
	public void updateTimeLinePanel() throws TwitterException{
		
		timeLineArea.setText("");
		
		statuses.clear();
		
		statuses = twitter.getHomeTimeline();
	    
	    for (Status status : statuses) {
	       timeLineArea.append(status.getUser().getName() + ":" + status.getText());
	       timeLineArea.append("\n\n");
	    }
	}
}
