package com.crittertap;

import com.crittertap.Assets;
import com.crittertap.MainMenuScreen;
import com.crittertap.Settings;
import com.crittertap.framework.Game;
import com.crittertap.framework.Graphics;
import com.crittertap.framework.Screen;
import com.crittertap.framework.Graphics.PixmapFormat;

public class LoadingScreen extends Screen {
    public LoadingScreen(Game game) {
        super(game);
    }

    public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        Assets.background = g.newPixmap("background.png", PixmapFormat.RGB565);
        Assets.logo = g.newPixmap("logo.png", PixmapFormat.ARGB4444);
        Assets.mainMenu = g.newPixmap("mainmenu.png", PixmapFormat.ARGB4444);
        Assets.buttons = g.newPixmap("buttons.png", PixmapFormat.ARGB4444);
        Assets.help = g.newPixmap("help.png", PixmapFormat.ARGB4444);
        Assets.numbers = g.newPixmap("numbers.png", PixmapFormat.ARGB4444);
        Assets.ready = g.newPixmap("ready.png", PixmapFormat.ARGB4444);
        Assets.pause = g.newPixmap("pausemenu.png", PixmapFormat.ARGB4444);
        Assets.gameOver = g.newPixmap("gameover.png", PixmapFormat.ARGB4444);
        Assets.cat = g.newPixmap("cat.png", PixmapFormat.ARGB4444);
        Assets.dog = g.newPixmap("dog.png", PixmapFormat.ARGB4444);
        Assets.fox = g.newPixmap("fox.png", PixmapFormat.ARGB4444);
        Assets.mole = g.newPixmap("mole.png", PixmapFormat.ARGB4444);
        Assets.stars = g.newPixmap("stars.png", PixmapFormat.ARGB4444);
        Assets.clock = g.newPixmap("clock.png", PixmapFormat.ARGB4444);
        Assets.global = g.newPixmap("global.png", PixmapFormat.ARGB4444);
        Assets.your = g.newPixmap("yourscore.png", PixmapFormat.ARGB4444);
        
        Assets.click = game.getAudio().newSound("click.ogg");
        Settings.load(game.getFileIO());
        game.setScreen(new MainMenuScreen(game));
        Settings.azureSetup(game.getMobileClient());
    }
    
    public void present(float deltaTime) {

    }

    public void pause() {

    }

    public void resume() {

    }

    public void dispose() {

    }
}
