import java.awt.Color;
import java.util.Random;

public class Weapon {

	public String name; // Weapon's name
	public int baseDamage; // Weapon's Minimum Damage
	public int hitRate; // Chance of hit out of 10
	public int critRate; // not for milestone 2
	public int accBuff; // How much to increase Person's accuracy
	public boolean hit; // Did Attack land or do dmg.
	public int x; // Weapon x
	public int y; // Weapon y
	public int width; // Weapon width
	public int height; // Weapon height
	public EZImage Weapon; // For when we get weapon image

	public int startx, starty; // Member variables to store x, y, startx, starty
	private int destx, desty; // Member variables to store destination values
	private long starttime; // Member variable to store the start time
	private long duration; // Member variable to store the duration
	private boolean interpolation; // Is the weapon in motion

	/**
	 * Weapon class Takes in the its name, base damage, hit rate, and accuracy
	 * buff
	 * 
	 * @param WeaponName
	 *            the name of the weapon's image file
	 * @param AlphaDamage
	 *            the min. damage that can be done when hit lands
	 * @param HitRate
	 *            the maximum value you need out of the 10 (may be removed)
	 * @param accBuff
	 *            how much persons accuracy is increased by
	 */
	public Weapon(String WeaponName, int AlphaDamage, int HitRate, int AccBuff, int posx, int posy) {
		baseDamage = AlphaDamage;
		hitRate = HitRate;
		accBuff = AccBuff;
		name = WeaponName;
		x = posx;
		y = posy;
		Weapon = EZ.addImage(name, x, y);
		Weapon.pullToFront();
		width = Weapon.getWidth();
		height = Weapon.getHeight();
	}

	/**
	 * Calls a random Generator and that goes from 0-10 (excluding 10) and if
	 * the number is less than the weapons hit rate than damage can be dealt.
	 * 
	 * this temporary will be modified when util. class is made
	 * 
	 * @return true if hitRate > number, else false
	 */

	public boolean Hit() {
		int R = Utility.Random(10);
		if (hitRate > R) {
			hit = true;
			return true;
		} else {
			hit = false;
			return false;
		}
	}

	/**
	 * Translates the weapon to the players center
	 * 
	 * @param player
	 *            used to get player's Center and translate weapon there
	 */
	public void translateTo(Player player) {
		x = player.getX();
		y = player.getY();
		Weapon.translateTo(x, y);
	}

	/**
	 * Translates the weapon to specified point could be used to move the weapon
	 * off screen or to a point relative to the person attacking
	 *
	 * @param x
	 *            X-coordinate of where you want to move the weapon to
	 * @param y
	 *            Y-coordinate of where you want to move the weapon to
	 */
	public void translateTo(int x, int y) {
		this.x = x;
		this.y = y;
		Weapon.translateTo(this.x, this.y);

	}

	/**
	 * Sets the beginning and end points of the weapon as well as the duration
	 * of the movement used for Player attacking Enemy.
	 * 
	 * @param player
	 *            the person attacking
	 * @param enemy
	 *            the person being attacked
	 * @param posx
	 *            the start x-coordinate relative to the attackers Center-X
	 * @param posy
	 *            the start y-coordinate relative to the attackers Center-Y
	 * @param dur
	 *            how long the weapon shall take to travel.
	 */
	public void setPEDestination(Player player, Enemy enemy, int posx, int posy, long dur) {

		// Set destination position and duration
		// Convert seconds to miliseconds
		// NOTE: Convert x,y in person class to public ints
		destx = enemy.getBodyX();
		desty = enemy.getBodyY();
		duration = dur * 1000;
		// System.out.println("Dest: "+destx + ", "+ desty);
		// Get the startting time (i.e. time according to your computer)
		starttime = System.currentTimeMillis();

		// Set the starting position of your bug
		startx = posx + player.getX();
		starty = posy + player.getY();
		// System.out.println("Start: "+startx + ", "+starty);
		// Set interolation mode to true
		interpolation = true;
	}

	/**
	 * Sets the beginning and end points of the weapon as well as the duration
	 * of the movement used for Enemy attacking Player
	 * 
	 * @param enemy
	 *            the person attacking
	 * @param player
	 *            the person being attacked
	 * @param posx
	 *            the start x-coordinate relative to the attackers Center-X
	 * @param posy
	 *            the start y-coordinate relative to the attackers Center-Y
	 * @param dur
	 *            how long the weapon shall take to travel.
	 */
	public void setEPDestination(Enemy enemy, Player player, int posx, int posy, long dur) {

		// Set destination position and duration
		// Convert seconds to miliseconds
		// NOTE: Convert x,y in person class to public ints
		destx = player.getX();
		desty = player.getY();
		duration = dur * 1000;
		// Get the starting time (i.e. time according to your computer)
		starttime = System.currentTimeMillis();

		// Set the starting position of your bug
		startx = posx + enemy.getBodyX();
		starty = posy + enemy.getBodyY();
		// Set interolation mode to true
		interpolation = true;
	}

	/**
	 * Move the weapon to destination set by setDestination.
	 * 
	 * Can only be used if setDestination has just been used.
	 * 
	 * Runs while interpolation is true. Ends when the duration specified has
	 * been reached.
	 * 
	 * Use the translateTo(int x, int y) afterwards if you want to instantly
	 * move the weapon off screen.
	 * 
	 * @param enemy
	 *            to keep enemy's wings rotating during weapon movement
	 */
	public void move(Enemy enemy) {
		// move the weapon to the start position
		x = startx;
		y = starty;
		Weapon.translateTo(x, y);
		// while interpolation mode is true then do interpolation
		while (interpolation == true) {

			// Normalize the time (i.e. make it a number from 0 to 1)
			float normTime = (float) (System.currentTimeMillis() - starttime) / (float) duration;

			// Calculate the interpolated position of the weapon
			x = (int) (startx + ((float) (destx - startx) * normTime));
			y = (int) (starty + ((float) (desty - starty) * normTime));
			enemy.rotateWings();
			// If the difference between current time and start time has
			// exceeded the duration
			// then the animation/interpolation is over.
			if ((System.currentTimeMillis() - starttime) >= duration) {

				// Set interpolation to false
				interpolation = false;

				// Move the weapon all the way to the destination
				x = destx;
				y = desty;
			}
			// move the image itself and pull it to front then refresh
			Weapon.translateTo(x, y);
			Weapon.pullToFront();
			EZ.refreshScreen();
		}
	}
}
