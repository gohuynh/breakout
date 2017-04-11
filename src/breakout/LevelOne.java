package breakout;

/**
 * LevelOne
 * Gordon Huynh
 * Level one of game
 * based off of Level class
 */

import java.util.ArrayList;

public class LevelOne extends Level{
	private final int BOSS_HP = 100;
	private final double SPAWN_RATE = 8;

	@Override
	public void setLevelValues() {
		// TODO Auto-generated method stub
		this.setLevelNumber(1);
		this.setSpawnRate(SPAWN_RATE);
		this.setMaxBossHP(BOSS_HP);
	}
	
	public void setUpBlocks(){
		for(int i = 75 + Level.SCREEN_TOP; i < 350 + Level.SCREEN_TOP; i = i + 75){
			for(int j = 0; j < Breakout.GAME_WIDTH; j = j + Block.BLOCK_WIDTH){
				addBlock(new Block(Block.NORMAL_BLOCK_STRING, j, i));
			}
		}
	}

	@Override
	public void setUpBoss() {
		// TODO Auto-generated method stub
		int width = Breakout.GAME_WIDTH / 3;
		int xPos = (Breakout.GAME_WIDTH - width) / 2;
		ArrayList<Integer> critWidths = new ArrayList<Integer>();
		critWidths.add(width);
		ArrayList<Integer> critPos = new ArrayList<Integer>();
		critPos.add(xPos);
		setBoss(new Boss(BOSS_HP, this.getGroup(),critWidths.size(), critWidths, critPos));
	}
}
