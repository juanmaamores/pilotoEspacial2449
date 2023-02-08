package gameObjects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import graphics.Assets;
import graphics.Sound;
import math.Vector2D;
import states.GameState;

public class WarMachine extends MovingObject{
	
	private ArrayList<Vector2D> path;
	private Vector2D currentNode;
	private int index;
	private boolean following;
	private Chronometer fireRate;
	private Sound shoot;
	private Vector2D heading1, heading2, heading3, heading4, heading5, heading6;
	private Vector2D headingL1, headingL2, headingL3, headingL4, headingL5, headingL6;
		
	public WarMachine(Vector2D position, Vector2D velocity, double maxVel, BufferedImage texture, ArrayList<Vector2D> path, GameState gameState) {
		
		super(position, velocity, maxVel, texture, gameState);
		this.path = path;
		index = 0;
		following = true;
		fireRate = new Chronometer();
		fireRate.run(Constants.WAR_M_FIRE_RATE);
		shoot = new Sound(Assets.ufoShoot);
		
		heading1 = new Vector2D(0,1);
		heading2 = new Vector2D(1,1);
		heading3 = new Vector2D(-1,1);
		heading4 = new Vector2D(0,1);
		heading5 = new Vector2D(1,1);
		heading6 = new Vector2D(-1,1);

		headingL1 = new Vector2D(0,1);
		headingL2 = new Vector2D(0,1);
		headingL3 = new Vector2D(0,1);
		headingL4 = new Vector2D(0,1);
		headingL5 = new Vector2D(0,1);
		headingL6 = new Vector2D(0,1);
		
		headingL1 = headingL1.setDirection(Math.PI/2); // Math.PI/2 = dispara recto hacía abajo.
		headingL2 = headingL2.setDirection(Math.PI/4); // -Math.PI/2 dispara recto hacia arriba.
		headingL3 = headingL3.setDirection((Math.PI*3)/4); // (Math.PI*3)/4 dispara hacia abajo y a la izquierda a 45º.
		headingL4 = headingL4.setDirection(-Math.PI/2); // -Math.PI/2 dispara hacia abajo y a la derecha a 45º.
		headingL5 = headingL5.setDirection(-(Math.PI*3)/4); // (Math.PI*3)/4 dispara hacia abajo y a la izquierda a 45º.
		headingL6 = headingL6.setDirection(-Math.PI/4); // -Math.PI/2 dispara hacia abajo y a la derecha a 45º.
		
	}
	
	private Vector2D pathFollowing() {
		currentNode = path.get(index);
		
		double distanceToNode = currentNode.subtract(getCenter()).getMagnitude();
		
		if(distanceToNode < Constants.NODE_RADIUS) {
			index ++;
			if(index >= path.size()) {
				following = false;
			}
		}
		
		return seekForce(currentNode);
		
	}
	
	private Vector2D seekForce(Vector2D target) {
		Vector2D desiredVelocity = target.subtract(getCenter());
		desiredVelocity = desiredVelocity.normalize().scale(maxVel);
		return desiredVelocity.subtract(velocity);
	}
	
	@Override
	public void update() {
		
		Vector2D pathFollowing;
		
		if(following)
			pathFollowing = pathFollowing();
		else
			pathFollowing = new Vector2D();
		
		pathFollowing = pathFollowing.scale(1/Constants.UFO_MASS);
		
		velocity = velocity.add(pathFollowing);
		
		velocity = velocity.limit(maxVel);
		
		position = position.add(velocity);
		
		if(position.getX() > Constants.WIDTH || position.getY() > Constants.HEIGHT || position.getX() < -width || position.getY() < -height)
			Destroy();
		
		// shoot
		
		if(!fireRate.isRunning()) {
			
			Laser laser1 = new Laser(getCenter().add(heading1.scale(width)), headingL1, Constants.WAR_M_LASER_SPEED, Math.PI , Assets.blueLaser, gameState);
			Laser laser2 = new Laser(getCenter().add(heading2.scale(width)), headingL2, Constants.WAR_M_LASER_SPEED, (Math.PI*3)/4 , Assets.blueLaser, gameState);
			Laser laser3 = new Laser(getCenter().add(heading3.scale(width)), headingL3, Constants.WAR_M_LASER_SPEED, -(Math.PI*3)/4 , Assets.blueLaser, gameState);
			Laser laser4 = new Laser(getCenter().add(heading4.scale(-width)), headingL4, Constants.WAR_M_LASER_SPEED, 0 , Assets.blueLaser, gameState);
			Laser laser5 = new Laser(getCenter().add(heading5.scale(-width)), headingL5, Constants.WAR_M_LASER_SPEED, -(Math.PI)/4 , Assets.blueLaser, gameState);
			Laser laser6 = new Laser(getCenter().add(heading6.scale(-width)), headingL6, Constants.WAR_M_LASER_SPEED, (Math.PI)/4 , Assets.blueLaser, gameState);
		
			gameState.getMovingObjects().add(0, laser1); //laser inferior 90º
			gameState.getMovingObjects().add(0, laser2); //laser inferior 45º
			gameState.getMovingObjects().add(0, laser3); //laser inferior -45º
			gameState.getMovingObjects().add(0, laser4); //laser inferior 90º
			gameState.getMovingObjects().add(0, laser5); //laser inferior 45º
			gameState.getMovingObjects().add(0, laser6); //laser inferior -45º
			
			fireRate.run(Constants.WAR_M_FIRE_RATE);
			shoot.play();
			
		}
		
		if(shoot.getFramePosition() > 8500) {
			shoot.stop();
		}
		
		angle += 0.05;
		
		collidesWith();
		fireRate.update();
		
	}
	
	@Override
	public void Destroy() {
		
		gameState.subtractWarMachineCounter(1);
		if(!(position.getX() > Constants.WIDTH || position.getY() > Constants.HEIGHT || position.getX() < -width || position.getY() < -height)) {
			gameState.addScore(Constants.WAR_M_SCORE, position);
		} else {
			gameState.sustractScore(Constants.WAR_M_SCORE_LOOSE, position);
			//gameState.substractLife();
		}
		
		super.Destroy();
		
	}
	
	@Override
	public void draw(Graphics g) {
		
		Graphics2D g2d = (Graphics2D)g;
		at = AffineTransform.getTranslateInstance(position.getX(), position.getY());
		g2d.drawImage(texture, at, null);
		
	}
	
}

/*
ES: Programado por Juan Manuel Amores (Dingo) con la guía de Joshua Hernandez en youtube.
EN: Programmed by Juan Manuel Amores (Dingo) with the guidance of Joshua Hernandez on youtube.
*/
