import java.util.ArrayList;

public class Enemy extends Person {
	private EZSound growl;
	EZGroup leftArmGrp;
	EZGroup rightArmGrp;
	double angle = 0;
	EZGroup bodyGrp;
	private int initX;
	private int initY;

	public Enemy(String name, int hp, int damage, int accuracy, int x, int y, String imageFile) {
		super(name, hp, damage, accuracy, imageFile, x, y);
		this.initX = x;
		this.initY = y;
		growl = EZ.addSound("growl.wav");

		int armWidth = 100;
		int armHeight = 150;
		leftArmGrp = EZ.addGroup();
		rightArmGrp = EZ.addGroup();
		bodyGrp = EZ.addGroup();

		bodyGrp.addElement(leftArmGrp);
		bodyGrp.addElement(rightArmGrp);

		EZImage leftArm = EZ.addImage("leftww.png", -100, armHeight / 2);
		leftArmGrp.addElement(leftArm);

		EZImage rightArm = EZ.addImage("wing.png", 100, armHeight / 2);
		rightArmGrp.addElement(rightArm);

		bodyGrp.addElement(this.getImage());
		bodyGrp.pullToFront();
		leftArmGrp.pushToBack();
		rightArmGrp.pushToBack();
		int bodyWidth = x;
		int bodyHeight = y;
		leftArmGrp.translateTo(bodyWidth, y);
		rightArmGrp.translateTo(bodyWidth, y);
		double angle = 0;
	}

	public int getGroupY() {
		return this.bodyGrp.getYCenter();
	}

	public int getBodyX() {
		
		return this.bodyGrp.getXCenter() + this.initX;
	}
	
	public int getBodyY() {
		return this.bodyGrp.getYCenter() + this.initY;
	}
	
	public void translate(int x, int y){
		bodyGrp.translateBy(x, y);
	}
	
	public void moveDown() {
		if(bodyGrp.getYCenter()<500) {			
			bodyGrp.translateBy(0, 5);
		}
		
	}
	
	public void rotateWings() {

		// Get the arms to sway side to side with a cos function.
		leftArmGrp.rotateTo((int) (Math.cos(Math.toRadians(angle)) * 40) + 55);
		rightArmGrp.rotateTo((int) (-Math.cos(Math.toRadians(angle)) * 30) - 55);

		// leftLegGrp.rotateTo((int) (Math.cos(Math.toRadians(angle))*30)+45);
		// rightLegGrp.rotateTo((int) (-Math.cos(Math.toRadians(angle))*30)-45);

		// Lastly move the robot to wherever your mouse is pointing. Notice when
		// the robot moves
		// the arms and head remain attached.

		angle += 2;

		EZ.refreshScreen();

	}

}
