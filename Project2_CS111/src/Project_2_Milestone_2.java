import java.awt.Color;

import java.awt.event.KeyEvent;
import java.util.Random;

public class Project_2_Milestone_2 {

	public static void main(String[] args) {

		// Make the screen
		int screenWidth = 1024;
		int screenHeight = 768;
		EZ.initialize(screenWidth, screenHeight);
		EZ.setBackgroundColor(new Color(250, 250, 250));
		// make a random generator for zombies and health pack
		Random r;
		r = new Random();

		// initialize hero and health pack
		EZImage heroPicture = EZ.addImage("SmallHeroPic.png", 400, 700);
		EZImage health = EZ.addImage("crate.png", r.nextInt(1024), r.nextInt(768));
		EZSound crate = EZ.addSound("crateSound.wav");

		/// Zombie Array for zombie objects
		int zombieIndex = 0;
		Zombie[] zombieArray = new Zombie[20];
		EZSound zombieSpawn = EZ.addSound("fdUp.wav");

		// time stuff
		long currentTime;
		currentTime = System.currentTimeMillis();

		// points, and scoreboard,
		int score = 0;
		EZText scoreKeeper = EZ.addText(10, 10, "0", Color.black, 40);
		EZText wait = EZ.addText(512, 300, "please wait until zombies spawn in 30 sec", Color.black, 40);

		// Barbwire Array for array objects
		int barbAvail = 0;
		int barbIndex = 0;
		int barbNum = 50;
		Barb[] barbArray = new Barb[barbNum];
		EZSound barbDrop = EZ.addSound("barbWireDrop.wav");

		while (true) {
			wait.setMsg("Please wait until the first zombie spawns in 30 sec :)");

			// press d to move right
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
			// drop barb wire with space
			if (EZInteraction.wasKeyReleased(KeyEvent.VK_SPACE)) {
				if (barbIndex < barbNum && barbAvail > 0) {
					barbArray[barbIndex] = new Barb("barb.png", heroPicture.getXCenter(), heroPicture.getYCenter());
					barbIndex++;
					barbAvail--;
					barbDrop.play();

				}
			}
			// if the hero grabs a healthpack
			if (heroPicture.isPointInElement(health.getXCenter(), health.getYCenter())) {
				barbAvail += 5;
				score++;
				scoreKeeper.setMsg(String.valueOf(score));
				health.xCenter = r.nextInt(1024);
				health.yCenter = r.nextInt(768);
				crate.play();

			}

			// make zombies
			// set up new time start
			long nextTime = System.currentTimeMillis();

			// every 30 secs zombie is made
			if ((nextTime - currentTime > 30000) && zombieIndex < 20) {
				EZ.removeEZElement(wait);
				zombieArray[zombieIndex] = new Zombie("zPic.png", r.nextInt(1000), r.nextInt(1));
				currentTime = nextTime;
				zombieIndex++;
				zombieSpawn.play();

			}
			// zombie attack codes
			for (int i = 0; i < zombieIndex; i++) {
				// moves zombies right when character is right of them
				if (zombieArray[i].getX() < heroPicture.getXCenter()) {
					zombieArray[i].attackRight();
				} // moves zombies down then character is above them
				if (zombieArray[i].getY() < heroPicture.getYCenter()) {
					zombieArray[i].attackUp();
				} // moves zombies right when character is right of them
				if (zombieArray[i].getX() > heroPicture.getXCenter()) {
					zombieArray[i].attackLeft();
				} // moves zombies up when character is above them
				if (zombieArray[i].getY() > heroPicture.getYCenter()) {
					zombieArray[i].attackDown();
				}
				// checks if each zombie hits the hero
				if ((zombieArray[i].isInside(heroPicture.getWorldXCenter() - 10, heroPicture.getWorldYCenter() - 10))
						|| (zombieArray[i].isInside(heroPicture.getWorldXCenter() + 10,
								heroPicture.getWorldYCenter() - 10))
						|| (zombieArray[i].isInside(heroPicture.getWorldXCenter() - 10,
								heroPicture.getWorldYCenter() + 10))
						|| (zombieArray[i].isInside(heroPicture.getWorldXCenter() + 10,
								heroPicture.getWorldYCenter() + 10))) {
					// game over
					EZ.removeEZElement(heroPicture);
					EZ.addImage("Loser.jpg", 512, 384);
					EZ.addText(512, 684, "LOSAH", Color.RED, 100);

				}
				// check if each barb wire hits a zombie
				zombieArray[i].regular();
				for (int j = 0; j < barbIndex; j++) {

					if ((zombieArray[i].isInside(barbArray[j].getBarbX() - 10, barbArray[j].getBarbY() - 10))
							|| (zombieArray[i].isInside(barbArray[j].getBarbX() + 10, barbArray[j].getBarbY() - 10))
							|| (zombieArray[i].isInside(barbArray[j].getBarbX() - 10, barbArray[j].getBarbY() + 10))
							|| (zombieArray[i].isInside(barbArray[j].getBarbX() + 10, barbArray[j].getBarbY() + 10))) {
						// slows zombie
						zombieArray[i].slow();

					}

				}

			}

			EZ.refreshScreen();

		}

	}
}
