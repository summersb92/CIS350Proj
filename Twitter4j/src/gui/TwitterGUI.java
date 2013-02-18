package gui;



import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import twitter4j.TwitterException;

import engine.TwitterEngine;



@SuppressWarnings("serial")
/**
 * Creates the GUI and runs the application.
 * 
 * @author Ben
 */
public class TwitterGUI extends javax.swing.JFrame {

//	private String[] options = {"Authenticate User",
//			"Get Status", "Get UserTimeline","Post Status",
//			"Search for Statuses"};
//	private JComboBox combo;
	/** GUI panel variables. */
	private JPanel eastPanel, westPanel;
	/** Main JFrame. */
	private JFrame GUI;
	/** GUI Menu Bar. */
	private JMenuBar menu;
	/** Menu Bar Components. */
	private JMenu file, generate, sort, help; 
	/** Items of individual components. */
	private JMenuItem fileExport, fileDeleteTable, fileQuit,
			fileDeleteStatus, generateWordFrequencyList, 
			generateTopTrendingList, helpAbout;
	/** GUI component contains twitter results. */
	private TwitterResultsPanel results;
//	private AuthenticatePanel authP;
//	private GetStatusPanel getStatusP;
	/** GUI panel for getting time. */
	private GetTimePanel getTimeP;
	/** GUI Panel for posts. */
	private PostPanel postP;
//	private SearchPanel searchP;
	/** Twitter engine communicates with model. */
	private TwitterEngine engine;
	
	/**
	 * Packs and sets the GUI.
	 * @throws TwitterException 
	 */
	public TwitterGUI() throws TwitterException {
		engine = new TwitterEngine();
		GUI = new JFrame("Twitter Lite");
		menuInit();
		westPanelInit();
		eastPanelInit();
		GUI.pack();
		GUI.setVisible(true);
	}
	/**
	 * Creates the Eastern Panel using the
	 * class TwitterResultsPanel().
	 */
	private void eastPanelInit() {
		eastPanel = new JPanel();
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
		results = new TwitterResultsPanel(engine);
		eastPanel.add(results);
		GUI.add(eastPanel, BorderLayout.EAST);
	}
	/**
	 * Contains the Panels in the Operation Section.
	 * The combobox allows selection bettween the different
	 * options.
	 * @throws TwitterException 
	 */
	private void westPanelInit() throws TwitterException {
		westPanel = new JPanel();
		westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
		getTimeP = new GetTimePanel(engine);
		postP = new PostPanel(engine);
//		searchP = new SearchPanel(engine);
//		TitledBorder operationTitle = 
//				BorderFactory.createTitledBorder("Select Operation");

		westPanel.add(Box.createVerticalGlue());
		westPanel.add(postP);
		westPanel.add(getTimeP);
		GUI.add(westPanel, BorderLayout.WEST);
		GUI.pack();
	}
	
	/**
	 * Creates a text panel from the given text.
	 *  
	 * @param text - String of text for the panel
	 * @return newly created text panel
	 */
    protected final JComponent makeTextPanel(final String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        //panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
    }
     
	/**
	 * menuInit() generates the JMenuBar to be used.
	 */
	private void menuInit() {
		menu = new JMenuBar();
		//Creates the File Menu
		file = new JMenu("File");
		fileDeleteStatus = new JMenuItem("Delete Status");
		fileDeleteTable = new
			JMenuItem("Delete Table Status");
		fileExport = new JMenuItem("Export to XML ...");
		fileQuit = new JMenuItem("Quit");
		fileDeleteStatus.addActionListener(menuHandeler);
		fileDeleteTable.addActionListener(menuHandeler);
		fileExport.addActionListener(menuHandeler);
		fileQuit.addActionListener(menuHandeler);
		file.add(fileDeleteStatus);
		file.add(fileDeleteTable);
		file.add(fileExport);
		file.add(fileQuit);
		menu.add(file);
		//Creates the Generate Menu
		generate = new JMenu("Generate");
		generateWordFrequencyList = new JMenuItem("Word Frequency List");
		generateTopTrendingList = new JMenuItem("Top Trending List");
		generateWordFrequencyList.addActionListener(menuHandeler);
		generateTopTrendingList.addActionListener(menuHandeler);
		generate.add(generateWordFrequencyList);
		generate.add(generateTopTrendingList);
		menu.add(generate);
		//Create the Help Menu
		help = new JMenu("Help");
		helpAbout = new JMenuItem("About...");
		helpAbout.addActionListener(menuHandeler);
		help.add(helpAbout);
		menu.add(help);
		//Add Menu to the GUI
		GUI.setJMenuBar(menu);
	}
	/**
	 * Handles all of the actions in the JMenuBar.
	 */
	private ActionListener menuHandeler = new 
			ActionListener() {
		@Override
		public void actionPerformed(final ActionEvent e) {
			//System.out.println(e);
			//if(e.getActionCommand().equals
				//	("Delete Status")){
				//engine.deleteStatus();
			//}
			if (e.getActionCommand().equals("Delete Table Status")) {
				engine.deleteTweet();
			}
			if (e.getActionCommand().equals("Export to XML ...")) {
				saveButtonAction();
			}
			if (e.getActionCommand().equals("Quit")) {
				System.exit(0);
			}
			if (e.getActionCommand().equals("About...")) {
				JOptionPane.showMessageDialog(null, 
						"Produced by\n Benjamin Summers \n" 
				+ "			 Kevin Anderson     \n" 
				+ "			 Seth Hilaski       \n"
				+ "			 Trent Newberry     \n" 
				+ "            2/17/2013          \n" 
				+ "            For a CIS350 Project");
			}
		}	
	};
	/**
	 * Handels which panel is active bellow the ComboBox
	 */
//	private ActionListener switchHandeler = new 
//			ActionListener(){
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			westPanel.remove(authP);
//			westPanel.remove(getStatusP);
//			westPanel.remove(getTimeP);
//			westPanel.remove(postP);
//			westPanel.remove(searchP);
//			//0 is the combo box, and 1 is whats beneath
//			//westPanel.remove(1);
//			GUI.pack();
//			switch(combo.getSelectedIndex()){
//			case 0: westPanel.add(authP); GUI.pack(); break;
//			case 1: westPanel.add(getStatusP); GUI.pack();
//				break;
//			case 2: westPanel.add(getTimeP); GUI.pack();
//				break; //timeline
//			case 3: westPanel.add(postP); GUI.pack(); break;
//			case 4: westPanel.add(searchP); GUI.pack();
//				break; //search
//			}	
//			GUI.pack();
//		}
//	};
	/**
	 * uses the saveXML action to save a the twitter Status
	 * list as an XML file.
	 */
	private void saveButtonAction() {
        
    }
	/**
	 * runs the main application starting with TwitterGUI
	 * which instantiates all other methods that are part
	 * of the GUI.
	 * 
	 * @param args - program arguments
	 */
	public static void main(final String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                try {
					new TwitterGUI();
				} catch (TwitterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });  
    }
	
}