
public class Zombie {

	// intialize zombie image, location, and speed
	public EZImage zombiePic;
	private int zombieX, zombieY;
	private int speed = 2;

	// gets zomebie's currentlocation
	public int getX() {
		return zombieX;
	}

	public int getY() {
		return zombieY;
	}

	// zombie constructor
	public Zombie(String filename, int posx, int posy) {
		zombiePic = EZ.addImage(filename, posx, posy);
		zombieX = posx;
		zombieY = posy;

	}

	// moves zombie picture to match it's position
	private void setZombieImagePosition(int posx, int posy) {
		zombiePic.translateTo(posx, posy);
	}

	// moves the "zombie" with the image
	public void setPosition(int posx, int posy) {
		zombieX = posx;
		zombieY = posy;
		setZombieImagePosition(zombieX, zombieY);
	}

	// hunting fuction to the right
	public void attackRight() {
		zombieX = zombieX + speed;
		zombiePic.translateTo(zombieX, zombieY);
	}

	// hunting fuction upwards
	public void attackUp() {
		zombieY = zombieY + speed;
		zombiePic.translateTo(zombieX, zombieY);
	}

	// hunting fuction to the left
	public void attackLeft() {
		zombieX = zombieX - speed;
		zombiePic.translateTo(zombieX, zombieY);
	}

	// hunting fuction downwards
	public void attackDown() {
		zombieY = zombieY - speed;
		zombiePic.translateTo(zombieX, zombieY);

	}

	// detects if zomebie is inside of another element
	public boolean isInside(int posx, int posy) {
		return zombiePic.isPointInElement(posx, posy);
	}

	// speed is halved hitting a barb wire
	public void slow() {
		speed = 1;
	}

	// normal hunting speed
	public void regular() {
		speed = 2;
	}

}
