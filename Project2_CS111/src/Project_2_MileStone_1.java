import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Project_2_MileStone_1 {

	public static int[] zombieX = new int[20];
	public static int[] zombieY = new int[20];

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Make the screen
		int screenWidth = 1024;
		int screenHeight = 768;
		EZ.initialize(screenWidth, screenHeight);
		EZ.setBackgroundColor(new Color(150, 150, 150));

		// initialize hero
		EZImage heroPicture = EZ.addImage("SmallHeroPic.png", 400, 700);
		// heroPicture.scaleTo(.2);
		
		

		// Zombie Stuff

		// Zombie Array for zombie images
		EZImage[] zombiePicture = new EZImage[20];

		// Random zombie generator
		Random r;
		r = new Random();

		// Zombie pop up loop and Array 3
		for (int i = 0; i < zombiePicture.length; i++) {
			zombieX[i] = r.nextInt(1000);
			zombieY[i] = r.nextInt(100);
			zombiePicture[i] = EZ.addImage("zPic.png", zombieX[i], zombieY[i]);
			// zombiePicture[i].scaleTo(.5);
		}

		// move hero loop
		while (true) {
			//game over screen, stop code
			if (isColliding(heroPicture, zombiePicture) == true) {
				EZ.addRectangle(512, 384, 800, 300, Color.BLACK, true);
				EZ.removeEZElement(heroPicture);
				EZ.addImage("Loser.jpg", 512, 384);
				break;

			} // press d to move right
			if (EZInteraction.isKeyDown('d')) {
				heroPicture.translateBy(5f, 0f);
			}

			// press d to move down
			if (EZInteraction.isKeyDown('s')) {
				heroPicture.translateBy(0f, 5f);
			}

			// press a to move left
			if (EZInteraction.isKeyDown('a')) {
				heroPicture.translateBy(-5f, 0f);
			}
			// press w to move up
			if (EZInteraction.isKeyDown('w')) {
				heroPicture.translateBy(0f, -5f);
			}
			// zombie attack array
			for (int i = 0; i < zombiePicture.length; i++) {
				// moves zombies right when character is right ofthem
				if (zombieX[i] < heroPicture.getXCenter()) {
					zombieX[i] = zombieX[i] + 1;
				} // moves zombies down then character is above them
				if (zombieY[i] < heroPicture.getYCenter()) {
					zombieY[i] += 1;
				} // moves zombies right when character is right of them
				if (zombieX[i] > heroPicture.getXCenter()) {
					zombieX[i] -= 1;
				}//moves zombies up when character is above them
				if (zombieY[i] > heroPicture.getYCenter()) {
					zombieY[i] -= 1;
				}
				//gives each and every zombie and individual coordinate
				zombiePicture[i].translateTo(zombieX[i], zombieY[i]);
			}

			EZ.refreshScreen();
		}

	}


	//creates collusion areas along x axis for zombies and hero
	public static boolean isOverlapX(EZImage heroPicture, EZImage zombiePicture) {

		// Get hero's width and x coordinate
		int xHero = heroPicture.getXCenter();
		int widthHero = heroPicture.getWidth();

		// Hero's collusion areas along x axis
		int heroLeftSide = xHero - widthHero / 5;
		int heroRightSide = xHero + widthHero / 5;

		// Zombie's width and x coordinate
		int xZombie = zombiePicture.getXCenter();
		int widthZombie = zombiePicture.getWidth();

		// Collusion zones for zombies along x axis
		int zombieLeft = xZombie - widthZombie / 5;
		int zombieRight = xZombie + widthZombie / 5;

		// If hero or zombies collide among x axis
		if (((heroRightSide >= zombieLeft) && (heroLeftSide < zombieLeft))
				|| ((heroLeftSide <= zombieRight) && (heroRightSide > zombieLeft))) {
			return true;
		}
		// anything else is false
		else {
			return false;
		}
	}
	//creates collusion areas along the y axis for hero and zombie
	public static boolean isOverlapY(EZImage heroPicture, EZImage zombiePicture) {
		// Get hero's height and y coordinate
		int yHero = heroPicture.getYCenter();
		int heightHero = heroPicture.getHeight();

		// Hero's collusion areas along y axis
		int heroUp = yHero - heightHero / 4;
		int heroDown = yHero + heightHero / 4;

		// Zombie's height and y coordinate
		int yZombie = zombiePicture.getYCenter();
		int heightZombie = zombiePicture.getHeight();

		// Collusion zones for zombies along y axis
		int zombieUp = yZombie - heightZombie / 4;
		int zombieDown = yZombie + heightZombie / 4;

		// If hero or zombies collide among x axis
		if (((heroDown >= zombieUp) && (heroUp < zombieUp)) || (heroUp <= zombieDown) && (heroDown > zombieUp)) {
			return true;
		}
		// anything else is false
		else {
			return false;
		}

	}

	// combining both functions to show that they are colliding and returning their booleans
	
	public static boolean isColliding(EZImage heroPicture, EZImage[] zombiePicture) {
		for (int i = 0; i < 20; i++) {
			if ((isOverlapX(heroPicture, zombiePicture[i]) == true)
					&& (isOverlapY(heroPicture, zombiePicture[i]) == true)) {

				return true;
			}
		}
		return false;
	}
}