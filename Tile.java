import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Graphics2D;
/*
 this class defines tile attributes 
 */
public class Tile {
    protected static enum tileID { RED,GREEN,BLUE,ORANGE,PURPLE,WHITE,YELLOW,FOCUS,DELETED } 
    protected tileID id;     
    protected ImageIcon gemIcon;
    // parameters needed for animation
    protected int row;
    protected int col;
    protected int anim_row;
    protected int anim_col;
    protected boolean inFocus;
    protected boolean willDrop;
    protected int beforeDrop;
    protected int dropDistance;
  
    public Tile(tileID id,int row,int col){
           this.id = id;
           this.row = row;
           this.col = col;
           this.anim_row = row*65+40;
           this.anim_col = col*65+240;
           this.beforeDrop = 0;
           this.dropDistance = 0;
           this.willDrop = false;
           this.inFocus   = false;
           if (this.id != tileID.DELETED){
               this.gemIcon = new ImageIcon();
               this.gemIcon.setImage(Game.imageLibrary.getImage(id));
           }
    }
    public boolean isNeighbor(Tile other) {
        if (Math.abs(row-other.row) == 1 && Math.abs(col-other.col) == 0){
            return true;
        }
        else if (Math.abs(row-other.row) == 0 && Math.abs(col-other.col) == 1){
            return true;
        }
        else {return false;}
    }
    public void draw(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        if (!id.equals(Tile.tileID.DELETED))
          gemIcon.paintIcon(null, g, anim_col, anim_row);
        if (inFocus){
            ImageIcon focusIcon = new ImageIcon();
            focusIcon.setImage(Game.imageLibrary.getImage(Tile.tileID.FOCUS));
            focusIcon.paintIcon(null,g,anim_col,anim_row);
        }    
    }
    public void moveRow(int step,int direction){
        anim_row += step*direction;
    }
    public void moveCol(int step,int direction) {
        anim_col += step*direction;
    }
    public void setAnimRow(int row){
        this.row = row;
        this.anim_row = row*65+40;
    }
    public void setAnimCol(int col){ 
        this.col = col;
        this.anim_col = col*65+240;
    }
}
