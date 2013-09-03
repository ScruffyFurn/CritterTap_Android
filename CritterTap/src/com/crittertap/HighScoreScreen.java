package com.crittertap;

import java.util.List;

import com.crittertap.framework.Game;
import com.crittertap.framework.Graphics;
import com.crittertap.framework.Screen;
import com.crittertap.framework.Input.TouchEvent;

public class HighScoreScreen extends Screen {
	String globalHighScore;
	String localHighScore;
	
	
	public HighScoreScreen (Game game){
		super (game);
		globalHighScore = "" + Settings.getGlobalHighScore();
		localHighScore = "" + Settings.localhighscore;
	}
	
	@Override
	public void update(float deltaTime) {
		
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();
		
		int len = touchEvents.size();
		for(int i=0; i<len; i++) {
			TouchEvent event = touchEvents.get(i);
			if(event.type == TouchEvent.TOUCH_UP) {
				if(event.x < 64 && event.y > 416) {
					if(Settings.soundEnabled)
						Assets.click.play(1);
					game.setScreen(new MainMenuScreen(game));
					return;
				}
			}
		}	
	}
	
	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();
		g.drawPixmap(Assets.background, 0, 0);
		g.drawPixmap(Assets.mainMenu, 64, 20, 0, 42, 196, 42);
		
		g.drawPixmap(Assets.global,((g.getWidth() /2) - (Assets.global.getWidth() /2)), 150);
		drawText(g,globalHighScore,(g.getWidth() - globalHighScore.length() * 20) /2,200);
		
		g.drawPixmap(Assets.your,((g.getWidth() /2) - (Assets.your.getWidth() /2)), 300);
		drawText(g,localHighScore,(g.getWidth() - localHighScore.length() * 20) /2,350);
		
		g.drawPixmap(Assets.buttons, 0, 416, 64, 64, 64, 64);
	}
	
	public void drawText(Graphics g, String line, int x, int y) {
		int len = line.length();
		for(int i = 0; i<len; i++){
			char character = line.charAt(i);
			if(character == ' '){
				x+=20;
				continue;
			}
			int srcX = 0;
			int srcWidth = 0;
			if(character == '.'){
				srcX = 100;
				srcWidth = 10;
			}else{
				srcX = (character - '0') * 20;
				srcWidth = 20;
			}
			g.drawPixmap(Assets.numbers, x, y, srcX, 0, srcWidth, 32);
			x += srcWidth;
		}
	}
	@Override
	public void pause(){

	}
	@Override
	public void resume(){

	}
	@Override
	public void dispose(){
		
	}
}

