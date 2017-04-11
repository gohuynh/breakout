package breakout;

/**
 * Level
 * Gordon Huynh
 * Super Class as basic design for levels
 * Depends on Breakout class
 */


import java.util.ArrayList;
import java.util.Random;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

public abstract class Level {
	private final double BLOCK_SPAWNER_RATE = 5;
	private final String HEAL_CHEAT = "HEAL";
	private final String ALLY_CHEAT = "ALLY";
	private final String MAGIC_CHEAT = "MAGIC";
	private final String SHIELD_CHEAT = "SHIELD";
	private final String LEVEL_CHEAT = "LEVELDIGIT";
	private final String KILL_CHEAT = "UPUPDOWNDOWNLEFTRIGHTLEFTRIGHT";
	private final String QUIT_CHEAT = "QUIT";
	
	public static final int SCREEN_TOP = 50;
	public static final int HITBOX_HEIGHT = 10;
	
	private Scene scene;
	private Group root;
	private Random myGenerator = new Random();
	private Timeline timeline;

	private ArrayList<Block> blocks;
	private ArrayList<Block> blocksToRemove;
	private ArrayList<Ball> balls;
	private ArrayList<Ball> ballsToRemove;
	private ArrayList<PowerUp> powerUps;
	private ArrayList<PowerUp> powerUpsToRemove;
	private ArrayList<KeyCode> keyInputs;
	
	private Paddle paddle;
	private Player player;
	private Boss boss;
	private Label playerLabel;
	private Label bossLabel;
	private Label levelLabel;
	
	private int maxBossHP;
	private int maxPlayerHP;
	private int levelNum;
	private double spawnerBlockTime;
	private double spawnTimer;
	private double spawnRate;

	// Setters and Getters
	public void addBlock(Block b) {
		if (blocks == null) {
			blocks = new ArrayList<Block>();
		}
		blocks.add(b);
		root.getChildren().add(b);
	}

	public void addBall(Ball b) {
		if (balls == null) {
			balls = new ArrayList<Ball>();
		}
		balls.add(b);
		root.getChildren().add(b);
	}

	public void addPowerUp(PowerUp p) {
		if (powerUps == null) {
			powerUps = new ArrayList<PowerUp>();
		}
		powerUps.add(p);
		root.getChildren().add(p);
	}

	public void removeBlock(Block b) {
		if (blocks == null) {
			return;
		}
		blocks.remove(b);
		root.getChildren().remove(b);
	}

	public void removeBall(Ball b) {
		if (balls == null) {
			return;
		}
		balls.remove(b);
		root.getChildren().remove(b);
	}

	public void removePowerUp(PowerUp p) {
		if (powerUps == null) {
			return;
		}
		powerUps.remove(p);
		root.getChildren().remove(p);
	}

	public Boss getBoss(){
		return boss;
	}
	
	public void setBoss(Boss b) {
		boss = b;
	}
	
	public Paddle getPaddle() {
		return paddle;
	}

	public void setPaddle(Paddle p) {
		paddle = p;
	}

	public Group getGroup() {
		return root;
	}

	public void setGroup(Group r) {
		root = r;
	}

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene s) {
		scene = s;
	}

	public int getMaxBossHP() {
		return maxBossHP;
	}

	public void setMaxBossHP(int i) {
		maxBossHP = i;
	}

	public int getMaxPlayerHP() {
		return maxPlayerHP;
	}

	public void setMaxPlayerHP(int i) {
		maxPlayerHP = i;
	}

	public int getLevelNumber() {
		return levelNum;
	}

	public void setLevelNumber(int i) {
		levelNum = i;
	}

	public void setSpawnRate(double d) {
		spawnRate = d;
	}
	
	public double getSpawnRate(){
		return spawnRate;
	}

	public Ball getEnemyBall() {
		return new Ball(false);
	}

	public Block getNormalBlock() {
		return new Block("normal");
	}

	// Core methods
	// Set up game area
	public Scene createScene() {
		root = new Group();

		this.addBall(new Ball(true));
		this.setLevelValues();
		this.setUpBlocks();
		this.setUpBoss();
		this.createHUD();
		this.createPlayerPaddle();
		this.setUpPlayerHitBox();
		this.createTimeline();

		scene = new Scene(root, Breakout.GAME_WIDTH, Breakout.GAME_HEIGHT, Color.GRAY);
		if (keyInputs == null) {
			keyInputs = new ArrayList<KeyCode>();
		}
		scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
		scene.setOnKeyReleased(e -> handleKeyRelease(e.getCode()));
		return scene;
	}

	// Initial setup methods
	// Level specific values such as level number, boss health, spawn rate
	public abstract void setLevelValues();

	// Level specific block layout
	public abstract void setUpBlocks();

	// Level specific boss hitbox setup i.e. crit points
	public abstract void setUpBoss();
	
	public void createHUD(){
		HBox infoBar = new HBox(3);
		infoBar.setMinWidth(Breakout.GAME_WIDTH);
		infoBar.setPadding(new Insets(10, 5, 10, 5));
		infoBar.setSpacing(150);

		playerLabel = new Label("Player HP: 100/100");
		playerLabel.setFont(new Font("Arial", 20));
		playerLabel.setTextFill(Color.WHITE);
		infoBar.getChildren().add(playerLabel);

		levelLabel = new Label("Level");
		levelLabel.setFont(new Font("Arial", 20));
		levelLabel.setTextFill(Color.WHITE);
		infoBar.getChildren().add(levelLabel);

		bossLabel = new Label("Boss HP: x/x");
		bossLabel.setFont(new Font("Arial", 20));
		bossLabel.setTextFill(Color.WHITE);
		infoBar.getChildren().add(bossLabel);

		Rectangle backDrop = new Rectangle(0, 0, Breakout.GAME_WIDTH, SCREEN_TOP);
		backDrop.setFill(Color.BLACK);
		root.getChildren().add(backDrop);

		infoBar.toFront();
		root.getChildren().add(infoBar);
	}

	public void createPlayerPaddle() {
		paddle = new Paddle(root);
	}
	
	public void setUpPlayerHitBox() {
		player = new Player();
		root.getChildren().add(player);
		this.setMaxPlayerHP(player.getHP());
	}

	public void createTimeline() {
		KeyFrame frame = new KeyFrame(Duration.millis(Breakout.MILLISECOND_DELAY), e -> step(Breakout.SECOND_DELAY));

		if (timeline == null) {
			timeline = new Timeline();
		}

		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.getKeyFrames().add(frame);
		timeline.play();
	}

	// Methods to run every frame
	public void step(double deltaT) {
		updateInputs();
		updateBalls(deltaT);
		updateBlocks(deltaT);
		updatePowerUps();
		
		checkPaddleCollisions();
		checkBlockCollisions();
		checkPowerUpCollisions();
		checkBossCollisions();
		checkPlayerCollisions();
		
		checkBalls();
		checkBlocks();
		checkPowerUps();
		
		clearObjects();
		spawnBalls(deltaT);
		updateDisplay();
		checkGameEnd();
	}
	
	// Object updates mostly position updates
	// Check for new user key inputs or cheat codes
	public void updateInputs() {
		if (keyInputs != null && keyInputs.size() > 0 && timeline.getStatus() != Animation.Status.PAUSED) {
			KeyCode current = keyInputs.get(0);
			if (current == KeyCode.LEFT) {
				paddle.moveLeft();
			}
			if (current == KeyCode.RIGHT) {
				paddle.moveRight();
			}
		}
		if (timeline.getStatus() == Animation.Status.PAUSED) {
			StringBuilder inputBuilder = new StringBuilder();
			for (int i = 0; i < keyInputs.size(); i++) {
				inputBuilder.append(keyInputs.get(i));
			}
			String inputCode = inputBuilder.toString();
			checkCheat(inputCode);
			keyInputs.clear();
		}
	}

	
	public void updateBalls(double deltaT) {
		if (balls == null) {
			balls = new ArrayList<Ball>();
		}
		for (Ball b : balls) {
			b.updateLocation();
			b.updateSpeed(deltaT);
		}
	}

	// check for spawner blocks to spwan new blocks
	public void updateBlocks(double deltaT) {
		spawnerBlockTime = spawnerBlockTime + deltaT;
		if (spawnerBlockTime >= BLOCK_SPAWNER_RATE) {
			spawnerBlockTime = 0;
			ArrayList<Block> toAdd = new ArrayList<Block>();
			for (Block b : blocks) {
				if (b.getType().equals("spawner")) {
					boolean[] open = { true, true, true, true };
					double[] adjSpacesX = { b.getX(), b.getX() + b.getWidth(), b.getX(), b.getX() - b.getWidth() };
					double[] adjSpacesY = { b.getY() - b.getHeight(), b.getY(), b.getY() + b.getHeight(), b.getY() };
					for (Block blocks : blocks) {
						if (blocks.getX() == b.getX()) {
							if (blocks.getY() == adjSpacesY[0]) {
								open[0] = false;
							} else if (blocks.getY() == adjSpacesY[2]) {
								open[2] = false;
							}
						} else if (blocks.getY() == b.getY()) {
							if (blocks.getX() == adjSpacesX[1]) {
								open[1] = false;
							} else if (blocks.getX() == adjSpacesX[3]) {
								open[3] = false;
							}
						}
					}
					for (int i = 0; i < open.length; i++) {
						if (open[i]) {
							Block temp = this.getNormalBlock();
							temp.setX(adjSpacesX[i]);
							temp.setY(adjSpacesY[i]);
							toAdd.add(temp);
							open[i] = false;
						}
					}
				}
			}
			for (Block b : toAdd) {
				addBlock(b);
			}
		}
	}

	public void updatePowerUps() {
		if (powerUps == null) {
			powerUps = new ArrayList<PowerUp>();
		}
		for (PowerUp p : powerUps) {
			p.updateLocation();
		}
	}

	// Collisions, collisions are dependent on what object was hit i.e. player, boss, block, paddle
	// Each collision is then handled by the object that got hit, which will then affect the ball
	public void checkPowerUpCollisions() {
		for (PowerUp p : powerUps) {
			if (p.intersects(paddle.getBoundsInLocal())) {
				if (p.getType().equals(PowerUp.ALLY_STRING)) {
					this.addBall(new Ball(true));
				} else if (p.getType().equals(PowerUp.MAGIC_STRING)) {
					this.buffBalls();
				} else {
					player.getBuff(p.getType());
				}
				p.setValid(false);
			}
		}
	}

	public void checkPaddleCollisions() {
		for (Ball b : balls) {
			if (b.intersects(paddle.getBoundsInLocal())) {
				paddle.hitBall(b);
			}
		}
	}

	public void checkBlockCollisions() {
		for (Ball ball : balls) {
			for (Block block : blocks) {
				if (ball.isAlly() && ball.intersects(block.getBoundsInLocal())) {
					block.gotHit(ball);
					if (ball.isBuffed()) {
						ball.deBuff();
						for (Block b : blocks) {
							if (block.intersects(b.getBoundsInLocal())) {
								b.takeDamage();
							}
						}
					}
					break;
				}
			}
		}
	}

	public void checkBossCollisions() {
		for (Ball b : balls) {
			if (b.intersects(boss.getBoundsInLocal())) {
				boss.gotHit(b);
			}
		}
	}

	public void checkPlayerCollisions() {
		for (Ball b : balls) {
			if (b.intersects(player.getBoundsInLocal())) {
				player.gotHit(b);
			}
		}
	}

	// Check for dead or used objects
	public void checkBalls() {
		if (ballsToRemove == null) {
			ballsToRemove = new ArrayList<Ball>();
		}
		for (Ball b : balls) {
			if (b.getHP() == 0) {
				ballsToRemove.add(b);
			}
		}
	}

	public void checkBlocks() {
		if (blocksToRemove == null) {
			blocksToRemove = new ArrayList<Block>();
		}
		for (Block b : blocks) {
			if (b.getHP() == 0) {
				blocksToRemove.add(b);
			}
		}
	}

	public void checkPowerUps() {
		if (powerUpsToRemove == null) {
			powerUpsToRemove = new ArrayList<PowerUp>();
		}
		for (PowerUp p : powerUps) {
			if (!p.isValid()) {
				powerUpsToRemove.add(p);
			}
		}
	}

	// Additional tasks
	// Clear out dead objects and handle power up spawns
	public void clearObjects() {
		for (Ball b : ballsToRemove) {
			// 20% for power up to drop from balls
			if (myGenerator.nextInt(100) < 20) {
				this.addPowerUp(new PowerUp(myGenerator.nextInt(100), b.getX(), b.getY()));
			}
			this.removeBall(b);
		}
		ballsToRemove.clear();

		for (Block b : blocksToRemove) {
			// 10% for power up to drop from blocks
			if (myGenerator.nextInt(100) < 10) {
				this.addPowerUp(new PowerUp(myGenerator.nextInt(100), b.getX(), b.getY()));
			}
			this.removeBlock(b);
		}
		blocksToRemove.clear();

		for (PowerUp p : powerUpsToRemove) {
			this.removePowerUp(p);
		}
		powerUpsToRemove.clear();
	}

	// update HUD
	public void updateDisplay() {
		playerLabel.setText("Player HP: " + player.getHP() + "/" + maxPlayerHP);
		bossLabel.setText("Boss HP: " + boss.getHP() + "/" + maxBossHP);
		levelLabel.setText("Level: " + levelNum);
	}

	public void checkGameEnd() {
		if (boss.isDead()) {
			timeline.stop();
			gameWon();
		} else if (player.isDead()) {
			timeline.stop();
			gameLoss();
		}
	}

	
	// Helper Methods
	// User key inputs will put the key pressed in an arrayList
	// There can only be one instance of the key in the arrayList while game is running
	// While paused, the arrayList will keep track of buttons pushed until ENTER is pressed
	public void handleKeyInput(KeyCode e) {
		if (timeline.getStatus() == Animation.Status.PAUSED) {
			if (e == KeyCode.ENTER) {
				updateInputs();
			} else {
				keyInputs.add(e);
			}
		}
		if (timeline != null && e == KeyCode.SPACE) {
			if (timeline.getStatus() == Animation.Status.PAUSED) {
				timeline.play();
				keyInputs.clear();
				levelLabel.setText("Level: " + levelNum);
			} else {
				timeline.pause();
				keyInputs.clear();
			}
		}
		if (!keyInputs.contains(e) && timeline.getStatus() != Animation.Status.PAUSED) {
			keyInputs.add(e);
		}
	}

	// remove buttons that have been let go
	public void handleKeyRelease(KeyCode e) {
		if (keyInputs.size() != 0 && timeline.getStatus() != Animation.Status.PAUSED) {
			keyInputs.remove(e);
		}
	}

	public void buffBalls() {
		for (Ball b : balls) {
			if (b.isAlly()) {
				b.getBuffed();
			}
		}
	}

	public void gameWon() {
		if(levelNum == 4){
			Breakout.sharedInstance.gameFinished();
		}
		else{
			Breakout.sharedInstance.levelWon(this);
		}
	}

	public void gameLoss() {
		Breakout.sharedInstance.gameOver(this);
	}

	// Cheat code handling
	public void checkCheat(String input) {
		String validCheat = null;
		if (input.equals(ALLY_CHEAT)) {
			this.addBall(new Ball(true));
			validCheat = "Ally granted";
		}
		if (input.equals(SHIELD_CHEAT)) {
			player.getBuff(PowerUp.SHIELD_STRING);
			validCheat = "Shield granted";
		}
		if (input.equals(HEAL_CHEAT)) {
			player.getBuff(PowerUp.HEALTH_STRING);
			validCheat = "Health granted";
		}
		if (input.equals(MAGIC_CHEAT)) {
			for (Ball b : balls) {
				b.getBuffed();
			}
			validCheat = "Magic granted";
		}
		if (input.equals(KILL_CHEAT)) {
			boss.setHP(0);
			validCheat = "Kill granted";
		}
		if (input.equals(QUIT_CHEAT)) {
			player.setHP(0);
			validCheat = "Quit granted";
		}
		if(input.equals(LEVEL_CHEAT + 1)){
			Breakout.sharedInstance.gameStart();
		}
		if(input.equals(LEVEL_CHEAT + 2)){
			Breakout.sharedInstance.moveToLevelTwo();;
		}
		if(input.equals(LEVEL_CHEAT + 3)){
			Breakout.sharedInstance.moveToLevelThree();;
		}
		if(input.equals(LEVEL_CHEAT + 4)){
			Breakout.sharedInstance.moveToLevelFour();;
		}
		if (validCheat != null) {
			levelLabel.setText(validCheat);
		}
	}

	public void spawnBalls(double deltaT) {
		spawnTimer = spawnTimer + deltaT;
		if (spawnTimer >= spawnRate) {
			this.addBall(this.getEnemyBall());
			spawnTimer = 0;
		}
	}

}
