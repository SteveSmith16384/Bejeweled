
import javax.imageio.*;
import java.io.File;
import java.util.HashMap;
import java.io.IOException;
import java.awt.image.BufferedImage;
public class ImageLibrary {
    private HashMap<Tile.tileID,BufferedImage> imageCollection;
    public ImageLibrary() {
        imageCollection = new HashMap(8);
        BufferedImage img;
        try {
        img = ImageIO.read(new File("gemRed.png"));
        imageCollection.put(Tile.tileID.RED, img);
        } catch (IOException e) { System.out.println(e.getMessage()); }
        try {
        img = ImageIO.read(new File("gemGreen.png"));
        imageCollection.put(Tile.tileID.GREEN, img);
        } catch (IOException e) { System.out.println(e.getMessage()); }
        try {
        img = ImageIO.read(new File("gemBlue.png"));
        imageCollection.put(Tile.tileID.BLUE, img);
        } catch (IOException e) { System.out.println(e.getMessage()); }
        try {
        img = ImageIO.read(new File("gemOrange.png"));
        imageCollection.put(Tile.tileID.ORANGE, img);
        } catch (IOException e) { System.out.println(e.getMessage()); }
        try {
        img = ImageIO.read(new File("gemPurple.png"));
        imageCollection.put(Tile.tileID.PURPLE, img);
        } catch (IOException e) { System.out.println(e.getMessage()); }
        try {
        img = ImageIO.read(new File("gemWhite.png"));
        imageCollection.put(Tile.tileID.WHITE, img);
        } catch (IOException e) { System.out.println(e.getMessage()); }
        try {
        img = ImageIO.read(new File("gemYellow.png"));
        imageCollection.put(Tile.tileID.YELLOW, img);
        } catch (IOException e) { System.out.println(e.getMessage()); }
        try {
        img = ImageIO.read(new File("focus.png"));
        imageCollection.put(Tile.tileID.FOCUS, img);
        } catch (IOException e) { System.out.println(e.getMessage()); }
    }
    BufferedImage getImage(Tile.tileID id) {
        return imageCollection.get(id);
    }
}
