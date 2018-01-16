
public class Player extends Person {
	private EZImage playerSprite;
	private int x = 0;				// Position of Sprite
	private int y = 0;
	private int spriteWidth;		// Width of each sprite
	private int spriteHeight;		// Height of each sprite
	private int direction = 0;		// Direction character is walking in
	private int walkSequence = 0;	// Walk sequence counter
	private int cycleSteps;			// Number of steps before cycling to next animation step
	private int counter = 0;		// Cycle counter
	private boolean allowWalk = true;

	public Player(String name, int hp, int damage, int accuracy, int x, int y, int width, int height, int steps, String spriteFile) {
		super(name, hp, damage, accuracy, spriteFile, x, y);
		this.x = x;
		this.y = y;
		this.spriteWidth = width;
		this.spriteHeight = height;
		this.cycleSteps = steps;
		setImagePosition();
	}
	
	// This is so we can stop the player when he gets to a certain point
	public void setAllowWalk(boolean x) {
		this.allowWalk = x;
	}
	
	public void translatePlayer(int x, int y) {
		this.x = x;
		this.y = y;
		setImagePosition();
	}
	
	private void setImagePosition() {
		this.getImage().translateTo(x,y);
		this.getImage().setFocus(walkSequence * spriteWidth, direction, walkSequence * spriteWidth + spriteWidth, direction + spriteHeight);
	}
	
	private void moveLeft(int stepSize) {
		x = x - stepSize;
		direction = spriteHeight;
		if ((counter % cycleSteps) == 0) {
			walkSequence--;
			if (walkSequence < 0)
				walkSequence = 3;
		}
		counter++;
		setImagePosition();
	}

	private void moveRight(int stepSize) {
		x = x + stepSize;
		direction = spriteHeight * 2;
		
		if ((counter % cycleSteps) == 0) {
			walkSequence++;
			if (walkSequence > 3)
				walkSequence = 0;
		}
		counter++;

		setImagePosition();
	}
	
	// Keyboard controls for moving the character.
	public void go() {
		if(allowWalk == true) {
			if (EZInteraction.isKeyDown('a')) {
				moveLeft(2);
			} else if (EZInteraction.isKeyDown('d')) {
				moveRight(2);
			}
		}
	}
	
}
