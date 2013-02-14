import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class SearchPanel extends JPanel {

	private JPanel searchPanel;
	private JLabel keywordsL, exactPhrasesL, fromUserL,
		toUserL;
	private JTextField keywordsF, exactPhrasesF,
		fromUserF, toUserF;
	private JButton search, cancel;
	
	//keywords, exact Phrases, from user, to user
	public SearchPanel(){
		GridLayout searchLayout = new GridLayout
				(5, 2, 2, 5);
		searchPanel = new JPanel();
		searchPanel.setLayout(searchLayout);
		keywordsL = new JLabel("KeyWords: ");
		keywordsF = new JTextField(20);
		searchPanel.add(keywordsL);
		searchPanel.add(keywordsF);
		exactPhrasesL = new JLabel("Exact Phrase: ");
		exactPhrasesF = new JTextField(20);
		searchPanel.add(exactPhrasesL);
		searchPanel.add(exactPhrasesF);
		fromUserL = new JLabel("From User: ");
		fromUserF = new JTextField(20);
		searchPanel.add(fromUserL);
		searchPanel.add(fromUserF);
		toUserL = new JLabel("To User: ");
		toUserF = new JTextField(20);
		searchPanel.add(toUserL);
		searchPanel.add(toUserF);
		//authenticate , canel
		search = new JButton("Authenticate: ");
		cancel = new JButton("Cancel");
		searchPanel.add(search);
		searchPanel.add(cancel);
		add(searchPanel);
	}
}
