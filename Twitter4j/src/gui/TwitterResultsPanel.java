package gui;



import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import engine.TwitterEngine;


/**
 * TwitterResultsPanel.class
 * 
 * Generates the TwitterResultsPanel which displays all
 * output from any action taken in the program
 * @author Ben
 *
 */
@SuppressWarnings("serial")
public class TwitterResultsPanel extends JPanel{

	private JTextArea textArea;
	private JPanel twitResults;
	private TitledBorder tableTitle, textTitle;
	private JTable table;

	private TwitterEngine engine;

	/**
	 * Constructs the TwitterResultsPanel
	 * 
	 * @param engine
	 */
	public TwitterResultsPanel(TwitterEngine engine){
		//engine = new TwitterEngine();

		this.engine = engine;
		GridLayout resultLayout = new GridLayout
		(2, 2, 5, 10);
		twitResults = new JPanel();
		twitResults.setLayout(resultLayout);

		//Table Creation
		table = new JTable();
		table.setToolTipText("");
		table.setSelectionMode(javax.swing.
				ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);

		table.addMouseListener(new java.awt.event.
				MouseAdapter() {
			public void mouseClicked(java.awt.event.
					MouseEvent evt) {
				tableMouseClicked();
			}
		});
		table.setPreferredScrollableViewportSize
		(new Dimension(500, 140));
		//table.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane();
		tableTitle = BorderFactory.createTitledBorder
		("Statuses");
		table.setModel(engine.getModel());
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.
				VERTICAL_SCROLLBAR_ALWAYS);
		//attaches table to the scroll pane
		scrollPane.setViewportView(table); 
		scrollPane.setBorder(tableTitle);
		twitResults.add(scrollPane);

		//Text Area
		textArea = new JTextArea(7, 40); 
		textArea.setEditable(true);
		textTitle = BorderFactory.createTitledBorder
		("Status Text");
		textArea.setLineWrap(true);
		textArea.setBorder(textTitle);
		JScrollPane textPane = new JScrollPane();
		textPane.setVerticalScrollBarPolicy(JScrollPane.
				VERTICAL_SCROLLBAR_ALWAYS);
		textPane.setViewportView(textArea);
		twitResults.add(textPane);

		//Add twitResults to the Panel
		add(twitResults); 
	}
	/**
	 * gets the value of the text for the particular 
	 * position of the table.
	 */
	private void tableMouseClicked(){
		int index = table.getSelectedRow();
		if(index!= -1){
			String output = engine.getDisplayStatis(index);
			setStatus(output);
		}
	}
	/**
	 * Orders what needs to be displayed with in the Status
	 * text area.
	 * @param object 
	 * 
	 * @param output - A string of the users current status.
	 */
	private void setStatus(String output) {
		textArea.setText(output);
	}
	/**
	 * Gets the topTrendingOutput
	 * 
	 * @param topTrendingList - what is top trending
	 */
	public void topTrendingOutput(Object topTrendingList) {
		textArea.setText(topTrendingList.toString());
	}
	/**
	 * Gets the wordFrequencyCount
	 * 
	 * @param wordFrequencyList - what is the word frequency
	 */
	public void wordFrequencyCount(Object wordFrequencyList)
	{
		textArea.setText(wordFrequencyList.toString());
	}
}
