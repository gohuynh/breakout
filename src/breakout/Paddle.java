package breakout;

//This entire file is part of my masterpiece
//Gordon Huynh

/**
 * Paddle
 * Gordon Huynh
 * Class to instantiate paddle object in a group
 * Depends on image and Breakout class variables
 */

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class Paddle extends BreakoutObject{
	private final int PADDLE_HEIGHT = 25;
	private final int PADDLE_SPEED = 10;
	private final int PADDLE_WIDTH = 150;
	private final String PADDLE_IMAGE = "paddle.gif";

	// hitboxes for paddle
	private Rectangle left;
	private Rectangle middle;
	private Rectangle right;
	
	// Constructor
	public Paddle(Group myRoot){
		super();
		this.initializeObject(new Image(getClass().getClassLoader().getResourceAsStream(PADDLE_IMAGE)), PADDLE_HEIGHT, PADDLE_WIDTH, PADDLE_SPEED);
		this.setX(Breakout.GAME_WIDTH/2 - width/2);
		this.setY(Breakout.GAME_HEIGHT - 50 - height);
		
		left = new Rectangle(this.getX(), this.getY(), width/3, height);
		middle = new Rectangle(this.getX()+ width/3, this.getY(), width/3, height);
		right = new Rectangle(this.getX() + 2*width/3, this.getY(), width/3, height);
		
		left.setOpacity(0);
		middle.setOpacity(0);
		right.setOpacity(0);
		
		myRoot.getChildren().add(this);
		myRoot.getChildren().add(left);
		myRoot.getChildren().add(middle);
		myRoot.getChildren().add(right);
	}
	
	// Helper Methods	
	public void moveLeft(){
		if(this.getX() >= speed){
			this.setX(this.getX() - speed);
		}
		else{
			this.setX(0);
		}
		updateHitBox();
	}
	
	public void moveRight(){
		if(this.getX() <= Breakout.GAME_WIDTH - width - speed){
			this.setX(this.getX() + speed);
		}
		else{
			this.setX(Breakout.GAME_WIDTH - width);
		}
		updateHitBox();
	}
	
	private void updateHitBox(){
		left.setX(this.getX());
		middle.setX(this.getX() + middle.getWidth());
		right.setX(this.getX() + middle.getWidth() + right.getWidth());
	}
	
	public void hitBall(Ball b){
		// check for hit on side
		if (this.hitOnSide(b)){
			if (b.getRight() > this.getLeft()) {
				b.bounceToLeft();
			} else {
				b.bounceToRight();
			}
		}
		else {
			if(b.intersects(left.getBoundsInLocal())){
				b.bounceToLeft();
			}
			else if(b.intersects(right.getBoundsInLocal())){
				b.bounceToRight();
			}
			if(b.getBottom() < this.getBottom()){
				b.bounceUp();
			}
			else{
				b.bounceDown();
			}
		}
		b.takeDamage();
	}
}
