package breakout;

/**
 * Player
 * Gordon Huynh
 * Class for player hitbox and status
 * depends on Breakout class
 */

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player extends Rectangle{
	private final Color HITBOX_COLOR = Color.LIGHTBLUE;
	private final Color SHIELD_COLOR = Color.GOLD;
	private final int PLAYER_HP = 100;
	private final int SHIELD_AMOUNT = Ball.BALL_DAMAGE * 3;
	private final int HEAL_AMOUNT = Ball.BALL_DAMAGE * 4;

	public static final int PLAYER_HEIGHT = Level.HITBOX_HEIGHT;
	public static final int PLAYER_WIDTH = Breakout.GAME_WIDTH;
	
	private int hitPoints;
	private int shield;
	
	// Setters and Getters
	public int getHP(){
		return hitPoints;
	}
	
	public void setHP(int i){
		hitPoints = i;
	}
	
	// Constructor
	public Player(){
		super(0, Breakout.GAME_HEIGHT - PLAYER_HEIGHT, PLAYER_WIDTH, PLAYER_HEIGHT);
		this.setFill(HITBOX_COLOR);
		hitPoints = PLAYER_HP;
		shield = 0;
	}
	
	// Helper methods
	public void gotHit(Ball b){
		if(!b.isAlly()){
			if(shield > 0){
				shield = shield - Ball.BALL_DAMAGE;
				if(shield == 0){
					this.setFill(HITBOX_COLOR);
				}
			}
			else{
				hitPoints = hitPoints - Ball.BALL_DAMAGE;
			}
		}
	}
	
	public boolean isDead(){
		return hitPoints <= 0;
	}
	
	public void getBuff(String buff){
		if(buff.equals(PowerUp.SHIELD_STRING)){
			shield = SHIELD_AMOUNT;
			this.setFill(SHIELD_COLOR);
		}
		else if(buff.equals(PowerUp.HEALTH_STRING)){
			hitPoints = hitPoints + HEAL_AMOUNT;
			if(hitPoints > PLAYER_HP){
				hitPoints = PLAYER_HP;
			}
		}
	}
}
