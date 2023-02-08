package states;

import ui.Action;
import ui.Button;

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

public class MenuState extends State {
	
	private ArrayList<Button> buttons;
	private Font font;
	private Sound menuMusic, buttonClick;
	Color specialBlack = new Color(0, 0, 0, 200);
	public static boolean muted = false;
	
	public MenuState() {
		
		buttons = new ArrayList<Button>();
		font = Loader.loadFont("/fonts/futureFont.ttf", 50);
		menuMusic = new Sound(Assets.menuStateMusic);
		menuMusic.loop();
		buttonClick = new Sound(Assets.buttonClick);
		
		buttons.add(new Button(Assets.greyBtn, Assets.blueBtn, Constants.WIDTH / 2 - Assets.greyBtn.getWidth()/2, Constants.HEIGHT / 2 - Assets.greyBtn.getHeight() + 10 , "Jugar", new Action() { @Override public void doAction() { 
			
			buttonClick.changeVolume(-10f);
			buttonClick.play(); 
			menuMusic.stop(); 
			State.changeState(new GameState());  
			} 
		} ));
		
		buttons.add(new Button(Assets.greyBtn, Assets.blueBtn, Constants.WIDTH / 2 - Assets.greyBtn.getWidth()/2, Constants.HEIGHT / 2 + Assets.greyBtn.getHeight() - 25, "Lore", new Action() { @Override public void doAction() { 
			
			buttonClick.changeVolume(-10f);
			buttonClick.play(); 
			menuMusic.stop(); 
			State.changeState(new LoreState());  
			} 
		} ));
		
		buttons.add(new Button(Assets.greyBtn, Assets.blueBtn, Constants.WIDTH / 2 - Assets.greyBtn.getWidth()/2,Constants.HEIGHT / 2 + Assets.greyBtn.getHeight()/2 + 50, "Salir",new Action() { @Override public void doAction() { 
			
			buttonClick.changeVolume(-10f);
			buttonClick.play();
			System.exit(0); 
			} 
		}));
		
		buttons.add(new Button(Assets.greyBtnSmall, Assets.blueBtnSmall, 1250, 650, " ", new Action() { @Override public void doAction() { 
			
			buttonClick.changeVolume(-10f);
			buttonClick.play();
			
			if(!muted) {
				menuMusic.stop();
				muted = true;
			} else {
				menuMusic.play();
				muted = false;
			}
		 } 
		} ));
		
		buttons.add(new Button(Assets.greyBtnSmallInfo, Assets.blueBtnSmallInfo, 50, 650, " ", new Action() { @Override public void doAction() { 
			
			buttonClick.changeVolume(-10f);
			buttonClick.play(); 
			menuMusic.stop(); 
			State.changeState(new CreditsState());  
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
		
		g2d.drawImage(Assets.menuBackground, 0, 0, Constants.WIDTH, Constants.HEIGHT, null);
		
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		g2d.setColor(specialBlack);
		
		g2d.fillRect(Constants.WIDTH / 2 - 310, Constants.HEIGHT / 2 - 145, 620, 55);
		
		Text.drawText(g2d, "Piloto espacial: 2449", new Vector2D(Constants.WIDTH / 2, Constants.HEIGHT / 2 - 80), true, Color.WHITE, font);
		
		for(Button b: buttons) {
			b.draw(g);
		}
			
	}
}

/*
ES: Programado por Juan Manuel Amores (Dingo) con la guía de Joshua Hernandez en youtube.
EN: Programmed by Juan Manuel Amores (Dingo) with the guidance of Joshua Hernandez on youtube.
*/
