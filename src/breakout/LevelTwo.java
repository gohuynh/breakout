package breakout;

/**
 * LevelTwo
 * Gordon Huynh
 * Level two of game
 * based off of Level class
 */

import java.util.ArrayList;
import java.util.Random;

public class LevelTwo extends Level{
	private final int BOSS_HP = 200;
	private final double SPAWN_RATE = 7;
	private Random myGenerator = new Random();

	@Override
	public void setLevelValues() {
		// TODO Auto-generated method stub
		this.setLevelNumber(2);
		this.setSpawnRate(SPAWN_RATE);
		this.setMaxBossHP(BOSS_HP);
	}

	@Override
	public void setUpBlocks() {
		// TODO Auto-generated method stub
		for(int i = 75 + Level.SCREEN_TOP; i < 350 + Level.SCREEN_TOP; i = i + 75){
			for(int j = 0; j < Breakout.GAME_WIDTH; j = j + Block.BLOCK_WIDTH){
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
		int width = Breakout.GAME_WIDTH / 8;
		int xPos1 = (Breakout.GAME_WIDTH/2 - width) / 2;
		int xPos2 = Breakout.GAME_WIDTH/2 + (Breakout.GAME_WIDTH/2 - width)/2;
		ArrayList<Integer> critWidths = new ArrayList<Integer>();
		critWidths.add(width);
		critWidths.add(width);
		ArrayList<Integer> critPos = new ArrayList<Integer>();
		critPos.add(xPos1);
		critPos.add(xPos2);
		setBoss(new Boss(BOSS_HP, this.getGroup(),critWidths.size(), critWidths, critPos));
	}
}
