Project:    Breakout Game

Author:     Gordon Huynh

Start: 1/16/2017 End: 1/22/2017

Hours: ~25

Resources:

	-docs.oracle.com
	-stackoverflow.com

Files:

	-Breakout (main)
	-Ball
	-Block
	-Boss
	-Level
	-LevelOne
	-LevelTwo
	-LevelThree
	-LevelFour
	-Paddle
	-Player
	-PowerUp

Other Files:

	-allyBall.png
	-ball.gif
	-brick1.gif
	-brick2.gif
	-brick3.gif
	-brick5.gif
	-brick10.gif
	-buffBall.png
	-enemyBall.png
	-health.png
	-intstructions.txt
	-magic.png
	-paddle.gif
	-shield.png
	-sword.png

Program info:

		SPACE to pause the game. Left and Right arrow keys to move paddle. Left edge of paddle will force ball to bounce leftwards, right 
	edge will force ball to bounce rightwards. Middle of paddle is neutral. Bottom of screen is player's hitbox, only enemy balls can 
	damage the player. Player hitbox will change to gold if shield is active. Top of screen is boss's hitbox. Only player's balls can damage 
	boss. Red areas are critical points and if hit, boss will take extra damage. Blocks are placed in specific positions per level; however 
	the type 	of spawns can be random or predetermined.
		There are 5 types of blocks: normal, ice, fire, spawner, and invincible. Blocks can only take damage from player's balls. normal 
	blocks only have 1 hp. Fire and ice blocks have 3 hp, and will speed up and slow down the ball respectively for a set duration. Spawner 
	blocks have 5 hp and will spawn normal blocks around it at set intervals. Invincible blocks cannot be destroyed. There are two types of 
	balls, enemy balls which spawn at set intervals at the top of the screen. These balls ignore block collisions and will be destroyed 
	after a set number of bounces. Ally balls cannot be destroyed. Only one is spawned at the beginning, and additional ones from collecting 
	a power up. Power ups can be dropped from destroyed blocks or destroyed enemy balls. There are 4 types: ally, shield, health, and magic. 
	Ally will spawn a new ally ball. Shield will give the player a shield that can block the next three hits from enemy balls. Shields do 
	NOT stack. Health will heal the player 20 hp but will not go over max hp. Magic will buff all player's balls for their next bounce on a 
	block or boss. Hits on a block, will chain to any adjacent block and cause the same damage. Hits on the boss will do extra damage in 
	addition to where the ball hit(the magic will stack with a critical strike).

Bugs:

	Occasional lag when a spawner spawns new blocks or an enemy ball is spawned
	
Extra Features:

		While the game is paused, user can enter cheat codes and press ENTER to apply the cheat. For example typing in "ally" then pressing 
	enter will grant the user an immediate ally power up. If mistyped, user can press ENTER to reset the input. There are 7 cheats in the 
	game. "ally" was explained in the example. "shield" grants a shield power up, "magic" grants a magic power up, and "health" grants a 
	health power up. The arrow key input, "up up down down left right left right" will instantly kill the current boss. "quit" will 
	immediately game over the level. "level" + a number (1-4) will move the user to that level, for example the input "level2" will move the 
	user to level 2.
	
Impressions:

		This was a fun project to get ideas of how software design works. Only difficulties were finding good resources and tutorials to 
	make a javaFX game. If I was to do this all over again without my current understanding, I would have greatly appreciated a lab or a 
	smaller project that highlighted the core components to a javaFX game.