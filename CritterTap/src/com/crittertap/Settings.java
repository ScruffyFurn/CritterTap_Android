package com.crittertap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import com.crittertap.framework.FileIO;
import com.microsoft.windowsazure.mobileservices.*;

public class Settings {
	public static boolean soundEnabled = true;
	public static int localhighscore; 
	
	private static MobileServiceClient mobileClient;
	
	public static List<GlobalHighScore> gHighScores = new ArrayList<GlobalHighScore>();
	
	
	public static void load(FileIO files) {
		BufferedReader in = null;
		try {
			in = new BufferedReader( new InputStreamReader(files.readFile(".wackamole")));
			soundEnabled = Boolean.parseBoolean(in.readLine());
			localhighscore = Integer.parseInt(in.readLine());
		} catch (IOException e) {
			// : ( It's OK we have defaults)
		} catch  (NumberFormatException e) {
			// :/ IT's ok, defaults save our day
		} finally {
			try {
				if (in !=null )
					in.close();
			} catch (IOException e) {
				
			}
		}	
	}
	
	public static void save(FileIO files) {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter( new OutputStreamWriter(files.writeFile(".wackamole")));
			out.write(Boolean.toString(soundEnabled));
			out.write(Integer.toString(localhighscore));
			} catch (IOException e) {
			} finally {
				try {
					if (out !=null)
						out.close();
				} catch (IOException e){
					
				}
			}
	}
	
	public static void addscore(int score) {
		if(localhighscore < score){
			localhighscore = score;
		}
	}
	
	public static void globalHighScoreSetup(MobileServiceClient mClient){
		mobileClient = mClient;
		
		mobileClient.getTable(GlobalHighScore.class).orderBy(
				"Score", QueryOrder.Descending).execute(new TableQueryCallback<GlobalHighScore>() {   
	        public void onCompleted(List<GlobalHighScore> result, 
	                                int count,
	                                Exception exception, 
	                                ServiceFilterResponse response) {
	            if (exception == null) {
	            	for(GlobalHighScore ghs : result) {
						gHighScores.add(ghs);
					}
	            } 
	        }
		});
	}
	
	public static void addGlobalScore(int score) {
		
		GlobalHighScore gHighScore = new GlobalHighScore();
		gHighScore.Score = score;
		
		mobileClient.getTable(GlobalHighScore.class).insert(gHighScore, new TableOperationCallback<GlobalHighScore>() {
		      public void onCompleted(GlobalHighScore entity, Exception exception, ServiceFilterResponse response) {
		            if (exception == null) {
		                  // Insert succeeded
		            } else {
		                  // Insert failed
		            }
		      }
		});
	}
	
	public static int getGlobalHighScore() {
		
			return gHighScores.get(0).Score;
	}
}