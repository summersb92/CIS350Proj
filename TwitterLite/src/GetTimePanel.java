
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class GetTimePanel extends JPanel{

	JPanel getTimePanel;
	JLabel getTimeLineL;
	JTextField getTimeLineF;
	JButton getB, cancelB;

	GetTimePanel(){
		GridLayout tiemLayout = new GridLayout
				(2, 2, 2, 5);
		getTimePanel = new JPanel();
		getTimePanel.setLayout(tiemLayout);

		getTimeLineL = new JLabel("UserName");
		getTimeLineF = new JTextField(20);
		getTimePanel.add(getTimeLineL);
		getTimePanel.add(getTimeLineF);

		getB = new JButton("Get");
		cancelB = new JButton("Cancel");
		getTimePanel.add(getB);
		getTimePanel.add(cancelB);
		add(getTimePanel);
	}
}
