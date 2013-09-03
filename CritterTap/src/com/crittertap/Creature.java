package com.crittertap;

import android.graphics.Rect;

import com.crittertap.framework.Graphics;
import com.crittertap.framework.Pixmap;

import java.util.Random;

public class Creature {
	public enum creatureState {
		hidden,
		popped,
		wacked
	};
	
	private Random random ;
	
	public creatureState currentCreatureState = creatureState.hidden; 
	public float poppedTimer;
	public float hiddenTimer;
	public float wackedTimer;
	public int xPos;
	public int yPos;
	public Rect creatureRect;
	public boolean isPopped = false;
	
	public Pixmap creaturePixmap;
	public Creature (int x, int y) {
		
		
		
		random = new Random();
		
		xPos = x;
		yPos = y;
		hiddenTimer = random.nextInt(1) + 2;
		poppedTimer = random.nextInt(1) + 2;
		
		creatureRect = new Rect(xPos,yPos,xPos + 96, yPos+ 96);
		
		
		int tempInt = random.nextInt(3);
		
		
		
		
		switch (tempInt){
		case 0:
			creaturePixmap = Assets.cat;
			break;
		case 1:
			creaturePixmap = Assets.fox;
			break;
		case 2:
			creaturePixmap = Assets.mole;
			break;
		case 3:
			creaturePixmap = Assets.dog;
			break;
		}
		
		
	}
	
	public void update(float deltaTime) {
		
		switch(currentCreatureState){
		case hidden:
			if(hiddenTimer >= 0){
				hiddenTimer -= deltaTime;
			} else {
				currentCreatureState = creatureState.popped;
				hiddenTimer = random.nextInt(11) + 10;
			}
			break;
		case popped:
			if(poppedTimer >= 0){
				poppedTimer -= deltaTime;
			} else {
				currentCreatureState = creatureState.hidden;
				poppedTimer = random.nextInt(11) + 10;
			}
			break;
		case wacked:
			if(wackedTimer >= 0){
				wackedTimer -= deltaTime;
			} else {
				currentCreatureState = creatureState.hidden;
				wackedTimer = 2;
			}
			break;
		}
		
	}
	
	public void draw(Graphics g) {
		
		switch(currentCreatureState){
		case hidden:
			g.drawPixmap(creaturePixmap, xPos, yPos, 0, 0, 96, 96);
			break;
		case popped:
			g.drawPixmap(creaturePixmap, xPos, yPos, 0, 96, 96, 96);
			break;
		case wacked:
			g.drawPixmap(creaturePixmap, xPos, yPos, 0, 192, 96, 96);
			break;
		}
		
	}
	
}