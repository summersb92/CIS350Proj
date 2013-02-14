
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.border.TitledBorder;

import winterwell.jtwitter.OAuthSignpostClient;
import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.User;

//TODO
/*
For a grade of C, you must implement the following features:
Functioning GUI -- Visual Finished -- Handeling in progress
Working GetStatusâ€� feature -- In Progress
Working GetUser Timeline feature
Working Search for Statuses feature
Working Sort menu options
File menu with working â€œQuitâ€� option -- Complete
Help menu with working â€œAboutâ€� option -- In Progress
 */
/*	Known Bugs
 * - Borders Cross and the fixed layout needs to be formated
 * into an adjustable Layout
 * - Results for the Action Listeners need to be implimented
 * - Combo Box is wrong size
 */

@SuppressWarnings("serial")
public class TwitterGUI extends javax.swing.JFrame{
	/**
	 * 
	 */
	private String[] options={"Authenticate User",
			"Get Status", "Get UserTimeline","Post Status",
			"Delete Status", "Search for Statuses"};
	private JComboBox combo;
	private JPanel eastPanel, westPanel;
	private JFrame GUI;
	private JMenuBar menu;
	private JMenu file, generate, sort, help;
	private JMenuItem fileExport, fileQuit,
			generateWordFrequencyList, 
			generateTopTrendingList, help_About;
	private JRadioButtonMenuItem sortByCreation,
			sortByLoginName, sortByDisplayName,
			sortByFreindCount, sortByFollowerCount;
	private ButtonGroup group;
	private TwitterResultsPanel results;
	private AuthenticatePanel authP;
	private GetStatusPanel getStatusP;
	private GetTimePanel getTimeP;
	private PostPanel postP;
	private SearchPanel searchP;
	
	public TwitterGUI(){
		GUI = new JFrame();
		menuInit();
		WestPanelInit();
		EastPanelInit();
		GUI.pack();
		GUI.setVisible(true);
	}
	private void EastPanelInit() {
		// TODO Auto-generated method stub
		eastPanel = new JPanel();
		eastPanel.setLayout(new BoxLayout
				(eastPanel, BoxLayout.Y_AXIS));
		results = new TwitterResultsPanel();
		eastPanel.add(results);
		GUI.add(eastPanel, BorderLayout.EAST);
	}
	private void WestPanelInit() {
		// TODO Auto-generated method stub
		westPanel = new JPanel();
		westPanel.setLayout(new BoxLayout
				(westPanel, BoxLayout.Y_AXIS));
		
		combo = new JComboBox(options);
		combo.setMaximumSize(new Dimension(300, 60)); //widht, height
		
		combo.addActionListener(switchHandeler);
		authP = new AuthenticatePanel();
		getStatusP = new GetStatusPanel();
		getTimeP = new GetTimePanel();
		postP = new PostPanel();
		searchP = new SearchPanel();
		TitledBorder operationTitle = 
				BorderFactory.createTitledBorder
        		("Select Operation");
		combo.setBorder(operationTitle);
		westPanel.add(combo);
		westPanel.add(Box.createVerticalGlue());
		westPanel.add(authP);
		GUI.add(westPanel, BorderLayout.WEST);
			}
	private void menuInit() {
		// TODO Auto-generated method stub
		menu = new JMenuBar();
		
		//Creates the File Menu
		file = new JMenu("File");
		fileExport = new JMenuItem("Export to XML ...");
		fileQuit = new JMenuItem("Quit");
		fileExport.addActionListener(menuHandeler);
		fileQuit.addActionListener(menuHandeler);
		file.add(fileExport);
		file.add(fileQuit);
		menu.add(file);
		//Creates the Generate Menu
		generate = new JMenu("Generate");
		generateWordFrequencyList = new JMenuItem
				("Word Frequency List");
		generateTopTrendingList = new JMenuItem
				("Top Trending List");
		generateWordFrequencyList.addActionListener
			(menuHandeler);
		generateTopTrendingList.addActionListener
			(menuHandeler);
		generate.add(generateWordFrequencyList);
		generate.add(generateTopTrendingList);
		menu.add(generate);
		
		//Creates the Sort Menu
		sort = new JMenu("Sort");
		sortByCreation = new JRadioButtonMenuItem
				("By Creation Date");
		sortByLoginName = new JRadioButtonMenuItem
				("By Login Name");
		sortByDisplayName = new JRadioButtonMenuItem
				("By Display Name");
		sortByFreindCount = new JRadioButtonMenuItem
				("By Freinds Count");
		sortByFollowerCount = new JRadioButtonMenuItem
				("By Followers Count");
		sortByCreation.addActionListener(menuHandeler);
		sortByLoginName.addActionListener(menuHandeler);
		sortByDisplayName.addActionListener(menuHandeler);
		sortByFreindCount.addActionListener(menuHandeler);
		sortByFollowerCount.addActionListener(menuHandeler);
		group = new ButtonGroup();
		group.add(sortByCreation);
		group.add(sortByLoginName);
		group.add(sortByDisplayName);
		group.add(sortByFreindCount);
		group.add(sortByFollowerCount);
		sort.add(sortByCreation);
		sort.add(sortByLoginName);
		sort.add(sortByDisplayName);
		sort.add(sortByFreindCount);
		sort.add(sortByFollowerCount);
		menu.add(sort);
		//Create the Help Menu
		help = new JMenu("Help");
		help_About = new JMenuItem("About...");
		help_About.addActionListener(menuHandeler);
		help.add(help_About);
		menu.add(help);
		//Add Menu to the GUI
		GUI.setJMenuBar(menu);
	}
	private ActionListener menuHandeler = new 
			ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.out.println(e);
			if(e.getActionCommand().equals("Quit")){
				System.exit(0);
			}
//			if(e.getActionCommand().equals("About...")){
//				JFrame helpFrame = new JFrame();
//				helpFrame.pack();
//				helpFrame.setVisible(true);
//				JLabel author = new JLabel("Benjamin Summers");
//				helpFrame.add(author);
//				JOptionPane.showMessageDialog(helpFrame, 
//						"test");
//			}
			//Export Utility Called here
		}	
	};
	private ActionListener switchHandeler = new 
			ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			westPanel.remove(authP);
			westPanel.remove(getStatusP);
			westPanel.remove(getTimeP);
			westPanel.remove(postP);
			westPanel.remove(searchP);
			//0 is the combo box, and 1 is whats beneath
			//westPanel.remove(1);
			GUI.pack();
			switch(combo.getSelectedIndex()){
			case 0: westPanel.add(authP); GUI.pack(); break;
			case 1: westPanel.add(getStatusP); GUI.pack(); break;
			case 2: westPanel.add(getTimeP); GUI.pack(); break; //timeline
			case 3: westPanel.add(postP); GUI.pack(); break; //post
			case 4: break; //delete not a seperate class but the user must
			//go to the Table and select delete option. Use a JDialog for error Handeling.
			case 5: westPanel.add(searchP); GUI.pack(); break; //search
			//TODO Impliment Actions
//			AuthenticationPanel
//			TweetGetPanel
//			TweetPostPanel
//			TweetSearchPanel
			}	
			int temp = combo.getSelectedIndex();
		}
	};
	public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TwitterGUI();
            }
        });
        
    }
}

