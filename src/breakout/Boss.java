package breakout;

/**
 * Boss
 * Gordon Huynh
 * Class for boss and manage boss status
 * depends on Breakout class
 */

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Boss extends Rectangle{
	private final Color REGULAR_COLOR = Color.ORANGE;
	private final Color CRIT_COLOR = Color.RED;
	
	public static final int HEIGHT = Level.HITBOX_HEIGHT;
	public static final int WIDTH = Breakout.GAME_WIDTH;
	
	private int hitPoints;
	private ArrayList<Rectangle> critBoxes;

	// Setters and Getters
	public int getHP(){
		return hitPoints;
	}
	
	public void setHP(int i){
		hitPoints = i;
	}
	
	// Constructor
	public Boss(int hp, Group root, int numCrits, ArrayList<Integer> critWidths, ArrayList<Integer> critPos){
		super(0, Level.SCREEN_TOP, WIDTH, HEIGHT);
		this.setFill(REGULAR_COLOR);
		root.getChildren().add(this);
		hitPoints = hp;
		critBoxes = new ArrayList<Rectangle>();
		if (numCrits > 0) {
			Rectangle temp;
			for (int i = 0; i < numCrits; i++) {
				temp = new Rectangle(critPos.get(i), Level.SCREEN_TOP, critWidths.get(i), HEIGHT);
				temp.setFill(CRIT_COLOR);
				critBoxes.add(temp);
				root.getChildren().add(temp);
			}
		}
	}
	
	// Helper methods
	public void gotHit(Ball b){
		if(b.isAlly()){
			int damage = Ball.BALL_DAMAGE;
			for(Rectangle R : critBoxes){
				if(b.intersects(R.getBoundsInLocal())){
					damage = damage * Ball.CRIT_MULTIPLIER;
				}
			}
			if(b.isBuffed()){
				damage = damage * Ball.MAGIC_MULTIPLIER;
				b.deBuff();
			}
			if(damage > hitPoints){
				hitPoints = 0;
			}
			else{
				hitPoints = hitPoints - damage;
			}
		}
	}
	
	public boolean isDead(){
		return hitPoints == 0;
	}
}
