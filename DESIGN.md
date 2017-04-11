


Design of Breakout Game
===================

Gordon Huynh (ghh6)

----------

#### **Design goals of the game**

The goal of the project was to make a functioning breakout variant that contained at least three levels with various blocks, balls, power ups, and cheat codes. Approaching this project, it appeared to rely on various scenes to portray levels and transition screens. For this dependency, I decided I wanted to maintain a single class that managed the flow of the game. When the user finished one level, it would return back to a central class that would then direct the user to next level. The class should also be able to handle game overs, restarting levels, and victory screens. Specifically, the class should only manage scenes though, my goal was to make sure my Breakout class would only serve to manage the flow of the game and not any part of the game itself.

Moving on, another goal for my game was to have the actual levels set up. From my understanding of how other games work and example code I reviewed, games typically have a single environment for each level that sets up it's own objects. With this in mind, I knew that my levels would all have very similar codes if I were to program them each separately. This spurred me to create a super class, Level, that would handle all the main mechanisms to level design and gameplay. All my levels would simply need to extend this class and fill in level specific information and methods. This also allowed easier additions of new levels.

As mentioned, the levels served to handle all the key aspects of the level and gameplay. This entailed that the level would keep track of every game object created in the level and initiate any interactions. With this in mind, all my game objects such as blocks, players, balls, power ups, and hitboxes were created as classes. In most of their cases, they handled their own collisions and interactions independent of the level. The level only served to check any collisions since it had access to every object on screen. Many of my objects such as blocks, balls, and power ups were designed so that new types of these objects could be included without having to make a new class and change other parts of code. 

#### **Adding new features**

Adding new features to the game depends on what features are being added. Currently, extending the existing features of the game such as levels, block types, power ups, and cheat codes is relatively easy and straightforward.

* Adding new levels
 * To add new levels, one must make a new class that extends level and add a few lines to the Breakout class. As I mentioned earlier, my main goal was that only Breakout handles scene transitions and that levels could be added in simply by creating a new subclass of level.
 * In the Breakout class the following additions must be made (mimicking an addition of level 4):
```java
		// add new method to Breakout
		public void moveToLevelFour(){
			LevelFour myLevel = new LevelFour();
			scene = myLevel.createScene();
			stage.setScene(scene);
			stage.show();
		}

		// add the following to levelWon method
		else if(l.getLevelNumber() == 4){
			continueButton.setOnAction(e -> gameFinished());
		}

		// add the following to replay method
		else if(l.getLevelNumber() == 4){
			LevelFour myLevel = new LevelFour();
			scene = myLevel.createScene();
		}
```

* Adding new block types
 * To add new block types, it depends on what abilities the block has. For example, the ice and fire blocks in my game affect the player's balls when they are hit by them. In general, the only class that needs to be changed is the Block class. Here a new type of block can be included and adjust the object to handle its characteristics. Depending on the powers of the block, changes may be needed to be made in the Level class or in other object classes that the power of the block affects.

* Adding new power ups
 * To add new power ups, it is similar to adding in a new block type where you have to add in the power up class the new power up you want to implement. Then depending on the power, you may have to add code in any other object that is being affected by the power. The only difference, is that you must edit the Level class to accommodate the new power up. It is in the level class, that the type of power up is decided.

* Adding a new cheat code
 * Cheat codes exist entirely in the Level class, therefore only changes there are needed generally. Again, depending on what the cheat affects, additional changes may be necessary for other classes that are affected by the cheat.
 * The following code would correctly add in a new power up, the only thing to do would be to change the boss.setHP(0); to something that the new power up is adding to the game.
```java
// to be added in checkCheat method
if (input.equals(KILL_CHEAT)) {
			boss.setHP(0);
			validCheat = "Kill granted";
		}
```

#### Choices and Trade-offs

One of the design choices I mentioned earlier, was the decision to have one class manage all the transitions of the game. I felt this was the more logical choice in making sure the project had a central class that chronologically showed the flow of the game. Most certainly I could have coded into each level where it should lead the user in cases of dying, winning the level, or winning the game. This would have made it easier to add additional levels, by simply adding it into the chain of events. Something along the lines of a linked list, where I have to change where levels pointed to. On the other hand, this would be extremely confusing with larger numbers of levels or swapping levels. In the end, for better flexibility, the choice I made instead allowed every transition be in one central location. The only downside is that additional levels have to be created in their own class and added into the Breakout class. 

Another design choice I made, was having the Level be where most of the game mechanics occur. Since I made it so that the Level class keeps track of every object visible on scene, it made the most sense that would be where I would be handling most of the game mechanics. This involves collisions, power ups, objects spawning, and cheat codes. By having Level handle these mechanics, it made it easier to follow the flow of the game and what updated where. Any outside eyes would see that most events were initiated or even carried out in the Level class; however, this does lead to the issue that additions to the game have to be managed in the Level and in any other relevant object as mentioned earlier on how to add new block types. In retrospect, the other choice could have been to instead, to create helper classes that handled very specific tasks such as interactions between balls and blocks. This would have made it more streamlined in looking for specific areas of code to implement new mechanics. To do accomplish this, it would create large amount of dependencies between classes. With the setting of this project being a breakout game and the finite amount of additions that could be added to my variant, I felt that my choice was the better solution to address the project. By allowing most of the broad logic remain in one class, it allows any user to follow along the flow of the game without having to switch between different classes.

One final major design choice I made was making all the objects in my game their own class that extended the Node class. I felt this choice best abstracted the items in my game and fully utilized the potential of object oriented programming. It seemed to make the most sense since each object had its own specific properties such as health and speed, while also having its own specific actions such as speeding up, bouncing, and buffing up objects. While this takes time implement and manage all instances of objects, I would choose this over manually managing information in arrays. A design approach with arrays would make it easier directly access variable and perform actions, but it seems extremely inefficient and cumbersome to manage dozens of objects.

#### Ambiguities in Breakout

Most of the ambiguities that exist in my project stem from initializing collections or objects. Some of the most obvious ones are in newly created levels. In new levels that correctly extend Level, there is an assumption that the creator will correctly place blocks in the level and create a boss correctly. If neither of these are done properly, there will be a null pointer exception. Adding on to this, new levels also require specific values such as level number and ball spawn rate to be passed in. Forgetting these could prove to be problematic, and I was not too sure how to address this issue well. Mostly, I focused on minimizing the amount of information each level needed to pass into Level; however, this issue does exist when creating new levels.

Another ambiguity that exists, is the fact that in the Level class, some methods have to occur before others.
```java
	// sequential code in Level
	this.addBall(new Ball(true));
	this.setLevelValues();
	this.setUpBlocks();
	this.setUpBoss();
	this.createHUD();
	this.createPlayerPaddle();
	this.setUpPlayerHitBox();
	this.createTimeline();
```
As shown, these methods must be called in this certain order so that arrays are correctly initialized for other methods to work with. I made the assumption of the order of methods when structuring Level in order to avoid constantly checking for existing variables. This made the code much less cluttered while indirectly enforcing the sequence of events in Level. I realize that for larger projects, this would cause more confusion and clutter; however, I made the decision for this project to create a pseudo order so that it would help me process the order of events the game would run through.

One ambiguity that I should mention would be how the game is played. If the user didn't read the README.md, then the game initially would be slightly hard to understand. I decided not to completely explain mechanics in the game thoroughly in the end. In my opinion, I really enjoy having players explore and learn mechanics of the game on their own. There is sufficient information displayed during gameplay to allow players to learn what does what; and for that reason, I left the instructions slightly vague.

The objects in my game also generate another ambiguity. I mentioned before that the Level initiates or even handles some of the aspects of the game. In each object, there are unique methods that each object does on its own; however, I haven't addressed completely what happens when objects need to interact with each other. In my project, I had the notion that the ball was the main factor in any object-object interaction. With this in mind, I made the assumption that any object the ball collided with would then figure out what the ball should do next. The ball solely knows how to move or change direction, not when to change. I made this choice thinking that any new objects added to the game could potentially have different collision mechanics with the ball. Therefore, current and new objects that collide with the ball should handle what happened and direct the ball to its next state.