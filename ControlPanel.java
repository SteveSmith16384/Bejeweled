/*import javax.swing.JPanel;

import com.scs.gmc.ConnectorMain.GameStage;

import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel implements ActionListener {

	private JButton newGameButton = new JButton("New Game");
	private Game game;
	private JButton exit = new JButton("Exit");
	//private JButton cheat = new JButton("Cheat");

	public ControlPanel(Game game){
		this.game = game;
		add(newGameButton);
		newGameButton.addActionListener(this);
		add(exit);
		exit.addActionListener(this);
		//add(cheat);
		//cheat.addActionListener(this);

	}
	public void actionPerformed(ActionEvent e){
		if (e.getSource().equals(newGameButton)){
			if (game.connector.getGameStage()== GameStage.IN_PROGRESS) {
				game.initGame();
			} else {
				System.out.println("Still waiting for players...");
			}
		}
		//if (e.getSource().equals(cheat))
		// game.addScore(500);
		if (e.getSource().equals(exit)) {
			game.connector.sendOutOfGame();
			game.connector.disconnect();
			System.exit(0);
		}
	}
}*/
