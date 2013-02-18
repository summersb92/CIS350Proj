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
public class GetTimePanel extends JPanel {
		/** JPanel for Twitter timeline. */
		private JPanel timeLinePanel;
		/** Text area for the timeline. */
		private JTextArea timeLineArea;
		/** Twitter variable that holds a twitter instance. */
		private Twitter twitter;
		/** A List of twitter User Statuses. */
		private List<Status> statuses;
		
		/**
		 * Creates the Timeline Panel and adds it's components.
		 * @param engine - engine variable communicates with model
		 * @throws TwitterException - a twitter exception
		 */
		public GetTimePanel(final TwitterEngine engine) 
								throws TwitterException {		
		
		twitter = TwitterFactory.getSingleton();
			
		timeLinePanel = new JPanel();
		timeLineArea = new JTextArea();
		
		//friendsScrollPane = new JScrollPane(timeLineArea);
		timeLinePanel.add(timeLineArea);

		statuses = engine.getTimeline();
	    System.out.println("Showing home timeline.");
	    
	    for (Status status : statuses) {
	       timeLineArea.append(status.getUser().getName() + ":" 
	    		   + status.getText());
	       timeLineArea.append("\n\n");
	    }
	 
	    add(timeLineArea);
	}
		
	/**
	 * Updates the timeline panel when necessary.
	 * 
	 * @throws TwitterException - a twitter exception
	 */
	public final void updateTimeLinePanel() throws TwitterException {
		
		timeLineArea.setText("");
		
		statuses.clear();
		
		statuses = twitter.getHomeTimeline();
	    
	    for (Status status : statuses) {
	       timeLineArea.append(status.getUser().getName() + ":" 
	    		   + status.getText());
	       timeLineArea.append("\n\n");
	    }
	}
}
