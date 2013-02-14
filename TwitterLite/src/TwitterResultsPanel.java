import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;


@SuppressWarnings("serial")
public class TwitterResultsPanel extends JPanel{
	
	String[] columnNames = {"Date",
            "Login Name",
            "Display Name",
            "Freinds",
            "Followers"};
	Object[][] data = {};
	 
	private final JTable table;
	private JTextArea textArea;
	private JPanel twitResults;
	private TitledBorder tableTitle, textTitle;
	
	public TwitterResultsPanel(){
		GridLayout resultLayout = new GridLayout
				(2, 2, 5, 10);
		twitResults = new JPanel();
		twitResults.setLayout(resultLayout);
	    table = new JTable(data, columnNames);
	    table.setPreferredScrollableViewportSize
	    	(new Dimension(500, 140));
	    table.setFillsViewportHeight(true);
	    //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);
        tableTitle = BorderFactory.createTitledBorder
        		("Statuses");
        scrollPane.setBorder(tableTitle);
        //Add the scroll pane to this panel.
        twitResults.add(scrollPane);
        
        textArea = new JTextArea(4, 40); 
        textArea.setEditable(true);
        textTitle = BorderFactory.createTitledBorder
        		("Status Text");
        textArea.setLineWrap(true);
        textArea.setBorder(textTitle);
        twitResults.add(textArea);
        
       add(twitResults); 
	}

}
