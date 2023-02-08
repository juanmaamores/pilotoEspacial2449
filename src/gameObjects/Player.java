package gameObjects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import graphics.Assets;
import graphics.Sound;
import input.KeyBoard;
import math.Vector2D;
import states.GameState;
import states.shopState;

public class Player extends MovingObject{
	
	private Vector2D heading;	
	private Vector2D acceleration;
	private Chronometer fireRate;
	private boolean spawning, visible, accelerating = false;
	public static boolean keyPressed = false;
	private Chronometer spawnTime, flickerTime;
	private Sound shoot, propulsion, explosion;
	private double playerAcceleration = 0.2;
	private int playerFireRate = 300;
	
	public Player(Vector2D position, Vector2D velocity, double maxVel, BufferedImage texture, GameState gameState) {
		super(position, velocity, maxVel, texture, gameState);
		heading = new Vector2D(0, 1);
		acceleration = new Vector2D();
		fireRate = new Chronometer();
		spawnTime = new Chronometer();
		flickerTime = new Chronometer();
		shoot = new Sound(Assets.playerShoot);
		shoot.changeVolume(-20f);
		if(shopState.propulsorB) {
			propulsion = new Sound(Assets.propulsionBoostedSound);
		} else {
			propulsion = new Sound(Assets.propulsionDefaultSound);
		}
		propulsion.changeVolume(-10);
		//loose.changeVolume();
		if(shopState.propulsorB) {
			playerAcceleration = 0.3;
		}
		
		if(shopState.fastShootB) {
			playerFireRate = 200;
		}
		explosion = new Sound(Assets.explosion);
		explosion.changeVolume(-10f);
		
	}
	
	@Override
	public void update() {
		
		if(!spawnTime.isRunning()) {
			spawning = false;
			visible = true;
		}
		
		if(spawning) {
			
			if(!flickerTime.isRunning()) {
				flickerTime.run(Constants.FLICKER_TIME);
				visible = !visible;
			}
		}
		
		if(KeyBoard.SHOOT &&  !fireRate.isRunning() && !spawning && !shopState.doubleCanonB){
			
			gameState.getMovingObjects().add(0,new Laser(getCenter().add(heading.scale(width)), heading, Constants.LASER_VEL, angle, Assets.redLaser, gameState));
			fireRate.run(playerFireRate);
			shoot.play();
			
		} else if(KeyBoard.SHOOT &&  !fireRate.isRunning() && !spawning && shopState.doubleCanonB) {
			
			Vector2D leftGun = getCenter();
			Vector2D rightGun = getCenter();
			
			Vector2D temp = heading;
			
			temp.normalize();
			temp = temp.setDirection(angle - 1.3f);
			temp = temp.scale(width);
			rightGun = rightGun.add(temp);
			
			temp = temp.setDirection(angle - 1.9f);
			leftGun = leftGun.add(temp);
			
			
			gameState.getMovingObjects().add(0, new Laser(leftGun, heading, Constants.LASER_VEL, angle, Assets.redLaser, gameState));
			gameState.getMovingObjects().add(0, new Laser(rightGun, heading, Constants.LASER_VEL, angle, Assets.redLaser, gameState));
			
			fireRate.run(playerFireRate);
			shoot.play();
		}
		
		if(shoot.getFramePosition() > 8500) {
			shoot.stop();
			
		} else if(shopState.fastShootB && shoot.getFramePosition() > 3000){
			shoot.stop();
		}
		
		if(KeyBoard.RIGHT)
			angle += Constants.DELTAANGLE;
		
		if(KeyBoard.LEFT)
			angle -= Constants.DELTAANGLE;
		
		if(KeyBoard.UP) {	
			acceleration = heading.scale(playerAcceleration);
			accelerating = true;
			
		} else {
			
			if(velocity.getMagnitude() != 0)
				acceleration = (velocity.scale(-1).normalize()).scale(playerAcceleration/2);
			accelerating = false;
		}
		
		if(KeyBoard.UP && !keyPressed) {
			
			keyPressed = true;
			propulsion.loop();
			
		} else if (!KeyBoard.UP) {
			keyPressed = false;
			propulsion.stop();
		}
		
		velocity = velocity.add(acceleration);
		
		velocity = velocity.limit(maxVel);
		
		heading = heading.setDirection(angle - Math.PI/2);
		
		position = position.add(velocity);
		
		if(position.getX() > Constants.WIDTH)
			position.setX(0);
		if(position.getY() > Constants.HEIGHT)
			position.setY(0);
		
		if(position.getX() < -width)
			position.setX(Constants.WIDTH);
		if(position.getY() < -height)
			position.setY(Constants.HEIGHT);
		
		fireRate.update();
		spawnTime.update();
		flickerTime.update();
		collidesWith();
	}
	
	@Override
	public void Destroy() {
		propulsion.stop();
		explosion.play();
		spawning = true;
		spawnTime.run(Constants.SPAWNING_TIME);
		if(!gameState.substractLife()) {
			gameState.gameOver();
			super.Destroy();
		}
		resetValues();
	}
	
	private void resetValues() {
		angle = 0;
		velocity = new Vector2D();
		
		if(!shopState.doubleCanonB) {
			position = new Vector2D(Constants.WIDTH/2 - Assets.playerSimple.getWidth()/2, Constants.HEIGHT/2 - Assets.playerSimple.getHeight()/2);
		} else {
			position = new Vector2D(Constants.WIDTH/2 - Assets.playerDouble.getWidth()/2, Constants.HEIGHT/2 - Assets.playerDouble.getHeight()/2);
		}
	}
	
	@Override
	public void draw(Graphics g) {
		
		if(!visible)
			return;
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		AffineTransform at1 = AffineTransform.getTranslateInstance(position.getX() + width/2 -8, position.getY()+49);
		at1.rotate(angle, +8, height/2 -49);
		
		AffineTransform at2 = AffineTransform.getTranslateInstance(position.getX() + width/2 -11, position.getY()+49);
		at2.rotate(angle, +11, height/2 -49);
		
		if(accelerating)
		{
			if(!shopState.propulsorB){
				g2d.drawImage(Assets.propulsionDefault, at1, null);
			} else {
				g2d.drawImage(Assets.propulsionBoosted, at2, null);
			}
			
		}
		
		at = AffineTransform.getTranslateInstance(position.getX(), position.getY());
		at.rotate(angle, width/2, height/2);
		g2d.drawImage(texture, at, null);
		
	}
	
	public boolean isSpawning() {
		
		return spawning;
	}
	
}

/*
ES: Programado por Juan Manuel Amores (Dingo) con la guía de Joshua Hernandez en youtube.
EN: Programmed by Juan Manuel Amores (Dingo) with the guidance of Joshua Hernandez on youtube.
*/
