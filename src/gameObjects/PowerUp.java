package gameObjects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import graphics.Assets;
import graphics.Sound;
import math.Vector2D;
import states.GameState;

public class PowerUp extends MovingObject {

	private Sound powerUpPick, pop;
	private BufferedImage typeTexture;
	private Chronometer time;
	private boolean life;
	
	public PowerUp(Vector2D position, BufferedImage texture, GameState gameState, boolean life) {
		
		super(position, new Vector2D(), 0, texture, gameState);
		this.typeTexture = texture;
		this.life = life;
		powerUpPick = new Sound(Assets.powerUpSound);
		pop = new Sound(Assets.powerUpPop);
		pop.changeVolume(-10f);
		time = new Chronometer();
		time.run(Constants.POWER_UP_DURATION);

	}
	
	void executeAction() {
		
		if(life) {
			powerUpPick.play();
			GameState.addLife();
			
			if(powerUpPick.getFramePosition() > 400){
				powerUpPick.stop();
			}
			
		} else {
			powerUpPick.play();
			
			gameState.addScore(1000, position);
			if(powerUpPick.getFramePosition() > 400){
				powerUpPick.stop();
			}
		}	
	}

	@Override
	public void update() {
		
		if(!time.isRunning()) {
		
			pop.play();
			this.Destroy();
			time.run(Constants.POWER_UP_DURATION);
		}
		
		collidesWith();

		angle += 0.05;
		time.update();
		
	}

	@Override
	public void draw(Graphics g) {
		
		Graphics2D g2d = (Graphics2D) g;
		at = AffineTransform.getTranslateInstance(position.getX(), position.getY());
		at.rotate(angle, width/2, height/2);
		g2d.drawImage(typeTexture, at, null);
		
	}
		
}

/*
ES: Programado por Juan Manuel Amores (Dingo) con la guía de Joshua Hernandez en youtube.
EN: Programmed by Juan Manuel Amores (Dingo) with the guidance of Joshua Hernandez on youtube.
*/
