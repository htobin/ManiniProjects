import java.awt.Color;
import java.util.Date;
import java.util.Random;

public class Utility {
	static boolean PLAYERSTURN = false;
	static boolean PLAYERATTACKTURN = true;
	static boolean ENEMYATTACKTURN = false;
	static EZText playerHpText;
	static EZText enemyHpText;
	static long startTime;
	public static EZText dialog = EZ.addText(375, 50, "Press key \'d\' to move right", Color.WHITE, 40);

	EZSound ohNoSound = EZ.addSound("Oh_No.wav");
	EZSound growlSound = EZ.addSound("growl.wav");
	EZGroup battleMenuGroup;
	EZImage battleMenuBg;
	EZRectangle attackBgBox;
	EZText attackText;
	EZRectangle guardBgBox;
	EZText guardText;
	EZRectangle healBgBox;
	EZText healText;
	EZText playerName;
	EZText enemyName;

	EZImage resetBg = EZ.addImage("battleMenuBg.png", 512, 384);
	EZRectangle resetRect = EZ.addRectangle(512, 384, 750, 100, Color.GRAY, true);
	EZText resetText = EZ.addText(512, 384, "you win, play again?", Color.white, 60);
	EZGroup resetGroup = EZ.addGroup();

	Random rand = new Random();

	private boolean playedOhNoSound = false;
	private boolean playedGrowlSound = false;
	static boolean resetButton = false;

	public Utility() {
		resetGroup.addElement(resetBg);
		resetGroup.addElement(resetRect);
		resetGroup.addElement(resetText);
		resetGroup.hide();
	}

	public void playOhNoSound() {
		if (playedOhNoSound == false) {
			ohNoSound.play();
			playedOhNoSound = true;
		}
	}

	public void playGrowlSound() {
		if (playedGrowlSound == false) {
			growlSound.play();
			playedGrowlSound = true;
		}
	}
	
	public void playersAttackTurn() {
		PLAYERSTURN = true;
	}

	// initiate the battle menu
	public void initBattleMenu(Player player, Enemy enemy) {
		dialog.pullToFront();
		battleMenuBg = EZ.addImage("battleMenuBg.png", 510, 900);
		attackBgBox = EZ.addRectangle(140, 850, 120, 70, Color.GRAY, true);
		attackText = EZ.addText(140, 850, "Attack", Color.YELLOW, 40);
		healBgBox = EZ.addRectangle(140, 950, 120, 70, Color.GRAY, true);
		healText = EZ.addText(140, 950, "Heal", Color.YELLOW, 50);
		playerName = EZ.addText(280, 850, player.getName() + ":", Color.BLUE, 40);
		enemyName = EZ.addText(280, 900, enemy.getName() + ":", Color.RED, 40);
		playerHpText = EZ.addText(playerName.getXCenter() + (playerName.getXCenter() / 2), 850,
				String.valueOf(player.getHp()), Color.BLUE, 40);
		enemyHpText = EZ.addText(enemyName.getXCenter() + (enemyName.getXCenter() / 2), 900,
				String.valueOf(enemy.getHp()), Color.RED, 40);

		battleMenuGroup = EZ.addGroup();

		battleMenuGroup.addElement(battleMenuBg);
		battleMenuGroup.addElement(attackText);
		battleMenuGroup.addElement(attackBgBox);
		battleMenuGroup.addElement(healText);
		battleMenuGroup.addElement(healBgBox);
		battleMenuGroup.addElement(playerHpText);
		battleMenuGroup.addElement(playerName);
		battleMenuGroup.addElement(enemyName);
		battleMenuGroup.addElement(enemyHpText);
	}

	// show the battle menu when fighting starts
	public void showBattleMenu() {
		if (battleMenuGroup.getYCenter() > -251) {
			battleMenuGroup.translateBy(0, -4);
		}
	}

	// start the battle sequence
	public void initFight(Enemy enemy, Player player, Weapon PlayerWeapon, Weapon EnemyWeapon) {
		this.playOhNoSound();
		this.showBattleMenu();

		if (battleMenuGroup.getYCenter() == -252) {
			while (player.getHp() > 0 && enemy.getHp() > 0) {
				resetGroup.hide();
				enemy.rotateWings(); // to keep the dragon's wings rotating
				if (PLAYERATTACKTURN == true) {
					allowPlayerBattleChoice(enemy, player, PlayerWeapon);
					startTime = System.currentTimeMillis();
				}
				// 2 second pause before enemy attacks
				if (PLAYERATTACKTURN == false) {
					int battleTicker = (int) (new Date().getTime() - startTime) / 1000;
					if (battleTicker == 2) { // pause for 2 seconds then let the enemy attack
						EnemyWeapon.setEPDestination(enemy, player, -100, -15, 2);
						EnemyWeapon.move(enemy);
						EnemyWeapon.translateTo(-100, 100);
						enemy.attack(player);
						updateMenuStat(player, enemy);
						PLAYERATTACKTURN = true;
						startTime = System.currentTimeMillis();
					}
				}
				resetButton = false;
			}
			// if player loses then show the restart menu
			if (player.getHp() == 0) {
				dialog.setMsg(player.getName() + " has died!");
				resetGroup.show();
				if (resetButton == false) {
					resetText.setMsg("You lose, try again?");
					resetGroup.pullToFront();
					
					resetButton = true;
					resetGroup.show();
				}

				if (resetRect.isPointInElement(EZInteraction.getXMouse(), EZInteraction.getYMouse())) {
					resetRect.setColor(Color.CYAN);
				} else {
					resetRect.setColor(Color.GREEN);
				}

				if (EZInteraction.wasMouseLeftButtonPressed()
						&& resetRect.isPointInElement(EZInteraction.getXMouse(), EZInteraction.getYMouse())) {
					dialog.setMsg("");
					enemy.setHp(50);
					player.setHp(50);
					resetGroup.show();
					updateMenuStat(player, enemy);
				}
				PLAYERATTACKTURN = true;
			}
			// if player wins then show the play again menu
			if (enemy.getHp() == 0) {
				dialog.setMsg(enemy.getName() + " has died!");
				resetGroup.show();
				if (resetButton == false) {
					resetText.setMsg("You win!, play again?");
					resetGroup.pullToFront();
					resetButton = true;
					resetGroup.show();
				}

				if (resetRect.isPointInElement(EZInteraction.getXMouse(), EZInteraction.getYMouse())) {
					resetRect.setColor(Color.CYAN);
				}

				else {
					resetRect.setColor(Color.GREEN);
				}
				if (EZInteraction.wasMouseLeftButtonPressed()
						&& resetRect.isPointInElement(EZInteraction.getXMouse(), EZInteraction.getYMouse())) {
					dialog.setMsg("");
					enemy.setHp(50);
					player.setHp(50);
					resetGroup.show();
					updateMenuStat(player, enemy);
				}
				PLAYERATTACKTURN = true;
			}

		}
	}

	// give the player choices during battle
	public void allowPlayerBattleChoice(Enemy enemy, Player player, Weapon weapon) {
		if (attackBgBox.isPointInElement(EZInteraction.getXMouse(), EZInteraction.getYMouse())) {
			attackBgBox.setColor(Color.RED);
			if (EZInteraction.wasMouseLeftButtonPressed()) {
				weapon.setPEDestination(player, enemy, 27, 0, 2);
				weapon.move(enemy);
				weapon.translateTo(-100, -100);
				player.attack(enemy);
				updateMenuStat(player, enemy);
				PLAYERATTACKTURN = false;
			}
		} else {
			attackBgBox.setColor(Color.GRAY);
		}

		if (healBgBox.isPointInElement(EZInteraction.getXMouse(), EZInteraction.getYMouse())) {
			healBgBox.setColor(Color.RED);
			if (EZInteraction.wasMouseLeftButtonPressed()) {
				if (player.getHp() < player.getStartingHealth()) {
					player.heal();
					dialog.setMsg(player.getName() + " health has incrased.");
					updateMenuStat(player, enemy);
					PLAYERATTACKTURN = false;
				} else {
					dialog.setMsg(player.getName() + " is already at max.");
				}
			}
		} else {
			healBgBox.setColor(Color.GRAY);
		}

	}

	// updates the battle menu with player and enemy stats
	private static void updateMenuStat(Player player, Enemy enemy) {
		playerHpText.setMsg(String.valueOf(player.getHp()));
		enemyHpText.setMsg(String.valueOf(enemy.getHp()));
	}

	public static int Random(int Range) {
		int result;
		Random rand = new Random();
		result = rand.nextInt(Range);
		return result;
	}

}
