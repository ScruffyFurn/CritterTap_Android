package com.crittertap;

import com.crittertap.framework.Screen;
import com.crittertap.framework.implemented.AndroidGame;

public class CritterTapGame extends AndroidGame {
	public Screen getStartScreen() {
		return new LoadingScreen(this);
	}
}