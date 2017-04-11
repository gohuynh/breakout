package breakout;

//This entire file is part of my masterpiece
//Gordon Huynh

/**
 * Block
 * Gordon Huynh
 * Class to initialize block objects
 * Assumes block is of 5 types: normal, ice, fire, spawner, invincible
 */

import javafx.scene.image.Image;

public class Block extends BreakoutObject{
	private final int NORMAL_BLOCK_HEALTH = 1;
	private final int SPEED_BLOCK_HEALTH = 3;
	private final int SPAWNER_BLOCK_HEALTH = 5;
	private final int INVINCIBLE_BLOCK_HEALTH = -1;
	private final String NORMAL_BLOCK_IMAGE = "brick3.gif";
	private final String FIRE_BLOCK_IMAGE = "brick10.gif";
	private final String ICE_BLOCK_IMAGE = "brick1.gif";
	private final String SPAWNER_BLOCK_IMAGE = "brick2.gif";
	private final String INVINCIBLE_BLOCK_IMAGE = "brick5.gif";
	
	public static final int BLOCK_WIDTH = 50;
	public static final int BLOCK_HEIGHT = 25;
	public static final String NORMAL_BLOCK_STRING = "normal";
	public static final String FIRE_BLOCK_STRING = "fire";
	public static final String ICE_BLOCK_STRING = "ice";
	public static final String SPAWNER_BLOCK_STRING = "spawner";
	public static final String INVINCIBLE_BLOCK_STRING = "invincible";
	
	private String type;
	
	// setters and getters
	public void setType(String s){
		type = s;
	}
	
	public String getType(){
		return type;
	}
	
	// Constructors
	public Block(String inType, double x, double y){
		super();
		type = inType;
		String imageSource = "";
		if(type.equals(NORMAL_BLOCK_STRING)){
			hitPoints = NORMAL_BLOCK_HEALTH;
			imageSource = NORMAL_BLOCK_IMAGE;
		}
		else if(type.equals(FIRE_BLOCK_STRING)){
			hitPoints = SPEED_BLOCK_HEALTH;
			imageSource = FIRE_BLOCK_IMAGE;
		}
		else if(type.equals(ICE_BLOCK_STRING)){
			hitPoints = SPEED_BLOCK_HEALTH;
			imageSource = ICE_BLOCK_IMAGE;
		}
		else if(type.equals(SPAWNER_BLOCK_STRING)){
			hitPoints = SPAWNER_BLOCK_HEALTH;
			imageSource = SPAWNER_BLOCK_IMAGE;
		}
		else if(type.equals(INVINCIBLE_BLOCK_STRING)){
			hitPoints = INVINCIBLE_BLOCK_HEALTH;
			imageSource = INVINCIBLE_BLOCK_IMAGE;
		}
		this.initializeObject(new Image(getClass().getClassLoader().getResourceAsStream(imageSource)), BLOCK_HEIGHT, BLOCK_WIDTH);
		this.setX(x);
		this.setY(y);
	}
	
	public Block(String inType){
		this(inType, 0, 0);
	}
	
	public void gotHit(Ball b){
		takeDamage();
		if(this.hitOnSide(b)){
			if (b.getRight() > this.getLeft()) {
				b.bounceToLeft();
			} else {
				b.bounceToRight();
			}
		}
		else {
			if(b.getBottom() < this.getBottom()){
				b.bounceUp();
			}
			else{
				b.bounceDown();
			}
		}
		b.collision(type);
	}
}
