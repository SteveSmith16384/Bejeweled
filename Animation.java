import javax.swing.Timer;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
// implements animation details
public class Animation implements ActionListener {
	private int frame;
	private Timer timer;
	private Tile t1;
	private Tile t2;
	private ArrayList<Tile> fallingTiles;
	private Game gamePanel;
	private Board gameBoard;
	private animType type;
	public static enum animType {SWAP, CASCADE};

	public Animation(Game gamePanel,Board gameBoard,animType type){
		this.gameBoard = gameBoard;
		this.gamePanel = gamePanel;
		this.type = type;
		t1 = null;
		t2 = null;
		fallingTiles = null;
		frame = 0;
		timer = new Timer(10,this);
	}
	public void setType(animType type) {this.type = type;}
	public int getFrame() { return frame;}
	public void endSwap(){
		timer.stop();
		frame = 0;
		gameBoard.swapTile(t1, t2);
		gamePanel.repaint();
		gamePanel.updateGame();
	}
	public void animateSwap(Tile t1, Tile t2) {    
		this.t1 = t1;
		this.t2 = t2;
		timer.start();
	}
	public void animateCascade() {
		fallingTiles = new ArrayList();
		gameBoard.collectFallingTiles(fallingTiles);
		timer.start();   
	}
	public void endCascade() {
		timer.stop();
		frame = 0;
		gamePanel.cleanBoard();
		gamePanel.repaint();
		gamePanel.updateGame();
	}
	public void actionPerformed(ActionEvent e){
		if (type.equals(Animation.animType.SWAP)){
			frame++;
			if (frame > 32) {endSwap();}
			else {
				int direction = 1;
				if (t1.col == t2.col){
					if (t1.row < t2.row) { direction = 1;}
					else { direction = -1;}
					t1.moveRow(2,direction);
					t2.moveRow(2, -direction);
				}
				else {                
					if (t1.col < t2.col) { direction = 1;}
					else { direction = -1;}
					t1.moveCol(2,direction);
					t2.moveCol(2, -direction);              
				}
				gamePanel.repaint();
			}
		}
		else if (type.equals(Animation.animType.CASCADE)){
			frame++;
			if(frame > 32) {
				endCascade();
			} else {
				for (Tile tile: fallingTiles) {
					tile.moveRow(tile.dropDistance*2,1);
				}
				gamePanel.repaint();
			}
		}
	}
}
