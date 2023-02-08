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

public class shopState extends State{
	
	private ArrayList<Button> buttons;
	private Font fontTitle, fontMoney, upgrades;
	private Sound shopMusic, buttonClick, buy, noBuy;
	private int playerFunds;
	private static int propulsorP = 12345, fastShootP = 55555, doubleCanonP = 69696 ;
	public static boolean propulsorB = false, fastShootB = false, doubleCanonB = false;
	Color specialBlack = new Color(0, 0, 0, 127);
	
	public shopState() {
		
		buttons = new ArrayList<Button>();
		shopMusic = new Sound(Assets.shopStateMusic);
		
		if(!MenuState.muted) {
			shopMusic.loop();
		}
		
		buttonClick = new Sound(Assets.buttonClick);
		buy = new Sound(Assets.CashRegister);
		noBuy = new Sound(Assets.errorSound);
		fontTitle = Loader.loadFont("/fonts/futureFont.ttf", 50);
		fontMoney = Loader.loadFont("/fonts/futureFont.ttf", 35);
		upgrades = Loader.loadFont("/fonts/futureFont.ttf", 40);
		playerFunds = GameOverState.getActualFunds();
		
		buttons.add(new Button(Assets.greyBtn, Assets.blueBtn, 10, 10, "Volver", new Action() { @Override public void doAction() { 
			
			shopMusic.stop();
			buttonClick.changeVolume(-10f);
			buttonClick.play();
			State.changeState(new GameOverState()); 
			} 
		} ));
		
		//compra mejora propulsor
		buttons.add(new Button(Assets.greyBtn, Assets.blueBtn, Constants.WIDTH / 2 + 330, 320, "Comprar", new Action() { @Override public void doAction() { 
				
				if(playerFunds >= propulsorP && !propulsorB) {
					playerFunds = playerFunds - propulsorP;
					propulsorB = true;
					buy.play();
					
				} else {
					noBuy.play();
				}
			} 
		} ));
		
		//compra disparo rapido
		buttons.add(new Button(Assets.greyBtn, Assets.blueBtn, Constants.WIDTH / 2 + 330, 370, "Comprar", new Action() { @Override public void doAction() { 
			
				if(playerFunds >= fastShootP && !fastShootB) {
					playerFunds = playerFunds - fastShootP;
					fastShootB = true;
					buy.play();
					
				} else {
					noBuy.play();
				}
			}
		} ));
		
		//compra doble canon
		buttons.add(new Button(Assets.greyBtn, Assets.blueBtn, Constants.WIDTH / 2 + 330, 420,"Comprar",new Action() { @Override public void doAction() { 
			
				if(playerFunds >= doubleCanonP && !doubleCanonB) {
					playerFunds = playerFunds - doubleCanonP;
					doubleCanonB = true;
					buy.play();
					
				} else {
					noBuy.play();
				}
			} 
		}));
		
		buttons.add(new Button(Assets.greyBtnSmall, Assets.blueBtnSmall, 1250, 650, " ", new Action() { @Override public void doAction() { 
			
			buttonClick.changeVolume(-10f);
			buttonClick.play();
			
			if(!MenuState.muted) {
				shopMusic.stop();
				MenuState.muted = true;
			} else {
				shopMusic.play();
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
		
		g2d.drawImage(Assets.shopBackground, 0, 0, Constants.WIDTH, Constants.HEIGHT, null);
		
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		g2d.setColor(specialBlack);
		g2d.fillRect(1070, 10, 200, 40);
		
		g2d.setColor(specialBlack);
		g2d.fillRect(Constants.WIDTH / 2 - 310, Constants.HEIGHT / 2 - 65, 620, 145);
		
		Text.drawText(g2d, "Tienda de mejoras", new Vector2D(Constants.WIDTH / 2, 70), true, Color.WHITE, fontTitle);
		
		Text.drawTextRight(g2d, "$ "+ playerFunds, new Vector2D(1075, 40), true, Color.YELLOW, fontMoney);
		
		Text.drawTextRight(g2d, "Propulsor potente: $12.345" /*12345*/, new Vector2D(Constants.WIDTH / 2 - 305, 350), true, Color.WHITE, upgrades);
		
		Text.drawTextRight(g2d, "Disparo rapido: $55.555" /*55555*/, new Vector2D(Constants.WIDTH / 2 - 305, 400), true, Color.WHITE, upgrades);
		
		Text.drawTextRight(g2d, "Cañon doble: $69.696" /*69696*/, new Vector2D(Constants.WIDTH / 2 - 305, 450), true, Color.WHITE, upgrades);
		
		for(Button b: buttons) {
			b.draw(g);
		}
		
		if(propulsorB) {
			g2d.drawImage(Assets.greyBtnPurchased, Constants.WIDTH / 2 + 330, 320, 190, 45, null);
			Text.drawText(g2d, "Comprado", new Vector2D(Constants.WIDTH / 2 + 425, 363), true, Color.WHITE, Assets.fontMed);
		}
		
		if(fastShootB) {
			g2d.drawImage(Assets.greyBtnPurchased, Constants.WIDTH / 2 + 330, 370, 190, 45, null);
			Text.drawText(g2d, "Comprado", new Vector2D(Constants.WIDTH / 2 + 425, 413), true, Color.WHITE, Assets.fontMed);
		}
		
		if(doubleCanonB) {
			g2d.drawImage(Assets.greyBtnPurchased, Constants.WIDTH / 2 + 330, 420, 190, 45, null);
			Text.drawText(g2d, "Comprado", new Vector2D(Constants.WIDTH / 2 + 425, 463), true, Color.WHITE, Assets.fontMed);
		}
		
	}

}

/*
ES: Programado por Juan Manuel Amores (Dingo) con la guía de Joshua Hernandez en youtube.
EN: Programmed by Juan Manuel Amores (Dingo) with the guidance of Joshua Hernandez on youtube.
*/
