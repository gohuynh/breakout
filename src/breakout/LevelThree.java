package breakout;

/**
 * LevelThree
 * Gordon Huynh
 * Level three of game
 * based off of Level class
 */

import java.util.ArrayList;
import java.util.Random;

public class LevelThree extends Level{
	private final int BOSS_HP = 500;
	private final double SPAWN_RATE = 6;
	private Random myGenerator = new Random();
	
	@Override
	public void setLevelValues() {
		// TODO Auto-generated method stub
		this.setLevelNumber(3);
		this.setSpawnRate(SPAWN_RATE);
		this.setMaxBossHP(BOSS_HP);
	}

	@Override
	public void setUpBlocks() {
		// TODO Auto-generated method stub
		for(int i = Breakout.GAME_WIDTH/2 - Block.BLOCK_WIDTH * 2; i < Breakout.GAME_WIDTH/2 + Block.BLOCK_WIDTH * 2; i = i + Block.BLOCK_WIDTH){
			addBlock(new Block(Block.INVINCIBLE_BLOCK_STRING, i, 50 + Level.SCREEN_TOP));
		}
		for(int i = 150 + Level.SCREEN_TOP; i < 350 + Level.SCREEN_TOP; i = i + 75){
			for(int j = 0; j < Breakout.GAME_WIDTH; j = j + Block.BLOCK_WIDTH){
				if(i == 275){
					if(j < Block.BLOCK_WIDTH * 2 || j >= Breakout.GAME_WIDTH - Block.BLOCK_WIDTH * 2){
						addBlock(new Block(Block.INVINCIBLE_BLOCK_STRING, j, i));
						continue;
					}
					if(j == Breakout.GAME_WIDTH / 2 - Block.BLOCK_WIDTH || j == Breakout.GAME_WIDTH / 2 ){
						addBlock(new Block(Block.SPAWNER_BLOCK_STRING, j, i));
						continue;
					}
				}
				if(myGenerator.nextInt(100) < 75){
					addBlock(new Block(Block.NORMAL_BLOCK_STRING, j, i));
				}
				else if(myGenerator.nextInt(100) < 50){
					addBlock(new Block(Block.ICE_BLOCK_STRING, j, i));
				}
				else{
					addBlock(new Block(Block.FIRE_BLOCK_STRING, j, i));
				}
			}
		}
	}

	@Override
	public void setUpBoss() {
		// TODO Auto-generated method stub
		int width1 = Breakout.GAME_WIDTH / 8;
		int width2 = Breakout.GAME_WIDTH / 16;
		int xPos1 = Breakout.GAME_WIDTH/4 - width2/2;
		int xPos2 = Breakout.GAME_WIDTH/2 - width1/2;
		int xPos3 = Breakout.GAME_WIDTH * 3 / 4 - width2/2;
		ArrayList<Integer> critWidths = new ArrayList<Integer>();
		critWidths.add(width2);
		critWidths.add(width1);
		critWidths.add(width2);
		ArrayList<Integer> critPos = new ArrayList<Integer>();
		critPos.add(xPos1);
		critPos.add(xPos2);
		critPos.add(xPos3);
		setBoss(new Boss(BOSS_HP, this.getGroup(),critWidths.size(), critWidths, critPos));
	}
}
