package states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import gameObjects.Constants;
import graphics.Assets;
import graphics.Loader;
import graphics.Sound;
import graphics.Text;
import math.Vector2D;
import ui.Action;
import ui.Button;

public class GameOverState extends State {
	
	private ArrayList<Button> buttons;
	private Font font;
	private static int playerFunds = 0, playerLastScore;
	private Sound gameOverMusic, buttonClick;
	Color specialBlack = new Color(0, 0, 0, 127);
	
	public GameOverState() {
		
		GameState.stopUfoSound();
		GameState.stopWarMachinesSound();
		buttons = new ArrayList<Button>();
		font = Loader.loadFont("/fonts/futureFont.ttf", 38);
		playerFunds = playerFunds + GameState.getScore();
		playerLastScore = GameState.getScore();
		GameState.setScore(0);
		buttonClick = new Sound(Assets.buttonClick);
		gameOverMusic = new Sound(Assets.gameOverMusic);
		GameState.addLife();
		GameState.addLife();
		
		
		if(!MenuState.muted) {
			gameOverMusic.loop();
		}
		
		buttons.add(new Button(Assets.greyBtn, Assets.blueBtn, Constants.WIDTH / 2 - Assets.greyBtn.getWidth()/2, Constants.HEIGHT / 2 - Assets.greyBtn.getHeight()+35, "Continuar", new Action() { @Override public void doAction() { 
			
			buttonClick.changeVolume(-10f);
			buttonClick.play();
			gameOverMusic.stop();
			State.changeState(new GameState()); 
			} 
		} ));
		
		buttons.add(new Button(Assets.greyBtn, Assets.blueBtn, Constants.WIDTH / 2 - Assets.greyBtn.getWidth()/2, Constants.HEIGHT / 2 + Assets.greyBtn.getHeight(), "Tienda", new Action() { @Override public void doAction() { 
			
			buttonClick.changeVolume(-10f);
			buttonClick.play(); 
			gameOverMusic.stop(); 
			State.changeState(new shopState());  
			} 
		} ));
		
		buttons.add(new Button(Assets.greyBtn, Assets.blueBtn, Constants.WIDTH / 2 - Assets.greyBtn.getWidth()/2,Constants.HEIGHT / 2 + Assets.greyBtn.getHeight()/2 +80, "Salir",new Action() { @Override public void doAction() { 
			
			buttonClick.changeVolume(-10f);
			buttonClick.play();
			gameOverMusic.stop();
			System.exit(0); 
			} 
		} ));
		
		buttons.add(new Button(Assets.greyBtnSmall, Assets.blueBtnSmall, 1250, 650, " ", new Action() { @Override public void doAction() { 
			
			buttonClick.changeVolume(-10f);
			buttonClick.play();
			
			if(!MenuState.muted) {
				gameOverMusic.stop();
				MenuState.muted = true;
			} else {
				gameOverMusic.play();
				MenuState.muted = false;
			}
		 } 
		} ));
		
	}

	@Override
	public void update() {
		
		
		
		for(Button b: buttons) {
			b.update();
		}
		
	}

	@Override
	public void draw(Graphics g) {
		
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		
		g2d.drawImage(Assets.gameOverBackground, 0, 0, Constants.WIDTH, Constants.HEIGHT, null);
		
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		g2d.setColor(specialBlack);
		g2d.fillRect(Constants.WIDTH / 2 - 305, Constants.HEIGHT / 2 - 250, 610, 195);
		
		Text.drawText(g2d, "Fin de la partida", new Vector2D(Constants.WIDTH / 2, Constants.HEIGHT / 2 - 200), true, Color.RED, font);
		
		Text.drawTextRight(g2d, "Oleada alcanzada: "+GameState.getWaves(), new Vector2D(Constants.WIDTH / 2 -290, Constants.HEIGHT / 2 - 170), true, Color.WHITE, font);
		
		Text.drawTextRight(g2d, "Paga recolectada: $ "+playerLastScore, new Vector2D(Constants.WIDTH / 2 -290, Constants.HEIGHT / 2 - 120), true, Color.GREEN, font);
		
		Text.drawTextRight(g2d, "Ahorros totales: $ "+playerFunds, new Vector2D(Constants.WIDTH / 2 -290, Constants.HEIGHT / 2 - 70), true, Color.YELLOW, font);
		
		for(Button b: buttons) {
			b.draw(g);
		}
		
	}
	
	public static int getActualFunds() {
		
		return playerFunds;
	}

}

/*
ES: Programado por Juan Manuel Amores (Dingo) con la guía de Joshua Hernandez en youtube.
EN: Programmed by Juan Manuel Amores (Dingo) with the guidance of Joshua Hernandez on youtube.
*/
