package com.crittertap;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.Editable;
import android.widget.EditText;

import com.crittertap.framework.Game;
import com.crittertap.framework.Graphics;
import com.crittertap.framework.Screen;
import com.crittertap.framework.Input.TouchEvent;

public class GameScreen extends Screen
{
	private enum GameState {
		Ready,
		Running,
		Paused,
		GameOver
	}
	GameState state = GameState.Ready;
	World world;
	
	int oldScore = 0;
	String score = "0";
	private static final float LEVEL_TIME = 61.0f;
	public float levelTimer;
	String levelTime = " ";
	String playerName = " ";
	
	public static AlertDialog.Builder alert;
	public static EditText input;
	public static String inputString = "";
	
	public GameScreen(Game game) {
		super(game);
		world = new World();
		levelTimer = LEVEL_TIME;
	}
	
	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();
		
		switch(state) {
		case Ready :
			UpdateReady(touchEvents);
			break;
		case Running :
			UpdateRunning(touchEvents, deltaTime);
			break;
		case Paused :
			UpdatePaused(touchEvents);
			break;
		case GameOver :
			UpdateGameOver(touchEvents);
		}
	}
	private void UpdateReady(List<TouchEvent> touchEvents) {
		if(touchEvents.size() > 0) {
			state = GameState.Running;
		}	
	}
	private void UpdateRunning(List<TouchEvent> touchEvents, float deltaTime) {
		//Check for the touch events;
		int len = touchEvents.size(); //Gets the amount of touch events that 
		//Cycle through the events
		for(int i=0; i<len; i++) {
			if(touchEvents.size() > 0){
				TouchEvent event = touchEvents.get(0); //Get the event 
				//Check if paused was touched 
				if(event.type == TouchEvent.TOUCH_UP) {
					if(event.x < 64 && event.y < 64) {
						if(Settings.soundEnabled) {
							Assets.click.play(1);
						}
						state = GameState.Paused;
						return;		
					}
				
					for(Creature tempCreature : world.creaturesArray){	
						if(tempCreature.currentCreatureState == Creature.creatureState.popped && 
								tempCreature.creatureRect.contains(event.x, event.y)){
								world.score += 10;
								tempCreature.currentCreatureState = Creature.creatureState.wacked;
							return;
						}
					}
				}
			}
		}
		
		for(Creature tempCreature : world.creaturesArray){
			tempCreature.update(deltaTime);
		}
		
		levelTimer -= deltaTime;
		levelTime = "" + (int)levelTimer;
		if(levelTimer <= 0) {
			world.gameOver = true;
		}
			
		//Update the world
		world.update(deltaTime);
		//Check if game is over
		if(world.gameOver) {
			if(Settings.soundEnabled){
				//Play game over sound
				Assets.click.play(1);
			}
				//Get user input
				//playerName = GetUserName();
				//Update Local Score
				Settings.addscore(world.score);
				//Update Global Score
				Settings.addGlobalScore(world.score);
				//Save settings
				Settings.save(game.getFileIO());
				//Switch game state to game over
				state = GameState.GameOver;
		}
		//Update the score if changed
		if(oldScore != world.score) {
			oldScore = world.score;
			score = "" + oldScore;
			//This is where we can play the "Wack" sound
			if(Settings.soundEnabled) {
				Assets.click.play(1);
			}	
		}
	}
	
	private String GetUserName(){
	    

		alert = new AlertDialog.Builder(game.getContext());
			alert.setTitle("HighScore");
			alert.setMessage("Enter your name");
			// Set an EditText view to get user input 
			input = new EditText(game.getContext());
			alert.setView(input);
			alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					inputString = input.getText().toString();
				}
			});
			
			alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() { 
				public void onClick(DialogInterface dialog, int whichButton) {   
					// Canceled.  
				}
			});
			
			alert.show();
		
		
		
		return inputString;
		
	}
	
	private void UpdatePaused(List<TouchEvent> touchEvents) {
		//Check for the touch events;
		int len = touchEvents.size(); //Gets the amount of touch events that 
		//Cycle through the events
		for(int i=0; i<len; i++) {
			TouchEvent event = touchEvents.get(i); //Get the event 
			//Check if paused was touched 
			if(event.type == TouchEvent.TOUCH_UP) {
				//Check "Continue" button
				if(event.x < 80 && event.x <= 148) {
					//Play sound
					if(Settings.soundEnabled) {
						Assets.click.play(1);
					}
					//Switch back to playing state
					state = GameState.Running;
					return;
				}
				//Check the "Quit" button
				if(event.y > 148 && event.y < 196) {
					//Play Sound
					if(Settings.soundEnabled){
						Assets.click.play(1);
					}
					//Switch to the Main Menu
					game.setScreen(new MainMenuScreen(game));
					return;
				}
			}
		}	
	}
	
	private void UpdateGameOver(List<TouchEvent> touchEvents) {
		//Check for the touch events;
		int len = touchEvents.size(); //Gets the amount of touch events that 
		//Cycle through the events
		for(int i=0; i<len; i++) {
			TouchEvent event = touchEvents.get(i); //Get the event 
			//Check if paused was touched 
			if(event.type == TouchEvent.TOUCH_UP) {
				//Check "Main Menu" button
				if(event.x >= 128 && event.x <= 192 && event.y >= 200 && event.y < 264) {
					//Play Sound
					if(Settings.soundEnabled){
						Assets.click.play(1);
					}
					//Switch to the Main Menu
					game.setScreen(new HighScoreScreen(game));
							//new MainMenuScreen(game));
					return;
				}
			}
		}
	}
	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();
		g.drawPixmap(Assets.background, 0, 0);
		drawWorld(world);
		
		switch(state){
		case Ready :
			drawReadyUI();
			break;
		case Running :
			drawRunningUI();
			break;
		case Paused :
			drawPausedUI();
			break;
		case GameOver :
			drawGameOverUI();
			break;			
		}
		
		
	}
	
	private void drawWorld(World world) {
		Graphics g = game.getGraphics();
		
		
	}
	
	private void drawReadyUI() {
		Graphics g = game.getGraphics();
		g.drawPixmap(Assets.ready, 47, 100);
		g.drawLine(0, 416, 480, 416, Color.BLACK);
	}
	
	private void drawRunningUI() {
		Graphics g = game.getGraphics();
		g.drawPixmap(Assets.buttons, 0, 0, 64, 128, 64, 64);
				
		g.drawPixmap(Assets.stars, (g.getWidth() /2) - ((score.length() * 20) + (Assets.stars.getWidth()) /2) + 110, 20);
		drawText(g, score, (g.getWidth() /2) - ((score.length() * 20) /2) + 110, 20);
		g.drawPixmap(Assets.clock, (g.getWidth() /2) - ((levelTime.length() * 20) + (Assets.clock.getWidth()) /2), 20);
		drawText(g, levelTime, (g.getWidth() /2) - ((levelTime.length() * 20) /2), 20);
		
		//Creatures
		for(Creature tempCreature : world.creaturesArray){
			tempCreature.draw(g);
			
		}
	}
	
	private void drawPausedUI() {
		Graphics g = game.getGraphics();
		g.drawPixmap(Assets.pause, 80, 100);
		g.drawLine(0, 416, 480, 416, Color.BLACK);
	}
	
	private void drawGameOverUI() {
		Graphics g = game.getGraphics();
		g.drawPixmap(Assets.gameOver, 62, 100);
		g.drawPixmap(Assets.buttons, 128, 200, 0, 128, 64, 64);
		g.drawLine(0, 416, 480, 416, Color.BLACK);
	}
	
	public void drawText(Graphics g, String line, int x, int y) {
		int len = line.length();
		for(int i=0; i<len; i++) {
			char character = line.charAt(i);
			if(character == ' ') {
				x += 20;
				continue;
			}
			int srcX = 0;
			int srcWidth = 0;
			if(character == '.') {
				srcX = 200;
				srcWidth = 10;
			}else {
				srcX = (character - '0') * 20;
				srcWidth = 20;
			}
			
			g.drawPixmap(Assets.numbers, x, y, srcX, 0, srcWidth, 32);
			x += srcWidth;
		}
	}
	
	
	@Override
	public void pause() {
		//Check if we are in "Running" state
		if(state == GameState.Running){
			//Switch to "Paused" state
			state = GameState.Paused;
		}
		//Check if game is over
		if(world.gameOver) {
			//Add score
			Settings.addscore(world.score);
			//Update Global Score
			Settings.addGlobalScore(world.score);
			//Save settings
			Settings.save(game.getFileIO());
		}
	}
	@Override
	public void resume() {
		
	}
	@Override
	public void dispose() {
		
	}
}
