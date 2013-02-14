import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class GetStatusPanel extends JPanel{
	
	JPanel getStatusPanel;
	JLabel getStatusL;
	JTextField getStatusF;
	JButton getB, cancelB;
	
	GetStatusPanel(){
		GridLayout statusLayout = new GridLayout
				(2, 2, 2, 5);
		getStatusPanel = new JPanel();
		getStatusPanel.setLayout(statusLayout);
		
		getStatusL = new JLabel("UserName");
		getStatusF = new JTextField(20);
		getStatusPanel.add(getStatusL);
		getStatusPanel.add(getStatusF);
		
		getB = new JButton("Get");
		cancelB = new JButton("Cancel");
		getStatusPanel.add(getB);
		getStatusPanel.add(cancelB);
		add(getStatusPanel);
	}

}
