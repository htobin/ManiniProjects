import java.awt.Color;
import java.util.Random;


public class Person {
	Random rand = new Random();
	
	private String name;
	private int hp;
	private int damage;
	private int accuracy;
	private EZImage image;
	private int startingHealth;
	
	public Person(String name, int hp, int damage, int accuracy, String image, int x, int y) {
		this.startingHealth = hp;
		this.name = name;
		this.hp = hp;
		this.damage = damage;
		this.image = EZ.addImage(image, x, y);
		// Make sure accuracy is at max 10.
		if(accuracy>10 || accuracy<0 ) {
			throw new IllegalArgumentException("Accuracy should in the range of [0-10].");
        } 
		this.accuracy = accuracy;
	}

	public EZImage getImage() {
		return image;
	}

	public void setImage(EZImage image) {
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}
	
	public void attack(Person victim) { 
		// if random roll between 0-10 is below the character acc stat then successfull hit 
		int hitRoll = rand.nextInt(11);
		if(hitRoll <= this.accuracy) { // if roll within person's acc range 
			Utility.dialog.setMsg("Successful Hit to " + victim.name + "!");
			victim.setHp(victim.getHp() - this.damage);
			if(victim.getHp()<=0) {
				System.out.println("That foo be dead!");
			}
		} else {
			System.out.println("Miss!");
			Utility.dialog.setMsg("Attack Missed to " + victim.name + "!");
		}
	}
	
	// heal player by 5
	public void heal() {
		if(this.hp < this.startingHealth) {			
			this.hp = this.hp+5;
		} else {
			System.out.println("Your health is already full");
		}
	}
	
	public int getStartingHealth() {
		return startingHealth;
	}

	public int getX() {
		return image.getXCenter();
	}
	
	public int getY() {
		return image.getYCenter();
	}
}
