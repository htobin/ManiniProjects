
import java.awt.Color;
import java.util.Random;

public class Main {
	public static int startX = 100;
	public static int startY = 400;

	public static void main(String[] args) {
		EZ.initialize(1024, 768);
		
		EZImage cat=EZ.addImage("start.png",Level.w/2, Level.h/2);
		EZText start = EZ.addText(Level.w/2, Level.h/2, "Press 's' to Start", Color.GREEN, 100);
		
		
		menu:while(true){
			
			if(EZInteraction.wasKeyPressed('s')){
				
				EZ.removeEZElement(start);
				EZ.removeEZElement(cat);
				break menu;
			}
			EZ.refreshScreen();
		}

		Random rand = new Random();

		Utility util = new Utility(); 
		
		Player player = new Player("Kevin", 50, 10, 6, startX, startY, 32, 48, 10, "schoolboy.png");
		Enemy dragon = new Enemy("Dale", 50, 10, 6, 800, -240, "enemyPicture.png");
		Weapon stick = new Weapon("WoodenStick.png", 5, 2, 2, player.getX(),player.getY());
		Weapon rocket = new Weapon("Rocket.png", 5, 2, 2, -100, -100);
		Weapon fire = new Weapon("fireo.png", 5,2,2,-100,-100);
		rocket.translateTo(-100, -100);
		Level walkingLevel = new Level("space.jpg",Level.w / 2, Level.h / 2);
		Level fightingLevel = new Level("BackgroundDone.jpg", -Level.w / 2,- Level.h / 2);
		Level.firstPart("textBox2.png");
		util.initBattleMenu(player, dragon);

		
		while (true) {
			
			player.go();
			stick.translateTo(player);
			if (player.getX() > 500) {
				walkingLevel.segue(fightingLevel, player,Level.w/2,Level.h/2);
				
				if (player.getX() > 300) {
					player.setAllowWalk(false);
					util.playGrowlSound();
					dragon.moveDown();
					dragon.rotateWings();
					//System.out.println(dragon.getBodyY());
					if (dragon.getBodyY() >= 260) { // once dragon lands
						// initiate fight
						util.initFight(dragon, player, rocket, fire);
					}
				}
			}

			EZ.refreshScreen();
		}

	}

}







