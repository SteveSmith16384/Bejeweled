import java.util.Random;
import java.util.ArrayList;
/*
 this class keeps track of the tiles in a 2D array
 */
public final class Board {
    private Tile gems[][];
    public static final int GAME_DIMENSION = 8;
 
    public Board(Game gamePanel){
        gems = new Tile[GAME_DIMENSION][GAME_DIMENSION];
        initAll();
    }
    
    public Tile getTileAt(int row,int col) {
        return gems[row][col];
    }
    public void putTileAt(int row,int col,Tile tile){
        tile.setAnimCol(col);
        tile.setAnimRow(row);
        gems[row][col] = tile;
    }

    public void initAll(){
        int r,c;
        for (r=0;r<GAME_DIMENSION;r++){
            for (c=0;c<GAME_DIMENSION;c++){
                gems[r][c] = randTile(r,c);
            }
        }
    }
    public Tile randTile(int row,int col){
        Random rand = new Random(); 
        int tileNum = rand.nextInt(7);
        Tile tile = null;
        switch (tileNum) {
        case 0:
            tile = new Tile(Tile.tileID.RED,row,col);
            break;
        case 1:
            tile = new Tile(Tile.tileID.GREEN,row,col);
            break;
        case 2:
            tile = new Tile(Tile.tileID.BLUE,row,col);
            break;
        case 3:
            tile = new Tile(Tile.tileID.ORANGE,row,col);
            break;
        case 4:
            tile = new Tile(Tile.tileID.PURPLE,row,col);
            break;
        case 5: 
            tile = new Tile(Tile.tileID.WHITE,row,col);
            break;
        case 6:
            tile = new Tile(Tile.tileID.YELLOW,row,col);
            break;
        }
        return tile;
    }
    /*
    public void swapTile(int r1,int c1,int r2,int c2) {
        Tile t1 = gems[r1][c1];
        Tile t2 = gems[r2][c2];
        t1.setAnimCol(c2);
        t1.setAnimRow(r2);
        t2.setAnimCol(c1);
        t2.setAnimRow(r1);
        gems[r1][c1] = t2;
        gems[r2][c2] = t1;
    }
    
     */
       public void swapTile(Tile t1,Tile t2) {
        int c1 = t1.col;
        int c2 = t2.col;
        int r1 = t1.row;
        int r2 = t2.row;
        t1.setAnimCol(c2);
        t1.setAnimRow(r2);
        t2.setAnimCol(c1);
        t2.setAnimRow(r1);
        gems[r1][c1] = t2;
        gems[r2][c2] = t1;
      }
      public void collectFallingTiles(ArrayList<Tile> collection){
          for (Tile tiles[]: gems){
              for (Tile tile: tiles){
                  if (tile.willDrop && !tile.id.equals(Tile.tileID.DELETED))
                      collection.add(tile);
              }
          }
      }
    
}
