import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;

public class Level {

	public static int w = 1024;// screen width
	public static int h = 770;// screen height
	public static int x, y;
	public static String file;// string for file name
	public static EZImage bg;// string for background image
	public static Level obj;// string for object inserting
	public static Player play;// string for player
	public static int x1;// int for top left corner X postion on focus
	public static int y1;// int for top left corner Y postion on setfocus
	public static int x2;// int for bottom right corner X postion
	public static int y2;// int for bottom right corner Y position
	public static int changeScene = 0;	// counter for holding rectangle up
	public static EZImage text;
	private static boolean FIRSTLEVELINIT = false;
	private static boolean FIRSTSEGUEINIT = false;

	// Level constructor
	public Level(String filename, int posx, int posy) {
		file = filename;
		x = posx;
		y = posy;
		bg = EZ.addImage(file, x, y);
		bg.pushToBack();
	}

	public static void intialize() {
		EZ.initialize(w, h);

	}

	// first part of the game, since the level is an object I used it to call it
	public static void firstPart(String textBox) {
		if (FIRSTLEVELINIT == false) {
			text = EZ.addImage(textBox, w / 2, 10);

			FIRSTLEVELINIT = true;
		}
	}

	public void walkLevel(Level ObjectIn, int xIn, int yIn) {
		ObjectIn.translateLevel(xIn, yIn);
	}

	// not sure where to go with next
	public void segue(Level objectIn, Player player, int xIn, int yIn) {
		if (FIRSTSEGUEINIT == false) {
			EZRectangle rect = EZ.addRectangle(w / 2, h / 2, w, h, Color.BLACK, true);
			objectIn.translateLevel(xIn, yIn);
			objectIn.pullOneLayerForward();
			player.translatePlayer(Main.startX, Main.startY);

			EZ.removeEZElement(rect);
			FIRSTSEGUEINIT = true;
		}

	}

	public void secondPart() {

	}

	public void translateLevel(int x, int y) {
		bg.translateTo(x, y);
	}

	public void removeLevel() {
		EZ.removeEZElement(bg);
	}
public void pullOneLayerForward(){
	bg.pullForwardOneLayer();
}
}
