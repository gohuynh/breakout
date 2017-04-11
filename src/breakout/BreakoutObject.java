package breakout;

import javafx.scene.image.Image;

// This entire file is part of my masterpiece
// Gordon Huynh

import javafx.scene.image.ImageView;

public abstract class BreakoutObject extends ImageView{
	protected double height;
	protected double width;
	protected int speed;
	protected int hitPoints;
	
	
	// Setters and getters
	public void setHeight(double i){
		height = i;
	}
	
	public double getHeight(){
		return height;
	}
	
	public void setWidth(double i){
		width = i;
	}
	
	public double getWidth(){
		return width;
	}
	
	public void setSpeed(int i){
		speed = i;
	}
	
	public int getSpeed(){
		return speed;
	}
	
	public void setHP(int i){
		hitPoints = i;
	}
	
	public int getHP(){
		return hitPoints;
	}
	
	// Get values of edges of paddle
	public double getTop(){
		return this.getY();
	}
	
	public double getBottom(){
		return this.getY() + height;
	}
	
	public double getLeft(){
		return this.getX();
	}
	
	public double getRight(){
		return this.getX() + width;
	}
	
	// Set up image
	public void initializeObject(Image i, double heightIn, double widthIn, int speedIn){
		this.setImage(i);
		this.setFitHeight(heightIn);
		this.setHeight(heightIn);
		this.setFitWidth(widthIn);
		this.setWidth(widthIn);
		speed = speedIn;
	}
	
	public void initializeObject(Image i, double heightIn, double widthIn){
		initializeObject(i, heightIn, widthIn, 0);
	}
	
	public void initializeObject(double heightIn, double widthIn, int speedIn){
		initializeObject(null, heightIn, widthIn, speedIn);
	}
	
	// Helper methods
	public void takeDamage(){
		if(hitPoints > 0){
			hitPoints--;
		}
	}
	
	public boolean isDead(){
		return hitPoints == 0;
	}
	
	public boolean hitOnSide(BreakoutObject b){
		return !(b.getRight() < this.getRight() + b.getWidth() - 1 && b.getLeft() > this.getLeft() - b.getWidth() + 1);
	}
}
