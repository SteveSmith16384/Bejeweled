import java.util.ArrayList;
/* provides algorithms for checking chains
 * and enables cascades for later projects
 */
public class Algorithms {

	private ArrayList<ArrayList<Tile>> chains; // lists of chains to be deleted
	private Game game;
	private Board gameBoard;
	public int jewels_to_add = 0;

	public Algorithms(Board gameBoard, Game game){
		this.gameBoard = gameBoard;
		this.game = game;
		this.chains = new ArrayList();
	}

	// whether board has chains
	public boolean isStable(){
		checkRows();
		checkColumns();
		return chains.isEmpty();
	}

	// establish a chainless board
	public void rmChains(boolean init) {
		markDeleted(init);
		calculateDrop();
		applyDrop();
		fillEmpty(init);
		endCascade();
	}

	// mark all deleted board 
	public void markDeleted(boolean init){
		int combo = 0;
		int score = 0;
		for (ArrayList<Tile> chain: chains){
			combo += chain.size();
			score += chain.size()*10;
			for (Tile tile: chain){
				tile.id = Tile.tileID.DELETED;
			}

		}  
		chains.clear();
		game.addScore(score);
		game.setCombo(combo);
		if (!init && combo > 0) {
			game.connector.sendKeyValueDataByTCP(Game.CMD_COMPLETED_ROW, combo);
		}
	}


	//calculate where chains fall to
	public void calculateDrop(){
		int row,col,temp;
		for (col=0;col<8;col++){
			for (row=7;row>=0;row--){
				Tile bottom = gameBoard.getTileAt(row, col);
				bottom.beforeDrop = row;
				if(bottom.id.equals(Tile.tileID.DELETED)){
					for(temp = row-1; temp >= 0;temp--){
						Tile above = gameBoard.getTileAt(temp, col);
						above.willDrop = true;
						above.dropDistance++;
					}
				}
			}
		}
	}
	// modify board data structure to apply the calculations
	// gathered from calculateDrop()
	public void applyDrop() {
		int row,col;
		for(col = 0; col < 8; ++col){
			for(row = 7; row >= 0; --row){
				Tile tile = gameBoard.getTileAt(row, col);
				if(tile.willDrop && !tile.id.equals(Tile.tileID.DELETED)){
					gameBoard.putTileAt(row+tile.dropDistance, col, tile);
					tile.setAnimCol(col);
					gameBoard.putTileAt(row, col, new Tile(Tile.tileID.DELETED,row,col));
				}
			}
		}
	}
	// modify board to fill in all empty cells
	public void fillEmpty(boolean init) {
		if (init || jewels_to_add > 0) {
			int row,col;
			for (row=0;row<8;row++){
				for (col=0;col<8;col++){
					Tile tile = gameBoard.getTileAt(row, col);
					if (tile.id.equals(Tile.tileID.DELETED)){
						Tile randTile = gameBoard.randTile(row,col);
						gameBoard.putTileAt(row, col, randTile);
						if (!init) {
							jewels_to_add--;
							if (jewels_to_add <= 0) {
								return;
							}
						}
					}
				}
			}
		}
	}
	// re-establish original tile state after animation
	public void endCascade() {
		int row,col;
		for (row=0;row<8;row++){
			for (col=0;col<8;col++){
				Tile tile = gameBoard.getTileAt(row, col);
				tile.beforeDrop = col;
				tile.dropDistance = 0;
				tile.willDrop = false;
			}
		}
	}
	// private functions to scan for chains
	private void checkRows() {
		int row,col,tmp;
		for(row = 0; row < 8; row++)
		{
			for(col= 0; col < 6; col++)
			{
				Tile start = gameBoard.getTileAt(row, col);
				if (start.id != Tile.tileID.DELETED) {
					ArrayList chain = new ArrayList(5);
					chain.add(start);
					for (tmp=(col+1);tmp<8;tmp++){
						Tile next = gameBoard.getTileAt(row, tmp);
						if (next.id.equals(start.id) && next.id != Tile.tileID.DELETED){
							chain.add(next);
						} else {
							break;
						}
					}
					if (chain.size() > 2) {
						this.chains.add(chain);
					}
					col = tmp - 1;
				}
			}
		}
	}


	private void checkColumns() {
		int row,col,tmp;
		for(col = 0; col < 8; col++)
		{
			for(row= 0; row < 6; row++)
			{
				Tile start = gameBoard.getTileAt(row, col);
				if (start.id != Tile.tileID.DELETED) {
					ArrayList chain = new ArrayList(3);
					chain.add(start);
					for (tmp=(row+1);tmp<8;tmp++){
						Tile next = gameBoard.getTileAt(tmp,col);
						if (next.id.equals(start.id) && next.id != Tile.tileID.DELETED){
							chain.add(next);
						} else {
							break;
						}
					}
					if (chain.size() > 2) {
						this.chains.add(chain);
					}
					row = tmp - 1;
				}
			}
		}
	}
}
