package com.crittertap;

import java.util.ArrayList;
import java.util.List;



public class World {
	
	private static final float TICK_INITIAL = 0.5f;
	
	//private static final float TICK_DECREMENT = 0.05f;
	private float tickTime;
	private float tick;
	
	public boolean gameOver;
	public int score;
	
	
	
	public List<Creature> creaturesArray = new ArrayList<Creature>();
	
	
	
	
	public World() {
		Setup();
	}
	
	public void Setup() {
		tickTime = 0.0f;
		tick = TICK_INITIAL;
		gameOver = false;
		score = 0;
		
		
		
		for(int i=0; i <=8; i++) {
			
			if(i <= 2){
				Creature tempCreature = new Creature((96 * i) + 19, 86 );
				creaturesArray.add(tempCreature);
			}
			if(i >= 3 && i <= 5){
				Creature tempCreature = new Creature((96 * (i-3)) + 19, 180);
				creaturesArray.add(tempCreature);
			}
			if(i >= 6 && i <= 8){
				Creature tempCreature = new Creature((96 * (i-6)) + 19, 276);
				creaturesArray.add(tempCreature);
			}
			
		}
	}
	
	public void update(float deltaTime) {
		if(gameOver) {
			return;
		}
		tickTime += deltaTime;
		
		while(tickTime > tick) {
			tickTime -= tick;
		}
	}
}