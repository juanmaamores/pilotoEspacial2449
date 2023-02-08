package states;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import gameObjects.Constants;
import graphics.Assets;
import graphics.Loader;
//import graphics.Sound;
import graphics.Text;
import math.Vector2D;

public class LoadingState extends State {

	private Thread loadingThread;
	private Font font, loadingFont;
	Color specialBlack = new Color(0, 0, 0, 200);
	//private Sound announcement;
	
	public LoadingState(Thread loadingThread) {
		
		this.loadingThread = loadingThread;
		this.loadingThread.start();
		
		font = Loader.loadFont("/fonts/futureFont.ttf", 50);
		loadingFont = Loader.loadFont("/fonts/futureFont.ttf", 35);
		//announcement = new Sound(Assets.announcement);
		//announcement.play();
		
	}
	
	@Override
	public void update() {
		
		if(Assets.loaded) {
			State.changeState(new MenuState());
			//announcement.stop();
			try {
				loadingThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void draw(Graphics g) {
		
		GradientPaint gp = new GradientPaint(Constants.WIDTH / 2 - Constants.LOADING_BAR_WIDTH / 2, Constants.HEIGHT / 2 - Constants.LOADING_BAR_HEIGHT / 2, Color.PINK, Constants.WIDTH / 2 + Constants.LOADING_BAR_WIDTH / 2, Constants.HEIGHT / 2 + Constants.LOADING_BAR_HEIGHT / 2, Color.RED);
		
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		
		g2d.drawImage(Assets.menuBackground, 0, 0, Constants.WIDTH, Constants.HEIGHT, null);
		
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		g2d.setPaint(gp);
		
		float percentage = (Assets.count / Assets.MAX_COUNT);
				
		g2d.fillRect(Constants.WIDTH / 2 - Constants.LOADING_BAR_WIDTH / 2, Constants.HEIGHT / 2 - Constants.LOADING_BAR_HEIGHT / 2 + 290, (int)(Constants.LOADING_BAR_WIDTH * percentage), Constants.LOADING_BAR_HEIGHT);
		
		g2d.drawRect(Constants.WIDTH / 2 - Constants.LOADING_BAR_WIDTH / 2, Constants.HEIGHT / 2 - Constants.LOADING_BAR_HEIGHT / 2 + 290, Constants.LOADING_BAR_WIDTH, Constants.LOADING_BAR_HEIGHT);
		
		g2d.setColor(specialBlack);
		
		g2d.fillRect(Constants.WIDTH / 2 - 310, Constants.HEIGHT / 2 - 145, 620, 55);
		
		Text.drawText(g2d, "Piloto espacial: 2449", new Vector2D(Constants.WIDTH / 2, Constants.HEIGHT / 2 - 80), true, Color.WHITE, font);
		
		Text.drawText(g2d, "CARGANDO...", new Vector2D(Constants.WIDTH / 2, Constants.HEIGHT / 2 + 320), true, Color.WHITE, loadingFont);
		
	}

}

/*
ES: Programado por Juan Manuel Amores (Dingo) con la guía de Joshua Hernandez en youtube.
EN: Programmed by Juan Manuel Amores (Dingo) with the guidance of Joshua Hernandez on youtube.
*/
