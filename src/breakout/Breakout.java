package breakout;
/**
 * Breakout
 * Gordon Huynh
 * Main class of package
 * Controls scenes and transitions
 * All other classes depend on Breakout variables
 */

import java.util.Scanner;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Breakout extends Application{
	public static Breakout sharedInstance;
	
	public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 700;
	public static final int TRANSITION_WIDTH = 600;
	public static final int TRANSITION_HEIGHT = 400;
	
	private Stage stage;
	private Scene scene;
	private Group root;
	
	public Stage stage(){
		return this.stage;
	}

	@Override
	public void start(Stage primaryStage) {
		sharedInstance = this;
		stage = primaryStage;
		stage.setTitle("Breakout");
		root = new Group();
		scene = initialScene();
        stage.setScene(scene);
        stage.show();
    }
	
	public Scene initialScene(){
		Scene startScene = new Scene(root,GAME_WIDTH,GAME_HEIGHT,Color.GRAY);
		BorderPane mainScreen = new BorderPane();
		mainScreen.setMinWidth(GAME_WIDTH);
		
		Label title = new Label("Breakout");
		title.setFont(new Font("Arial",80));
		title.setTextFill(Color.DARKRED);
		BorderPane.setAlignment(title, Pos.TOP_CENTER);
		BorderPane.setMargin(title, new Insets(20,10,10,10));
		mainScreen.setTop(title);
		
		Text instructions = new Text();
		instructions.setFont(new Font("Times New Roman",20));
		Scanner tempScan = new Scanner(getClass().getClassLoader().getResourceAsStream("instructions.txt"));
		String tempString = tempScan.useDelimiter("\\Z").next();
		tempScan.close();
		instructions.setText(tempString);
		instructions.setTextAlignment(TextAlignment.CENTER);
		BorderPane.setAlignment(instructions,Pos.CENTER);
		BorderPane.setMargin(instructions,new Insets(50,10,10,10));
		mainScreen.setCenter(instructions);

		Button startButton = new Button("Play!");
		startButton.setOnAction(e -> gameStart());
		startButton.setFont(new Font("Times New Roman",40));
		BorderPane.setAlignment(startButton,Pos.BOTTOM_CENTER);
		BorderPane.setMargin(startButton, new Insets(50,10,10,10));
		mainScreen.setBottom(startButton);
		
		
		root.getChildren().add(mainScreen);
		return startScene;
	}
	
	public void gameStart(){
		LevelOne myLevel = new LevelOne();
		scene = myLevel.createScene();
		stage.setScene(scene);
		stage.show();
	}
	
	public void moveToLevelTwo(){
		LevelTwo myLevel = new LevelTwo();
		scene = myLevel.createScene();
		stage.setScene(scene);
		stage.show();
	}
	
	public void moveToLevelThree(){
		LevelThree myLevel = new LevelThree();
		scene = myLevel.createScene();
		stage.setScene(scene);
		stage.show();
	}
	
	public void moveToLevelFour(){
		LevelFour myLevel = new LevelFour();
		scene = myLevel.createScene();
		stage.setScene(scene);
		stage.show();
	}
	
	public void gameFinished(){
		Group winGroup = new Group();
		Scene winScene = new Scene(winGroup, TRANSITION_WIDTH, TRANSITION_HEIGHT, Color.LIGHTSKYBLUE);
		
		BorderPane winScreen = new BorderPane();
		winScreen.setMinWidth(TRANSITION_WIDTH);
		
		Label title = new Label("Congratulations,\n you beat the game!");
		title.setTextAlignment(TextAlignment.CENTER);
		title.setFont(new Font("Arial",40));
		title.setTextFill(Color.GOLD);
		BorderPane.setAlignment(title, Pos.TOP_CENTER);
		BorderPane.setMargin(title, new Insets(50,10,10,10));
		winScreen.setTop(title);

		VBox options = new VBox(2);
		options.setMinWidth(TRANSITION_WIDTH);
		options.setPadding(new Insets(10, 10 , 10 , 10));
		options.setSpacing(50);
		options.setAlignment(Pos.CENTER);
		
		Button playAgainButton = new Button("Play Again");
		playAgainButton.setOnAction(e -> start(stage));
		playAgainButton.setFont(new Font("Times New Roman",30));
		options.getChildren().add(playAgainButton);
		
		winScreen.setCenter(options);
		winGroup.getChildren().add(winScreen);
		stage.setScene(winScene);
		stage.show();
	}
	
	public void levelWon(Level l){
		Group winGroup = new Group();
		Scene winScene = new Scene(winGroup, TRANSITION_WIDTH, TRANSITION_HEIGHT, Color.LIGHTSKYBLUE);
		
		BorderPane winScreen = new BorderPane();
		winScreen.setMinWidth(TRANSITION_WIDTH);
		
		Label title = new Label("You Beat the Boss");
		title.setFont(new Font("Arial",60));
		title.setTextFill(Color.GOLD);
		BorderPane.setAlignment(title, Pos.TOP_CENTER);
		BorderPane.setMargin(title, new Insets(50,10,10,10));
		winScreen.setTop(title);

		VBox options = new VBox(2);
		options.setMinWidth(TRANSITION_WIDTH);
		options.setPadding(new Insets(10, 10 , 10 , 10));
		options.setSpacing(50);
		options.setAlignment(Pos.CENTER);
		
		Button continueButton = new Button("Continue");
		if(l.getLevelNumber() == 1){
			continueButton.setOnAction(e -> moveToLevelTwo());
		}
		else if(l.getLevelNumber() == 2){
			continueButton.setOnAction(e -> moveToLevelThree());
		}
		else if(l.getLevelNumber() == 3){
			continueButton.setOnAction(e -> moveToLevelFour());
		}
		else if(l.getLevelNumber() == 4){
			continueButton.setOnAction(e -> gameFinished());
		}
		continueButton.setFont(new Font("Times New Roman",30));
		options.getChildren().add(continueButton);
		
		Button restartButton = new Button("Restart");
		restartButton.setOnAction(e -> start(stage));
		restartButton.setFont(new Font("Times New Roman",30));
		options.getChildren().add(restartButton);
		
		winScreen.setCenter(options);
		winGroup.getChildren().add(winScreen);
		stage.setScene(winScene);
		stage.show();
	}
	
	public void gameOver(Level l){
		Group endGroup = new Group();
		Scene endScene = new Scene(endGroup, TRANSITION_WIDTH, TRANSITION_HEIGHT, Color.BLACK);
		
		BorderPane endScreen = new BorderPane();
		endScreen.setMinWidth(TRANSITION_WIDTH);
		
		Label title = new Label("YOU DIED");
		title.setFont(new Font("Arial",60));
		title.setTextFill(Color.DARKRED);
		BorderPane.setAlignment(title, Pos.TOP_CENTER);
		BorderPane.setMargin(title, new Insets(50,10,10,10));
		endScreen.setTop(title);

		VBox options = new VBox(2);
		options.setMinWidth(TRANSITION_WIDTH);
		options.setPadding(new Insets(10, 10 , 10 , 10));
		options.setSpacing(50);
		options.setAlignment(Pos.CENTER);
		
		Button retryButton = new Button("Retry");
		retryButton.setOnAction(e -> replay(l));
		retryButton.setFont(new Font("Times New Roman",30));
		options.getChildren().add(retryButton);
		
		Button restartButton = new Button("Restart");
		restartButton.setOnAction(e -> start(stage));
		restartButton.setFont(new Font("Times New Roman",30));
		options.getChildren().add(restartButton);
		
		endScreen.setCenter(options);
		endGroup.getChildren().add(endScreen);
		stage.setScene(endScene);
		stage.show();
	}
	
	public void replay(Level l){
		if(l.getLevelNumber() == 1){
			LevelOne myLevel = new LevelOne();
			scene = myLevel.createScene();
		}
		else if(l.getLevelNumber() == 2){
			LevelTwo myLevel = new LevelTwo();
			scene = myLevel.createScene();
		}
		else if(l.getLevelNumber() == 3){
			LevelThree myLevel = new LevelThree();
			scene = myLevel.createScene();
		}
		else if(l.getLevelNumber() == 4){
			LevelFour myLevel = new LevelFour();
			scene = myLevel.createScene();
		}
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args){
		launch(args);
	}
}
