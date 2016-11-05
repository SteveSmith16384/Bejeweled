import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import com.scs.gmc.ConnectorMain;
import com.scs.gmc.ConnectorMain.GameStage;
import com.scs.gmc.IGameClient;
import com.scs.gmc.SimpleStartGameOptions;
import com.scs.gmc.StartGameOptions;
/*
 This class records the state of the game
 It will be extended to support animation 
 and the various states of the bejeweled game.
 */
public final class Game extends JComponent implements IGameClient {

	//Commands
	public static final int CMD_COMPLETED_ROW = 1;
	
	
	private StatePanel sPanel;
	private Board gameBoard;
	private Algorithms solver;
	private Animation animation;
	private Tile focus;
	private boolean started;
	private int score;
	private int level;
	private int combo;
	public static ImageLibrary imageLibrary = new ImageLibrary();
	public static SoundLibrary soundLibrary = new SoundLibrary();
	public BufferedImage boardImg;
	public ImageIcon boardIcon;
	public ConnectorMain connector;

	public Game(StatePanel sPanel){
		connector = StartGameOptions.ShowSimpleOptionsAndConnect(this, "Bejeweled", 2, 99);
		connector.joinGame();
		
		try {
			boardImg = ImageIO.read(new File("board.png"));
		} catch (IOException e) { 
			System.out.println(e.getMessage());
		}

		this.boardIcon = new ImageIcon();
		this.boardIcon.setImage(this.boardImg);
		started = false;
		this.sPanel = sPanel;
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(800,600)); 
		this.addMouseListener(new MouseListener(this));
		
		connector.waitForStage(GameStage.IN_PROGRESS);
		
		initGame();
	}


	public void initGame(){
		gameBoard = new Board(this);
		solver = new Algorithms(gameBoard, this);
		animation = new Animation(this,gameBoard,null);
		// initialize game
		gameBoard.initAll();
		while(!solver.isStable()) {
			solver.rmChains(true);
		}
		//set up state information
		started = true;
		focus = null;
		score = 0;
		combo = 0;
		level = 1;
		sPanel.setScore(score);
		sPanel.setCombo(combo);
		sPanel.setLevel(level);
		sPanel.setRow(-1);
		sPanel.setColumn(-1);
		repaint();
		Game.soundLibrary.playAudio("fall");
	}
	
	
	public void updateGame() {
		if (!solver.isStable()) {
			solver.markDeleted(false);
			solver.calculateDrop();
			animation.setType(Animation.animType.CASCADE);
			animation.animateCascade();
			Game.soundLibrary.playAudio("fall");
		}
	}
	
	
	public void cleanBoard() {
		solver.applyDrop();
		solver.fillEmpty(false);
		solver.endCascade();
	}
	
	
	public void addScore(int points){
		if ((this.score+points) > 1000){
			this.level++;
			sPanel.setLevel(this.level);
			this.score = 0;
			sPanel.setScore(this.score);
		}
		else {
			this.score += points;
			sPanel.setScore(score);
		}

	}
	public void setCombo(int combo){
		if (combo > this.combo){
			this.combo = combo;
			sPanel.setCombo(combo);
		}
	}
	public void clickPerformed(int click_x,int click_y) {
		sPanel.setColumn(click_x);
		sPanel.setRow(click_y);
		Tile clicked = gameBoard.getTileAt(click_y, click_x);
		if (focus == null) {
			focus = clicked;
			clicked.inFocus = true;
			Game.soundLibrary.playAudio("select");
		} else {
			if (focus.equals(clicked)) {
				clicked.inFocus = false;
				focus = null;
			}
			else {
				if(focus.isNeighbor(clicked)){
					focus.inFocus = false;
					swapTiles(focus,clicked);
					focus = null;
				}
				else {
					focus.inFocus = false;
					focus = clicked;  
					clicked.inFocus = true;
				}
			}
		}
	}


	private void swapTiles(Tile t1,Tile t2){
		animation.setType(Animation.animType.SWAP);
		animation.animateSwap(t1, t2);
	}


	public void paintComponent(Graphics g){
		this.boardIcon.paintIcon(null, g, 0, 0);
		if(started)
			drawGems(g);
	}


	private void drawGems(Graphics g){
		int row,col;
		for (row=0;row<8;row++){
			for (col=0;col<8;col++){
				Tile tile = gameBoard.getTileAt(row, col);
				tile.draw(g);
			}
		}
	}


	@Override
	public void playerLeft(String name) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void playerJoined(String name) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void gameStarted() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void gameEnded(String winner) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void dataReceivedByTCP(int fromplayerid, int code, int value) {
		if (code == CMD_COMPLETED_ROW) {
			System.out.println("Opponent has completed " + value);
			solver.jewels_to_add += value;
		}
		
	}


	@Override
	public void dataReceivedByUDP(long time, int fromplayerid, int code, int value) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void dataReceivedByTCP(int fromplayerid, String data) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void dataReceivedByTCP(int fromplayerid, byte[] data) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void dataReceivedByUDP(long time, int fromplayerid, String data) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void dataReceivedByUDP(long time, int fromplayerid, byte[] data) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void error(int error_code, String msg) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void serverDown(long ms_since_response) {
		// TODO Auto-generated method stub
		
	}

}
