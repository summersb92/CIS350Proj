import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


@SuppressWarnings("serial")
public class PostPanel extends JPanel{

	JPanel postPanel;
	JLabel postPanelL;
	JTextArea postPaneLF;
	JButton getB, cancelB;

	PostPanel(){
		GridLayout postLayout = new GridLayout
				(2, 2, 2, 5);
		postPanel = new JPanel();
		postPanel.setLayout(postLayout);

		postPanelL = new JLabel("Status: ");
		postPaneLF = new JTextArea(2,10);
        postPaneLF.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(postPaneLF);
		postPanel.add(postPanelL);
		postPanel.add(scrollPane);
		
		getB = new JButton("Get");
		cancelB = new JButton("Cancel");
		postPanel.add(getB);
		postPanel.add(cancelB);
		add(postPanel);
	}
}
