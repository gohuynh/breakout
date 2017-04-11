package breakout;

/**
 * LevelThree
 * Gordon Huynh
 * Level three of game
 * based off of Level class
 */

import java.util.ArrayList;
import java.util.Random;

public class LevelFour extends Level{
	private final int BOSS_HP = 1000;
	private final double SPAWN_RATE = 5;
	private Random myGenerator = new Random();
	
	@Override
	public void setLevelValues() {
		// TODO Auto-generated method stub
		this.setLevelNumber(4);
		this.setSpawnRate(SPAWN_RATE);
		this.setMaxBossHP(BOSS_HP);
	}

	@Override
	public void setUpBlocks() {
		// TODO Auto-generated method stub
		int iCount = 6;
		int sCount = 0;
		int xDefense = 50 + Level.SCREEN_TOP;
		for(int i = 0; i < Breakout.GAME_WIDTH; i = i + Block.BLOCK_WIDTH){
			if(iCount  >= 4){
				addBlock(new Block(Block.INVINCIBLE_BLOCK_STRING, i, xDefense));
			}
			iCount++;
			if(iCount == 8){
				iCount = 0;
			}
			if(sCount  == 3 || sCount == 4){
				addBlock(new Block(Block.SPAWNER_BLOCK_STRING, i, xDefense));
			}
			sCount++;
			if(sCount == 8){
				sCount = 0;
			}
		}
		for(int i = 150 + Level.SCREEN_TOP; i < 400 + Level.SCREEN_TOP; i = i + 75){
			if(i == 275 || i == 425){
				for(int j = Block.BLOCK_WIDTH; j < Breakout.GAME_WIDTH; j = j + 2 * Block.BLOCK_WIDTH){
					addRandomBlock(j, i);
				}
			}
			else {
				for (int j = 0; j < Breakout.GAME_WIDTH; j = j + 2 * Block.BLOCK_WIDTH) {
					addRandomBlock(j, i);
				}
			}
		}
	}

	@Override
	public void setUpBoss() {
		// TODO Auto-generated method stub
		int width = Breakout.GAME_WIDTH / 16;
		int xPos1 = 0;
		int xPos2 = Breakout.GAME_WIDTH/2 - width/2;
		int xPos3 = Breakout.GAME_WIDTH - width;
		ArrayList<Integer> critWidths = new ArrayList<Integer>();
		critWidths.add(width);
		critWidths.add(width);
		critWidths.add(width);
		ArrayList<Integer> critPos = new ArrayList<Integer>();
		critPos.add(xPos1);
		critPos.add(xPos2);
		critPos.add(xPos3);
		setBoss(new Boss(BOSS_HP, this.getGroup(),critWidths.size(), critWidths, critPos));
	}

	private void addRandomBlock(int x, int y){
		if(myGenerator.nextInt(100) < 75){
			addBlock(new Block(Block.NORMAL_BLOCK_STRING, x, y));
		}
		else {
			int tempRoll = myGenerator.nextInt(100);
			if (tempRoll < 40) {
				addBlock(new Block(Block.ICE_BLOCK_STRING, x, y));
			}
			else if (tempRoll < 80) {
				addBlock(new Block(Block.FIRE_BLOCK_STRING, x, y));
			}
			else{
				addBlock(new Block(Block.SPAWNER_BLOCK_STRING, x, y));
			}
		}
	}
}
