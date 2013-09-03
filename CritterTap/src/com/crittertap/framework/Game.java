package com.crittertap.framework;

import android.content.Context;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;

public interface Game {
    public Input getInput();

    public FileIO getFileIO();

    public Graphics getGraphics();

    public Audio getAudio();

    public void setScreen(Screen screen);

    public Screen getCurrentScreen();

    public Screen getStartScreen();
    
    public MobileServiceClient getMobileClient();
    
    public Context getContext();
}