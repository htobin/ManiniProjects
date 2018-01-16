
public class Barb {
	// initialize barbwire image and location
	public EZImage barbPic;
	private int barbX, barbY;

	// return barbwire's x location
	public int getBarbX() {
		return barbX;
	}

	// return barbwire's y location
	public int getBarbY() {
		return barbY;
	}

	// barbwire's image and location
	public Barb(String filename, int posx, int posy) {
		barbPic = EZ.addImage(filename, posx, posy);
		barbX = posx;
		barbY = posy;
		barbPic.pushToBack();

	}

	// moves barbwire picture to match it's position
	private void setBarbImagePosition(int posx, int posy) {
		barbPic.translateTo(posx, posy);
	}

	// moves the "barbwire" with the image
	public void setBarbPosition(int posx, int posy) {
		barbX = posx;
		barbY = posy;
		setBarbImagePosition(barbX, barbY);
	}

}
