package breakout;

//This entire file is part of my masterpiece
//Gordon Huynh

/**
 * PowerUp
 * Gordon Huynh
 * Class to initialize power ups
 * Assumes power up is one of 4: shield, ally, magic, health
 * Only affects ball and player classes
 */

import javafx.scene.image.Image;

public class PowerUp extends BreakoutObject{
	private final String SHIELD_IMAGE = "shield.png";
	private final String ALLY_IMAGE = "sword.png";
	private final String MAGIC_IMAGE = "magic.png";
	private final String HEALTH_IMAGE = "health.png";
	private final int FALL_SPEED = 2;
	private final int DIMENSIONS = 25;
	
	public static final String SHIELD_STRING = "shield";
	public static final String ALLY_STRING = "ally";
	public static final String MAGIC_STRING = "magic";
	public static final String HEALTH_STRING = "health";
	
	private String type;
	private Boolean valid;
	
	// Setters and Getters
	public String getType(){
		return type;
	}
	
	public void setType(String s){
		type = s;
	}
	
	public void setValid(boolean b){
		valid = b;
	}
	
	public boolean isValid(){
		return valid;
	}
	
	// Constructor
	public PowerUp(int randomNum, double x, double y){
		super();
		Image temp;
		if(randomNum < 25){
			type = SHIELD_STRING;
			temp = new Image(getClass().getClassLoader().getResourceAsStream(SHIELD_IMAGE));
		}
		else if(randomNum < 50){
			type = MAGIC_STRING;
			temp = new Image(getClass().getClassLoader().getResourceAsStream(MAGIC_IMAGE));
		}
		else if(randomNum < 75){
			type = ALLY_STRING;
			temp = new Image(getClass().getClassLoader().getResourceAsStream(ALLY_IMAGE));
		}
		else{
			type = HEALTH_STRING;
			temp = new Image(getClass().getClassLoader().getResourceAsStream(HEALTH_IMAGE));
		}
		this.initializeObject(temp, DIMENSIONS, DIMENSIONS, FALL_SPEED);
		this.setX(x);
		this.setY(y);
		valid = true;
	}
	
	// Helper Methods
	public void updateLocation(){
		this.setY(this.getY() + speed);
		if(this.getY() > Breakout.GAME_HEIGHT){
			valid = false;
		}
	}
}
