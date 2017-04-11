package breakout;

//This entire file is part of my masterpiece
//Gordon Huynh

/**
 * Ball
 * Gordon Huynh
 * Class to initialize Ball objects in game
 * Ball can only be ally or enemy balls
 * Ball collisions depend on other classes
 */

import java.util.Random;

import javafx.scene.image.Image;

public class Ball extends BreakoutObject{
	private final String ALLY_BALL_IMAGE = "allyBall.png";
	private final String ENEMY_BALL_IMAGE = "enemyBall.png";
	private final String BUFF_BALL_IMAGE = "buffBall.png";
	private final double topBound = Level.SCREEN_TOP + Level.HITBOX_HEIGHT;
	private final double bottomBound = Breakout.GAME_HEIGHT - Level.HITBOX_HEIGHT;
	private final double ALLY_START_POS = .66;
	private final int NORMAL_SPEED = 8;
	private final int FAST_SPEED = 16;
	private final int SLOW_SPEED = 4;
	private final int ENEMY_HP = 10;
	private final int SPEED_BUFF_DUR = 5;
	private final int DIAMETER = 25;
	
	public static final int CRIT_MULTIPLIER = 2;
	public static final int MAGIC_MULTIPLIER = 2;
	public static final int BALL_DAMAGE = 5;
	public static final int BALL_SPEED = 8;
	
	private boolean ally;
	private boolean buffed;
	private double speedDuration = 0;
	private double[] direction = new double[2];
	private Image regularBall;
	private Image buffedBall;
	private Random RNG = new Random();
	
	
	// Getters and Setters	
	public boolean isAlly(){
		return ally;
	}
	
	public boolean isBuffed(){
		return buffed;
	}
	
	public double getSpeedDuration(){
		return speedDuration;
	}
	
	public void setSpeedDuration(double d){
		speedDuration = d;
	}
	
	// Constructor
	public Ball(boolean allyIn){
		super();
		this.initializeObject(DIAMETER, DIAMETER, NORMAL_SPEED);
		if(allyIn){
			regularBall = new Image(getClass().getClassLoader().getResourceAsStream(ALLY_BALL_IMAGE));
			buffedBall = new Image(getClass().getClassLoader().getResourceAsStream(BUFF_BALL_IMAGE));
			this.setX(Breakout.GAME_WIDTH/2 - width/2);
			this.setY(Breakout.GAME_HEIGHT * ALLY_START_POS);
			hitPoints = -1;
		}
		else{
			regularBall = new Image(getClass().getClassLoader().getResourceAsStream(ENEMY_BALL_IMAGE));
			hitPoints = ENEMY_HP;
			this.setX(RNG.nextDouble() * Breakout.GAME_WIDTH);
			this.setY(topBound);
		}
		this.setImage(regularBall);
		
		ally = allyIn;
		Double initDir = RNG.nextDouble()*2 - 1;
		while(Math.abs(initDir) < .10 || Math.abs(initDir) > .9){
			initDir = RNG.nextDouble()*2 - 1;
		}
		direction[0] = initDir;
		direction[1] = Math.sqrt(1 - Math.pow(direction[0],2));
	}
	
	// Helper methods
	public void updateLocation(){
		if(this.getX() > -speed * direction[0] && this.getX() < Breakout.GAME_WIDTH - width - speed * direction[0]){
			this.setX(this.getX() + speed * direction[0]);
		}
		else{
			if(direction[0] > 0){
				this.setX(Breakout.GAME_WIDTH - width);
				this.bounceToLeft();
			}
			else{
				this.setX(0);
				this.bounceToRight();
			}
			takeDamage();
		}
		if (this.getY() > - speed * direction[1] + topBound && this.getY() < bottomBound - height - speed * direction[1]) {
			this.setY(this.getY() + speed * direction[1]);
		}
		else {
			if (direction[1] > 0) {
				this.setY(bottomBound - height);
				this.bounceUp();
			}
			else {
				this.setY(topBound);
				this.bounceDown();
			}
			takeDamage();
		}
	}
	
	public void bounceToRight(){
		direction[0] = Math.abs(direction[0]);
	}
	
	public void bounceToLeft(){
		direction[0] = -Math.abs(direction[0]);
	}
	
	public void bounceUp(){
		direction[1] = -Math.abs(direction[1]);
	}
	
	public void bounceDown(){
		direction[1] = Math.abs(direction[1]);
	}
		
	public void collision(String source){
		if (source.equals(Block.FIRE_BLOCK_STRING)) {
			speed = FAST_SPEED;
			speedDuration = SPEED_BUFF_DUR;
		}
		else if (source.equals(Block.ICE_BLOCK_STRING)) {
			speed = SLOW_SPEED;
			speedDuration = SPEED_BUFF_DUR;
		}
	}
	
	public void getBuffed(){
		buffed = true;
		this.setImage(buffedBall);
	}
	
	public void deBuff(){
		buffed = false;
		this.setImage(regularBall);
	}
	
	public void updateSpeed(double deltaT){
		if(deltaT > speedDuration){
			speedDuration = 0;
			speed = BALL_SPEED;
		}
		else{
			speedDuration = speedDuration - deltaT;
		}
	}
}
